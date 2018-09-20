package com.example.kamil.magisterkaone.views.elements;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.BitmapFactory.decodeResource;
import static com.example.kamil.magisterkaone.R.drawable.black_piano_button;
import static com.example.kamil.magisterkaone.R.drawable.black_piano_button_pressed;
import static com.example.kamil.magisterkaone.R.drawable.white_piano_button;
import static com.example.kamil.magisterkaone.R.drawable.white_piano_button_pressed;

public enum PianoButtonColor {
    BLACK(Color.BLACK, black_piano_button, black_piano_button_pressed),
    WHITE(Color.WHITE, white_piano_button, white_piano_button_pressed);

    private Paint paint;
    private int buttonDrawable;
    private int buttonPressedDrawable;
    private Bitmap buttonBitmap;
    private Bitmap buttonPressedBitmap;

    PianoButtonColor(int color, int buttonDrawable, int buttonPressedDrawable) {
        this.paint = new Paint(0);
        paint.setColor(color);
        this.buttonDrawable = buttonDrawable;
        this.buttonPressedDrawable = buttonPressedDrawable;
    }

    public Paint getPaint() {
        return paint;
    }

    public Bitmap getButtonBitmap(Context context) {
        if (buttonBitmap == null) {
            buttonBitmap = decodeResource(context.getResources(), buttonDrawable);
        }
        return buttonBitmap;
    }

    public Bitmap getButtonPressedBitmap(Context context) {
        if (buttonPressedBitmap == null) {
            buttonPressedBitmap = decodeResource(context.getResources(), buttonPressedDrawable);
        }
        return buttonPressedBitmap;
    }

}
