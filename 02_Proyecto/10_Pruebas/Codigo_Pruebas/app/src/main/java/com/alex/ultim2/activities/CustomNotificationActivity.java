package com.alex.ultim2.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.ultim2.R;

public class CustomNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_notification);

        // Recupera el título y el mensaje de la notificación desde los extras
        String title = getIntent().getStringExtra("title");
        String message = getIntent().getStringExtra("message");

        // Configura y muestra el cuadro de diálogo
        mostrarDialogo(title, message);
    }

    private void mostrarDialogo(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra la actividad cuando se presiona el botón "Cerrar"
                        finish();
                    }
                });

        // Crea y muestra el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
