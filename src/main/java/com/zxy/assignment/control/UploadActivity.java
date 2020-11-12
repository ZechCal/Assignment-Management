package com.zxy.assignment.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLockInterruptionException;

@Controller
@ResponseBody
public class UploadActivity {
    public static String FILE_PATH = "F:/assigns";

    public void executeUpload(String uploadDir,MultipartFile file,
                               String sname, String snum, String time, int i) throws Exception
    {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = "第"+time+"周"+"-"+snum+"-"+sname;
        if(i>0){
            filename+=String.valueOf(i);
        }
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir +"/"+ filename+suffix);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("sfile") MultipartFile[] mfiles,
                       @RequestParam("stime") String time, @RequestParam("sname") String name,
                       @RequestParam("snum") String snum) throws IOException {
        int timeInt = 0;
        try {
            timeInt = Integer.parseInt(time);
        }catch(NumberFormatException e){
            System.out.println("Number format exception during fileupload: "+snum+" 学生姓名:"+name);
            response.sendRedirect("error.html");
            return;
        }
        if(timeInt<3||timeInt>19) {
            response.sendRedirect("error.html");
            return;
        }
        if(snum.length()<8){
            response.sendRedirect("error.html");
            return;
        }
        try{
            String uploadDir = FILE_PATH+"/"+time;
            File dir = new File(uploadDir);
            if(!dir.exists()){
                dir.mkdir();
                System.out.println(dir+" making...");
            }
            for(int i =0;i<mfiles.length;i++){
                if(mfiles[i]!=null){
                    executeUpload(uploadDir,mfiles[i],name,snum,time,i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
            return;
        }
        SearchTool searchTool = new SearchTool();
        searchTool.FilesCounter();
        response.sendRedirect("success.html");
    }
}
