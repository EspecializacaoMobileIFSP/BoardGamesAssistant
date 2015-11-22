package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.Random;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;

public class DiceLuckActivity extends AppCompatActivity implements Runnable {

    private static final String EXTRA_FACE = "FACE";

    private FloatingActionButton fab;
    private AppCompatTextView txtFace;
    private int round;


    private int face;

    public static void start(Activity activity, int value) {
        Intent starter = new Intent(activity, DiceLuckActivity.class);
        starter.putExtra(EXTRA_FACE, value);
        ActivityCompat.startActivity(activity, starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_luck);

        face = getFace();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtFace = (AppCompatTextView) findViewById(R.id.txt_face);

        initialize();
    }

    @Override
    public void run() {
        genFace();
    }

    private int getFace() {
        final Intent intent = getIntent();
        int face = 0;
        if (null != intent) {
            face = intent.getIntExtra(EXTRA_FACE, -1);
        }
        return face;
    }

    private void genFace() {
        String value = "" + (new Random().nextInt(face) + 1);
        txtFace.setText(value);
    }

    private void initialize() {
        round = 0;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                round += 1;
                int loop = (new Random().nextInt(face) + 1) * (new Random().nextInt(face) + 1);

                for (int i = 0; i < loop; i++) {
                    new Handler().postDelayed(DiceLuckActivity.this, i * 50);
                }
            }
        });

        if (face > 0) {
            genFace();
        } else {
            Snackbar.make(
                    findViewById(R.id.lln_root),
                    R.string.snc_try_again,
                    Snackbar.LENGTH_INDEFINITE
            ).show();
        }
    }
}