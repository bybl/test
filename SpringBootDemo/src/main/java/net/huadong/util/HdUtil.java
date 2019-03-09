package net.huadong.util;

import net.huadong.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 工具类
 *
 * @author wuph
 * @create 2017-01-19 10:32
 */
public class HdUtil {
    public static final char UNDERLINE = '_';

    /**
     * 获取访问IP地址
     *
     * @param request
     * @return java.lang.String
     * @throw
     * @author wuph
     * @date 2017-03-10
     */
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }


    /**
     * 获取请求参数，返回参数集
     *
     * @param request
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @throw
     * @author wuph
     * @date 2017/1/19
     */
    public static HashMap<String, Object> getRequestParams(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if ("sort".equals(paraName) && StringUtils.isNotEmpty(request.getParameter(paraName))) {
                map.put(paraName, HdUtil.camelToUnderline(request.getParameter(paraName)));
            } else {
                map.put(paraName, request.getParameter(paraName));
            }

        }
        return map;
    }

    /**
     * 驼峰转下划线
     *
     * @param param
     * @return java.lang.String
     * @throw
     * @author wuph
     * @date 2017/1/20
     */
    public static String camelToUnderline(String param) {

        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String md5(String str) {
        String result = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update((str).getBytes("UTF-8"));
            byte b[] = md5.digest();

            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
                result = buf.toString();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取当前登录用户对象
     *
     * @param
     * @return net.huadong.entity.XtYh
     * @author wuph
     * @date 2016-12-29
     */
    public static SysUser getLoginEmp() {
        return (SysUser) SecurityUtils.getSubject().getSession().getAttribute(Constant.LOGIN_USER);
    }



    /**
     * 设置当前登录用户
     *
     * @param
     * @return
     * @author wuph
     * @date 2016-12-29
     */
    public static void setLoginUser(SysUser employee) {
        SecurityUtils.getSubject().getSession().setAttribute(Constant.LOGIN_USER, employee);
    }

    public static void setLoginEmployee(SysUser cEmployee) {
        SecurityUtils.getSubject().getSession().setAttribute(Constant.LOGIN_USER, cEmployee);
    }


    /**
     * 判断请求是否是ajax
     *
     * @param request
     * @return boolean
     * @throw
     * @author wuph
     * @date 2017-02-22
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    public static void responseString(HttpServletResponse response,
                                      String result) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
//
//    /**
//     * 连接FTP服务器
//     *
//     * @param ftpClient
//     * @param path
//     * @return
//     * @throws IOException
//     */
//    public static FTPClient connect(FTPClient ftpClient, String path) throws IOException {
//        if (!ftpClient.isConnected()) {
//            ftpClient.connect(Constant.FTP_SERVER);
//            ftpClient.login(Constant.FTP_USER, Constant.FTP_PASSWORD);
//            ftpClient.enterLocalPassiveMode();
//            ftpClient.changeWorkingDirectory(path);
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftpClient.setControlEncoding("gbk");
//        }
//        return ftpClient;
//    }
//
//    /**
//     * 关闭FTP连接
//     *
//     * @param ftpClient
//     */
//    public static void closeFtp(FTPClient ftpClient) {
//        if (ftpClient != null && ftpClient.isConnected()) {
//            try {
//                ftpClient.logout();
//                ftpClient.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    /**
     * 生成xml文件
     *
     * @param filename
     * @param document
     * @throws IOException
     */
    public static void createXMLFile(String filename, Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();//缩减型格式
        format.setEncoding("UTF-8");//设置文件内部文字的编码
        format.setExpandEmptyElements(true);
        format.setTrimText(false);
        format.setIndent(true);      // 设置是否缩进
        format.setIndent("   ");     // 以空格方式实现缩进
        String encoding = "UTF-8";//设置文件的编码！！和format不是一回事
        OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(filename), encoding);
        XMLWriter writer = new XMLWriter(outstream, format);
        writer.write(document);
        writer.close();
    }

    //生成时间字符串
    public static String timeString() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = formatter.format(date);
        return time;
    }

    //日期转时间格式
    public static Date stringDate(String dateFormat, String dateStr) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = formatter.parse(dateStr);
        return date;
    }

    //获得上个月的第一天
    public static Date getFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    //获得上个月的最后一天
    public static Date getLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.SECOND, -1);
        Date date = calendar.getTime();
        return date;
    }

    //获得上个月的总小时数
    public static BigDecimal getMonthHours(String yearMonth) {
        int year = Integer.valueOf(yearMonth.split("-")[0]);
        int month = Integer.valueOf(yearMonth.split("-")[1]);
        //取得系统当前时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        //取得系统当前时间所在月第一天时间对象
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //日期减一,取得上月最后一天时间对象
        cal.add(Calendar.DAY_OF_MONTH, -1);
        BigDecimal time = new BigDecimal(cal.get(Calendar.DAY_OF_MONTH) * 24);
        return time;
    }

    //获得上个月月份
    public static String getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }

    //获得上个月第一天
    public static String getFirstMonthStr() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(c.getTime());
        return time;
    }

    //获得上个月最后一天
    public static String getLastMonthStr() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(c.getTime());
        return time;
    }
}
