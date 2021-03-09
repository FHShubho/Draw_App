package com.netlify.fhshubho.draw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private PaintAll painting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        painting = new PaintAll(this);
        painting.setBackgroundColor(Color.WHITE);
        setContentView(painting);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sub_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.subMenu0:
//                painting.freeHandDraw();
//
//            case R.id.subMenu1:
//                painting.lineDraw();
//
//            case R.id.subMenu2:
//                painting.rectangleDraw();
//
//            case R.id.subMenu5:
//                painting.clearCanvas();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void drawFreeHand(MenuItem menu) {
        painting.freeHandDraw();
    }

    public void drawLines(MenuItem menu) {
        painting.lineDraw();
    }

    public void drawRectangle(MenuItem menu) {
        painting.rectangleDraw();
    }

    public void drawCircle(MenuItem menu) {
        painting.circleDraw();
    }

    public void drawTriangle(MenuItem menu) {
        painting.triangleDraw();
    }

    public void clearAll(MenuItem menu) {
        painting.clearCanvas();
    }
}