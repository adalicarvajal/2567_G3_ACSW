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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class Sugerencias extends BaseActivity {
    private RadioGroup radioGroupSugerencias;
    private RadioButton radioButtonSi;
    private RadioButton radioButtonNo;
    private EditText editTextTema;
    private EditText editTextDescripcion;
    Button botonRegresar;
    Button botonGuardar;
    IGoogleSheets iGoogleSheets1;
    String sugerencias="";
    String temasugerencias="";
    String descripcionsugerencias="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerencias_activity);
        botonRegresar = findViewById(R.id.botonRegresar);
        botonGuardar = findViewById(R.id.botonGuardar);
        radioGroupSugerencias= findViewById(R.id.radioGroupSugerencias);
        radioButtonSi= findViewById(R.id.radioButtonSi);
        radioButtonNo= findViewById(R.id.radioButtonNo);
        editTextTema= findViewById(R.id.editTextTema);
        editTextDescripcion=findViewById(R.id.editTextDescripcion);
        LoadData1();
        radioGroupSugerencias.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi) {
                    editTextTema.setEnabled(true);
                    editTextDescripcion.setEnabled(true);
                } else {
                    editTextTema.setEnabled(false);
                    editTextTema.setText(""); // Limpiar el campo de texto cuando no tiene maestría
                    editTextDescripcion.setEnabled(false);
                    editTextDescripcion.setText("");
                }
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedData();
                Toast.makeText(Sugerencias.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Sugerencias.this, MainActivity.class);
                startActivity(intent);
            }

            
        });
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para regresar o cerrar la actividad
                Toast.makeText(Sugerencias.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void LoadData1() {
        ProgressDialog progressDialog = ProgressDialog.show(Sugerencias.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=sugerencias";


        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String tiene_sugerencia = object.getString("tiene_sugerencia");
                            if(tiene_sugerencia.equals("SI")){
                                String t_sugerencia = object.getString("tema");
                                String d_sugerencia = object.getString("descripcion");
                                radioButtonSi.setChecked(true);
                                editTextTema.setText(t_sugerencia);
                                editTextDescripcion.setText(d_sugerencia);
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
        ProgressDialog progressDialog = ProgressDialog.show(Sugerencias.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);
        if(radioButtonSi.isChecked()){
            sugerencias="SI";
            temasugerencias=editTextTema.getText().toString();
            descripcionsugerencias=editTextDescripcion.getText().toString();
        }else{
            sugerencias="NO";
        }
        String sheet="sugerencias";
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
                        "    \"field\": \"" + "tiene_sugerencia" + "\",\n" +
                        "    \"value\": \"" + sugerencias + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "tema" + "\",\n" +
                            "    \"value\": \"" + temasugerencias + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "descripcion" + "\",\n" +
                                "    \"value\": \"" + descripcionsugerencias + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            return;
                        }
                        progressDialog.dismiss();
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
