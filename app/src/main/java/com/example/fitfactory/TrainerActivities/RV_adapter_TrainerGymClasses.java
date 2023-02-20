package com.example.fitfactory.TrainerActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Finals;
import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.R;
import com.example.fitfactory.TrainerActivities.Fragments.RV_Nested_info;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class RV_adapter_TrainerGymClasses extends RecyclerView.Adapter<RV_adapter_TrainerGymClasses.GymClassesTrainerViewHolder> {

    private final ArrayList<GymClass> gymClasses;
    private final Context context;
    private RV_Nested_info nested_info;
    //    private RV_adapter_TrainerGymClasses.OnItemClickListener onItemClickListener;
    private ArrayList<User> users = new ArrayList<>();
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
        holder.RV_IMG_className.setText("" + gymClasses.get(position).getName().toString());
        holder.RV_IMG_classStartTime.setText("" + gymClasses.get(position).getStartHour() + ":" + "00" + "-" + gymClasses.get(position).getFinishedHour() + ":00");
        holder.RV_IMG_classDuration.setText("1 hour");
        holder.RV_IMG_classDate.setText("" + gymClasses.get(position).getDate());
        holder.RV_IMG_classPic.setImageResource(gymClass.getImageRes());

        boolean isExpandable = gymClass.isExpandable();
        holder.expandable_layout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        if (isExpandable) {
            holder.arrow.setImageResource(R.drawable.arrow_up);
        } else {
            holder.arrow.setImageResource(R.drawable.arrow_down);
        }

        nested_info = new RV_Nested_info(users);
        holder.child_rv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.child_rv.setHasFixedSize(true);
        holder.child_rv.setAdapter(nested_info);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                gymClass.setExpandable(!gymClass.isExpandable());
                getAllSignedUsersOfClassFromDB(gymClass);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });


    }

    private void getAllSignedUsersOfClassFromDB(GymClass gymClass) {

        db.getReference().child(Finals.users).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    users.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.hasChild(Finals.gymClasses)) {
                            User user = ds.getValue(User.class);
                            assert user != null;
                            Log.d("user exists", "" + user.getName());
                            for (String us : user.getGymClasses()) {
                                Log.d("user has a class", "" + us);
                                if (us.equals(gymClass.getClassUUid())) {
                                    users.add(user);
                                    Log.d("yayyyyyyyyyyyyyyyyy 3", "");
                                }
                            }
                        }

                    }
                    notifyDataSetChanged();
                    Log.d("signed", users + "");

                } else {
                    users.clear();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    public class GymClassesTrainerViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView RV_IMG_classPic;
        MaterialTextView RV_IMG_className, RV_IMG_classDate, RV_IMG_classDuration, RV_IMG_classStartTime;
        RelativeLayout expandable_layout;
        RecyclerView child_rv;
        LinearLayout linearLayout;
        ImageView arrow;


        public GymClassesTrainerViewHolder(@NonNull View itemView) {
            super(itemView);
            RV_IMG_classPic = itemView.findViewById(R.id.RV_IMG_classPic);
            RV_IMG_className = itemView.findViewById(R.id.RV_IMG_className);
            RV_IMG_classDuration = itemView.findViewById(R.id.RV_IMG_classDuration);
            RV_IMG_classStartTime = itemView.findViewById(R.id.RV_IMG_classStartTime);
            RV_IMG_classDate = itemView.findViewById(R.id.RV_IMG_classDate);
            child_rv = itemView.findViewById(R.id.child_rv);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            arrow = itemView.findViewById(R.id.arrow_imageview);
            expandable_layout = itemView.findViewById(R.id.expandable_layout);


        }


    }
}
