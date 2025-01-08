package com.alex.ultim2.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.ultim2.R;
import com.alex.ultim2.adapters.FormacionAcademicaAdapter;
import com.alex.ultim2.adapters.ProfesionalAdapter;
import com.alex.ultim2.models.FormacionAcademica;
import com.alex.ultim2.models.IGoogleSheets;
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

public class ProfesionalActivity extends BaseActivity {
    List<ListElement> elements;
    List<FormacionAcademica> postgrados;
    List<String> actividades;
    List<String> area_agricola;
    List<String> area_pecuaria;
    List<String> conferencias;
    List<String> pasantes;
    List<String> capacitaciones;
    private RecyclerView recyclerFormacion;
    private RecyclerView recyclerProfesional;
    private RecyclerView recyclerAgricola;
    private RecyclerView recyclerPecuaria;
    private RecyclerView recyclerConferencias;
    private RecyclerView recyclerPasantes;
    private RecyclerView recyclerCapacitacion;

    public TextView titulodegina;
    public TextView textViewProfesional;
    public TextView textViewAgricolal;
    public TextView textViewPecuaria;
    public TextView textViewConferencias;
    public TextView textViewPasantes;
    public TextView textViewCapacitacion;
    private Button buttonBack;
    public boolean bandera=true;


    IGoogleSheets iGoogleSheets1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profesional_activity);

        recyclerFormacion=findViewById(R.id.recycler_formacion1);
        recyclerProfesional=findViewById(R.id.recycler_actividadp);
        recyclerAgricola=findViewById(R.id.recycler_agricola);
        recyclerPecuaria=findViewById(R.id.recycler_pecuaria);
        recyclerConferencias=findViewById(R.id.recycler_conferencias);
        recyclerPasantes=findViewById(R.id.recycler_pasantes);
        recyclerCapacitacion=findViewById(R.id.recycler_capacitacion);

        //postgrados
        titulodegina=findViewById(R.id.textViewTítulo);
        textViewProfesional=findViewById(R.id.textViewActividad);
        textViewAgricolal=findViewById(R.id.textViewAgricola);
        textViewPecuaria=findViewById(R.id.textViewPecuaria);
        textViewConferencias=findViewById(R.id.textViewConferencias);
        textViewPasantes=findViewById(R.id.textViewPasantes);
        textViewCapacitacion=findViewById(R.id.textViewCapacitacion);

        postgrados = new ArrayList<>();
        actividades = new ArrayList<>();
        area_agricola= new ArrayList<>();
        area_pecuaria= new ArrayList<>();
        conferencias= new ArrayList<>();
        pasantes= new ArrayList<>();
        capacitaciones= new ArrayList<>();

        //setFormacionAdapter(postgrados);

        LoadData1();
        LoadActivities();
        LoadAgricola();
        LoadPecuaria();
        LoadConferencias();
        LoadPasantes();
        LoadCapacitacion();
        if(bandera){
            ContractRecycler();
            bandera=false;
        }
        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar al MainActivity
                finish();
            }
        });
        //init();
        // Asignar el listener de clic a cada TextView
        titulodegina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.VISIBLE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewProfesional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.VISIBLE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewAgricolal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.VISIBLE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewPecuaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.VISIBLE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewConferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.VISIBLE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewPasantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.VISIBLE);
                recyclerCapacitacion.setVisibility(View.GONE);

            }
        });
        textViewCapacitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el RecyclerView de formación y ocultar los demás
                recyclerFormacion.setVisibility(View.GONE);
                recyclerProfesional.setVisibility(View.GONE);
                recyclerAgricola.setVisibility(View.GONE);
                recyclerPecuaria.setVisibility(View.GONE);
                recyclerConferencias.setVisibility(View.GONE);
                recyclerPasantes.setVisibility(View.GONE);
                recyclerCapacitacion.setVisibility(View.VISIBLE);

            }
        });

    }

    private void ContractRecycler() {
        recyclerFormacion.setVisibility(View.GONE);
        recyclerProfesional.setVisibility(View.GONE);
        recyclerAgricola.setVisibility(View.GONE);
        recyclerPecuaria.setVisibility(View.GONE);
        recyclerConferencias.setVisibility(View.GONE);
        recyclerPasantes.setVisibility(View.GONE);
        recyclerCapacitacion.setVisibility(View.GONE);
    }

    public void init(){
        elements=new ArrayList<>();
        elements.add(new ListElement("#123456","hola","quito","active"));
        elements.add(new ListElement("#123443","hola","Guayaquil","active2"));
        ListAdapter listAdapter=new ListAdapter(elements,this);
        RecyclerView recyclerView=findViewById(R.id.recycler_formacion1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    public void LoadData1(){


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
                                String n_cuarto_nivel = object.getString("nominacion_cuarto_nivel");
                                String t_cuarto_nivel = object.getString("titulo_cuarto_nivel");
                                postgrados.add(new FormacionAcademica(n_cuarto_nivel,t_cuarto_nivel));
                            }
                            String quintonivel = object.getString("quinto_nivel");
                            if(quintonivel.equals("SI")){
                                String n_quinto_nivel = object.getString("nominacion_quinto_nivel");
                                String t_quinto_nivel = object.getString("titulo_quinto_nivel");
                                postgrados.add(new FormacionAcademica(n_quinto_nivel,t_quinto_nivel));
                            }
                            String titulo_3 = object.getString("tercer_titulo");
                            if(titulo_3.equals("SI")){
                                String t_tercer_titulo = object.getString("nombre_tercer_titulo");
                                String nominacion_tercer_titulo = object.getString("nominacion_tercer_titulo");
                                postgrados.add(new FormacionAcademica(nominacion_tercer_titulo,t_tercer_titulo));

                            }
                            String titulo_4 = object.getString("cuarto_titulo");
                            if(titulo_4.equals("SI")){
                                String t_cuarto_titulo = object.getString("nombre_cuarto_titulo");
                                String nominacion_cuarto_titulo = object.getString("nominacion_cuarto_titulo");
                                postgrados.add(new FormacionAcademica(nominacion_cuarto_titulo,t_cuarto_titulo));

                            }
                        }
                        setFormacionAdapter(postgrados);

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
    public void LoadActivities(){


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
                                actividades.add("Productor agropecuario");
                            }
                            if(emprendimiento_propio.equals("SI")){
                                actividades.add("Emprendimiento propio");
                            }
                            if(empleado_privado.equals("SI")){
                                actividades.add("Empleado privado");
                            }
                            if(empleado_publico.equals("SI")){
                                actividades.add("Empleado público");
                            }
                            if(comercialización_de_productos_agro_veterinarios.equals("SI")){
                                actividades.add("Comercialización de productos agro veterinarios");
                            }
                            if(exportación_de_productos_agropecuarios.equals("SI")){
                                actividades.add("Exportación de productos agropecuarios");
                            }
                            if(industrialización_de_productos_agrícolas.equals("SI")){
                                actividades.add("Industrialización de productos agrícolas");
                            }
                            if(industrialización_de_productos_pecuarios.equals("SI")){
                                actividades.add("Industrialización de productos pecuarios");
                            }
                            if(asistencia_técnica.equals("SI")){
                                actividades.add("Asistencia técnica");
                            }
                            if(docencia.equals("SI")){
                                actividades.add("Docencia");
                            }
                            if(investigación.equals("SI")){
                                actividades.add("Investigación");
                            }
                            if(otros.equals("SI")){
                                String actividadotros=object.getString("actividad_otros");
                                actividades.add("Otros: "+actividadotros);
                            }

                        }
                        setProfesionalAdapter(actividades);

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
    public void LoadAgricola(){


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=area_actividad_agricola";

        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");
                        if(postgradosArray.length()>0){

                            JSONObject object = postgradosArray.getJSONObject(0);
                            String hortalizas = object.getString("hortalizas");
                            String frutales = object.getString("frutales");
                            String cultivos_andinos = object.getString("cultivos_andinos");
                            String cultivos_tropicales = object.getString("cultivos_tropicales");
                            String floricultura = object.getString("floricultura");
                            String ornamentales = object.getString("ornamentales");
                            String desarrollo_y_evaluacion_de_proyectos = object.getString("desarrollo y evaluacion de proyectos");
                            String control_de_calidad = object.getString("control de calidad");
                            String manejo_y_remediacion = object.getString("manejo y remediacion");
                            String agroecologia_y_medioambiente = object.getString("agroecologia y medioambiente");
                            String nutricion_vegetal = object.getString("nutricion vegetal");
                            String otros = object.getString("otros");

                            if(hortalizas.equals("SI")){
                                area_agricola.add("Hortalizas");
                            }
                            if(frutales.equals("SI")){
                                area_agricola.add("Frutales");
                            }
                            if(cultivos_andinos.equals("SI")){
                                area_agricola.add("Cultivos Andinos");
                            }
                            if(cultivos_tropicales.equals("SI")){
                                area_agricola.add("Cultivos tropicales");
                            }
                            if(floricultura.equals("SI")){
                                area_agricola.add("Floricultura");
                            }
                            if(ornamentales.equals("SI")){
                                area_agricola.add("Ornamentales");
                            }
                            if(desarrollo_y_evaluacion_de_proyectos.equals("SI")){
                                area_agricola.add("Desarrollo y evaluación de proyectos");
                            }
                            if(control_de_calidad.equals("SI")){
                                area_agricola.add("Control de calidad");
                            }
                            if(manejo_y_remediacion.equals("SI")){
                                area_agricola.add("Manejo y remediación");
                            }
                            if(agroecologia_y_medioambiente.equals("SI")){
                                area_agricola.add("Agroecología y medioambiente");
                            }
                            if(nutricion_vegetal.equals("SI")){
                                area_agricola.add("Nutrición vegetal");
                            }
                            if(otros.equals("SI")){
                                String area_otros=object.getString("area_otros");
                                area_agricola.add("Otros: "+area_otros);
                            }

                        }
                        setAgricolaAdapter(area_agricola);

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
    public void LoadPecuaria(){


        iGoogleSheets1 = Common.iGSGetMethodClient1(Common.BASE_URL1);
        String pathUrl1;
        pathUrl1 = "exec?id=" + Common.getUsername().toString()+"&sheet=area_actividad_pecuaria";


        try {
            iGoogleSheets1.getPeople(pathUrl1).enqueue(new Callback<String>() {

                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {

                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray postgradosArray = responseObject.getJSONArray("persons");

                        if(postgradosArray.length()>0){


                            JSONObject object = postgradosArray.getJSONObject(0);
                            String bovinos_leche = object.getString("bovinos leche");
                            String bovinos_carne = object.getString("bovinos carne");
                            String porcinos = object.getString("porcinos");
                            String bovinos_doble_proposito = object.getString("bovinos doble proposito");
                            String rumiantes_menores = object.getString("rumiantes menores");
                            String especies_menores = object.getString("especies menores");
                            String avicultura_ponedoras = object.getString("avicultura ponedoras");
                            String avicultura_broilers = object.getString("avicultura broilers");
                            String acuicultura_agua_dulce = object.getString("acuicultura agua dulce");
                            String acuicultura_tropical = object.getString("acuicultura tropical");
                            String pastos_y_forrajes = object.getString("pastos y forrajes");
                            String nutricion_animal = object.getString("nutricion animal");
                            String reproduccion_animal = object.getString("reproduccion animal");
                            String sanidad = object.getString("sanidad");
                            String desarrollo_y_evaluacion_de_proyectos = object.getString("desarrollo y evaluacion de proyectos");
                            String otros = object.getString("otros");

                            if(bovinos_leche.equals("SI")){
                                area_pecuaria.add("Bovinos leche");


                            }
                            if(bovinos_carne.equals("SI")){
                                area_pecuaria.add("Bovinos carne");

                            }
                            if(porcinos.equals("SI")){
                                area_pecuaria.add("Porcinos");

                            }
                            if(bovinos_doble_proposito.equals("SI")){
                                area_pecuaria.add("Bovinos doble propósito");
                            }
                            if(rumiantes_menores.equals("SI")){
                                area_pecuaria.add("Rumiantes menores");
                            }
                            if(especies_menores.equals("SI")){
                                area_pecuaria.add("Especies menores");
                            }
                            if(avicultura_ponedoras.equals("SI")){
                                area_pecuaria.add("Avicultura ponedoras");
                            }
                            if(avicultura_broilers.equals("SI")){
                                area_pecuaria.add("Avicultura broilers");
                            }
                            if(acuicultura_agua_dulce.equals("SI")){
                                area_pecuaria.add("Acuicultura agua dulce");
                            }
                            if(acuicultura_tropical.equals("SI")){
                                area_pecuaria.add("Acuicultura tropical");
                            }
                            if(pastos_y_forrajes.equals("SI")){
                                area_pecuaria.add("Pastos y forrajes");
                            }
                            if(nutricion_animal.equals("SI")){
                                area_pecuaria.add("Nutrición animal");
                            }
                            if(reproduccion_animal.equals("SI")){
                                area_pecuaria.add("Reproducción animal");
                            }
                            if(sanidad.equals("SI")){
                                area_pecuaria.add("Sanidad");
                            }
                            if(desarrollo_y_evaluacion_de_proyectos.equals("SI")){
                                area_pecuaria.add("Desarrollo y evaluación de proyectos");
                            }
                            if(otros.equals("SI")){

                                String actividad_otros=object.getString("actividad_otros");
                                area_pecuaria.add("Otros: "+actividad_otros);
                            }



                        }

                        setPecuariaAdapter(area_pecuaria);

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
    public void LoadConferencias(){


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
                                conferencias.add("Disponibilidad: Sí");
                                String modalidad = object.getString("modalidad");
                                conferencias.add("Modalidad:"+modalidad);
                            }else{
                                conferencias.add("Disponibilidad: NO");
                            }
                        }

                        setConferenciasAdapter(conferencias);

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
    public void LoadPasantes(){


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
                                pasantes.add("Posibilidad: Sí");
                                String cantidad = object.getString("cantidad");
                                pasantes.add("Cantidad: "+cantidad);
                            }else{
                                pasantes.add("Posibilidad: NO");
                            }
                        }

                        setPasantesAdapter(pasantes);

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
    public void LoadCapacitacion(){


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
                                capacitaciones.add("Curso: "+tema_curso);
                            }
                            if(diplomado.equals("SI")){
                                String tema_diplomado = object.getString("tema_diplomado");
                                capacitaciones.add("Diplomado: "+tema_diplomado);
                            }
                            if(especialidad.equals("SI")){
                                String tema_especialidad = object.getString("tema_especialidad");
                                capacitaciones.add("Especialidad: "+tema_especialidad);
                            }
                            if(maestria.equals("SI")){
                                String tema_maestria = object.getString("tema_maestria");
                                capacitaciones.add("Maestría: "+tema_maestria);
                            }
                        }

                        setCapacitacionAdapter(capacitaciones);

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
    private void setConferenciasAdapter(List<String> conference) {
        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, conference);
        recyclerConferencias.setLayoutManager(manager);
        recyclerConferencias.setAdapter(profesionalAdapter );
    }
    private void setPasantesAdapter(List<String> pasante) {
        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, pasante);
        recyclerPasantes.setLayoutManager(manager);
        recyclerPasantes.setAdapter(profesionalAdapter );
    }
    private void setCapacitacionAdapter(List<String> capacitacion) {
        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, capacitacion);
        recyclerCapacitacion.setLayoutManager(manager);
        recyclerCapacitacion.setAdapter(profesionalAdapter );
    }

    private void setPecuariaAdapter(List<String> pecuaria_area) {

        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, pecuaria_area);
        recyclerPecuaria.setLayoutManager(manager);
        recyclerPecuaria.setAdapter(profesionalAdapter );
    }

    private void setFormacionAdapter(List<FormacionAcademica> formacionAcademica) {
        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        FormacionAcademicaAdapter formacionAcademicaAdapter = new FormacionAcademicaAdapter (ProfesionalActivity.this, formacionAcademica);
        recyclerFormacion.setLayoutManager(manager);
        recyclerFormacion.setAdapter(formacionAcademicaAdapter );
    }
    private void setProfesionalAdapter(List<String> actividadprofesional) {
        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, actividadprofesional);
        recyclerProfesional.setLayoutManager(manager);
        recyclerProfesional.setAdapter(profesionalAdapter );
    }
    private void setAgricolaAdapter(List<String> agricola_area) {

        LinearLayoutManager manager = new LinearLayoutManager(ProfesionalActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        ProfesionalAdapter profesionalAdapter = new ProfesionalAdapter (ProfesionalActivity.this, agricola_area);
        recyclerAgricola.setLayoutManager(manager);
        recyclerAgricola.setAdapter(profesionalAdapter );
    }
}
