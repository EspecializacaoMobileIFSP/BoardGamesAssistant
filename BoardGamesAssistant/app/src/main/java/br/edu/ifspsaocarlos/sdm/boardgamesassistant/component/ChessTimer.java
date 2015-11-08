package br.edu.ifspsaocarlos.sdm.boardgamesassistant.component;

import android.os.CountDownTimer;

/**
 * Classe responsável por adicionar a funcionalidade de 'pause' no CountDownTimer nativo
 * da Google (http://developer.android.com/intl/pt-br/reference/android/os/CountDownTimer.html)
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

    /**
     * Cria um contador do tipo nativo CountDownTimer no qual chama a ChessTimer
     */
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
     * Retorna o valor da contagem em milis
     *
     * @param millisUntilFinished
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Metodo chamado ao termino da contagem
     */
    public abstract void onFinish();

    /**
     * inicia ou despausa o contador
     *
     * @return contador regressico pausavel
     */
    public synchronized final ChessTimer start() {
        if (mIsPaused) {
            createTimer();
            mCountDownTimer.start();
            mIsPaused = false;
        }
        return this;
    }

    /**
     * pausa o contador
     */
    public void pause() {
        if (mIsPaused == false) {
            mCountDownTimer.cancel();
        }
        mIsPaused = true;
    }

    /**
     * verificar se o contador esta pausado
     *
     * @return pause do contador
     */
    public boolean isPaused() {
        return mIsPaused;
    }
}