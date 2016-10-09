package com.sda.iManu.converter;

/**
 * Created by klolo on 27.09.16.
 */
public interface IConverter<T, U> {

    U convert(T t);
}
