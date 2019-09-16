package com.cj.sfile.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.sfile.config.FilePro;
import com.cj.sfile.utlis.file.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/s-file/api/file")
@Api(tags = "公共接口:  文件服务")
@Slf4j
//@RefreshScope
public class FileController {

    @Value("${web.upload-path}")
    String path;

    @Value("${server.port}")
    String port;

    @Value("${spring.profiles.active}")
    String profile;


    @Autowired
    private FilePro filePro;

    static String boot = "";

    void isBoot(){
        if (filePro.getName().equals("uj")){
            boot = "s-file/";
        }
    }

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(value = "/uploads")
    @ApiOperation("文件上传，字节流，不压缩")
    @Log(name = "文件服务", value = "文件上传，字节流，不压缩")
    public ApiResult upload(HttpServletRequest request,
                            @ApiParam(name = "multipartFile", value = "文件字节流", required = true)
                                    MultipartFile multipartFile) throws Exception {
        isBoot();
        String fileUrl = "";  //地址
        String prifix = "";

        String contentType = multipartFile.getContentType();   //文件类型
        String fileName = multipartFile.getOriginalFilename();  //文件名字

        //获取前缀
        String fileNamePrifix = fileName.substring(0,fileName.lastIndexOf("."))+"_";
        //获取后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        System.out.println("=======文件类型" + contentType);
        System.out.println("=======文件名字" + fileName);
        fileName =  (UUID.randomUUID().toString().replaceAll("-","")).toUpperCase() + suffix;

        prifix = "img/";
        //文件存放路径
        String filePath = path + prifix;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //调用文件处理类FileUtil，处理文件，将文件写入指定位置
        try {
            FileUtil.uploadFile(multipartFile.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
            log.info("保存图片异常");
        }


        String url = null;
        url = request.getHeader("url");
        if (url != null){
            String uri = request.getRequestURI();
            url = url.replace(uri,"/");

        }else if (url == null) {
            url = request.getRequestURL().toString();
            String uri = request.getRequestURI();
            url = url.replace(uri,"/");
        }

        url = url.replace("http:","https:");



        //图片的访问路径
        fileUrl = url  +boot + prifix + fileName;
        System.out.println("文件在系统中的完整访问路径===>>" + fileUrl);
        Map map = new HashMap();
        map.put("fileUrl", fileUrl);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(map);

        return apiResult;
    }

    @PostMapping(value = "/uploadIdCard")
    @ApiOperation("文件上传,字节流，压缩")
    @Log(name = "文件服务", value = "文件上传,字节流，压缩")
    public ApiResult uploadIdCard(HttpServletRequest request,
                            @ApiParam(name = "multipartFile", value = "文件字节流", required = true)
                                    MultipartFile multipartFile) throws Exception {
        isBoot();
        String fileUrl = "";  //地址
        String prifix = "";

        String contentType = multipartFile.getContentType();   //文件类型
        String fileName = multipartFile.getOriginalFilename();  //文件名字
        //获取前缀
        String fileNamePrifix = fileName.substring(0,fileName.lastIndexOf("."))+"_";

        //获取后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        System.out.println("=======文件类型" + contentType);
        System.out.println("=======文件名字" + fileName);
        fileName =  UUID.randomUUID().toString().replaceAll("-","") + suffix;

        prifix = "idcard/";
        //文件存放路径
        String filePath = path + prifix;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
//        //调用文件处理类FileUtil，处理文件，将文件写入指定位置
//        try {
//            FileUtil.uploadFile(multipartFile.getBytes(), filePath, fileName);
//        } catch (Exception e) {
//            // TODO: handle exception
//            log.info("保存图片异常");
//        }

        try {
            // 先尝试压缩并保存图片
//            Thumbnails.of(multipartFile.getInputStream()).scale(1f).outputQuality(0.5f).toFile(new File(filePath+fileName));
            Thumbnails.of(multipartFile.getInputStream()).size(1920,1080).toFile(new File(filePath+fileName));
        } catch (IOException e) {
            try {
                System.out.println("=================压缩失败");
                // 失败了再用springmvc自带的方式
                multipartFile.transferTo(new File(filePath+fileName));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


        String url = null;
        url = request.getHeader("url");
        if (url != null){
            String uri = request.getRequestURI();
            url = url.replace(uri,"/");

        }else if (url == null) {
            url = request.getRequestURL().toString();
            String uri = request.getRequestURI();
            url = url.replace(uri,"/");
        }

        url = url.replace("http:","https:");

        //图片的访问路径
        fileUrl = url +boot  + prifix + fileName;
        System.out.println("文件在系统中的完整访问路径===>>" + fileUrl);
        Map map = new HashMap();
        map.put("fileUrl", fileUrl);

        ApiResult apiResult = ApiResult.SUCCESS();
        apiResult.setData(map);

        return apiResult;
    }


    /**
     * 从环信服务器 下载 图片 并保存到本地的接口
     *
     * @param map
     * @throws Exception
     */
    @PostMapping(value = "/hxUploads")
    @ApiOperation("环信文件上传")
    @Log(name = "文件服务", value = "环信文件上传")
    public ApiResult downloadFile(@RequestBody Map map) throws Exception {
        final String APPLICATION_JPG = "application/jpg";
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            List list = new ArrayList<>();
            list.add(MediaType.valueOf(APPLICATION_JPG));
            headers.setAccept(list);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    "http://a1.easemob.com/" + map.get("ORG_NAME") + "/" + map.get("APP_NAME") + "/chatfiles/" + map.get("uuid"),
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);

            byte[] result = response.getBody();


            inputStream = new ByteArrayInputStream(result);

            File file = new File(path + "/im/img/" + "hx_" + map.get("uuid") + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        ApiResult apiResult = ApiResult.SUCCESS();
        return apiResult;
    }

}
