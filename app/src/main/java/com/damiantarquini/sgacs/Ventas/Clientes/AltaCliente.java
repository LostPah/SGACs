package com.damiantarquini.sgacs.Ventas.Clientes;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.damiantarquini.sgacs.Libreria.VolleyRP;
import com.damiantarquini.sgacs.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

public class AltaCliente extends AppCompatActivity {

    private static String IPAgregarCliente = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_AgregarNuevo.php";
    private static String IPVerifporDNI = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_VerificarPorDni.php?DniC=";
    private static String IPAltaExistente = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_DarDeBaja.php";
    private static String IPVerifAlta = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_VerifcarClienteAltaBaja.php?DniC=";
    private VolleyRP volley;
    private RequestQueue request;
    private static Context mContext;
    private Boolean Existe = Boolean.TRUE;
    private Boolean Estado = Boolean.FALSE;
    private CheckBox checkexistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_cliente_layout);

        //Linkeo de botones por id con su layout.
        final EditText nombre = (EditText) findViewById(R.id.etnvuser_nombre);
        final EditText dni = (EditText) findViewById(R.id.etnvuser_dni);
        final EditText alias = (EditText) findViewById(R.id.etnvuser_alias);
        final EditText telefono = (EditText) findViewById(R.id.etnvuser_telefono);
        final EditText domic = (EditText) findViewById(R.id.etnvuser_domicilio);
        final EditText reflug = (EditText) findViewById(R.id.etnvuser_reflug);
        Button btnconfiruser = (Button) findViewById(R.id.btnagregaruserconfir);
        checkexistente = (CheckBox) findViewById(R.id.checkuserexistente);


        Cliente cliente = new Cliente(nombre.getText().toString(),dni.getText().toString(),alias.getText().toString(),telefono.getText().toString(),domic.getText().toString(),reflug.getText().toString());

        AltaCliente.mContext = getApplicationContext();

        //Controles para Librería Volley
        volley = VolleyRP.getInstance(this);
        request = Volley.newRequestQueue(mContext);
        request = volley.getRequestQueue();

        btnconfiruser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard();
                String vacio = "";
                if (nombre.getText().toString().equals(vacio) || dni.getText().toString().equals(vacio)){
                    if (nombre.getText().toString().equals(vacio) && dni.getText().toString().equals(vacio)){
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.til_nombre);
                        til.setError("Error, no puede ser vacío");
                        TextInputLayout tila = (TextInputLayout) findViewById(R.id.til_dni);
                        tila.setError("Error, no puede ser vacío");
                    }
                    else {
                        if (nombre.getText().toString().equals(vacio)){
                            TextInputLayout til = (TextInputLayout) findViewById(R.id.til_nombre);
                            til.setError("Error, no puede ser vacío");
                            TextInputLayout tila = (TextInputLayout) findViewById(R.id.til_dni);
                            tila.setErrorEnabled(false);
                            tila.setErrorEnabled(true);
                        }
                        else{
                            TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dni);
                            til.setError("Error, no puede ser vacío");
                            TextInputLayout tila = (TextInputLayout) findViewById(R.id.til_nombre);
                            tila.setErrorEnabled(false);
                            tila.setErrorEnabled(true);
                        }
                    }
                }
                else{
                    String DNI = dni.getText().toString();
                    verificarExistencia(IPVerifporDNI+DNI);
                    if (Existe == Boolean.TRUE){
                        if(checkexistente.isChecked()){
                            final org.json.JSONObject json = new org.json.JSONObject();
                            try {
                                json.put("dnic",dni.getText().toString());
                                json.put("activo","1");
                                altaexistente(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dni);
                            til.setError("DNI Existente");
                        }
                    }
                    else {
                        hideSoftKeyboard();
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dni);
                        til.setErrorEnabled(false);
                        til.setErrorEnabled(true);
                        TextInputLayout tila = (TextInputLayout) findViewById(R.id.til_nombre);
                        tila.setErrorEnabled(false);
                        tila.setErrorEnabled(true);
                        final org.json.JSONObject json = new org.json.JSONObject();
                        try {
                            json.put("dnic",dni.getText().toString());
                            json.put("nomc",nombre.getText().toString());
                            if(domic.getText().toString()==vacio){domic.setText("");}
                            if(alias.getText().toString()==vacio){alias.setText("");}
                            if(reflug.getText().toString()==vacio){reflug.setText("");}
                            if(telefono.getText().toString()==vacio){reflug.setText("");}
                            json.put("telc",telefono.getText().toString());
                            json.put("domic",domic.getText().toString());
                            json.put("aliasc",alias.getText().toString());
                            json.put("reflug",reflug.getText().toString());
                            json.put("activo","1");
                            agregarcliente(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }


    public static Context getAppContext() {
        return AltaCliente.mContext;
    }

    public void agregarcliente(JSONObject jsonBody){
        JsonObjectRequest jrequest = new JsonObjectRequest
                (Request.Method.POST, IPAgregarCliente, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario Agregado",LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario Agregado. !",LENGTH_SHORT).show();
                    }
                });
        request.add(jrequest);
    }

    public void verificarExistencia(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                //Controlar el JSON
                try {
                    String estado = datos.getString("resultado");
                    if(estado.equals("Existe")){
                        Toast.makeText(mContext,"DNI Existente", Toast.LENGTH_SHORT).show();
                        Existe = Boolean.TRUE;
                    }
                    else{
                        Existe = Boolean.FALSE;
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
        VolleyRP.addToQueue(solicitud,request,this,volley);
    }

    public void altaexistente(JSONObject jsonBody){
        JsonObjectRequest jrequest = new JsonObjectRequest
                (Request.Method.POST, IPAltaExistente, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario dado de alta con éxito.",LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario dado de alta con éxito. !",LENGTH_SHORT).show();
                    }
                });
        request.add(jrequest);
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
