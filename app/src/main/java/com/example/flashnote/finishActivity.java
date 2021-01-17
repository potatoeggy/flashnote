package com.example.flashnote;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.flashnote.data.Card;
import com.example.flashnote.data.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class finishActivity extends AppCompatActivity {

    void notif(String msg){
        Snackbar.make(findViewById(R.id.newtag), msg, Snackbar.LENGTH_LONG).show();
    }

    ArrayList<Tag> tags = new ArrayList<>();
    ArrayList<LinearLayout> layouts = new ArrayList<>();
    ArrayList<EditText> terms = new ArrayList<>();
    ArrayList<EditText> defs = new ArrayList<>();
    ArrayList<ArrayList<Chip>> chipList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        System.out.println("yeet?");

        System.out.println(State.newCards);

        ArrayList<Card> noNulls = new ArrayList<>();
        for(Card c:State.newCards) if(c != null) noNulls.add(c);
        State.newCards = noNulls;

        for(int i=0;i<State.newCards.size();i++){
            layouts.add(new LinearLayout(this));
            layouts.get(i).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            terms.add(new EditText(this));
            terms.get(i).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            terms.get(i).setText(State.newCards.get(i).getQuestion());
            terms.get(i).setTextSize(20);
            defs.add(new EditText(this));
            defs.get(i).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            defs.get(i).setText(State.newCards.get(i).getAnswer());
            defs.get(i).setTextSize(20);

            TextView t1 = new TextView(this);
            t1.setLayoutParams(new LinearLayout.LayoutParams(10,5));
            layouts.get(i).addView(t1);

            layouts.get(i).addView(terms.get(i));

            TextView t = new TextView(this);
            t.setLayoutParams(new LinearLayout.LayoutParams(10,5));
            layouts.get(i).addView(t);

            layouts.get(i).addView(defs.get(i));

            LinearLayout cardLayout = findViewById(R.id.layout);
            cardLayout.addView(layouts.get(i));

            chipList.add(new ArrayList<Chip>());
        }

        Button newtag = findViewById(R.id.newtag);
        EditText tagName = findViewById(R.id.tagname);
        EditText tagColor = findViewById(R.id.tagcolor);
        LinearLayout tagLayout = findViewById(R.id.tagLayout);
        newtag.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if(tagName.getText().toString().trim().length()==0) notif("No tag name specified");
                else{
                    try{
                        Color.parseColor(tagColor.getText().toString().trim());
                    } catch(Exception e){
                        notif("Invalid Color code");
                        return;
                    }

                    String color = tagColor.getText().toString().trim();
                    String name = tagName.getText().toString().trim();
                    Tag next = new Tag(name, State.user, color);
                    tags.add(next);

                    TextView t = new TextView(finishActivity.this);
                    t.setLayoutParams(new LinearLayout.LayoutParams(10,5));
                    tagLayout.addView(t);

                    CardView tag = new CardView(finishActivity.this);
                    tag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    tag.setCardBackgroundColor(Color.parseColor(next.getColour()));
                    tag.setRadius(8);
                    tag.setId(102931);
                    tagLayout.addView(tag);

                    ConstraintLayout subLayout = new ConstraintLayout(finishActivity.this);
                    subLayout.setId(1293123);
                    subLayout.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT,CardView.LayoutParams.WRAP_CONTENT));
                    tag.addView(subLayout);

                    TextView tagText = new TextView(finishActivity.this);
                    tagText.setText(next.getName());
                    tagText.setId(67123);
                    tagText.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    subLayout.addView(tagText);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(subLayout);
                    set.connect(tagText.getId(),ConstraintSet.LEFT,subLayout.getId(),ConstraintSet.LEFT,20);
                    set.connect(tagText.getId(),ConstraintSet.RIGHT,subLayout.getId(),ConstraintSet.RIGHT,20);
                    set.connect(tagText.getId(),ConstraintSet.TOP,subLayout.getId(),ConstraintSet.TOP,20);
                    set.connect(tagText.getId(),ConstraintSet.BOTTOM,subLayout.getId(),ConstraintSet.BOTTOM,20);
                    set.applyTo(subLayout);

                    for(int i=0;i<State.newCards.size();i++){
                        Chip newChip = new Chip(finishActivity.this);
                        newChip.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        newChip.setText(next.getName());
                        newChip.setCheckable(true);
                        int[][] states = {{android.R.attr.state_enabled}};
                        int[] colors = {Color.parseColor(next.getColour())};
                        ColorStateList state = new ColorStateList(states,colors);
                        newChip.setChipBackgroundColor(state);
                        layouts.get(i).addView(newChip);
                        layouts.get(i).addView(new Space(finishActivity.this));
                        chipList.get(i).add(newChip);
                    }
                }
            }
        });
        Button doneButton = findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               for(int i=0;i<State.newCards.size();i++){
                   for(int v=0;v<tags.size();v++){
                       if(chipList.get(i).get(v).isChecked()){
                           State.newCards.get(i).getTagList().add(tags.get(v));
                       }
                   }
                   State.newCards.get(i).setQuestion(terms.get(i).getText().toString());
                   State.newCards.get(i).setAnswer(defs.get(i).getText().toString());
               }
           }
        });


    }
}
