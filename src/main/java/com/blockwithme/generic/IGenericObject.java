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
 * A generic object instance, wrapping a IGenericObjectAccessor and
 * a storage instance together, for easier use.
 *
 * Note that currently, GenericObject neither override equals, hashcode,
 * nor toString, since the underlying generic object implementations do
 * not currently provide that functionality.
 *
 * @see IGenericObjectAccessor for more information.
 *
 * @author monster
 */
public interface IGenericObject<STORAGE> {

    /**
     * Returns the generic object accessor.
     *
     * @return the generic object accessor.
     */
    public IGenericObjectAccessor<STORAGE> getGenericObjectAccessor();

    /**
     * Returns the generic object storage instance.
     *
     * @return the generic object storage instance.
     */
    public STORAGE getGenericObjectStorage();

    /**
     * Sets the generic object storage instance.
     *
     * @param instance The new generic object storage instance.
     * @return this
     *
     * @throws NullPointerException if instance is null
     */
    public GenericObject<STORAGE> setGenericObjectStorage(STORAGE instance);

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesMaximumIndex(Object)
     */
    public int getPrimitiveValuesMaximumIndex();

    /**
     * @see IGenericObjectAccessor#getBooleanValuesMaximumIndex(Object)
     */
    public int getBooleanValuesMaximumIndex()
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#getObjectValuesMaximumIndex(Object)
     */
    public int getObjectValuesMaximumIndex();

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesSlotsAvailable(Object)
     */
    public int getPrimitiveValuesSlotsAvailable();

    /**
     * @see IGenericObjectAccessor#getBooleanValuesSlotsAvailable(Object)
     */
    public int getBooleanValuesSlotsAvailable()
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#getObjectValuesSlotsAvailable(Object)
     */
    public int getObjectValuesSlotsAvailable();

    /**
     * @see IGenericObjectAccessor#getPrimitiveValuesReservedSize(Object)
     */
    public int getPrimitiveValuesReservedSize()
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#getBooleanValuesReservedSize(Object)
     */
    public int getBooleanValuesReservedSize()
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#getObjectValuesReservedSize(Object)
     */
    public int getObjectValuesReservedSize()
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#resizePrimitiveValues(Object, int)
     */
    public GenericObject<STORAGE> resizePrimitiveValues(int reservedSize);

    /**
     * @see IGenericObjectAccessor#resizeBooleanValues(Object, int)
     */
    public GenericObject<STORAGE> resizeBooleanValues(int reservedSize)
            throws UnsupportedOperationException;

    /**
     * @see IGenericObjectAccessor#resizeObjectValues(Object, int)
     */
    public GenericObject<STORAGE> resizeObjectValues(int reservedSize);

    /**
     * @see IGenericObjectAccessor#getBooleanValue(Object, int)
     */
    public boolean getBooleanValue(int index);

    /**
     * @see IGenericObjectAccessor#setBooleanValue(Object, int, boolean)
     */
    public GenericObject<STORAGE> setBooleanValue(int index, boolean value);

    /**
     * @see IGenericObjectAccessor#getByteValue(Object,int)
     */
    public byte getByteValue(int index);

    /**
     * @see IGenericObjectAccessor#setByteValue(Object, int, byte)
     */
    public GenericObject<STORAGE> setByteValue(int index, byte value);

    /**
     * @see IGenericObjectAccessor#getCharValue(Object, int)
     */
    public char getCharValue(int index);

    /**
     * @see IGenericObjectAccessor#setCharValue(Object, int, char)
     */
    public GenericObject<STORAGE> setCharValue(int index, char value);

    /**
     * @see IGenericObjectAccessor#getShortValue(Object, int)
     */
    public short getShortValue(int index);

    /**
     * @see IGenericObjectAccessor#setShortValue(Object, int, short)
     */
    public GenericObject<STORAGE> setShortValue(int index, short value);

    /**
     * @see IGenericObjectAccessor#getIntValue(Object, int)
     */
    public int getIntValue(int index);

    /**
     * @see IGenericObjectAccessor#setIntValue(Object, int, int)
     */
    public GenericObject<STORAGE> setIntValue(int index, int value);

    /**
     * @see IGenericObjectAccessor#getFloatValue(Object, int)
     */
    public float getFloatValue(int index);

    /**
     * @see IGenericObjectAccessor#setFloatValue(Object, int, float)
     */
    public GenericObject<STORAGE> setFloatValue(int index, float value);

    /**
     * @see IGenericObjectAccessor#getLongValue(Object, int)
     */
    public long getLongValue(int index);

    /**
     * @see IGenericObjectAccessor#setLongValue(Object, int, long)
     */
    public GenericObject<STORAGE> setLongValue(int index, long value);

    /**
     * @see IGenericObjectAccessor#getDoubleValue(Object, int)
     */
    public double getDoubleValue(int index);

    /**
     * @see IGenericObjectAccessor#setDoubleValue(Object, int, double)
     */
    public GenericObject<STORAGE> setDoubleValue(int index, double value);

    /**
     * @see IGenericObjectAccessor#getObjectValue(Object, int, double)
     */
    public Object getObjectValue(int index);

    /**
     * @see IGenericObjectAccessor#setObjectValue(Object, int, double)
     */
    public GenericObject<STORAGE> setObjectValue(int index, Object value);

}