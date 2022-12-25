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
import com.madeean.madeedrive.user.AdapterDataUserHome;

import java.util.List;

public class AdapterDataAdminHome extends RecyclerView.Adapter<AdapterDataAdminHome.HolderData> {
    List<String> listData;
    LayoutInflater layoutInflater;


    public AdapterDataAdminHome(Context context, List<String> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterDataAdminHome.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_admin_home, parent, false);
        return new AdapterDataAdminHome.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataAdminHome.HolderData holder, int position) {
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
            btn_lihat = itemView.findViewById(R.id.btn_lihat_admin_home);
            imageView = itemView.findViewById(R.id.iv_data_admin_home);
            nama = itemView.findViewById(R.id.tv_nama_admin_home);
            sinopsis = itemView.findViewById(R.id.tv_sinopsis_admin_home);


        }
    }
}
