package com.example.fitfactory.TrainerActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class RV_adapter_signedUsers  extends RecyclerView.Adapter<RV_adapter_signedUsers.SignedUsersViewHolder>{
    private final ArrayList<String> users;
    private final Context context;

    public RV_adapter_signedUsers( Context context,ArrayList<String> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public RV_adapter_signedUsers.SignedUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.signed_user_item, parent, false);

        return new SignedUsersViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_adapter_signedUsers.SignedUsersViewHolder holder, int position) {
        String user = getItem(position);
        holder.RV_TXT_signedUserName.setText(position+1+". "+user);
    }

    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public String getItem(int position) {
        return users.get(position);
    }

    public static class SignedUsersViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView RV_TXT_signedUserName;
        public SignedUsersViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_TXT_signedUserName = itemView.findViewById(R.id.RV_TXT_signedUserName);

        }




    }





}
