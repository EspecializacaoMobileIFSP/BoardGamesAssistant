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
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;

/**
 * Class responsible for generate random numbers
 * @author: Denis Wilson de Souza Oliveira
 */
public class DiceLuckActivity extends AppCompatActivity implements Runnable {

    private static final String EXTRA_FACE = "FACE";

    private int round;
    private int face;

    private FloatingActionButton fab;
    private AppCompatTextView txtFace;

    // The new standard to start an activity with ActivityCompat
    public static void start(Activity activity, int requestCode, int value) {
        Intent starter = new Intent(activity, DiceLuckActivity.class);
        starter.putExtra(EXTRA_FACE, value);
        ActivityCompat.startActivityForResult(activity, starter, requestCode,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_luck);

        // Get a random face to initialize activity
        face = getFace();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtFace = (AppCompatTextView) findViewById(R.id.txt_face);

        // Initialize listeners
        initialize();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void finish() {
        // Set the result to other activity
        // Return the rounds number
        setResult(RESULT_OK, new Intent().putExtra("round", round));
        super.finish();
    }

    // Run the random generator face method
    @Override
    public void run() {
        genFace();
    }

    // Get the face number
    private int getFace() {
        final Intent intent = getIntent();
        int face = 0;
        if (null != intent) {
            face = intent.getIntExtra(EXTRA_FACE, -1);
        }
        return face;
    }

    // Get a random face
    private void genFace() {
        String value = "" + (new Random().nextInt(face) + 1);
        txtFace.setText(value);
    }

    // Initialize listeners
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