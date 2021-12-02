package com;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.framework.annotations.Secured;
import com.framework.models.Request;

import org.junit.jupiter.api.Test;

public class MethodsTest {
    class SomeController {
        public void someMethod(Request<String> req) {
            System.out.println("Some");
        }

        @Secured
        public void foo() {
            System.out.println("DAsd");
        }
    }

    @Test
    void testMethods() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            SecurityException, NoSuchMethodException {
        Class<SomeController> type = SomeController.class;
        Method method = type.getMethod("foo");
        assertEquals(true, method.isAnnotationPresent(Secured.class));
    }
}
