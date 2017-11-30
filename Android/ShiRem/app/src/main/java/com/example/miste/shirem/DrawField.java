package com.example.miste.shirem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

/**
 * Created by Miste on 19.11.2017.
 */

public class DrawField extends View implements View.OnTouchListener {

    private Paint linePaint;
    private Paint colorFieldPaint;
    private float height;
    private float width;
    private float fieldwidth;
    private int numberFields = 15; // Number of field per side

    public DrawField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setOnTouchListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBase(canvas);
    }

    private void drawBase(Canvas c){

        // Inits the first values for the height,width and the width of a single field
        if(height == 0){
            height = getHeight();
            width = getWidth();
            fieldwidth = width/numberFields;
        }

        for(int i = 0; i<numberFields-1; i++){

            // Paints the top row with a color
            colorFieldPaint.setColor(Model.getInstance().getColorFieldContainer()[i]);
            c.drawRect(fieldwidth*i,0,fieldwidth*(i+1),fieldwidth,colorFieldPaint);

            // Paints the right row a color
            colorFieldPaint.setColor(Model.getInstance().getColorFieldContainer()[i+numberFields*1-1]);
            c.drawRect(width-fieldwidth, fieldwidth*i,width,fieldwidth*(i+1),colorFieldPaint);

            // Paints the bottom row a color
            colorFieldPaint.setColor(Model.getInstance().getColorFieldContainer()[i+numberFields*2-2]);
            c.drawRect(width-fieldwidth*(i+1),width-fieldwidth,width-fieldwidth*i,width,colorFieldPaint);

            // Paints the left row a color
            colorFieldPaint.setColor(Model.getInstance().getColorFieldContainer()[i+numberFields*3-3]);
            c.drawRect(0, width-fieldwidth*(i+1),fieldwidth,width-fieldwidth*i,colorFieldPaint);


            // Paints the outer lines for the grid

            c.drawRect(fieldwidth*i,0,fieldwidth*(i+1),fieldwidth,linePaint);
            c.drawRect(width-fieldwidth, fieldwidth*i,width,fieldwidth*(i+1),linePaint);
            c.drawRect(width-fieldwidth*(i+1),width-fieldwidth,width-fieldwidth*i,width,linePaint);
            c.drawRect(0, width-fieldwidth*(i+1),fieldwidth,width-fieldwidth*i,linePaint);

        }

    }


    private void init(){
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(5);


        colorFieldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        colorFieldPaint.setColor(Color.GRAY);
        colorFieldPaint.setStyle(Paint.Style.FILL);
        colorFieldPaint.setStrokeWidth(1);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int position = computerPositionOnField(event.getX(),event.getY());
        if(position > -1){
            drawFieldAtPosition(position);
        }
        return false; // hier true machen wenn ich einen drag machen will
    }

    private int computerPositionOnField(float x, float y){
        int tempX = (int) (x/fieldwidth);
        int tempY = (int) (y/fieldwidth);

        if((tempX>0&&tempX<numberFields-1)&&(numberFields-1>tempY && tempY>0)){
            Log.d("DrawField","X: " +tempX+"   Y:"+tempY);
            Log.d("DrawField","Not Valid");
            return -1;
        }else{
            if(tempY<1){
                Log.d("Drawfield","Position on field: "+tempX);
                return tempX;
            }else if(tempX==numberFields-1&& tempY>0){
                Log.d("Drawfield","Position on field: "+ (tempX+tempY));
                return tempX+tempY;
            }else if(tempY > numberFields-3&&tempX>0){
                Log.d("Drawfield","Position on field: "+ (2*numberFields-2-tempX+tempY));
                return 2*numberFields-2-tempX+tempY;
            }else{
                Log.d("Drawfield","Position on field: "+ (4*numberFields-4-tempY));
                return 4*numberFields-4-tempY;
            }
        }

    }



    public void updatePaint(int color){
        Model.getInstance().setAccDrawColor(Color.YELLOW);
    }

    private void drawFieldAtPosition(int position){
        Model.getInstance().setColorintoField(position);
        invalidate();
    }




}
