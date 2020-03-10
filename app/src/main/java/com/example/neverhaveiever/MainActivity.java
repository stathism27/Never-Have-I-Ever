package com.example.neverhaveiever;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.content.res.*;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class MainActivity extends AppCompatActivity {

    private int noCards = 0;
    private String[] languages;
    ImageButton changeLang = null;
    ImageButton buttonDirty;
    ImageButton buttonOthers;
    ImageButton buttonGross;
    ImageButton buttonEmbarrased;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_main);

        changeLang = findViewById(R.id.changeLang);
        String locale = getSharedPreferences("Settings",MODE_PRIVATE).getString("My Lang","en");
        updateButtonBack(locale);

        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog(changeLang);

            }
        });

        languages = getResources().getStringArray(R.array.languages);



        buttonDirty = findViewById(R.id.buttonDirty);
        buttonGross = findViewById(R.id.buttonGross);
        buttonEmbarrased = findViewById(R.id.buttonEmbarrased);
        buttonOthers = findViewById(R.id.buttonOthers);
        final Button startGame = findViewById(R.id.startGame);
        final String embArray[] = getResources().getStringArray(R.array.embarassing);
        final String dirtyArray[] = getResources().getStringArray(R.array.dirty);
        final String othersArray[] = getResources().getStringArray(R.array.others);
        final String grossArray[] = getResources().getStringArray(R.array.gross);
        final TextView grossText = findViewById(R.id.grossText);
        final TextView embText = findViewById(R.id.embText);
        final TextView othersText = findViewById(R.id.othersText);
        final TextView dirtyText = findViewById(R.id.dirtyText);

        noCards = embArray.length + dirtyArray.length + othersArray.length + grossArray.length;

        final TextView cards = findViewById(R.id.cards);

        cards.setText(getResources().getString(R.string.number_of_cards)+": "+noCards);




        buttonDirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonDirty.isSelected()){
                    buttonDirty.setBackgroundResource(R.drawable.dirty1);
                    buttonDirty.setSelected(false);
                    updateCards(cards,dirtyArray,true);
                    dirtyText.setTextColor(getResources().getColor(R.color.dirtyColor));
                }
                else{
                    buttonDirty.setBackgroundResource(R.drawable.dirty2);
                    buttonDirty.setSelected(true);
                    updateCards(cards,dirtyArray,false);
                    dirtyText.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });

        buttonGross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonGross.isSelected()){
                    buttonGross.setBackgroundResource(R.drawable.gross1);
                    buttonGross.setSelected(false);
                    updateCards(cards,grossArray,true);
                    grossText.setTextColor(getResources().getColor(R.color.grossColor));
                }
                else{
                    buttonGross.setBackgroundResource(R.drawable.gross2);
                    buttonGross.setSelected(true);
                    updateCards(cards,grossArray,false);
                    grossText.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });

        buttonEmbarrased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonEmbarrased.isSelected()){
                    buttonEmbarrased.setBackgroundResource(R.drawable.embarrased1);
                    buttonEmbarrased.setSelected(false);
                    updateCards(cards,embArray,true);
                    embText.setTextColor(getResources().getColor(R.color.embColor));
                }
                else{
                    buttonEmbarrased.setBackgroundResource(R.drawable.embarrased2);
                    buttonEmbarrased.setSelected(true);
                    updateCards(cards,embArray,false);
                    embText.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });

        buttonOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonOthers.isSelected()){
                    buttonOthers.setBackgroundResource(R.drawable.others1);
                    buttonOthers.setSelected(false);
                    updateCards(cards,othersArray,true);
                    othersText.setTextColor(getResources().getColor(R.color.othersColor));
                }
                else{
                    buttonOthers.setBackgroundResource(R.drawable.others2);
                    buttonOthers.setSelected(true);
                    updateCards(cards,othersArray,false);
                    othersText.setTextColor(getResources().getColor(R.color.grey));
                }
            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noCards == 0){
                    Toast.makeText(getApplicationContext(),"Select a category",Toast.LENGTH_LONG).show();

                }else{
                    HashSet<String> set = new HashSet<>();
                    if(!buttonDirty.isSelected()){
                        set.addAll(Arrays.asList(dirtyArray));
                    }
                    if(!buttonEmbarrased.isSelected()){
                        set.addAll(Arrays.asList(embArray));
                    }
                    if(!buttonGross.isSelected()){
                        set.addAll(Arrays.asList(grossArray));
                    }
                    if(!buttonOthers.isSelected()){
                        set.addAll(Arrays.asList(othersArray));
                    }
                    ArrayList<String> mainList = new ArrayList<String>();
                    mainList.addAll(set);
                    java.util.Collections.shuffle(mainList);
                    //System.out.println(mainList);
                    openActivity2(mainList,embArray,grossArray,dirtyArray,othersArray);
                }
            }
        });



    }

    void updateCards(TextView textView,String[] arr, boolean add){
        if(add){
            noCards = noCards + arr.length;
        }else{
            noCards = noCards - arr.length;
        }

        textView.setText(getResources().getString(R.string.number_of_cards)+": "+noCards);
    }

    public void openActivity2(ArrayList mainlist,String[] embArray,String[] grossArray,String[] dirtyArray,String[] othersArray){
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putStringArrayListExtra("cards", mainlist);
        List<String> e = new ArrayList<String>(Arrays.asList(embArray));
        List<String> g = new ArrayList<String>(Arrays.asList(grossArray));
        List<String> d = new ArrayList<String>(Arrays.asList(dirtyArray));
        List<String> o = new ArrayList<String>(Arrays.asList(othersArray));
        intent.putStringArrayListExtra("embArray", (ArrayList<String>) e);
        intent.putStringArrayListExtra("grossArray", (ArrayList<String>) g);
        intent.putStringArrayListExtra("dirtyArray", (ArrayList<String>) d);
        intent.putStringArrayListExtra("othersArray", (ArrayList<String>) o);
        startActivity(intent);
    }

    /*
    private void setAppLocale(String localeCode){
        Resources res = getResources();
        DisplayMetrics dn = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        }else{
            conf.locale = new Locale(localeCode.toLowerCase());

        }
        res.updateConfiguration(conf,dn);

    }
    */

    private void setAppLocale(String language){
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My Lang",language);
        editor.apply();

    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String lang = prefs.getString("My Lang","");
        setAppLocale(lang);


    }

    private void showChangeLanguageDialog(ImageButton imgButton){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(getResources().getString(R.string.choose));
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(languages[which].equals(getResources().getString(R.string.cancel))){
                    dialog.dismiss();
                }else{
                    String localeName = "";
                    switch (which){
                        case 0:
                            localeName = "en";
                            break;
                        case 1:
                            localeName = "gr";
                            break;
                    }
                    updateButtonBack(localeName);
                    setAppLocale(localeName);

                    recreate();
                    dialog.dismiss();
                }

            }

        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private Drawable getDrawableImage(String name){
        Resources resources = this.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                this.getPackageName());
        return resources.getDrawable(resourceId);
    }

    private void updateButtonBack(String localeName){
        changeLang.setImageDrawable(getDrawableImage(localeName));
    }


}
