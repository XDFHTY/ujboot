package com.cj.sim.utils;

import com.baidu.aip.ocr.AipOcr;
import com.cj.core.util.SpringUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.imageio.stream.FileImageInputStream;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 百度身份证识别工具类
 * Created by XD on 2019/3/9.
 */
public class BdIdCard {



    private RestTemplate restTemplate = SpringUtil.getBean(RestTemplate.class);


    /**
     * 身份证正面照识别
     * @param APP_ID
     * @param API_KEY
     * @param SECRET_KEY
     */
    public  JSONObject IDCardRecogizeFront(String APP_ID,String API_KEY,String SECRET_KEY,String url) {
        AipOcr client = new AipOcr(APP_ID, API_KEY,SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        //是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。
        options.put("detect_direction", "false");
        //是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。
        options.put("detect_risk", "false");
        //背面照
        //String idCardSide = "back";

        //前面照
        String idCardSide = "front";

        // 参数为本地图片路径
        //String image = "D:\\back.jpg";
        //String image = "D:\\front.jpg";
//        String image = url;//身份证正面照片路径
//        return client.idcard(image, idCardSide, options);

        // 参数为本地图片二进制数组
       byte[] file = getImageFromNetByUrl(url);
        return client.idcard(file, idCardSide, options);
    }

    /**
     * 身份证背面照识别
     * @param APP_ID
     * @param API_KEY
     * @param SECRET_KEY
     */
    public JSONObject IDCardRecogizeBack(String APP_ID,String API_KEY,String SECRET_KEY,String url) {
        AipOcr client = new AipOcr(APP_ID, API_KEY,SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        //是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。
        options.put("detect_direction", "false");
        //是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。
        options.put("detect_risk", "false");
        //背面照
        String idCardSide = "back";

        // 参数为本地图片二进制数组
        byte[] file = getImageFromNetByUrl(url);
        // 参数为本地图片路径
        return client.idcard(file, idCardSide, options);

        // 参数为本地图片二进制数组
       /* byte[] file = readImageFile(image);
        res = client.idcard(file, idCardSide, options);
        System.out.println(res.toString(2));*/
    }


    /**
     将图像转为二进制数组
     * @param path
     * @return
     */
    public static byte[] readImageFile(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl
     *            网络连接地址
     * @return
     */
    public  byte[] getImageFromNetByUrl(String strUrl) {
       /* try {
            URL url = new URL(strUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);

            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Resource> httpEntity = new HttpEntity<Resource>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(strUrl, HttpMethod.GET,
                httpEntity, byte[].class);
        return response.getBody();

    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream
     *            输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
