package com.example.mysavingsv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LogupActivity extends AppCompatActivity {

    private AdminSQLiteOpenHelper bbdd;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        bbdd = new AdminSQLiteOpenHelper(getApplicationContext(), "bbddSavings", null, 9);

        final Button buttonSignUp = findViewById(R.id.registration);
        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etRepeatPassword = findViewById(R.id.repeatPassword);
        final CheckBox checkboxPass = findViewById(R.id.checkBoxPassword);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsername.getText().toString().equals("") || etEmail.getText().toString().equals("") ||
                    etPassword.getText().toString().equals("") || etRepeatPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else{
                    if(!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    } else {
                        saveDataDatabase(view);
                        Intent intent = new Intent(LogupActivity.this, MenuActivity.class);

                        etUsername.getText().clear();
                        etEmail.getText().clear();
                        etPassword.getText().clear();
                        etRepeatPassword.getText().clear();

                        startActivity(intent);
                        finish();
                    }
                }
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

    public void saveDataDatabase(View view){
        SQLiteDatabase wDatabase = bbdd.getWritableDatabase();

        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        ContentValues register = new ContentValues();

        register.put("username", username);
        register.put("email", email);
        register.put("password", password);

        long inserted = wDatabase.insert("users", null, register);

        if(inserted == -1){
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
        } else {
            bbdd.close();
        }
    }
}
