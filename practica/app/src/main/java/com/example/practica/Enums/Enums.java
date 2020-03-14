package com.example.practica.Enums;

public class Enums {
    private static String login = "api/v1/login/";
    private static String alumnos = "api/v1/alumnos/";
    private static String  header = "Authorization";

    public static String getAlumnos() {
        return alumnos;
    }

    public static String getLogin() {
        return login;
    }

    public static String getHeader() {
        return header;
    }
}
