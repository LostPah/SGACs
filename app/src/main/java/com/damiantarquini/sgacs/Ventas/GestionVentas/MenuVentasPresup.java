package com.damiantarquini.sgacs.Ventas.GestionVentas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.damiantarquini.sgacs.R;

public class MenuVentasPresup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ventas_presup);

        Button btnventapresup = (Button) findViewById(R.id.btnmenuventas_nvaventaxpresup);
        Button btnventadirecta = (Button) findViewById(R.id.btnmenuventas_nvaventa);

        btnventapresup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Ventaxpresup.class);
                startActivity(i);
            }
        });

        btnventadirecta.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),Ventadirecta.class);
                startActivity(i);
            }
        });
    }
}
