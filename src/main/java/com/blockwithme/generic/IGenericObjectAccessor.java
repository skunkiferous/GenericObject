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
 * Common interface implemented by all generic object implementations.
 *
 * This should be the center for most of the API documentation.
 *
 * All implementations must provide an immutable, thread-safe, instance
 * implementing this interface. All implementations are expected to provide
 * exactly the same methods as defined here, but static, and prefixed with
 * "_", to make "direct usage" mostly plugable (just replace the import line).
 *
 * This API is not aimed at "wrapping a DB", and so we see no value in
 * using "long" to represent indexes, and allowing for extremely large
 * generic objects.
 *
 * Since the main purpose of this project is to produce a generic-object API
 * that does NOT require primitive values to be boxed, and unboxed, the API
 * assumes that objects and primitive values always have a different ID space
 * because efficiency requires them to be stored in separate arrays.
 *
 * Using an "accessor" object, instead of having each generic object instance
 * implement some interface, allows us in many case to be more efficient, as
 * it is possible in many case to store the whole data entirely using one
 * Object array and one primitive array, saving us the cost of one more
 * "wrapper" object for the arrays. It should be simple to create higher-level
 * API and stand-alone generic-object instances, built upon this API.
 *
 * While all implementations offer direct static access, switching
 * implementation would mean editing all files making use of the currently
 * chosen implementation. Using an accessor instead allows for easy replacement
 * of implementation. The price is (internally), in most case, one more
 * indirection and one more method invocation.
 *
 * Optimal packing means that the arrays are just big enough to hold the
 * data. This means you save space, and always get an IndexOutOfBounds
 * exception when using a *too big* index. One downside is that frequent
 * resizing will cause more garbage to be produced. Optimal packing is
 * normally required, to be able to query the requested size of an array,
 * size the actual size might be bigger then the requested size.
 *
 * This API does NOT require the implementation to keep track of the actual
 * type of primitive values. In other words, this API does NOT require the
 * implementation to check that primitive set and get use matching primitive
 * types. If a double value is set, and later read as a char, the return value
 * might be garbage, and the application is NOT expected to throw some
 * exception.
 *
 * Note that, for some type of values, even if the "start index" was 0, and
 * the "available slots" (which depends on the "reserved size") was
 * Integer.MAX_VALUE, then the "maximum index" would never be more then
 * (Integer.MAX_VALUE - 1). Otherwise, we would need to use "long" to represent
 * "available slots" and "reserved size".
 *
 * The API is expected to throw *some* RuntimeException if an invalid instance is
 * specified in a call (null, or wrong type). Most likely, null will result in
 * NullPointerException, and "wrong type" in a ClassCastException, but this
 * interface does not requires any specific RuntimeException, to allow the
 * implementation to use "whatever goes faster". The same applies to invalid
 * indexes, which will *likely* result in an IndexOutOfBoundsException.
 *
 * *Potential future extensions:*
 *
 * For implementations to provide a unified ID space, the complete value space
 * of integer would have to be used, by having either the primitive values, or
 * the object values, use negative IDs. Another alternative is to stick to
 * non-negative IDs, by reducing the maximum size, but having property IDs
 * alternate between primitive and object properties, for example, by using
 * even IDs for primitive values, and odd IDs for object values. In any case,
 * this kind of "compression" would require additional validation on every
 * access, and therefore slow down the code.
 *
 * We could add implementation for equals, hashcode and toString, but this
 * probably requires know the "reserved" size for values array, AND the
 * current type for primitive values, so it would not work for most
 * Implementations.
 *
 * We could compute and return the shallow memory footprint of one generic object.
 *
 * @author monster
 */
public interface IGenericObjectAccessor<STORAGE> {
    /**
     * Do boolean values use their own ID space, separate from other primitives?
     *
     * @return True if boolean values have an independent ID space
     */
    boolean isBooleanValuesIDSpaceIndependentFromPrimitive();

    /**
     * Do boolean values use a fixed ID space?
     *
     * @return True if boolean values have a fixed ID space
     */
    boolean isBooleanValuesIDSpaceFixed();

