package com.example.practica.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.example.practica.Enums.Enums;
import com.example.practica.Helpers.Helpers;
import com.example.practica.Login.Login;
import com.example.practica.R;
import com.example.practica.models.HttpClient;
import com.example.practica.presents.MainActivity;
import com.loopj.android.http.*;
import android.view.View;
//import android.content.Context;
import android.content.Intent;
import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUser;
    private EditText txtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnlogin = findViewById(R.id.login);
        txtUser = findViewById(R.id.user);
        txtPwd = findViewById(R.id.password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                login();
            }
        });
    }

    public void login(){
        RequestParams params = new RequestParams();
        params.put("username", txtUser.getText());
        params.put("password", txtPwd.getText());

        HttpClient.post(Helpers.getBaseUrl()+ Enums.getLogin(),params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                txtPwd.setText("");
                txtUser.setText("");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);
                try {
                    String rs = new String(responseBody);
                    JSONObject  obj = new JSONObject(rs);
                    Login.setUsername(obj.getString("username"));
                    Login.setToken("Token "+obj.getString("token"));
                    //String content = new String(responseBody, "UTF-8");
                    //JSONObject obj = new JSONObject(content);
                    Toast.makeText(getApplicationContext(),"Bienvenido "+ Login.getUsername(), Toast.LENGTH_LONG).show();
                    goMain();
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                switch(statusCode){
                    case 401:
                        Toast.makeText(getApplicationContext(), "401 !", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getApplicationContext(), "404 !", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Toast.makeText(getApplicationContext(), "400 !", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getApplicationContext(), "500 !", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_LONG).show();
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

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
