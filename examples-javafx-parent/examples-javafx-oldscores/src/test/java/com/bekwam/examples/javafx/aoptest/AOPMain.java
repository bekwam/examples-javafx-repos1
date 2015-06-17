package com.bekwam.examples.javafx.aoptest;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by carl_000 on 6/13/2015.
 */
public class AOPMain {

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new AOPTestModule());

        MyClass obj = injector.getInstance(MyClass.class);

        obj.doSomething();
    }
}
