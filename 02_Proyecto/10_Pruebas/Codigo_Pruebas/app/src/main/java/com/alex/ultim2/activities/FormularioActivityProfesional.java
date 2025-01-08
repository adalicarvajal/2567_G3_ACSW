package com.alex.ultim2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class FormularioActivityProfesional extends BaseActivity {
    CheckBox checkBoxOtros;
    EditText editTextOtros;
    CheckBox checkBoxProductor;
    CheckBox checkBoxEmprendimiento;
    CheckBox checkBoxEmpleadoPrivado;
    CheckBox checkBoxEmpleadoPublico;
    CheckBox checkBoxComercializacion;
    CheckBox checkBoxExportacion;
    CheckBox checkBoxIndustrializacionAgricola;
    CheckBox checkBoxIndustrializacionPecuarios;
    CheckBox checkBoxAsistenciaTecnica;
    CheckBox checkBoxDocencia;
    CheckBox checkBoxInvestigacion;
    IGoogleSheets iGoogleSheets1;
    //string
    String otros="NO";
    String productor="NO";
    String emprendimiento="NO";
    String empleadoPrivado="NO";
    String empleadoPublico="NO";
    String comercializacion="NO";
    String exportacion="NO";
    String industrializacionAgricola="NO";
    String industrializacionPecuarios="NO";
    String asistenciaTecnica="NO";
    String docencia="NO";
    String investigacion="NO";
    String actividadotros="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_profesional);

        checkBoxOtros = findViewById(R.id.checkBoxOtros);
        checkBoxProductor= findViewById(R.id.checkBoxProductor);
        checkBoxEmprendimiento= findViewById(R.id.checkBoxEmprendimiento);
        checkBoxEmpleadoPrivado= findViewById(R.id.checkBoxEmpleadoPrivado);
        checkBoxEmpleadoPublico= findViewById(R.id.checkBoxEmpleadoPublico);
        checkBoxComercializacion= findViewById(R.id.checkBoxComercializacion);
        checkBoxExportacion= findViewById(R.id.checkBoxExportacion);
        checkBoxIndustrializacionAgricola= findViewById(R.id.checkBoxIndustrializacionAgricola);
        checkBoxIndustrializacionPecuarios= findViewById(R.id.checkBoxIndustrializacionPecuarios);
        checkBoxAsistenciaTecnica= findViewById(R.id.checkBoxAsistenciaTecnica);
        checkBoxDocencia= findViewById(R.id.checkBoxDocencia);
        checkBoxInvestigacion= findViewById(R.id.checkBoxInvestigacion);
        editTextOtros = findViewById(R.id.editTextOtros);
        LoadActivities();

        checkBoxOtros.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextOtros.setVisibility(View.VISIBLE);
            } else {
                editTextOtros.setVisibility(View.GONE);
            }
        });

        Button botonGuardar = findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(v -> {
            saveUpdatedData();
            Toast.makeText(FormularioActivityProfesional.this, "Guardando cambios", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormularioActivityProfesional.this, MainActivity.class);
            startActivity(intent);

        });

        Button botonRegresar = findViewById(R.id.botonRegresar);
        botonRegresar.setOnClickListener(v -> {
            Toast.makeText(FormularioActivityProfesional.this, "Botón Regresar presionado", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    public void LoadActivities(){

        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityProfesional.this,
                "Cargando datos",
                "Espere por favor",
                true,
                false);
        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=actividad_profesional";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String productoragropecuario = object.getString("Productor agropecuario");
                            String emprendimiento_propio = object.getString("Emprendimiento propio");
                            String empleado_privado = object.getString("Empleado privado");
                            String empleado_publico = object.getString("Empleado público");
                            String comercialización_de_productos_agro_veterinarios = object.getString("Comercialización de productos agro veterinarios");
                            String exportación_de_productos_agropecuarios = object.getString("Exportación de productos agropecuarios");
                            String industrialización_de_productos_agrícolas = object.getString("Industrialización de productos agrícolas");
                            String industrialización_de_productos_pecuarios = object.getString("Industrialización de productos pecuarios");
                            String asistencia_técnica = object.getString("Asistencia técnica");
                            String docencia = object.getString("Docencia");
                            String investigación = object.getString("Investigación");
                            String otros = object.getString("Otros");

                            if(productoragropecuario.equals("SI")){
                                checkBoxProductor.setChecked(true);
                                //actividades.add("Productor agropecuario");
                            }
                            if(emprendimiento_propio.equals("SI")){
                                checkBoxEmprendimiento.setChecked(true);
                                //actividades.add("Emprendimiento propio");
                            }
                            if(empleado_privado.equals("SI")){
                                checkBoxEmpleadoPrivado.setChecked(true);
                                //actividades.add("Empleado privado");
                            }
                            if(empleado_publico.equals("SI")){
                                checkBoxEmpleadoPublico.setChecked(true);
                                //actividades.add("Empleado público");
                            }
                            if(comercialización_de_productos_agro_veterinarios.equals("SI")){
                                checkBoxComercializacion.setChecked(true);
                                //actividades.add("Comercialización de productos agro veterinarios");
                            }
                            if(exportación_de_productos_agropecuarios.equals("SI")){
                                checkBoxExportacion.setChecked(true);
                                //actividades.add("Exportación de productos agropecuarios");
                            }
                            if(industrialización_de_productos_agrícolas.equals("SI")){
                                checkBoxIndustrializacionAgricola.setChecked(true);
                                //actividades.add("Industrialización de productos agrícolas");
                            }
                            if(industrialización_de_productos_pecuarios.equals("SI")){
                                checkBoxIndustrializacionPecuarios.setChecked(true);
                                //actividades.add("Industrialización de productos pecuarios");
                            }
                            if(asistencia_técnica.equals("SI")){
                                checkBoxAsistenciaTecnica.setChecked(true);
                                //actividades.add("Asistencia técnica");
                            }
                            if(docencia.equals("SI")){
                                checkBoxDocencia.setChecked(true);
                                //actividades.add("Docencia");
                            }
                            if(investigación.equals("SI")){
                                checkBoxInvestigacion.setChecked(true);
                                //actividades.add("Investigación");
                            }
                            if(otros.equals("SI")){
                                String actividadotros=object.getString("actividad_otros");
                                checkBoxOtros.setChecked(true);
                                editTextOtros.setText(actividadotros);
                                //actividades.add("Otros: "+actividadotros);
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
        ProgressDialog progressDialog = ProgressDialog.show(FormularioActivityProfesional.this,
                "Actualizando datos",
                "Espere por favor",
                true,
                false);


        if(checkBoxProductor.isChecked()){
            productor="SI";
        }
        if(checkBoxEmprendimiento.isChecked()){
            emprendimiento="SI";
        }
        if(checkBoxEmpleadoPublico.isChecked()){
            empleadoPublico="SI";
        }
        if(checkBoxEmpleadoPrivado.isChecked()){
            empleadoPrivado="SI";
        }
        if(checkBoxComercializacion.isChecked()){
            comercializacion="SI";
        }

        if(checkBoxExportacion.isChecked()){
            exportacion="SI";
        }
        if(checkBoxIndustrializacionAgricola.isChecked()){
            industrializacionAgricola="SI";
        }
        if(checkBoxIndustrializacionPecuarios.isChecked()){
            industrializacionPecuarios="SI";
        }
        if(checkBoxAsistenciaTecnica.isChecked()){
            asistenciaTecnica="SI";
        }
        if(checkBoxDocencia.isChecked()){
            docencia="SI";
        }
        if(checkBoxInvestigacion.isChecked()){
            investigacion="SI";
        }
        if(checkBoxOtros.isChecked()){
            otros="SI";
            actividadotros=editTextOtros.getText().toString();
        }


        String sheet="actividad_profesional";




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
                        "    \"field\": \"" + "Productor agropecuario" + "\",\n" +
                        "    \"value\": \"" + productor + "\"\n" +
                        "}";
                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();
                if (code == 200) {
                    jsonRequest = "{\n" +
                            "    \"sheet\": \"" + sheet + "\",\n" +
                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                            "    \"field\": \"" + "Emprendimiento propio" + "\",\n" +
                            "    \"value\": \"" + emprendimiento + "\"\n" +
                            "}";
                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                    response = call.execute();
                    code = response.code();
                    if(code==200){
                        jsonRequest = "{\n" +
                                "    \"sheet\": \"" + sheet + "\",\n" +
                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                "    \"field\": \"" + "Empleado privado" + "\",\n" +
                                "    \"value\": \"" + empleadoPrivado + "\"\n" +
                                "}";
                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                        response = call.execute();
                        code = response.code();
                        if(code==200){
                            jsonRequest = "{\n" +
                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                    "    \"field\": \"" + "Empleado público" + "\",\n" +
                                    "    \"value\": \"" + empleadoPublico + "\"\n" +
                                    "}";
                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                            response = call.execute();
                            code = response.code();
                            if(code==200){
                                jsonRequest = "{\n" +
                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                        "    \"field\": \"" + "Comercialización de productos agro veterinarios" + "\",\n" +
                                        "    \"value\": \"" + comercializacion + "\"\n" +
                                        "}";
                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                response = call.execute();
                                code = response.code();
                                if(code==200){
                                    jsonRequest = "{\n" +
                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                            "    \"field\": \"" + "Exportación de productos agropecuarios" + "\",\n" +
                                            "    \"value\": \"" + exportacion + "\"\n" +
                                            "}";
                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                    response = call.execute();
                                    code = response.code();
                                    if(code==200){
                                        jsonRequest = "{\n" +
                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                "    \"field\": \"" + "Industrialización de productos agrícolas" + "\",\n" +
                                                "    \"value\": \"" + industrializacionAgricola + "\"\n" +
                                                "}";
                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                        response = call.execute();
                                        code = response.code();
                                        if(code==200){
                                            jsonRequest = "{\n" +
                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                    "    \"field\": \"" + "Industrialización de productos pecuarios" + "\",\n" +
                                                    "    \"value\": \"" + industrializacionPecuarios + "\"\n" +
                                                    "}";
                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                            response = call.execute();
                                            code = response.code();
                                            if(code==200){
                                                jsonRequest = "{\n" +
                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                        "    \"field\": \"" + "Asistencia técnica" + "\",\n" +
                                                        "    \"value\": \"" + asistenciaTecnica + "\"\n" +
                                                        "}";
                                                call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                response = call.execute();
                                                code = response.code();
                                                if(code==200){
                                                    jsonRequest = "{\n" +
                                                            "    \"sheet\": \"" + sheet + "\",\n" +
                                                            "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                            "    \"field\": \"" + "Docencia" + "\",\n" +
                                                            "    \"value\": \"" + docencia + "\"\n" +
                                                            "}";
                                                    call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                    response = call.execute();
                                                    code = response.code();
                                                    if(code==200){
                                                        jsonRequest = "{\n" +
                                                                "    \"sheet\": \"" + sheet + "\",\n" +
                                                                "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                "    \"field\": \"" + "Investigación" + "\",\n" +
                                                                "    \"value\": \"" + investigacion + "\"\n" +
                                                                "}";
                                                        call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                        response = call.execute();
                                                        code = response.code();
                                                        if(code==200){
                                                            jsonRequest = "{\n" +
                                                                    "    \"sheet\": \"" + sheet + "\",\n" +
                                                                    "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                    "    \"field\": \"" + "Otros" + "\",\n" +
                                                                    "    \"value\": \"" + otros + "\"\n" +
                                                                    "}";
                                                            call = iGoogleSheets.getStringRequestBody(jsonRequest);
                                                            response = call.execute();
                                                            code = response.code();
                                                            if(code==200){
                                                                jsonRequest = "{\n" +
                                                                        "    \"sheet\": \"" + sheet + "\",\n" +
                                                                        "    \"id\": \"" + Common.getUsername() + "\",\n" +
                                                                        "    \"field\": \"" + "actividad_otros" + "\",\n" +
                                                                        "    \"value\": \"" + actividadotros + "\"\n" +
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

