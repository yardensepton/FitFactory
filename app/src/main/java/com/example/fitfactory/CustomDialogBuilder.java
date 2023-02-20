package com.example.fitfactory;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomDialogBuilder extends AlertDialog.Builder {

    private TextView popup_TXT_users;
    private TextView popup_TXT_title;

    public CustomDialogBuilder(Context context) {
        super(context);
        View customView = View.inflate(context, R.layout.pop_up, null);
        popup_TXT_title = customView.findViewById(R.id.popup_TXT_title);
        popup_TXT_users = customView.findViewById(R.id.popup_TXT_users);
        setView(customView);
    }

    public CustomDialogBuilder setTitle(String title) {
        popup_TXT_title.setText(title);
        return this;
    }

    public CustomDialogBuilder setSignedUsers(ArrayList<String> users){
        StringBuilder total = new StringBuilder();
        int i = 1;
        for (String user: users) {
            total.append(i).append(". ").append(user).append("\n");
            i++;
        }
        popup_TXT_users.setText(total);
        return this;
    }



}
