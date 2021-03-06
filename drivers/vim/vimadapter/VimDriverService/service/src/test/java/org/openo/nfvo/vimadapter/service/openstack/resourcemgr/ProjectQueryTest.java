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
package org.openo.nfvo.vimadapter.service.openstack.resourcemgr;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ProjectQueryTest {

    @Test
    public void initTest(){
        Map<String, String> conMap = new HashMap<>();
        conMap.put("queryId", "123");
        ProjectQuery query = new ProjectQuery(conMap);
        assertTrue(query != null);
    }
    @Test
    public void initTest2(){
        Map<String, String> conMap = new HashMap<>();
        conMap.put("queryId", "");
        ProjectQuery query = new ProjectQuery(conMap);
        assertTrue(query != null);
    }
    @Test
    public void initTest3(){
        Map<String, String> conMap = new HashMap<>();
        conMap.put("queryId", null);
        ProjectQuery query = new ProjectQuery(conMap);
        assertTrue(query != null);
    }

}
