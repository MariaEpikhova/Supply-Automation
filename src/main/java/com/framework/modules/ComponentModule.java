package com.framework.modules;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.framework.annotations.Component;
import com.framework.annotations.Controller;

import org.reflections.Reflections;

public class ComponentModule {
    private ComponentModule() {
    }

    private static Map<Class<?>, Object> instanses;

    public static void initComponents() {
        ComponentModule.instanses = new HashMap<>();
        Reflections reflections = ReflectionsCreator.getReflections();
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> tClass : classes) {
            if (!tClass.isAnnotation()) {
                addInstance(tClass);
            }
        }

    }

    private static void addInstance(Class<?> type) {
        Constructor<?> ctor = type.getConstructors()[0];
        try {
            ComponentModule.instanses.put(type, ctor.newInstance());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Object> T getInstance(Class<T> tClass) {
        if (ComponentModule.instanses == null) {
            ComponentModule.initComponents();
            getInstance(tClass);
        }

        Object instanse = ComponentModule.instanses.get(tClass);
        if (instanse != null) {
            return tClass.cast(instanse);
        }

        return null;
    }
}
