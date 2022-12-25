package com.madeean.madeedrive.user;

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

public class AdapterDataUserUpload extends RecyclerView.Adapter<AdapterDataUserUpload.HolderData> {
    List<String> listData;
    LayoutInflater layoutInflater;


    public AdapterDataUserUpload(Context context, List<String> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterDataUserUpload.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_user_upload, parent, false);
        return new AdapterDataUserUpload.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataUserUpload.HolderData holder, int position) {
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
            btn_lihat = itemView.findViewById(R.id.btn_lihat_user_upload);
            imageView = itemView.findViewById(R.id.iv_data_user_upload);
            nama = itemView.findViewById(R.id.tv_nama_user_upload);
            sinopsis = itemView.findViewById(R.id.tv_sinopsis_user_upload);


        }
    }
}