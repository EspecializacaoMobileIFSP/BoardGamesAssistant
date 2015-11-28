package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;

/**
 * Class responsible for select the dice
 * @author: Denis Wilson de Souza Oliveira
 */
public class DiceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PLAYS = 100;

    private static final int DICE_TETRAHEDRON = 0;
    private static final int DICE_CUBE = 1;
    private static final int DICE_OCCTAHEDRON = 2;
    private static final int DICE_PENTAGONAL_TRAPEZOHEDRON = 3;
    private static final int DICE_DODECAHEDRON = 4;
    private static final int DICE_ICOSAHEDRON = 5;

    private static final int DICE_FACE_TETRAHEDRON = 4;
    private static final int DICE_FACE_CUBE = 6;
    private static final int DICE_FACE_OCCTAHEDRON = 8;
    private static final int DICE_FACE_PENTAGONAL_TRAPEZOHEDRON = 10;
    private static final int DICE_FACE_DODECAHEDRON = 12;
    private static final int DICE_FACE_ICOSAHEDRON = 20;

    private FloatingActionButton fab;
    private AppCompatSpinner spnDice;
    private ImageView imgDice;

    private int face;

    public DiceActivity() {
        face = 0;
    }

    // The new standard to start an activity with ActivityCompat
    public static void start(Activity activity) {
        Intent starter = new Intent(activity, DiceActivity.class);
        ActivityCompat.startActivity(activity, starter,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        spnDice = (AppCompatSpinner) findViewById(R.id.spn_dice);
        imgDice = (ImageView) findViewById(R.id.img_dice);

        // Initialize listeners
        listeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int returnCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLAYS && returnCode == RESULT_OK) {
            int round = data.getExtras().getInt("round");
            View view = findViewById(R.id.fab);
            Snackbar.make(
                    view,
                    (round > 1)
                            ? String.format("Você jogou %d vezes", round)
                            : (round > 0)
                            ? "Você jogou  1 vez"
                            : ("Você não jogou nenhuma vez"),
                    Snackbar.LENGTH_INDEFINITE
            ).setAction("ok", null).show();
        }
    }

    // Initialize listeners
    private void listeners() {
        spnDice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case DICE_TETRAHEDRON: {
                        imgDice.setImageResource(R.drawable.tetrahedron);
                        face = DICE_FACE_TETRAHEDRON;
                        break;
                    }
                    case DICE_CUBE: {
                        imgDice.setImageResource(R.drawable.cube);
                        face = DICE_FACE_CUBE;
                        break;
                    }
                    case DICE_OCCTAHEDRON: {
                        imgDice.setImageResource(R.drawable.occtahedron);
                        face = DICE_FACE_OCCTAHEDRON;
                        break;
                    }
                    case DICE_PENTAGONAL_TRAPEZOHEDRON: {
                        imgDice.setImageResource(R.drawable.pentagonal_trapezohedron);
                        face = DICE_FACE_PENTAGONAL_TRAPEZOHEDRON;
                        break;
                    }
                    case DICE_DODECAHEDRON: {
                        imgDice.setImageResource(R.drawable.dodecahedron);
                        face = DICE_FACE_DODECAHEDRON;
                        break;
                    }
                    case DICE_ICOSAHEDRON: {
                        imgDice.setImageResource(R.drawable.icosahedron);
                        face = DICE_FACE_ICOSAHEDRON;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiceLuckActivity.start(DiceActivity.this, REQUEST_CODE_PLAYS, face);
            }
        });
    }
}
