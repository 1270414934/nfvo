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
package org.openo.nfvo.monitor.umc.drill.wrapper.common.util;

/**
 *       Service utils for obtaining the root path of the back services
 */
public class ServiceAccessUtil {

    /**
     * get the root path of the Roc Service
     *
     */
    public static String getRocApiRootDomain() {
        return "/openoapi/roc/v1";
    }

}
