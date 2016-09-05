/*
 * Copyright 2016, Huawei Technologies Co., Ltd.
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
package org.openo.nfvo.vimadapter.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberUtilTest {
	@Test
	public void testIsNumeric(){
		boolean result = NumberUtil.isNumeric("123");
		assertTrue(result);
	}
	
	@Test
	public void testIsNumeric1(){
		boolean result = NumberUtil.isNumeric("abc");
		assertFalse(result);
	}
	
	@Test
	public void testIsNumeric2(){
		boolean result = NumberUtil.isNumeric("123abc");
		assertFalse(result);
	}

}
