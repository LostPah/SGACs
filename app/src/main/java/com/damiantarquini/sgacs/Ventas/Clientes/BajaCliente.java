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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.damiantarquini.sgacs.Libreria.VolleyRP;
import com.damiantarquini.sgacs.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

public class BajaCliente extends AppCompatActivity {

    private VolleyRP volley;
    private RequestQueue request;
    private static Context mContext;
    Button btnbajacliente;
    private static String IPVerifporDNI = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_VerificarPorDni.php?DniC=";
    private static String IPBajarCliente = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Cliente_DarDeBaja.php";
    private Boolean Existe = Boolean.FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baja_cliente_layout);

        final EditText etdni = (EditText) findViewById(R.id.etdardebaja_dni);

        //Linkeo de boton dar de baja del layout
        btnbajacliente = (Button) findViewById(R.id.btnbajacliente_baja);

        BajaCliente.mContext = getApplicationContext();

        //Controles para Librería Volley
        volley = VolleyRP.getInstance(this);
        request = Volley.newRequestQueue(mContext);
        request = volley.getRequestQueue();

        btnbajacliente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideSoftKeyboard();
                String vacio = "";
                if (etdni.getText().toString().equals(vacio)){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dnibaja);
                    til.setError("Error, no puede ser vacío");
                }
                else{
                    String DNI = etdni.getText().toString();
                    verificarExistencia(IPVerifporDNI+DNI);
                    if (Existe == Boolean.FALSE){
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dnibaja);
                        til.setError("DNI inexistente");
                    }
                    else {
                        hideSoftKeyboard();
                        TextInputLayout til = (TextInputLayout) findViewById(R.id.til_dnibaja);
                        til.setErrorEnabled(false);
                        til.setErrorEnabled(true);
                        final org.json.JSONObject json = new org.json.JSONObject();
                        try {
                            json.put("dnic",etdni.getText().toString());
                            json.put("activo","0");
                            bajarcliente(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static Context getAppContext() {
        return BajaCliente.mContext;
    }

    public void verificarExistencia(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                //Controlar el JSON
                try {
                    String estado = datos.getString("resultado");
                    if(estado.equals("Existe")){
                        //Toast.makeText(mContext,"DNI Existente", Toast.LENGTH_SHORT).show();
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

    public void bajarcliente(JSONObject jsonBody){
        JsonObjectRequest jrequest = new JsonObjectRequest
                (Request.Method.POST, IPBajarCliente, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario dado de baja con éxito.",LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext,"Usuario dado de baja con éxito. !",LENGTH_SHORT).show();
                    }
                });
        request.add(jrequest);
    }

}
