package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class FormularioConferencias extends BaseActivity {

    private RadioGroup radioGroupConferencias;
    private RadioButton radioButtonSi,radioButtonNo;
    private Spinner spinnerModalidad;
    IGoogleSheets iGoogleSheets1;
    ArrayAdapter<String> adapter;
    String disponibilidad="NO";
    String modalidad="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_conferencias);
        radioButtonSi=findViewById(R.id.radioButtonSi);
        radioButtonNo=findViewById(R.id.radioButtonNo);
        radioGroupConferencias = findViewById(R.id.radioGroupConferencias);
        spinnerModalidad = findViewById(R.id.spinnerModalidad);
        // Define las opciones para el Spinner
        String[] modalidades = {"Presencial", "En línea", "Híbrido"};
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, modalidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModalidad.setAdapter(adapter);
        LoadData1();
        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(v -> {
            saveUpdatedData();
            Toast.makeText(FormularioConferencias.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormularioConferencias.this, MainActivity.class);
            startActivity(intent);

        });

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Toast.makeText(FormularioConferencias.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
            finish();
        });

        // Define el evento para gestionar la visibilidad del Spinner
        radioGroupConferencias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButtonSi = findViewById(R.id.radioButtonSi);
                if (checkedId == radioButtonSi.getId()) {
                    // Mostrar el Spinner cuando se selecciona el RadioButton "Sí"
                    spinnerModalidad.setVisibility(View.VISIBLE);
                } else {
                    // Ocultar el Spinner cuando se selecciona el RadioButton "No"
                    spinnerModalidad.setVisibility(View.GONE);
                }
            }
        });
        spinnerModalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el valor seleccionado del Spinner
                String valorSeleccionado = parent.getItemAtPosition(position).toString();
                modalidad=valorSeleccionado;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                modalidad="";
            }
        });
    }
    public void LoadData1(){
        ProgressDialog progressDialog = ProgressDialog.show(FormularioConferencias.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=disponibilidad_conferencias";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){


                            JSONObject object = postgradosArray.getJSONObject(0);
                            String disponibilidad = object.getString("disponibilidad");


                            if(disponibilidad.equals("SI")){
                                //conferencias.add("Disponibilidad: Sí");
                                String modalidad = object.getString("modalidad");
                                radioButtonSi.setChecked(true);
                                int indexToSelect = adapter.getPosition(modalidad);
                                spinnerModalidad.setSelection(indexToSelect);

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
        ProgressDialog progressDialog = ProgressDialog.show(FormularioConferencias.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);

        if(radioButtonSi.isChecked()){
            disponibilidad="SI";
        }else{
            disponibilidad="NO";
            modalidad="";
        }
        String sheet="disponibilidad_conferencias";
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
                        "    \"field\": \"" + "disponibilidad" + "\",\n" +
                        "    \"value\": \"" + disponibilidad + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "modalidad" + "\",\n" +
                            "    \"value\": \"" + modalidad + "\"\n" +
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

