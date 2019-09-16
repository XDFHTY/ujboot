package com.cj.sbasic.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.common.utils.sms.SmsCodeUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.domain.OldPager;
import com.cj.core.exception.MyException;
import com.cj.sbasic.domain.VoDoctorInfo;
import com.cj.sbasic.mapper.SBDoctorInfoMapper;
import com.cj.sbasic.service.DoctorInfoService;
import com.cj.sbasic.vo.DoctorCheckVO;
import com.cj.sbasic.vo.DoctorDetailVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DoctorInfoServiceImpl implements DoctorInfoService {
    @Autowired(required = false)
    private SBDoctorInfoMapper doctorInfoMapper;

    @Override
    public OldPager getDoctorCheckPage(OldPager oldPager) {
        //查询总条数
        oldPager.setRecordTotal(doctorInfoMapper.getDoctorCheckCount(oldPager));
        //查询数据
        oldPager.setContent(doctorInfoMapper.getDoctorCheckPage(oldPager));
        return oldPager;
    }


    @Override
    public DoctorDetailVO getDoctorDetailById(Long id) {
        DoctorDetailVO doctorDetailVO = doctorInfoMapper.getDoctorDetailById(id);
        if (doctorDetailVO != null) {
            return doctorDetailVO;
        } else {
            return null;
        }
    }

    @Override
    public int updateStatePassById(Long id, BigDecimal goodPrice) {
        int i = doctorInfoMapper.updateStatePassById(id);
        int j =0;
        if (goodPrice!=null){
            j = doctorInfoMapper.updateGoodPrice(id,goodPrice);
        }else {
            j = -1;
        }

        if(i==0||j==0){
            throw new MyException("审核失败");
        }

        VoDoctorInfo voDoctorInfo = doctorInfoMapper.findDoctorInfoByDoctorInfoId(id);


        SendSmsResponse sendSmsResponse = SmsCodeUtil.sendSuccess(voDoctorInfo.getPhone(), voDoctorInfo.getName(),voDoctorInfo.getUpdateTime());

        if (!"OK".equals(sendSmsResponse.getCode())){
            throw new MyException("短信通知失败");
        }

        return 1;
    }

    @Override
    public int updateStateFailById(Long id) {
        int i = doctorInfoMapper.updateStateFailById(id);
        if (i==0){
            throw new MyException("修改失败");
        }
        VoDoctorInfo voDoctorInfo = doctorInfoMapper.findDoctorInfoByDoctorInfoId(id);
        SendSmsResponse sendSmsResponse = SmsCodeUtil.sendFail(voDoctorInfo.getPhone(),voDoctorInfo.getName(),voDoctorInfo.getUpdateTime());
        if (!"OK".equals(sendSmsResponse.getCode())){
            throw new MyException("短信通知失败");
        }

        return i;
    }

    @Override
    public String exportDoctorCheck(OldPager oldPager, HttpServletResponse response) {
        List<DoctorCheckVO> doctorCheckVOList = (List<DoctorCheckVO>) oldPager.getContent();
        try {
            OutputStream out = response.getOutputStream();

            String fileName = "医生审核记录";//文件名
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");

            List<List<String>> result = new ArrayList<>();
            //生成格式是xlsx可存储103万行数据，如果是xls则只能存不到6万行数据
            XSSFWorkbook workbook = new XSSFWorkbook();
            //设置标题栏类容
            String[] headers = {"姓名","手机号","身份","省","市","县","所属医院","科室","职称","申请时间"};
            //设置数据
            if(doctorCheckVOList!=null){
                for(DoctorCheckVO doctorCheckVO:doctorCheckVOList){
                    List<String> rowData = new ArrayList<String>();
                    //设置姓名
                    rowData.add(doctorCheckVO.getUserName());
                    //设置手机号
                    rowData.add(doctorCheckVO.getPhoneNumber());
                    //设置身份
                    if ("1".equals(doctorCheckVO.getUserType())) {
                        rowData.add("用户");
                    } else if ("2".equals(doctorCheckVO.getUserType()) || "3".equals(doctorCheckVO.getUserType())) {
                        rowData.add("医生");
                    } else if ("".equals(doctorCheckVO.getUserType())) {
                        rowData.add("");
                    }
                    //设置省
                    rowData.add(doctorCheckVO.getProvince());
                    //设置市
                    rowData.add(doctorCheckVO.getCity());
                    //设置县
                    rowData.add(doctorCheckVO.getArea());
                    //设置所属医院
                    rowData.add(doctorCheckVO.getHospitalName());
                    //设置科室
                    rowData.add(doctorCheckVO.getDepartmentName());
                    //设置职称
                    rowData.add(doctorCheckVO.getTitle());
                    //设置申请时间
                    rowData.add(DateUtil.dateToStr(doctorCheckVO.getUpdateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "医生审核记录", headers, result, out);
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
