package com.example.practica.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practica.Helpers.Helpers;
import com.example.practica.models.HttpClient;
import com.example.practica.presents.LoginActivity;
import com.example.practica.presents.MainActivityPresent;
import com.example.practica.R;
import com.example.practica.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment implements MainActivityPresent.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    private Button btnDetalles;
    private Button btnCrear;
    private Button btnLogout;
    private String token;
    private int id;
    private String http;
    private String header;
    private AsyncHttpClient client;
    private View root;
    private ArrayList<User> lista;
    private MainActivityPresent present;

    private OnFragmentInteractionListener mListener;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            lista = new ArrayList<>();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new AsyncHttpClient();
        root =  inflater.inflate(R.layout.fragment_first, container,false);
        http = "https://58e8ab5e.ngrok.io/api/v1/alumnos/";
        header = "Authorization";
        tlTabla =  root.findViewById(R.id.tlTablafragment);
        token = "Token "+ LoginActivity.getInstance().getToken();
        present = new MainActivityPresent(this);


        //System.out.println("Nombre: "+lista.get(0).getNombre());
        User.getAlumnos(lista,client,header,token,http);
        present.llenarLista(lista);
        System.out.println("Nombre: "+lista.get(0).getNombre());
        //getAlumnos();
        // Inflate the layout for this fragment
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
                        Toast.makeText(getContext(), "401 !", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "404 !", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Toast.makeText(getContext(), "400 !", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "500 !", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "Error !", Toast.LENGTH_LONG).show();
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

    public void crearTabla(JSONArray obj){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutNombre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutApellidos = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutEdad = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutSexo = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutCarrera = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        for(int i = -1 ; i < obj.length(); i++) {
            fila = new TableRow(getContext());
            fila.setLayoutParams(layoutFila);

            if(i == -1) {
                tvNombres = new TextView(getContext());
                tvNombres.setText("Nombres");
                tvNombres.setGravity(Gravity.CENTER);
                tvNombres.setBackgroundColor(Color.GRAY);
                tvNombres.setTextColor(Color.WHITE);
                tvNombres.setPadding(10, 10, 10, 10);
                tvNombres.setLayoutParams(layoutNombre);
                fila.addView(tvNombres);

                tvApellidos = new TextView(getContext());
                tvApellidos.setText("Apellidos");
                tvApellidos.setGravity(Gravity.CENTER);
                tvApellidos.setBackgroundColor(Color.GRAY);
                tvApellidos.setTextColor(Color.WHITE);
                tvApellidos.setPadding(10, 10, 10, 10);
                tvApellidos.setLayoutParams(layoutNombre);
                fila.addView(tvApellidos);

              /*  tvEdad = new TextView(getView().getContext());
                tvEdad.setText("Edad");
                tvEdad.setGravity(Gravity.CENTER);
                tvEdad.setBackgroundColor(Color.GRAY);
                tvEdad.setTextColor(Color.WHITE);
                tvEdad.setPadding(10, 10, 10, 10);
                tvEdad.setLayoutParams(layoutNombre);
                fila.addView(tvEdad);

                tvSexo = new TextView(getView().getContext());
                tvSexo.setText("Sexo");
                tvSexo.setGravity(Gravity.CENTER);
                tvSexo.setBackgroundColor(Color.GRAY);
                tvSexo.setTextColor(Color.WHITE);
                tvSexo.setPadding(10, 10, 10, 10);
                tvSexo.setLayoutParams(layoutNombre);
                fila.addView(tvSexo);

                tvCarrera = new TextView(getView().getContext());
                tvCarrera.setText("Carrera");
                tvCarrera.setGravity(Gravity.CENTER);
                tvCarrera.setBackgroundColor(Color.GRAY);
                tvCarrera.setTextColor(Color.WHITE);
                tvCarrera.setPadding(10, 10, 10, 10);
                tvCarrera.setLayoutParams(layoutNombre);
                fila.addView(tvCarrera);*/

                tvOpciones = new TextView(getContext());
                tvOpciones.setText("");
                tvOpciones.setGravity(Gravity.CENTER);
                tvOpciones.setBackgroundColor(Color.GRAY);
                tvOpciones.setTextColor(Color.WHITE);
                tvOpciones.setPadding(10, 10, 10, 10);
                tvOpciones.setLayoutParams(layoutNombre);
                fila.addView(tvOpciones);

                tvOpciones = new TextView(getContext());
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
                    tvNombres = new TextView(getContext());
                    tvNombres.setGravity(Gravity.CENTER);
                    tvNombres.setText(obj.getJSONObject(i).getString("nombre"));
                    tvNombres.setPadding(10, 10, 10, 10);
                    tvNombres.setLayoutParams(layoutNombre);
                    fila.addView(tvNombres);

                    tvApellidos = new TextView(getContext());
                    tvApellidos.setGravity(Gravity.CENTER);
                    tvApellidos.setText(obj.getJSONObject(i).getString("apellidos"));
                    tvApellidos.setPadding(10, 10, 10, 10);
                    tvApellidos.setLayoutParams(layoutApellidos);
                    fila.addView(tvApellidos);

                 /*   tvEdad = new TextView(this);
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
                    fila.addView(tvCarrera);*/

                    btnDetalles = new Button(getContext());
                    btnDetalles.setGravity(Gravity.CENTER);
                    btnDetalles.setText("Detalles");
                    btnDetalles.setBackgroundColor(Color.BLUE);
                    btnDetalles.setTextColor(Color.WHITE);
                    btnDetalles.setTag(obj.getJSONObject(i).getString("id"));
                    btnDetalles.setPadding(1, 1, 1, 1);
                    fila.addView(btnDetalles);

                    btnDetalles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            int id2 = Integer.parseInt(v.getTag().toString());
                            System.out.println("La id de detalles es :"+id2);
                            id = id2;
                            getAlumno();
                        }
                    });

                  /*  btnEditar = new Button(getView().getContext());
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

                    btnEliminar = new Button(getView().getContext());
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
                    });*/

                    tlTabla.addView(fila);
                }catch (Exception e){
                }
            }
        }
    }

    public void crearTablaArray(ArrayList<User> obj){
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutNombre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutApellidos = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutEdad = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutSexo = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutCarrera = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        for(int i = -1 ; i < obj.size(); i++) {
            fila = new TableRow(getContext());
            fila.setLayoutParams(layoutFila);

            if(i == -1) {
                tvNombres = new TextView(getContext());
                tvNombres.setText("Nombres");
                tvNombres.setGravity(Gravity.CENTER);
                tvNombres.setBackgroundColor(Color.GRAY);
                tvNombres.setTextColor(Color.WHITE);
                tvNombres.setPadding(10, 10, 10, 10);
                tvNombres.setLayoutParams(layoutNombre);
                fila.addView(tvNombres);

                tvApellidos = new TextView(getContext());
                tvApellidos.setText("Apellidos");
                tvApellidos.setGravity(Gravity.CENTER);
                tvApellidos.setBackgroundColor(Color.GRAY);
                tvApellidos.setTextColor(Color.WHITE);
                tvApellidos.setPadding(10, 10, 10, 10);
                tvApellidos.setLayoutParams(layoutNombre);
                fila.addView(tvApellidos);

              /*  tvEdad = new TextView(getView().getContext());
                tvEdad.setText("Edad");
                tvEdad.setGravity(Gravity.CENTER);
                tvEdad.setBackgroundColor(Color.GRAY);
                tvEdad.setTextColor(Color.WHITE);
                tvEdad.setPadding(10, 10, 10, 10);
                tvEdad.setLayoutParams(layoutNombre);
                fila.addView(tvEdad);

                tvSexo = new TextView(getView().getContext());
                tvSexo.setText("Sexo");
                tvSexo.setGravity(Gravity.CENTER);
                tvSexo.setBackgroundColor(Color.GRAY);
                tvSexo.setTextColor(Color.WHITE);
                tvSexo.setPadding(10, 10, 10, 10);
                tvSexo.setLayoutParams(layoutNombre);
                fila.addView(tvSexo);

                tvCarrera = new TextView(getView().getContext());
                tvCarrera.setText("Carrera");
                tvCarrera.setGravity(Gravity.CENTER);
                tvCarrera.setBackgroundColor(Color.GRAY);
                tvCarrera.setTextColor(Color.WHITE);
                tvCarrera.setPadding(10, 10, 10, 10);
                tvCarrera.setLayoutParams(layoutNombre);
                fila.addView(tvCarrera);*/

                tvOpciones = new TextView(getContext());
                tvOpciones.setText("");
                tvOpciones.setGravity(Gravity.CENTER);
                tvOpciones.setBackgroundColor(Color.GRAY);
                tvOpciones.setTextColor(Color.WHITE);
                tvOpciones.setPadding(10, 10, 10, 10);
                tvOpciones.setLayoutParams(layoutNombre);
                fila.addView(tvOpciones);

                tvOpciones = new TextView(getContext());
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
                    tvNombres = new TextView(getContext());
                    tvNombres.setGravity(Gravity.CENTER);
                    tvNombres.setText(obj.get(i).getNombre());
                    tvNombres.setPadding(10, 10, 10, 10);
                    tvNombres.setLayoutParams(layoutNombre);
                    fila.addView(tvNombres);

                    tvApellidos = new TextView(getContext());
                    tvApellidos.setGravity(Gravity.CENTER);
                    tvApellidos.setText(obj.get(i).getApellidos());
                    tvApellidos.setPadding(10, 10, 10, 10);
                    tvApellidos.setLayoutParams(layoutApellidos);
                    fila.addView(tvApellidos);

                 /*   tvEdad = new TextView(this);
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
                    fila.addView(tvCarrera);*/

                    btnDetalles = new Button(getContext());
                    btnDetalles.setGravity(Gravity.CENTER);
                    btnDetalles.setText("Detalles");
                    btnDetalles.setBackgroundColor(Color.BLUE);
                    btnDetalles.setTextColor(Color.WHITE);
                    btnDetalles.setTag(obj.get(i).getId());
                    btnDetalles.setPadding(1, 1, 1, 1);
                    fila.addView(btnDetalles);

                    btnDetalles.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            int id2 = Integer.parseInt(v.getTag().toString());
                            System.out.println("La id de detalles es :"+id2);
                            id = id2;
                            getAlumno();
                        }
                    });

                  /*  btnEditar = new Button(getView().getContext());
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

                    btnEliminar = new Button(getView().getContext());
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
                    });*/

                    tlTabla.addView(fila);
                }catch (Exception e){
                }
            }
        }
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

                    AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                    alerta.setMessage("Nombre: "+obj.getString("nombre")+"\nApellido: "+
                            obj.getString("apellidos") + "\nEdad: " + obj.getString("edad") +
                            "\nSexo: " +obj.getString("sexo"));
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onFinish();
                        }
                    });
                    AlertDialog titlulo = alerta.create();
                    titlulo.show();

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                switch(statusCode){
                    case 401:
                        Toast.makeText(getContext(), "401 !", Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getContext(), "404 !", Toast.LENGTH_LONG).show();
                        break;
                    case 400:
                        Toast.makeText(getContext(), "400 !", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(getContext(), "500 !", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "Error !", Toast.LENGTH_LONG).show();
                        break;
                }
                String rs = new String(responseBody);
          //      Toast.makeText(getApplicationContext(),rs, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRetry(int retryNo) {
                System.out.println(retryNo);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void llenarTabla(ArrayList<User> lista) {
        //hacer tabla con la lista
        //crearTabla(lista);
        //lista.get(0).getNombre();
        crearTablaArray(lista);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
