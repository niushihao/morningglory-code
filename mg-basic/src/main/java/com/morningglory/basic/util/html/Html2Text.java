package com.morningglory.basic.util.html;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

/**
 * @Author: qianniu
 * @Date: 2019-04-11 10:13
 * @Desc:
 */
public class Html2Text extends HTMLEditorKit.ParserCallback{

    private static Html2Text html2Text = new Html2Text();

    StringBuffer s;

    public Html2Text() {
    }

    public void parse(String str) throws IOException {

        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        s = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    public void handleText(char[] text, int pos) {
        s.append(text);
    }

    public String getText() {
        return s.toString();
    }

    public static String getContent(String str) {
        try {
            html2Text.parse(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return html2Text.getText();
    }

    public static void main(String[] args) {

        String content = Html2Text.getContent("<ol><li><p>123&lt;p&gt;</p></li></ol><blockquote></blockquote><p></p>");
        System.out.println("content = "+content);

    }
}

