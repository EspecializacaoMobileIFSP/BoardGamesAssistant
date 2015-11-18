package br.edu.ifspsaocarlos.sdm.boardgamesassistant.entity;

import br.edu.ifspsaocarlos.sdm.boardgamesassistant.R;

/**
 * @author maiko.trindade
 */
public enum CardEnum {

    CARD_A(1, R.drawable.card_a),
    CARD_2(2, R.drawable.card_2),
    CARD_3(3, R.drawable.card_3),
    CARD_4(4, R.drawable.card_4),
    CARD_5(5, R.drawable.card_5),
    CARD_6(6, R.drawable.card_6),
    CARD_7(7, R.drawable.card_7),
    CARD_8(8, R.drawable.card_8),
    CARD_9(9, R.drawable.card_9),
    CARD_10(10, R.drawable.card_10),
    CARD_J(10, R.drawable.card_j),
    CARD_Q(10, R.drawable.card_q),
    CARD_K(10, R.drawable.card_k);

    private int mValue;
    private int mImageResId;

    CardEnum(int value, int imageResId) {
        mValue = value;
        mImageResId = imageResId;
    }

    public int getImageResId() {
        return mImageResId;
    }

    public int getValue() {
        return mValue;
    }
}
