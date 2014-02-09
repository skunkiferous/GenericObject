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

import org.junit.Test;

import com.blockwithme.generic.IGenericObjectAccessor;

/**
 * Base class for tests of IGenericObjectAccessor.
 *
 * The protected "expected values" can be adjusted in the subclass constructor.
 *
 * @author monster
 */
public abstract class AbstractGenericObjectAccessorTest<STORAGE> {
    protected boolean expectedIsBooleanValuesIDSpaceIndependentFromPrimitive = true;

    protected boolean expectedIsBooleanValuesIDSpaceFixed = true;

    protected boolean expectedIsPrimitiveValuesIDSpaceIndependentFromObject = true;

    protected int expectedGetBooleanValuesIDSpaceFixedSize = 64;

    protected boolean expectedIsImmutableInstancesUsed = false;

    protected boolean expectedIsThreadSafeInstancesUsed = false;

    protected boolean expectedIsOptimalPackingUsedForPrimitiveValues = false;

    protected boolean expectedIsOptimalPackingUsedForBooleanValues = false;

    protected boolean expectedIsOptimalPackingUsedForObjectValues = false;

    protected int expectedGetPrimitiveValuesStartIndex = 1;

    protected int expectedGetBooleanValuesStartIndex = 0;

    protected int expectedGetObjectValuesStartIndex = 1;

    protected int expectedGetPrimitiveValuesMaximumCount = Integer.MAX_VALUE - 1;

    protected int expectedGetBooleanValuesMaximumCount = 64;

    protected int expectedGetObjectValuesMaximumCount = Integer.MAX_VALUE - 1;

    protected int expectedGetPrimitiveValuesSlotsAvailable = 8;

    protected int expectedGetBooleanValuesSlotsAvailable = 64;

    protected int expectedGetObjectValuesSlotsAvailable = 8;

    protected int expectedGetPrimitiveValuesReservedSize = -1;

    protected int expectedGetBooleanValuesReservedSize = -1;

    protected int expectedGetObjectValuesReservedSize = -1;

    private int expectedGetPrimitiveValuesMaximumIndex() {
        return expectedGetPrimitiveValuesSlotsAvailable - 1;
    }

    private int expectedGetBooleanValuesMaximumIndex() {
        return expectedGetBooleanValuesSlotsAvailable - 1;
    }

    private int expectedGetObjectValuesMaximumIndex() {
        return expectedGetObjectValuesSlotsAvailable - 1;
    }

    @Test
    public void testIsBooleanValuesIDSpaceIndependentFromPrimitive() {
        assertEquals("IsBooleanValuesIDSpaceIndependentFromPrimitive",
                expectedIsBooleanValuesIDSpaceIndependentFromPrimitive,
                getAccessor().isBooleanValuesIDSpaceIndependentFromPrimitive());
    }

    @Test
    public void testIsBooleanValuesIDSpaceFixed() {
        assertEquals("IsBooleanValuesIDSpaceFixed",
                expectedIsBooleanValuesIDSpaceFixed, getAccessor()
                        .isBooleanValuesIDSpaceFixed());
    }

    @Test
    public void testIsPrimitiveValuesIDSpaceIndependentFromObject() {
        assertEquals("IsPrimitiveValuesIDSpaceIndependentFromObject",
                expectedIsPrimitiveValuesIDSpaceIndependentFromObject,
                getAccessor().isPrimitiveValuesIDSpaceIndependentFromObject());
    }

    @Test
    public void testGetBooleanValuesIDSpaceFixedSize() {
        assertEquals("GetBooleanValuesIDSpaceFixedSize",
                expectedGetBooleanValuesIDSpaceFixedSize, getAccessor()
                        .getBooleanValuesIDSpaceFixedSize());
    }

    @Test
    public void testIsImmutableInstancesUsed() {
        assertEquals("IsImmutableInstancesUsed",
                expectedIsImmutableInstancesUsed, getAccessor()
                        .isImmutableInstancesUsed());
    }

    @Test
    public void testIsThreadSafeInstancesUsed() {
        assertEquals("IsThreadSafeInstancesUsed",
                expectedIsThreadSafeInstancesUsed, getAccessor()
                        .isThreadSafeInstancesUsed());
    }

    @Test
    public void testIsOptimalPackingUsedForPrimitiveValues() {
        assertEquals("IsOptimalPackingUsedForPrimitiveValues",
                expectedIsOptimalPackingUsedForPrimitiveValues, getAccessor()
                        .isOptimalPackingUsedForPrimitiveValues());
    }

    @Test
    public void testIsOptimalPackingUsedForBooleanValues() {
        assertEquals("IsOptimalPackingUsedForBooleanValues",
                expectedIsOptimalPackingUsedForBooleanValues, getAccessor()
                        .isOptimalPackingUsedForBooleanValues());
    }

