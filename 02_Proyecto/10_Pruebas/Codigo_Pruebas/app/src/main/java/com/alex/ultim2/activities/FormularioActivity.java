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

public class FormularioActivity extends BaseActivity {

    private RadioGroup radioGroupMaestria;
    private RadioButton radioButtonSi;
    private RadioButton radioButtonNo;
    private EditText editTextMaestria;
    private RadioGroup radioGroupDoctorado;
    private RadioButton radioButtonSi1;
    private RadioButton radioButtonNo1;
    private EditText editTextDoctorado;
    private EditText editTextDenominacion1;
    private EditText editTextDenominacion2;
    //nuevos componentes
    private RadioGroup radioGroupTercero;
    private RadioButton radioButtonSi2;
    private RadioButton radioButtonNo2;
    private EditText editTextTitulotercero;
    private EditText editTextDenominacion3;
    //cuarto componente
    private RadioGroup radioGroupCuarto;
    private RadioButton radioButtonSi3;
    private RadioButton radioButtonNo3;
    private EditText editTextTitulocuarto;
    private EditText editTextDenominacion4;

    String maestria="";
    String nominacionmaestria="";
    String titulomaestria="";
    String doctorado="";
    String nominaciondoctorado="";
    String titulodoctorado="";
    //nuevos componentes
    String titulo3="";
    String nominaciontitulo3="";
    String titulotitulo3="";
    String titulo4="";
    String nominaciontitulo4="";
    String titulotitulo4="";
    IGoogleSheets iGoogleSheets1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        radioGroupMaestria = findViewById(R.id.radioGroupMaestria);
        radioButtonSi = findViewById(R.id.radioButtonSi);
        radioButtonNo = findViewById(R.id.radioButtonNo);
        editTextMaestria = findViewById(R.id.editTextMaestria);
        radioGroupDoctorado= findViewById(R.id.radioGroupDoctorado);
        radioButtonSi1= findViewById(R.id.radioButtonSi1);
        radioButtonNo1= findViewById(R.id.radioButtonNo1);
        editTextDoctorado= findViewById(R.id.editTextDoctorado);
        editTextDenominacion1=findViewById(R.id.editTextDenominacion1);
        editTextDenominacion2=findViewById(R.id.editTextDenominacion2);
        //nuevos componetes
        radioGroupTercero= findViewById(R.id.radioGroupTercero);
        radioButtonSi2= findViewById(R.id.radioButtonSi2);
        radioButtonNo2= findViewById(R.id.radioButtonNo2);
        editTextTitulotercero= findViewById(R.id.editTextTitulotercero);
        editTextDenominacion3=findViewById(R.id.editTextDenominacion3);
        radioGroupCuarto= findViewById(R.id.radioGroupCuarto);
        radioButtonSi3= findViewById(R.id.radioButtonSi3);
        radioButtonNo3= findViewById(R.id.radioButtonNo3);
        editTextTitulocuarto= findViewById(R.id.editTextTitulocuarto);
        editTextDenominacion4=findViewById(R.id.editTextDenominacion4);

        Button botonRegresar = findViewById(R.id.botonRegresar);
        Button botonGuardar = findViewById(R.id.botonGuardar);
        LoadData1();

