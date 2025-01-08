package com.alex.ultim2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.ultim2.R;

import java.util.List;

public class ProfesionalAdapter extends RecyclerView.Adapter<ProfesionalAdapter.ProfesionalViewHolder>{
    List<String> mData;
    LayoutInflater mInflater;
    Context context;
    public ProfesionalAdapter(Context context,List<String> itemList){
        //this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;

    }
    @NonNull
    @Override
    public ProfesionalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfesionalViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_actividadprofesional, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesionalViewHolder holder, int position) {
        String pos = mData.get(holder.getAdapterPosition());
        holder.item1.setText(pos);
    }



    @Override
    public int getItemCount(){
        return mData.size();
    }



    public static class ProfesionalViewHolder extends RecyclerView.ViewHolder {

        TextView item1;

        public ProfesionalViewHolder(@NonNull View itemView) {
            super(itemView);

            item1 = itemView.findViewById(R.id.itemTextView);

        }
    }
}
