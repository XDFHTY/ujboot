package com.cj.sbasic.service.impl;

import com.cj.common.utils.excle.exportExcelUtil;
import com.cj.core.util.timeUtil.DateUtil;
import com.cj.core.domain.OldPager;
import com.cj.sbasic.mapper.SBDoctorInfoMapper;
import com.cj.sbasic.mapper.SBFeedbackMapper;
import com.cj.sbasic.mapper.SBUserInfoMapper;
import com.cj.sbasic.service.FeedbackService;
import com.cj.sbasic.vo.FeedbackTemp;
import com.cj.sbasic.vo.FeedbackVO;
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
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private SBFeedbackMapper feedbackMapper;
    @Autowired
    private SBUserInfoMapper userInfoMapper;
    @Autowired
    private SBDoctorInfoMapper doctorInfoMapper;

    @Override
    public OldPager getFeedbackPage(OldPager oldPager) {
        List<FeedbackTemp> feedbackTempList = feedbackMapper.getFeedbackPage(oldPager);
        List<FeedbackVO> feedbackVOList = new ArrayList<>();
        for (FeedbackTemp feedbackTemp : feedbackTempList){
            Long userId = feedbackTemp.getUserId();
            String userType = feedbackTemp.getUserType();
            String name = "";//用户姓名
            if ("1".equals(userType)){
                name = userInfoMapper.selectByUserId(userId).getName();
            } else if ("0".equals(userType)){
                name = "";
            }else{
                name = doctorInfoMapper.selectByUserId(userId).getName();
            }
            feedbackVOList.add(feedbackTemp.convertToFeedBackVO(name));
        }
        //查询总条数
        oldPager.setRecordTotal(feedbackMapper.getFeedbackCount(oldPager));
        //查询数据
        oldPager.setContent(feedbackVOList);
        return oldPager;
    }

    @Override
    public int updateFeedbackStateById(Long id) {
        return feedbackMapper.updateFeedbackStateById(id);
    }

    /**
     * 导出
     * @param oldPager
     * @param response
     * @return
     */
    @Override
    public String exportFeedback(OldPager oldPager, HttpServletResponse response) {
        List<FeedbackVO> feedbackVOList = (List<FeedbackVO>) oldPager.getContent();
        try {
            OutputStream out = response.getOutputStream();

            String fileName = "投诉信息列表";//文件名
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());

            response.setHeader("content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "; filename*=utf-8''" + fileName);
            response.setCharacterEncoding("UTF-8");

            List<List<String>> result = new ArrayList<>();
            //生成格式是xlsx可存储103万行数据，如果是xls则只能存不到6万行数据
            XSSFWorkbook workbook = new XSSFWorkbook();
            //设置标题栏类容
            String[] headers = {"用户名","姓名","邮箱","手机号","投诉类别","投诉说明","状态","申请时间"};
            //设置数据
            if(feedbackVOList!=null){
                for(FeedbackVO feedbackVO:feedbackVOList){
                    List<String> rowData = new ArrayList<String>();
                    //设置用户名
                    rowData.add(feedbackVO.getUsername());
                    //设置姓名
                    rowData.add(feedbackVO.getName());
                    //设置邮箱
                    rowData.add(feedbackVO.getEmail());
                    //设置手机号
                    rowData.add(feedbackVO.getPhone());
                    //设置投诉类别
                    if ("0".equals(feedbackVO.getFeedbackType())){
                        rowData.add("设备");
                    } else if ("1".equals(feedbackVO.getFeedbackType())){
                        rowData.add("医生");
                    } else if ("2".equals(feedbackVO.getFeedbackType())){
                        rowData.add("平台");
                    } else if("".equals(feedbackVO.getFeedbackType())){
                        rowData.add("");
                    }
                    //设置投诉说明
                    rowData.add(feedbackVO.getContent());
                    //设置状态
                    if ("0".equals(feedbackVO.getState())){
                        rowData.add("未处理");
                    } else if ("1".equals(feedbackVO.getState())){
                        rowData.add("已处理");
                    } else if ("".equals(feedbackVO.getState())){
                        rowData.add("");
                    }
                    //设置申请时间
                    rowData.add(DateUtil.dateToStr(feedbackVO.getTime(),DateUtil.YYYY_MM_DDHHMMSS));
                    result.add(rowData);
                }
            }
            exportExcelUtil.exportExcel(workbook, 0, "投诉信息列表", headers, result, out);
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
