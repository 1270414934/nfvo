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
package org.openo.orchestrator.nfv.umc.drill.wrapper.resources.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author 10188044
 * @date 2015-8-13
 *       <p/>
 *       the entity definition of the VIM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VimData extends NfvoData {
    private String user;
    private String type;
    private String url;
    private String hostName;
    private String status;
    private String errorInfo;
    private String userName;
    private String password;
    private String checkTime;
    private String[] vnfmIds;
}
