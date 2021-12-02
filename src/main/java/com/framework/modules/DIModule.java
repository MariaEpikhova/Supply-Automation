package com.framework.modules;

import java.lang.reflect.Field;
import java.util.Set;

import com.framework.annotations.Autowired;

import org.reflections.Reflections;

public class DIModule {
    private DIModule() {
    }

    public static void injectAll() {
        Reflections reflections = ReflectionsCreator.getReflections();
        Set<Field> autowiredFields = reflections.getFieldsAnnotatedWith(Autowired.class);

        autowiredFields.forEach(field -> {
            Object instanceToInject = ComponentModule.getInstance(field.getType());
            Object fieldOwnernstance = ComponentModule.getInstance(field.getDeclaringClass());
            if (instanceToInject != null) {
                field.setAccessible(true);
                try {
                    field.set(fieldOwnernstance, instanceToInject);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
