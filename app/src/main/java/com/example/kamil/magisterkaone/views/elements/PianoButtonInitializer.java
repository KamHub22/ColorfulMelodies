package com.example.kamil.magisterkaone.views.elements;

import android.content.Context;

public class PianoButtonInitializer {
    private PianoButtonColor color;
    private int soundId;

    private PianoButtonInitializer(PianoButtonColor color, int soundId) {
        this.color = color;
        this.soundId = soundId;
    }

    public static PianoButtonInitializer button(PianoButtonColor color, int soundId) {
        return new PianoButtonInitializer(color, soundId);
    }

    public PianoButton create(Context context) {
        return new PianoButton(color, context, soundId);
    }

}
