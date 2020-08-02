package com.example.mysavingsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BarChartActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private AdminSQLiteOpenHelper bbdd;

    //Datos de prueba para meses anteriores
    List<String> comidasMoney = Arrays.asList("100", "11.99", "8.5", "7.25");
    List<String> comidasMonth = Arrays.asList("1", "5", "6", "7");
    List<String> ocioMoney = Arrays.asList("12", "5", "18", "10","12", "5", "18", "10","12", "5", "18", "10",
            "12", "5", "18", "10","12", "5", "18", "10");
    List<String> ocioMonth = Arrays.asList("0","0","0","0","1","1","1","1","2","2","2","2","3","3","3","3","4","4","4","4");
    List<String> viviendaMoney = Arrays.asList("150", "200", "90", "85");
    List<String> viviendaMonth = Arrays.asList("7", "8", "9", "10");
    List<String> transporteMoney = Arrays.asList("100", "25", "20", "110");
    List<String> transporteMonth = Arrays.asList("0", "10", "10", "6");
    List<String> otrosMoney = Arrays.asList("50", "25", "10", "70");
    List<String> otrosMonth = Arrays.asList("2","3","4","5");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be drawn
        chart.setMaxVisibleValueCount(60);

        // scaling is now disabled
        chart.setPinchZoom(false);
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        //this is for the X legend
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add("Ene");
        xAxisLabel.add("Feb");
        xAxisLabel.add("Mar");
        xAxisLabel.add("Abr");
        xAxisLabel.add("May");
        xAxisLabel.add("Jun");
        xAxisLabel.add("Jul");
        xAxisLabel.add("Ago");
        xAxisLabel.add("Sep");
        xAxisLabel.add("Oct");
        xAxisLabel.add("Nov");
        xAxisLabel.add("Dic");

        final XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(13);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value){
                return xAxisLabel.get((int) (value % 12));
            }
        });

        //this is for the Y legend
        ValueFormatter custom = new MyValueFormatter("€");

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setTextColor(Color.WHITE);
        yAxisRight.setValueFormatter(custom);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        // setting data
        seekBarX.setProgress(12);
        seekBarY.setProgress(2000);

        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(true);
    }

    private void setData(int count, float range) {
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);

        Cursor fila = wDatabase.rawQuery("SELECT moneyConcept, monthDate FROM historial WHERE username " +
                "LIKE '" + LoginActivity.loggedUser + "' AND yearDate = " + year + " AND category NOT LIKE 'Ingresos'",null);

        float[] moneyPerMonth = new float[12];

        //Datos de prueba
        for(int i = 0; i < ocioMonth.size(); i++){
            switch(ocioMonth.get(i)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(ocioMoney.get(i));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(ocioMoney.get(i));
                    break;
            }
        }

        for(int i = 0; i < comidasMonth.size(); i++){
            switch(comidasMonth.get(i)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(comidasMoney.get(i));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(comidasMoney.get(i));
                    break;
            }
        }

        for(int i = 0; i < viviendaMonth.size(); i++){
            switch(viviendaMonth.get(i)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(viviendaMoney.get(i));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(viviendaMoney.get(i));
                    break;
            }
        }

        for(int i = 0; i < transporteMonth.size(); i++){
            switch(transporteMonth.get(i)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(transporteMoney.get(i));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(transporteMoney.get(i));
                    break;
            }
        }

        for(int i = 0; i < otrosMonth.size(); i++){
            switch(otrosMonth.get(i)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(otrosMoney.get(i));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(otrosMoney.get(i));
                    break;
            }
        }

        //Datos de la base de datos
        if(fila.moveToFirst()){
            fila.getString(0);
            fila.getString(1);

            switch(fila.getString(1)){
                case "0":
                    moneyPerMonth[0] += Float.parseFloat(fila.getString(0));
                    break;
                case "1":
                    moneyPerMonth[1] += Float.parseFloat(fila.getString(0));
                    break;
                case "2":
                    moneyPerMonth[2] += Float.parseFloat(fila.getString(0));
                    break;
                case "3":
                    moneyPerMonth[3] += Float.parseFloat(fila.getString(0));
                    break;
                case "4":
                    moneyPerMonth[4] += Float.parseFloat(fila.getString(0));
                    break;
                case "5":
                    moneyPerMonth[5] += Float.parseFloat(fila.getString(0));
                    break;
                case "6":
                    moneyPerMonth[6] += Float.parseFloat(fila.getString(0));
                    break;
                case "7":
                    moneyPerMonth[7] += Float.parseFloat(fila.getString(0));
                    break;
                case "8":
                    moneyPerMonth[8] += Float.parseFloat(fila.getString(0));
                    break;
                case "9":
                    moneyPerMonth[9] += Float.parseFloat(fila.getString(0));
                    break;
                case "10":
                    moneyPerMonth[10] += Float.parseFloat(fila.getString(0));
                    break;
                case "11":
                    moneyPerMonth[11] += Float.parseFloat(fila.getString(0));
                    break;
            }

            while(fila.moveToNext()){
                switch(fila.getString(1)){
                    case "0":
                        moneyPerMonth[0] += Float.parseFloat(fila.getString(0));
                        break;
                    case "1":
                        moneyPerMonth[1] += Float.parseFloat(fila.getString(0));
                        break;
                    case "2":
                        moneyPerMonth[2] += Float.parseFloat(fila.getString(0));
                        break;
                    case "3":
                        moneyPerMonth[3] += Float.parseFloat(fila.getString(0));
                        break;
                    case "4":
                        moneyPerMonth[4] += Float.parseFloat(fila.getString(0));
                        break;
                    case "5":
                        moneyPerMonth[5] += Float.parseFloat(fila.getString(0));
                        break;
                    case "6":
                        moneyPerMonth[6] += Float.parseFloat(fila.getString(0));
                        break;
                    case "7":
                        moneyPerMonth[7] += Float.parseFloat(fila.getString(0));
                        break;
                    case "8":
                        moneyPerMonth[8] += Float.parseFloat(fila.getString(0));
                        break;
                    case "9":
                        moneyPerMonth[9] += Float.parseFloat(fila.getString(0));
                        break;
                    case "10":
                        moneyPerMonth[10] += Float.parseFloat(fila.getString(0));
                        break;
                    case "11":
                        moneyPerMonth[11] += Float.parseFloat(fila.getString(0));
                        break;
                }
            }
        }

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < seekBarX.getProgress(); i++) {
            values.add(new BarEntry(i, moneyPerMonth[i]));
        }

        BarDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Gastos por mes");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            chart.setFitBars(true);
            chart.setData(data);
        }

        wDatabase.close();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        tvX.setTextColor(Color.WHITE);
        tvY.setTextColor(Color.WHITE);

        setData(seekBarX.getProgress(), seekBarY.getProgress());

        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if(chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                chart.invalidate();
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(2000, 2000);
                break;
            }
        }
        return true;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
