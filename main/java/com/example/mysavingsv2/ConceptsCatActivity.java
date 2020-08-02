package com.example.mysavingsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConceptsCatActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper bbdd;
    private ListView listViewConcepts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concepts_cat);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        TextView nombreCategoria = findViewById(R.id.textViewCatsViews);
        listViewConcepts = findViewById(R.id.listviewConcepts);

        Bundle dato = this.getIntent().getExtras();

        if (dato != null) {
            nombreCategoria.setText(dato.getString("nombreCat"));
        }

        showDataConcepts(nombreCategoria.getText().toString());
    }

    private class MyAdapter extends ArrayAdapter<String>{
        List<String> list;
        List<String> subTotal;
        Context c;
        LayoutInflater inflater;

        private MyAdapter(Context context, List<String> list, List<String> subTotal){
            super(context, R.layout.custom_row, list);
            this.c = context;
            this.list = list;
            this.subTotal = subTotal;
        }

        private class ViewHolder{
            TextView list;
            TextView subTotal;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder = new ViewHolder();

            if(convertView == null){
                inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_row, null);
                holder.list = convertView.findViewById(R.id.rowConcept);
                holder.subTotal = convertView.findViewById(R.id.rowConceptMoney);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            if(list.get(position).length() > 15){
                String newString = list.get(position).substring(0,15) + "...";
                holder.list.setText(newString);
            } else {
                holder.list.setText(list.get(position));
            }
            holder.subTotal.setText(subTotal.get(position));

            return convertView;
        }
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
                Intent intentProfile = new Intent(ConceptsCatActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(ConceptsCatActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(ConceptsCatActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                finish();
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(ConceptsCatActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDataConcepts(String category){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        Cursor fila = wDatabase.rawQuery("SELECT nameConcept, moneyConcept FROM historial WHERE username " +
                        "LIKE '" + LoginActivity.loggedUser + "' AND category LIKE '" + category + "'",null);

        List<String> list = new ArrayList<>();
        List<String> subTotal = new ArrayList<>();

        insertDataForTest(category, list, subTotal);

        if(fila.moveToFirst()){
            list.add(fila.getString(0));
            subTotal.add(fila.getString(1));

            while(fila.moveToNext()){
                list.add(fila.getString(0));
                subTotal.add(fila.getString(1));
            }
        }

        Collections.reverse(list);
        Collections.reverse(subTotal);

        MyAdapter ad = new MyAdapter(this, list, subTotal);
        listViewConcepts.setAdapter(ad);

        wDatabase.close();
    }

    private void insertDataForTest(String category, List<String> list, List<String> subtotal){
        //----------------------INSERCION DATOS INGRESOS----------------------

        List<String> ingresosName = Arrays.asList("Pago enero", "Pago febrero", "Pago marzo", "Pago abril");
        List<String> ingresosMoney = Arrays.asList("1300", "1200", "1200", "1200");

        //----------------------INSERCION DATOS COMIDAS----------------------

        List<String> comidasName = Arrays.asList("Fosters", "Vips", "Burger", "Dominos");
        List<String> comidasMoney = Arrays.asList("100", "11.99", "8.5", "7.25");

        //----------------------INSERCION DATOS OCIO----------------------

        List<String> ocioName = Arrays.asList("Netflix", "Spotify", "Prime", "HBO", "Netflix", "Spotify", "Prime", "HBO",
                "Netflix", "Spotify", "Prime", "HBO","Netflix", "Spotify", "Prime", "HBO","Netflix", "Spotify", "Prime", "HBO");
        List<String> ocioMoney = Arrays.asList("12", "5", "18", "10","12", "5", "18", "10","12", "5", "18", "10",
                "12", "5", "18", "10","12", "5", "18", "10");

        //----------------------INSERCION DATOS VIVIENDA----------------------

        List<String> viviendaName = Arrays.asList("Alquiler", "Agua", "Gas", "Luz");
        List<String> viviendaMoney = Arrays.asList("150", "200", "90", "85");

        //----------------------INSERCION DATOS TRANSPORTE----------------------

        List<String> transporteName = Arrays.asList("Metro", "Coche", "Metro", "Coche");
        List<String> transporteMoney = Arrays.asList("100", "25", "20", "110");

        //----------------------INSERCION DATOS OTRO----------------------

        List<String> otrosName = Arrays.asList("Fnac", "Hollister", "Kiko", "La casa del libro");
        List<String> otrosMoney = Arrays.asList("50", "25", "10", "70");

        switch(category){
            case "Ingresos":
                list.addAll(ingresosName);
                subtotal.addAll(ingresosMoney);
                break;
            case "Comidas":
                list.addAll(comidasName);
                subtotal.addAll(comidasMoney);
                break;
            case "Ocio":
                list.addAll(ocioName);
                subtotal.addAll(ocioMoney);
                break;
            case "Vivienda":
                list.addAll(viviendaName);
                subtotal.addAll(viviendaMoney);
                break;
            case "Transporte":
                list.addAll(transporteName);
                subtotal.addAll(transporteMoney);
                break;
            case "Otros":
                list.addAll(otrosName);
                subtotal.addAll(otrosMoney);
                break;
        }
    }
}
