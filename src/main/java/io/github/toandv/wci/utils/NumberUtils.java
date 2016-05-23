package io.github.toandv.wci.utils;


import com.google.common.base.Preconditions;

/**
 * Created by toan on 5/23/16.
 */
public class NumberUtils {

    public static Object negate(Object number) {
        Preconditions.checkArgument(number instanceof Number,
                "Number required but " + number.getClass().getName() + " found.");
        if (number instanceof Integer) {
            return -(Integer) number;
        }
        if (number instanceof Long) {
            return -(Long) number;
        }
        if (number instanceof Float) {
            return -(Float) number;
        }
        if (number instanceof Double) {
            return -(Double) number;
        }
        if (number instanceof Short) {
            return -(Short) number;
        }
        if (number instanceof Byte) {
            return -(Byte) number;
        }
        throw new IllegalArgumentException("Not supported Number subtypes: "
                + number.getClass().getName());
    }
}
