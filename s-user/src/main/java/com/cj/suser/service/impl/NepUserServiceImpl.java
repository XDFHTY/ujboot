package com.cj.suser.service.impl;

import com.cj.common.domain.VoScore;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.domain.ApiResult;
import com.cj.core.domain.Pager;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.FnuModel;
import com.cj.suser.domain.FpuModel;
import com.cj.suser.domain.TModel;
import com.cj.suser.mapper.NepUserMapper;
import com.cj.suser.service.NepUserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 肾病医生管理业务实现层
 * @date： 2019/6/28 18:51
 **/
@Service
@Transactional
public class NepUserServiceImpl implements NepUserService {


    @Autowired
    private NepUserMapper nepUserMapper;
    @Autowired
    private UserCallShopServiceImpl userCallShopService;

    /**
     * 分页查询医生列表
     * @param pager
     * @return
     */
    @Override
    public Pager findDoctor(Pager pager) {
        List<FnuModel> list = nepUserMapper.findDoctor(pager);
//        for(FnuModel fnuModel:list){
//            fnuModel.getDcList();
//        }
        pager.setRecords(list);
        return pager;
    }

    /**
     * 分页查询患者生列表
     * @param pager
     * @return
     */
    @Override
    public Pager findUserByDoctorId(Pager pager) {
        Map<String,String> map = (Map<String, String>) pager.getParameters();
        if(map.get("minAge")!=null){
            map.put("minAge",ageToYear(map.get("minAge")));
        }
        if(map.get("maxAge")!=null){
            map.put("maxAge",ageToYear(map.get("maxAge")));
        }
        pager.setParameters(map);
        pager.setRecords(nepUserMapper.findUserByDoctorId(pager));
        return pager;
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
    /**
     * 根据医生id查询医生团队
     * @param doctorId
     * @return
     */
    @Override
    public TModel findTeamByDoctorId(Long doctorId) {
        TModel tModel = nepUserMapper.findTeamByDoctorId(doctorId);
        if(tModel!=null){
            ApiResult apiResult2 = userCallShopService.findScore(tModel.getTeamCaptainId());
            Gson gson = JsonUtil.gson;
            VoScore voScore = gson.fromJson(gson.toJson(apiResult2.getData()),VoScore.class);
            if (voScore != null) {
                //设置咨询量
                tModel.setScore(voScore.getConsultNum()+"");
            }
        }
        return tModel;
    }

    /**
     * 导出
     * @param map
     * @return
     */
    @Override
    public int export(Map map, HttpServletResponse response) {
        List<FnuModel> list = nepUserMapper.findAll(map);
        //医生类型
        // 29-专科医生
        // 30-家庭医生
        // 32-营养师
        // 33-乡干部
        // 34-卫健委干部
        // 35-离退休干部
        // 36-护士
        // 37-健康管理师
        Double d = (Double) map.get("doctorType");
        Integer i = d.intValue();
        String type = i.toString();
        String fileName="关注端用户列表";
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
            if(type.equals(29)  || type.equals(30) || type.equals(32)){
                String[] headers = {"头像","用户名","姓名","性别","职称","科室","所属医院","咨询价格/次"};
                //设置数据
                for(FnuModel fnuModel:list){
                    List<String> rowData = new ArrayList<String>();
                    //设置头像
                    rowData.add(fnuModel.getHeadUrl());
                    //设置用户名
                    rowData.add(fnuModel.getUserName());
                    //设置姓名
                    rowData.add(fnuModel.getName());
                    //设置性别
                    rowData.add(fnuModel.getSex());
                    //设置职称
                    rowData.add(fnuModel.getTitle());
                    //设置科室
                    rowData.add(fnuModel.getDepartments());
                    //设置医院
                    rowData.add(fnuModel.getHospital());
                    //设置价格
                    rowData.add(fnuModel.getPrice());
                    result.add(rowData);
                }
                exportExcelUtil.exportExcel(workbook, 0, "关注端用户列表", headers, result, out);
            }else if(type.equals(33) || type.equals(34) || type.equals(35)){
                String[] headers = {"头像","用户名","姓名","性别","所在地区","所在单位"};
                //设置数据
                for(FnuModel fnuModel:list){
                    List<String> rowData = new ArrayList<String>();
                    //设置头像
                    rowData.add(fnuModel.getHeadUrl());
                    //设置用户名
                    rowData.add(fnuModel.getUserName());
                    //设置姓名
                    rowData.add(fnuModel.getName());
                    //设置性别
                    rowData.add(fnuModel.getSex());
                    //设置地区
                    rowData.add(fnuModel.getProvince()+fnuModel.getCity()+fnuModel.getArea());
                    //设置单位
                    rowData.add(fnuModel.getCompany());
                    result.add(rowData);
                }
                exportExcelUtil.exportExcel(workbook, 0, "关注端用户列表", headers, result, out);
            }else{
                String[] headers = {"头像","用户名","姓名","性别","职称","科室","所属医院"};
                //设置数据
                for(FnuModel fnuModel:list){
                    List<String> rowData = new ArrayList<String>();
                    //设置头像
                    rowData.add(fnuModel.getHeadUrl());
                    //设置用户名
                    rowData.add(fnuModel.getUserName());
                    //设置姓名
                    rowData.add(fnuModel.getName());
                    //设置性别
                    rowData.add(fnuModel.getSex());
                    //设置职称
                    rowData.add(fnuModel.getTitle());
                    //设置科室
                    rowData.add(fnuModel.getDepartments());
                    //设置医院
                    rowData.add(fnuModel.getHospital());
                    result.add(rowData);
                }
                exportExcelUtil.exportExcel(workbook, 0, "关注端用户列表", headers, result, out);
            }
            //将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
