package common.utils.Iphone;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

/**
 * Created by sean on 2016/11/16.
 */
public class IphoneUtils {
    /**
     * @param length 随机数长度
     * @return
     * @Description 生成随机数
     * @date 2016-11-16
     * @author sean
     */
    public static String getCode(int length) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    //发送短信
    public static String sendSMS(String mobile, String code) {
        String result = postTemMsg(
                "http://120.76.25.160:7788/sms.aspx",
                "action=send&userid=31&account=yyej&password=520620&mobile=" + mobile + "&content=【医药e家】您的验证码为:" + code + "&sendTime=&extno=");
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(result);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("returnstatus");
            String returnstatus = nodelist1.item(0).getTextContent();
            return returnstatus;
        } catch (DOMException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Faild";
    }

    /**
     * @param urlStr 请求url地址; String paramStr 参数列表
     *               paramData=123&name=abc
     * @return String 返回值
     * @Description 发送post请求 --发送模板消息
     * @date 2016-11-16
     * @author sean
     */
    public static String postTemMsg(String urlStr, String paramStr) {
        // url地址
        URL url = null;
        // http连接
        HttpURLConnection httpConn = null;
        // 输入流
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            httpConn = (HttpURLConnection) url.openConnection();
            // 提交模式
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStreamWriter out2 = new OutputStreamWriter(httpConn.getOutputStream(), "utf-8");
            out2.write(paramStr);
            out2.flush();
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        return result;
    }

}
