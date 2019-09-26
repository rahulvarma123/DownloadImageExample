package com.example.downloadimageexample;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ImageProgressFragment extends DialogFragment {

    ProgressDialog progressDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Image Download in Progress");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        return progressDialog;
    }

    public void showProgress(int progress) {
        progressDialog.setProgress(progress);
    }
}
