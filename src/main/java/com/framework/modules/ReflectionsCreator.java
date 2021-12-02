package com.framework.modules;

import com.framework.EasyTcpApplication;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

public class ReflectionsCreator {
    private ReflectionsCreator() {
    }

    private static Reflections reflections;

    public static void create(Class<?> mainClass) {
        ReflectionsCreator.reflections = new Reflections(mainClass.getPackageName(),
                EasyTcpApplication.class.getPackageName(), new FieldAnnotationsScanner(), new SubTypesScanner(),
                new TypeAnnotationsScanner());
    }

    public static Reflections getReflections() {
        return reflections;
    }
}
