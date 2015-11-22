package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;
import br.edu.ifspsaocarlos.sdm.boardgamesassistant.entity.CardEnum;

import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.id;
import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.layout;

/**
 * @author maiko.trindade
 */
public class BlackJackActivity extends AppCompatActivity {

    private final static int WAITING_TIME_IN_SEC = 5 * 1000;
    private final static int BLACKJACK_NUMBER = 21;

    private LinearLayout mContainerBlackJack, mContainerDealer;
    private Button mBtnHit, mBtnStand;
    private TextView mTxtScoreValue, mTxtScoreLabel, mTxtDealerScore;
    private List<CardEnum> mCards;
    private int mPlayerScore;
    private int mDealerScore;
    private int mTurns;

    public static void start(Activity activity) {
        ActivityCompat.startActivity(
                activity, new Intent(activity, BlackJackActivity.class),
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_black_jack);

        //toolbar handling
        Toolbar toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindElements();
        bindListerners();
        generateRandomCardsList();
    }

    public BlackJackActivity() {
        mPlayerScore = 0;
        mDealerScore = 0;
        mTurns = 0;
    }

    private void bindElements() {
        mContainerBlackJack = (LinearLayout) findViewById(id.container_blackjack);
        mContainerDealer = (LinearLayout) findViewById(id.container_dealer);
        mTxtScoreValue = (TextView) findViewById(id.txt_score_value);
        mTxtScoreLabel = (TextView) findViewById(id.txt_score_label);
        mTxtDealerScore = (TextView) findViewById(id.txt_score_dealer_value);
        mBtnHit = (Button) findViewById(id.btn_hit);
        mBtnStand = (Button) findViewById(id.btn_stand);
    }

    private void bindListerners() {

        mBtnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get sequential cards from a randomized list of card
                final CardEnum card = mCards.get(mTurns);
                mPlayerScore += card.getValue();
                mTxtScoreValue.setText(String.valueOf(mPlayerScore));

                if (isValidGame()) {
                    mTurns++;
                    final ImageView cardImage = new ImageView(getBaseContext());
                    cardImage.setImageDrawable(getResources().getDrawable(card.getImageResId()));

                    //remove former card and add a new player's card on the screen
                    mContainerBlackJack.removeAllViews();
                    mContainerBlackJack.addView(cardImage, new LinearLayout.LayoutParams(340, 340));
                } else {
                    playerBusted();
                }

                //enable button 'stand' after the first turn
                if (mTurns > 0) {
                    mBtnStand.setEnabled(true);
                }
            }
        });

        mBtnStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simulateDealerGame();
            }
        });
    }

    private boolean isValidGame() {
        if (mPlayerScore > BLACKJACK_NUMBER) {
            return false;
        }
        return true;
    }

    private void generateRandomCardsList() {
        mCards = Arrays.asList(CardEnum.values());
        Collections.shuffle(mCards);
    }

    private void simulateDealerGame() {
        generateRandomCardsList();
        mTurns = 0;

        /**
         * while dealer is not the winner and he didn't get busted, he keeps playing!
         */
        while (mDealerScore <= mPlayerScore) {
            mTurns++;

            final CardEnum card = mCards.get(mTurns);
            mDealerScore += card.getValue();
            mTxtDealerScore.setText(getString(R.string.lbl_dealer_score) + " " + String.valueOf(mDealerScore));
            final ImageView cardImage = new ImageView(getBaseContext());
            cardImage.setImageDrawable(getResources().getDrawable(card.getImageResId()));

            //add a new dealer's card on the screen
            mContainerDealer.addView(cardImage, new LinearLayout.LayoutParams(120, 160));
        }

        defineWinner();
    }

    private void defineWinner() {
        /**
         * It will be a draw game only if the dealer and the player scores the
         * BLACKJACK_NUMBER at the same time
         */
        if (mDealerScore == mPlayerScore && mDealerScore == BLACKJACK_NUMBER) {
            drawGame();
        } else if (mDealerScore > mPlayerScore && mDealerScore <= BLACKJACK_NUMBER) {
            //the dealer wins
            playerDefeat();
        } else {
            //the dealer loses
            playerVictory();
        }
    }

    private void drawGame() {
        showResultDialog(R.string.msg_draw_game);
        restartGame();
    }

    private void playerBusted() {
        mTxtScoreLabel.setTextColor(getResources().getColor(R.color.material_red_600));
        mTxtScoreLabel.setText(R.string.lbl_busted);
        playerDefeat();
    }

    private void playerDefeat() {
        mTxtScoreValue.setTextColor(getResources().getColor(R.color.material_red_600));
        showResultDialog(R.string.msg_you_lose);
        restartGame();
    }


    private void playerVictory() {
        showResultDialog(R.string.msg_you_win);
        restartGame();
    }

    private void restartGame() {
        disableActions();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //restart activity after the 'waiting time'
                start(BlackJackActivity.this);
                finish();
            }
        }, WAITING_TIME_IN_SEC);
    }

    private void disableActions() {
        mBtnHit.setEnabled(false);
        mBtnHit.setOnClickListener(null);

        mBtnStand.setEnabled(false);
        mBtnStand.setOnClickListener(null);
    }

    private void showResultDialog(final int messageResId) {
        AlertDialog alertDialog = new AlertDialog.Builder(BlackJackActivity.this).create();
        alertDialog.setMessage(getResources().getString(messageResId));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}