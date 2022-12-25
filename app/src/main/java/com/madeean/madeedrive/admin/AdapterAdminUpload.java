package com.madeean.madeedrive.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.madeean.madeedrive.R;

import java.util.List;

public class AdapterAdminUpload extends RecyclerView.Adapter<AdapterAdminUpload.HolderData> {
    List<String> listData;
    LayoutInflater layoutInflater;


    public AdapterAdminUpload(Context context, List<String> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterAdminUpload.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_admin_upload, parent, false);
        return new AdapterAdminUpload.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdminUpload.HolderData holder, int position) {
        holder.imageView.setImageResource(R.drawable.flac);
        holder.nama.setText(listData.get(position));
        holder.sinopsis.setText("Sinopsis");

        holder.btn_lihat.setOnClickListener(view -> {
            Toast.makeText(holder.btn_lihat.getContext(), "Login Dahulu", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nama, sinopsis;
        Button btn_lihat;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            btn_lihat = itemView.findViewById(R.id.btn_lihat_admin_upload);
            imageView = itemView.findViewById(R.id.iv_data_admin_upload);
            nama = itemView.findViewById(R.id.tv_nama_admin_upload);
            sinopsis = itemView.findViewById(R.id.tv_sinopsis_admin_upload);


        }
    }
}