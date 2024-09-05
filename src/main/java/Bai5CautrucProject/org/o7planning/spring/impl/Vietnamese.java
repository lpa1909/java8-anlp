package Bai5CautrucProject.org.o7planning.spring.impl;

import Bai5CautrucProject.org.o7planning.spring.lang.Language;

public class Vietnamese implements Language {
    @Override
    public String getGreeting() {
        return "Xin chao";
    }

    @Override
    public String getBye() {
        return "Tam biet";
    }
}
