package com.example.practica.models;

import com.example.practica.presents.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class User {
    private int id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String sexo;
    private String carrera;


    public User(){
        nombre = "";
        apellidos = "";
        edad = 0;
        sexo = "";
        carrera = "";

    }

    public User(int id, String nombre, String apellidos, int edad, String sexo, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.carrera = carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void getAlumnos(final ArrayList<User> lista, AsyncHttpClient client, String header, String token, String http){
        client.addHeader(header,token);
        client.get(http, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);
                try {
                    String rs = new String(responseBody);
                    System.out.println(rs);
                    //String content = new String(responseBody, "UTF-8");
                    JSONArray obj = new JSONArray(rs);

                    for (int i=0; i < obj.length();i++) {
                        lista.add(new User(
                                Integer.parseInt(obj.getJSONObject(i).getString("id")),
                                obj.getJSONObject(i).getString("nombre"),
                                obj.getJSONObject(i).getString("apellidos"),
                                Integer.parseInt(obj.getJSONObject(i).getString("edad")),
                                obj.getJSONObject(i).getString("sexo"),
                                obj.getJSONObject(i).getString("carrera")));
                        System.out.println("Lista user"+lista.get(0).getNombre());
                    }
                    System.out.println(obj.getJSONObject(0).getString("nombre"));
                    //Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                switch(statusCode){
                    case 401:
                      //  Toast.makeText(getContext(), "401 !", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                      //  Toast.makeText(getContext(), "404 !", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                     //   Toast.makeText(getContext(), "400 !", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                     //   Toast.makeText(getContext(), "500 !", Toast.LENGTH_LONG).show();
                        break;
                    default:
                     //   Toast.makeText(getContext(), "Error !", Toast.LENGTH_LONG).show();
                        break;
                }
                //String rs = new String(responseBody);
                //  Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println(retryNo);
            }
        });
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString(){
      return this.nombre;
    }


}
