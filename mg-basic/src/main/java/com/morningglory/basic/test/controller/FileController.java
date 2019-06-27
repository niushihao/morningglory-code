package com.morningglory.basic.test.controller;

/**
 * @Author: nsh
 * @Date: 2018/4/24
 * @Description:
 */
//@RestController
public class FileController {

    /*@RequestMapping("/get/pic")
    public void getPic(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

        String path ="/usr/local/aa.txt";
        String fileName ="aa.txt";

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("multipart/form-data");
        // 2.设置文件头：最后一个参数是设置下载文件名
        // 设定Http头部
        resp.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName , "UTF-8"));
        resp.addHeader("Content-Type", "application/vnd.ms-excel");
        OutputStream out;

        //找到文件
        File file = new File(path);

        try (FileInputStream inputStream = new FileInputStream(file)) {

            out = resp.getOutputStream();
            byte[] buffer = new byte[2048];

            int b = inputStream.read(buffer);
            while (b != -1) {
                // 4.写到输出流(out)中
                out.write(buffer, 0, b);
                b = inputStream.read(buffer);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}