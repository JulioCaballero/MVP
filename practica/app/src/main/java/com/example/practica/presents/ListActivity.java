package com.example.practica.presents;

import androidx.appcompat.app.AppCompatActivity;

//import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONArray;

import com.example.practica.R;
import com.loopj.android.http.*;
import android.widget.Toast;
import android.widget.Button;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity {

    private TableLayout tlTabla;
    private TableRow fila;
    private TextView tvNombres;
    private TextView tvApellidos;
    private TextView tvEdad;
    private TextView tvSexo;
    private TextView tvCarrera;
    private TextView tvOpciones;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnCrear;
    private Button btnLogout;
    private String token;
    private int id;
    private String http;
    private String header;
    private AsyncHttpClient  client;


    private  static ListActivity list;

    public ListActivity(){
        list = this;
        client = LoginActivity.getInstance().getClient();
    }

    public static ListActivity getInstance(){
        return list;
    }

    public int getId(){
        return id;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        http = "https://fc508409.ngrok.io/api/v1/alumnos/";
        header = "Authorization";
        tlTabla = findViewById(R.id.tlTabla);
        token = "Token "+LoginActivity.getInstance().getToken();
        btnCrear = findViewById(R.id.button);
        btnLogout = findViewById(R.id.button2);
        getAlumnos();
       // client = LoginActivity.getInstance().getClient();
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                id = 0;
                goForm();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goLogin();
            }
        });


    }

    public void getAlumnos(){
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
                    crearTabla(obj);
                    //System.out.println(obj.getJSONObject(0).getString("nombres"));
                    //Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
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



    public void crearTabla(JSONArray obj){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutNombre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutApellidos = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutEdad = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutSexo = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutCarrera = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        for(int i = -1 ; i < obj.length(); i++) {
            fila = new TableRow(this);
            fila.setLayoutParams(layoutFila);

            if(i == -1) {
                tvNombres = new TextView(this);
                tvNombres.setText("Nombres");
                tvNombres.setGravity(Gravity.CENTER);
                tvNombres.setBackgroundColor(Color.GRAY);
                tvNombres.setTextColor(Color.WHITE);
                tvNombres.setPadding(10, 10, 10, 10);
                tvNombres.setLayoutParams(layoutNombre);
                fila.addView(tvNombres);

                tvApellidos = new TextView(this);
                tvApellidos.setText("Apellidos");
                tvApellidos.setGravity(Gravity.CENTER);
                tvApellidos.setBackgroundColor(Color.GRAY);
                tvApellidos.setTextColor(Color.WHITE);
                tvApellidos.setPadding(10, 10, 10, 10);
                tvApellidos.setLayoutParams(layoutNombre);
                fila.addView(tvApellidos);

                tvEdad = new TextView(this);
                tvEdad.setText("Edad");
                tvEdad.setGravity(Gravity.CENTER);
                tvEdad.setBackgroundColor(Color.GRAY);
                tvEdad.setTextColor(Color.WHITE);
                tvEdad.setPadding(10, 10, 10, 10);
                tvEdad.setLayoutParams(layoutNombre);
                fila.addView(tvEdad);

                tvSexo = new TextView(this);
                tvSexo.setText("Sexo");
                tvSexo.setGravity(Gravity.CENTER);
                tvSexo.setBackgroundColor(Color.GRAY);
                tvSexo.setTextColor(Color.WHITE);
                tvSexo.setPadding(10, 10, 10, 10);
                tvSexo.setLayoutParams(layoutNombre);
                fila.addView(tvSexo);

                tvCarrera = new TextView(this);
                tvCarrera.setText("Carrera");
                tvCarrera.setGravity(Gravity.CENTER);
                tvCarrera.setBackgroundColor(Color.GRAY);
                tvCarrera.setTextColor(Color.WHITE);
                tvCarrera.setPadding(10, 10, 10, 10);
                tvCarrera.setLayoutParams(layoutNombre);
                fila.addView(tvCarrera);

                tvOpciones = new TextView(this);
                tvOpciones.setText("");
                tvOpciones.setGravity(Gravity.CENTER);
                tvOpciones.setBackgroundColor(Color.GRAY);
                tvOpciones.setTextColor(Color.WHITE);
                tvOpciones.setPadding(10, 10, 10, 10);
                tvOpciones.setLayoutParams(layoutNombre);
                fila.addView(tvOpciones);

                tvOpciones = new TextView(this);
                tvOpciones.setText("");
                tvOpciones.setGravity(Gravity.CENTER);
                tvOpciones.setBackgroundColor(Color.GRAY);
                tvOpciones.setTextColor(Color.WHITE);
                tvOpciones.setPadding(10, 10, 10, 10);
                tvOpciones.setLayoutParams(layoutNombre);
                fila.addView(tvOpciones);


                tlTabla.addView(fila);
            } else {
                try {
                    tvNombres = new TextView(this);
                    tvNombres.setGravity(Gravity.CENTER);
                    tvNombres.setText(obj.getJSONObject(i).getString("nombre"));
                    tvNombres.setPadding(10, 10, 10, 10);
                    tvNombres.setLayoutParams(layoutNombre);
                    fila.addView(tvNombres);

                    tvApellidos = new TextView(this);
                    tvApellidos.setGravity(Gravity.CENTER);
                    tvApellidos.setText(obj.getJSONObject(i).getString("apellidos"));
                    tvApellidos.setPadding(10, 10, 10, 10);
                    tvApellidos.setLayoutParams(layoutApellidos);
                    fila.addView(tvApellidos);

                    tvEdad = new TextView(this);
                    tvEdad.setGravity(Gravity.CENTER);
                    tvEdad.setText(obj.getJSONObject(i).getString("edad"));
                    tvEdad.setPadding(10, 10, 10, 10);
                    tvEdad.setLayoutParams(layoutEdad);
                    fila.addView(tvEdad);

                    tvSexo = new TextView(this);
                    tvSexo.setGravity(Gravity.CENTER);
                    tvSexo.setText(obj.getJSONObject(i).getString("sexo"));
                    tvSexo.setPadding(10, 10, 10, 10);
                    tvSexo.setLayoutParams(layoutSexo);
                    fila.addView(tvSexo);

                    tvCarrera = new TextView(this);
                    tvCarrera.setGravity(Gravity.CENTER);
                    tvCarrera.setText(obj.getJSONObject(i).getString("carrera"));
                    tvCarrera.setPadding(10, 10, 10, 10);
                    tvCarrera.setLayoutParams(layoutCarrera);
                    fila.addView(tvCarrera);

                    btnEditar = new Button(this);
                    btnEditar.setGravity(Gravity.CENTER);
                    btnEditar.setText("Editar");
                    btnEditar.setBackgroundColor(Color.BLUE);
                    btnEditar.setTextColor(Color.WHITE);
                    btnEditar.setTag(obj.getJSONObject(i).getString("id"));
                    btnEditar.setPadding(1, 1, 1, 1);
                    fila.addView(btnEditar);

                    btnEditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            int id2 = Integer.parseInt(v.getTag().toString());
                            System.out.println("La id de editar es :"+id2);
                            id = id2;
                            goForm();
                        }
                    });

                    btnEliminar = new Button(this);
                    btnEliminar.setGravity(Gravity.CENTER);
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setBackgroundColor(Color.RED);
                    btnEliminar.setTextColor(Color.WHITE);
                    btnEliminar.setTag(obj.getJSONObject(i).getString("id"));
                    btnEliminar.setPadding(1, 1, 1, 1);
                    fila.addView(btnEliminar);

                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            int id = Integer.parseInt(v.getTag().toString());
                            System.out.println("La id de eliminar es :"+id);
                            deleteAlumno(id);
                        }
                    });

                    tlTabla.addView(fila);
                }catch (Exception e){
                }
            }
        }
    }

    public void deleteAlumno(int id){
        client.addHeader(header,token);
        client.delete(http+id, new AsyncHttpResponseHandler() {
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
                    Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
                    tlTabla.removeAllViews();
                    getAlumnos();

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

    public void goForm(){
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    public void goLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
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
