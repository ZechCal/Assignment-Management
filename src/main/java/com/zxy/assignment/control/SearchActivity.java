package com.zxy.assignment.control;

import com.zxy.assignment.pojo.Assignment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@Controller
public class SearchActivity {
    @RequestMapping(value="/search",method= RequestMethod.GET)
    public String search(@RequestParam("stime") String time, @RequestParam("snum") String snum,
                         Model model){
        System.out.println(time);
        int timeInt = Integer.parseInt(time);
        if(timeInt<3||timeInt>19) {
            return "error.html";
        }

        String key = time+snum.substring(5);
        int intKey = Integer.valueOf(key);
        SearchTool searchTool = new SearchTool();
        System.out.println(intKey);
        Assignment assignment = searchTool.Reader(intKey);
        if(assignment==null){
            model.addAttribute("week", "未查到");
            model.addAttribute("date", "Nan");
            model.addAttribute("stunum", "Nan");
        }else {
            model.addAttribute("week", assignment.getWeek());
            model.addAttribute("date", assignment.getDate());
            model.addAttribute("stunum", assignment.getStudentNumber());
        }
        return "searchresult.html";
    }
}
