package com.zxy.assignment.pojo;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class Users{
    private int ID;
    private String SNUM;

    public Users(int ID, String SNUM, String SNAME, Date TIME) {
        this.ID = ID;
        this.SNUM = SNUM;
        this.SNAME = SNAME;
        this.TIME = TIME;
    }

    public int getID() {
        return ID;
    }

    public String getSNUM() {
        return SNUM;
    }

    public void setSNUM(String SNUM) {
        this.SNUM = SNUM;
    }

    public String getSNAME() {
        return SNAME;
    }

    public void setSNAME(String SNAME) {
        this.SNAME = SNAME;
    }

    public Date getTIME() {
        return TIME;
    }

    public void setTIME(Date TIME) {
        this.TIME = TIME;
    }

    private String SNAME;
    private Date TIME;


}
