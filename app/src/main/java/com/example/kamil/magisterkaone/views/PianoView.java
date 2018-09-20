package com.example.kamil.magisterkaone.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.example.kamil.magisterkaone.views.elements.PianoButton;
import com.example.kamil.magisterkaone.views.elements.PianoButtonInitializer;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.MotionEventCompat.getActionIndex;
import static android.support.v4.view.MotionEventCompat.getActionMasked;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;
import static com.example.kamil.magisterkaone.views.elements.PianoButtonColor.WHITE;
import static java.lang.Math.abs;

public class PianoView extends View {

    private static final String TAG = PianoView.class.getSimpleName();

    private int width, height;
    private List<PianoButton> buttons = new ArrayList<>();
    private List<PianoButton> whiteButtons = new ArrayList<>();
    private List<PianoButton> blackButtons = new ArrayList<>();
    private List<PianoButton> pressedButtons = new ArrayList<>();

    private int buttonWidth;
    private int oldX;

    public PianoView(Context context) {
        super(context);
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public final void initializeButtons(final Context context, PianoButtonInitializer... initializers) {
        for (PianoButtonInitializer initializer : initializers) {
            PianoButton button = initializer.create(context);
            buttons.add(button);
            if (button.getColor() == WHITE)
                whiteButtons.add(button);
            else {
                blackButtons.add(button);
            }
        }
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        Log.d(TAG, "Size changed " + width + " " + height + " " + oldWidth + " " + oldHeight);
        this.width = width;
        this.height = height;
        this.buttonWidth = width / buttons.size();
        repositionButtons();
        super.onSizeChanged(width, height, oldWidth, oldHeight);
    }

    private void repositionButtons() {
        int whiteButtonWidth = width / whiteButtons.size();
        int blackButtonHalfWidth = whiteButtonWidth / 3;
        int currentPosition = 0;
        for (PianoButton button : buttons) {
            if (button.getColor() == WHITE) {
                button.setPositions(currentPosition, 0, currentPosition + whiteButtonWidth, height);
                currentPosition += whiteButtonWidth;
            }
            else {
                button.setPositions(currentPosition - blackButtonHalfWidth, 0, currentPosition + blackButtonHalfWidth, height / 2);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = getActionMasked(event);
        int index = getActionIndex(event);

        int x = (int) MotionEventCompat.getX(event, index);
        int y = (int) MotionEventCompat.getY(event, index);

        if (action == ACTION_DOWN || action == ACTION_POINTER_DOWN || action == ACTION_MOVE) {
            if (action == ACTION_MOVE) {
                int oldX = this.oldX;
                this.oldX = x;
                int verticalMoveOffset = abs(x - oldX);
                int minimalMoveOffset = buttonWidth;
                int maximalMoveOffset = buttonWidth * 2;
                if (verticalMoveOffset < minimalMoveOffset || verticalMoveOffset > maximalMoveOffset) {
                    return true;
                }
            }

            oldX = x;

            for (PianoButton button : blackButtons) {
                if (button.onClick(x, y)) {
                    pressedButtons.add(button);
                    invalidate();
                    return true;
                }
            }
            for (PianoButton button : whiteButtons) {
                if (button.onClick(x, y)) {
                    pressedButtons.add(button);
                    invalidate();
                    return true;
                }
            }
        }
        else if (action == ACTION_UP || action == ACTION_POINTER_UP) {
            for (PianoButton button : pressedButtons) {
                button.onRelease();

            }
            invalidate();
            pressedButtons.clear();
        }

        return true;
    }

    @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for (PianoButton button : whiteButtons) {
                button.drawMeOn(canvas);
            }
            for (PianoButton button : blackButtons) {
                button.drawMeOn(canvas);
            }

    }

}
