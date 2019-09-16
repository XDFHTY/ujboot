package com.cj.sbasic.service.impl;

import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.mapper.SBUserInfoMapper;
import com.cj.sbasic.service.UserInfoService;
import com.cj.sbasic.vo.UserCheckVO;
import com.cj.sbasic.vo.UserDetailVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private SBUserInfoMapper userInfoMapper;

    @Override
    public OldPager getUserCheckPage(OldPager oldPager) {
        //查询总条数
        oldPager.setRecordTotal(userInfoMapper.getUserCheckCount(oldPager));
        //查询数据
        oldPager.setContent(userInfoMapper.getUserCheckPage(oldPager));
        return oldPager;
    }

    @Override
    public UserDetailVO getUserDetailById(Long id) {
        UserDetailVO userDetailVO = userInfoMapper.getUserDetailById(id);
        if (userDetailVO != null){
            return userDetailVO;
        } else {
            return null;
        }
    }

    @Override
    public String exportUserCheck(OldPager oldPager, HttpServletResponse response) {
        List<UserCheckVO> userCheckVOList = (List<UserCheckVO>) oldPager.getContent();
        try {
            OutputStream out = response.getOutputStream();

            String fileName = "用户审核记录";//文件名
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");

            List<List<String>> result = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook();
            //设置标题栏类容
            String[] headers = {"姓名","手机号","身份","省","市","县","身份证号","申请时间"};
            //设置数据
            if(userCheckVOList!=null){
                for(UserCheckVO userCheckVO:userCheckVOList){
                    List<String> rowData = new ArrayList<String>();
                    //设置姓名
                    rowData.add(userCheckVO.getName());
                    //设置手机号
                    rowData.add(userCheckVO.getPhoneNumber());
                    //设置身份
                    if ("1".equals(userCheckVO.getUserType())) {
                        rowData.add("用户");
                    } else if ("2".equals(userCheckVO.getUserType()) || "3".equals(userCheckVO.getUserType())) {
                        rowData.add("医生");
                    } else if ("".equals(userCheckVO.getUserType())) {
                        rowData.add("");
                    }
                    //设置省
                    rowData.add(userCheckVO.getProvince());
                    //设置市
                    rowData.add(userCheckVO.getCity());
                    //设置县
                    rowData.add(userCheckVO.getArea());
                    //设置身份证号
                    rowData.add(userCheckVO.getIdNumber());
                    //设置申请时间
                    rowData.add(DateUtil.dateToStr(userCheckVO.getUpdateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "用户审核记录", headers, result, out);
            //将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            return "导出失败";
        }
        return "导出成功";
    }
}
