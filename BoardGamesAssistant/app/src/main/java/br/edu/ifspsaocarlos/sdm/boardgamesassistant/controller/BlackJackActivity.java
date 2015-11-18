package br.edu.ifspsaocarlos.sdm.boardgamesassistant.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.color;
import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.id;
import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.layout;
import static br.edu.ifspsaocarlos.sdm.boardgamesassistant.R.string;

/**
 * @author maiko.trindade
 */
public class BlackJackActivity extends AppCompatActivity {

    private final static int WAITING_TIME_IN_SEC = 4 * 1000;
    private final static int BLACKJACK_NUMBER = 21;

    private LinearLayout mContainerBlackJack, mContainerDealer;
    private Button mBtnHit, mBtnStand;
    private TextView mTxtScoreValue, mTxtScoreLabel, mTxtDealerScore;
    private List<CardEnum> mCards;
    private int mPlayerScore;
    private int mDealerScore;
    private int mTurns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_black_jack);

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
                    mContainerBlackJack.removeAllViews();
                    mContainerBlackJack.addView(cardImage, new LinearLayout.LayoutParams(340, 340));
                } else {
                    playerBusted();
                }

                //enable button 'stand' after first turn
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

        //while dealer is not the winner and he didn't get busted, he keeps playing!
        while (mDealerScore <= mPlayerScore) {
            mTurns++;
            final CardEnum card = mCards.get(mTurns);
            mDealerScore += card.getValue();
            mTxtDealerScore.setText(getString(R.string.lbl_dealer_score) + " " + String.valueOf(mDealerScore));
            final ImageView cardImage = new ImageView(getBaseContext());
            cardImage.setImageDrawable(getResources().getDrawable(card.getImageResId()));
            mContainerDealer.addView(cardImage, new LinearLayout.LayoutParams(120, 160));
        }

        //Dealer will have more score than player anyway, just check if this score is still valid
        if (mDealerScore <= BLACKJACK_NUMBER) {
            playerDefeat();
        } else {
            playerVictory();
        }

    }

    private void playerBusted() {
        mTxtScoreLabel.setTextColor(getResources().getColor(color.material_red_600));
        mTxtScoreLabel.setText(string.lbl_busted);
        playerDefeat();
    }

    private void playerDefeat() {
        mTxtScoreValue.setTextColor(getResources().getColor(color.material_red_600));
        showResultDialog(string.msg_you_lose);
        restartGame();
    }


    private void playerVictory() {
        showResultDialog(string.msg_you_win);
        restartGame();
    }

    private void restartGame() {
        disableActions();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //restart activity after the 'waiting time'
                final Intent gameIntent = getIntent();
                finish();
                startActivity(gameIntent);
            }
        }, WAITING_TIME_IN_SEC);
    }

    private void disableActions() {
        mBtnHit.setEnabled(false);
        mBtnStand.setEnabled(false);
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
