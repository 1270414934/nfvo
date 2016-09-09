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
package org.openo.nfvo.monitor.umc.snmptrap.common;

import java.util.Date;
import java.util.Properties;

import lombok.Data;

@Data
public class TrapMsgData
{
	private String eventKey;
    private byte eventType;
    private String entity;
    private long alarmCode = 0;
    private long reasonCode = -1;
    private byte severityLevel;
    private String trapOid;
    private String ipAddress;
    private String neId;
    private String neType;
    private int systemType;
    private Date AlarmRaiseTime;
    public Properties bindValues;
}
