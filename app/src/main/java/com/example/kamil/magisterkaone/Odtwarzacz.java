package com.example.kamil.magisterkaone;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamil.magisterkaone.share.Acords;
import com.example.kamil.magisterkaone.share.Uploads;
import com.example.kamil.magisterkaone.welcome.DescriptionofSounds;

import java.io.File;
import java.util.ArrayList;

public class Odtwarzacz extends AppCompatActivity implements View.OnClickListener {
    ListView lv;
    ArrayList<File> mySongs;
    String[] items;
    TextView namesong, timeprogres;
    ImageButton btnPlay;
    int pozycja;

    Uri u;
    MediaPlayer mp;
    Thread updateSeekBar;
    SeekBar sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odtwarzacz);

        lv = (ListView) findViewById(R.id.lvPlaylist);
        namesong = (TextView) findViewById(R.id.song);
        timeprogres = (TextView) findViewById(R.id.progresstime);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        sb = (SeekBar) findViewById(R.id.seekBar);


        String path = "/storage/emulated/0/Projekty";
        File folder = new File(path);
        mySongs = findSongs(folder);
        items = new String[mySongs.size()];

        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.song_layout, R.id.textView, items);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnPlay.setEnabled(true);

                namesong.setText(items[position]);
                pozycja = position;

//                if (updateSeekBar != null) {
//                    updateSeekBar.interrupt();
//                }
                playDemo();

            }
        });

        btnPlay.setOnClickListener(this);
    }

    public void playDemo() {
        btnPlay.setImageResource(R.drawable.glyphicons_174_pause);
        if (mp != null) {
            updateSeekBar.interrupt();
            mp.stop();
            mp.release();
            updateSeekBar = null;
            mp = null;
        }

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mp.getDuration();
                int currentPosition = 0;
                sb.setMax((totalDuration));
                try {
                    while (currentPosition < totalDuration && mp != null) {
                        sleep(200);
                        if (mp.isPlaying()) {
                            currentPosition = mp.getCurrentPosition();
                            sb.setProgress(currentPosition);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        u = Uri.parse(mySongs.get(pozycja).toString());
        mp = MediaPlayer.create(getApplicationContext(), u);
        mp.start();
        sb.setMax(mp.getDuration());
        timeprogres.setText("Czas: " + sb.getProgress() + " / " + sb.getMax());
        updateSeekBar.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                timeprogres.setText("Postęp: " + progress + " / " + sb.getMax());
                if (progress > sb.getMax() - 50) {
                    btnPlay.setImageResource(R.drawable.glyphicons_175_stop);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                timeprogres.setText("Postęp: " + progress_value + " / " + sb.getMax());
                mp.seekTo(seekBar.getProgress());
            }
        });
    }

    public ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findSongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp4") || singleFile.getName().endsWith(".3gpp")) {
                    al.add(singleFile);
                }
            }

        }
        return al;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                if (mp.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.glyphicons_173_play);
                    mp.pause();
                } else if (mp.equals(null)) {
                    for (int i = 0; i < mySongs.size(); i++) {
                        toast(mySongs.get(i).getName().toString());
                    }
                } else {
                    btnPlay.setImageResource(R.drawable.glyphicons_174_pause);
                    mp.start();
                }
        }
    }

    public void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                Intent up = new Intent(this, Uploads.class);
                startActivity(up);
                break;
            case R.id.acords:
                Intent em = new Intent(this, Acords.class);
                startActivity(em);
                break;
            case R.id.share:
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Demo");
                //share.putExtra(Intent.EXTRA_TEXT, "Moje nowe demo!!");
                startActivity(Intent.createChooser(share, "Podziel Się"));
                break;
            case R.id.info_sounds:
                Intent ds = new Intent(this, DescriptionofSounds.class);
                startActivity(ds);
        }

        return super.onOptionsItemSelected(item);
    }
}

