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
package org.openo.nfvo.monitor.dac;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class DacAppConfig extends Configuration {
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "DAC Service";
    
    @NotEmpty
    private String msbAddress = "";

    
    private String umcServerPort = "8205";
    

	@JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
    
    @JsonProperty
    public String getMsbAddress() {
		return msbAddress;
	}

    @JsonProperty
	public void setMsbAddress(String msbAddress) {
		this.msbAddress = msbAddress;
	}
    
    @JsonProperty
    public String getUmcServerPort() {
		return umcServerPort;
	}
    @JsonProperty
	public void setUmcServerPort(String umcServerPort) {
		this.umcServerPort = umcServerPort;
	}
}
