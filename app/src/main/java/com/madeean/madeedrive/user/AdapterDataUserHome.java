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
import com.madeean.madeedrive.belumlogin.AdapterDataBelumLogin;

import java.util.List;

public class AdapterDataUserHome extends RecyclerView.Adapter<AdapterDataUserHome.HolderData> {
    List<String> listData;
    LayoutInflater layoutInflater;


    public AdapterDataUserHome(Context context, List<String> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterDataUserHome.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_user_home, parent, false);
        return new AdapterDataUserHome.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataUserHome.HolderData holder, int position) {
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
            btn_lihat = itemView.findViewById(R.id.btn_lihat_user_home);
            imageView = itemView.findViewById(R.id.iv_data_user_home);
            nama = itemView.findViewById(R.id.tv_nama_user_home);
            sinopsis = itemView.findViewById(R.id.tv_sinopsis_user_home);


        }
    }
}
