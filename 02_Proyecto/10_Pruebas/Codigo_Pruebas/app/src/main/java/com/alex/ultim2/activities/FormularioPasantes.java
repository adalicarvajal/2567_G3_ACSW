package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alex.ultim2.R;
import com.alex.ultim2.models.IGoogleSheets;
import com.alex.ultim2.utils.BaseActivity;
import com.alex.ultim2.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FormularioPasantes extends BaseActivity {

    private RadioGroup radioGroupPasantes;
    private RadioButton radioButtonSi,radioButtonNo;
    private EditText editTextCantidadPasantes;
    private TextView textViewCantidad;
    Button botonIncrementar;
    Button botonDecrementar;

    IGoogleSheets iGoogleSheets1;

    String posibilidad="NO";
    String cantidadpasantes="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pasantes);
        radioButtonSi=findViewById(R.id.radioButtonSi);
        radioButtonNo=findViewById(R.id.radioButtonNo);
        radioGroupPasantes = findViewById(R.id.radioGroupPasantes);
        editTextCantidadPasantes = findViewById(R.id.editTextCantidadPasantes);
        botonIncrementar = findViewById(R.id.botonIncrementar);
        botonDecrementar = findViewById(R.id.botonDecrementar);
        textViewCantidad=findViewById(R.id.textViewCantidad);


        LoadData1();
        // Configurar botón de incremento
        botonIncrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCantidadPasantes.getText().toString().equals("")){
                    editTextCantidadPasantes.setText("0");
                }
                int cantidadActual = Integer.parseInt(editTextCantidadPasantes.getText().toString());
                cantidadActual++;
                editTextCantidadPasantes.setText(String.valueOf(cantidadActual));
            }
        });

// Configurar botón de decremento
        botonDecrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCantidadPasantes.getText().toString().equals("")){
                    editTextCantidadPasantes.setText("0");
                }
                int cantidadActual = Integer.parseInt(editTextCantidadPasantes.getText().toString());
                if (cantidadActual > 0) {
                    cantidadActual--;
                    editTextCantidadPasantes.setText(String.valueOf(cantidadActual));
                }
            }
        });
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(v -> {
            saveUpdatedData();
            Toast.makeText(FormularioPasantes.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormularioPasantes.this, MainActivity.class);
            startActivity(intent);

        });

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Toast.makeText(FormularioPasantes.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Define el evento para gestionar la visibilidad del Spinner
        radioGroupPasantes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButtonSi = findViewById(R.id.radioButtonSi);
                if (checkedId == radioButtonSi.getId()) {
                    // Mostrar el Spinner cuando se selecciona el RadioButton "Sí"
                    editTextCantidadPasantes.setVisibility(View.VISIBLE);
                    botonIncrementar.setVisibility(View.VISIBLE);
                    botonDecrementar.setVisibility(View.VISIBLE);
                    textViewCantidad.setVisibility(View.VISIBLE);
                } else {
                    editTextCantidadPasantes.setVisibility(View.GONE);
                    botonIncrementar.setVisibility(View.GONE);
                    botonDecrementar.setVisibility(View.GONE);
                    textViewCantidad.setVisibility(View.GONE);
                }
            }
        });

    }
    public void LoadData1(){
        ProgressDialog progressDialog = ProgressDialog.show(FormularioPasantes.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=posibilidad de pasantes";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");

                        if(postgradosArray.length()>0){
                            JSONObject object = postgradosArray.getJSONObject(0);
                            String posibilidad = object.getString("posibilidad");
                            if(posibilidad.equals("SI")){
                                String cantidad = object.getString("cantidad");
                                radioButtonSi.setChecked(true);
                                editTextCantidadPasantes.setText(cantidad);
                            }else{
                                radioButtonNo.setChecked(true);
                            }
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
    private void saveUpdatedData() {
        // Retrieve the updated data from the EditText fields
        ProgressDialog progressDialog = ProgressDialog.show(FormularioPasantes.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);

        if(radioButtonSi.isChecked()){
            posibilidad="SI";
            cantidadpasantes=editTextCantidadPasantes.getText().toString();
        }else{
            posibilidad="NO";

        }
        String sheet="posibilidad de pasantes";
        AsyncTask.execute(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Common.BASE_URL1)
                        .build();

                IGoogleSheets iGoogleSheets = retrofit.create(IGoogleSheets.class);


                String jsonRequest = "{\n" +
                        "    \"sheet\": \"" + sheet + "\",\n" +
                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                        "    \"field\": \"" + "posibilidad" + "\",\n" +
                        "    \"value\": \"" + posibilidad + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "cantidad" + "\",\n" +
                            "    \"value\": \"" + cantidadpasantes + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        return;

                    }

                }
                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