    /**
     * Do primitive values use their own ID space, separate from objects?
     *
     * HINT: Currently always returns true!
     *
     * @return True if primitive values have an independent ID space from objects
     */
    boolean isPrimitiveValuesIDSpaceIndependentFromObject();

    /**
     * If boolean values use their own ID space, AND their ID space has a
     * fixed size, then it will be returned. If they do NOT have a fixed size,
     * or do not have their own ID space, then -1 is returned.
     *
     * @return The fixed-size of the boolean value ID space, or -1.
     */
    int getBooleanValuesIDSpaceFixedSize();

    /**
     * Are immutable instances used? If yes, the instances can always be used
     * safely across threads. The downside is that all changes require the
     * creation of a new instance, and therefore are much more expensive,
     * and result in more garbage.
     *
     * @return true if the instances of STORAGE are immutable.
     */
    boolean isImmutableInstancesUsed();

    /**
     * Are thread-safe instances used? If yes, the instances can always be used
     * safely across threads. Normally, this returns the same thing as
     * isImmutableInstancesUsed().
     *
     * @return true if the instances of STORAGE are thread-safe.
     *
     * @see #isImmutableInstancesUsed()
     */
    boolean isThreadSafeInstancesUsed();

    /**
     * Returns true if long values require *two* primitive slots.
     *
     * This would happen if the internal primitive storage was based on 32-bit
     * values. Even smaller values would result in bad performance and are
     * therefore not supported.
     *
     * @return true if long values require *two* primitive slots.
     */
    boolean isLongUsingTwoPrimitiveSlots();

    /**
     * Returns true if double values require *two* primitive slots.
     *
     * This would happen if the internal primitive storage was based on 32-bit
     * values. Even smaller values would result in bad performance and are
     * therefore not supported.
     *
     * @return true if double values require *two* primitive slots.
     */
    boolean isDoubleUsingTwoPrimitiveSlots();

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns true, if this implementation use "optimal packing" for
     * primitive values.
     *
     * @return True, if this implementation use "optimal packing" for primitive values.
     */
    boolean isOptimalPackingUsedForPrimitiveValues();

    /**
     * Returns true, if this implementation use "optimal packing" for
     * boolean values.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @return True, if this implementation use "optimal packing" for boolean values.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    boolean isOptimalPackingUsedForBooleanValues()
            throws UnsupportedOperationException;

    /**
     * Returns true, if this implementation use "optimal packing" for
     * primitive values.
     *
     * @return True, if this implementation use "optimal packing" for object values.
     */
    boolean isOptimalPackingUsedForObjectValues();

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns the start index (usually either 0 or 1) of primitive values.
     *
     * @return The start index for primitive values.
     */
    int getPrimitiveValuesStartIndex();

    /**
     * Returns the start index (usually either 0 or 1) of boolean values.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @return The start index for boolean values.
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    int getBooleanValuesStartIndex() throws UnsupportedOperationException;

    /**
     * Returns the start index (usually either 0 or 1) of Object values.
     *
     * @return The start index for object values.
     */
    int getObjectValuesStartIndex();

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns the maximum number of primitive values.
     *
     * @return The maximum number of primitive values.
     */
    int getPrimitiveValuesMaximumCount();

    /**
     * Returns the maximum number of boolean values.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @return The maximum number of boolean values.
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    int getBooleanValuesMaximumCount() throws UnsupportedOperationException;

    /**
     * Returns the maximum number of Object values.
     *
     * @return The maximum number of object values.
     */
    int getObjectValuesMaximumCount();

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns an empty instance. If the instances are always immutable, then
     * this method will always return the same instance. Otherwise, it depends
     * on the specific implementation.
     *
     * @return a new empty generic object instance
     */
    STORAGE newEmptyGenericObject();

    /**
     * Returns a new instance with *at least* the required number of slots,
     * depending on the use of "optimal packing".
     *
     * This method will throw an IllegalArgumentException, if the required
     * slots exceed the maximum slot count.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do NOT have their own ID space, OR if they use
     * a fixed-size ID space.
     *
     * @param requiredPrimitiveSlots The minimum desired number of primitive slots.
     * @param requiredBooleanSlots The minimum desired number of boolean slots.
     * @param requiredObjectSlots The minimum desired number of object slots.
     * @return a new generic object instance with the desired minimum size
     *
     * @throws IllegalArgumentException If the required slots exceed the maximum slot count.
     * @throws UnsupportedOperationException If boolean values do NOT have their own ID space, OR if they use a fixed-size ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     * @see #getBooleanValuesIDSpaceFixedSize()
     */
    STORAGE newGenericObject(int requiredPrimitiveSlots,
            int requiredBooleanSlots, int requiredObjectSlots)
            throws UnsupportedOperationException;