        radioGroupMaestria.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi) {
                    editTextMaestria.setEnabled(true);
                    editTextDenominacion1.setEnabled(true);
                } else {
                    editTextMaestria.setEnabled(false);
                    editTextMaestria.setText(""); // Limpiar el campo de texto cuando no tiene maestría
                    editTextDenominacion1.setEnabled(false);
                    editTextDenominacion1.setText("");
                }
            }
        });
        radioGroupDoctorado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi1) {
                    editTextDoctorado.setEnabled(true);
                    editTextDenominacion2.setEnabled(true);
                } else {
                    editTextDoctorado.setEnabled(false);
                    editTextDoctorado.setText(""); // Limpiar el campo de texto cuando no tiene maestría
                    editTextDenominacion2.setEnabled(false);
                    editTextDenominacion2.setText("");
                }
            }
        });
        radioGroupTercero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi2) {
                    editTextTitulotercero.setEnabled(true);
                    editTextDenominacion3.setEnabled(true);
                } else {
                    editTextTitulotercero.setEnabled(false);
                    editTextTitulotercero.setText(""); // Limpiar el campo de texto cuando no tiene maestría
                    editTextDenominacion3.setEnabled(false);
                    editTextDenominacion3.setText("");
                }
            }
        });
        radioGroupCuarto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonSi3) {
                    editTextTitulocuarto.setEnabled(true);
                    editTextDenominacion4.setEnabled(true);
                } else {
                    editTextTitulocuarto.setEnabled(false);
                    editTextTitulocuarto.setText(""); // Limpiar el campo de texto cuando no tiene maestría
                    editTextDenominacion4.setEnabled(false);
                    editTextDenominacion4.setText("");
                }
            }
        });

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedData();
                Toast.makeText(FormularioActivity.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormularioActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para regresar o cerrar la actividad
                Toast.makeText(FormularioActivity.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void LoadData1(){
        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivity.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=formacion_postgrados";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String cuartonivel = object.getString("cuarto nivel");
                            if(cuartonivel.equals("SI")){
                                String t_cuarto_nivel = object.getString("titulo_cuarto_nivel");
                                String nominacion_cuarto_nivel = object.getString("nominacion_cuarto_nivel");
                                radioButtonSi.setChecked(true);
                                editTextMaestria.setText(t_cuarto_nivel);
                                editTextDenominacion1.setText(nominacion_cuarto_nivel);
                            }else{
                                radioButtonNo.setChecked(true);
                            }
                            String quintonivel = object.getString("quinto_nivel");
                            if(quintonivel.equals("SI")){
                                String t_quinto_nivel = object.getString("titulo_quinto_nivel");
                                String nominacion_quinto_nivel = object.getString("nominacion_quinto_nivel");
                                radioButtonSi1.setChecked(true);
                                editTextDoctorado.setText(t_quinto_nivel);
                                editTextDenominacion2.setText(nominacion_quinto_nivel);
                            }else{
                                radioButtonNo1.setChecked(true);

                            }
                            String titulo_3 = object.getString("tercer_titulo");
                            if(titulo_3.equals("SI")){
                                String t_tercer_titulo = object.getString("nombre_tercer_titulo");
                                String nominacion_tercer_titulo = object.getString("nominacion_tercer_titulo");
                                radioButtonSi2.setChecked(true);
                                editTextTitulotercero.setText(t_tercer_titulo);
                                editTextDenominacion3.setText(nominacion_tercer_titulo);
                            }else{
                                radioButtonNo2.setChecked(true);

                            }
                            String titulo_4 = object.getString("cuarto_titulo");
                            if(titulo_4.equals("SI")){
                                String t_cuarto_titulo = object.getString("nombre_cuarto_titulo");
                                String nominacion_cuarto_titulo = object.getString("nominacion_cuarto_titulo");
                                radioButtonSi3.setChecked(true);
                                editTextTitulocuarto.setText(t_cuarto_titulo);
                                editTextDenominacion4.setText(nominacion_cuarto_titulo);
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
        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivity.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);

        if(radioButtonSi.isChecked()){
            maestria="SI";
            nominacionmaestria=editTextDenominacion1.getText().toString();
            titulomaestria=editTextMaestria.getText().toString();
        }else{
            maestria="NO";
        }

        if(radioButtonSi1.isChecked()){
            doctorado="SI";
            nominaciondoctorado=editTextDenominacion2.getText().toString();
            titulodoctorado=editTextDoctorado.getText().toString();
        }else{
            doctorado="NO";
        }
        if(radioButtonSi2.isChecked()){
            titulo3="SI";
            nominaciontitulo3=editTextDenominacion3.getText().toString();
            titulotitulo3=editTextTitulotercero.getText().toString();
        }else{
            titulo3="NO";
        }
        if(radioButtonSi3.isChecked()){
            titulo4="SI";
            nominaciontitulo4=editTextDenominacion4.getText().toString();
            titulotitulo4=editTextTitulocuarto.getText().toString();
        }else{
            titulo4="NO";
        }


        String sheet="formacion_postgrados";




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
                        "    \"field\": \"" + "cuarto nivel" + "\",\n" +
                        "    \"value\": \"" + maestria + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "nominacion_cuarto_nivel" + "\",\n" +
                            "    \"value\": \"" + nominacionmaestria + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "titulo_cuarto_nivel" + "\",\n" +
                                "    \"value\": \"" + titulomaestria + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "quinto_nivel" + "\",\n" +
                                    "    \"value\": \"" + doctorado + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "nominacion_quinto_nivel" + "\",\n" +
                                        "    \"value\": \"" + nominaciondoctorado + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "titulo_quinto_nivel" + "\",\n" +
                                            "    \"value\": \"" + titulodoctorado + "\"\n" +
                                            "}";
                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                    response = call.execute();
                                    code = response.code();
                                    if(code==200){
                                        jsonRequest = "{\n" +
                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                "    \"field\": \"" + "tercer_titulo" + "\",\n" +
                                                "    \"value\": \"" + titulo3 + "\"\n" +
                                                "}";
                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                        response = call.execute();
                                        code = response.code();
                                        if(code==200){
                                            jsonRequest = "{\n" +
                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                    "    \"field\": \"" + "nominacion_tercer_titulo" + "\",\n" +
                                                    "    \"value\": \"" + nominaciontitulo3 + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                jsonRequest = "{\n" +
                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                        "    \"field\": \"" + "nombre_tercer_titulo" + "\",\n" +
                                                        "    \"value\": \"" + titulotitulo3 + "\"\n" +
                                                        "}";
                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                response = call.execute();
                                                code = response.code();
                                                if(code==200){
                                                    jsonRequest = "{\n" +
                                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                            "    \"field\": \"" + "cuarto_titulo" + "\",\n" +
                                                            "    \"value\": \"" + titulo4 + "\"\n" +
                                                            "}";
                                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                    response = call.execute();
                                                    code = response.code();
                                                    if(code==200){
                                                        jsonRequest = "{\n" +
                                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                "    \"field\": \"" + "nominacion_cuarto_titulo" + "\",\n" +
                                                                "    \"value\": \"" + nominaciontitulo4 + "\"\n" +
                                                                "}";
                                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                        response = call.execute();
                                                        code = response.code();
                                                        if(code==200){
                                                            jsonRequest = "{\n" +
                                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                    "    \"field\": \"" + "nombre_cuarto_titulo" + "\",\n" +
                                                                    "    \"value\": \"" + titulotitulo4 + "\"\n" +
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

