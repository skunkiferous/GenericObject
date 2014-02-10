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

import java.util.Objects;

/**
 * A generic object instance, wrapping a IGenericObjectAccessor and
 * a storage instance together, for easier use.
 *
 * Note that currently, GenericObject neither override equals, hashcode,
 * nor toString, since the underlying generic object implementations do
 * not currently provide that functionality.
 *
 * @see IGenericObjectAccessor for more information.
 *
 * TODO Create a type-safe variant of this class (record type on set, and check on get).
 *
 * @author monster
 */
public class GenericObject<STORAGE> implements IGenericObject<STORAGE> {
    /** The generic object accessor implementation. */
    private final IGenericObjectAccessor<STORAGE> accessor;

    /** The generic object storage instance. */
    private STORAGE instance;

    /**
     * Creates a new empty generic object.
     *
     * @param accessor The generic object accessor implementation.
     *
     * @throws NullPointerException if accessor is null
     */
    public GenericObject(final IGenericObjectAccessor<STORAGE> accessor) {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        instance = accessor.newEmptyGenericObject();
    }

    /**
     * Creates a new generic object, with the given storage.
     *
     * @param accessor The generic object accessor implementation.
     *
     * @throws NullPointerException if accessor is null
     * @throws NullPointerException if instance is null
     */
    public GenericObject(final IGenericObjectAccessor<STORAGE> accessor,
            final STORAGE instance) {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        this.instance = Objects.requireNonNull(instance, "instance");
    }

    /**
     * Creates a new generic object with *at least* the required number of slots,
     * depending on the use of "optimal packing".
     *
     * @param accessor The generic object accessor implementation.
     * @param requiredPrimitiveSlots The minimum desired number of primitive slots.
     * @param requiredBooleanSlots The minimum desired number of boolean slots.
     * @param requiredObjectSlots The minimum desired number of object slots.
     *
     * @throws NullPointerException if accessor is null
     *
     * @see IGenericObjectAccessor#newGenericObject(int,int,int)
     */
    public GenericObject(final IGenericObjectAccessor<STORAGE> accessor,
            final int requiredPrimitiveSlots, final int requiredBooleanSlots,
            final int requiredObjectSlots) throws UnsupportedOperationException {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        instance = accessor.newGenericObject(requiredPrimitiveSlots,
                requiredBooleanSlots, requiredObjectSlots);
    }

