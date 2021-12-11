package com.main.controller;


import com.main.model.User;
import com.main.service.UserService;
import com.main.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController

@RequestMapping("/image")
public class ImageController {

    @ResponseBody
    @RequestMapping(value="/upload",produces="application/json;charset=UTF-8",method = RequestMethod.POST)
    public  String upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        if(session.getAttribute("uid") == null) {
            return JsonData.buildError(4004, "你还未登录，请先登录");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());

        // uploads文件夹位置
        String rootPath = request.getSession().getServletContext().getRealPath("WEB-INF/upload");
        // 原始名称
        String originalFileName = file.getOriginalFilename();
        // 新文件名
        String newFileName = UUID.randomUUID().toString().replaceAll("-","" )+ res + originalFileName.substring(originalFileName.lastIndexOf("."));
        // 创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));

        // 新文件
        File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
        // 判断目标文件所在目录是否存在
        if( !newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        // 将内存中的数据写入磁盘
        file.transferTo(newFile);


        String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH)+1) + "/" + newFileName;
        Map <String,String> result;

        return  JsonData.buildSuccess(fileUrl);
    }


    @ResponseBody
    @RequestMapping(value="/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String file_name = request.getParameter("file");
        System.out.println(file_name);
        String file_path = request.getSession().getServletContext().getRealPath("WEB-INF/upload/")+file_name;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(file_path)));
        String [] file_name_split = file_name.split("/");
        String filename = URLEncoder.encode(file_name_split[file_name_split.length-1],"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}

