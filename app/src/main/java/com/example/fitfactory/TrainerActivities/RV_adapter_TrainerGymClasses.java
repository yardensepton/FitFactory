package com.example.fitfactory.TrainerActivities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.CustomDialogBuilder;
import com.example.fitfactory.Finals;
import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class RV_adapter_TrainerGymClasses extends RecyclerView.Adapter<RV_adapter_TrainerGymClasses.GymClassesTrainerViewHolder> {

    private final ArrayList<GymClass> gymClasses;
    private final Context context;
    //    private RV_adapter_TrainerGymClasses.OnItemClickListener onItemClickListener;
    private Dialog dialog;
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    public RV_adapter_TrainerGymClasses(Context context, ArrayList<GymClass> gymClasses) {
        this.gymClasses = gymClasses;
        Collections.sort(this.gymClasses);
        this.context = context;
    }


    @NonNull
    @Override
    public GymClassesTrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trainer_class_item, parent, false);
        return new GymClassesTrainerViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GymClassesTrainerViewHolder holder, int position) {
        GymClass gymClass = getItem(position);
        gymClass.setImageRes();
        holder.RV_IMG_className.setText("" + gymClasses.get(position).getName().toString() + " with " + gymClasses.get(position).getTeacherName());
        holder.RV_IMG_classStartTime.setText("" + gymClasses.get(position).getStartHour() + ":" + "00" + "-" + gymClasses.get(position).getFinishedHour() + ":00");
        holder.RV_IMG_classDuration.setText("1 hour");
        holder.RV_IMG_classDate.setText("" + gymClasses.get(position).getDate());
        holder.RV_IMG_classPic.setImageResource(gymClass.getImageRes());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getAllSignedUsersOfClassFromDB(gymClass);
                return true;
            }
        });
    }

    private void getAllSignedUsersOfClassFromDB(GymClass gymClass) {
        db.getReference().child(Finals.users).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> signedUsers = new ArrayList<>();
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        if (ds.hasChild(Finals.gymClasses)) {
                            User user = ds.getValue(User.class);
                            assert user != null;
                            Log.d("user exists", "" + user.getName());
                            for (String us : user.getGymClasses()) {
                                Log.d("user has a class", "" + us);
                                if (us.equals(gymClass.getClassUUid())) {
                                    signedUsers.add(user.getName() + " " + user.getLastName());
                                    Log.d("yayyyyyyyyyyyyyyyyy 3", "");
                                }
                            }
                        }

                    }
                    showSignedUsersDialog(signedUsers);
                } else {

                    Log.d("get classes of user in home user fra", "");
                }

            }
        });


    }

    private void showSignedUsersDialog(ArrayList<String> signedUsers) {
        CustomDialogBuilder builder = new CustomDialogBuilder(context);
        builder.setTitle("Signed Users");
        builder.setSignedUsers(signedUsers);
        if (!signedUsers.isEmpty()) {
            builder.show();
        }
    }


    @Override
    public int getItemCount() {
        return gymClasses == null ? 0 : gymClasses.size();
    }

    public GymClass getItem(int position) {
        return gymClasses.get(position);
    }

    public class GymClassesTrainerViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView RV_IMG_classPic;
        MaterialTextView RV_IMG_className, RV_IMG_classDate, RV_IMG_classDuration, RV_IMG_classStartTime;

        public GymClassesTrainerViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_IMG_classPic = itemView.findViewById(R.id.RV_IMG_classPic);
            RV_IMG_className = itemView.findViewById(R.id.RV_IMG_className);
            RV_IMG_classDuration = itemView.findViewById(R.id.RV_IMG_classDuration);
            RV_IMG_classStartTime = itemView.findViewById(R.id.RV_IMG_classStartTime);
            RV_IMG_classDate = itemView.findViewById(R.id.RV_IMG_classDate);


        }


    }
}
