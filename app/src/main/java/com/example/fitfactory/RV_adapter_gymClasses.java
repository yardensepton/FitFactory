package com.example.fitfactory;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Model.GymClass;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;

public class RV_adapter_gymClasses extends RecyclerView.Adapter<RV_adapter_gymClasses.GymClassesViewHolder> {
    private final ArrayList<GymClass> gymClasses;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private Dialog dialog;


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
        GymClassesViewHolder viewHolder = new GymClassesViewHolder(view);
//        initDialog(viewHolder);
//        viewHolder.itemView.setOnClickListener(v -> {
//            dialog.show();
//        });

        return viewHolder;
    }

    private void initDialog(RecyclerView.ViewHolder viewHolder) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.pop_up);
        ImageView popup_BTN_yes = dialog.findViewById(R.id.popup_BTN_yes);
        popup_BTN_yes.setOnClickListener(v -> {

        });

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RV_adapter_gymClasses.GymClassesViewHolder holder, int position) {
        holder.RV_IMG_className.setText("" + gymClasses.get(position).getName().toString() + " with " + gymClasses.get(position).getTeacherName());
        holder.RV_IMG_classPic.setImageResource(gymClasses.get(position).getImageRes());
        holder.RV_IMG_classStatus.setText("free");
        holder.RV_IMG_classStartTime.setText("" + gymClasses.get(position).getStartHour() + ":" + "00" + "-" + gymClasses.get(position).getFinishedHour() + ":00");
        holder.RV_IMG_classDuration.setText("1 hour");
        holder.RV_IMG_classDate.setText("" + gymClasses.get(position).getDate());

        holder.itemView.setOnClickListener(v -> {
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
        MaterialTextView RV_IMG_className, RV_IMG_classDate, RV_IMG_classStatus, RV_IMG_classDuration, RV_IMG_classStartTime;

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
