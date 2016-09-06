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
package org.openo.orchestrator.nfv.dac.snmptrap.processor;

import org.openo.orchestrator.nfv.dac.snmptrap.entity.TrapData;
import org.snmp4j.PDU;


public interface TrapProcessor
{
    public boolean process(TrapData trapData, PDU trapPDU);

    public TrapProcessor getNext();

    public void setNext(TrapProcessor next);
}
