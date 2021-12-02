package com.maria_epikhova;

import com.framework.EasyTcpApplication;
import com.framework.annotations.EasyTcpMain;

import io.github.cdimascio.dotenv.Dotenv;

@EasyTcpMain
public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        EasyTcpApplication.run(App.class, Integer.parseInt(dotenv.get("PORT")));
    }
}
