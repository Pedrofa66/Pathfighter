package com.example.pathfighter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements View.OnClickListener{
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        view.setOnClickListener(this);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElement> items){ mData = items;}

    public void setOnclickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView nombrePJ, usuarioPJ, nivelPJ;
        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombrePJ = itemView.findViewById(R.id.nombreElementTextView);
            usuarioPJ = itemView.findViewById(R.id.usuarioElementTextView);
            nivelPJ = itemView.findViewById(R.id.nivelElementTextView);
        }


        void bindData(final ListElement item){
            nombrePJ.setText(item.getNombre());
            usuarioPJ.setText(item.getUsuario());
            nivelPJ.setText(item.getNivel());
            if(item.getClase().equals("add")){
                iconImage.setImageResource(R.drawable.outline_add_24);
            }else if(item.getClase().equals("Picaro")){
                iconImage.setImageResource(R.drawable.daco_1482423);
            }else if(item.getClase().equals("Guerrero")){
                iconImage.setImageResource(R.drawable.outline_shield_24);
            }else if(item.getClase().equals(("Mago"))){
                iconImage.setImageResource(R.drawable.outline_local_fire_department_24);
            }

        }
    }




}
