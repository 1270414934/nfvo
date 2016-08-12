/**
 * Copyright (C) 2015 CMCC, Inc. and others. All rights reserved. (CMCC)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openo.orchestrator.nfv.umc.monitor.wrapper;

import java.util.Map;
import java.util.UUID;

import org.openo.orchestrator.nfv.umc.db.entity.MonitorInfo;
import org.openo.orchestrator.nfv.umc.monitor.bean.MonitorParamInfo;
import org.openo.orchestrator.nfv.umc.monitor.bean.MonitorResult;
import org.openo.orchestrator.nfv.umc.monitor.bean.MonitorTaskInfo;
import org.openo.orchestrator.nfv.umc.monitor.common.MonitorConst;
import org.openo.orchestrator.nfv.umc.pm.common.PmConst;
import org.openo.orchestrator.nfv.umc.pm.db.process.PmCommonProcess;
import org.openo.orchestrator.nfv.umc.pm.db.process.PmDBProcess;
import org.openo.orchestrator.nfv.umc.pm.services.NeHandler;
import org.openo.orchestrator.nfv.umc.pm.task.PmTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: MonitorService
 * @Description: TODO(Monitor CRDU implementation class)
 * @author tanghua10186366
 * @date 2015.12.20
 *
 */
public class MonitorServiceWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorServiceWrapper.class);

    private static MonitorServiceWrapper instance = new MonitorServiceWrapper();

    private MonitorServiceWrapper() {}

    public static MonitorServiceWrapper getInstance() {
        return instance;
    }


    /**
     * @Title updateMonitorTask
     * @Description update Monitor Task
     * @param monitorTaskInfo
     * @param ifProxyIPChange (if changed:1 then will reCreate the Task,else modify the Task)
     * @return MonitorResult
     */
    public MonitorResult updateMonitorTask(MonitorTaskInfo monitorTaskInfo, int ifProxyIPChange) {
        MonitorResult monitorResult = new MonitorResult();

        try {
            if (ifProxyIPChange == MonitorConst.IF_PROXYIP_CHANGE_TRUE) {
                PmTaskService.pmTaskReCreate(null, monitorTaskInfo.getCustomPara().getProxyIp(),
                        monitorTaskInfo.getOid(), monitorTaskInfo.getMoc());
                LOGGER.info(monitorTaskInfo.getOid() + " pmTask ReCreate to ProxyIp: "+monitorTaskInfo.getCustomPara().getProxyIp());

            } else {
                PmTaskService.pmTaskModify(monitorTaskInfo.getOid());
                LOGGER.info(monitorTaskInfo.getOid() + " pmTask Modify");
            }


            monitorResult.setResult(MonitorConst.REQUEST_SUCCESS);
            monitorResult.setInfo(monitorTaskInfo.getOid() + " monitorTaskInfo updated ok");
            LOGGER.info(monitorTaskInfo.getOid() + " monitorTaskInfo updated ok");
        } catch (Exception e) {
            monitorResult.setResult(MonitorConst.REQUEST_FAIL);
            monitorResult.setInfo(monitorTaskInfo.getOid() + " monitorTaskInfo updated fail:"
                    + e.getMessage());
            LOGGER.error(monitorTaskInfo.getOid() + " monitorTaskInfo updated fail:"
                    + e.getMessage());
        }
        return monitorResult;
    }

    /**
     * @author wangjiangping
     * @date 2016/4/13 11:12:00
     * @description add device monitor param information into datatable.
     */
    public MonitorResult addMonitorInfo(MonitorParamInfo paramInfo) {
        MonitorResult monitorResult = new MonitorResult();

        MonitorInfo monitorInfo =  new MonitorInfo();
        String oid = paramInfo.getOid();
        if(oid == null){
            oid = UUID.randomUUID().toString();
        }
        monitorInfo.setOid(oid);
        monitorInfo.setIpAddress(paramInfo.getIpAddress());
        monitorInfo.setNeTypeId(paramInfo.getNeTypeId());
        monitorInfo.setCustomPara(paramInfo.getCustomPara());
        monitorInfo.setLabel(paramInfo.getLabel());
        monitorInfo.setExtendPara(paramInfo.getExtendPara());

        String origin = paramInfo.getOrigin();
        if(origin!=null){
            monitorInfo.setOrigin(origin);
        }else{
            monitorInfo.setOrigin("client");
        }

        try{
            PmCommonProcess.save(PmConst.MONITOR_INFO, monitorInfo);
            Map<String, String> infoMap = WrapperUtil.convertParamInfo2Map(monitorInfo);
            WrapperUtil.cacheTrapInfo(monitorInfo, infoMap);// cache trapinfo if ne support receive trap
            NeHandler.createHandle(infoMap, monitorInfo.getOid(), monitorInfo.getNeTypeId());
            monitorResult.setResult(MonitorConst.REQUEST_SUCCESS);
            monitorResult.setOid(oid);
            monitorResult.setInfo(paramInfo.getLabel() + " monitorParamInfo add ok");
            LOGGER.info(paramInfo.getLabel() + " monitorParamInfo add ok");

        }catch(Exception e){
            monitorResult.setResult(MonitorConst.REQUEST_FAIL);
            monitorResult.setInfo(paramInfo.getLabel() + " monitorParamInfo add fail:"
                    + e.getMessage());
            LOGGER.error(paramInfo.getLabel() + " monitorParamInfo add fail:"
                    + e.getMessage());
        }

        return monitorResult;
    }

    /**
     * @author wangjiangping
     * @date 2016/4/13 11:23:04
     * @description delete device monitor param information from datatable by oid.
     */
    public MonitorResult deleteMonitorInfo(String oid) {
        MonitorResult monitorResult = new MonitorResult();

        try{
            PmDBProcess.deleteDbByOid(PmConst.MONITOR_INFO, oid);
            WrapperUtil.removeTrapInfo(oid);//remove trap info
            NeHandler.deleteHandle(oid);
            monitorResult.setResult(MonitorConst.REQUEST_SUCCESS);
            monitorResult.setInfo(oid + " monitorParamInfo delete ok");
            LOGGER.info(oid + " monitorParamInfo delete ok");

        }catch(Exception e){
            monitorResult.setResult(MonitorConst.REQUEST_FAIL);
            monitorResult.setInfo(oid + " monitorParamInfo delete fail:"
                    + e.getMessage());
            LOGGER.error(oid + " monitorParamInfo delete fail:"
                    + e.getMessage());
        }

        return monitorResult;
    }
}
