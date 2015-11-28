package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;

/**
 * Activity presents info about the app
 *
 * @author maiko.trindade
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
