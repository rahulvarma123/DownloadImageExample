package com.example.downloadimageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String imageUrl = "http://freesms8.co.in/downloads/wallpapers/00988_paradiselost_1920x1200.jpg";
    ImageView imgView;
    Handler handler;
    ImageProgressFragment progressFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = findViewById(R.id.imgView);

        handler = new Handler();
    }

    public void download(View view) {

        progressFragment = new ImageProgressFragment();
        progressFragment.show(getSupportFragmentManager(), "Download");

        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        Thread thread = new Thread(imageDownloadTask);
        thread.start();

    }

    class ImageDownloadTask implements Runnable {

        @Override
        public void run() {
            try {

                // 1. Create URL object
                URL url = new URL(imageUrl);

                // 2. Open the Http Connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // 3. Checking the Response Code - if 200 - Success else failure
                if (urlConnection.getResponseCode() != 200) {
                    throw new Exception("Failed to get response");
                }

                // 4. Get Input Stream from Connection object
                InputStream inputStream = urlConnection.getInputStream();

                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressFragment.dismiss();  // UI logic
                        imgView.setImageBitmap(bitmap); // UI logic
                    }
                });


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
