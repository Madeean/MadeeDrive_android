package com.madeean.madeedrive.belumlogin;

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

import com.bumptech.glide.Glide;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.model.ModelIsiData;

import java.util.List;

public class AdapterDataBelumLogin extends RecyclerView.Adapter<AdapterDataBelumLogin.HolderData> {
    List<ModelIsiData> listData;
    LayoutInflater layoutInflater;


    public AdapterDataBelumLogin(Context context, List<ModelIsiData> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public AdapterDataBelumLogin.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new AdapterDataBelumLogin.HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataBelumLogin.HolderData holder, int position) {
        holder.nama.setText(listData.get(position).getJudul());
        holder.sinopsis.setText(listData.get(position).getSinopsis());

        String path = listData.get(position).getFoto_buku();
        String extension = path.substring(path.lastIndexOf("."));
        System.out.println("EXTENSION : "+ listData.get(position).getShorturl());
        if(extension.equals(".pdf")) {
            holder.imageView.setImageResource(R.drawable.pdf);
        }else if(extension.equals(".flac")){
            holder.imageView.setImageResource(R.drawable.flac);
        }else if(extension.equals(".mp4")) {
            holder.imageView.setImageResource(R.drawable.mp4);
        }else if(extension.equals(".zip")) {
            holder.imageView.setImageResource(R.drawable.zip);
        }else if(extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg")){
            Glide.with(holder.imageView.getContext()).load(listData.get(position).getFoto_buku()).into(holder.imageView);
        }else{
            holder.imageView.setImageResource(R.drawable.dummy);
        }

        holder.btn_lihat.setOnClickListener(view -> {
            Toast.makeText(holder.btn_lihat.getContext(), "Login Dahulu", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nama, sinopsis;
        Button btn_lihat;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            btn_lihat = itemView.findViewById(R.id.btn_lihat_belum_login);
            imageView = itemView.findViewById(R.id.iv_data_belum_login);
            nama = itemView.findViewById(R.id.tv_nama_belum_login);
            sinopsis = itemView.findViewById(R.id.tv_sinopsis_belum_login);


        }
    }
}
