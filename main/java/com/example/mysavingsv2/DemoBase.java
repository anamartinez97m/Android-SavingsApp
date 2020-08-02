package com.example.mysavingsv2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public abstract class DemoBase extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected final String[] cats = new String[]{"Comidas","Ocio","Vivienda","Transporte","Otros"};
    //protected final String[] listIngresos = new String[]{"Pago enero", "Pago febrero", "Pago marzo", "Pago abril"};
    //protected final String[] subTotalIngresos = new String[]{"1300", "1200", "1200", "1200"};
    //protected final String[] fechaIngresos = new String[]{"01","02","03","04"};
    //protected final String[] listComidas = new String[]{"Fosters", "Vips", "Burger", "Dominos"};
    protected final String[] subTotalComidas = new String[]{"10.99", "11.99", "8.5", "7.25"};
    //protected final String[] fechaComidas = new String[]{"01","02","03","04"};
    //protected final String[] listOcio = new String[]{"Netflix", "Spotify", "Prime", "HBO",
    //        "Netflix", "Spotify", "Prime", "HBO",
    //        "Netflix", "Spotify", "Prime", "HBO",
    //       "Netflix", "Spotify", "Prime", "HBO",
    //        "Netflix", "Spotify", "Prime", "HBO"};
    protected final String[] subTotalOcio = new String[]{"12", "5", "18", "10", "12", "5", "18", "10",
                                    "12", "5", "18", "10","12", "5", "18", "10","12", "5", "18", "10"};
    //protected final String[] fechaOcio = new String[]{"01","01","01","01","02","02","02","02",
    //                                                "03","03","03","03","04","04","04","04"};
    //protected final String[] listVivienda = new String[]{"Alquiler", "Agua", "Gas", "Luz"};
    protected final String[] subTotalVivienda = new String[]{"600", "20", "0", "40"};
    //protected final String[] fechaVivienda = new String[]{"01","02","03","04"};
    //protected final String[] listTransporte = new String[]{"Metro enero", "Coche enero", "Metro febrero", "Coche febrero"};
    protected final String[] subTotalTransporte = new String[]{"20", "25", "20", "30"};
    //protected final String[] fechaTransporte = new String[]{"01","01","02","02"};
    //protected final String[] listOtros = new String[]{"Fnac", "Hollister", "Kiko", "La casa del libro"};
    protected final String[] subTotalOtros = new String[]{"12", "25", "10", "7"};
    //protected final String[] fechaOtros = new String[]{"01","02","03","04"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*protected float getMoneyFromMonths(String month, String[] cats){
        float money;

        if(month.equals("01")){

        } else if(month.equals("02")){

        } else if(month.equals("03")){

        } else if(month.equals("04")){

        }

        return money = 0;
    }*/

    protected float getPercentageCat(String cat, float range){
        float percentage;
        float subtotal = 0;
        int totalLength = subTotalComidas.length + subTotalVivienda.length + subTotalOcio.length +
                            subTotalOtros.length + subTotalTransporte.length;

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

        percentage = subtotal / totalLength;

        return percentage;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
