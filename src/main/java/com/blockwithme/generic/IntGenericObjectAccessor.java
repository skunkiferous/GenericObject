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
package com.blockwithme.generic;

/**
 * IntGenericObjectAccessor implements IGenericObjectAccessor using ints.
 * Booleans are stored as normal primitives. Instances are not immutable/thread-safe.
 * Using float and doubles require a native call, and so goes slower.
 *
 * @see IGenericObjectAccessor for more documentation.
 *
 * @author monster
 */
public class IntGenericObjectAccessor implements
        IGenericObjectAccessor<Object[]> {
    /** Primitive Initial index. */
    private static final int PRIMITIVE_START_INDEX = 0;
    /** Primitive Minimum size */
    private static final int PRIMITIVE_MIN_SIZE = 8;
    /** Primitive Maximum size */
    private static final int PRIMITIVE_MAX_SIZE = Integer.MAX_VALUE;
    /** Object Initial index. */
    private static final int OBJECT_START_INDEX = 1;
    /** Object Minimum size */
    private static final int OBJECT_MIN_SIZE = 8 + 1 /* +1 for int[]) */;
    /** Object Maximum size */
    private static final int OBJECT_MAX_SIZE = Integer.MAX_VALUE - 1;

    //////////////////////////////////////////////////////////////////////////

    /**
     * Validates the primitive index.
     *
     * @param index The primitive index
     */
    private static void checkPrimitiveIndex(final int index) {
        if ((index < PRIMITIVE_START_INDEX) || (index >= PRIMITIVE_MAX_SIZE)) {
            throw new IllegalArgumentException("index: " + index
                    + " must be withing [" + PRIMITIVE_START_INDEX + ", "
                    + (PRIMITIVE_MAX_SIZE - 1) + "]");
        }
    }

    /**
     * Validates the Object index.
     *
     * @param index The Object index
     */
    private static void checkObjectIndex(final int index) {
        if ((index < OBJECT_START_INDEX) || (index >= OBJECT_MAX_SIZE)) {
            throw new IllegalArgumentException("index: " + index
                    + " must be withing [" + OBJECT_START_INDEX + ", "
                    + (OBJECT_MAX_SIZE - 1) + "]");
        }
    }

    /** Computes a "new size" for primitive values */
    private static int newPrimitiveSize(final int reservedSize,
            final String name) {
        if ((reservedSize < 0) || (reservedSize > PRIMITIVE_MAX_SIZE)) {
            throw new IllegalArgumentException(name + ": " + reservedSize);
        }
        final int newSize;
        if (reservedSize > (PRIMITIVE_MIN_SIZE - PRIMITIVE_START_INDEX)) {
            final int powerOfTwo = 32 - Integer
                    .numberOfLeadingZeros(reservedSize - 1);
            if (powerOfTwo == 31) {
                // Cannot represent (1 << 31) correctly
                newSize = Integer.MAX_VALUE;
            } else {
                newSize = (1 << powerOfTwo);
            }
        } else {
            newSize = PRIMITIVE_MIN_SIZE;
        }
        return newSize;
    }

    /** Computes a "new size" for Objects */
    private static int newObjectSize(final int reservedSize, final String name) {
        if ((reservedSize < 0) || (reservedSize > OBJECT_MAX_SIZE)) {
            throw new IllegalArgumentException(name + ": " + reservedSize);
        }
        final int newSize;
        if (reservedSize > (OBJECT_MIN_SIZE - OBJECT_START_INDEX)) {
            final int powerOfTwo = 32 - Integer
                    .numberOfLeadingZeros(reservedSize - 1);
            if (powerOfTwo == 31) {
                // Cannot represent (1 << 31) correctly
                newSize = Integer.MAX_VALUE;
            } else {
                newSize = (1 << powerOfTwo) + 1 /* For int[] */;
            }
        } else {
            newSize = OBJECT_MIN_SIZE;
        }
        return newSize;
    }

    /** Returns the primitive array */
    private static int[] getPrimitiveArray(final Object[] instance) {
        return (int[]) instance[0];
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    public static boolean _isBooleanValuesIDSpaceIndependentFromPrimitive() {
        return false;
    }

    /**
     * @see IGenericObjectAccessor#isBooleanValuesIDSpaceFixed()
     */
    public static boolean _isBooleanValuesIDSpaceFixed() {
        return false;
    }

    /**
     * @see IGenericObjectAccessor#isPrimitiveValuesIDSpaceIndependentFromObject()
     */
    public static boolean _isPrimitiveValuesIDSpaceIndependentFromObject() {
        return true;
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesIDSpaceFixedSize()
     */
    public static int _getBooleanValuesIDSpaceFixedSize() {
        return -1;
    }

    /**
     * @see IGenericObjectAccessor#isImmutableInstancesUsed()
     */
    public static boolean _isImmutableInstancesUsed() {
        return false;
    }

    /**
     * @see IGenericObjectAccessor#isThreadSafeInstancesUsed()
     */
    public static boolean _isThreadSafeInstancesUsed() {
        return false;
    }

    /**
     * @see IGenericObjectAccessor#isLongUsingTwoPrimitiveSlots()
     */
    public static boolean _isLongUsingTwoPrimitiveSlots() {
        return true;
    }

    /**
     * @see IGenericObjectAccessor#isDoubleUsingTwoPrimitiveSlots()
     */
    public static boolean _isDoubleUsingTwoPrimitiveSlots() {
        return true;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForPrimitiveValues()
     */
    public static boolean _isOptimalPackingUsedForPrimitiveValues() {
        return false;
    }

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForBooleanValues()
     */
    public static boolean _isOptimalPackingUsedForBooleanValues()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForObjectValues()
     */
    public static boolean _isOptimalPackingUsedForObjectValues() {
        return false;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesStartIndex()
     */
    public static int _getPrimitiveValuesStartIndex() {
        return PRIMITIVE_START_INDEX;
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesStartIndex()
     */
    public static int _getBooleanValuesStartIndex()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesStartIndex()
     */
    public static int _getObjectValuesStartIndex() {
        return OBJECT_START_INDEX;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesMaximumCount()
     */
    public static int _getPrimitiveValuesMaximumCount() {
        return PRIMITIVE_MAX_SIZE;
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesMaximumCount()
     */
    public static int _getBooleanValuesMaximumCount()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesMaximumCount()
     */
    public static int _getObjectValuesMaximumCount() {
        return OBJECT_MAX_SIZE;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#newEmptyGenericObject()
     */
    public static Object[] _newEmptyGenericObject() {
        final Object[] result = new Object[OBJECT_MIN_SIZE];
        result[0] = new int[PRIMITIVE_MIN_SIZE];
        return result;
    }

    /**
     * @see IGenericObjectAccessor#newGenericObject(int,int,int)
     */
    public static Object[] _newGenericObject(final int requiredPrimitiveSlots,
            final int requiredBooleanSlots, final int requiredObjectSlots)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#newGenericObject(int,int)
     */
    public static Object[] _newGenericObject(final int requiredPrimitiveSlots,
            final int requiredObjectSlots) {
        final int newPrimitiveSize = newPrimitiveSize(requiredPrimitiveSlots,
                "requiredPrimitiveSlots");
        final int newObjectSize = newObjectSize(requiredObjectSlots,
                "requiredObjectSlots");
        final Object[] result = new Object[newObjectSize];
        result[0] = new int[newPrimitiveSize];
        return result;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesMaximumIndex(Object)
     */
    public static int _getPrimitiveValuesMaximumIndex(final Object[] instance) {
        return getPrimitiveArray(instance).length - (1 + PRIMITIVE_START_INDEX);
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesMaximumIndex(Object)
     */
    public static int _getBooleanValuesMaximumIndex(final Object[] instance)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesMaximumIndex(Object)
     */
    public static int _getObjectValuesMaximumIndex(final Object[] instance) {
        return instance.length - (1 + OBJECT_START_INDEX);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesSlotsAvailable(Object)
     */
    public static int _getPrimitiveValuesSlotsAvailable(final Object[] instance) {
        return getPrimitiveArray(instance).length - PRIMITIVE_START_INDEX;
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesSlotsAvailable(Object)
     */
    public static int _getBooleanValuesSlotsAvailable(final Object[] instance)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesSlotsAvailable(Object)
     */
    public static int _getObjectValuesSlotsAvailable(final Object[] instance) {
        return instance.length - OBJECT_START_INDEX;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesReservedSize(Object)
     */
    public static int _getPrimitiveValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Reserved size is not available");
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesReservedSize(Object)
     */
    public static int _getBooleanValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Reserved size is not available");
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesReservedSize(Object)
     */
    public static int _getObjectValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Reserved size is not available");
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#resizePrimitiveValues(Object, int)
     */
    public static Object[] _resizePrimitiveValues(final Object[] instance,
            final int reservedSize) {
        final int[] oldData = getPrimitiveArray(instance);
        final int oldSize = oldData.length;
        final int newSize = newPrimitiveSize(reservedSize, "reservedSize");
        if (oldSize < newSize) {
            final int[] newData = new int[newSize];
            System.arraycopy(oldData, 0, newData, 0, oldSize);
            instance[0] = newData;
        }
        return instance;
    }

    /**
     * @see IGenericObjectAccessor#resizeBooleanValues(Object, int)
     */
    public static Object[] _resizeBooleanValues(final Object[] instance,
            final int reservedSize) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Boolean do not have their own ID space");
    }

    /**
     * @see IGenericObjectAccessor#resizeObjectValues(Object, int)
     */
    public static Object[] _resizeObjectValues(final Object[] instance,
            final int reservedSize) {
        final Object[] oldData = instance;
        final int oldSize = oldData.length;
        final int newSize = newObjectSize(reservedSize, "reservedSize");
        if (oldSize < newSize) {
            final Object[] newData = new Object[newSize];
            System.arraycopy(oldData, 0, newData, 0, oldSize);
            return newData;
        }
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getBooleanValue(Object, int)
     */
    public static boolean _getBooleanValue(final Object[] instance,
            final int index) {
        checkPrimitiveIndex(index);
        return getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] != 0;
    }

    /**
     * @see IGenericObjectAccessor#setBooleanValue(Object, int, boolean)
     */
    public static Object[] _setBooleanValue(final Object[] instance,
            final int index, final boolean value) {
        checkPrimitiveIndex(index);
        getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] = value ? 1
                : 0;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getByteValue(Object, int)
     */
    public static byte _getByteValue(final Object[] instance, final int index) {
        checkPrimitiveIndex(index);
        return (byte) getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX];
    }

    /**
     * @see IGenericObjectAccessor#setByteValue(Object, int, byte)
     */
    public static Object[] _setByteValue(final Object[] instance,
            final int index, final byte value) {
        checkPrimitiveIndex(index);
        getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] = value;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getCharValue(Object, int)
     */
    public static char _getCharValue(final Object[] instance, final int index) {
        checkPrimitiveIndex(index);
        return (char) getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX];
    }

    /**
     * @see IGenericObjectAccessor#setCharValue(Object, int, char)
     */
    public static Object[] _setCharValue(final Object[] instance,
            final int index, final char value) {
        checkPrimitiveIndex(index);
        getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] = value;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getShortValue(Object, int)
     */
    public static short _getShortValue(final Object[] instance, final int index) {
        checkPrimitiveIndex(index);
        return (short) getPrimitiveArray(instance)[index
                + PRIMITIVE_START_INDEX];
    }

    /**
     * @see IGenericObjectAccessor#setShortValue(Object, int, short)
     */
    public static Object[] _setShortValue(final Object[] instance,
            final int index, final short value) {
        checkPrimitiveIndex(index);
        getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] = value;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getIntValue(Object, int)
     */
    public static int _getIntValue(final Object[] instance, final int index) {
        checkPrimitiveIndex(index);
        return getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX];
    }

    /**
     * @see IGenericObjectAccessor#setIntValue(Object, int, int)
     */
    public static Object[] _setIntValue(final Object[] instance,
            final int index, final int value) {
        checkPrimitiveIndex(index);
        getPrimitiveArray(instance)[index + PRIMITIVE_START_INDEX] = value;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getFloatValue(Object, int)
     */
    public static float _getFloatValue(final Object[] instance, final int index) {
        return Float.intBitsToFloat(_getIntValue(instance, index));
    }

    /**
     * @see IGenericObjectAccessor#setFloatValue(Object, int, float)
     */
    public static Object[] _setFloatValue(final Object[] instance,
            final int index, final float value) {
        return _setIntValue(instance, index, Float.floatToRawIntBits(value));
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getLongValue(Object, int)
     */
    public static long _getLongValue(final Object[] instance, final int index) {
        checkPrimitiveIndex(index);
        checkPrimitiveIndex(index + 1);
        final int[] array = getPrimitiveArray(instance);
        final int low = array[index + PRIMITIVE_START_INDEX];
        final int high = array[index + PRIMITIVE_START_INDEX + 1];
        return ((long) high << 32) | (low & 0xFFFFFFFFL);
    }

    /**
     * @see IGenericObjectAccessor#setLongValue(Object, int, long)
     */
    public static Object[] _setLongValue(final Object[] instance,
            final int index, final long value) {
        checkPrimitiveIndex(index);
        checkPrimitiveIndex(index + 1);
        final int[] array = getPrimitiveArray(instance);
        final int low = (int) value;
        final int high = (int) (value >> 32);
        array[index + PRIMITIVE_START_INDEX] = low;
        array[index + PRIMITIVE_START_INDEX + 1] = high;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getDoubleValue(Object, int)
     */
    public static double _getDoubleValue(final Object[] instance,
            final int index) {
        return Double.longBitsToDouble(_getLongValue(instance, index));
    }

    /**
     * @see IGenericObjectAccessor#setDoubleValue(Object, int, double)
     */
    public static Object[] _setDoubleValue(final Object[] instance,
            final int index, final double value) {
        return _setLongValue(instance, index, Double.doubleToRawLongBits(value));
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getObjectValue(Object, int)
     */
    public static Object _getObjectValue(final Object[] instance,
            final int index) {
        checkObjectIndex(index);
        return instance[index + OBJECT_START_INDEX];
    }

    /**
     * @see IGenericObjectAccessor#setObjectValue(Object, int, Object)
     */
    public static Object[] _setObjectValue(final Object[] instance,
            final int index, final Object value) {
        checkObjectIndex(index);
        instance[index + OBJECT_START_INDEX] = value;
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    @Override
    public final boolean isBooleanValuesIDSpaceIndependentFromPrimitive() {
        return _isBooleanValuesIDSpaceIndependentFromPrimitive();
    }

    /**
     * @see IGenericObjectAccessor#isBooleanValuesIDSpaceFixed()
     */
    @Override
    public final boolean isBooleanValuesIDSpaceFixed() {
        return _isBooleanValuesIDSpaceFixed();
    }

    /**
     * @see IGenericObjectAccessor#isPrimitiveValuesIDSpaceIndependentFromObject()
     */
    @Override
    public final boolean isPrimitiveValuesIDSpaceIndependentFromObject() {
        return _isPrimitiveValuesIDSpaceIndependentFromObject();
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesIDSpaceFixedSize()
     */
    @Override
    public final int getBooleanValuesIDSpaceFixedSize() {
        return _getBooleanValuesIDSpaceFixedSize();
    }

    /**
     * @see IGenericObjectAccessor#isImmutableInstancesUsed()
     */
    @Override
    public final boolean isImmutableInstancesUsed() {
        return _isImmutableInstancesUsed();
    }

    /**
     * @see IGenericObjectAccessor#isThreadSafeInstancesUsed()
     */
    @Override
    public final boolean isThreadSafeInstancesUsed() {
        return _isThreadSafeInstancesUsed();
    }

    /**
     * @see IGenericObjectAccessor#isLongUsingTwoPrimitiveSlots()
     */
    @Override
    public final boolean isLongUsingTwoPrimitiveSlots() {
        return _isLongUsingTwoPrimitiveSlots();
    }

    /**
     * @see IGenericObjectAccessor#isDoubleUsingTwoPrimitiveSlots()
     */
    @Override
    public final boolean isDoubleUsingTwoPrimitiveSlots() {
        return _isDoubleUsingTwoPrimitiveSlots();
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForPrimitiveValues()
     */
    @Override
    public final boolean isOptimalPackingUsedForPrimitiveValues() {
        return _isOptimalPackingUsedForPrimitiveValues();
    }

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForBooleanValues()
     */
    @Override
    public final boolean isOptimalPackingUsedForBooleanValues()
            throws UnsupportedOperationException {
        return _isOptimalPackingUsedForBooleanValues();
    }

    /**
     * @see IGenericObjectAccessor#isOptimalPackingUsedForObjectValues()
     */
    @Override
    public final boolean isOptimalPackingUsedForObjectValues() {
        return _isOptimalPackingUsedForObjectValues();
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesStartIndex()
     */
    @Override
    public final int getPrimitiveValuesStartIndex() {
        return _getPrimitiveValuesStartIndex();
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesStartIndex()
     */
    @Override
    public final int getBooleanValuesStartIndex()
            throws UnsupportedOperationException {
        return _getBooleanValuesStartIndex();
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesStartIndex()
     */
    @Override
    public final int getObjectValuesStartIndex() {
        return _getObjectValuesStartIndex();
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesMaximumCount()
     */
    @Override
    public final int getPrimitiveValuesMaximumCount() {
        return _getPrimitiveValuesMaximumCount();
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesMaximumCount()
     */
    @Override
    public final int getBooleanValuesMaximumCount()
            throws UnsupportedOperationException {
        return _getBooleanValuesMaximumCount();
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesMaximumCount()
     */
    @Override
    public final int getObjectValuesMaximumCount() {
        return _getObjectValuesMaximumCount();
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#newEmptyGenericObject()
     */
    @Override
    public final Object[] newEmptyGenericObject() {
        return _newEmptyGenericObject();
    }

    /**
     * @see IGenericObjectAccessor#newGenericObject(int,int,int)
     */
    @Override
    public final Object[] newGenericObject(final int requiredPrimitiveSlots,
            final int requiredBooleanSlots, final int requiredObjectSlots)
            throws UnsupportedOperationException {
        return _newGenericObject(requiredPrimitiveSlots, requiredBooleanSlots,
                requiredObjectSlots);
    }

    /**
     * @see IGenericObjectAccessor#newGenericObject(int,int)
     */
    @Override
    public final Object[] newGenericObject(final int requiredPrimitiveSlots,
            final int requiredObjectSlots) {
        return _newGenericObject(requiredPrimitiveSlots, requiredObjectSlots);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesMaximumIndex(Object)
     */
    @Override
    public final int getPrimitiveValuesMaximumIndex(final Object[] instance) {
        return _getPrimitiveValuesMaximumIndex(instance);
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesMaximumIndex(Object)
     */
    @Override
    public final int getBooleanValuesMaximumIndex(final Object[] instance)
            throws UnsupportedOperationException {
        return _getBooleanValuesMaximumIndex(instance);
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesMaximumIndex(Object)
     */
    @Override
    public final int getObjectValuesMaximumIndex(final Object[] instance) {
        return _getObjectValuesMaximumIndex(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesSlotsAvailable(Object)
     */
    @Override
    public final int getPrimitiveValuesSlotsAvailable(final Object[] instance) {
        return _getPrimitiveValuesSlotsAvailable(instance);
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesSlotsAvailable(Object)
     */
    @Override
    public final int getBooleanValuesSlotsAvailable(final Object[] instance)
            throws UnsupportedOperationException {
        return _getBooleanValuesSlotsAvailable(instance);
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesSlotsAvailable(Object)
     */
    @Override
    public final int getObjectValuesSlotsAvailable(final Object[] instance) {
        return _getObjectValuesSlotsAvailable(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesReservedSize(Object)
     */
    @Override
    public final int getPrimitiveValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        return _getPrimitiveValuesReservedSize(instance);
    }

    /**
     * @see IGenericObjectAccessor#getBooleanValuesReservedSize(Object)
     */
    @Override
    public final int getBooleanValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        return _getBooleanValuesReservedSize(instance);
    }

    /**
     * @see IGenericObjectAccessor#getObjectValuesReservedSize(Object)
     */
    @Override
    public final int getObjectValuesReservedSize(final Object[] instance)
            throws UnsupportedOperationException {
        return _getObjectValuesReservedSize(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#resizePrimitiveValues(Object, int)
     */
    @Override
    public final Object[] resizePrimitiveValues(final Object[] instance,
            final int reservedSize) {
        return _resizePrimitiveValues(instance, reservedSize);
    }

    /**
     * @see IGenericObjectAccessor#resizeBooleanValues(Object, int)
     */
    @Override
    public final Object[] resizeBooleanValues(final Object[] instance,
            final int reservedSize) throws UnsupportedOperationException {
        return _resizeBooleanValues(instance, reservedSize);
    }

    /**
     * @see IGenericObjectAccessor#resizeObjectValues(Object, int)
     */
    @Override
    public final Object[] resizeObjectValues(final Object[] instance,
            final int reservedSize) {
        return _resizeObjectValues(instance, reservedSize);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getBooleanValue(Object, int)
     */
    @Override
    public final boolean getBooleanValue(final Object[] instance,
            final int index) {
        return _getBooleanValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setBooleanValue(Object, int, boolean)
     */
    @Override
    public final Object[] setBooleanValue(final Object[] instance,
            final int index, final boolean value) {
        return _setBooleanValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getByteValue(Object, int)
     */
    @Override
    public final byte getByteValue(final Object[] instance, final int index) {
        return _getByteValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setByteValue(Object, int, byte)
     */
    @Override
    public final Object[] setByteValue(final Object[] instance,
            final int index, final byte value) {
        return _setByteValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getCharValue(Object, int)
     */
    @Override
    public final char getCharValue(final Object[] instance, final int index) {
        return _getCharValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setCharValue(Object, int, char)
     */
    @Override
    public final Object[] setCharValue(final Object[] instance,
            final int index, final char value) {
        return _setCharValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getShortValue(Object, int)
     */
    @Override
    public final short getShortValue(final Object[] instance, final int index) {
        return _getShortValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setShortValue(Object, int, short)
     */
    @Override
    public final Object[] setShortValue(final Object[] instance,
            final int index, final short value) {
        return _setShortValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getIntValue(Object, int)
     */
    @Override
    public final int getIntValue(final Object[] instance, final int index) {
        return _getIntValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setIntValue(Object, int, int)
     */
    @Override
    public final Object[] setIntValue(final Object[] instance, final int index,
            final int value) {
        return _setIntValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getFloatValue(Object, int)
     */
    @Override
    public final float getFloatValue(final Object[] instance, final int index) {
        return _getFloatValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setFloatValue(Object, int, float)
     */
    @Override
    public final Object[] setFloatValue(final Object[] instance,
            final int index, final float value) {
        return _setFloatValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getLongValue(Object, int)
     */
    @Override
    public final long getLongValue(final Object[] instance, final int index) {
        return _getLongValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setLongValue(Object, int, long)
     */
    @Override
    public final Object[] setLongValue(final Object[] instance,
            final int index, final long value) {
        return _setLongValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getDoubleValue(Object, int)
     */
    @Override
    public final double getDoubleValue(final Object[] instance, final int index) {
        return _getDoubleValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setDoubleValue(Object, int, double)
     */
    @Override
    public final Object[] setDoubleValue(final Object[] instance,
            final int index, final double value) {
        return _setDoubleValue(instance, index, value);
    }

    //////////////////////////////////////////////////////////////////////////

    /**
     * @see IGenericObjectAccessor#getObjectValue(Object, int)
     */
    @Override
    public final Object getObjectValue(final Object[] instance, final int index) {
        return _getObjectValue(instance, index);
    }

    /**
     * @see IGenericObjectAccessor#setObjectValue(Object, int, Object)
     */
    @Override
    public final Object[] setObjectValue(final Object[] instance,
            final int index, final Object value) {
        return _setObjectValue(instance, index, value);
    }
}
