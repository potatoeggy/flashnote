package com.example.flashnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.flashnote.data.Card;
import com.example.flashnote.data.Tag;

import com.example.flashnote.data.Card;
import com.example.flashnote.data.Tag;

public class PlayCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_card);

        final Card cur = State.playing.get(State.playingIndex);

        CardView roundCard = findViewById(R.id.roundCard);
        long avgColor = 0;
        for(Tag tag:cur.getTagList()) avgColor += Long.parseLong(tag.getColour().substring(1),16);
        avgColor /= cur.getTagList().size();
        roundCard.setCardBackgroundColor(Color.parseColor("#"+Long.toHexString(avgColor)));

        TextView categoryView = findViewById(R.id.categoryView);
        String text = "Tags: ";
        for(int i=0;i<cur.getTagList().size()-1;i++) text += cur.getTagList().get(i).getName()+",";
        text += cur.getTagList().get(cur.getTagList().size()-1).getName();
        categoryView.setText(text);

        TextView termView = findViewById(R.id.TermView);
        termView.setText(cur.getQuestion());

        final TextView defView = findViewById(R.id.defView);
        defView.setText("");

        TextView creditLine = findViewById(R.id.creditLine);
        creditLine.setText("This card was made by "+cur.getUsername()+" at "+cur.getTimestamp());

        final Button cont = findViewById(R.id.ContinueButton);
        cont.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                defView.setText(cur.getAnswer());
                State.playingIndex++;
                if(State.playingIndex >= State.playing.size()){
                    cont.setText("Finish");
                    cont.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PlayCard.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    cont.setText("Continue");
                    cont.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PlayCard.this, PlayCard.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }
}
