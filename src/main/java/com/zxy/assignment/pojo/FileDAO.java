package com.zxy.assignment.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class FileDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean SaveFileRecord(FileObject fileObject){
        String sqlstring = "insert into ";
        return true;
    }
}
