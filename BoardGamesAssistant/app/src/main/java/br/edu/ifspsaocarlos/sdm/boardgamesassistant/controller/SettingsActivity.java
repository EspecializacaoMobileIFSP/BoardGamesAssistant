package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;
import br.edu.ifspsaocarlos.sdm.boardgamesassistant.util.SharedPrefsUtil;

/**
 * Activity responsável por configurar opções do jogo
 *
 * @author maiko.trindade
 */
public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mSharedPrefs;
    private EditText mEdtInitialChess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bindElements();
        loadData();
    }

    private void bindElements() {
        mSharedPrefs = SharedPrefsUtil.getPrefs(getBaseContext());
        mEdtInitialChess = (EditText) findViewById(R.id.edt_initial_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_settings_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSave:
                savePrefs();
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePrefs() {
        final SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(SharedPrefsUtil.Key.CHESS_INITIAL_TIME.name(), mEdtInitialChess.getText()
                .toString());
        editor.commit();

        Toast.makeText(getBaseContext(), R.string.msg_settings_successful, Toast.LENGTH_SHORT)
                .show();
    }

    private void loadData() {
        String initialTimeChess = mSharedPrefs.getString(SharedPrefsUtil.Key.CHESS_INITIAL_TIME.name(), null);
        if (initialTimeChess != null) {
            mEdtInitialChess.setText(initialTimeChess);
        }
    }
}
