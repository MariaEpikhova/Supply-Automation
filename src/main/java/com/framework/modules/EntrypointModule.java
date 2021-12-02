package com.framework.modules;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.framework.annotations.Controller;
import com.framework.annotations.Entrypoint;
import com.framework.annotations.Secured;
import com.framework.models.EntrypointModel;
import com.maria_epikhova.exceptions.InvalidRequestException;

import org.reflections.Reflections;

public class EntrypointModule {
    private EntrypointModule() {
    }

    public static List<EntrypointModel> getAllEntrypointsActions() {
        Reflections reflections = ReflectionsCreator.getReflections();
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);

        List<EntrypointModel> entrypoints = new ArrayList<>();
        for (Class<?> typeClass : classes) {
            Controller controller = typeClass.getAnnotation(Controller.class);

            for (final Method method : typeClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Entrypoint.class)) {
                    Entrypoint entrypoint = method.getAnnotation(Entrypoint.class);
                    Type parameterType = method.getGenericParameterTypes()[0];
                    if (parameterType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) parameterType;
                        Class<?> parameterArgType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                        String action = controller.path() + entrypoint.actionType();

                        boolean secured = method.isAnnotationPresent(Secured.class);
                        entrypoints.add(new EntrypointModel(action, parameterArgType, secured, req -> {
                            try {
                                method.setAccessible(true);
                                Object data = method.invoke(ComponentModule.getInstance(typeClass), req);
                                return data;
                            } catch (IllegalAccessException | IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                throw new InvalidRequestException(e.getCause().getMessage());
                            }
                            return null;
                        }));
                    }
                }
            }
        }
        return entrypoints;
    }
}
