package com.framework;

import com.framework.annotations.EasyTcpMain;
import com.framework.modules.ComponentModule;
import com.framework.modules.DIModule;
import com.framework.modules.ReflectionsCreator;
import com.framework.recievers.ConnectionManager;

public final class EasyTcpApplication {
    private EasyTcpApplication() {
    }

    public static void run(Class<?> appClass, int port) {
        if (appClass.isAnnotationPresent(EasyTcpMain.class)) {
            ReflectionsCreator.create(appClass);
            ComponentModule.initComponents();
            DIModule.injectAll();

            ConnectionManager connectionManager = new ConnectionManager(port);
            connectionManager.start();
        } else {
            System.err.println("Please. Mark your main class with annotaion @EasyTcpMain");
        }
    }
}
