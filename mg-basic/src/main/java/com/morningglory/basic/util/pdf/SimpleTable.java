package com.morningglory.basic.util.pdf;

/**
 * @Author: nsh
 * @Date: 2018/6/7
 * @Description:
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class SimpleTable {
    public static final String DEST = "/Users/nsh/tool/pdf_example/simple_table.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new SimpleTable().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(8);
        for(int aw = 0; aw < 16; aw++){
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new Phrase("hi"));
            table.addCell(cell);
            table.addCell("hi");
        }
        document.add(table);
        document.close();
    }

}