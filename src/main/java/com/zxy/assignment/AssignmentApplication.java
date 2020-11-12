package com.zxy.assignment;

import com.zxy.assignment.control.SearchTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class AssignmentApplication {

    public static void main(String[] args) {
        SearchTool searchTool = new SearchTool();
        searchTool.FilesCounter();
        SpringApplication.run(AssignmentApplication.class, args);
    }

}
