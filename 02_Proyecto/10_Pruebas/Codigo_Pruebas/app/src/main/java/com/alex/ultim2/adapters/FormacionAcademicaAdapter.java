package com.alex.ultim2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.ultim2.R;
import com.alex.ultim2.models.FormacionAcademica;

import java.util.List;

public class FormacionAcademicaAdapter extends RecyclerView.Adapter<FormacionAcademicaAdapter.FormacionViewHolder>{
    List<FormacionAcademica> mData;
    LayoutInflater mInflater;
    Context context;
    public FormacionAcademicaAdapter(Context context,List<FormacionAcademica> itemList){
        //this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;

    }
    @NonNull
    @Override
    public FormacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FormacionViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_formacion_academica, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull FormacionViewHolder holder, int position) {
        FormacionAcademica pos = mData.get(holder.getAdapterPosition());
        holder.denominacion.setText(pos.getDenominacion());
        holder.titulo.setText(pos.getTitulo());


    }
    @Override
    public int getItemCount(){
        return mData.size();
    }



    public static class FormacionViewHolder extends RecyclerView.ViewHolder {
        TextView denominacion,titulo;

        public FormacionViewHolder(@NonNull View itemView) {
            super(itemView);
            denominacion = itemView.findViewById(R.id.denominacionTextView);
            titulo = itemView.findViewById(R.id.tituloTextView);

        }
    }



}
