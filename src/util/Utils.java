package util;

/**
 * @author ： gaoang
 * @mudule: 应用模块名称
 * @Copyright: Copyright (C) 2019 BlueSea, Inc. All rights reserved.
 * @Company : 北京巅峰同道科技有限公司
 * @since ：2019/9/27 14:21
 */
public class Utils {
    /**
     * TODO 需要优化一下
     * double类型转String 类型的小数点后的00
     *
     * @param num double参数
     * @return String 类型
     */
    public static String doubleTransform(double num) {
        String strNum = num + "";
        int a = strNum.indexOf(".");
        if (a > 0) {
            //获取小数点后面的数字
            String dianAfter = strNum.substring(a + 1);
            if ("0".equals(dianAfter)) {
                return strNum + "0";
            } else {
                if (dianAfter.length() == 1) {
                    return strNum + "0";
                } else {
                    return strNum;
                }
            }
        } else {
            return strNum ;
        }
    }

    public static void main(String[] args) {
        System.out.println(doubleTransform(555));
    }

}
