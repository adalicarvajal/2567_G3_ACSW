package com.alex.ultim2.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.alex.ultim2.R;
import com.alex.ultim2.activities.CustomNotificationActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Aquí puedes procesar los datos de la notificación
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        // Llama a la función para mostrar la notificación
        mostrarNotificacion(title, body);
    }

    private void mostrarNotificacion(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "MiCanalID"; // Debes definir un ID de canal para Android 8.0 y superior

        // Crea un canal de notificación si estás en Android 8.0 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Mi Canal",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Define una acción cuando se toca la notificación (abrir un cuadro de diálogo)
        Intent intent = new Intent(this, CustomNotificationActivity.class);
        intent.putExtra("title", title); // Pasa el título como extra
        intent.putExtra("message", body); // Pasa el mensaje como extra

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Configura la notificación
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_logo_espe) // Icono de la notificación (personaliza según tus necesidades)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent); // Establece la intención para abrir el cuadro de diálogo

        // Muestra la notificación
        notificationManager.notify(0, notificationBuilder.build());
    }


}
