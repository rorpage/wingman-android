package com.rorpage.wingman.common;

public class Comparator {
    public static boolean compare(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }

        if (obj == null || obj2 == null) {
            return false;
        }

        return obj.equals(obj2);
    }
}
