package com.example.pathfighter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolderDatos>{

    List<TextElement> listDatos;

    public TextAdapter(List<TextElement> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_element,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            textView= (TextView) itemView.findViewById(R.id.textView);
        }

        public void asignarDatos(TextElement textElement) {
            textView.setText(textElement.getTexto());
        }
    }
}