    /**
     * Returns a new instance with *at least* the required number of slots,
     * depending on the use of "optimal packing".
     *
     * This method will throw an IllegalArgumentException, if the required
     * slots exceed the maximum slot count.
     *
     * If boolean values do have their own ID space, and it does not have a
     * fixed-size, then 0 is used as requiredBooleanSlots.
     *
     * @param requiredPrimitiveSlots The minimum desired number of primitive slots.
     * @param requiredObjectSlots The minimum desired number of object slots.
     * @return a new generic object instance with the desired minimum size
     *
     * @throws IllegalArgumentException If the required slots exceed the maximum slot count.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    STORAGE newGenericObject(int requiredPrimitiveSlots, int requiredObjectSlots);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns the current maximum index for primitive values.
     *
     * Note that it can be greater then the (requested size - 1), depending
     * on the use of "optimal packing".
     *
     * @param instance The generic object storage instance.
     * @return The current maximum index of primitive values *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     */
    int getPrimitiveValuesMaximumIndex(STORAGE instance);

    /**
     * Returns the current maximum index boolean values.
     *
     * Note that it can be greater then the (requested size - 1), depending
     * on the use of "optimal packing".
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @param instance The generic object storage instance.
     * @return The current maximum index of boolean values *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    int getBooleanValuesMaximumIndex(STORAGE instance)
            throws UnsupportedOperationException;

    /**
     * Returns the current maximum index Object values.
     *
     * Note that it can be greater then the (requested size - 1), depending
     * on the use of "optimal packing".
     *
     * @param instance The generic object storage instance.
     * @return The current maximum index of object values *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     */
    int getObjectValuesMaximumIndex(STORAGE instance);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns the actual number of primitive values "slots" available.
     *
     * @param instance The generic object storage instance.
     * @return The actual number of primitive values "slots" available *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     */
    int getPrimitiveValuesSlotsAvailable(STORAGE instance);

    /**
     * Returns the actual number of boolean values "slots" available.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @param instance The generic object storage instance.
     * @return The actual number of boolean values "slots" available *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     */
    int getBooleanValuesSlotsAvailable(STORAGE instance)
            throws UnsupportedOperationException;

    /**
     * Returns the actual number of Object values "slots" available.
     *
     * @param instance The generic object storage instance.
     * @return The actual number of object values "slots" available *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     */
    int getObjectValuesSlotsAvailable(STORAGE instance);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns the "reserved size" for primitive values, which can be less then
     * the number of "slots" available.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if "optimal packing" is NOT used.
     *
     * @param instance The generic object storage instance.
     * @return The "reserved size" for primitive values, *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If isOptimalPackingUsedForPrimitiveValues() returns false.
     *
     * @see #isOptimalPackingUsedForPrimitiveValues()
     */
    int getPrimitiveValuesReservedSize(STORAGE instance)
            throws UnsupportedOperationException;

    /**
     * Returns the "reserved size" for boolean values, which can be less then
     * the number of "slots" available.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if "optimal packing" is NOT used.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @param instance The generic object storage instance.
     * @return The "reserved size" for boolean values, *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     * @throws UnsupportedOperationException If isOptimalPackingUsedForBooleanValues() returns false.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     * @see #isOptimalPackingUsedForBooleanValues()
     */
    int getBooleanValuesReservedSize(STORAGE instance)
            throws UnsupportedOperationException;

    /**
     * Returns the "reserved size" for primitive values, which can be less then
     * the number of "slots" available.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if "optimal packing" is NOT used.
     *
     * @param instance The generic object storage instance.
     * @return The "reserved size" for object values, *for instance*.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If isOptimalPackingUsedForObjectValues() returns false.
     *
     * @see #isOptimalPackingUsedForObjectValues()
     */
    int getObjectValuesReservedSize(STORAGE instance)
            throws UnsupportedOperationException;

