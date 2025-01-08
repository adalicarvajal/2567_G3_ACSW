package com.alex.ultim2.activities;

import android.annotation.SuppressLint;
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

public class FormularioCapacitacion extends BaseActivity {

    private RadioGroup radioGroupCurso;
    private RadioGroup radioGroupMaestria;
    private RadioGroup radioGroupEspecialidad;
    private RadioGroup radioGroupDiplomado;

    private RadioButton radioButtonSi;
    private RadioButton radioButtonNo;
    private EditText editTextTemaCurso;
    private EditText editTextTemaDiplomado;
    private EditText editTextTemaEspecialidad;
    private EditText editTextTemaMaestria;

    private RadioButton radioButtonSi1;
    private RadioButton radioButtonNo1;
    private RadioButton radioButtonSi2;
    private RadioButton radioButtonNo2;
    private RadioButton radioButtonSi3;
    private RadioButton radioButtonNo3;

    String maestria="NO";
    String temamaestria="";
    String curso="NO";
    String temacurso="";
    String diplomado="NO";
    String temadiplomado="";
    String especialidad="NO";
    String temaespecialidad="";

    IGoogleSheets iGoogleSheets1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_capacitacion);

        radioGroupMaestria = findViewById(R.id.radioGroupMaestria);
        radioGroupEspecialidad = findViewById(R.id.radioGroupEspecialidad);
        radioGroupDiplomado = findViewById(R.id.radioGroupDiplomado);
        radioGroupCurso = findViewById(R.id.radioGroupCurso);

        radioButtonSi = findViewById(R.id.radioButtonSi);
        radioButtonNo = findViewById(R.id.radioButtonNo);
        radioButtonSi1= findViewById(R.id.radioButtonSi1);
        radioButtonNo1= findViewById(R.id.radioButtonNo1);
        radioButtonSi2= findViewById(R.id.radioButtonSi2);
        radioButtonNo2= findViewById(R.id.radioButtonNo2);
        radioButtonSi3= findViewById(R.id.radioButtonSi3);
        radioButtonNo3= findViewById(R.id.radioButtonNo3);
        editTextTemaCurso= findViewById(R.id.editTextTemaCurso);
        editTextTemaMaestria= findViewById(R.id.editTextTemaMaestria);
        editTextTemaDiplomado= findViewById(R.id.editTextTemaDiplomado);
        editTextTemaEspecialidad= findViewById(R.id.editTextTemaEspecialidad);

        Button botonRegresar = findViewById(R.id.botonRegresar);
        Button botonGuardar = findViewById(R.id.botonGuardar);
        LoadData1();

        radioGroupMaestria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi3) {
                    editTextTemaMaestria.setEnabled(true);

                } else {
                    editTextTemaMaestria.setEnabled(false);
                    editTextTemaMaestria.setText(""); // Limpiar el campo de texto cuando no tiene maestría

                }
            }
        });
        radioGroupCurso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi) {
                    editTextTemaCurso.setEnabled(true);

                } else {
                    editTextTemaCurso.setEnabled(false);
                    editTextTemaCurso.setText(""); // Limpiar el campo de texto cuando no tiene maestría

                }
            }
        });
        radioGroupDiplomado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi1) {
                    editTextTemaDiplomado.setEnabled(true);

                } else {
                    editTextTemaDiplomado.setEnabled(false);
                    editTextTemaDiplomado.setText(""); // Limpiar el campo de texto cuando no tiene maestría

                }
            }
        });
        radioGroupEspecialidad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi2) {
                    editTextTemaEspecialidad.setEnabled(true);

                } else {
                    editTextTemaEspecialidad.setEnabled(false);
                    editTextTemaEspecialidad.setText(""); // Limpiar el campo de texto cuando no tiene maestría

                }
            }
        });


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedData();
                Toast.makeText(FormularioCapacitacion.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormularioCapacitacion.this, MainActivity.class);
                startActivity(intent);
            }
        });
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para regresar o cerrar la actividad
                Toast.makeText(FormularioCapacitacion.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void LoadData1(){
        ProgressDialog progressDialog = ProgressDialog.show(FormularioCapacitacion.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=necesidad de postgrados";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String curso = object.getString("curso");
                            String diplomado = object.getString("diplomado");
                            String especialidad= object.getString("especialidad");
                            String maestria= object.getString("maestria");
                            if(curso.equals("SI")){
                                String tema_curso = object.getString("tema_curso");
                                radioButtonSi.setChecked(true);
                                editTextTemaCurso.setText(tema_curso);

                            }else{
                                radioButtonNo.setChecked(true);
                            }
                            if(diplomado.equals("SI")){
                                String tema_diplomado = object.getString("tema_diplomado");
                                radioButtonSi1.setChecked(true);
                                editTextTemaDiplomado.setText(tema_diplomado);

                            }else{
                                radioButtonNo1.setChecked(true);
                            }
                            if(especialidad.equals("SI")){
                                String tema_especialidad = object.getString("tema_especialidad");
                                radioButtonSi2.setChecked(true);
                                editTextTemaEspecialidad.setText(tema_especialidad);

                            }else{
                                radioButtonNo2.setChecked(true);
                            }
                            if(maestria.equals("SI")){
                                String tema_maestria = object.getString("tema_maestria");
                                radioButtonSi3.setChecked(true);
                                editTextTemaMaestria.setText(tema_maestria);

                            }else{
                                radioButtonNo3.setChecked(true);
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
        ProgressDialog progressDialog = ProgressDialog.show(FormularioCapacitacion.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);

        if(radioButtonSi.isChecked()){
            curso="SI";
            temacurso=editTextTemaCurso.getText().toString();
        }
        if(radioButtonSi1.isChecked()){
            diplomado="SI";
            temadiplomado=editTextTemaDiplomado.getText().toString();
        }
        if(radioButtonSi2.isChecked()){
            especialidad="SI";
            temaespecialidad=editTextTemaEspecialidad.getText().toString();
        }
        if(radioButtonSi3.isChecked()){
            maestria="SI";
            temamaestria=editTextTemaMaestria.getText().toString();
        }


        String sheet="necesidad de postgrados";




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
                        "    \"field\": \"" + "curso" + "\",\n" +
                        "    \"value\": \"" + curso + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "tema_curso" + "\",\n" +
                            "    \"value\": \"" + temacurso + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "diplomado" + "\",\n" +
                                "    \"value\": \"" + diplomado + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "tema_diplomado" + "\",\n" +
                                    "    \"value\": \"" + temadiplomado + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "especialidad" + "\",\n" +
                                        "    \"value\": \"" + especialidad + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "tema_especialidad" + "\",\n" +
                                            "    \"value\": \"" + temaespecialidad + "\"\n" +
                                            "}";
                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                    response = call.execute();
                                    code = response.code();
                                    if(code==200){
                                        jsonRequest = "{\n" +
                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                "    \"field\": \"" + "maestria" + "\",\n" +
                                                "    \"value\": \"" + maestria + "\"\n" +
                                                "}";
                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                        response = call.execute();
                                        code = response.code();
                                        if(code==200){
                                            jsonRequest = "{\n" +
                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                    "    \"field\": \"" + "tema_maestria" + "\",\n" +
                                                    "    \"value\": \"" + temamaestria + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
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

