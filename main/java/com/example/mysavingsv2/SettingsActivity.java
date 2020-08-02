package com.example.mysavingsv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    private EditText etPassword;
    private EditText etRepeatPassword;
    private AdminSQLiteOpenHelper bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        Button changePass = findViewById(R.id.buttonChangePassword);
        etPassword = findViewById(R.id.password);
        etRepeatPassword = findViewById(R.id.repeatPassword);
        final Button save = findViewById(R.id.buttonOk);
        final Button cancel = findViewById(R.id.buttonCancel);
        final CheckBox checkboxPass = findViewById(R.id.checkBoxPassword);

        etPassword.setVisibility(View.INVISIBLE);
        etRepeatPassword.setVisibility(View.INVISIBLE);
        checkboxPass.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPassword.setVisibility(View.VISIBLE);
                etRepeatPassword.setVisibility(View.VISIBLE);
                checkboxPass.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                } else {
                    modifyPasswordDatabase();
                }
                etPassword.setVisibility(View.INVISIBLE);
                etRepeatPassword.setVisibility(View.INVISIBLE);
                checkboxPass.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPassword.getText().clear();
                etRepeatPassword.getText().clear();
                etPassword.setVisibility(View.INVISIBLE);
                etRepeatPassword.setVisibility(View.INVISIBLE);
                checkboxPass.setVisibility(View.INVISIBLE);
                save.setVisibility(View.INVISIBLE);
                cancel.setVisibility(View.INVISIBLE);
            }
        });

        checkboxPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
                if(isChecked){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etRepeatPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etRepeatPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
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
                Intent intentProfile = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_statistics:
                Intent intentStatistics = new Intent(SettingsActivity.this, StatisticsActivity.class);
                startActivity(intentStatistics);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                finish();
                return true;
            case R.id.action_logout:
                Intent intentLogout = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void modifyPasswordDatabase(){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        String password = etPassword.getText().toString();

        if(!password.isEmpty()){
            ContentValues register = new ContentValues();
            register.put("password", password);

            long inserted = wDatabase.update("users", register, "username like '"+ LoginActivity.loggedUser + "'", null);

            if(inserted == 1){
                Toast.makeText(this, "La contraseña ha sido modificada correctamente", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }

        //if(etPassword.getText().toString().equals("La contraseña no puede ser la misma"))
        //    Toast.makeText(getApplicationContext(), "La contraseña no puede ser la misma", Toast.LENGTH_SHORT).show();

    }
}
