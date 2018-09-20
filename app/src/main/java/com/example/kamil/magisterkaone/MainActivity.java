package com.example.kamil.magisterkaone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamil.magisterkaone.share.Acords;
import com.example.kamil.magisterkaone.share.Uploads;
import com.example.kamil.magisterkaone.welcome.DescriptionofSounds;
import com.example.kamil.magisterkaone.welcome.Informacje;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    String wybraneBrzmienie = "";
    View przyciskTworz;
    Spinner listaBrzmien;

    static EditText input ;
    static String demo_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaBrzmien = (Spinner) findViewById(R.id.brzmienia);
        listaBrzmien.setPrompt("Wybierz dźwięk!");

        final View przyciskKontynuacja = findViewById(R.id.przycisk_kontynuacja);
        przyciskKontynuacja.setOnClickListener(this);

        przyciskTworz = findViewById(R.id.przycisk_tworz);
        przyciskTworz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wybraneBrzmienie.equals("Piano")) {
                    startActivity(new Intent(getApplicationContext(), PianoActivity.class));
                    przyciskTworz.setEnabled(false);
                } else if (wybraneBrzmienie.equals("Guitar")) {
                    startActivity(new Intent(getApplicationContext(), GuitarActivity.class));
                    przyciskTworz.setEnabled(false);
                } else if (wybraneBrzmienie.equals("Human Voices")) {
                    startActivity(new Intent(getApplicationContext(), VoicesActivity.class));
                    przyciskTworz.setEnabled(false);
                } else if (wybraneBrzmienie.equals("Violin")) {
                    startActivity(new Intent(getApplicationContext(), ViolinActivity.class));
                    przyciskTworz.setEnabled(false);
                } else
                    Toast.makeText(MainActivity.this, "Brak wybranych dźwięków!", Toast.LENGTH_LONG).show();
                    przyciskTworz.setEnabled(false);
            }
        });

        View przyciskInformacje = findViewById(R.id.przycisk_informacje);
        przyciskInformacje.setOnClickListener(this);

        View przyciskWyjscie = findViewById(R.id.przycisk_wyjscie);
        przyciskWyjscie.setOnClickListener(this);
    }

    public static String title() {
        demo_title = input.getText().toString();
        return demo_title;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.przycisk_informacje:
                Intent b = new Intent(this, Informacje.class);
                startActivity(b);
                break;
            case R.id.przycisk_kontynuacja:
                wybraneBrzmienie = String.valueOf(listaBrzmien.getSelectedItem());
                if (wybraneBrzmienie.equals("Wybierz Dźwięk ↓")) {
                    Toast.makeText(MainActivity.this, "Brak wybranych dźwięków!", Toast.LENGTH_LONG).show();
                    break;
                }
                przyciskTworz.setEnabled(true);
                Toast.makeText(MainActivity.this, "Załadowano dźwięki: " + wybraneBrzmienie, Toast.LENGTH_LONG).show();
                break;
            case R.id.przycisk_wyjscie:
                finish();
                break;
        }
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

