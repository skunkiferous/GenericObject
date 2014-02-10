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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import com.blockwithme.generic.GenericObject;
import com.blockwithme.generic.IGenericObject;
import com.blockwithme.generic.LongFixedBooleanGenericObjectAccessor;

/**
 * Tests of GenericObject.
 *
 * TODO Add test for GenericObject-specific methods
 *
 * @author monster
 */
public class GenericObjectTest {
    private static final LongFixedBooleanGenericObjectAccessor accessor = new LongFixedBooleanGenericObjectAccessor();

    private static final Random random = new Random();

    private static final int expectedGetPrimitiveValuesSlotsAvailable = 8;

    private static final int expectedGetBooleanValuesSlotsAvailable = 64;

    private static final int expectedGetObjectValuesSlotsAvailable = 8;

    private static final int expectedGetPrimitiveValuesMaximumIndex = expectedGetPrimitiveValuesSlotsAvailable - 1;

    private static final int expectedGetBooleanValuesMaximumIndex = expectedGetBooleanValuesSlotsAvailable - 1;

    private static final int expectedGetObjectValuesMaximumIndex = expectedGetObjectValuesSlotsAvailable - 1;

    @Test
    public void testGetPrimitiveValuesMaximumIndex() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetPrimitiveValuesMaximumIndex",
                expectedGetPrimitiveValuesMaximumIndex,
                instance.getPrimitiveValuesMaximumIndex());
    }

    @Test
    public void testGetBooleanValuesMaximumIndex() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetBooleanValuesMaximumIndex",
                expectedGetBooleanValuesMaximumIndex,
                instance.getBooleanValuesMaximumIndex());
    }

    @Test
    public void testGetObjectValuesMaximumIndex() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetObjectValuesMaximumIndex",
                expectedGetObjectValuesMaximumIndex,
                instance.getObjectValuesMaximumIndex());
    }

    @Test
    public void testGetPrimitiveValuesSlotsAvailable() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetPrimitiveValuesSlotsAvailable",
                expectedGetPrimitiveValuesSlotsAvailable,
                instance.getPrimitiveValuesSlotsAvailable());
    }

    @Test
    public void testGetBooleanValuesSlotsAvailable() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetBooleanValuesSlotsAvailable",
                expectedGetBooleanValuesSlotsAvailable,
                instance.getBooleanValuesSlotsAvailable());
    }

    @Test
    public void testGetObjectValuesSlotsAvailable() {
        final IGenericObject<Object[]> instance = newInstance();
        assertEquals("GetObjectValuesSlotsAvailable",
                expectedGetObjectValuesSlotsAvailable,
                instance.getObjectValuesSlotsAvailable());
    }

    @Test
    public void testGetPrimitiveValuesReservedSize() {
        final IGenericObject<Object[]> instance = newInstance();
        // Must fail!
        boolean failed = false;
        try {
            instance.getPrimitiveValuesReservedSize();
        } catch (final RuntimeException e) {
            failed = true;
        }
        assertTrue("GetPrimitiveValuesReservedSize", failed);
    }

    @Test
    public void testGetBooleanValuesReservedSize() {
        final IGenericObject<Object[]> instance = newInstance();
        // Must fail!
        boolean failed = false;
        try {
            instance.getBooleanValuesReservedSize();
        } catch (final RuntimeException e) {
            failed = true;
        }
        assertTrue("GetBooleanValuesReservedSize", failed);
    }

    @Test
    public void testGetObjectValuesReservedSize() {
        final IGenericObject<Object[]> instance = newInstance();
        // Must fail!
        boolean failed = false;
        try {
            instance.getObjectValuesReservedSize();
        } catch (final RuntimeException e) {
            failed = true;
        }
        assertTrue("GetObjectValuesReservedSize", failed);
    }

    @Test
    public void testBooleanValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getBooleanValuesStartIndex();
        final int endIncl = instance.getBooleanValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("BooleanValue[" + i + "]", false,
                    instance.getBooleanValue(i));
        }

        final boolean[] values = new boolean[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = random.nextBoolean();
                instance.setBooleanValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("BooleanValue[" + i + "]", values[i],
                        instance.getBooleanValue(i));
            }
        }
    }

    @Test
    public void testByteValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("ByteValue[" + i + "]", (byte) 0,
                    instance.getByteValue(i));
        }

        final byte[] values = new byte[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = (byte) random.nextInt();
                instance.setByteValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("ByteValue[" + i + "]", values[i],
                        instance.getByteValue(i));
            }
        }
    }

    @Test
    public void testCharValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("CharValue[" + i + "]", (char) 0,
                    instance.getCharValue(i));
        }

        final char[] values = new char[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = (char) random.nextInt();
                instance.setCharValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("CharValue[" + i + "]", values[i],
                        instance.getCharValue(i));
            }
        }
    }

    @Test
    public void testShortValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("ShortValue[" + i + "]", (short) 0,
                    instance.getShortValue(i));
        }

        final short[] values = new short[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = (short) random.nextInt();
                instance.setShortValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("ShortValue[" + i + "]", values[i],
                        instance.getShortValue(i));
            }
        }
    }

    @Test
    public void testIntValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("IntValue[" + i + "]", 0, instance.getIntValue(i));
        }

        final int[] values = new int[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = random.nextInt();
                instance.setIntValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("IntValue[" + i + "]", values[i],
                        instance.getIntValue(i));
            }
        }
    }

    @Test
    public void testFloatValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("FloatValue[" + i + "]", 0.0f,
                    instance.getFloatValue(i), 0.0001f);
        }

        final float[] values = new float[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = Float.intBitsToFloat(random.nextInt());
                instance.setFloatValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("FloatValue[" + i + "]", values[i],
                        instance.getFloatValue(i), 0.0001f);
            }
        }
    }

    @Test
    public void testLongValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("LongValue[" + i + "]", 0L, instance.getLongValue(i));
        }

        final long[] values = new long[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = random.nextLong();
                instance.setLongValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("LongValue[" + i + "]", values[i],
                        instance.getLongValue(i));
            }
        }
    }

    @Test
    public void testDoubleValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getPrimitiveValuesStartIndex();
        final int endIncl = instance.getPrimitiveValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("DoubleValue[" + i + "]", 0.0,
                    instance.getDoubleValue(i), 0.0001);
        }

        final double[] values = new double[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = Double.longBitsToDouble(random.nextLong());
                instance.setDoubleValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("DoubleValue[" + i + "]", values[i],
                        instance.getDoubleValue(i), 0.0001);
            }
        }
    }

    @Test
    public void testObjectValue() {
        final IGenericObject<Object[]> instance = newInstance();
        final int startIncl = accessor.getObjectValuesStartIndex();
        final int endIncl = instance.getObjectValuesMaximumIndex();

        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("ObjectValue[" + i + "]", null,
                    instance.getObjectValue(i));
        }

        final Object[] values = new Object[endIncl + 1];
        for (int loop = 0; loop < 10; loop++) {
            for (int i = startIncl; i <= endIncl; i++) {
                values[i] = String.valueOf(random.nextInt());
                instance.setObjectValue(i, values[i]);
            }
            for (int i = startIncl; i <= endIncl; i++) {
                assertEquals("ObjectValue[" + i + "]", values[i],
                        instance.getObjectValue(i));
            }
        }
    }

    @Test
    public void testNewEmptyGenericObject() {
        final IGenericObject<Object[]> instance = newInstance();
        assertNotNull("instance", instance);
    }

    @Test
    public void testNewGenericObjectIntInt() {
        final IGenericObject<Object[]> instance = new GenericObject<Object[]>(
                accessor, 42, 42);
        assertNotNull("instance", instance);
        assertEquals("GetPrimitiveValuesSlotsAvailable", 64,
                instance.getPrimitiveValuesSlotsAvailable());
        assertEquals("GetObjectValuesSlotsAvailable", 64,
                instance.getObjectValuesSlotsAvailable());
    }

    @Test
    public void testNewGenericObjectIntIntInt() {
        // Must fail!
        boolean failed = false;
        try {
            accessor.newGenericObject(42, 42, 42);
        } catch (final Exception e) {
            failed = true;
        }
        assertTrue("newGenericObject(42, 42, 42)", failed);
    }

    @Test
    public void testResizePrimitiveValues() {
        IGenericObject<Object[]> instance = newInstance();
        instance = instance.resizePrimitiveValues(42);
        assertEquals("GetPrimitiveValuesSlotsAvailable", 64,
                instance.getPrimitiveValuesSlotsAvailable());
    }

    @Test
    public void testResizeBooleanValues() {
        final IGenericObject<Object[]> instance = newInstance();
        // Must fail!
        boolean failed = false;
        try {
            instance.resizeBooleanValues(42);
        } catch (final Exception e) {
            failed = true;
        }
        assertTrue("resizeBooleanValues(42)", failed);
    }

    @Test
    public void testResizeObjectValues() {
        IGenericObject<Object[]> instance = newInstance();
        instance = instance.resizeObjectValues(42);
        assertEquals("GetObjectValuesSlotsAvailable", 64,
                instance.getObjectValuesSlotsAvailable());
    }

    /**
     * Creates and returns a new instance.
     *
     * Neither Primitive, nor Boolean, nor Objects values should truly have
     * a size of 0.
     */
    private IGenericObject<Object[]> newInstance() {
        return new GenericObject<Object[]>(accessor);
    }
}
