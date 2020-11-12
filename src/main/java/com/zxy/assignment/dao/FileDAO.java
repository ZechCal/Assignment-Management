package com.zxy.assignment.dao;

import com.zxy.assignment.pojo.FileObject;
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
