package Bai5CautrucProject.org.o7planning.spring.bean;

import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MyRespository {
    public String getAppName() {
        return "Hello Spring App";
    }

    public Date getSystemDateTime() {
        return new Date();
    }
}
