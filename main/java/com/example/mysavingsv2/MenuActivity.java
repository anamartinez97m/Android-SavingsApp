package com.example.mysavingsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper bbdd;

    //Esto son datos de prueba
    List<String> ingresosMoney = Arrays.asList("1300", "1200", "1200", "1200");
    List<String> comidasMoney = Arrays.asList("100", "11.99", "8.5", "7.25");
    List<String> ocioMoney = Arrays.asList("12", "5", "18", "10","12", "5", "18", "10","12", "5", "18", "10",
            "12", "5", "18", "10","12", "5", "18", "10");
    List<String> viviendaMoney = Arrays.asList("150", "200", "90", "85");
    List<String> transporteMoney = Arrays.asList("100", "25", "20", "110");
    List<String> otrosMoney = Arrays.asList("50", "25", "10", "70");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        Button buttonAdd = findViewById(R.id.buttonAddConcept);
        final Button buttonCat1 = findViewById(R.id.buttonCat1);
        final Button buttonCat2 = findViewById(R.id.buttonCat2);
        final Button buttonCat3 = findViewById(R.id.buttonCat3);
        final Button buttonCat4 = findViewById(R.id.buttonCat4);
        final Button buttonCat5 = findViewById(R.id.buttonCat5);
        final Button buttonCat6 = findViewById(R.id.buttonCat6);

        TextView totalMenu = findViewById(R.id.textViewTotalNumberMenu);

        String showNumber = String.format("%.2f", totalWallet());

        Log.d("tag", ""+showNumber);

        totalMenu.setText(showNumber);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, AddConceptActivity.class);
                startActivity(intent);
            }
        });

        buttonCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat1.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });

        buttonCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat2.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });

        buttonCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat3.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });

        buttonCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat4.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });

        buttonCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat5.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });

        buttonCat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombreCat = (String)buttonCat6.getText();

                Intent intent = new Intent(MenuActivity.this, ConceptsCatActivity.class);

                Bundle transport = new Bundle();
                transport.putString("nombreCat", nombreCat);
                intent.putExtras(transport);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intentProfile = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(MenuActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private float totalWallet(){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        float resultIngresos = 0;
        float resultGastos = 0;

        Cursor fila = wDatabase.rawQuery("SELECT category, moneyConcept FROM historial WHERE username " +
                "LIKE '" + LoginActivity.loggedUser + "'",null);

        List<Float> acuIngresos = new ArrayList<>();
        List<Float> acuGastos = new ArrayList<>();

        if(fila.moveToFirst()){
            fila.getString(0);
            fila.getString(1);
            if(fila.getString(0).equals("Ingresos")){
                while(fila.moveToNext()){
                    acuIngresos.add(Float.parseFloat(fila.getString(1)));
                }
            } else {
                while(fila.moveToNext()){
                    if(fila.getString(0).equals("Ingresos")){
                        acuIngresos.add(Float.parseFloat(fila.getString(1)));
                    } else {
                        acuGastos.add(Float.parseFloat(fila.getString(1)));
                    }
                }
            }
        }

        //Esto son los datos de prueba
        for(int i = 0; i < ingresosMoney.size(); i++){
            resultIngresos += Float.parseFloat(ingresosMoney.get(i));
        }

        for(int i = 0; i < ocioMoney.size(); i++){
            resultGastos += Float.parseFloat(ocioMoney.get(i));
        }

        for(int i = 0; i < comidasMoney.size(); i++){
            resultGastos += Float.parseFloat(comidasMoney.get(i)) +
                            Float.parseFloat(transporteMoney.get(i)) +
                            Float.parseFloat(viviendaMoney.get(i)) +
                            Float.parseFloat(otrosMoney.get(i));
        }

        //Esto son los datos de la base de datos
        for(int i = 0; i < acuIngresos.size(); i++){
            resultIngresos += acuIngresos.get(i);
        }
        for(int i = 0; i < acuGastos.size(); i++){
            resultGastos += acuGastos.get(i);
        }

        wDatabase.close();

        return resultIngresos - resultGastos;
    }
}
