package com.framework.modules;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.framework.interfaces.SecurityConfig;

import org.reflections.Reflections;

public class SecurityModule {
    private static List<SecurityConfig> configs;

    public static void initAllConfigs() {
        configs = new ArrayList<>();
        Reflections reflections = ReflectionsCreator.getReflections();
        Set<Class<?>> configClasses = reflections.getTypesAnnotatedWith(com.framework.annotations.SecurityConfig.class);

        for (Class<?> configClassType : configClasses) {
            if (SecurityConfig.class.isAssignableFrom(configClassType)) {
                try {
                    configs.add((SecurityConfig) configClassType.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<SecurityConfig> getConfigs() {
        if (configs == null) {
            initAllConfigs();
        }

        return configs;
    }
}
