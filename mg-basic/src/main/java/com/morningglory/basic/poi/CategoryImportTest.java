package com.morningglory.basic.poi;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * @author qianniu
 * @date 2020/8/24 9:52 上午
 * @desc
 */
@Slf4j
public class CategoryImportTest {
    static String SQL_FORMAT = "INSERT INTO crm_biz_category (id,biz_type,pid,name,code,level,has_children,router,createdAt,updatedAt) " +
            "VALUES (%d,'%s',%d,'%s','%s',%d,%d,'%s',now(),now());";

    public static void main(String[] args) throws IOException, InvalidFormatException {

        File file = new File("/Users/nsh/Downloads/category_sql.xlsx");

        Workbook book =  WorkbookFactory.create(file);
        int numberOfSheets = book.getNumberOfSheets();
        long beginId = 1;
        LinkedHashMap<Long, Category> map = Maps.newLinkedHashMapWithExpectedSize(1024);
        for(int s=0;s<numberOfSheets;s++) {

            Sheet sheet = book.getSheetAt(s);
            int lastRowNum = sheet.getLastRowNum();
            log.info("总行数 = {}", lastRowNum);


            Row rowInfo = sheet.getRow(0);
            int physicalNumberOfCells = rowInfo.getPhysicalNumberOfCells();
            log.info("总列数 = {}", physicalNumberOfCells);

            String bizType = sheet.getSheetName();
            long firstLevelId = 0;
            long secondLevelId = 0;
            long thirdLevelId = 0;
            long fourthLevelId = 0;
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell firstLevelCell = row.getCell(0);
                Cell secondLevelCell = row.getCell(1);
                Cell thirdLevelCell = row.getCell(2);
                Cell fourthLevelCell = row.getCell(3);
                long nextId = beginId++;

                if (firstLevelCell != null && !CellType.BLANK.equals(firstLevelCell.getCellTypeEnum())) {
                    String stringCellValue = firstLevelCell.getStringCellValue();
                    String code = "";
                    String[] split = stringCellValue.split("#");
                    if (split.length == 2) {
                        stringCellValue = split[0];
                        code = split[1];
                    }
                    Category firstCategory = new Category();
                    firstLevelId = nextId;
                    firstCategory.setId(nextId);
                    firstCategory.setBizType(bizType);
                    firstCategory.setPid(0L);
                    firstCategory.setName(stringCellValue);
                    // TODO 可以从名称后边截取
                    firstCategory.setCode(code);
                    firstCategory.setLevel(1);
                    firstCategory.setHasChildren(0);
                    firstCategory.setRouter("/" + firstLevelId);

                    map.put(firstLevelId, firstCategory);
                    log.info("第 {} 行 发现了一级类目,名称: {},id : {},PID: {},router: {}", i, stringCellValue, firstLevelId, 0, firstCategory.getRouter());
                    continue;
                }

                if (secondLevelCell != null && !CellType.BLANK.equals(secondLevelCell.getCellTypeEnum())) {
                    String stringCellValue = secondLevelCell.getStringCellValue();
                    String code = "";
                    String[] split = stringCellValue.split("#");
                    if (split.length == 2) {
                        stringCellValue = split[0];
                        code = split[1];
                    }
                    Category secondCategory = new Category();
                    secondLevelId = nextId;
                    secondCategory.setId(nextId);
                    secondCategory.setBizType(bizType);
                    secondCategory.setPid(firstLevelId);
                    secondCategory.setName(stringCellValue);
                    // TODO 可以从名称后边截取
                    secondCategory.setCode(code);
                    secondCategory.setLevel(2);
                    secondCategory.setHasChildren(0);

                    Category parent = map.get(firstLevelId);
                    secondCategory.setRouter(parent.router + "/" + secondLevelId);
                    parent.setHasChildren(1);
                    map.put(secondLevelId, secondCategory);
                    log.info("第 {} 行 发现了二级类目,名称: {},id : {},PID: {},router: {}", i, stringCellValue, secondLevelId, firstLevelId, secondCategory.getRouter());
                    continue;
                }

                if (thirdLevelCell != null && !CellType.BLANK.equals(thirdLevelCell.getCellTypeEnum())) {
                    String stringCellValue = thirdLevelCell.getStringCellValue();
                    String code = "";
                    String[] split = stringCellValue.split("#");
                    if (split.length == 2) {
                        stringCellValue = split[0];
                        code = split[1];
                    }
                    Category thirdCategory = new Category();
                    thirdLevelId = nextId;
                    thirdCategory.setId(nextId);
                    thirdCategory.setBizType(bizType);
                    thirdCategory.setPid(secondLevelId);
                    thirdCategory.setName(stringCellValue);
                    // TODO 可以从名称后边截取
                    thirdCategory.setCode(code);
                    thirdCategory.setLevel(3);
                    thirdCategory.setHasChildren(0);

                    Category parent = map.get(secondLevelId);
                    thirdCategory.setRouter(parent.router + "/" + thirdLevelId);
                    parent.setHasChildren(1);
                    map.put(thirdLevelId, thirdCategory);
                    log.info("第 {} 行 发现了三级类目,名称: {},id : {},PID: {},router: {}", i, stringCellValue, thirdLevelId, secondLevelId, thirdCategory.getRouter());
                    continue;
                }

                if (fourthLevelCell != null && !CellType.BLANK.equals(fourthLevelCell.getCellTypeEnum())) {
                    String stringCellValue = fourthLevelCell.getStringCellValue();
                    String code = "";
                    String[] split = stringCellValue.split("#");
                    if (split.length == 2) {
                        stringCellValue = split[0];
                        code = split[1];
                    }
                    Category fourthCategory = new Category();
                    fourthLevelId = nextId;
                    fourthCategory.setId(nextId);
                    fourthCategory.setBizType(bizType);
                    fourthCategory.setPid(thirdLevelId);
                    fourthCategory.setName(stringCellValue);
                    // TODO 可以从名称后边截取
                    fourthCategory.setCode(code);
                    fourthCategory.setLevel(4);
                    fourthCategory.setHasChildren(0);

                    Category parent = map.get(thirdLevelId);
                    fourthCategory.setRouter(parent.router + "/" + fourthLevelId);
                    parent.setHasChildren(1);

                    map.put(fourthLevelId, fourthCategory);
                    log.info("第 {} 行 发现了四级类目,名称: {},id : {},PID: {},router: {}", i, stringCellValue, fourthLevelId, thirdLevelId, fourthCategory.getRouter());
                    continue;
                }

            }
        }
        log.info("处理完成");

        log.info("**************打印sql****************");
        map.forEach((k,v) -> {
            System.out.println(v.toSql());
        });

    }

    @Data
    public static class Category{

        private Long id;

        private String bizType;

        private Long pid;

        private String name;

        private String code;

        private int level;

        private int hasChildren;

        private String router;


        public String toSql(){
            return String.format(SQL_FORMAT,id,bizType,pid,name,code,level,hasChildren,router);
        }
    }
}
