package com.damiantarquini.sgacs.Ventas.Clientes;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.damiantarquini.sgacs.Libreria.VolleyRP;
import com.damiantarquini.sgacs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

public class ActualizarCliente extends AppCompatActivity {

    private static String IPActualizarCliente = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_ActualizarDatos.php";
    private static String IPObtenerporDNI = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_ObtenerDatosPorDni.php?DniC=";
    private VolleyRP volley;
    private RequestQueue request;
    private static Context mContext;
    private Cliente cliente;

    //String globales para manejar traspaso de datos
    private Button btnactuinfocliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_cliente);

        ActualizarCliente.mContext = getApplicationContext();

        //Controles para Librería Volley
        volley = VolleyRP.getInstance(mContext);
        request = Volley.newRequestQueue(mContext);
        request = volley.getRequestQueue();

        final EditText nombre,dni,alias,telefono,domic,reflug;
        //Linkeo de botones por id con su layout.
        nombre = (EditText) findViewById(R.id.etnvuser_nombre_ac);
        dni = (EditText) findViewById(R.id.etnvuser_dni_ac);
        alias = (EditText) findViewById(R.id.etnvuser_alias_ac);
        telefono = (EditText) findViewById(R.id.etnvuser_telefono_ac);
        domic = (EditText) findViewById(R.id.etnvuser_domicilio_ac);
        reflug = (EditText) findViewById(R.id.etnvuser_reflug_ac);
        btnactuinfocliente = (Button) findViewById(R.id.btnactualizarcliente_ac);
        Button btnobtenerinfo = (Button) findViewById(R.id.btnobtenerinfo_ac);


        btnactuinfocliente.setVisibility(View.INVISIBLE);
        cliente = new Cliente("","","","","","");

        btnobtenerinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard();
                String vacio = "";
                ObtenerDatosClientes(IPObtenerporDNI+dni.getText().toString());
                nombre.setText(cliente.getNomC());
                alias.setText(cliente.getAliasC());
                telefono.setText(cliente.getTelC());
                domic.setText(cliente.getDomiC());
                reflug.setText(cliente.getRefLug());
                if(cliente.getNomC().toString().equals(vacio)){
                }
                else {
                    alias.setFocusable(true);
                    telefono.setFocusable(true);
                    domic.setFocusable(true);
                    reflug.setFocusable(true);
                    btnactuinfocliente.setVisibility(View.VISIBLE);
                }
            }
        });

        btnactuinfocliente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard();
                String vacio = "";
                final org.json.JSONObject json = new org.json.JSONObject();
                try {
                    json.put("dnic",dni.getText().toString());
                    if(domic.getText().toString()==vacio){domic.setText("");}
                    if(alias.getText().toString()==vacio){alias.setText("");}
                    if(reflug.getText().toString()==vacio){reflug.setText("");}
                    if(telefono.getText().toString()==vacio){reflug.setText("");}
                    json.put("telc",telefono.getText().toString());
                    json.put("domic",domic.getText().toString());
                    json.put("aliasc",alias.getText().toString());
                    json.put("reflug",reflug.getText().toString());
                    ActualizarCliente(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void ActualizarCliente(JSONObject jsonBody){
        JsonObjectRequest jrequest = new JsonObjectRequest
                (Request.Method.POST, IPActualizarCliente, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Actualizado con éxito.",LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Actualizado con éxito. !",LENGTH_SHORT).show();
                    }
                });
        request.add(jrequest);
    }



    public void ObtenerDatosClientes(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                //Controlar el JSON
                try {
                    String estado = datos.getString("resultado");
                    if(estado.equals("Correcto")){
                        JSONObject Jsondatos = new JSONObject(datos.getString("datos"));
                        cliente = new Cliente(Jsondatos.getString("dniC"),Jsondatos.getString("nomC"),Jsondatos.getString("telC"),Jsondatos.getString("domiC"),Jsondatos.getString("aliasC"),Jsondatos.getString("refLug"));
                    }
                    else{
                        Toast.makeText(mContext,"DNI Inexistente", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,"Ocurrio un error, por favor contactese con el administrador", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,request,mContext,volley);
    }

    public static Context getAppContext() {
        return ActualizarCliente.mContext;
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
