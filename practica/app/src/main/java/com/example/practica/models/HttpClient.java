package com.example.practica.models;
import android.provider.SyncStateContract;

import com.example.practica.Helpers.Helpers;
import com.loopj.android.http.*;

public class HttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    //Helpers y Enums agregados desde el modelo ya que no se permiten atributos estaticos desde Helpers

    public static void get(String url, RequestParams params,String token,String header, AsyncHttpResponseHandler responseHandler) {
        client.addHeader(header,token);
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static  void getByid(int id,String url,RequestParams params,String token,String header, AsyncHttpResponseHandler asyncHttpResponseHandler){
        client.addHeader(header,token);
        client.get(url+id,params,asyncHttpResponseHandler);
    }


}
