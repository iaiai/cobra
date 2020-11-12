package com.iaiai.cobra.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core.util
 * Author: iaiai
 * Create Time: 2020-03-24 09:28
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@Slf4j
public class ExcelFormatUtil {

    /**
     * 设置报表头样式
     * @param workbook
     * @return
     */
    public static CellStyle headSytle(SXSSFWorkbook workbook){
        // 设置style1的样式，此样式运用在第二行
        CellStyle style1 = workbook.createCellStyle();// cell样式
        // 设置单元格背景色，设置单元格背景色以下两句必须同时设置
        style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 设置填充样式
        style1.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());// 设置填充色
        // 设置单元格上、下、左、右的边框线
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        Font font1 = workbook.createFont();// 创建一个字体对象
        font1.setBold(true);
//        font1.setBoldweight((short) 10);// 设置字体的宽度
        font1.setFontHeightInPoints((short) 10);// 设置字体的高度
//        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        style1.setFont(font1);// 设置style1的字体
        style1.setWrapText(true);// 设置自动换行
        style1.setAlignment(HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
        return style1;
    }
    /**
     * 设置报表体样式
     * @param wb
     * @return
     */
    public static CellStyle contentStyle(SXSSFWorkbook wb){
        // 设置style1的样式，此样式运用在第二行
        CellStyle style1 = wb.createCellStyle();// cell样式
        // 设置单元格上、下、左、右的边框线
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setWrapText(true);// 设置自动换行
        style1.setAlignment(HorizontalAlignment.LEFT);// 设置单元格字体显示居中（左右方向）
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
        return style1;
    }
    /**
     * 设置报表标题样式
     * @param workbook
     * @return
     */
    public static HSSFCellStyle titleSytle(HSSFWorkbook workbook,short color,short fontSize){
        // 设置style1的样式，此样式运用在第二行
        HSSFCellStyle style1 = workbook.createCellStyle();// cell样式
        // 设置单元格背景色，设置单元格背景色以下两句必须同时设置
        //style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 设置填充样式
        //short fcolor = color;
        if(color != HSSFColor.HSSFColorPredefined.WHITE.getIndex()){
            style1.setFillForegroundColor(color);// 设置填充色
        }
        // 设置单元格上、下、左、右的边框线
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        HSSFFont font1 = workbook.createFont();// 创建一个字体对象
        font1.setBold(true);
//        font1.setBoldweight(fontSize);// 设置字体的宽度
        font1.setFontHeightInPoints(fontSize);// 设置字体的高度
//        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
        style1.setFont(font1);// 设置style1的字体
        style1.setWrapText(true);// 设置自动换行
        style1.setAlignment(HorizontalAlignment.CENTER);// 设置单元格字体显示居中（左右方向）
        style1.setVerticalAlignment(VerticalAlignment.CENTER);// 设置单元格字体显示居中(上下方向)
        return style1;
    }
    /**
     *设置表头
     * @param sheet
     */
    public static void initTitleEX(SXSSFSheet sheet, CellStyle header,String title[],int titleLength[]) {
        SXSSFRow row0 = sheet.createRow(0);
//        row0.setHeight((short) 800);  //行高
        for(int j = 0;j<title.length; j++) {
            SXSSFCell cell = row0.createCell(j);
            //设置每一列的字段名
            cell.setCellValue(title[j]);
            cell.setCellStyle(header);
            sheet.setColumnWidth(j, titleLength[j]);
        }
    }

    //导出文件
    public static<T> ResponseEntity<byte[]> export(
            List<T> list,   //数据
            String filename,    //文件名
            String[] fields,    //标题
            int[] fieldsWidth,    //标题宽度
            Callback<T> callback    //回调方法
    ) throws IOException {
        InputStream inputStream = exportData(list,fields,fieldsWidth,callback);

//        String filename = "用户_"+DateFormatUtils.format(new Date(),"yyyy_MM_dd_HH_mm_ss")+".xls";   //文件名称,带后缀名
        HttpHeaders header = new HttpHeaders();
        String fileSuffix = filename.substring(filename.lastIndexOf('.') + 1);
        fileSuffix = fileSuffix.toLowerCase();

        Map<String, String> arguments = new HashMap<String, String>();
        arguments.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        arguments.put("xls", "application/vnd.ms-excel");

        String contentType = arguments.get(fileSuffix);
        header.add("Content-Type", (org.springframework.util.StringUtils.hasText(contentType) ? contentType : "application/x-download"));
        if(inputStream!=null && inputStream.available()!=0){
            header.add("Content-Length", String.valueOf(inputStream.available()));
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(filename, "UTF-8"));
            header.add("filename",URLEncoder.encode(filename, "UTF-8"));
            byte[] bs = IOUtils.toByteArray(inputStream);
            log.info(">>>>>>>>>>>>>>>>>>>>结束下载文件-有记录>>>>>>>>>>");
            log.info(">>>>>>>>>>结束导出excel>>>>>>>>>>");
            return new ResponseEntity<>(bs, header, HttpStatus.OK);
        }else{
            String string="数据为空";
            header.add("Content-Length", "0");
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(filename, "UTF-8"));
            log.info(">>>>>>>>>>>>>>>>>>>>结束下载文件-无记录>>>>>>>>>>");
            log.info(">>>>>>>>>>结束导出excel>>>>>>>>>>");
            return new ResponseEntity<>(string.getBytes(), header, HttpStatus.OK);
        }
    }

    private static<T> InputStream exportData(List<T> list,String[] fields,int[] fieldsWidth,Callback<T> callback) {
        log.info(">>>>>>>>>>>>>>>>>>>>开始进入导出方法>>>>>>>>>>");
        ByteArrayOutputStream output = null;
        InputStream inputStream1 = null;
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);// 保留1000条数据在内存中
        SXSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
        CellStyle style = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 设置表头样式
        ExcelFormatUtil.initTitleEX(sheet, header, fields, fieldsWidth);
        log.info(">>>>>>>>>>>>>>>>>>>>表头样式设置完成>>>>>>>>>>");
        if (list != null && list.size() > 0) {
            log.info(">>>>>>>>>>>>>>>>>>>>开始遍历数据组装单元格内容>>>>>>>>>>");
            for (int i = 0; i < list.size(); i++) {
                SXSSFRow row = sheet.createRow(i + 1);

                callback.item(row,style,list.get(i));
            }
            log.info(">>>>>>>>>>>>>>>>>>>>结束遍历数据组装单元格内容>>>>>>>>>>");
        }
        try {
            output = new ByteArrayOutputStream();
            wb.write(output);
            inputStream1 = new ByteArrayInputStream(output.toByteArray());
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                    if (inputStream1 != null)
                        inputStream1.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStream1;
    }

    public interface Callback<T>{
        void item(SXSSFRow row,CellStyle cellStyle,T t);
    }

}