    @Test
    public void testIsOptimalPackingUsedForObjectValues() {
        assertEquals("IsOptimalPackingUsedForObjectValues",
                expectedIsOptimalPackingUsedForObjectValues, getAccessor()
                        .isOptimalPackingUsedForObjectValues());
    }

    @Test
    public void testGetPrimitiveValuesStartIndex() {
        assertEquals("GetPrimitiveValuesStartIndex",
                expectedGetPrimitiveValuesStartIndex, getAccessor()
                        .getPrimitiveValuesStartIndex());
    }

    @Test
    public void testGetBooleanValuesStartIndex() {
        assertEquals("GetBooleanValuesStartIndex",
                expectedGetBooleanValuesStartIndex, getAccessor()
                        .getBooleanValuesStartIndex());
    }

    @Test
    public void testGetObjectValuesStartIndex() {
        assertEquals("GetObjectValuesStartIndex",
                expectedGetObjectValuesStartIndex, getAccessor()
                        .getObjectValuesStartIndex());
    }

    @Test
    public void testGetPrimitiveValuesMaximumCount() {
        assertEquals("GetPrimitiveValuesMaximumCount",
                expectedGetPrimitiveValuesMaximumCount, getAccessor()
                        .getPrimitiveValuesMaximumCount());
    }

    @Test
    public void testGetBooleanValuesMaximumCount() {
        assertEquals("GetBooleanValuesMaximumCount",
                expectedGetBooleanValuesMaximumCount, getAccessor()
                        .getBooleanValuesMaximumCount());
    }

    @Test
    public void testGetObjectValuesMaximumCount() {
        assertEquals("GetObjectValuesMaximumCount",
                expectedGetObjectValuesMaximumCount, getAccessor()
                        .getObjectValuesMaximumCount());
    }

    @Test
    public void testGetPrimitiveValuesMaximumIndex() {
        final STORAGE instance = newInstance();
        assertEquals("GetPrimitiveValuesMaximumIndex",
                expectedGetPrimitiveValuesMaximumIndex(), getAccessor()
                        .getPrimitiveValuesMaximumIndex(instance));
    }

    @Test
    public void testGetBooleanValuesMaximumIndex() {
        final STORAGE instance = newInstance();
        assertEquals("GetBooleanValuesMaximumIndex",
                expectedGetBooleanValuesMaximumIndex(), getAccessor()
                        .getBooleanValuesMaximumIndex(instance));
    }

    @Test
    public void testGetObjectValuesMaximumIndex() {
        final STORAGE instance = newInstance();
        assertEquals("GetObjectValuesMaximumIndex",
                expectedGetObjectValuesMaximumIndex(), getAccessor()
                        .getObjectValuesMaximumIndex(instance));
    }

    @Test
    public void testGetPrimitiveValuesSlotsAvailable() {
        final STORAGE instance = newInstance();
        assertEquals("GetPrimitiveValuesSlotsAvailable",
                expectedGetPrimitiveValuesSlotsAvailable, getAccessor()
                        .getPrimitiveValuesSlotsAvailable(instance));
    }

    @Test
    public void testGetBooleanValuesSlotsAvailable() {
        final STORAGE instance = newInstance();
        assertEquals("GetBooleanValuesSlotsAvailable",
                expectedGetBooleanValuesSlotsAvailable, getAccessor()
                        .getBooleanValuesSlotsAvailable(instance));
    }

    @Test
    public void testGetObjectValuesSlotsAvailable() {
        final STORAGE instance = newInstance();
        assertEquals("GetObjectValuesSlotsAvailable",
                expectedGetObjectValuesSlotsAvailable, getAccessor()
                        .getObjectValuesSlotsAvailable(instance));
    }

    @Test
    public void testGetPrimitiveValuesReservedSize() {
        final STORAGE instance = newInstance();
        final int expected = expectedGetPrimitiveValuesReservedSize;
        if (expected == -1) {
            // Must fail!
            boolean failed = false;
            try {
                getAccessor().getPrimitiveValuesReservedSize(instance);
            } catch (final RuntimeException e) {
                failed = true;
            }
            assertTrue("GetPrimitiveValuesReservedSize", failed);
        } else {
            assertEquals("GetPrimitiveValuesReservedSize", expected,
                    getAccessor().getPrimitiveValuesReservedSize(instance));
        }
    }

