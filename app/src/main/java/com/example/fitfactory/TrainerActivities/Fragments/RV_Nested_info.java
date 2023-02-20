package com.example.fitfactory.TrainerActivities.Fragments;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Model.User;
import com.example.fitfactory.R;

import java.util.ArrayList;

public class RV_Nested_info extends RecyclerView.Adapter<RV_Nested_info.NestedViewHolder> {

    private ArrayList<User> users;


    public RV_Nested_info(ArrayList<User> users) {
        this.users = users;
    }



    @NonNull
    @Override
    public RV_Nested_info.NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.signed_user_item, parent, false);
        return new RV_Nested_info.NestedViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_Nested_info.NestedViewHolder holder, int position) {
        User user = users.get(position);
        holder.RV_TXT_signedUserName.setText(user.getName() + " " +user.getLastName());

    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public User getItem(int position) {
        return users.get(position);
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder {
        TextView RV_TXT_signedUserName;

        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_TXT_signedUserName = itemView.findViewById(R.id.RV_TXT_signedUserName);
        }
    }
}
