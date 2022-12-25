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

public class AdapterDataAdminKonfirmasi extends RecyclerView.Adapter<AdapterDataAdminKonfirmasi.HolderData> {
    List<String> listData;
    LayoutInflater layoutInflater;


    public AdapterDataAdminKonfirmasi(Context context, List<String> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterDataAdminKonfirmasi.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_admin_konfirmasi, parent, false);
        return new AdapterDataAdminKonfirmasi.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataAdminKonfirmasi.HolderData holder, int position) {
        holder.nama.setText(listData.get(position));
        holder.email.setText("email");

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView nama, email;
        Button btn_terima, btn_tolak;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_nama_admin_konfirmasi);
            email = itemView.findViewById(R.id.tv_email_admin_konfirmasi);
            btn_terima = itemView.findViewById(R.id.btn_terima_admin_konfirmasi);
            btn_tolak = itemView.findViewById(R.id.btn_tolak_admin_konfirmasi);


        }
    }
}