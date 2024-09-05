package Bai5CautrucProject.org.o7planning.spring;

import Bai5CautrucProject.org.o7planning.spring.bean.GreetingService;
import Bai5CautrucProject.org.o7planning.spring.bean.MyComponent;
import Bai5CautrucProject.org.o7planning.spring.config.AppConfiguration;
import Bai5CautrucProject.org.o7planning.spring.lang.Language;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainProgram {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        System.out.println();
        Language language = (Language) context.getBean("language");
        System.out.println("Bean language: " + language);
        System.out.println("Call language say Bye: " + language.getBye());

        System.out.println();

        GreetingService service = (GreetingService) context.getBean("greeting");
        service.sayGreeting();
        System.out.println();
        MyComponent myComponent = (MyComponent) context.getBean("myComponent");
        myComponent.showAppInfo();

    }
}
