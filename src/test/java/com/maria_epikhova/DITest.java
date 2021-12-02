package com.maria_epikhova;

import com.framework.EasyTcpApplication;
import com.maria_epikhova.controller.ProductsControllerTest;

import org.junit.jupiter.api.Test;

public class DITest {
    public DITest() {
        EasyTcpApplication.run(App.class, 4321);
    }

    @Test
    void testDI() {
        // DIModule.injectAll();
    }

}
