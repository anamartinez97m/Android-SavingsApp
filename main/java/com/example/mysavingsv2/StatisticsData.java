package com.example.mysavingsv2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public abstract class StatisticsData extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected final String[] cats = new String[]{"Comidas","Ocio","Vivienda","Transporte","Otros"};
    protected final String[] subTotalComidas = new String[]{"100", "11.99", "8.5", "7.25"};
    protected final String[] subTotalOcio = new String[]{"12", "5", "18", "10", "12", "5", "18", "10",
                                    "12", "5", "18", "10","12", "5", "18", "10","12", "5", "18", "10"};
    protected final String[] subTotalVivienda = new String[]{"150", "200", "90", "85"};
    protected final String[] subTotalTransporte = new String[]{"100", "25", "20", "110"};
    protected final String[] subTotalOtros = new String[]{"50", "25", "10", "70"};

    private AdminSQLiteOpenHelper bbdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);
    }

    protected float getPercentageCat(String cat, float range){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        Cursor fila = wDatabase.rawQuery("SELECT category, moneyConcept FROM historial WHERE username " +
                "LIKE '" + LoginActivity.loggedUser + "'",null);

        float percentage;
        float subtotal = 0;
        float total = 0;

        //Esto son los datos de prueba
        for(int i = 0; i < subTotalOcio.length; i++){
            total += Float.parseFloat(subTotalOcio[i]);
        }

        for(int i = 0; i < subTotalComidas.length; i++){
            total += Float.parseFloat(subTotalComidas[i]) +
                Float.parseFloat(subTotalOtros[i]) +
                Float.parseFloat(subTotalTransporte[i]) +
                Float.parseFloat(subTotalVivienda[i]);
        }

        //Esto son los datos de la base de datos
        if(fila.moveToFirst()){
            fila.getString(0);
            fila.getString(1);
            if(!fila.getString(0).equals("Ingresos")){
                total += Float.parseFloat(fila.getString(1));

                if(cat.equals(fila.getString(0))){
                    subtotal += Float.parseFloat(fila.getString(1));
                }

                while(fila.moveToNext()){
                    if(!fila.getString(0).equals("Ingresos")){
                        total += Float.parseFloat(fila.getString(1));

                        if(cat.equals(fila.getString(0))){
                            subtotal += Float.parseFloat(fila.getString(1));
                        }
                    }
                }
            }
        }

        //Datos de prueba
        switch (cat){
            case "Comidas":
                for(String s: subTotalComidas){
                    subtotal += Float.parseFloat(s);
                }
                break;
            case "Ocio":
                for(String s: subTotalOcio){
                    subtotal += Float.parseFloat(s);
                }
                break;
            case "Vivienda":
                for(String s: subTotalVivienda){
                    subtotal += Float.parseFloat(s);
                }
                break;
            case "Transporte":
                for(String s: subTotalTransporte){
                    subtotal += Float.parseFloat(s);
                }
                break;
            case "Otros":
                for(String s: subTotalOtros){
                    subtotal += Float.parseFloat(s);
                }
                break;
        }

        percentage = subtotal * 100 / total;

        wDatabase.close();

        return percentage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
