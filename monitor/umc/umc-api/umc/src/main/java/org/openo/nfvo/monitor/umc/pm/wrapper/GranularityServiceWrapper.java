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
package org.openo.nfvo.monitor.umc.pm.wrapper;

import org.openo.nfvo.monitor.umc.i18n.I18n;
import org.openo.nfvo.monitor.umc.pm.bean.GranularityBean;

public class GranularityServiceWrapper {
	public static GranularityBean[] getGranularitys(String resourceTypeId, String moTypeId, String language) {
	    I18n i18n = I18n.getInstance(language);
        return new GranularityBean[]{new GranularityBean(i18n.translate("i18n.pm.common.word.5minute"), 300)};
	}
}
