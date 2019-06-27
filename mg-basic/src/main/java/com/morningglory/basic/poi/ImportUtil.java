package com.morningglory.basic.poi;

import cn.afterturn.easypoi.util.PoiCellUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

/**
 * @Author: qianniu
 * @Date: 2019-04-15 13:42
 * @Desc:
 */
public class ImportUtil {

    public static void main(String[] args) throws IOException, InvalidFormatException {

        File file = new File("/Users/nsh/Desktop/bb.xls");

        Workbook book =  WorkbookFactory.create(file);

        Sheet sheet = book.getSheetAt(0);

        Row row = sheet.getRow(5);

        boolean mergedRegion = PoiCellUtil.isMergedRegion(sheet, row.getRowNum(), 0);
        System.out.println("mergedRegion = "+mergedRegion);

    }
}