    //////////////////////////////////////////////////////////////////////////

    /**
     * Modifies the "reserved size" for primitive values. The implementation is
     * free to allocate more space then specified, if
     * isOptimalPackingUsedForPrimitiveValues() returns false. The
     * implementation is not required to shrink the storage, if
     * isOptimalPackingUsedForPrimitiveValues() returns false. If the storage
     * grows, the new slots are expected to have the respective default values.
     *
     * @param instance The generic object storage instance.
     * @param reservedSize The new "reserved size" for primitive values, *for instance*.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     *
     * @see #isOptimalPackingUsedForPrimitiveValues()
     */
    STORAGE resizePrimitiveValues(STORAGE instance, int reservedSize);

    /**
     * Modifies the "reserved size" for boolean values. The implementation is
     * free to allocate more space then specified, if
     * isOptimalPackingUsedForBooleanValues() returns false. The
     * implementation is not required to shrink the storage, if
     * isOptimalPackingUsedForBooleanValues() returns false. If the storage
     * grows, the new slots are expected to have the respective default values.
     *
     * Note that this method will throw UnsupportedOperationException,
     * if boolean values do not have their own ID space.
     *
     * @param instance The generic object storage instance.
     * @param reservedSize The new "reserved size" for primitive values, *for instance*.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws UnsupportedOperationException If boolean values do not have their own ID space.
     *
     * @see #isBooleanValuesIDSpaceIndependentFromPrimitive()
     * @see #isOptimalPackingUsedForBooleanValues()
     */
    STORAGE resizeBooleanValues(STORAGE instance, int reservedSize)
            throws UnsupportedOperationException;

    /**
     * Modifies the "reserved size" for Object values. The implementation is
     * free to allocate more space then specified, if
     * isOptimalPackingUsedForObjectValues() returns false. The
     * implementation is not required to shrink the storage, if
     * isOptimalPackingUsedForObjectValues() returns false. If the storage
     * grows, the new slots are expected to have the respective default values.
     *
     * @param instance The generic object storage instance.
     * @param reservedSize The new "reserved size" for primitive values, *for instance*.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     *
     * @see #isOptimalPackingUsedForObjectValues()
     */
    STORAGE resizeObjectValues(STORAGE instance, int reservedSize);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a boolean value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    boolean getBooleanValue(STORAGE instance, int index);

    /**
     * Sets a boolean value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setBooleanValue(STORAGE instance, int index, boolean value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a byte value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    byte getByteValue(STORAGE instance, int index);

    /**
     * Sets a byte value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setByteValue(STORAGE instance, int index, byte value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a char value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    char getCharValue(STORAGE instance, int index);

    /**
     * Sets a char value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setCharValue(STORAGE instance, int index, char value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a short value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    short getShortValue(STORAGE instance, int index);

    /**
     * Sets a short value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setShortValue(STORAGE instance, int index, short value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a int value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    int getIntValue(STORAGE instance, int index);

    /**
     * Sets a int value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setIntValue(STORAGE instance, int index, int value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a float value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    float getFloatValue(STORAGE instance, int index);

    /**
     * Sets a float value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setFloatValue(STORAGE instance, int index, float value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a long value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    long getLongValue(STORAGE instance, int index);

    /**
     * Sets a long value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setLongValue(STORAGE instance, int index, long value);

    //////////////////////////////////////////////////////////////////////////

    /**
     * Returns a double value at the given index.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @return The desired value.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    double getDoubleValue(STORAGE instance, int index);

    /**
     * Sets a double value at the given index.
     *
     * The return value of this call should always be used to replace the
     * instance passed as parameter.
     *
     * @param instance The generic object storage instance.
     * @param index The index of the desired value.
     * @param value The desired new value.
     * @return The new replacement, or modified, generic object storage instance.
     *
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if instance is invalid (null, or wrong type)
     * @throws RuntimeException The API is expected to throw *some* RuntimeException if index is invalid.
     */
    STORAGE setDoubleValue(STORAGE instance, int index, double value);
}
