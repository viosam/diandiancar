package com.diandiancar.demo.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class POIExcelExport {


    public void wirteExcel(HttpServletResponse response) {

        //对应的bean字段
        String titleColumn[] = {"name"};
        //excel表头名
            String titleName[] = {"用户姓名"};
        //表头宽度
//    int titleSize[] = {20,20,20,20,10};
        String testDate1 = "test1";
        String testDate2 = "test2";
        List<String> list = new ArrayList<String>();
        list.add(testDate1);
        list.add(testDate2);

        String sheetName = "wealfare_activity";
        String fileDir = "F:\\wealfareActivity.xls";

        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        //新建文件
        OutputStream out = null;
        try {
            if (fileDir != null) {
                //有文件路径
                out = new FileOutputStream(fileDir);
            } else {
                //否则，直接写到输出流中
                out = response.getOutputStream();//获取响应对象的输出流

                //解决火狐下载文件名乱码
//                String agent = request.getHeader("USER-AGENT");
//                if (agent != null && agent.indexOf("MSIE") == -1) {// FF
//                    String newFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
//                    response.setHeader("Content-Disposition", "attachment; filename=" + newFileName);
//                } else { // IE
//                    response.setContentType("application/x-msdownload");
//                    response.setHeader("Content-Disposition", "attachment; filename="
//                            + URLEncoder.encode(fileName, "UTF-8"));
//                }
              /*  response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(fileName, "UTF-8"));*/
            }

            //写入excel的表头
            Row titleNameRow = workbook.getSheet(sheetName).createRow(0);
            for(int i = 0;i < titleName.length;i++){
                Cell cell = titleNameRow.createCell(i);
                cell.setCellValue(titleName[i]);
            }
            //数据写入到excel中
            if (list != null && list.size() > 0) {
                System.out.println(list.size()+"list=========size");
                if (titleColumn.length > 0) {
                    for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
                        Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex+1);
                        for (int columnIndex = 0; columnIndex < titleColumn.length; columnIndex++) {
                            String title = titleColumn[columnIndex].trim();
                            if (!"".equals(title)) {  //字段不为空
                                Cell cell = dataRow.createCell(columnIndex);
                                if (list.get(rowIndex) != null && !"".equals(list.get(rowIndex))) {//判断数据是否为空，为空不填入
                                    cell.setCellValue(list.get(rowIndex));
                                }
                            }
                        }
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}