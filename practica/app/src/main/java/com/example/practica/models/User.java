package com.example.practica.models;

import com.example.practica.Enums.Enums;
import com.example.practica.Helpers.Helpers;
import com.example.practica.Login.Login;
import com.example.practica.presents.LoginPresent;
import com.example.practica.presents.MainActivityPresent;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class User {
    private int id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String sexo;
    private String carrera;
    private static ArrayList<User> lista = null;
    private static User user = null;



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

    public static void getAlumnos(final MainActivityPresent present){
        lista = new ArrayList<>();
        HttpClient.get(Helpers.getBaseUrl()+Enums.getAlumnos(),null,Login.getToken(),
                Enums.getHeader(), new AsyncHttpResponseHandler() {
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
                    String content = new String(responseBody, "UTF-8");
                    System.out.println(content);
                    JSONArray obj = new JSONArray(rs);
                    for (int i=0; i < obj.length();i++) {
                        lista.add(new User(
                                Integer.parseInt(obj.getJSONObject(i).getString("id")),
                                obj.getJSONObject(i).getString("nombre"),
                                obj.getJSONObject(i).getString("apellidos"),
                                Integer.parseInt(obj.getJSONObject(i).getString("edad")),
                                obj.getJSONObject(i).getString("sexo"),
                                obj.getJSONObject(i).getString("carrera")));
                    }
                    System.out.println("Lista"+lista.get(0).getNombre());
                    present.crearTabla(lista);
                    //Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    System.out.println("error "+e);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
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

    public static void getAlumnoByID(final int id,final MainActivityPresent present){
        user = null;
        HttpClient.getByid(id,Helpers.getBaseUrl()+Enums.getAlumnos(),null,Login.getToken(),
                Enums.getHeader(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        System.out.println(statusCode);
                        try {

                            String rs = new String(responseBody);
                            JSONObject obj = new JSONObject(rs);

                            user = new User(Integer.parseInt(obj.getString("id")),
                                    obj.getString("nombre"),
                                    obj.getString("apellidos"),
                                    Integer.parseInt(obj.getString("edad")),
                                    obj.getString("sexo"),
                                    obj.getString("carrera"));

                            present.dialog(user);
                            //Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            System.out.println("error "+e);
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

    public static void postLogin(String username, String password, final LoginPresent present){
        user = null;
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        HttpClient.post(Helpers.getBaseUrl()+ Enums.getLogin(),params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);
                try {
                    String rs = new String(responseBody);
                    JSONObject  obj = new JSONObject(rs);


                    Login.setUsername(obj.getString("username"));
                    Login.setToken("Token "+obj.getString("token"));

                    present.accerder();
                    //String content = new String(responseBody, "UTF-8");
                    //JSONObject obj = new JSONObject(content);
                   // Toast.makeText(getApplicationContext(),"Bienvenido "+ Login.getUsername(), Toast.LENGTH_LONG).show();
                   // goMain();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                switch(statusCode){
                    case 401:
                       // Toast.makeText(getApplicationContext(), "401 !", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                     //   Toast.makeText(getApplicationContext(), "404 !", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                       // Toast.makeText(getApplicationContext(), "400 !", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                       // Toast.makeText(getApplicationContext(), "500 !", Toast.LENGTH_LONG).show();
                        break;
                    default:
                       // Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_LONG).show();
                        break;
                }
                //String rs = new String(responseBody);
                //Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
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

    public static ArrayList<User> getLista() {
        return lista;
    }

    @Override
    public String toString(){
      return this.nombre;
    }


}
