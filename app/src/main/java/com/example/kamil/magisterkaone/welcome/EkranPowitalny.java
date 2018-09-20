package com.example.kamil.magisterkaone.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.kamil.magisterkaone.MainActivity;
import com.example.kamil.magisterkaone.R;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class EkranPowitalny extends Activity {

    private static int witanie = 4000;

    private GifImageView gifImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran_powitalny);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        try {
            InputStream inputStream = getAssets().open("notesgif.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        } catch (IOException ex) {

        }

        new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i = new Intent(EkranPowitalny.this, MainActivity.class);
               startActivity(i);

               this.finish();
          }
            private void finish() {

           }},witanie);
   };
}
