package com.morningglory.basic.util.pdf;

/**
 * @Author: nsh
 * @Date: 2018/6/4
 * @Description:
 */
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class TableWithTab {
    public static final String DEST = "/Users/nsh/tool/pdf_example/table_with_tab.pdf";

    public static void main(String[] args) throws IOException,
            DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableWithTab().createPdf(DEST);

        System.out.println(String.format("%-35s", "John Edward Jr."));
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Chunk glue = new Chunk(new VerticalPositionMark());
        PdfPTable table = new PdfPTable(1);
        Phrase p = new Phrase();
        p.add("Left");
        p.add(glue);
        p.add("Right");
        table.addCell(p);
        document.add(table);
        document.close();
    }

}