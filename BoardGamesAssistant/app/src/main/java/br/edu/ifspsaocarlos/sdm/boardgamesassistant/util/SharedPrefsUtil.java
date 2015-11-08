package br.edu.ifspsaocarlos.sdm.boardgamesassistant.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class util para manipular informacoes de SharedPreferences
 *
 * @author maiko.trindade
 */
public class SharedPrefsUtil {

    private static final String PREFS_NAME = "BOARDINGGAMES";

    public static SharedPreferences getPrefs(final Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(PREFS_NAME, Context
                .MODE_PRIVATE);
        return sharedpreferences;
    }

    public enum Key {

        CHESS_INITIAL_TIME("CHESS_INITIAL_TIME");

        final private String key;

        Key(String key) {
            this.key = key;
        }
    }
}
