package com.example.kamil.magisterkaone;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by Kamil on 2017-03-24.
 */

public class Nagrywanie {
    private MediaRecorder nagrywanie = null;
    private int obsformaty[] =
            {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};

    private String rodzajPliku[] = {".mp4", ".3gpp"};
    public int wybFormat = 0;
    int i = 0;


    Odtwarzacz odtwarzacz = new Odtwarzacz();

    public void zacznijNagrywac() {
        nagrywanie = new MediaRecorder();

        nagrywanie.setAudioSource(MediaRecorder.AudioSource.MIC);
        nagrywanie.setOutputFormat(obsformaty[wybFormat]);
        nagrywanie.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        nagrywanie.setOutputFile(uzyskanaSciezka());

        try {
            nagrywanie.prepare();
            nagrywanie.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uzyskanaSciezka() {
        String sciezkaPliku = "/storage/emulated/0/";
        File plik = new File(sciezkaPliku, "Projekty");

        if (!plik.exists()) {
            plik.mkdirs();
        }


            File folder = new File("/storage/emulated/0/Projekty");
            ArrayList<File> ile = odtwarzacz.findSongs(folder);
            i = ile.size();

        return (plik.getAbsolutePath() + "/" + MainActivity.title()  + " (" + i++ + ")" + rodzajPliku[wybFormat]);
    }

    public void przestanNagrywac() {
        if (nagrywanie != null) {
            nagrywanie.stop();
            nagrywanie.reset();
            nagrywanie.release();

            nagrywanie = null;
        }
    }
}
