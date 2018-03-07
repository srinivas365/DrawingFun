package com.example.srinivas.drawingfun;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private ImageButton currPaint;
    private DrawingView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = (DrawingView)findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
        currPaint = (ImageButton)paintLayout.getChildAt(0);

    }

    public void paintClicked(View view){
        String color = view.getTag().toString();
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            color = view.getTag().toString();
            drawView.setColor(color);
        }
        ImageView i=(ImageView)findViewById(R.id.colorpicked);
        int pc = Color.parseColor(color);
        i.setBackgroundColor(pc);
    }

    public void save_file(View view) {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //save drawing
                drawView.setDrawingCacheEnabled(true);
                String imgSaved = MediaStore.Images.Media.insertImage(
                        getContentResolver(), drawView.getDrawingCache(),
                        UUID.randomUUID().toString()+".png", "drawing");
                //Toast.makeText(getApplicationContext(),"hey u clicked yes",Toast.LENGTH_LONG).show();
                if(imgSaved!=null){
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                    savedToast.show();
                }
                else{
                    Toast unsavedToast = Toast.makeText(getApplicationContext(),
                            "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                    unsavedToast.show();
                }
                drawView.destroyDrawingCache();
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //Toast.makeText(getApplicationContext(),"hey u clicked No",Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public void new_draw(View view) {
        AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
        newDialog.setTitle("New drawing");
        newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
        newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                drawView.startNew();
                dialog.dismiss();
            }
        });
        newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        newDialog.show();
    }

    public void bursh_choose(View view) {
        final Dialog brushDialog = new Dialog(this);
        brushDialog.setContentView(R.layout.burshchooser);
        brushDialog.show();

    }
}
