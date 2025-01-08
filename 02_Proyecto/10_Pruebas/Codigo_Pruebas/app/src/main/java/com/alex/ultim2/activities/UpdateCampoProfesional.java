package com.alex.ultim2.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alex.ultim2.R;
import com.alex.ultim2.utils.BaseActivity;

public class UpdateCampoProfesional extends BaseActivity {
    private Button updateFormacion, updateProfesional, updateAgricola,updateConferencias,updatePasantes,updateCapacitacion,logoutButton,Sugerencias;
    private Button updatePecuaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_campo_profesional);

        updateFormacion = findViewById(R.id.updateFormacion);
        updateProfesional = findViewById(R.id.updateProfesional);
        updateAgricola= findViewById(R.id.updateAgricola);
        updateConferencias=findViewById(R.id.updateConferencias);
        updatePasantes=findViewById(R.id.updatePasantes);
        updateCapacitacion=findViewById(R.id.updateCapacitacion);
        logoutButton=findViewById(R.id.logoutButton);
        updatePecuaria=findViewById(R.id.updatePecuaria);
        Sugerencias=findViewById(R.id.Sugerencias);



        updateFormacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        updateProfesional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para ver los datos
                // Agrega aquí tu código para mostrar los datos en una nueva actividad
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioActivityProfesional.class);
                startActivity(intent);
            }
        });

        updateAgricola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar los datos
                // Agrega aquí tu código para actualizar los datos en una nueva actividad
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioActivityAgricola.class);
                startActivity(intent);
            }
        });

        updateConferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioConferencias.class);
                startActivity(intent);
            }
        });
        updatePasantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioPasantes.class);
                startActivity(intent);
            }
        });
        updateCapacitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioCapacitacion.class);
                startActivity(intent);
            }
        });
        updatePecuaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, FormularioActivityPecuaria.class);
                startActivity(intent);
            }
        });
        Sugerencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para actualizar la contraseña
                // Agrega aquí tu código para actualizar la contraseña
                Intent intent = new Intent(UpdateCampoProfesional.this, Sugerencias.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para salir de la sesión
                // Agrega aquí tu código para salir de la sesión y regresar a la pantalla de inicio de sesión
                Intent intent = new Intent(UpdateCampoProfesional.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
