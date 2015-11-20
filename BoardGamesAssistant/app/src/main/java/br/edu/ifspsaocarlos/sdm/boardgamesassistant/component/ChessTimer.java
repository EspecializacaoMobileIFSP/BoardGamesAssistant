package br.edu.ifspsaocarlos.sdm.boardgamesassistant.component;

import android.os.CountDownTimer;

/**
 * Class responsible for add 'pause' feature in a native Android CountDownTimer
 * (http://developer.android.com/intl/pt-br/reference/android/os/CountDownTimer.html)
 *
 * @author maiko.trindade
 */
public abstract class ChessTimer {
    private long millisInFuture = 0;
    private long countDownInterval = 0;
    private long millisRemaining = 0;
    private boolean mIsPaused = true;
    private CountDownTimer mCountDownTimer;

    public ChessTimer(long millisInFuture, long countDownInterval) {
        this.millisInFuture = millisInFuture;
        this.millisRemaining = millisInFuture;
        this.countDownInterval = countDownInterval;
    }

    private void createTimer() {
        mCountDownTimer = new CountDownTimer(millisRemaining, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                millisRemaining = millisUntilFinished;
                ChessTimer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                ChessTimer.this.onFinish();
            }
        };
    }

    /**
     * It returns a counting value in milis
     */
    public abstract void onTick(long millisUntilFinished);

    public abstract void onFinish();

    /**
     * It starts or stops the counter
     *
     * @return timer
     */
    public synchronized final ChessTimer start() {
        if (mIsPaused) {
            createTimer();
            mCountDownTimer.start();
            mIsPaused = false;
        }
        return this;
    }

    public void pause() {
        if (mIsPaused == false) {
            mCountDownTimer.cancel();
        }
        mIsPaused = true;
    }

    public boolean isPaused() {
        return mIsPaused;
    }
}