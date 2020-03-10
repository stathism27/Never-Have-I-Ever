package com.example.neverhaveiever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Button;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ArrayList<String> cardsList;
    ArrayList<String> embArray;
    ArrayList<String> grossArray;
    ArrayList<String> dirtyArray;
    ArrayList<String> othersArray;
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        cardsList = getIntent().getStringArrayListExtra("cards");
        embArray = getIntent().getStringArrayListExtra("embArray");
        grossArray = getIntent().getStringArrayListExtra("grossArray");
        dirtyArray = getIntent().getStringArrayListExtra("dirtyArray");
        othersArray = getIntent().getStringArrayListExtra("othersArray");


        final TextView cards = findViewById(R.id.cards);
        final int[] index = {-1};
        final TextView remaining = findViewById(R.id.remaining);
        index[0] = round(cardsList,cards, index[0],true,remaining);
        final Button previous = findViewById(R.id.previous);
        previous.setVisibility(View.VISIBLE);
        previous.setBackgroundColor(android.R.color.transparent);
        final Button next = findViewById(R.id.next);
        next.setVisibility(View.VISIBLE);
        next.setBackgroundColor(android.R.color.transparent);

        //final int[] textIndex = {index[0] + 1};
        //remaining.setText(textIndex[0] +"/"+cardsList.size());
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index[0] = round(cardsList,cards, index[0],false,remaining);
                //textIndex[0] = index[0]+1;
                //remaining.setText(textIndex[0] +"/"+cardsList.size());
                //cards.setText(" PREVIOUS");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(index[0]);
                index[0] = round(cardsList,cards, index[0],true,remaining);
                System.out.println(index[0]);
                //textIndex[0] = index[0]+1;
                //remaining.setText(textIndex[0] +"/"+cardsList.size());
                //cards.setText(" NEXT");
            }
        });
    }

    public int round(ArrayList cardsList,TextView cards, int i, boolean add,TextView remaining){
        int index = i;
        int textIndex;
        String currentText;
        if(add){
            if(index+1<cardsList.size()){
                index+=1;
                currentText = cardsList.get(index).toString();
                System.out.println(currentText);
                cards.setText(currentText);
                updateBackground(currentText);
                textIndex = index+1;
                remaining.setText(textIndex +"/"+cardsList.size());
            }
        }else{
            if(index-1>=0){
                index-=1;
                currentText = cardsList.get(index).toString();
                cards.setText(currentText);
                updateBackground(currentText);
                textIndex = index+1;
                remaining.setText(textIndex +"/"+cardsList.size());
            }
        }
        return index;
    }

    public void updateBackground(String currentText){
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity2);
        if(dirtyArray.contains(currentText)){
            rl.setBackgroundColor(getResources().getColor(R.color.dirtyColor));
        }else{
            if(othersArray.contains(currentText)){
                System.out.println("The problem is here");
                rl.setBackgroundColor(getResources().getColor(R.color.othersColor));
            }else{
                if(embArray.contains(currentText)){
                    rl.setBackgroundColor(getResources().getColor(R.color.embColor));
                }else{
                    rl.setBackgroundColor(getResources().getColor(R.color.grossColor));
                }
            }
        }
    }
}
