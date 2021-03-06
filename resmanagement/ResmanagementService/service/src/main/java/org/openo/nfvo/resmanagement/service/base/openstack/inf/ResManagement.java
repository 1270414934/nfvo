/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.nfvo.resmanagement.service.base.openstack.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;

import net.sf.json.JSONObject;

/**
 * <br/>
 * <p>
 * </p>
 *
 * @author
 * @version NFVO 0.5 Aug 25, 2016
 */
public interface ResManagement {

    /**
     * <br/>
     *
     * @param jsonObject
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    int add(JSONObject jsonObject) throws ServiceException;

    /**
     * <br/>
     *
     * @param jsonObject
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    int update(JSONObject jsonObject) throws ServiceException;

    /*
     * key : parameter key value : parameter value
     */
    /**
     * <br/>
     *
     * @param id
     * @return
     * @throws ServiceException
     * @since NFVO 0.5
     */
    int delete(String id) throws ServiceException;

}
