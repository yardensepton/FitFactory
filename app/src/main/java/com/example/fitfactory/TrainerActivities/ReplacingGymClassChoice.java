package com.example.fitfactory.TrainerActivities;

import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fitfactory.R;

public class ReplacingGymClassChoice {

    public ReplacingGymClassChoice() {
    }

    public void boxingPressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit,ImageButton addClass_PIC_cycling,ImageButton addClass_PIC_dance
            ,ImageButton addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.selected_boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.cycling);
        addClass_PIC_dance.setImageResource(R.drawable.dance);
        addClass_PIC_yoga.setImageResource(R.drawable.yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.pilates);
        boxing_name.setTextColor(Color.parseColor("#A41C1C"));
        crossfit_name.setTextColor(Color.parseColor("#000000"));
        yoga_name.setTextColor(Color.parseColor("#000000"));
        pilates_name.setTextColor(Color.parseColor("#000000"));
        dance_name.setTextColor(Color.parseColor("#000000"));
        cycling_name.setTextColor(Color.parseColor("#000000"));
    }

    public void yogaPressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit,ImageButton addClass_PIC_cycling,ImageButton addClass_PIC_dance,ImageButton
            addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.cycling);
        addClass_PIC_dance.setImageResource(R.drawable.dance);
        addClass_PIC_yoga.setImageResource(R.drawable.selected_yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.pilates);
        yoga_name.setTextColor(Color.parseColor("#A41C1C"));
        boxing_name.setTextColor(Color.parseColor("#000000"));
        crossfit_name.setTextColor(Color.parseColor("#000000"));
        pilates_name.setTextColor(Color.parseColor("#000000"));
        dance_name.setTextColor(Color.parseColor("#000000"));
        cycling_name.setTextColor(Color.parseColor("#000000"));
    }

    public void dancePressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit,ImageButton addClass_PIC_cycling,
                             ImageButton addClass_PIC_dance,ImageButton addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.cycling);
        addClass_PIC_dance.setImageResource(R.drawable.selected_dance);
        addClass_PIC_yoga.setImageResource(R.drawable.yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.pilates);
        dance_name.setTextColor(Color.parseColor("#A41C1C"));
        boxing_name.setTextColor(Color.parseColor("#000000"));
        yoga_name.setTextColor(Color.parseColor("#000000"));
        pilates_name.setTextColor(Color.parseColor("#000000"));
        crossfit_name.setTextColor(Color.parseColor("#000000"));
        cycling_name.setTextColor(Color.parseColor("#000000"));
    }
    public void pilatesPressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit,ImageButton
            addClass_PIC_cycling,ImageButton addClass_PIC_dance,ImageButton addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.cycling);
        addClass_PIC_dance.setImageResource(R.drawable.dance);
        addClass_PIC_yoga.setImageResource(R.drawable.yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.selected_pilates);
        pilates_name.setTextColor(Color.parseColor("#A41C1C"));
        boxing_name.setTextColor(Color.parseColor("#000000"));
        yoga_name.setTextColor(Color.parseColor("#000000"));
        crossfit_name.setTextColor(Color.parseColor("#000000"));
        dance_name.setTextColor(Color.parseColor("#000000"));
        cycling_name.setTextColor(Color.parseColor("#000000"));
    }
    public void crossfitPressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit,ImageButton addClass_PIC_cycling
            ,ImageButton addClass_PIC_dance,ImageButton addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.selected_crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.cycling);
        addClass_PIC_dance.setImageResource(R.drawable.dance);
        addClass_PIC_yoga.setImageResource(R.drawable.yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.pilates);
        crossfit_name.setTextColor(Color.parseColor("#A41C1C"));
        boxing_name.setTextColor(Color.parseColor("#000000"));
        yoga_name.setTextColor(Color.parseColor("#000000"));
        pilates_name.setTextColor(Color.parseColor("#000000"));
        dance_name.setTextColor(Color.parseColor("#000000"));
        cycling_name.setTextColor(Color.parseColor("#000000"));
    }

    public void cyclingPressed(ImageButton addClass_PIC_boxing, ImageButton addClass_PIC_crossfit, ImageButton addClass_PIC_cycling,
                               ImageButton addClass_PIC_dance, ImageButton addClass_PIC_pilates, ImageButton addClass_PIC_yoga, TextView boxing_name,TextView pilates_name, TextView yoga_name,TextView crossfit_name, TextView dance_name,TextView cycling_name){
        addClass_PIC_boxing.setImageResource(R.drawable.boxing);
        addClass_PIC_crossfit.setImageResource(R.drawable.crossfit);
        addClass_PIC_cycling.setImageResource(R.drawable.selected_cycling);
        addClass_PIC_dance.setImageResource(R.drawable.dance);
        addClass_PIC_yoga.setImageResource(R.drawable.yoga);
        addClass_PIC_pilates.setImageResource(R.drawable.pilates);
        cycling_name.setTextColor(Color.parseColor("#A41C1C"));
        boxing_name.setTextColor(Color.parseColor("#000000"));
        yoga_name.setTextColor(Color.parseColor("#000000"));
        pilates_name.setTextColor(Color.parseColor("#000000"));
        dance_name.setTextColor(Color.parseColor("#000000"));
        crossfit_name.setTextColor(Color.parseColor("#000000"));

    }

}
