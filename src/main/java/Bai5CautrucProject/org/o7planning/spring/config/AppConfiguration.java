package Bai5CautrucProject.org.o7planning.spring.config;

import Bai5CautrucProject.org.o7planning.spring.impl.Vietnamese;
import Bai5CautrucProject.org.o7planning.spring.lang.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"Bai5CautrucProject.org.o7planning.spring.bean"})
public class AppConfiguration {

    @Bean(name = "language")
    public Language getLanguage() {
        return new Vietnamese();
    }
}
