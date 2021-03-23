package com.morningglory.basic.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.morningglory.basic.pojo.zoo.Zoo;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import static com.morningglory.basic.poi.ExportUtil.getZoos;

/**
 * @Author: qianniu
 * @Date: 2019-03-15 11:35
 * @Desc: 使用easypoi测试
 */
public class EasypoiTest {

    public static void main(String[] args) throws Exception {

        exportExcel();

        //importExcel();
    }


    private static void importExcel() throws Exception {

        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        //params.setStartRows(2);

        FileInputStream fileInputStream = new FileInputStream("/Users/nsh/Desktop/bb.xls");


        List<Zoo> list = ExcelImportUtil.importExcel(fileInputStream, Zoo.class, params);

        System.out.println(list.size());
    }

    private static void exportExcel() {
        List<Zoo> zoos = getZoos();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, null, "测试"),
                Zoo.class, zoos);

        FileOutputStream fileOutputStream = null;
        try {
            // workbook 2 FileOutputStream
            fileOutputStream = new FileOutputStream("/Users/nsh/Desktop/bb.xls");
            workbook.write(fileOutputStream);

            // flush
            fileOutputStream.flush();
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (fileOutputStream!=null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                //logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }
}
