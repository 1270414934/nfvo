/**
 * Copyright (C) 2015 CMCC, Inc. and others. All rights reserved. (CMCC)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openo.orchestrator.nfv.umc.drill.resources.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.openo.orchestrator.nfv.umc.drill.wrapper.common.TopologyConsts;
import org.openo.orchestrator.nfv.umc.drill.wrapper.resources.bean.NsData;

/**
 * @author 10188044
 * @date 2015-8-12
 *       <p/>
 *       Detail information of Network Service
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class NSInformation extends NodeInformation {
    private String status;
    private String vendor;
    private String version;
    private final String rendertype = TopologyConsts.RENDERTYPE_NS;

    public NSInformation(NsData nsData) {
        this(nsData.getStatus(), nsData.getVendor(), nsData.getVersion());
        this.setId(nsData.getOid());
        this.setName(nsData.getName());
        this.setMoc(nsData.getMoc());
        this.setMoc_name(nsData.getMocName());
    }
}
