package com.example.downloadimageexample;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ImageProgressFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Image Dowload in Progress");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }
}
