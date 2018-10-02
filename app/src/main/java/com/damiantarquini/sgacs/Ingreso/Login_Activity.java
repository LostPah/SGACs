package com.damiantarquini.sgacs.Ingreso;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.damiantarquini.sgacs.Libreria.VolleyRP;
import com.damiantarquini.sgacs.R;
import com.damiantarquini.sgacs.Ventas.DrawerVentas;

import org.json.JSONException;
import org.json.JSONObject;


public class Login_Activity extends AppCompatActivity {

    private EditText etcuile;
    private EditText etpass;
    private Button btingresar;
    private static String IP = "http://sistemadegestion.000webhostapp.com/SGACs/Login/Empleado_ObtenerdatosPorCuil.php?CuilE=";
    private RequestQueue mRequest;
    private VolleyRP volley;
    private String NCUIL = "";
    private String PASSWORD = "";
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etcuile = (EditText) findViewById(R.id.etcuilE);
        etpass = (EditText) findViewById(R.id.etpass);
        btingresar = (Button) findViewById(R.id.btingresar);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        btingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarLogin(etcuile.getText().toString(),etpass.getText().toString());
            }
        });
    }

    public void verificarLogin(String cuile, String pass){
        NCUIL = cuile;
        PASSWORD = pass;
        solicitudJSON(IP+cuile);
    }


    public void solicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                VerificarPassword(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Activity.this,"Ocurrio un error, por favor contactese con el administrador",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void VerificarPassword(JSONObject datos){
        //Controlar el JSON
        try {
            String estado = datos.getString("resultado");
            if(estado.equals("Correcto")){
                JSONObject Jsondatos = new JSONObject(datos.getString("datos"));
                String cuil = Jsondatos.getString("cuilE");
                String contraseña = Jsondatos.getString("clave");
                if (cuil.equals(NCUIL) && contraseña.equals(PASSWORD)){
                    Toast.makeText(this,"Usted se ha logeado correctamente",Toast.LENGTH_SHORT).show();
                    int cagetoriaemp = Jsondatos.getInt("esAdmin");
                    if (cagetoriaemp == 0) {
                        Intent i = new Intent(this,DrawerVentas.class);
                        Bundle extras = new Bundle();
                        extras.putString("Nombre",Jsondatos.getString("nombre"));
                        extras.putInt("CuilE",Jsondatos.getInt("cuilE"));
                        extras.putInt("DNI",Jsondatos.getInt("dnie"));
                        extras.putString("Direccion",Jsondatos.getString("direccion"));
                        extras.putInt("Tel",Jsondatos.getInt("tel"));
                        extras.putString("Mail",Jsondatos.getString("mail"));
                        i.putExtras(extras);
                        titulo = (TextView) findViewById(R.id.titulologin);
                        titulo.setText("Bienvenido, "+Jsondatos.getString("nombre"));
                        titulo.setVisibility(View.VISIBLE);
                        titulo.setTextColor(Color.WHITE);
                        titulo.setGravity(Gravity.CENTER);
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Toast.makeText(this,"Cuil o Clave Incorrecto.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this,estado,Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e){

        }
    }
}

