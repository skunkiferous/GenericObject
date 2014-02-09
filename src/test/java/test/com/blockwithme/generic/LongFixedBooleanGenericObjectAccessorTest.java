/*
 * Copyright (C) 2014 Sebastien Diot.
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
package test.com.blockwithme.generic;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.blockwithme.generic.IGenericObjectAccessor;
import com.blockwithme.generic.LongFixedBooleanGenericObjectAccessor;

/**
 * @author monster
 *
 */
public class LongFixedBooleanGenericObjectAccessorTest extends
        AbstractGenericObjectAccessorTest<Object[]> {

    private static LongFixedBooleanGenericObjectAccessor accessor;

    @BeforeClass
    public static void setup() {
        accessor = new LongFixedBooleanGenericObjectAccessor();
    }

    @AfterClass
    public static void deinit() {
        accessor = null;
    }

    @Override
    protected IGenericObjectAccessor<Object[]> getAccessor() {
        return accessor;
    }
}
