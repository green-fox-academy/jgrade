package com.github.tkutcher.jgrade.utils;

import java.math.BigDecimal;

public class GroovyUtils {
    public static double getNumberAsDouble(Object number) {
        if (number instanceof Double) {
            return (double) number;
        } else if (number instanceof BigDecimal) {
            return ((BigDecimal) number).doubleValue();
        } else if (number instanceof Integer) {
            return (double) (int) number;
        } else {
            throw new IllegalArgumentException(String.format("Cannot cast %s to double", number));
        }
    }
}