    /**
     * Creates a new generic object with *at least* the required number of slots,
     * depending on the use of "optimal packing".
     *
     * @param accessor The generic object accessor implementation.
     * @param requiredPrimitiveSlots The minimum desired number of primitive slots.
     * @param requiredObjectSlots The minimum desired number of object slots.
     *
     * @throws NullPointerException if accessor is null
     *
     * @see IGenericObjectAccessor#newGenericObject(int,int)
     */
    public GenericObject(final IGenericObjectAccessor<STORAGE> accessor,
            final int requiredPrimitiveSlots, final int requiredObjectSlots)
            throws UnsupportedOperationException {
        this.accessor = Objects.requireNonNull(accessor, "accessor");
        instance = accessor.newGenericObject(requiredPrimitiveSlots,
                requiredObjectSlots);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getGenericObjectAccessor()
     */
    @Override
    public final IGenericObjectAccessor<STORAGE> getGenericObjectAccessor() {
        return accessor;
    }

    /* (non-Javadoc)
     * @see IGenericObject#getGenericObjectStorage()
     */
    @Override
    public final STORAGE getGenericObjectStorage() {
        return instance;
    }

    /* (non-Javadoc)
     * @see IGenericObject#setGenericObjectStorage(STORAGE)
     */
    @Override
    public final GenericObject<STORAGE> setGenericObjectStorage(
            final STORAGE instance) {
        this.instance = Objects.requireNonNull(instance, "instance");
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getPrimitiveValuesMaximumIndex()
     */
    @Override
    public final int getPrimitiveValuesMaximumIndex() {
        return accessor.getPrimitiveValuesMaximumIndex(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getBooleanValuesMaximumIndex()
     */
    @Override
    public final int getBooleanValuesMaximumIndex()
            throws UnsupportedOperationException {
        return accessor.getBooleanValuesMaximumIndex(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getObjectValuesMaximumIndex()
     */
    @Override
    public final int getObjectValuesMaximumIndex() {
        return accessor.getObjectValuesMaximumIndex(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getPrimitiveValuesSlotsAvailable()
     */
    @Override
    public final int getPrimitiveValuesSlotsAvailable() {
        return accessor.getPrimitiveValuesSlotsAvailable(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getBooleanValuesSlotsAvailable()
     */
    @Override
    public final int getBooleanValuesSlotsAvailable()
            throws UnsupportedOperationException {
        return accessor.getBooleanValuesSlotsAvailable(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getObjectValuesSlotsAvailable()
     */
    @Override
    public final int getObjectValuesSlotsAvailable() {
        return accessor.getObjectValuesSlotsAvailable(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getPrimitiveValuesReservedSize()
     */
    @Override
    public final int getPrimitiveValuesReservedSize()
            throws UnsupportedOperationException {
        return accessor.getPrimitiveValuesReservedSize(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getBooleanValuesReservedSize()
     */
    @Override
    public final int getBooleanValuesReservedSize()
            throws UnsupportedOperationException {
        return accessor.getBooleanValuesReservedSize(instance);
    }

    /* (non-Javadoc)
     * @see IGenericObject#getObjectValuesReservedSize()
     */
    @Override
    public final int getObjectValuesReservedSize()
            throws UnsupportedOperationException {
        return accessor.getObjectValuesReservedSize(instance);
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#resizePrimitiveValues(int)
     */
    @Override
    public final GenericObject<STORAGE> resizePrimitiveValues(
            final int reservedSize) {
        instance = accessor.resizePrimitiveValues(instance, reservedSize);
        return this;
    }

    /* (non-Javadoc)
     * @see IGenericObject#resizeBooleanValues(int)
     */
    @Override
    public final GenericObject<STORAGE> resizeBooleanValues(
            final int reservedSize) throws UnsupportedOperationException {
        instance = accessor.resizeBooleanValues(instance, reservedSize);
        return this;
    }

    /* (non-Javadoc)
     * @see IGenericObject#resizeObjectValues(int)
     */
    @Override
    public final GenericObject<STORAGE> resizeObjectValues(
            final int reservedSize) {
        instance = accessor.resizeObjectValues(instance, reservedSize);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getBooleanValue(int)
     */
    @Override
    public final boolean getBooleanValue(final int index) {
        return accessor.getBooleanValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setBooleanValue(int, boolean)
     */
    @Override
    public final GenericObject<STORAGE> setBooleanValue(final int index,
            final boolean value) {
        instance = accessor.setBooleanValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getByteValue(int)
     */
    @Override
    public final byte getByteValue(final int index) {
        return accessor.getByteValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setByteValue(int, byte)
     */
    @Override
    public final GenericObject<STORAGE> setByteValue(final int index,
            final byte value) {
        instance = accessor.setByteValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getCharValue(int)
     */
    @Override
    public final char getCharValue(final int index) {
        return accessor.getCharValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setCharValue(int, char)
     */
    @Override
    public final GenericObject<STORAGE> setCharValue(final int index,
            final char value) {
        instance = accessor.setCharValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getShortValue(int)
     */
    @Override
    public final short getShortValue(final int index) {
        return accessor.getShortValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setShortValue(int, short)
     */
    @Override
    public final GenericObject<STORAGE> setShortValue(final int index,
            final short value) {
        instance = accessor.setShortValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getIntValue(int)
     */
    @Override
    public final int getIntValue(final int index) {
        return accessor.getIntValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setIntValue(int, int)
     */
    @Override
    public final GenericObject<STORAGE> setIntValue(final int index,
            final int value) {
        instance = accessor.setIntValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getFloatValue(int)
     */
    @Override
    public final float getFloatValue(final int index) {
        return accessor.getFloatValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setFloatValue(int, float)
     */
    @Override
    public final GenericObject<STORAGE> setFloatValue(final int index,
            final float value) {
        instance = accessor.setFloatValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getLongValue(int)
     */
    @Override
    public final long getLongValue(final int index) {
        return accessor.getLongValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setLongValue(int, long)
     */
    @Override
    public final GenericObject<STORAGE> setLongValue(final int index,
            final long value) {
        instance = accessor.setLongValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getDoubleValue(int)
     */
    @Override
    public final double getDoubleValue(final int index) {
        return accessor.getDoubleValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setDoubleValue(int, double)
     */
    @Override
    public final GenericObject<STORAGE> setDoubleValue(final int index,
            final double value) {
        instance = accessor.setDoubleValue(instance, index, value);
        return this;
    }

    //////////////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see IGenericObject#getObjectValue(int)
     */
    @Override
    public final Object getObjectValue(final int index) {
        return accessor.getObjectValue(instance, index);
    }

    /* (non-Javadoc)
     * @see IGenericObject#setObjectValue(int, Object)
     */
    @Override
    public final GenericObject<STORAGE> setObjectValue(final int index,
            final Object value) {
        instance = accessor.setObjectValue(instance, index, value);
        return this;
    }
}
