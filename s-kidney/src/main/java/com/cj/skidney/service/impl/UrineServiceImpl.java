package com.cj.skidney.service.impl;

import com.cj.common.mapper.PublicMapper;
import com.cj.core.domain.OldPager;
import com.cj.core.entity.*;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.skidney.domain.InspectModel;
import com.cj.skidney.domain.UrineModel;
import com.cj.skidney.mapper.*;
import com.cj.skidney.service.KidneyCallImService;
import com.cj.skidney.service.UrineService;
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
 * @Description: 检查记录业务实现层
 * @date： 2019/3/13 10:27
 **/
@Service
@Transactional
public class UrineServiceImpl implements UrineService {

    @Autowired
    private UrineResultMapper urineResultMapper;
    @Autowired
    private UrineIdentificationMapper urineIdentificationMapper;
    @Autowired
    private UrineAbnormalMapper urineAbnormalMapper;
    @Autowired
    private SKUserInfoMapper userInfoMapper;
    @Autowired
    private UrineProteinMapper urineProteinMapper;
    @Autowired
    private BloodPressureMapper bloodPressureMapper;
    @Autowired
    private BloodCreatinineMapper bloodCreatinineMapper;
    @Autowired
    private KidneyCallImService kidneyCallImService;
    @Autowired
    private PublicMapper publicMapper;
    /**
     * 插入尿检记录
     * @param list
     * @return
     */
    @Override
    public List<Long> insertUrine(List<UrineModel> list) {
        List<Long> idList = new ArrayList<>();
        for (UrineModel urineModel : list) {
            //修改设备最后使用时间
            if(urineModel.getUrineResultVO().getBluetoothMac()!=null){
                publicMapper.putUseTime(urineModel.getUrineResultVO().getBluetoothMac(),
                        urineModel.getUrineResultVO().getInspectTime());
            }
            //插入尿检结果
            urineModel.getUrineResultVO().setUrineResultId(null);
            urineResultMapper.replaseSelective(urineModel.getUrineResultVO());
            //查询刚存入的尿检结果表id
            Long urineResultId = urineModel.getUrineResultVO().getUrineResultId();
            idList.add(urineResultId);
            urineModel.getUrineIdentification().setUrineResultId(urineResultId);
            urineModel.getUrineAbnormal().setUrineResultId(urineResultId);
            //插入机器标识
            urineIdentificationMapper.insertSelective(urineModel.getUrineIdentification());
            //插入异常记录
            urineAbnormalMapper.insertSelective(urineModel.getUrineAbnormal());
        }
        for (UrineModel urineModel : list) {
            String alert = findUa(urineModel.getUrineAbnormal());
            //推送异常信息给医生
            if (alert != "") {
                Map<String, Object> map0 = userInfoMapper.findByUserId(urineModel.getUrineResultVO().getUserId());
                if (map0 != null) {
                    String name = map0.get("name") + "";
                    alert = "患者“" + name +
                            "”于" + DateUtil.dateToStr(urineModel.getUrineResultVO().getInspectTime(), DateUtil.YYYY_MM_DDHHMMSS) +
                            "的监测数据指标“" + alert + "”处于异常状态";
                    Map<String, Object> map = new HashMap<>();
                    //通知内容
                    Map<String, String> map1 = new HashMap<>();
                    //1-用药 2-系统 3-异常提醒 4-绑定通知
                    map1.put("type", "3");
                    map1.put("urineResultId", urineModel.getUrineResultVO().getUrineResultId() + "");
                    //1-个人端 2-医生端
                    map.put("appType", "2");
                    map.put("days", "1");
                    //设置标题
                    map.put("alert", alert);
                    map.put("params", map1);
                    List<String> list1 = new ArrayList<>();
                    //设置通知医生id
                    list1.add(map0.get("id") + "");
                    map.put("alias", list1);
                    new Thread(()->{
                        kidneyCallImService.buildPushObjectAllAliasAlert(map);
                    }).start();
                }
            }
        }
        return idList;
    }


