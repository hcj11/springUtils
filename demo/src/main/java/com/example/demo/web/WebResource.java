package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

@RequestMapping(value = "web")
@Controller
public class WebResource {

    /**
     * 返回pdf格式数据
     */
//    @RequestMapping("")
//    public String getPdf() {
//
//    }

    /**
     * 根据版本号下载最新的安卓apk
     *
     */
    @RequestMapping(value = "/download",produces = "application/pdf")
    @ResponseBody
    public void downAndroidApk(HttpServletRequest request, HttpServletResponse response) {
        // String fileName = "智慧组工.apk";
        response.setHeader("Access-Control-Allow-Origin", "*");
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String fileName = properties.getProperty("fileName");
        String filePath = properties.getProperty("filePath");
        String path = filePath + fileName;
        File file = new File(path);
        response.reset();
//		 response.setContentType("application/octet-stream");
//		 response.setContentType("application/vnd.android.package-archive");
        // 设置响应类型（应用程序强制下载）
        response.setContentType("application/force-download");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(file));
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
