package com.example.kamil.magisterkaone;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamil.magisterkaone.share.Acords;
import com.example.kamil.magisterkaone.share.Uploads;
import com.example.kamil.magisterkaone.views.PianoView;
import com.example.kamil.magisterkaone.welcome.DescriptionofSounds;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;
import static com.example.kamil.magisterkaone.R.raw.va5;
import static com.example.kamil.magisterkaone.R.raw.vais5;
import static com.example.kamil.magisterkaone.R.raw.vb5;
import static com.example.kamil.magisterkaone.R.raw.vc5;
import static com.example.kamil.magisterkaone.R.raw.vc6;
import static com.example.kamil.magisterkaone.R.raw.vcis5;
import static com.example.kamil.magisterkaone.R.raw.vcis6;
import static com.example.kamil.magisterkaone.R.raw.vd5;
import static com.example.kamil.magisterkaone.R.raw.vd6;
import static com.example.kamil.magisterkaone.R.raw.vdis5;
import static com.example.kamil.magisterkaone.R.raw.vdis6;
import static com.example.kamil.magisterkaone.R.raw.ve5;
import static com.example.kamil.magisterkaone.R.raw.ve6;
import static com.example.kamil.magisterkaone.R.raw.vf5;
import static com.example.kamil.magisterkaone.R.raw.vf6;
import static com.example.kamil.magisterkaone.R.raw.vfis5;
import static com.example.kamil.magisterkaone.R.raw.vg5;
import static com.example.kamil.magisterkaone.R.raw.vgis5;
import static com.example.kamil.magisterkaone.views.elements.PianoButtonColor.BLACK;
import static com.example.kamil.magisterkaone.views.elements.PianoButtonColor.WHITE;
import static com.example.kamil.magisterkaone.views.elements.PianoButtonInitializer.button;

/**
 * Created by Kamil on 2017-03-21.
 */

public class ViolinActivity extends Activity implements OnClickListener{
    @BindView(R.id.piano_view)
    PianoView pianoView;
    boolean czyK = false;
    Nagrywanie nagrywanie = new Nagrywanie();

    AlertDialog ad;
    ImageView titleDialog;
    ImageView przyciskWysylania;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_piano);
        bind(this);

        oknoTytulu();
        klawiszeViolin();
        clicked();

        titlesound();

        przyciskWysylania = (ImageView) findViewById(R.id.send_button);
        przyciskWysylania.setOnClickListener(this);
    }

    public void klawiszeViolin() {
        pianoView.initializeButtons(getApplicationContext(),
                button(WHITE, vc5),
                button(BLACK, vcis5),
                button(WHITE, vd5),
                button(BLACK, vdis5),
                button(WHITE, ve5),
                button(WHITE, vf5),
                button(BLACK, vfis5),
                button(WHITE, vg5),
                button(BLACK, vgis5),
                button(WHITE, va5),
                button(BLACK, vais5),
                button(WHITE, vb5),
                button(WHITE, vc6),
                button(BLACK, vcis6),
                button(WHITE, vd6),
                button(BLACK, vdis6),
                button(WHITE, ve6),
                button(WHITE, vf6));
    }

    public void oknoTytulu() {
        // Okno tytułowe do demo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tytuł");
        builder.setIcon(R.drawable.input);
        builder.setMessage("Podaj nazwę ścieżki");

        MainActivity.input = new EditText(this);
        MainActivity.input.setText("demo");
        builder.setView(MainActivity.input);
        //Przycisk pozytywu
        builder.setPositiveButton("Zatwierdź", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ttt = MainActivity.input.getText().toString();
                Toast.makeText(getApplicationContext(), ttt, Toast.LENGTH_LONG).show();
            }
        });
        //Przycisk negatywu
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //Tworzenie oknadialogowego
        ad = builder.create();
    }

    public void titlesound() {
        titleDialog = (ImageView) findViewById(R.id.title);
        titleDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
    }

    public void clicked() {
        final ImageView formatBn = (ImageView) findViewById(R.id.formatBn);
        formatBn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        final TextView textView = (TextView) findViewById(R.id.pokaz);

        final ImageView przyciskNagrywania = (ImageView) findViewById(R.id.record_button);
        przyciskNagrywania.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!czyK) {
                    textView.setText(nagrywanie.uzyskanaSciezka());
                    czyK=true;

                    Drawable a = getResources().getDrawable(R.drawable.record2);
                    przyciskNagrywania.setBackgroundDrawable(a);
                    nagrywanie.zacznijNagrywac();

                    Log.i("Demmo app", "Start recording...");
                    Toast.makeText(ViolinActivity.this, "Włączyłeś nagrywanie!", Toast.LENGTH_LONG).show();

                    titleDialog.setEnabled(false);
                    Drawable title = getResources().getDrawable(R.drawable.input_off);
                    titleDialog.setImageDrawable(title);

                    formatBn.setEnabled(false);
                    Drawable form = getResources().getDrawable(R.drawable.format2_off);
                    formatBn.setImageDrawable(form);

                    przyciskWysylania.setEnabled(false);
                    Drawable player = getResources().getDrawable(R.drawable.player_off);
                    przyciskWysylania.setImageDrawable(player);
                }
                else {
                    textView.setText(null);
                    czyK=false;

                    Drawable a = getResources().getDrawable(R.drawable.record);
                    przyciskNagrywania.setBackgroundDrawable(a);
                    nagrywanie.przestanNagrywac();

                    Log.i("Demmo app", "Stop recording...");
                    Toast.makeText(ViolinActivity.this, "Wyłączyłeś nagrywanie!", Toast.LENGTH_LONG).show();

                    titleDialog.setEnabled(true);
                    Drawable title = getResources().getDrawable(R.drawable.input);
                    titleDialog.setImageDrawable(title);

                    formatBn.setEnabled(true);
                    Drawable form = getResources().getDrawable(R.drawable.format2);
                    formatBn.setImageDrawable(form);

                    przyciskWysylania.setEnabled(true);
                    Drawable player = getResources().getDrawable(R.drawable.player);
                    przyciskWysylania.setImageDrawable(player);
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder buduj = new AlertDialog.Builder(this);
        String formaty[] = {"MPEG4", "3GPP"};

        buduj.setTitle("Wybierz Format");
        buduj.setSingleChoiceItems(formaty, nagrywanie.wybFormat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nagrywanie.wybFormat = which;
            }
        });
        return buduj.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                Intent i = new Intent(this, Odtwarzacz.class);
                startActivity(i);
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
