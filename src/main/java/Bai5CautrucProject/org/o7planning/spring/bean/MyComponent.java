package Bai5CautrucProject.org.o7planning.spring.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyComponent {

    @Autowired
    private MyRespository myRespository;

    public void showAppInfo() {
        System.out.println("Now is: " + myRespository.getSystemDateTime());
        System.out.println("App Name: " + myRespository.getAppName());
    }
}
