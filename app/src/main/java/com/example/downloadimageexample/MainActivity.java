package com.example.downloadimageexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

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
        ImageDownloadTask imageDownloadTask = new ImageDownloadTask();
        imageDownloadTask.execute(imageUrl);

    }

    class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap> {//

        @Override
        protected void onPreExecute() { //Main Thread
            super.onPreExecute();
            progressFragment = new ImageProgressFragment();
            progressFragment.show(getSupportFragmentManager(), "Download");
        }

        @Override
        protected Bitmap doInBackground(String... strings) { //Worker Thread or Background Thread
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

                int totalImageSize = urlConnection.getContentLength();

                byte[] byteBuffer = new byte[1024];

                ByteBuffer mainBuffer = ByteBuffer.allocate(totalImageSize);

                int bufferByteCount = 0;
                int totalBufferBytes = 0;

                while ((bufferByteCount = inputStream.read(byteBuffer)) != -1) {
                    totalBufferBytes += bufferByteCount;
                    int progress = (totalBufferBytes*100) / totalImageSize;
                    mainBuffer.put(byteBuffer,0,bufferByteCount);
                    publishProgress(progress);
                }

                final Bitmap bitmap = BitmapFactory.decodeByteArray(mainBuffer.array(), 0, totalBufferBytes);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) { //Main Thread
            super.onProgressUpdate(values);
            progressFragment.showProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) { //Main Thread
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                progressFragment.dismiss();  // UI logic
                imgView.setImageBitmap(bitmap); // UI logic
            }

        }

    }
}
