package com.common.util;

/**
 * @author dingxl
 * @date 3/6/2021 2:40 PM
 */
public class YesNo {
    public static final int YES = 1;

    public static boolean isYes(Integer value) {
        return value != null && value == YES;
    }
}