    /**
     * 拼接尿检异常项
     * @param urineAbnormal
     * @return
     */
    public String findUa(UrineAbnormal urineAbnormal){
        String alert = "";
        if(urineAbnormal.getAbnormalAcr().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "ACR";
        }
        if(urineAbnormal.getAbnormalLeu().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "白细胞";
        }
        if(urineAbnormal.getAbnormalNit().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "亚硝酸盐";
        }
        if(urineAbnormal.getAbnormalUbg().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "尿胆原";
        }
        if(urineAbnormal.getAbnormalPro().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "蛋白质";
        }
        if(urineAbnormal.getAbnormalPh().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "PH值";
        }
        if(urineAbnormal.getAbnormalBld().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "潜血";
        }
        if(urineAbnormal.getAbnormalSg().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "比重";
        }
        if(urineAbnormal.getAbnormalKet().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "酮体";
        }
        if(urineAbnormal.getAbnormalBil().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "胆红素";
        }
        if(urineAbnormal.getAbnormalGlu().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "葡萄糖";
        }
        if(urineAbnormal.getAbnormalVc().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "维生素c";
        }
        if(urineAbnormal.getAbnormalMa().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "微量白蛋白";
        }
        if(urineAbnormal.getAbnormalCre().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "肌酐";
        }
        if(urineAbnormal.getAbnormalCa().equals(1)){
            if(!alert.equals("")){
                alert = alert + "、";
            }
            alert = alert + "钙";
        }
        return alert;
    }
    /**
     * 插入24小时尿蛋白记录
     * @param urineProtein
     * @return
     */
    @Override
    public int insertUrineProtein(UrineProtein urineProtein) {
        return urineProteinMapper.insertSelective(urineProtein);
    }
    /**
     * 插入血压记录
     * @param bloodPressure
     * @return
     */
    @Override
    public int insertBloodPressure(BloodPressure bloodPressure) {
        //修改设备最后使用时间
        if(bloodPressure.getBluetoothMac()!=null){
            publicMapper.putUseTime(bloodPressure.getBluetoothMac(),
                    bloodPressure.getCreateTime());
        }
        return bloodPressureMapper.insertSelective(bloodPressure);
    }
    /**
     * 插入血肌酐记录
     * @param bloodCreatinine
     * @return
     */
    @Override
    public int insertBloodCreatinine(BloodCreatinine bloodCreatinine) {
        return bloodCreatinineMapper.insertSelective(bloodCreatinine);
    }

