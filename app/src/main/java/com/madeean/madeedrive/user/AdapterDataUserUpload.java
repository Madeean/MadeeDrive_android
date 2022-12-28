package com.madeean.madeedrive.user;

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

import com.bumptech.glide.Glide;
import com.madeean.madeedrive.DetailBuku;
import com.madeean.madeedrive.R;
import com.madeean.madeedrive.model.ModelIsiData;

import java.util.List;

public class AdapterDataUserUpload extends RecyclerView.Adapter<AdapterDataUserUpload.HolderData> {
    List<ModelIsiData> listData;
    LayoutInflater layoutInflater;


    public AdapterDataUserUpload(Context context, List<ModelIsiData> listData) {
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
            String foto_buku = listData.get(position).getFoto_buku();
            String shortlink = listData.get(position).getShorturl();
            String judul = listData.get(position).getJudul();
            String sinopsis = listData.get(position).getSinopsis();
            Intent intent = new Intent(view.getContext(), DetailBuku.class);
            intent.putExtra("foto_buku", foto_buku);
            intent.putExtra("shortlink", shortlink);
            intent.putExtra("judul", judul);
            intent.putExtra("sinopsis", sinopsis);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if(listData.size() > 0){
            return listData.size();
        }else{
            return 0;
        }
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
