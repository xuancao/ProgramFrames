package com.xuancao.programframes.utils;

public class StringUtil {

    /**
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim()) && !"null".equals(s.trim())
                && !"NULL".equals(s.trim());
    }

    /**
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * Judge if a variable of String or String[] is null or ""
     *
     * @param string
     * @param strings
     * @return
     */
    public static boolean judgeNotNull(String string, String... strings) {
        boolean flag = true;
        if (!(string != null && string.trim().length() > 0 && !string.equals("null") && !string.trim().equals("")))
            return false;
        for (String s : strings) {
            if (s == null || string.trim().length() == 0 || s.equals("null")) {
                flag = false;
                break;
            }
        }

        return flag;
    }
}
