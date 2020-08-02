package com.example.mysavingsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddConceptActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper bbdd;
    private String currentValue = "";
    private float resultConcept = 0;
    private EditText etNumbers;
    private EditText etConceptName;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_concept);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        spinner = findViewById(R.id.spinnerCats);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        Button buttonSave = findViewById(R.id.buttonOk);
        etNumbers = findViewById(R.id.inputNumbersConcept);
        etConceptName = findViewById(R.id.editTextNameConcept);
        currentValue = etNumbers.getText().toString();

        Button button_0 = findViewById(R.id.button_0);
        Button button_1 = findViewById(R.id.button_1);
        Button button_2 = findViewById(R.id.button_2);
        Button button_3 = findViewById(R.id.button_3);
        Button button_4 = findViewById(R.id.button_4);
        Button button_5 = findViewById(R.id.button_5);
        Button button_6 = findViewById(R.id.button_6);
        Button button_7 = findViewById(R.id.button_7);
        Button button_8 = findViewById(R.id.button_8);
        Button button_9 = findViewById(R.id.button_9);
        Button button_delete = findViewById(R.id.buttonDelete);
        Button button_point = findViewById(R.id.buttonPoint);

        String[] cats = new String[]{"Elige categoria","Ingresos","Comidas","Ocio","Vivienda","Transporte","Otros"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cats));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddConceptActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etConceptName.getText().toString().equals("")
                        || etNumbers.getText().toString().equals("")
                        || spinner.getSelectedItem().toString().equals("Elige categoria")){
                    Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else{
                    saveDataDatabase();
                    Intent intent = new Intent(AddConceptActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNumbers.getText().clear();
                currentValue = etNumbers.getText().toString();
            }
        });

        button_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "0";
                etNumbers.setText(currentValue);
            }
        });

        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "1";
                etNumbers.setText(currentValue);
            }
        });

        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "2";
                etNumbers.setText(currentValue);
            }
        });

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "3";
                etNumbers.setText(currentValue);
            }
        });

        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "4";
                etNumbers.setText(currentValue);
            }
        });

        button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "5";
                etNumbers.setText(currentValue);
            }
        });

        button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "6";
                etNumbers.setText(currentValue);
            }
        });

        button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "7";
                etNumbers.setText(currentValue);
            }
        });

        button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "8";
                etNumbers.setText(currentValue);
            }
        });

        button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + "9";
                etNumbers.setText(currentValue);
            }
        });

        button_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentValue = currentValue + ".";
                etNumbers.setText(currentValue);
            }
        });
    }

    private void saveDataDatabase(){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(!etNumbers.getText().toString().equals("")){
            resultConcept = Float.parseFloat(etNumbers.getText().toString());
        }

        String username = LoginActivity.loggedUser;
        String category = spinner.getSelectedItem().toString();
        String nameConcept = etConceptName.getText().toString();
        float moneyConcept = resultConcept;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        ContentValues register = new ContentValues();

        register.put("username", username);
        register.put("category", category);
        register.put("nameConcept", nameConcept);
        register.put("moneyConcept", moneyConcept);
        register.put("dayDate", day);
        register.put("monthDate", month);
        register.put("yearDate", year);

        long inserted = wDatabase.insert("historial", null, register);

        if(inserted == -1){
            Toast.makeText(this, "La inserción ha fallado", Toast.LENGTH_SHORT).show();
        } else {
            bbdd.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intentProfile = new Intent(AddConceptActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(AddConceptActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(AddConceptActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                finish();
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(AddConceptActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
