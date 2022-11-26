package com.example.cw23;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ImageView img;
    Button Back, Next, Add;
    EditText Link;

    ArrayList<String> link;
    myDatabaseHelper myDatabaseHelper;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);
        Back = findViewById(R.id.Back);
        Next = findViewById(R.id.Next);
        Add = findViewById(R.id.button);
        Link = findViewById(R.id.Link);
        myDatabaseHelper = new myDatabaseHelper(MainActivity.this);
        link = new ArrayList<String>();
        getAllLink();
        link.add("https://i.natgeofe.com/n/4f5aaece-3300-41a4-b2a8-ed2708a0a27c/domestic-dog_thumb_4x3.jpg");
//        link.add("https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/322868_1100-800x825.jpg");
//        link.add("https://post.healthline.com/wp-content/uploads/2020/08/3180-Pug_green_grass-732x549-thumbnail-732x549.jpg");
//        link.add("https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2017/11/12153852/American-Eskimo-Dog-standing-in-the-grass-in-bright-sunlight-400x267.jpg");

        Glide.with(MainActivity.this)
                .load(link.get(i))
                .centerCrop()
                .into(img);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i >=link.size())
                    i = 0;
                Glide.with(MainActivity.this)
                        .load(link.get(i))
                        .centerCrop()
                        .into(img);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i <0)
                    i = link.size() -1;
                Glide.with(MainActivity.this)
                        .load(link.get(i))
                        .centerCrop()
                        .into(img);
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                URLConnection connection = null;
                try {
                    connection = new URL(Link.getText().toString()).openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String contentType = connection.getHeaderField("Content-Type");
                boolean image = contentType.startsWith("image/");
                if (image == true){
                    int oldSize = link.size();
                    myDatabaseHelper.addLink(Link.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Not Link Format", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public boolean test(String a){

        if (a == "b") return true;
        return false;
    }
    public boolean exists(String URLName) {
        boolean result = false;
        try {
            InputStream input = (new URL(URLName)).openStream();
            result = true;
        } catch (IOException ex) {
            System.out.println("Image doesnot exits :");
        }
        return result;
    }
    void getAllLink(){
        Cursor cursor = myDatabaseHelper.getLink();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Img in data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                link.add(cursor.getString(1));
            }
        }
    }


    private boolean isValidURLLink(String toString) {
        try {
            URL url = new URL(toString);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    };
}