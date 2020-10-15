package com.zxy.assignment.pojo;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UsersMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet resultSet, int i) throws SQLException {
        int ID = resultSet.getInt("ID");
        String SNUM = resultSet.getString("SNUM");
        String SNAME = resultSet.getString("SNAME");
        Date TIME = resultSet.getTime("TIME");
        Users use = new Users(ID,SNUM,SNAME,TIME);
        return use;
    }
}
