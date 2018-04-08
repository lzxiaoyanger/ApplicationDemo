package com.zz.personal.Utils;


import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Excel 工具类
 *
 * @author zz
 * @date 2018-03-21
 */
public class ExcelUtil {


    /**
     * 读取Excel
     * @param fileUrl excel地址
     * @param type  java对象
     * @param <T>   java对象
     * @return
     */
    public static <T> List<T> importExcel(String fileUrl,Class<T> type) throws IllegalAccessException, InstantiationException {
        XSSFWorkbook workbook = null;

        try {
            // 读取Excel文件
            if (fileUrl.contains("&#047;")){
                fileUrl = fileUrl.replace("&#047;","/");
            }
            URL url = new URL(fileUrl);
            FileChannel channel = new FileInputStream(String.valueOf(url.openStream())).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            channel.read(byteBuffer);
            byteBuffer.flip();
//            InputStream inputStream = new BufferedInputStream(url.openStream());
            workbook = new XSSFWorkbook(String.valueOf(byteBuffer));
//            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<T> result = new ArrayList<>();
        // 循环工作表
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 循环行
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                T object = type.newInstance();
                Field[] declaredFields = object.getClass().getDeclaredFields();
                Field.setAccessible(declaredFields,true);
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);

                for (int ci = xssfRow.getFirstCellNum();ci < xssfRow.getLastCellNum();ci ++){
                    XSSFCell cell = xssfRow.getCell(ci);
                    Object value = cell.getStringCellValue();
                    declaredFields[ci].set(object,value);
                }
                result.add(object);
                Field.setAccessible(declaredFields,false);
            }
        }
       return result;
    }

    /**
     * 导出excel
     * @param dataList 数据源
     * @param title    表头
     * @param response
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void exportExcel(List dataList, String[] title, HttpServletResponse response) throws IOException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //创建Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个工作表sheet
        XSSFSheet sheet = workbook.createSheet();
        //创建第一行
        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = null;
        //插入第一行数据 也就是表头
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
        }
        //遍历集合数据，产生数据行
        Iterator iterator = dataList.iterator();
        int index = 1;
        while (iterator.hasNext()){
            XSSFRow nextrow = sheet.createRow(index);
            Object next = iterator.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = next.getClass().getDeclaredFields();
            for (int m = 0; m < fields.length; m ++){
                XSSFCell cell1 = nextrow.createCell(m);
                Field field = fields[m];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                Class<?> aClass = next.getClass();
                Method getMethod = aClass.getMethod(getMethodName,
                        new Class[]{});
                Object value = getMethod.invoke(next, new Object[]{});
                String textValue = null;
                //数据类型都当作字符串简单处理
                textValue = value.toString();

                XSSFRichTextString xssfRichTextString = new XSSFRichTextString(textValue);
                cell1.setCellValue(xssfRichTextString);
            }
            index++;
        }
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=createList.xlsx");//默认Excel名称
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
