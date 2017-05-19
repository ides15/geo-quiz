package org.johnide.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private TextView mAPILevelTextView;

    private static final String EXTRA_ANSWER_IS_TRUE =
            "org.johnide.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "org.johnide.geoquiz.answer_shown";
    private static final int API_LEVEL = Build.VERSION.SDK_INT;

    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_CLICKED = "clicked";

    private boolean mAnswerIsTrue;
    private boolean mIsCheater;
    private boolean mWasClicked;

    private static final int trueText = R.string.true_button;
    private static final int falseText = R.string.false_button;

    public static Intent newIntent(Context packageContext, boolean mAnswerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, mAnswerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mAPILevelTextView = (TextView) findViewById(R.id.api_level_text_view);
        String apiLevelString = getString(R.string.api_level, API_LEVEL);
        mAPILevelTextView.setText(apiLevelString);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerShownResult(true);
                mIsCheater = true;
                mWasClicked = true;

                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(trueText);
                } else {
                    mAnswerTextView.setText(falseText);
                }

                int cx = mShowAnswer.getWidth() / 2;
                int cy = mShowAnswer.getHeight() / 2;
                float radius = mShowAnswer.getWidth();
                Animator anim = ViewAnimationUtils
                        .createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mShowAnswer.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            }
        });

        if (savedInstanceState != null) {
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            setAnswerShownResult(mIsCheater);

            mWasClicked = savedInstanceState.getBoolean(KEY_CLICKED, false);
            if (mWasClicked) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(trueText);
                } else {
                    mAnswerTextView.setText(falseText);
                }
            }
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CHEATER, mIsCheater);
        outState.putBoolean(KEY_CLICKED, mWasClicked);
    }
}
