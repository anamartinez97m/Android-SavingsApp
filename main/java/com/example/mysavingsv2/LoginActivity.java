package com.example.mysavingsv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    public AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bbddSavings", null, 9);

    public static String loggedUser = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        final Button buttonLogin = findViewById(R.id.login);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        final TextView textviewSignup = findViewById(R.id.textviewSignUp);
        final CheckBox checkboxPass = findViewById(R.id.checkBoxPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etUsername.getText().toString().equals("") && !etPassword.getText().toString().equals("")){
                    if(searchData(view)){
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        etUsername.getText().clear();
                        etPassword.getText().clear();
                    } else {
                        Toast.makeText(getApplicationContext(), "El usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textviewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LogupActivity.class);
                startActivity(intent);
            }
        });

        checkboxPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked){
                if(isChecked){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public boolean searchData(View view){
        SQLiteDatabase bbdd = admin.getWritableDatabase();

        boolean result = false;

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        Cursor fila = bbdd.rawQuery("select username, password from users where username like '"+username+"' and password like '" + password + "'", null);

        if(fila.moveToFirst()){
            result = true;
            etUsername.setText(fila.getString(0));
            etPassword.setText(fila.getString(1));
            bbdd.close();
            loggedUser = username;
            return result;
        }
        return result;
    }
}
