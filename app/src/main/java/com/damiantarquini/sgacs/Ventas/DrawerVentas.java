package com.damiantarquini.sgacs.Ventas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.damiantarquini.sgacs.Ingreso.Login_Activity;
import com.damiantarquini.sgacs.R;
import com.damiantarquini.sgacs.Ventas.GestionVentas.MenuVentasPresup;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DrawerVentas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CalendarView calendar;
    TextView fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_ventas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ventas);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String nombreEmp = extras.getString("Nombre");
        int CuilE = extras.getInt("CuilE");
        int DNIE = extras.getInt("DNI");
        String DireccionE = extras.getString("Direccion");
        int Telefono = extras.getInt("Tel");
        String MailE = extras.getString("Mail");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ventas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_ventas);
        //con esto generamos el usuario en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        TextView TVnomb = (TextView) hView.findViewById(R.id.nombreempnav);
        TextView TVdesc = (TextView) hView.findViewById(R.id.cuilempnav);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null){
            String j =(String) b.get("Nombre");
            TVnomb.setText("Bienvenido, "+j);
            String k =(String) b.get("CuilE").toString();
            TVdesc.setText("Cuil: "+k);
        }
        navigationView.setNavigationItemSelectedListener(this);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setFocusable(false);
        fecha = (TextView)findViewById(R.id.textViewcalendar);
        setFechaActual();
    }

    @Override
    public void onBackPressed() {
        //Maneja la respuesta a la presión de botón "Atrás"
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ventas);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_ventas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja el menú desplegable.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            Intent i = new Intent(this,Login_Activity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gestionc) {
            Intent i = new Intent(this,GestionClientes.class);
            startActivity(i);
        } else if (id == R.id.nav_presupuesto) {
            Intent i = new Intent(this,MenuVentasPresup.class);
            startActivity(i);
        } else if (id == R.id.nav_ventas) {
            Intent i = new Intent(this,MenuVentasPresup.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ventas);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFechaActual()
    {
        fecha = (TextView) findViewById(R.id.textViewcalendar);
        final Calendar c = Calendar.getInstance();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        fecha.setText("Fecha: " + dia + "/" + mes + "/" + año);
    }
}
