package com.kingpiggy.study.productordersystem.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
