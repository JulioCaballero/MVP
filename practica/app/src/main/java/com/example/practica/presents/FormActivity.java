package com.example.practica.presents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

import com.example.practica.R;
import com.loopj.android.http.*;

import org.json.JSONObject;

import android.widget.Toast;

import cz.msebera.android.httpclient.Header;


public class FormActivity extends AppCompatActivity {

    private EditText txtNombres;
    private EditText txtApellidos;
    private EditText txtEdad;
    private EditText txtSexo;
    private EditText txtCarrera;

    private Button btnGuardar;
    private String token;
    private int id;
    private String http;
    private String header;
    private AsyncHttpClient  client;

    private  static FormActivity form;

    public FormActivity(){
        form = this;
        client = LoginActivity.getInstance().getClient();
    }

    public static FormActivity getInstance(){
        return form;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        txtNombres = findViewById(R.id.nombres);
        txtApellidos = findViewById(R.id.apellidos);
        txtEdad = findViewById(R.id.edad);
        txtSexo = findViewById(R.id.sexo);
        txtCarrera = findViewById(R.id.carrera);
        token = "Token "+LoginActivity.getInstance().getToken();
        btnGuardar = findViewById(R.id.guardar);
        id = ListActivity.getInstance().getId();
        http = "https://fc508409.ngrok.io/api/v1/alumnos/";
        header = "Authorization";
        //client = LoginActivity.getInstance().getClient();

        if(id != 0){
            getAlumno();
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(id != 0)
                    putCliente(id);
                else
                    postCliente();
            }
        });
    }

    public void getAlumno(){
        client.addHeader(header,token);
        client.get(http+id, new AsyncHttpResponseHandler() {
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
                    txtNombres.setText(obj.getString("nombre"));
                    txtApellidos.setText(obj.getString("apellidos"));
                    txtEdad.setText(obj.getString("edad"));
                    txtSexo.setText(obj.getString("sexo"));
                    txtCarrera.setText(obj.getString("carrera"));

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
                String rs = new String(responseBody);
                Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println(retryNo);
            }
        });
    }

    public void postCliente(){
        RequestParams params = new RequestParams();
        params.put("nombre", txtNombres.getText());
        params.put("apellidos", txtApellidos.getText());
        if(!txtEdad.getText().toString().equals(""))
            params.put("edad",Integer.parseInt(txtEdad.getText().toString()));
        params.put("sexo", txtSexo.getText());
        params.put("carrera", txtCarrera.getText() );

        client.addHeader(header,token);
        client.post(http,params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);

                    String rs = new String(responseBody);
                    System.out.println(rs);
                    //String content = new String(responseBody, "UTF-8");
                    //JSONObject obj = new JSONObject(content);
                    Toast.makeText(getApplicationContext(),"Alumno creado con exito", Toast.LENGTH_LONG).show();
                    goList();

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
                String rs = new String(responseBody);
                Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println(retryNo);
            }
        });

    }

    public void putCliente(int id){
        RequestParams params = new RequestParams();
        params.put("nombre", txtNombres.getText());
        params.put("apellidos", txtApellidos.getText());
        if(!txtEdad.getText().toString().equals(""))
            params.put("edad",Integer.parseInt(txtEdad.getText().toString()));
        params.put("sexo", txtSexo.getText());
        params.put("carrera", txtCarrera.getText() );

        client.addHeader(header,token);
        client.put(http+id,params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(statusCode);

                    String rs = new String(responseBody);
                    System.out.println(rs);
                    System.out.println("Entro en el put....");
                    //String content = new String(responseBody, "UTF-8");
                    Toast.makeText(getApplicationContext(),"Alumno actualizado con exito", Toast.LENGTH_LONG).show();
                    System.out.println("Entro en el put....");
                    goList();

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
                String rs = new String(responseBody);
                Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println(retryNo);
            }
        });

    }

    public void goList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        token = "Token "+LoginActivity.getInstance().getToken();
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
