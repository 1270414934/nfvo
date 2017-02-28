/*
 * Copyright (c) 2017, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openo.nfvo.jujuvnfmadapter.common;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class DownloadCsarManagerTest {
    DownloadCsarManager mgr;
    @Test
    public void test() {
        String url="";
        String filepath="";
        mgr.download(url);
        mgr.download(url, filepath);
        mgr.getRandomFileName();
    }
    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = DownloadCsarManager.class.getDeclaredConstructor();
        assertTrue("Constructor  private", Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