    @Test
    public void testGetBooleanValuesReservedSize() {
        final STORAGE instance = newInstance();
        final int expected = expectedGetBooleanValuesReservedSize;
        if (expected == -1) {
            // Must fail!
            boolean failed = false;
            try {
                getAccessor().getBooleanValuesReservedSize(instance);
            } catch (final RuntimeException e) {
                failed = true;
            }
            assertTrue("GetBooleanValuesReservedSize", failed);
        } else {
            assertEquals("GetBooleanValuesReservedSize", expected,
                    getAccessor().getBooleanValuesReservedSize(instance));
        }
    }

    @Test
    public void testGetObjectValuesReservedSize() {
        final STORAGE instance = newInstance();
        final int expected = expectedGetObjectValuesReservedSize;
        if (expected == -1) {
            // Must fail!
            boolean failed = false;
            try {
                getAccessor().getObjectValuesReservedSize(instance);
            } catch (final RuntimeException e) {
                failed = true;
            }
            assertTrue("GetObjectValuesReservedSize", failed);
        } else {
            assertEquals("GetObjectValuesReservedSize", expected, getAccessor()
                    .getObjectValuesReservedSize(instance));
        }
    }

    @Test
    public void testBooleanValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        boolean value = true;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("BooleanValue[" + i + "]", false, getAccessor()
                    .getBooleanValue(instance, i));
            getAccessor().setBooleanValue(instance, i, value);
            assertEquals("BooleanValue[" + i + "]", value, getAccessor()
                    .getBooleanValue(instance, i));
            value = !value;
        }
    }

    @Test
    public void testByteValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        byte value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("ByteValue[" + i + "]", 0,
                    getAccessor().getByteValue(instance, i));
            getAccessor().setByteValue(instance, i, value);
            assertEquals("ByteValue[" + i + "]", value, getAccessor()
                    .getByteValue(instance, i));
            value++;
        }
    }

    @Test
    public void testCharValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        char value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("CharValue[" + i + "]", 0,
                    getAccessor().getCharValue(instance, i));
            getAccessor().setCharValue(instance, i, value);
            assertEquals("CharValue[" + i + "]", value, getAccessor()
                    .getCharValue(instance, i));
            value++;
        }
    }

    @Test
    public void testShortValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        short value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("ShortValue[" + i + "]", 0, getAccessor()
                    .getShortValue(instance, i));
            getAccessor().setShortValue(instance, i, value);
            assertEquals("ShortValue[" + i + "]", value, getAccessor()
                    .getShortValue(instance, i));
            value++;
        }
    }

    @Test
    public void testIntValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        int value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("IntValue[" + i + "]", 0,
                    getAccessor().getIntValue(instance, i));
            getAccessor().setIntValue(instance, i, value);
            assertEquals("IntValue[" + i + "]", value, getAccessor()
                    .getIntValue(instance, i));
            value++;
        }
    }

    @Test
    public void testFloatValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        float value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("FloatValue[" + i + "]", 0, getAccessor()
                    .getFloatValue(instance, i), 0.0001f);
            getAccessor().setFloatValue(instance, i, value);
            assertEquals("FloatValue[" + i + "]", value, getAccessor()
                    .getFloatValue(instance, i), 0.0001f);
            value++;
        }
    }

    @Test
    public void testLongValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        long value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("LongValue[" + i + "]", 0,
                    getAccessor().getLongValue(instance, i));
            getAccessor().setLongValue(instance, i, value);
            assertEquals("LongValue[" + i + "]", value, getAccessor()
                    .getLongValue(instance, i));
            value++;
        }
    }

    @Test
    public void testDoubleValue() {
        final STORAGE instance = newInstance();
        final int startIncl = getAccessor().getPrimitiveValuesStartIndex();
        final int endIncl = getAccessor().getPrimitiveValuesMaximumIndex(
                instance);
        double value = 42;
        for (int i = startIncl; i <= endIncl; i++) {
            assertEquals("DoubleValue[" + i + "]", 0, getAccessor()
                    .getDoubleValue(instance, i), 0.0001);
            getAccessor().setDoubleValue(instance, i, value);
            assertEquals("DoubleValue[" + i + "]", value, getAccessor()
                    .getDoubleValue(instance, i), 0.0001);
            value++;
        }
    }

    @Test
    public void testNewEmptyGenericObject() {
        final STORAGE instance = newInstance();
        assertNotNull("instance", instance);
    }

    @Test
    public void testNewGenericObjectIntInt() {
        final STORAGE instance = getAccessor().newGenericObject(42, 42);
        assertNotNull("instance", instance);
        if (getAccessor().isOptimalPackingUsedForPrimitiveValues()) {
            assertEquals("GetPrimitiveValuesSlotsAvailable", 42, getAccessor()
                    .getPrimitiveValuesSlotsAvailable(instance));
        } else {
            assertEquals("GetPrimitiveValuesSlotsAvailable", 64, getAccessor()
                    .getPrimitiveValuesSlotsAvailable(instance));
        }
        if (getAccessor().isOptimalPackingUsedForObjectValues()) {
            assertEquals("GetObjectValuesSlotsAvailable", 42, getAccessor()
                    .getObjectValuesSlotsAvailable(instance));
        } else {
            assertEquals("GetObjectValuesSlotsAvailable", 64, getAccessor()
                    .getObjectValuesSlotsAvailable(instance));
        }
    }

    @Test
    public void testNewGenericObjectIntIntInt() {
        if (!getAccessor().isBooleanValuesIDSpaceIndependentFromPrimitive()
                || getAccessor().isBooleanValuesIDSpaceFixed()) {
            // Must fail!
            boolean failed = false;
            try {
                getAccessor().newGenericObject(42, 42, 42);
            } catch (final Exception e) {
                failed = true;
            }
            assertTrue("newGenericObject(42, 42, 42)", failed);
        } else {
            final STORAGE instance = getAccessor().newGenericObject(42, 42, 42);
            assertNotNull("instance", instance);
            if (getAccessor().isOptimalPackingUsedForPrimitiveValues()) {
                assertEquals("GetPrimitiveValuesSlotsAvailable", 42,
                        getAccessor()
                                .getPrimitiveValuesSlotsAvailable(instance));
            } else {
                assertEquals("GetPrimitiveValuesSlotsAvailable", 64,
                        getAccessor()
                                .getPrimitiveValuesSlotsAvailable(instance));
            }
            if (getAccessor().isOptimalPackingUsedForBooleanValues()) {
                assertEquals("GetBooleanValuesSlotsAvailable", 42,
                        getAccessor().getBooleanValuesSlotsAvailable(instance));
            } else {
                assertEquals("GetBooleanValuesSlotsAvailable", 64,
                        getAccessor().getBooleanValuesSlotsAvailable(instance));
            }
            if (getAccessor().isOptimalPackingUsedForObjectValues()) {
                assertEquals("GetObjectValuesSlotsAvailable", 42, getAccessor()
                        .getObjectValuesSlotsAvailable(instance));
            } else {
                assertEquals("GetObjectValuesSlotsAvailable", 64, getAccessor()
                        .getObjectValuesSlotsAvailable(instance));
            }
        }
    }

    @Test
    public void testResizePrimitiveValues() {
        STORAGE instance = newInstance();
        instance = getAccessor().resizePrimitiveValues(instance, 42);
        if (getAccessor().isOptimalPackingUsedForPrimitiveValues()) {
            assertEquals("GetPrimitiveValuesSlotsAvailable", 42, getAccessor()
                    .getPrimitiveValuesSlotsAvailable(instance));
        } else {
            assertEquals("GetPrimitiveValuesSlotsAvailable", 64, getAccessor()
                    .getPrimitiveValuesSlotsAvailable(instance));
        }
    }

    @Test
    public void testResizeBooleanValues() {
        final STORAGE instance = newInstance();
        if (!getAccessor().isBooleanValuesIDSpaceIndependentFromPrimitive()
                || getAccessor().isBooleanValuesIDSpaceFixed()) {
            // Must fail!
            boolean failed = false;
            try {
                getAccessor().resizeBooleanValues(instance, 42);
            } catch (final Exception e) {
                failed = true;
            }
            assertTrue("resizeBooleanValues(instance, 42)", failed);
        } else {
            getAccessor().resizeBooleanValues(instance, 42);
            if (getAccessor().isOptimalPackingUsedForBooleanValues()) {
                assertEquals("GetBooleanValuesSlotsAvailable", 42,
                        getAccessor().getBooleanValuesSlotsAvailable(instance));
            } else {
                assertEquals("GetBooleanValuesSlotsAvailable", 64,
                        getAccessor().getBooleanValuesSlotsAvailable(instance));
            }
        }
    }

    @Test
    public void testResizeObjectValues() {
        STORAGE instance = newInstance();
        instance = getAccessor().resizeObjectValues(instance, 42);
        if (getAccessor().isOptimalPackingUsedForObjectValues()) {
            assertEquals("GetObjectValuesSlotsAvailable", 42, getAccessor()
                    .getObjectValuesSlotsAvailable(instance));
        } else {
            assertEquals("GetObjectValuesSlotsAvailable", 64, getAccessor()
                    .getObjectValuesSlotsAvailable(instance));
        }
    }

    /**
     * Creates and returns a new instance.
     *
     * Neither Primitive, nor Boolean, nor Objects values should truly have
     * a size of 0.
     */
    protected STORAGE newInstance() {
        return getAccessor().newEmptyGenericObject();
    }

    /** Must be implemented in subclass. */
    protected abstract IGenericObjectAccessor<STORAGE> getAccessor();
}
