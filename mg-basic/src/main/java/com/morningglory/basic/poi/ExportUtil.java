package com.morningglory.basic.poi;

import com.google.common.collect.Lists;
import com.morningglory.basic.poi.ann.ExcelCollection;
import com.morningglory.basic.poi.ann.ExcelSheet;
import com.morningglory.basic.pojo.zoo.Animal;
import com.morningglory.basic.pojo.zoo.Feature;
import com.morningglory.basic.pojo.zoo.Zoo;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 11:29
 * @Desc:
 */
public class ExportUtil<T> {

    public static void main(String[] args) throws IllegalAccessException {

        List<Zoo> zoos = getZoos();

        Workbook workbook = exportExcel(zoos,Zoo.class);

        FileOutputStream fileOutputStream = null;
        try {
            // workbook 2 FileOutputStream
            fileOutputStream = new FileOutputStream("/Users/nsh/Desktop/aa.xls");
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

    private static Workbook exportExcel(List<?> list,Class<?> clazz) throws IllegalAccessException {

        Workbook workbook = new HSSFWorkbook();

        ExcelSheet sheetAnnotation = clazz.getAnnotation(ExcelSheet.class);
        Sheet sheet = workbook.createSheet(sheetAnnotation.name());
        HSSFColor.HSSFColorPredefined headColor = sheetAnnotation.headColor();
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(headColor.getIndex());
        headStyle.setFillBackgroundColor(headColor.getIndex());
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setAlignment(HorizontalAlignment.CENTER);

        int nowIndex = 0;
        for(int rowIndex =0; rowIndex<list.size(); rowIndex++){


            Object obj = list.get(rowIndex);
            Row rowX = sheet.createRow(nowIndex);
            /*List<Field> collectionFields = FieldUtil.getFields(obj.getClass(), ExcelCollection.class);
            int merge = 0;
            for(Field field : collectionFields){
                field.setAccessible(true);
                List list1 = (List) field.get(obj);
                merge = merge > list1.size() ? merge : list1.size();

            }*/
            int merge = 3;
            Field[] fields = FieldUtils.getAllFields(obj.getClass());

            for(int i =0;i< fields.length;i++){
                Field field = fields[i];
                field.setAccessible(true);
                ExcelCollection annotation = field.getAnnotation(ExcelCollection.class);
                Cell cellX = rowX.createCell(i);
                if(annotation == null){
                    CellRangeAddress cra = new CellRangeAddress(nowIndex,nowIndex+merge-1,i,i);
                    sheet.addMergedRegion(cra);

                    Object fieldValue = field.get(obj) == null ? "" : field.get(obj);
                    cellX.setCellValue(String.valueOf(fieldValue).trim());
                    cellX.setCellType(CellType.STRING);
                }else{
                    List subList = field.get(obj) == null ? Collections.emptyList() : (List)field.get(obj);
                    for(int j =0;j<subList.size();j++){
                        Row subRow = sheet.getRow(j + rowIndex);
                        if(subRow == null){
                            subRow = sheet.createRow(j + rowIndex);
                        }
                        Object o = subList.get(j);
                        Field[] subFields = FieldUtils.getAllFields(o.getClass());
                        for(int m =0; m< subFields.length;m++){
                            Cell subcellX = subRow.createCell(i+m);
                            Field subField = subFields[i];
                            subField.setAccessible(true);
                            Object fieldValue = subField.get(o) == null ? "" : subField.get(o);
                            System.out.println("第"+j + rowIndex+"行，第"+i+m+"列增加了表格，值为"+fieldValue);
                            subcellX.setCellValue(String.valueOf(fieldValue).trim());
                            subcellX.setCellType(CellType.STRING);
                        }
                    }
                }
                //field.




            }

            if(merge > 0){
                nowIndex += merge;
            }else{
                nowIndex++;
            }


        }


        return workbook;
    }

    public static List<Zoo> getZoos(){

        List<Zoo> zoos = Lists.newArrayList();
        for(int i=0;i<10;i++){
            Zoo zoo = new Zoo();
            zoo.setName(i+"");
            zoo.setAddress("皇后大道"+i);
            zoo.setTest("测试");
            List<Animal> animals = Lists.newArrayList();
            for(int j =0;j<3;j++){
                Animal animal = new Animal();
                if(j == 0){
                    animal.setTest("123");
                }
                animal.setName("animal"+i);
                animal.setAge(j);
                animal.setSex("男");
                animals.add(animal);

                List<Feature> features = Lists.newArrayList();
                for(int h = 0; h< 2; h++){
                    Feature feature = new Feature();
                    feature.setName("feature"+h);
                    feature.setDesc("desc"+h);
                    features.add(feature);
                }
                animal.setFeatureList(features);
            }
            zoo.setAnimals(animals);
            zoo.setAnimalList(Lists.newArrayList("动物1","动物2","动物3","动物4"));
            zoos.add(zoo);
        }

        return zoos;
    }
}
