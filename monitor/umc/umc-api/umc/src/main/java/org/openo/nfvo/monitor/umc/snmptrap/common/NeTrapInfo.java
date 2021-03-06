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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class NeTrapInfo {
	private String neId;
	private String neTypeId;
	private TrapInfo trapInfo;
	
	public boolean isTrapSupport(String trapOid)
	{
		String[] trapIds = trapInfo.getTrapId();
		if (trapIds == null || trapIds.length == 0)
		{
			return true;
		}
		for (String trapId : trapIds)
		{
			if (trapOid.indexOf(trapId) != -1)
			{
				return true;
			}
		}
		return false;
	}
}
