package com.cj.sbasic.service.impl;

import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.domain.Msg;
import com.cj.sbasic.domain.MsgSendReq;
import com.cj.sbasic.mapper.MsgMapper;
import com.cj.sbasic.service.BasicCallImService;
import com.cj.sbasic.service.BasicMsgService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class BasicMsgServiceImpl implements BasicMsgService {
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private BasicCallImService basicCallImService;

    @Override
    public OldPager getMsgPage(OldPager oldPager) {
        oldPager.setRecordTotal(msgMapper.getMsgPageCount(oldPager));
        oldPager.setContent(msgMapper.getMsgPage(oldPager));
        return oldPager;
    }

    @Override
    public int deleteMsgById(Long id) {
        return msgMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String exportMsgs(OldPager oldPager, HttpServletResponse response) {
        List<Msg> msgList = (List<Msg>) oldPager.getContent();
        try {
            OutputStream out = response.getOutputStream();

            String fileName = "推送消息列表";//文件名
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");

            List<List<String>> result = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook();
            String[] headers = {"对象","消息类型","发布时间"};
            //设置数据
            if(msgList!=null){
                for(Msg msg:msgList){
                    List<String> rowData = new ArrayList<String>();
                    //设置对象
                    rowData.add(msg.getObjectstr());
                    //设置消息类型
                    if ("0".equals(msg.getMsgType())){
                        rowData.add("广告消息");
                    } else if ("1".equals(msg.getMsgType())){
                        rowData.add("活动消息");
                    } else if ("".equals(msg.getMsgType())){
                        rowData.add("");
                    }
                    //设置发布时间
                    rowData.add(DateUtil.dateToStr(msg.getPublishTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "投诉信息列表", headers, result, out);
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            return "导出失败";
        }
        return "导出成功";
    }

    @Override
    public int addMsg(Msg msg){
        return msgMapper.insertSelective(msg);
    }

    @Override
    public Msg getMsgById(Long id) {
        Msg msg = msgMapper.selectByPrimaryKey(id);
        if (msg != null){
            return msg;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean sendMsg(MsgSendReq msgSendReq) {
        //消息表新增记录，并返回主键
        Msg msg = new Msg();
        msg.setContent(msgSendReq.getMsgContent());
        msg.setTitle(msgSendReq.getMsgTitle());
        msg.setMsgType(msgSendReq.getMsgType());
        msg.setObjectstr(generateObjectStrMsgSend(msgSendReq));
        msg.setPublishTime(new Date());
        long b1 = System.currentTimeMillis();
        addMsg(msg);//数据库存放消息记录
        long b2 = System.currentTimeMillis();
        System.out.println("+++++++++++++++++++++++");
        System.out.println("存放推送记录耗时："+(b2-b1));
        System.out.println("+++++++++++++++++++++++");

        //将消息推送出去
        Map map = new HashMap();

        map.put("days",msgSendReq.getDays());//设置保留天数(最大10)

        Map params = new HashMap();//自定义kv
        params.put("msgId",String.valueOf(msg.getMsgId()));//将消息id放入自定义kv中
        params.put("type","2");//标识推送消息类型属于系统消息类型
        map.put("params",params);

        map.put("alert",msg.getTitle());//通知内容
        List tag = new ArrayList();//tag用户的标签集合,最多20个标签
        String type = "";//1-标签之间取并集 2-标签之间取交集
        if ("1".equals(msgSendReq.getUserType())){
            if(msgSendReq.getMinAge().equals("") && msgSendReq.getMaxAge().equals("")){
                tag.add("用户");
            }else{
                List<Integer> tagList = generateAgeTagList(msgSendReq.getMinAge(),msgSendReq.getMaxAge());
                for (int i = 0; i < tagList.size(); i++) {
//                if (i < 20) {//最大只能传20个标签
                    tag.add(String.valueOf(tagList.get(i)));
//                }
                }
            }
            type = "1";//取并集
            map.put("appType","1");//1表示用户端
        } else if ("2".equals(msgSendReq.getUserType())){
            if (!"".equals(msgSendReq.getDepartment()) && !"".equals(msgSendReq.getTitle())){
                tag.add(msgSendReq.getDepartment());
                tag.add(msgSendReq.getTitle());
                type = "2";//取交集
            } else if ("".equals(msgSendReq.getDepartment())&& !"".equals(msgSendReq.getTitle())){
                tag.add(msgSendReq.getTitle());
                type = "1";//取并集
            } else if (!"".equals(msgSendReq.getDepartment()) && "".equals(msgSendReq.getTitle())){
                tag.add(msgSendReq.getDepartment());
                type = "1";//取并集
            } else if ("".equals(msgSendReq.getDepartment()) && "".equals(msgSendReq.getTitle())){
                tag.add("家庭医生");
                type = "1";//取并集
            }
            map.put("appType","2");//2表示医生端
        }else if ("3".equals(msgSendReq.getUserType())){
                tag.add("肾病医生");
                type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("4".equals(msgSendReq.getUserType())){
            tag.add("营养师");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("5".equals(msgSendReq.getUserType())){
            tag.add("乡干部");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("6".equals(msgSendReq.getUserType())){
            tag.add("卫健委干部");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("7".equals(msgSendReq.getUserType())){
            tag.add("离退休干部");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("8".equals(msgSendReq.getUserType())){
            tag.add("护士");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }else if ("9".equals(msgSendReq.getUserType())){
            tag.add("健康管理师");
            type = "1";//取并集
//            }
            map.put("appType","2");//2表示医生端
        }
        map.put("tag",tag);
        map.put("type",type);

        long a1 = System.currentTimeMillis();
        System.out.println("+++++++++++++++++++++++");
        System.out.println("设置map："+(b2-a1));
        System.out.println("+++++++++++++++++++++++");
        ApiResult apiResult = basicCallImService.buildPushObjectAllOrAndTagAlert(map);//将消息推送出去
        long a2 = System.currentTimeMillis();
        System.out.println("+++++++++++++++++++++++");
        System.out.println("调用推送耗时："+(a2-a1));
        System.out.println("+++++++++++++++++++++++");
        if (apiResult.getCode() == 1001){//推送成功
            return true;
        } else{//推送失败，回滚新增的消息记录
            msgMapper.deleteByPrimaryKey(msg.getMsgId());
            throw new RuntimeException("推送失败");
        }
    }


    /**
     * 将年龄转换为年份
     * @param age
     * @return
     */
    private String convertAgeToYear(String age){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return (currentYear - Integer.valueOf(age)) + "";
    }

    /**
     * 获取指定年龄段之间的所有年龄标签
     * @param minAge
     * @param maxAge
     * @return
     */
    private List<Integer> generateAgeTagList(String minAge,String maxAge){
        List<Integer> tagList = new ArrayList<>();
        int minYear = Integer.valueOf(convertAgeToYear(maxAge));
        int maxYear = Integer.valueOf(convertAgeToYear(minAge));
        while (minYear != maxYear){
            tagList.add(minYear++);
        }
        tagList.add(minYear);
        return tagList;
    }

    /**
     * 生成推送对象描述字符串
     * @param msgSendReq
     * @return
     */
    private String generateObjectStrMsgSend(MsgSendReq msgSendReq){
        String objectStr = "";
        if ("1".equals(msgSendReq.getUserType())){
            if(msgSendReq.getMaxAge().equals("")&& msgSendReq.getMinAge().equals("")){
                objectStr = "所有用户";
            }else{
                objectStr += "年龄在" + msgSendReq.getMinAge() + "-" + msgSendReq.getMaxAge() + "周岁的用户";
            }
        } else if ("2".equals(msgSendReq.getUserType())){
            if (!"".equals(msgSendReq.getDepartment())){
                objectStr += "科室是"+msgSendReq.getDepartment();
            }
            if (!"".equals(msgSendReq.getTitle())){
                objectStr += "职称是" + msgSendReq.getTitle();
            }
            if ("".equals(msgSendReq.getTitle()) && "".equals(msgSendReq.getDepartment())){
                objectStr += "所有";
            }
            objectStr += "的家庭医生";
        }else if ("3".equals(msgSendReq.getUserType())){
            objectStr = "专科医生";
        }else if ("4".equals(msgSendReq.getUserType())){
            objectStr = "营养师" ;
        }else if ("5".equals(msgSendReq.getUserType())){
            objectStr = "乡干部";
        }else if ("6".equals(msgSendReq.getUserType())){
            objectStr = "卫健委干部";
        }else if ("7".equals(msgSendReq.getUserType())){
            objectStr = "离退休干部";
        }else if ("8".equals(msgSendReq.getUserType())){
            objectStr = "护士";
        }else if ("9".equals(msgSendReq.getUserType())){
            objectStr = "健康管理师";
        }
        return objectStr;
    }
}
