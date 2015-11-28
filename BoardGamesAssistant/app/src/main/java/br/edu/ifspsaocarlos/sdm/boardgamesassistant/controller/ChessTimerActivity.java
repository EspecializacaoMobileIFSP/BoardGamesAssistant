package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;
import br.edu.ifspsaocarlos.sdm.boardgamesassistant.component.ChessTimer;
import br.edu.ifspsaocarlos.sdm.boardgamesassistant.util.SharedPrefsUtil;

/**
 * Activity responsible for provide a chess' timer
 *
 * @author maiko.trindade
 */
public class ChessTimerActivity extends AppCompatActivity {

    private TextView mTimerTextOne, mTimerTextTwo;
    private LinearLayout mContainerPlayerOne, mContainerPlayerTwo;
    private ChessTimer mTimerOne, mTimerTwo;
    private Long mInitialTime;
    private static final long DEFAULT_INITIAL_TIME = 1800000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_timer);

        bindElements();
        configureContainers();
        loadPreferences();
        configureTimers();
    }


    private void bindElements() {
        mTimerTextOne = (TextView) findViewById(R.id.timer_player_one);
        mTimerTextTwo = (TextView) findViewById(R.id.timer_player_two);
        mContainerPlayerOne = (LinearLayout) findViewById(R.id.container_player_one);
        mContainerPlayerTwo = (LinearLayout) findViewById(R.id.container_player_two);
    }

    /**
     * configure listeners that allow only one player's timer loading at the same time
     */
    private void configureContainers() {
        mContainerPlayerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handling one player at time
                if (mTimerOne.isPaused() && mTimerTwo.isPaused()) {
                    mTimerOne.start();
                } else {
                    mTimerOne.pause();
                }
            }
        });

        mContainerPlayerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handling one player at time
                if (mTimerTwo.isPaused() && mTimerOne.isPaused()) {
                    mTimerTwo.start();
                } else {
                    mTimerTwo.pause();
                }
            }
        });
    }

    /**
     * It configures the players' timers
     */
    private void configureTimers() {
        mTimerOne = new ChessTimer(mInitialTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextOne.setText(getFormattedTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                mTimerTextOne.setText(R.string.lbl_game_over);
            }
        };

        mTimerTwo = new ChessTimer(mInitialTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextTwo.setText(getFormattedTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                mTimerTextTwo.setText(R.string.lbl_game_over);
            }
        };
    }

    private String getFormattedTime(long milis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toMinutes(milis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milis) % TimeUnit.MINUTES.toSeconds(1));
    }

    /**
     * It's stops the game in case of the Activity loses the focus
     */
    @Override
    protected void onPause() {
        super.onPause();
        mTimerOne.pause();
        mTimerTwo.pause();
    }

    /**
     * Load data from SharedPreferences if it exists
     */
    private void loadPreferences() {
        final SharedPreferences preferences = SharedPrefsUtil.getPrefs(getBaseContext());
        String initialTimeChess = preferences.getString(SharedPrefsUtil.Key.CHESS_INITIAL_TIME.name(),
                null);
        if (initialTimeChess != null) {
            mInitialTime = convertToMilis(Integer.valueOf(initialTimeChess));
        } else {
            mInitialTime = DEFAULT_INITIAL_TIME;
        }
    }

    private Long convertToMilis(Integer minutes) {
        minutes *= 60 * 1000;
        return minutes.longValue();
    }
}
