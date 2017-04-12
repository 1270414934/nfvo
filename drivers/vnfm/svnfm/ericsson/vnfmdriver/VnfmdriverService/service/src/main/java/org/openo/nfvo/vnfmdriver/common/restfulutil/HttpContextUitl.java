/*
 * Copyright (c) 2017 Ericsson (China) Communication Co. Ltd.
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

package org.openo.nfvo.vnfmdriver.common.restfulutil;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Feb 15, 2017
 */
public final class HttpContextUitl {

    private static final Logger LOG = LoggerFactory.getLogger(HttpContextUitl.class);

    private HttpContextUitl() {
    }

    /**
     * <br>
     *
     * @param vnfReq
     * @return
     * @since NFVO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> T extractJsonObject(HttpServletRequest vnfReq) {
        try {
            InputStream vnfInput = vnfReq.getInputStream();
            String vnfJsonStr = IOUtils.toString(vnfInput);
            JSONTokener vnfJsonTokener = new JSONTokener(vnfJsonStr);

            if(vnfJsonTokener.nextClean() == Character.codePointAt("{", 0)) {
                return (T)JSONObject.fromObject(vnfJsonStr);
            }

            vnfJsonTokener.back();

            if(vnfJsonTokener.nextClean() == Character.codePointAt("[", 0)) {
                return (T)JSONArray.fromObject(vnfJsonStr);
            }
        } catch(IOException e) {
            LOG.warn("fuc=[extractJsonObject], IOException!");
        } catch(JSONException e) {
            LOG.warn("fuc=[extractJsonObject], JSONException!");
        }

        return null;
    }
}
