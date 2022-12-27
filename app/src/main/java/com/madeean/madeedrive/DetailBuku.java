package com.madeean.madeedrive;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;

public class DetailBuku extends AppCompatActivity {
    ImageView iv_detail_buku;
    TextView tv_judul,tv_sinopsis;
    Button btn_download,btn_copy;
    EditText et_shortlink;


    private String path_download;
    private TextView file_downloaded_path, file_name, downloading_percent;
    private ProgressBar progressBar;
    private Button btnStart, btnCancel, buttonDownload;
    private RelativeLayout details;
    int downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);
        iv_detail_buku = findViewById(R.id.iv_detail_buku);
        tv_judul = findViewById(R.id.tv_judul);
        tv_sinopsis = findViewById(R.id.tv_sinopsis);
        btn_download = findViewById(R.id.btn_download);
        btn_copy = findViewById(R.id.btn_copy);
        et_shortlink = findViewById(R.id.et_shortlink);







//        get intent
        String judul = getIntent().getStringExtra("judul");
        String sinopsis = getIntent().getStringExtra("sinopsis");
        String foto_buku = getIntent().getStringExtra("foto_buku");
        String shortlink = getIntent().getStringExtra("shortlink");




        tv_judul.setText(judul);
        tv_sinopsis.setText(sinopsis);
        et_shortlink.setText(shortlink);
        String path = foto_buku;
        String extension = path.substring(path.lastIndexOf("."));
        System.out.println("EXTENSION : "+ extension);
        if(extension.equals(".pdf")) {
            iv_detail_buku.setImageResource(R.drawable.pdf);
        }else if(extension.equals(".flac")){
            iv_detail_buku.setImageResource(R.drawable.flac);
        }else if(extension.equals(".mp4")) {
            iv_detail_buku.setImageResource(R.drawable.mp4);
        }else if(extension.equals(".zip")) {
            iv_detail_buku.setImageResource(R.drawable.zip);
        }else if(extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg")){
            Glide.with(this).load(foto_buku).into(iv_detail_buku);
        }else{
            iv_detail_buku.setImageResource(R.drawable.dummy);
        }

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("shortlink telah di copy", et_shortlink.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

        System.out.println("SHORTLINK : "+ foto_buku);


        PRDownloader.initialize(getApplicationContext());

        // finding textview by its id
        file_downloaded_path = findViewById(R.id.txt_url);

        // finding textview by its id
        file_name = findViewById(R.id.file_name);

        // finding progressbar by its id
        progressBar = findViewById(R.id.progress_horizontal);

        // finding textview by its id
        downloading_percent = findViewById(R.id.downloading_percentage);

        // finding button by its id
        btnStart = findViewById(R.id.btn_start);

        // finding button by its id
        btnCancel = findViewById(R.id.btn_stop);

        // finding linear layout by its id
        details = findViewById(R.id.details_box);


        path_download = Utils.getRootDirPath(this);


        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = foto_buku;
                details.setVisibility(View.VISIBLE);
                // calling method downloadFile passing url as parameter
                downloadFile(url);
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void downloadFile(final String url) {

        // handing click event on start button
        // which starts the downloading of the file
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // checks if the process is already running
                if (Status.RUNNING == PRDownloader.getStatus(downloadID)) {
                    // pauses the download if
                    // user click on pause button
                    PRDownloader.pause(downloadID);
                    return;
                }

                // enabling the start button
                btnStart.setEnabled(false);

                // checks if the status is paused
                if (Status.PAUSED == PRDownloader.getStatus(downloadID)) {
                    // resume the download if download is paused
                    PRDownloader.resume(downloadID);
                    return;
                }

                // getting the filename
                String fileName = URLUtil.guessFileName(url, null, null);

                // setting the file name
                file_name.setText("Downloading " + fileName);

                // making the download request
                downloadID = PRDownloader.download(url, path_download, fileName)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onStartOrResume() {
                                progressBar.setIndeterminate(false);
                                // enables the start button
                                btnStart.setEnabled(true);
                                // setting the text of start button to pause
                                btnStart.setText("Pause");
                                // enabling the stop button
                                btnCancel.setEnabled(true);
                                Toast.makeText(DetailBuku.this, "Downloading started", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                // setting the text of start button to resume
                                // when the download is in paused state
                                btnStart.setText("Resume");
                                Toast.makeText(DetailBuku.this, "Downloading Paused", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                // resetting the downloadId when
                                // the download is cancelled
                                downloadID = 0;
                                // setting the text of start button to start
                                btnStart.setText("Start");
                                // disabling the cancel button
                                btnCancel.setEnabled(false);
                                // resetting the progress bar
                                progressBar.setProgress(0);
                                // resetting the download percent
                                downloading_percent.setText("");
                                progressBar.setIndeterminate(false);
                                Toast.makeText(DetailBuku.this, "Downloading Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                // getting the progress of download
                                long progressPer = progress.currentBytes * 100 / progress.totalBytes;
                                // setting the progress to progressbar
                                progressBar.setProgress((int) progressPer);
                                // setting the download percent
                                downloading_percent.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                progressBar.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {

                            @Override
                            public void onDownloadComplete() {
                                // disabling the start button
                                btnStart.setEnabled(false);
                                // disabling the cancel button
                                btnCancel.setEnabled(false);
                                // setting the text completed to start button
                                btnStart.setText("Completed");
                                // will show the path after the file is downloaded
                                file_downloaded_path.setText("File stored at : " + path_download);
                                Toast.makeText(DetailBuku.this, "Downloading Completed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Error error) {
                                // setting the text start
                                btnStart.setText("Start");
                                // resetting the download percentage
                                downloading_percent.setText("0");
                                // resetting the progressbar
                                progressBar.setProgress(0);
                                // resetting the downloadID
                                downloadID = 0;
                                // enabling the start button
                                btnStart.setEnabled(true);
                                // disabling the cancel button
                                btnCancel.setEnabled(false);
                                progressBar.setIndeterminate(false);
                                Toast.makeText(DetailBuku.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                System.out.println("Error : " + error.getServerErrorMessage());
                            }
                        });

                // handling click event on cancel button
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnStart.setText("Start");
                        // cancels the download
                        PRDownloader.cancel(downloadID);
                    }
                });
            }
        });
    }
}