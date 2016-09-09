/*
 * Copyright 2016, CMCC Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openo.nfvo.monitor.umc.pm.task.threshold;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.openo.nfvo.monitor.umc.pm.common.DebugPrn;
import org.openo.nfvo.monitor.umc.pm.common.PmOsfUtil;


/**
 * Performance Threshold Data Queue.
 */
public class ThresholdDataQueue extends Thread
{
	private static DebugPrn dMsg = new DebugPrn(ThresholdDataQueue.class.getName());

	private int maxQueueSize = 500;
	private int maxThreadNum = 10;
	private int runCollectPeriod = 1;
	private transient boolean isWorking = true;

	private ConcurrentLinkedQueue<Map> queue = new ConcurrentLinkedQueue<Map>();
	private ThreadPoolExecutor poolThreads = null;

	/**
	 * 
	 */
	public ThresholdDataQueue()
	{
		init();
		poolThreads = new ThreadPoolExecutor(10, maxThreadNum, 2, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(
				maxQueueSize), new DefaultRejectedExecutionHandler());

	}

	private void init()
	{
		Hashtable<String, String> htQueue = PmOsfUtil.getHmQueueInfo("ThresholdDataQueue");
		maxQueueSize = Integer.parseInt(htQueue.get("maxQueueSize"));
		int tmp_maxThreadNum = Integer.parseInt((String) htQueue.get("maxThreadNum"));
		maxThreadNum = tmp_maxThreadNum < 10 ? 10 : tmp_maxThreadNum;
	}

	public void run()
	{
		while (isWorking)
		{
			if (queue.size() == 0)
			{
				try
				{
					Thread.sleep(runCollectPeriod * 100);
				}
				catch (InterruptedException e)
				{
					dMsg.warn("Thread Interrupted!", e);
				}
			} else
			{
				dispatchDataRptMsg();
			}
		}
	}

	/**
	 * Processing queue data.
	 */
	private void dispatchDataRptMsg()
	{
		while (queue.size() > 0)
		{
			Map attrList = (Map) queue.remove();
			dMsg.debug("PM DATA queue size:" + poolThreads.getQueue().size());
			poolThreads.execute(new ThresholdHandleThread(attrList));
		}
	}

	/**
	 * put data to the queue.
	 */
	public void put(Map attrList)
	{
		queue.add(attrList);
	}

	public void close()
	{
		isWorking = false;
		poolThreads.shutdownNow();
	}

	public boolean isRunning()
	{
		return isWorking;
	}
	private class DefaultRejectedExecutionHandler implements
	RejectedExecutionHandler {

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			dMsg.info("PM DATA queue full!" + executor.getQueue().size());
            if (!executor.isShutdown()) {
            	executor.getQueue().poll();
            	executor.execute(r);
            }
		}
	}
}
