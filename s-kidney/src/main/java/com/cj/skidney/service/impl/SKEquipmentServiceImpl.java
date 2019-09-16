package com.cj.skidney.service.impl;

import com.cj.core.domain.OldPager;
import com.cj.core.entity.UserEquipment;
import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.skidney.domain.EquipmentModel;
import com.cj.skidney.mapper.SKUserEquipmentMapper;
import com.cj.skidney.mapper.SKUserInfoMapper;
import com.cj.skidney.service.SKEquipmentService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： 刘磊
 * @Description: 设备管理业务实现层
 * @date： 2019/3/14 18:35
 **/
@Service
public class SKEquipmentServiceImpl implements SKEquipmentService {
    @Autowired
    private SKUserInfoMapper userInfoMapper;
    @Autowired
    private SKUserEquipmentMapper userEquipmentMapper;

    /**
     * 分页查询设备信息
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getEquipmentModelPage(OldPager oldPager) {
        //查询记录
        oldPager.setContent(userInfoMapper.findEquipmentModelPage(oldPager));
        //查询条数
        oldPager.setRecordTotal(userInfoMapper.findEquipmentModelCount(oldPager));
        return oldPager;
    }

    /**
     * 分页查询历史绑定记录
     * @param oldPager
     * @return
     */
    @Override
    public OldPager getHistoricalPage(OldPager oldPager) {
        //查询记录
        oldPager.setContent(userEquipmentMapper.findHistoricalPage(oldPager));
        //查询条数
        oldPager.setRecordTotal(userEquipmentMapper.findHistoricalCount(oldPager));
        return oldPager;
    }

    /**
     * 导出绑定设备信息
     * @param oldPager
     * @return
     */
    @Override
    public void exportEquipmentModel(OldPager oldPager, HttpServletResponse response) {
        List<EquipmentModel> list = userInfoMapper.findEquipmentModel(oldPager);
        String fileName="绑定设备信息";
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
            String[] headers = {"姓名","年龄","性别","设备类别","设备型号","地址","设备SN","绑定时间"};
            //设置数据
            for(EquipmentModel equipmentModel:list){
                for(UserEquipment userEquipment:equipmentModel.getUeList()){
                    List<String> rowData = new ArrayList<String>();
                    //设置姓名
                    rowData.add(equipmentModel.getUserInfo().getName());
                    //设置年龄
                    if(equipmentModel.getUserInfo().getAge() != null){
                        rowData.add(equipmentModel.getUserInfo().getAge().toString());
                    }else {
                        rowData.add("null");
                    }
                    //设置性别
                    rowData.add(equipmentModel.getUserInfo().getSex());
                    //设置设备类别
                    rowData.add(userEquipment.getEquipmentType());
                    //设置设备型号
                    rowData.add(userEquipment.getEquipmentModel());
                    //设置地址
                    String province = equipmentModel.getProvince();
                    String city = equipmentModel.getCity();
                    String area = equipmentModel.getArea();
                    rowData.add(province+city+area);
                    //设置设备SN
                    rowData.add(userEquipment.getEquipmentSn());
                    //设置绑定时间
                    rowData.add(DateUtil.dateToStr(userEquipment.getStartTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "绑定设备信息", headers, result, out);
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
     * 导出设备历史绑定记录
     * @param oldPager
     * @return
     */
    @Override
    public void exportHistorical(OldPager oldPager, HttpServletResponse response) {
        List<UserEquipment> list = userEquipmentMapper.findHistorical(oldPager);
        String fileName="历史绑定记录";
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
            String[] headers = {"设备类型","设备型号","设备SN","绑定时间","解绑时间"};
            //设置数据
            if(list!=null){
                for(UserEquipment userEquipment:list){
                    List<String> rowData = new ArrayList<String>();
                    //设置设备类型
                    rowData.add(userEquipment.getEquipmentType());
                    //设置设备型号
                    rowData.add(userEquipment.getEquipmentModel());
                    //设置设备SN
                    rowData.add(userEquipment.getEquipmentSn());
                    //设置绑定时间
                    rowData.add(DateUtil.dateToStr(userEquipment.getStartTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    //设置解绑时间
                    rowData.add(DateUtil.dateToStr(userEquipment.getEndTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "设备绑定记录", headers, result, out);
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
