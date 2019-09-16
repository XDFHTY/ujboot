package com.cj.spension.service.impl;

import com.cj.common.domain.DoctorModel;
import com.cj.core.domain.OldPager;
import com.cj.core.entity.DoctorCertificate;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.spension.mapper.SPDoctorInfoMapper;
import com.cj.spension.mapper.SPUserMapper;
import com.cj.spension.service.SPDoctorService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 医生账号管理业务实现层
 * @date： 2019/3/8 16:08
 **/
@Service
@Transactional
public class SPDoctorServiceImpl implements SPDoctorService {
    @Autowired
    private SPDoctorInfoMapper doctorInfoMapper;
    @Autowired
    private SPUserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页显示医生信息
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getDocPage(OldPager oldPager, HttpServletRequest request) {
        //查询条数
        oldPager.setRecordTotal(doctorInfoMapper.findDocInfoCount(oldPager));
        //查询类容
        List<DoctorModel> list = doctorInfoMapper.findDocInfoPage(oldPager);
        list.forEach(doctorModel -> {
            String s = "i"+doctorModel.getUser().getUserId()+"";
            doctorModel.setBoostate(stringRedisTemplate.hasKey(s));
        });
        oldPager.setContent(list);
        return oldPager;
    }

    /**
     * 根据id查询医生信息
     * @param userId
     * @return
     */
    @Override
    public DoctorModel getDocByID(Long userId,HttpServletRequest request) {
        DoctorModel doctorModel = doctorInfoMapper.findDocByID(userId);
        Long id = Long.valueOf(request.getHeader("id"));
        if(doctorInfoMapper.findIsBind(userId,id)>0){
            doctorModel.setIsBind(true);
        }else{
            doctorModel.setIsBind(false);
        }
        return doctorModel;
    }

    /**
     * 根据id删除
     * @param userId
     * @return
     */
    @Override
    public int deleteByID(Long userId) {
        return userMapper.deleteByIdL(userId);
    }

    /**
     * 修改密码
     * @param userId
     * @param pass
     * @return
     */
    @Override
    public int updateForPassByID(Long userId, String pass) {
        return userMapper.updateForPassById(userId,pass);
    }

    /**
     * 修改信息
     * @param doctorModel
     * @return
     */
    @Override
    public int updateForInfo(DoctorModel doctorModel) {
        return doctorInfoMapper.updateByPrimaryKeySelective(doctorModel.getDoctorInfo());
    }

    /**
     * 导出Excel
     * @param oldPager
     * @return
     */
    @Override
    public void exportExcel(OldPager oldPager, HttpServletResponse response, HttpServletRequest request) {
        List<DoctorModel> list = doctorInfoMapper.findDocInfo(oldPager);
        String fileName="医生信息";
        //String path = "C:/Users/User/Desktop/test/测试Eexle导出.xlsx";
        try {
            //设置输出文件地址和名字
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            OutputStream out = response.getOutputStream();
            response.setHeader("content-Type", "application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");
            //设置输出文件地址和名字
            List<List<String>> result = new ArrayList<>();
            //生成格式是xlsx可存储103万行数据，如果是xls则只能存不到6万行数据
            XSSFWorkbook workbook = new XSSFWorkbook();
            //医生账号信息
            //设置标题栏类容
            String[] headers = {"用户名","姓名","性别","职称","科室","头像","手机号","医师资格证","从业证","简介","状态","注册时间"};
            //设置数据
            for(DoctorModel doctorModel:list){
                List<String> rowData = new ArrayList<String>();
                //设置用户名
                rowData.add(doctorModel.getUser().getUserName());
                //设置姓名
                rowData.add(doctorModel.getDoctorInfo().getName());
                //设置性别
                rowData.add(doctorModel.getDoctorInfo().getSex());
                //设置职称
                rowData.add(doctorModel.getDoctorInfo().getTitle());
                //设置科室
                rowData.add(doctorModel.getDepartmentName());
                //设置头像
                rowData.add(doctorModel.getDoctorInfo().getHeadUrl());
                //设置手机号
                rowData.add(doctorModel.getDoctorInfo().getPhone());
                //设置医师资格证
                for(DoctorCertificate doctorCertificate : doctorModel.getDoctorCertificateList()){
                    if(doctorCertificate.getCertificateType().equals("1")){
                        rowData.add(doctorCertificate.getCertificateUrl());
                    }
                }
                //设置从业证
                for(DoctorCertificate doctorCertificate : doctorModel.getDoctorCertificateList()){
                    if(doctorCertificate.getCertificateType().equals("2")){
                        rowData.add(doctorCertificate.getCertificateUrl());
                    }
                }
                //设置简介
                rowData.add(doctorModel.getDepartmentName());
                //设置状态
                String s = "i"+doctorModel.getUser().getUserId()+"";
                if(stringRedisTemplate.hasKey(s)){
                    rowData.add("在线");
                }else{
                    rowData.add("离线");
                }
                //设置注册时间
                rowData.add(DateUtil.dateToStr(doctorModel.getUser().getCreateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "医生信息", headers, result, out);
            //将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("导出失败");
        }
    }
}
