package com.zxy.assignment.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class UploadActivity {
    private void executeUpload(String uploadDir,MultipartFile file,
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
        File serverFile = new File(uploadDir + filename+suffix);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String upload(HttpServletRequest request, @RequestParam("sfile") MultipartFile[] mfiles,
                                       @RequestParam("stime") String time, @RequestParam("sname") String name,
                                       @RequestParam("snum") String snum){
        int timeInt = Integer.parseInt(time);
        if(timeInt<3||timeInt>19) {
            return "/error";
        }
        if(snum.length()<8){
            return "/error";
        }
        try{
            String uploadDir = "F:/"+"upload/"+time+"/";
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
            return "/error";
        }
        return "/success";
    }
}
