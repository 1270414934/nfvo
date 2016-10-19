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
package org.openo.nfvo.monitor.umc.msb.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MsbRegisterBean {
	private String serviceName ;
	private String version = "v1";
	private String url;
	private String protocol = "REST";
	private String visualRange = "1";
	private List<ServiceNodeBean> nodes;
	
}
