package com.thanos.web3j.abi;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thanos.web3j.abi.datatypes.DynamicArray;
import com.thanos.web3j.abi.datatypes.DynamicBytes;
import com.thanos.web3j.abi.datatypes.Fixed;
import com.thanos.web3j.abi.datatypes.Int;
import com.thanos.web3j.abi.datatypes.StaticArray;
import com.thanos.web3j.abi.datatypes.Type;
import com.thanos.web3j.abi.datatypes.Ufixed;
import com.thanos.web3j.abi.datatypes.Uint;
import com.thanos.web3j.abi.datatypes.Utf8String;

/**
 * Utility functions.
 */
public class Utils {
    private Utils() {}

    static <T extends Type> String getTypeName(TypeReference<T> typeReference) {
        try {
            java.lang.reflect.Type reflectedType = typeReference.getType();

            Class<?> type;
            if (reflectedType instanceof ParameterizedType) {
                type = (Class<?>) ((ParameterizedType) reflectedType).getRawType();
                return getParameterizedTypeName(typeReference, type);
            } else {
                type = Class.forName(reflectedType.getTypeName());
                return getSimpleTypeName(type);
            }
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Invalid class reference provided", e);
        }
    }

    static String getSimpleTypeName(Class<?> type) {
        String simpleName = type.getSimpleName().toLowerCase();

        if (type.equals(Uint.class) || type.equals(Int.class)
                || type.equals(Ufixed.class) || type.equals(Fixed.class)) {
            return simpleName + "256";
        } else if (type.equals(Utf8String.class)) {
            return "string";
        } else if (type.equals(DynamicBytes.class)) {
            return "bytes";
        } else {
            return simpleName;
        }
    }

    static <T extends Type, U extends Type> String getParameterizedTypeName(
            TypeReference<T> typeReference, Class<?> type) {

        try {
            if (type.equals(DynamicArray.class)) {
                Class<U> parameterizedType = getParameterizedTypeFromArray(typeReference);
                String parameterizedTypeName = getSimpleTypeName(parameterizedType);
                return parameterizedTypeName + "[]";
            } else if (type.equals(StaticArray.class)) {
                Class<U> parameterizedType = getParameterizedTypeFromArray(typeReference);
                String parameterizedTypeName = getSimpleTypeName(parameterizedType);
                return parameterizedTypeName
                        + "["
                        + ((TypeReference.StaticArrayTypeReference) typeReference).getSize()
                        + "]";
            } else {
                throw new UnsupportedOperationException("Invalid type provided " + type.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Invalid class reference provided", e);
        }
    }

    static <T extends Type> Class<T> getParameterizedTypeFromArray(
            TypeReference typeReference) throws ClassNotFoundException {

        java.lang.reflect.Type type = typeReference.getType();
        java.lang.reflect.Type[] typeArguments =
                ((ParameterizedType) type).getActualTypeArguments();

        String parameterizedTypeName = typeArguments[0].getTypeName();
        return (Class<T>) Class.forName(parameterizedTypeName);
    }

    @SuppressWarnings("unchecked")
    public static List<TypeReference<Type>> convert(List<TypeReference<?>> input) {
        List<TypeReference<Type>> result = new ArrayList<>(input.size());
        result.addAll(input.stream()
                .map(typeReference -> (TypeReference<Type>) typeReference)
                .collect(Collectors.toList()));
        return result;
    }
}
