package com.cj.suser.service.impl;

import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.FpuModel;
import com.cj.suser.mapper.PerUserMapper;
import com.cj.suser.service.PerUserService;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author： 刘磊
 * @Description: 个人端用户管理业务实现层
 * @date： 2019/6/28 10:31
 **/
@Service
@Transactional
public class PerUserServiceImpl implements PerUserService {

    @Autowired
    private PerUserMapper perUserMapper;

    /**
     * 新增用户信息
     *
     * @param id
     * @param name
     * @param sex
     * @return
     */
    @Override
    public int insertUserInfo(Long id, String name, String sex) {
        return perUserMapper.insertUserInfo(id, name, sex);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUser(Long id) {
        return perUserMapper.logDeleteUser(id);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param userPass
     * @return
     */
    @Override
    public int updatePass(Long userId, String userPass) {
        return perUserMapper.updatePass(userId, userPass);
    }

    /**
     * 分页查询与用户列表
     *
     * @param pager
     * @return
     */
    @Override
    public Pager findUser(Pager pager) {
        Map<String, String> map = (Map<String, String>) pager.getParameters();
        if (map.get("minAge") != null) {
            map.put("minAge", ageToYear(map.get("minAge")));
        }
        if (map.get("maxAge") != null) {
            map.put("maxAge", ageToYear(map.get("maxAge")));
        }
        pager.setParameters(map);
        List<FpuModel> list = perUserMapper.findUser(pager);
//        for (FpuModel fpuModel:list){
//            fpuModel.getOrder();
//            fpuModel.getUbeList();
//            fpuModel.getBrmList();
//            fpuModel.getFimList();
//        }
        pager.setRecords(list);
        return pager;
    }

    /**
     * 查询订单
     *
     * @param pager
     * @return
     */
    @Override
    public Pager findOrder(Pager pager) {
        return perUserMapper.findOrder(pager);
    }

    /**
     * 模糊查询医生
     *
     * @param current
     * @param parameter
     * @return
     */
    @Override
    public List<Map<String, String>> findPeople(int current, String parameter) {
        return perUserMapper.findPeople(current, parameter);
    }

    /**
     * 年龄转日期
     *
     * @param o
     * @return
     */
    public String ageToYear(Object o) {
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        String year = (yearNow - (Double.valueOf(o.toString())).intValue()) + "-00-00";
        return year;
    }

    /**
     * 导出
     *
     * @param map
     * @return
     */
    @Override
    public int export(Map<String, String> map, HttpServletResponse response) {
        if (map.get("minAge") != null) {
            map.put("minAge", ageToYear(map.get("minAge")));
        }
        if (map.get("maxAge") != null) {
            map.put("maxAge", ageToYear(map.get("maxAge")));
        }
        List<FpuModel> list = perUserMapper.find(map);
        String fileName = "个人端用户列表";
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
            //设置标题栏类容
            String[] headers = {"头像", "用户名", "姓名", "性别", "年龄", "地区", "身份证号"};
            //设置数据
            for (FpuModel fpuModel : list) {
                List<String> rowData = new ArrayList<String>();
                //设置头像
                rowData.add(fpuModel.getHeadUrl());
                //设置用户名
                rowData.add(fpuModel.getUserName());
                //设置姓名
                rowData.add(fpuModel.getName());
                //设置性别
                rowData.add(fpuModel.getSex());
                //设置年龄
                rowData.add(getAge(fpuModel.getBirth()));
                //设置地区
                rowData.add(fpuModel.getProvince() + fpuModel.getCity() + fpuModel.getArea());
                //设置身份证号
                rowData.add(fpuModel.getIdNumber());
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "个人端用户列表", headers, result, out);
            //将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 根据出身日期获取年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public String getAge(Date birthDay) throws Exception {
        if (birthDay == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age + "";
    }

}
