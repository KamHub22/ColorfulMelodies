package com.example.kamil.magisterkaone.views.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class PianoButton {
    private Rect rect;

    private boolean isPressed = false;

    private final PianoButtonColor color;

    private final Context context;
    private final MediaPlayer soundPlayer;

    public PianoButton(PianoButtonColor color, Context context, int soundId) {
        this.color = color;
        this.context = context;
        soundPlayer = MediaPlayer.create(context, soundId);
    }

    public void setPositions(int positionLeft, int positionTop, int positionRight, int positionBottom) {
        rect = new Rect(positionLeft, positionTop, positionRight, positionBottom);
    }

    public void drawMeOn(Canvas canvas) {
        Bitmap bitmap = isPressed ? color.getButtonPressedBitmap(context) : color.getButtonBitmap(context);

        canvas.drawBitmap(bitmap, null, rect, color.getPaint());
    }

    public boolean onClick(int x, int y) {
        if (isInBounds(x, y)) {
            isPressed = true;
            playSound();
            return true;
        }
        return false;
    }

    public boolean isInBounds(int x, int y) {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;
    }

    private void playSound() {
        if (soundPlayer.isPlaying()) {
            soundPlayer.seekTo(0);
        }
        else {
            soundPlayer.start();
        }
    }

    public PianoButtonColor getColor() {
        return color;
    }

    public void onRelease() {
        isPressed = false;
    }
}
