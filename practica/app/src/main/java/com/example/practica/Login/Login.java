package com.example.practica.Login;

public class Login {
    private static String username;
    private static String token;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Login.username = username;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Login.token = token;
    }
}
