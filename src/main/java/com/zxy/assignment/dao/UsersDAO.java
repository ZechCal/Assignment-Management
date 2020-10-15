package com.zxy.assignment.dao;

import com.zxy.assignment.pojo.Users;
import com.zxy.assignment.pojo.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Users> query(String SNUM){
        String sql = "select * from USERS where SNUM="+SNUM;
        List<Users> lists = jdbcTemplate.query(sql, new UsersMapper());
        return lists;
    }

    public void increase(Users user){
        jdbcTemplate.update("insert into USERS (SNUM,SNAME,TIME) values(?,?,?)",
                user.getSNUM(),user.getSNAME(),user.getTIME());
    }
}
