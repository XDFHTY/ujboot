package com.cj.sim.hx.easemob;


import com.cj.common.utils.hx.IMUtil;
import com.cj.common.utils.hx.api.impl.EasemobFile;
import com.cj.common.utils.hx.entity.RespUploadImgFile;
import org.junit.Test;

import java.io.File;

/**
 * Created by easemob on 2017/3/22.
 */
public class FileTest {
    private EasemobFile easemobFile = new EasemobFile();

    @Test
    public void uploadFile() {
        //String path = TokenUtil.class.getClassLoader().getResource("D:/1.jpg").getPath();
        /*File file = new File("D:/1.jpg");
        Object result = easemobFile.uploadFile(file);
        System.out.println(result);
        Assert.assertNotNull(result);*/

        RespUploadImgFile respUploadImgFile = new IMUtil().uploadImgFile("D:/2.jpg");
        System.out.println(respUploadImgFile.toString());
    }
//https://a1.easemob.com/1118190227216911/testdemo/chatfiles
    @Test
    public void downloadFile() {
        String uuid = "8066b560-3bcd-11e9-8242-c354a64d1353";
        String shareSecret = "gGa1ajvNEem3Jpl43DlMiSsy1AnOA5XmdBFmtwNvp70zEofF";
        Boolean thumbnail = true;
        File result = (File) easemobFile.downloadFile(uuid, shareSecret, thumbnail);
        System.out.println(result);

    }
}
