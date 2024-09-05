package Bai5CautrucProject.org.o7planning.spring.impl;

import Bai5CautrucProject.org.o7planning.spring.lang.Language;

public class English implements Language {
    @Override
    public String getGreeting() {
        return "Hello";
    }

    @Override
    public String getBye() {
        return "Goodbye";
    }
}
