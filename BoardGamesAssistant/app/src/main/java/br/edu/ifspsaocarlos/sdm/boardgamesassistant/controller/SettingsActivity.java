package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;
import br.edu.ifspsaocarlos.sdm.boardgamesassistant.util.SharedPrefsUtil;

/**
 * Activity responsible for customize game's options
 *
 * @author maiko.trindade
 */
public class SettingsActivity extends AppCompatActivity {

    //approximately one day
    private final static int MAX_NUMBER_MINUTES = 1439;

    private SharedPreferences mSharedPrefs;
    private SeekBar mSeekInitialChess;
    private TextView mSeekValueTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        bindElements();
        configureSeekBar();
        loadData();
    }


    private void bindElements() {
        mSharedPrefs = SharedPrefsUtil.getPrefs(getBaseContext());
        mSeekValueTxt = (TextView) findViewById(R.id.txt_seek_value);
        mSeekInitialChess = (SeekBar) findViewById(R.id.seek_initial_chess);
    }

    private void configureSeekBar() {
        mSeekInitialChess.setMax(MAX_NUMBER_MINUTES);
        mSeekInitialChess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSeekValueTxt.setText(progress + "minutes");
            }
        });
    }

    private void savePrefs() {
        final SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(SharedPrefsUtil.Key.CHESS_INITIAL_TIME.name(),
                String.valueOf(mSeekInitialChess.getProgress()));
        editor.commit();

        Toast.makeText(getBaseContext(), R.string.msg_settings_successful, Toast.LENGTH_SHORT)
                .show();
    }

    private void loadData() {
        final String initialTimeChess = mSharedPrefs.getString(SharedPrefsUtil.Key.CHESS_INITIAL_TIME.name(),
                null);
        if (initialTimeChess != null) {
            final int progress = Integer.valueOf(initialTimeChess);
            mSeekInitialChess.setProgress(progress);
            mSeekValueTxt.setText(progress + " minutes");
        }
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
}
