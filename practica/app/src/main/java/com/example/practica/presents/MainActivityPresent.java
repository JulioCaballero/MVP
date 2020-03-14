package com.example.practica.presents;
import com.example.practica.models.User;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

public class MainActivityPresent {
    private User user;
    private View view;

    public MainActivityPresent(View view){
        this.user = new User();
        this.view = view;
    }


    public void llenarLista(ArrayList<User> lista){
        //User.getAlumnos(lista,client,header,token,http);
        view.llenarTabla(lista);
    }

    public interface View{
        void llenarTabla(ArrayList<User> lista);
    }
}