    /**
     * 查询检查记录列表
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getInsM(OldPager oldPager) {
        if(oldPager.getParameters().get("minAge")!=null){
            oldPager.getParameters().put("minAge",ageToYear(oldPager.getParameters().get("minAge")));
        }
        if(oldPager.getParameters().get("maxAge")!=null){
            oldPager.getParameters().put("maxAge",ageToYear(oldPager.getParameters().get("maxAge")));
        }
        //查询条数
        oldPager.setRecordTotal(userInfoMapper.findUserCount(oldPager));
        //查询记录
        List<InspectModel> list = userInfoMapper.findUserPage(oldPager);
        oldPager.setContent(list);
        return oldPager;
    }

    /**
     * 查询检查结果记录列表
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getResult(OldPager oldPager) {
        String type = oldPager.getParameters().get("type").toString();
        if(type.equals("0")){
            //分页查询尿检记录
            //查询记录
            oldPager.setContent(urineResultMapper.findUrePage(oldPager));
            //查询条数
            oldPager.setRecordTotal(urineResultMapper.findUreCount(oldPager));
        }else if (type.equals("1")){
            //分页查询血压检记录
            //查询记录
            oldPager.setContent(bloodPressureMapper.findBloodPressurePage(oldPager));
            //查询条数
            oldPager.setRecordTotal(bloodPressureMapper.findBloodPressureCount(oldPager));
        }else if (type.equals("2")){
            //分页查询血肌酐检记录
            //查询记录
            oldPager.setContent(bloodCreatinineMapper.findBloodCreatininePage(oldPager));
            //查询条数
            oldPager.setRecordTotal(bloodCreatinineMapper.findBloodCreatinineCount(oldPager));
        }else if(type.equals("3")){
            //分页查询24小时尿蛋白记录
            //查询记录
            oldPager.setContent(urineProteinMapper.findUrineProteinPage(oldPager));
            //查询条数
            oldPager.setRecordTotal(urineProteinMapper.findUrineProteinCount(oldPager));
        }
        return oldPager;
    }

    /**
     * 根据id查询检查结果
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getResultById(OldPager oldPager) {
        if(oldPager.getParameters().get("type").equals("0")){
            oldPager.setContent(urineResultMapper.findById(oldPager));
        }else if (oldPager.getParameters().get("type").equals("1")){
            oldPager.setContent(bloodPressureMapper.selectByPrimaryKey((Long) oldPager.getParameters().get("id")));
        }else if (oldPager.getParameters().get("type").equals("2")){
            oldPager.setContent(bloodCreatinineMapper.selectByPrimaryKey((Long) oldPager.getParameters().get("id")));
        }else if (oldPager.getParameters().get("type").equals("3")){
            oldPager.setContent(urineProteinMapper.selectByPrimaryKey((Long) oldPager.getParameters().get("id")));
        }else{
            oldPager.setContent("输入正确的检查类型");
        }
        return oldPager;
    }

    /**
     * 导出检查结果
     * @param oldPager
     * @return
     */
    @Override
    public void export(OldPager oldPager, HttpServletResponse response) {
        String type = oldPager.getParameters().get("type").toString();
        if(type.equals("0")){
            //尿检记录
            exportUrine(oldPager,response);
        }else if (type.equals("1")){
            //血压
            exportBloodPressure(oldPager,response);
        }else if (type.equals("2")){
            //血肌酐
            exportBloodCreatinine(oldPager,response);
        }else if (type.equals("3")){
            //24小时尿蛋白
            exportUrineProtein(oldPager,response);
        }else{
            System.out.println("输入导出类型");
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
    /**
     * 导出检查记录列表
     * @param oldPager
     * @return
     */
    @Override
    public void exportIspect(OldPager oldPager, HttpServletResponse response) {
        if(oldPager.getParameters().get("minAge")!=null){
            oldPager.getParameters().put("minAge",ageToYear(oldPager.getParameters().get("minAge")));
        }
        if(oldPager.getParameters().get("maxAge")!=null){
            oldPager.getParameters().put("maxAge",ageToYear(oldPager.getParameters().get("maxAge")));
        }
        List<InspectModel> list = userInfoMapper.findUser(oldPager);
        String fileName="检查记录列表";
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
            String[] headers = {"姓名","性别","年龄","所患疾病","使用次数","异常次数"};
            //设置数据
            for(InspectModel inspectModel:list){
                List<String> rowData = new ArrayList<String>();
                //设置姓名
                rowData.add(inspectModel.getUserInfo().getName());
                //设置性别
                rowData.add(inspectModel.getUserInfo().getSex());
                //设置年龄
                Integer age = inspectModel.getUserInfo().getAge();
                if(age != null){
                    rowData.add(age.toString());
                }else{
                    rowData.add("null");
                }
                //设置所患疾病
                rowData.add(inspectModel.getDisease());
                //设置使用次数
                if(inspectModel.getInspectSum()!=null){
                    rowData.add(inspectModel.getInspectSum().toString());
                }else{
                    rowData.add("0");
                }
                //设置异常次数
                rowData.add(inspectModel.getAbnormalSum().toString());
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "检查记录信息", headers, result, out);
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
     * 导出尿检记录列表
     * @param oldPager
     * @return
     */
    public void exportUrine(OldPager oldPager, HttpServletResponse response) {
        List<UrineModel> list = urineResultMapper.findUre(oldPager);
        String fileName="尿检记录列表";
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
            String[] headers = {"检测时间","ACR","白细胞","亚硝酸盐","尿胆原","蛋白质","PH值","潜血",
                    "比重","酮体","胆红素","葡萄糖","维生素C","微量白蛋白","肌酐","钙"};
            //设置数据
            for(UrineModel urineModel:list){
                List<String> rowData = new ArrayList<String>();
                //设置检测时间
                rowData.add(DateUtil.dateToStr(urineModel.getUrineResultVO().getInspectTime(),DateUtil.YYYY_MM_DDHHMMSS));
                //设置ACR
                if(urineModel.getUrineResultVO().getResultAcr() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultAcr().toString());
                }else{
                    rowData.add("null");
                }
                //设置白细胞
                if(urineModel.getUrineResultVO().getResultLeu() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultLeu().toString());
                }else{
                    rowData.add("null");
                }
                //设置亚硝酸盐
                rowData.add(urineModel.getUrineResultVO().getResultNit());
                //设置尿胆原
                if(urineModel.getUrineResultVO().getResultUbg() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultUbg().toString());
                }else{
                    rowData.add("null");
                }
                //设置蛋白质
                if(urineModel.getUrineResultVO().getResultPro() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultPro().toString());
                }else{
                    rowData.add("null");
                }
                //设置PH值
                if(urineModel.getUrineResultVO().getResultPh() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultPh().toString());
                }else{
                    rowData.add("null");
                }
                //设置潜血
                if(urineModel.getUrineResultVO().getResultBil() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultBil().toString());
                }else{
                    rowData.add("null");
                }
                //设置比重
                if(urineModel.getUrineResultVO().getResultSg() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultSg().toString());
                }else{
                    rowData.add("null");
                }
                //设置酮体
                if(urineModel.getUrineResultVO().getResultKet() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultKet().toString());
                }else{
                    rowData.add("null");
                }
                //设置胆红素
                if(urineModel.getUrineResultVO().getResultBil() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultBil().toString());
                }else{
                    rowData.add("null");
                }
                //设置葡萄糖
                if(urineModel.getUrineResultVO().getResultGlu() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultGlu());
                }else{
                    rowData.add("null");
                }
                //设置维生素C
                if(urineModel.getUrineResultVO().getResultVc() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultVc().toString());
                }else{
                    rowData.add("null");
                }
                //设置微量白蛋白
                if(urineModel.getUrineResultVO().getResultMa() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultMa().toString());
                }else{
                    rowData.add("null");
                }
                //设置肌酐
                if(urineModel.getUrineResultVO().getResultCre() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultCre().toString());
                }else{
                    rowData.add("null");
                }
                //设置钙
                if(urineModel.getUrineResultVO().getResultCa() != null){
                    rowData.add(urineModel.getUrineResultVO().getResultCa().toString());
                }else{
                    rowData.add("null");
                }
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "尿检记录信息", headers, result, out);
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
     * 导出血肌酐记录列表
     * @param oldPager
     * @return
     */
    public void exportBloodCreatinine(OldPager oldPager, HttpServletResponse response) {
        List<BloodCreatinine> list = bloodCreatinineMapper.findBloodCreatinine(oldPager);
        String fileName="血肌酐记录列表";
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
            String[] headers = {"创建时间","血肌酐"};
            //设置数据
            for(BloodCreatinine bloodCreatinine:list){
                List<String> rowData = new ArrayList<String>();
                //设置创建时间
                rowData.add(DateUtil.dateToStr(bloodCreatinine.getCreateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                //设置血肌酐
                if(bloodCreatinine.getSc()!=null){
                    rowData.add(bloodCreatinine.getSc().toString());
                }else{
                    rowData.add("null");
                }
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "血肌酐记录信息", headers, result, out);
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
     * 导出血压记录列表
     * @param oldPager
     * @return
     */
    public void exportBloodPressure(OldPager oldPager, HttpServletResponse response) {
        List<BloodPressure> list = bloodPressureMapper.findBloodPressure(oldPager);
        String fileName="血压记录列表";
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
            String[] headers = {"填写时间","收缩压","舒张压","心跳（次/分）"};
            //设置数据
            for(BloodPressure bloodPressure:list){
                List<String> rowData = new ArrayList<String>();
                //设置填写时间
                rowData.add(DateUtil.dateToStr(bloodPressure.getCreateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                //设置收缩压
                if(bloodPressure.getSystolicPressure()!=null){
                    rowData.add(bloodPressure.getSystolicPressure().toString());
                }else{
                    rowData.add("null");
                }
                //设置舒张压
                if(bloodPressure.getDiastolicPressure()!=null){
                    rowData.add(bloodPressure.getDiastolicPressure().toString());
                }else{
                    rowData.add("null");
                }
                //设置 心跳（次/分）
                if(bloodPressure.getPulse()!=null){
                    rowData.add(bloodPressure.getPulse().toString());
                }else{
                    rowData.add("null");
                }
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "血压记录信息", headers, result, out);
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
     * 导出24小时尿蛋白记录列表
     * @param oldPager
     * @return
     */
    public void exportUrineProtein(OldPager oldPager, HttpServletResponse response) {
        List<UrineProtein> list = urineProteinMapper.findUrineProtein(oldPager);
        String fileName="24小时尿蛋白记录列表";
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
            String[] headers = {"创建时间","蛋白质量g/d","尿量"};
            //设置数据
            for(UrineProtein urineProtein:list){
                List<String> rowData = new ArrayList<String>();
                //设置创建时间
                rowData.add(DateUtil.dateToStr(urineProtein.getCreateTime(),DateUtil.YYYY_MM_DDHHMMSS));
                //设置蛋白质量g/d
                if(urineProtein.getProtein()!=null){
                    rowData.add(urineProtein.getProtein().toString());
                }else{
                    rowData.add("null");
                }
                //设置尿量
                if(urineProtein.getAmount()!=null){
                    rowData.add(urineProtein.getAmount().toString());
                }else{
                    rowData.add("null");
                }
                result.add(rowData);
            }
            exportExcelUtil.exportExcel(workbook, 0, "24小时尿蛋白记录信息", headers, result, out);
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
