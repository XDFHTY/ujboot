package com.cj.spension.service.impl;

import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.domain.OldPager;
import com.cj.spension.domain.FillIllModel;
import com.cj.spension.domain.UserModel;
import com.cj.spension.mapper.SPFallIllMapper;
import com.cj.spension.mapper.SPUserInfoMapper;
import com.cj.spension.service.SPUserService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 用户信息管理业务实现层
 * @date： 2019/3/11 16:17
 **/
@Service
public class SPUserServiceImpl implements SPUserService {
    @Autowired
    private SPUserInfoMapper userInfoMapper;
    @Autowired
    private SPFallIllMapper fallIllMapper;

    /**
     * 分页查询用户信息
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getUserPage(OldPager oldPager) {
        //获取当前时间
        Date stateTime = new Date();
        Calendar nowTime = Calendar.getInstance();
        //推后3天
        nowTime.add(Calendar.DATE, 3);
        //获得3天后时间
        Date endtime = nowTime.getTime();
        oldPager.getParameters().put("stateTime",DateUtil.dateToStr(stateTime,DateUtil.YYYY_MM_DDHHMMSS));
        oldPager.getParameters().put("endtime",DateUtil.dateToStr(endtime,DateUtil.YYYY_MM_DDHHMMSS));
        if(oldPager.getParameters().get("minAge")!=null){
            oldPager.getParameters().put("minAge",ageToYear(oldPager.getParameters().get("minAge")));
        }
        if(oldPager.getParameters().get("maxAge")!=null){
            oldPager.getParameters().put("maxAge",ageToYear(oldPager.getParameters().get("maxAge")));
        }
        //查询条数
        oldPager.setRecordTotal(userInfoMapper.findUserCount(oldPager));
        //查询类容
        List<UserModel> list = userInfoMapper.findUserPage(oldPager);
        oldPager.setContent(list);
        return oldPager;
    }

    /**
     * 分页查询用户疾病信息
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getFillPage(OldPager oldPager) {
        //查询条数
        oldPager.setRecordTotal(fallIllMapper.findFillIllModelCount(oldPager));
        //查询类容
        oldPager.setContent(fallIllMapper.findFillIllModelPage(oldPager));
        return oldPager;
    }

    /**
     * 根据ID查询用户信息
     * @param userId
     * @return
     */
    @Override
    public UserModel getUserByID(Long userId) {
        return userInfoMapper.findUserByID(userId);
    }

    /**
     * 根据ID查询用户疾病信息
     * @param fallIllId
     * @return
     */
    @Override
    public FillIllModel getFillByID(Long fallIllId) {
        return fallIllMapper.findByid(fallIllId);
    }

    /**
     * 导出Excel
     * @param oldPager
     * @return
     */
    @Override
    public void exportExcel(OldPager oldPager, HttpServletResponse response) {
        if(oldPager.getParameters().get("minAge")!=null){
            oldPager.getParameters().put("minAge",ageToYear(oldPager.getParameters().get("minAge")));
        }
        if(oldPager.getParameters().get("maxAge")!=null){
            oldPager.getParameters().put("maxAge",ageToYear(oldPager.getParameters().get("maxAge")));
        }
        List<UserModel> list = userInfoMapper.findUser(oldPager);
        String fileName="用户基本信息";
        try {
            //设置输出文件地址和名字
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            //获得输出流
            OutputStream out = response.getOutputStream();
            //设置响应头
            response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");
            List<List<String>> result = new ArrayList<>();
            //生成格式是xlsx可存储103万行数据，如果是xls则只能存不到6万行数据
            XSSFWorkbook workbook = new XSSFWorkbook();
            //用户信息
            //设置标题栏类容
            String[] headers = {"用户名","姓名","性别","年龄","手机号","身份证号","头像","地区","地址","所属医院","病史"};
            //设置数据
            for(UserModel userModel:list){
                List<String> rowData = new ArrayList<String>();
                //设置用户名
                rowData.add(userModel.getUser().getUserName());
                //设置姓名
                rowData.add(userModel.getUserInfo().getName());
                //设置性别
                rowData.add(userModel.getUserInfo().getSex());
                //设置年龄
                Integer age = userModel.getUserInfo().getAge();
                if(age != null){
                    rowData.add(age.toString());
                }else{
                    rowData.add("null");
                }
                //设置手机号
                rowData.add(userModel.getUser().getPhoneNumber());
                //设置身份证号
                rowData.add(userModel.getUserInfo().getIdNumber());
                //设置头像
                rowData.add(userModel.getUserInfo().getHeadUrl());
                //设置地区
                String city = userModel.getCity();
                rowData.add(city);
                //设置地址
                String province = userModel.getProvince();
                String area = userModel.getArea();
                rowData.add(province+city+area);
                //设置所属医院
                rowData.add(userModel.getHospital());
                //设置病史
                rowData.add(userModel.getDisease());
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "用户信息", headers, result, out);
            //将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("导出失败");
        }
    }

    /**
     * 年龄转日期
     * @param o
     * @return
     */
    public String ageToYear(Object o){
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        String year = (yearNow - (Double.valueOf(o.toString())).intValue())+"-00-00";
        return year;
    }
}
