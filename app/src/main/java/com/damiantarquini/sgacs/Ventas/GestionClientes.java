package com.damiantarquini.sgacs.Ventas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.damiantarquini.sgacs.R;
import com.damiantarquini.sgacs.Ventas.Clientes.ActualizarCliente;
import com.damiantarquini.sgacs.Ventas.Clientes.AltaCliente;
import com.damiantarquini.sgacs.Ventas.Clientes.BajaCliente;

public class GestionClientes extends AppCompatActivity {

    Button btnregicliente;
    Button btnactucliente;
    Button btnbajacliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_clientes_layout);

        btnregicliente = (Button) findViewById(R.id.btnregicliente);
        btnregicliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),AltaCliente.class);
                startActivity(i);
            }
        });

        btnactucliente = (Button) findViewById(R.id.btnactucliente);
        btnactucliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),ActualizarCliente.class);
                startActivity(i);
            }
        });


        btnbajacliente = (Button) findViewById(R.id.btnbajacliente);
        btnbajacliente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),BajaCliente.class);
                startActivity(i);
            }
        });

    }

}
