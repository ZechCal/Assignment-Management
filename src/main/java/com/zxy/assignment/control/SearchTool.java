package com.zxy.assignment.control;

import com.zxy.assignment.pojo.Assignment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// File based Search Utility
// Interval: half hour.
@Component
public class SearchTool {
    public static Map<Integer,Long> FILE_MAP = new HashMap<Integer, Long>();
    public static final int START_WEEK = 3;
    public static boolean [] finished = new boolean[11];
    public final static long HALF_HOUR = 30* 60 * 1000;

    public static Object[] ifRepeat(Object[] arr){
        //实例化一个set集合
        Set set = new HashSet();
        //遍历数组并存入集合,如果元素已存在则不会重复存入
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        //返回Set集合的数组形式
        return set.toArray();
    }

    public static LinkedList<Integer> repeatFinder(String [] strings){
        LinkedList<Integer> repeats = new LinkedList<Integer>();
        for(int i=0;i<strings.length-1;i++){
            if(strings[i]==strings[i+1]){
                repeats.add(i);
            }
        }
        return repeats;
    }

    public String Decoder(int number){
        String str = "";
        str = String.valueOf(number);
        return str;
    }

    public int Encoder(String filename){
        int x = 0;
        String patternString = "第([0-9]{1,2})周-([0-9]{9})";

        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher1 =pattern.matcher(filename);

        StringBuilder strbuilder = new StringBuilder();
        if(matcher1.find()) {
            strbuilder.append(matcher1.group(1));
            strbuilder.append(matcher1.group(2).substring(5));
        }
        String str = new String(strbuilder);
        try{
            x = Integer.valueOf(str);
        }catch(NumberFormatException e) {
            System.out.println("Number format error");
        }
        return x;
    }

    public static void init(){
        int limit=1;
        for(int i=0;i<limit;i++){
            finished[i]=true;
        }
        for(int i=limit;i<finished.length;i++){
            finished[i]=false;
        }
    }

    public void Writer(String[] fileNames, String folderName, Long[] time){
        LinkedList<Integer> repeatList = repeatFinder(fileNames);
        // translate str into integer
        for(int i =0;i<fileNames.length;i++) {
            if(!repeatList.isEmpty()&&i==repeatList.getFirst()){
                repeatList.removeFirst();
                continue;
            }
            try {
                FILE_MAP.put(Encoder(fileNames[i]), time[i]);
            }catch(NumberFormatException e){
                System.out.println("Number format error 1");
            }
        }
    }

//    public void Writer(String[] fileNames, String folderName, Long[] time){
//        Object [] objects = ifRepeat(fileNames);
//        String str = "";
//        // translate str into integer
//        for(int i =0;i<objects.length;i++) {
//            str = String.valueOf(objects[i]);
//            try {
//                FILE_MAP.put(Encoder(str), time[i]);
//            }catch(NumberFormatException e){
//                System.out.println("Number format error 1");
//            }
//        }
//    }

    public Assignment Reader(Integer key){
        Assignment assignment = new Assignment();
        String str = Decoder(key);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(FILE_MAP.containsKey(key)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(FILE_MAP.get(key));
            String date = dateFormat.format(calendar.getTime());
            assignment.setWeek(str.charAt(0)-'0');
            assignment.setStudentNumber("20011"+str.substring(1));
            assignment.setDate(date);
            return assignment;
        }else if(finished[str.charAt(0)-'0'-START_WEEK]){
            assignment.setWeek(str.charAt(0)-'0');
            assignment.setStudentNumber("20011"+str.substring(1));
            assignment.setDate("整批次已确认提交到服务器");
            return assignment;
        }
        return null;
    }


//    @Scheduled(fixedDelay = HALF_HOUR)
    public void FilesCounter(){
        init();
        FILE_MAP.clear();
        String path = UploadActivity.FILE_PATH;		//作业路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中

        if(fs!=null) {
            for (File f : fs) {                    //遍历File[]数组
                int index= Integer.valueOf(f.getName());
                if (f.isDirectory()&&!finished[index-START_WEEK])        //如果不是文件
                {
                    File[] file_lists = f.listFiles();
                    if(file_lists.length>0) {
                        String[] fileNames = new String[file_lists.length];
                        Long[] times = new Long[file_lists.length];
                        for (int i = 0; i < file_lists.length; i++) {
                            String temp = file_lists[i].getName();
                            fileNames[i] = temp.substring(0,temp.lastIndexOf("."));
                            times[i] = file_lists[i].lastModified();
                        }
                        Writer(fileNames, f.getName(), times);
                    }
                }
            }
        }
    }
}
