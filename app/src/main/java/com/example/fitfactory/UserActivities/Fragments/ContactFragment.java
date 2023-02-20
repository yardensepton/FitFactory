package com.example.fitfactory.UserActivities.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.fitfactory.R;


public class ContactFragment extends Fragment {

    private ImageButton contact_call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contact, container, false);
        contact_call = view.findViewById(R.id.contact_call);
        contact_call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0123456789"));
            startActivity(intent);
        });
        return view;
    }
}