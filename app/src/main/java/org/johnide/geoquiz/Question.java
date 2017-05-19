package org.johnide.geoquiz;

/**
 * Created by john on 2/6/17.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mCheated;

    public Question (int mTextResId, boolean mAnswerTrue, boolean mCheated) {
        this.mAnswerTrue = mAnswerTrue;
        this.mTextResId = mTextResId;
        this.mCheated = mCheated;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isCheated() {
        return mCheated;
    }

    public void setCheated(boolean cheated) {
        mCheated = cheated;
    }
}
