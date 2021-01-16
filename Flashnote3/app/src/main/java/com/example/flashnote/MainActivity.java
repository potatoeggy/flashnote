package com.example.flashnote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Card dummy;
    ArrayList<Card> cards = new ArrayList<>();

    void makeDummy(){
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("#FFC0CB","test"));
        Date creation = new Date();
        dummy = new Card(tags, "termmyTerm", "definteely a def",creation,"urmom");
    }

    @SuppressLint("ResourceType")
    void loadCards(){
        makeDummy();
        cards.clear();
        cards.add(dummy);

        ArrayList<Tag> tags = new ArrayList<>();
        for(Card card:cards){
            for(Tag tag:card.categories){
                if(!tags.contains(tag)) tags.add(tag);
            }
        }

        System.out.println("REEEEEEEEEEEEEEEEEEEEEEEEEE"+tags.get(0).name+"REEEEEEEEEEEEEEEEEEEEEEEE");

        LinearLayout playLayout = findViewById(R.id.playLayout);
        for(Tag tag:tags){
            CardView card = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(700, 200);
            cardParams.gravity = Gravity.CENTER;
            card.setLayoutParams(cardParams);
            card.setCardBackgroundColor(Color.parseColor(tag.color));
            card.setRadius(30);
            playLayout.addView(card);

            ConstraintLayout subLayout = new ConstraintLayout(this);
            //subLayout.setPadding(50,50,50,50);
            CardView.LayoutParams subLayoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,CardView.LayoutParams.MATCH_PARENT);
            subLayoutParams.gravity = Gravity.CENTER;
            subLayout.setLayoutParams(subLayoutParams);
            subLayout.setId(123);
            card.addView(subLayout);

            TextView nameView = new TextView(this);
            nameView.setText(tag.name);
            nameView.setTextSize(22);
            nameView.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nameView.setId(456);
            subLayout.addView(nameView);

            TextView buffer = new TextView(this);
            buffer.setText(" ");
            buffer.setLayoutParams(new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            buffer.setId(789);
            subLayout.addView(buffer);

            ImageButton playButton = new ImageButton(this);
            playButton.setImageResource(R.drawable.play);
            playButton.setLayoutParams(new ConstraintLayout.LayoutParams(150, 150));
            playButton.setId(101112);
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
                 o: for(Card card:cards){
                        for(Tag curTag:card.categories){
                            if(curTag.name.equals(tag.name)){
                                State.playing.add(card);
                                continue o;
                            }
                        }
                    }
                    Intent intent = new Intent(MainActivity.this, PlayCard.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        loadCards();

        if(State.justLoggedIn){
            State.justLoggedIn = false;
            Snackbar.make(findViewById(R.id.playLayout), "Logged in Successfully!", Snackbar.LENGTH_LONG).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            State.justLoggedOut = true;
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_browse){
            Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_search){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}