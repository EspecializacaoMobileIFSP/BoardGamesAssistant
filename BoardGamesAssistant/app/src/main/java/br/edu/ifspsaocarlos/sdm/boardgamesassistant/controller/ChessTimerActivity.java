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
 * Activity responsável por apresentar um contador para o jogo de Xadrez
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

    /**
     * Realiza bind dos elementos utilizados na view
     */
    private void bindElements() {
        mTimerTextOne = (TextView) findViewById(R.id.timer_player_one);
        mTimerTextTwo = (TextView) findViewById(R.id.timer_player_two);
        mContainerPlayerOne = (LinearLayout) findViewById(R.id.container_player_one);
        mContainerPlayerTwo = (LinearLayout) findViewById(R.id.container_player_two);
    }

    /**
     * Configura os eventos de click nos containers
     */
    private void configureContainers() {
        mContainerPlayerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerOne.isPaused()) {
                    mTimerOne.start();
                } else {
                    mTimerOne.pause();
                }
            }
        });

        mContainerPlayerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerTwo.isPaused()) {
                    mTimerTwo.start();
                } else {
                    mTimerTwo.pause();
                }
            }
        });
    }

    /**
     * Configura os timers dos jogadores
     */
    private void configureTimers() {
        mTimerOne = new ChessTimer(mInitialTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextOne.setText(String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1)));
            }

            @Override
            public void onFinish() {
                mTimerTextOne.setText(R.string.lbl_game_over);
            }
        };

        mTimerTwo = new ChessTimer(mInitialTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerTextTwo.setText(String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % TimeUnit.MINUTES.toSeconds(1)));
            }

            @Override
            public void onFinish() {
                mTimerTextTwo.setText(R.string.lbl_game_over);
            }
        };
    }

    /**
     * paralisa o jogo caso a Activity perca o foco
     */
    @Override
    protected void onPause() {
        super.onPause();
        mTimerOne.pause();
        mTimerTwo.pause();
    }

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
