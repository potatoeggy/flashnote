package com.example.flashnote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.flashnote.data.Card;
import com.example.flashnote.data.Tag;

import java.util.ArrayList;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    Card dummy;
    ArrayList<Card> userCards = new ArrayList<>();

    void makeDummy(){
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("test","james","#FFC0CB"));
        Date creation = new Date();
        dummy = new Card("james","termemaiterm","definterieriition",tags);
    }

    @SuppressLint("ResourceType")
    void loadCards(){
        makeDummy();
        userCards.add(dummy);

        ArrayList<Tag> tags = new ArrayList<>();
        for(Card card:userCards){
            for(Tag tag:card.getTagList()){
                if(!tags.contains(tag)) tags.add(tag);
            }
        }

        System.out.println("REEEEEEEEEEEEEE 33333 EEEEEEEEEEEE"+tags.get(0).getName()+"REEEEEEEEEEEEEEEEEEEEEEEE");

        LinearLayout playLayout = findViewById(R.id.cardLayout);
        for(final Tag tag:tags){
            CardView card = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(700, 200);
            cardParams.gravity = Gravity.CENTER;
            card.setLayoutParams(cardParams);
            card.setCardBackgroundColor(Color.parseColor(tag.getColour()));
            card.setRadius(30);
            playLayout.addView(card);

            ConstraintLayout subLayout = new ConstraintLayout(this);
            //subLayout.setPadding(50,50,50,50);
            CardView.LayoutParams subLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.MATCH_PARENT);
            subLayoutParams.gravity = Gravity.CENTER;
            subLayout.setLayoutParams(subLayoutParams);
            subLayout.setId(1233);
            card.addView(subLayout);

            TextView nameView = new TextView(this);
            nameView.setText(tag.getName());
            nameView.setTextSize(22);
            nameView.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nameView.setId(4566);
            subLayout.addView(nameView);

            TextView buffer = new TextView(this);
            buffer.setText(" ");
            buffer.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buffer.setId(7899);
            subLayout.addView(buffer);

            ImageButton playButton = new ImageButton(this);
            playButton.setImageResource(R.drawable.play);
            playButton.setLayoutParams(new ConstraintLayout.LayoutParams(150, 150));
            playButton.setId(1011122);
            subLayout.addView(playButton);

            ConstraintSet set = new ConstraintSet();
            set.clone(subLayout);
            set.connect(buffer.getId(), ConstraintSet.LEFT, subLayout.getId(), ConstraintSet.LEFT);
            set.connect(buffer.getId(), ConstraintSet.RIGHT, subLayout.getId(), ConstraintSet.RIGHT);
            set.connect(buffer.getId(), ConstraintSet.TOP, subLayout.getId(), ConstraintSet.TOP);
            set.connect(buffer.getId(), ConstraintSet.BOTTOM, subLayout.getId(), ConstraintSet.BOTTOM);

            set.connect(playButton.getId(), ConstraintSet.TOP, subLayout.getId(), ConstraintSet.TOP);
            set.connect(playButton.getId(), ConstraintSet.BOTTOM, subLayout.getId(), ConstraintSet.BOTTOM);
            set.connect(playButton.getId(), ConstraintSet.RIGHT, subLayout.getId(), ConstraintSet.RIGHT);
            set.connect(playButton.getId(), ConstraintSet.LEFT, buffer.getId(), ConstraintSet.RIGHT);

            set.connect(nameView.getId(), ConstraintSet.TOP, subLayout.getId(), ConstraintSet.TOP);
            set.connect(nameView.getId(), ConstraintSet.BOTTOM, subLayout.getId(), ConstraintSet.BOTTOM);
            set.connect(nameView.getId(), ConstraintSet.LEFT, subLayout.getId(), ConstraintSet.LEFT);
            set.connect(nameView.getId(), ConstraintSet.RIGHT, buffer.getId(), ConstraintSet.LEFT);
            set.applyTo(subLayout);

            playButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    State.playing = new ArrayList<>();
                    State.playingIndex = 0;
                    o: for(final Card card:userCards){
                        for(final Tag curTag:card.getTagList()){
                            if(curTag.getName().equals(tag.getName())){
                                State.playing.add(card);
                                continue o;
                            }
                        }
                    }
                    Intent intent = new Intent(ProfileActivity.this, PlayCard.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadCards();
    }
}
