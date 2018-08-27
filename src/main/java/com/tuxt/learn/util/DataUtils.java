/**
 * 项目microxt -包 com.microxt.workflow.utils
 * <p>File: FlowUtils.java 创建时间:2013-10-14下午03:24:49</p>
 * <p>Title: 标题（要求能简洁地表达出类的功能和职责）</p>
 * <p>Description: 描述（简要描述类的职责、实现方式、使用注意事项等）</p>
 * <p>Copyright: Copyright (c) 2013 河南亿众科技有限公司</p>
 * <p>Company: 河南亿众科技有限公司</p>
 *
 * @author 陈帅
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */

package com.tuxt.learn.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wbq
 */
public class DataUtils {

    public static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static final SimpleDateFormat secFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat monthDay = new SimpleDateFormat(
            "MM-dd");

    public static final DecimalFormat numFormat = new DecimalFormat("#,###.##");

    public static Date toDate(Object s, String datePatten) {
        if (s == null)
            return null;

        try {
            if (datePatten != null) {
                return new SimpleDateFormat(datePatten).parse(s.toString()
                        .trim());
            } else {
                return sdf.parse(s.toString().trim());
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Date toDate(Object s) {
        if (s == null)
            return null;

        try {
            return sdf.parse(s.toString().trim());
        } catch (Exception e) {
        }
        return null;
    }

    public static Integer toInteger(Object s) {
        if (s != null) {
            try {
                return Integer.parseInt(s.toString().trim());
            } catch (Exception e) {
            }
        }

        return null;
    }

    public static Long toLong(Object s) {
        if (s != null) {
            try {
                return Long.parseLong(s.toString().trim());
            } catch (Exception e) {
            }
        }

        return null;
    }

    public static Double toDouble(Object s) {
        if (s != null) {
            try {
                return Double.parseDouble(s.toString().trim());
            } catch (Exception e) {
            }
        }

        return null;
    }

    public static String insertComma(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat numberFormat;
        double num = Double.parseDouble(s);
        if (len == 0) {
            numberFormat = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            numberFormat = new DecimalFormat(buff.toString());
        }
        String formatStr = numberFormat.format(num);
        if (!formatStr.contains(".") && len > 0) {
            formatStr = StringUtils.rightPad(formatStr.concat("."), formatStr.concat(".").length() + len, "0");
        }

        return formatStr;
    }

    /**
     * 保留两位小数
     *
     * @param data
     * @return
     */
    public static String getBigDecimalFormat(BigDecimal data) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(data);
    }

}
