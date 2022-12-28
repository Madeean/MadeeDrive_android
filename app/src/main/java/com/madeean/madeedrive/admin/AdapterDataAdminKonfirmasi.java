package com.madeean.madeedrive.admin;

import android.content.Context;
import android.content.Intent;
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
import com.madeean.madeedrive.api.ApiRequest;
import com.madeean.madeedrive.api.Server;
import com.madeean.madeedrive.model.ModelIsiData;
import com.madeean.madeedrive.model.ModelIsiDataAuthLogin;
import com.madeean.madeedrive.model.ModelLogout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataAdminKonfirmasi extends RecyclerView.Adapter<AdapterDataAdminKonfirmasi.HolderData> {
    List<ModelIsiDataAuthLogin> listData;
    LayoutInflater layoutInflater;
    String token;


    public AdapterDataAdminKonfirmasi(Context context, List<ModelIsiDataAuthLogin> listData, String token) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
        this.token = token;

    }

    @NonNull
    @Override
    public AdapterDataAdminKonfirmasi.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data_admin_konfirmasi, parent, false);
        return new AdapterDataAdminKonfirmasi.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataAdminKonfirmasi.HolderData holder, int position) {
        holder.nama.setText(listData.get(position).getName());
        holder.email.setText(listData.get(position).getEmail());

        holder.btn_terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
                Call<ModelLogout> konfirmasi = api.terimaKonfirmasi(token, listData.get(position).getId());
                konfirmasi.enqueue(new Callback<ModelLogout>() {
                    @Override
                    public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(holder.itemView.getContext(), "Berhasil terima user", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(holder.itemView.getContext(), AdminKonfirmasi.class);
                            holder.itemView.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Gagal terima user", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelLogout> call, Throwable t) {
                        Toast.makeText(v.getContext(), "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiRequest api = Server.konekRetrofit().create(ApiRequest.class);
                Call<ModelLogout> konfirmasi = api.tolakKonfirmasi(token, listData.get(position).getId());
                konfirmasi.enqueue(new Callback<ModelLogout>() {
                    @Override
                    public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(holder.itemView.getContext(), "Berhasil tolak user", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(holder.itemView.getContext(), AdminKonfirmasi.class);
                            holder.itemView.getContext().startActivity(intent);
                        } else {
                            Toast.makeText(holder.itemView.getContext(), "Gagal tolak user", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelLogout> call, Throwable t) {
                        Toast.makeText(v.getContext(), "gagal menghubungi server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

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