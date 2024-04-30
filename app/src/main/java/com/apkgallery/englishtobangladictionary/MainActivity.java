package com.apkgallery.englishtobangladictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    DatabaseHelper dbHelper;
    HashMap<String,String>hashMap;
    ArrayList<HashMap<String,String>>arrayList;

    TextInputEditText searchView;

    private CharSequence hint = "Search Word (Ex: Brave)";
    private int index = 0;
    private long delay = 150;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= findViewById(R.id.listView);
        dbHelper= new DatabaseHelper(MainActivity.this);
        searchView= findViewById(R.id.searchView);

        loadData(dbHelper.getAllData() );

        animateTyping();



        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String key= searchView.getText().toString();
               loadData(dbHelper.searchView(key, MainActivity.this));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });








    }

    private void loadData(Cursor cursor) {
     //   Cursor cursor= dbHelper.getAllData();

        if (cursor!=null && cursor.getCount()>0){

            arrayList= new ArrayList<>();

            while (cursor.moveToNext()){

                int id= cursor.getInt(0);
                String word = cursor.getString(1);
                String meaning = cursor.getString(2);
                String partsOfSpeesh = cursor.getString(3);
                String example = cursor.getString(4);

                hashMap= new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("word",word);
                hashMap.put("meaning",meaning);
                hashMap.put("partsOfSpeesh",partsOfSpeesh);
                hashMap.put("example",example);

                arrayList.add(hashMap);



            }
            listView.setAdapter(new MyAdapter());

        }

    }

    public class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater inflater= getLayoutInflater();
            View myView= inflater.inflate(R.layout.item_view,viewGroup, false);
            TextView tvword= myView.findViewById(R.id.tvword);
            TextView tvmeaning= myView.findViewById(R.id.tvmeaning);
            TextView tvexample= myView.findViewById(R.id.tvexample);

            LinearLayout layClick= myView.findViewById(R.id.layClick);



            hashMap =arrayList.get(position);

            String word= hashMap.get("word");
            String meaning= hashMap.get("meaning");
            String partsOfSpeesh= hashMap.get("partsOfSpeesh");
            String example= hashMap.get("example");


            tvword.setText(word+" ("+partsOfSpeesh+")");
            tvmeaning.setText(meaning);
            tvexample.setText(example);






            return myView;
        }
    }




    private void animateTyping() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateHint();
                handler.postDelayed(this, hint.length() * delay); // Adjust the delay to match the length of the hint text
            }
        }, delay);
    }

    private void animateHint() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (index <= hint.length()) {
                    searchView.setHint(hint.subSequence(0, index++));
                    handler.postDelayed(this, delay);
                } else {
                    index = 0; // Reset index to restart animation
                }
            }
        });
    }




}