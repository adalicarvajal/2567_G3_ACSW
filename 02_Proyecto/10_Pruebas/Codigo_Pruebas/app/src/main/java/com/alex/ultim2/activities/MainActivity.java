package com.alex.ultim2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alex.ultim2.R;
import com.alex.ultim2.utils.BaseActivity;

public class MainActivity extends BaseActivity {
    private Button updatePasswordButton, viewDataButton, updateDataButton, logoutButton,viewPostgrados,updatePostgrados,noticias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        updatePasswordButton = findViewById(R.id.updatePasswordButton);
        viewDataButton = findViewById(R.id.viewDataButton);
        updateDataButton = findViewById(R.id.updateDataButton);
        logoutButton = findViewById(R.id.logoutButton);
        viewPostgrados=findViewById(R.id.viewPostgrados);
        updatePostgrados=findViewById(R.id.updatePostgrados1);
        noticias=findViewById(R.id.noticias);

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                Intent intent = new Intent(MainActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para ver los datos
                Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class);
                startActivity(intent);
            }
        });

        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar los datos
                Intent intent = new Intent(MainActivity.this, UpdateDataActivity.class);
                startActivity(intent);
            }
        });
        viewPostgrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(MainActivity.this, ProfesionalActivity.class);
                startActivity(intent);
            }
        });
        updatePostgrados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(MainActivity.this, UpdateCampoProfesional.class);
                startActivity(intent);
            }
        });
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(MainActivity.this, Notificaciones.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarAplicacion();
            }
        });
    }
    private void cerrarAplicacion() {
        finishAffinity(); // Cierra todas las actividades y la aplicación
    }
}

