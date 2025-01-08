package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.ultim2.R;
import com.alex.ultim2.adapters.NotificationAdapter;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.models.Notification;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notificaciones extends BaseActivity {
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;
    private Button buttonBack;
    ProgressDialog progressDialog;
    IGoogleSheets iGoogleSheets2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        recyclerView = findViewById(R.id.recyclerView);
        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al MainActivity
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        //notificationList.add(new Notification("Título 1", "Cuerpo de la notificación 1"));
        //notificationList.add(new Notification("Título 2", "Cuerpo de la notificación 2"));
        // Agrega más notificaciones aquí según sea necesario.
        LoadData();



    }
    public void LoadData(){
        ProgressDialog progressDialog = ProgressDialog.show(Notificaciones.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);
        iGoogleSheets2 = Common.iGSGetMethodClient2(Common.BASE_URL_NOTIFICATION);
        String pathUrl1;
        pathUrl1 = "exec?sheet=notificacion";
        try {
            iGoogleSheets2.getPeople(pathUrl1).enqueue(new Callback<String>() {
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {
                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray peopleArray = responseObject.getJSONArray("persons");
                        JSONObject object;
                        if(peopleArray.length()>0){
                            for (int i = 0; i < peopleArray.length(); i++) {
                                object = peopleArray.getJSONObject(i);
                                String estado = object.getString("estado");
                                if(estado.equals("activa")){
                                    String titulo = object.getString("titulo");
                                    String texto = object.getString("texto");
                                    notificationList.add(new Notification(titulo, texto));
                                }
                            }
                            if(notificationList.isEmpty()){
                                notificationList.add(new Notification("No existe notificaciones", "Nuevas"));
                                notificationAdapter = new NotificationAdapter(notificationList);
                                recyclerView.setAdapter(notificationAdapter);

                            }
                            notificationAdapter = new NotificationAdapter(notificationList);
                            recyclerView.setAdapter(notificationAdapter);
                        }else{
                            notificationList.add(new Notification("No existe notificaciones", "Nuevas"));
                            notificationAdapter = new NotificationAdapter(notificationList);
                            recyclerView.setAdapter(notificationAdapter);

                        }
                        progressDialog.dismiss();
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
