package com.example.practica.presents;

import com.example.practica.models.User;

import java.util.ArrayList;

public class LoginPresent {
    private View view;

    public LoginPresent(View view){
        this.view = view;
    }

    /*Aplica para los dos fragments, ya que los usuarios vienen con los datos completos, es decir,
     *en el metodo crear tabla se utilizan los atributos que se quiere mostrar
     */

    public void postLogin(String username, String password){
        System.out.println("data. "+username+password);
        User.postLogin(username,password,this);
    }

    public void accerder(){
        view.accerderLogin();
    }

    public interface View{
        void accerderLogin();
    }
}
