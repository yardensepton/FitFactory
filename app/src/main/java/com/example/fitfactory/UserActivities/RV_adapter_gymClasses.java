package com.example.fitfactory.UserActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class RV_adapter_gymClasses extends RecyclerView.Adapter<RV_adapter_gymClasses.GymClassesViewHolder> {
    private final ArrayList<GymClass> gymClasses;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = db.getReference();;


    public RV_adapter_gymClasses(Context context, ArrayList<GymClass> gymClasses) {
        this.gymClasses = gymClasses;
        Collections.sort(this.gymClasses);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public RV_adapter_gymClasses.GymClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.class_item, parent, false);

        return new GymClassesViewHolder(view);
    }



    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RV_adapter_gymClasses.GymClassesViewHolder holder, int position) {
        GymClass gymClass = getItem(position);
        gymClass.setImageRes();
        holder.RV_IMG_className.setText("" + gymClasses.get(position).getName().toString() + " with " + gymClasses.get(position).getTeacherName());
         if (gymClass.isClassFull()){
            holder.RV_IMG_classStatus.setText("FULL");
             holder.RV_IMG_classStatus.setBackground(context.getDrawable(R.drawable.full_status_shape));
        }else if (gymClass.isClassInThePast()){
             holder.RV_IMG_classStatus.setText("CLOSED");
             holder.RV_IMG_classStatus.setBackground(context.getDrawable(R.drawable.closed_status_shape));
         } else if (gymClass.getSignedUsers().contains(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())){
             holder.RV_IMG_classStatus.setText("SIGNED");
             holder.RV_IMG_classStatus.setBackground(context.getDrawable(R.drawable.signed_status_shape));
         }
         else {
            holder.RV_IMG_classStatus.setText("AVAILABLE");
             holder.RV_IMG_classStatus.setBackground(context.getDrawable(R.drawable.status_shape));
        }

        holder.RV_IMG_classStartTime.setText("" + gymClasses.get(position).getStartHour() + ":" + "00" + "-" + gymClasses.get(position).getFinishedHour() + ":00");
        holder.RV_IMG_classDuration.setText("1 hour");
        holder.RV_IMG_classDate.setText("" + gymClasses.get(position).getDate());
        holder.RV_IMG_classPic.setImageResource(gymClass.getImageRes());

        holder.RV_IMG_classStatus.setOnClickListener(v -> {
            if (onItemClickListener !=null){
                onItemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return gymClasses == null ? 0 : gymClasses.size();
    }

    public GymClass getItem(int position) {
        return gymClasses.get(position);
    }

    public class GymClassesViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView RV_IMG_classPic;
        MaterialTextView RV_IMG_className, RV_IMG_classDate, RV_IMG_classDuration, RV_IMG_classStartTime;
        AppCompatButton RV_IMG_classStatus;

        public GymClassesViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_IMG_classPic = itemView.findViewById(R.id.RV_IMG_classPic);
            RV_IMG_className = itemView.findViewById(R.id.RV_IMG_className);
            RV_IMG_classStatus = itemView.findViewById(R.id.RV_IMG_classStatus);
            RV_IMG_classDuration = itemView.findViewById(R.id.RV_IMG_classDuration);
            RV_IMG_classStartTime = itemView.findViewById(R.id.RV_IMG_classStartTime);
            RV_IMG_classDate = itemView.findViewById(R.id.RV_IMG_classDate);


        }


    }




}
