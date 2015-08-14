package io.gank.util;


public class StringUtils {


    /**
     * 判断给定字符串是否空白串。
     * 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 格式化时间，如果时间是0-9就在前面加0
     *
     * @param time
     * @return
     */
    public static String addZeroForTime(int time) {
        String newTime = "";
        if (time >= 0 && time < 10) {
            newTime = "0" + time;
        } else {
            newTime = String.valueOf(time);
        }
        return newTime;
    }

}
