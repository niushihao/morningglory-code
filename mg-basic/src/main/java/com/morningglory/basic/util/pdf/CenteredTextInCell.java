package com.morningglory.basic.util.pdf;

/**
 * @Author: nsh
 * @Date: 2018/6/4
 * @Description:
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CenteredTextInCell {

    public static final String DEST = "/Users/nsh/tool/pdf_example/centered_text.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new CenteredTextInCell().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph para = new Paragraph("Test asdfasdfasdfa asdfadsf asdfasdfasfasd asdfasdfasdfadfad asdfasdfasdfasdf asdfasdfasdfasdfasdfasdf asdfasdfasdafsdfasdfasdfasdf asdfasdfasdfasdfasdfasdf asdfasdfasdfasdf", font);
        para.setLeading(0, 1);
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        cell.setMinimumHeight(20);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.addElement(para);
        table.addCell(cell);
        document.add(table);
        document.close();
    }
}