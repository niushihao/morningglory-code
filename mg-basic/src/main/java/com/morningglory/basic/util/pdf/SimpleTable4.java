package com.morningglory.basic.util.pdf;

/**
 * @Author: nsh
 * @Date: 2018/6/4
 * @Description: 缩进
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class SimpleTable4 {
    public static final String DEST = "/Users/nsh/tool/pdf_example/simple_table4.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable4().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(1);
        Paragraph wrong = new Paragraph("This is wrong, because an object that was originally a paragraph is reduced to a phrase due to the fact that it's put into a cell that uses text mode.");
        wrong.setIndentationLeft(20);
        //TODO 这里不一样
        PdfPCell wrongCell = new PdfPCell(wrong);
        table.addCell(wrongCell);
        Paragraph right = new Paragraph("This is right, because we create a paragraph with an indentation to the left and as we are adding the paragraph in composite mode, all the properties of the paragraph are preserved.");
        right.setIndentationLeft(20);

        //TODO 这里不一样
        PdfPCell rightCell = new PdfPCell();
        rightCell.addElement(right);
        table.addCell(rightCell);
        document.add(table);
        document.close();
    }

}