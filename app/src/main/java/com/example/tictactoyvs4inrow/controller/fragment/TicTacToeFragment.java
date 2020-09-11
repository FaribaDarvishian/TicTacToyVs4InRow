package com.example.tictactoyvs4inrow.controller.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tictactoyvs4inrow.Player;
import com.example.tictactoyvs4inrow.R;
import com.example.tictactoyvs4inrow.repository.SettingRepository;
import com.example.tictactoyvs4inrow.repository.TicTacToeRepository;
import com.google.android.material.snackbar.Snackbar;



public class TicTacToeFragment extends Fragment implements OnClickListener {

    private Button mButton00, mButton01, mButton02, mButton10, mButton11, mButton12, mButton20, mButton21, mButton22, mButtonReset;
    private Button[][] mButtons;
    private RelativeLayout mRelativeLayoutGamerName;
    private TextView mTextViewGamer1, mTextViewGamer2;
    private TicTacToeRepository mTicTacToeRepository;
    private SettingRepository mSettingsRepository;

    public static TicTacToeFragment newInstance() {

        TicTacToeRepository.getInstance().resetGame();
        Bundle args = new Bundle();

        TicTacToeFragment fragment = new TicTacToeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettingsRepository = SettingRepository.getInstance();
        mTicTacToeRepository = TicTacToeRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);

        findViews(view);
        initialization();
        setGamerName();
        configurationChange(savedInstanceState);

        return view;
    }

    private void findViews(View view){
        mButton00 = view.findViewById(R.id.btn_00);
        mButton01 = view.findViewById(R.id.btn_01);
        mButton02 = view.findViewById(R.id.btn_02);
        mButton10 = view.findViewById(R.id.btn_10);
        mButton11 = view.findViewById(R.id.btn_11);
        mButton12 = view.findViewById(R.id.btn_12);
        mButton20 = view.findViewById(R.id.btn_20);
        mButton21 = view.findViewById(R.id.btn_21);
        mButton22 = view.findViewById(R.id.btn_22);
        mButtonReset = view.findViewById(R.id.reset_tic_tac);
        mRelativeLayoutGamerName = view.findViewById(R.id.tic_tac_toe_gamer_layout);
        mTextViewGamer1 = view.findViewById(R.id.tic_gamer1);
        mTextViewGamer2 = view.findViewById(R.id.tic_gamer2);
    }

    private void initialization(){

        mButton00.setOnClickListener(this);
        mButton01.setOnClickListener(this);
        mButton02.setOnClickListener(this);
        mButton10.setOnClickListener(this);
        mButton11.setOnClickListener(this);
        mButton12.setOnClickListener(this);
        mButton20.setOnClickListener(this);
        mButton21.setOnClickListener(this);
        mButton22.setOnClickListener(this);
        mButtonReset.setOnClickListener(this);
        mButtons = new Button[][]{{mButton00, mButton01, mButton02},
                                  {mButton10, mButton11, mButton12},
                                  {mButton20, mButton21, mButton22}};

    }

    private void configurationChange(Bundle savedInstanceState){

        if (savedInstanceState != null){

            for (int i = 0; i < mButtons.length; i++) {
                for (int j = 0; j < mButtons[i].length; j++) {
                    if (mTicTacToeRepository.getElements()[i][j] != Player.E)
                        mButtons[i][j].setText(mTicTacToeRepository.getElements()[i][j].toString());
                    mButtons[i][j].setAlpha(mTicTacToeRepository.getFloatButtonsAlpha());
                    mButtons[i][j].setClickable(mTicTacToeRepository.getBooleanAllButtonsClickable()[i][j]);
                    mButtonReset.setVisibility(mTicTacToeRepository.getViewButtonVisibility());
                }
            }

        }
    }

    @Override
    public void onClick(View v) {


        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons[i].length; j++) {

                if (v == mButtons[i][j]) {
                    mButtons[i][j].setClickable(false);
                    mTicTacToeRepository.setBooleanButtonClickable(i, j, mButtons[i][j].isClickable());
                    setButtonsText(mButtons[i][j]);
                    mTicTacToeRepository.startGame(i, j);

                    isGameOver(v);
                }
            }
        }

        if (v == mButtonReset){
            mTicTacToeRepository.setFloatButtonsAlpha(1);
            for (int i = 0; i < mButtons.length; i++) {
                for (int j = 0; j < mButtons[i].length; j++) {
                    mButtons[i][j].setClickable(true);

                    mButtons[i][j].setAlpha(mTicTacToeRepository.getFloatButtonsAlpha());
                    mTicTacToeRepository.setBooleanButtonClickable(i, j, mButtons[i][j].isClickable());
                    mButtons[i][j].setText("");
                }
            }
            mTextViewGamer1.setTextColor(Color.BLUE);
            mTextViewGamer2.setTextColor(Color.BLACK);

            mTicTacToeRepository.setViewButtonVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(mTicTacToeRepository.getViewButtonVisibility());
            mTicTacToeRepository.resetGame();
        }

    }

    private void setButtonsText(Button button){
        if (mTicTacToeRepository.getElement().equals(Player.X)) {
            button.setText("X");
            mTextViewGamer2.setTextColor(Color.RED);
            mTextViewGamer1.setTextColor(Color.BLACK);

        }
        else if (mTicTacToeRepository.getElement().equals(Player.O)) {
            button.setText("O");
            mTextViewGamer1.setTextColor(Color.BLUE);
            mTextViewGamer2.setTextColor(Color.BLACK);

        }
    }

    private void isGameOver(View view){
        if (mTicTacToeRepository.isGameOver()){
            String winner = mTicTacToeRepository.getMessage();

            if (mSettingsRepository.getGamer1().length() > 0 && mSettingsRepository.getGamer2().length() > 0) {


                if (winner.contains("X")) {
                    winner = mSettingsRepository.getGamer1() + " wins!";
                    mSettingsRepository.setScoreGamer1(mSettingsRepository.getScoreGamer1() + 1);
                }
                else if (winner.contains("O")) {
                    winner = mSettingsRepository.getGamer2() + " wins!";
                    mSettingsRepository.setScoreGamer2(mSettingsRepository.getScoreGamer2() + 1);
                }
                mTextViewGamer1.setText("Gamer 1 : " + mSettingsRepository.getGamer1() + "\nScore : " + mSettingsRepository.getScoreGamer1());
                mTextViewGamer2.setText("Gamer 2 : " + mSettingsRepository.getGamer2() + "\nScore : " + mSettingsRepository.getScoreGamer2());
            }
            Snackbar.make(view, winner, Snackbar.LENGTH_LONG).show();

            setAllButtonClickable();
            mTicTacToeRepository.setViewButtonVisibility(View.VISIBLE);
            mButtonReset.setVisibility(mTicTacToeRepository.getViewButtonVisibility());
        }
    }

    private void setAllButtonClickable(){
        mTicTacToeRepository.setFloatButtonsAlpha(0.3f);
        for (int i = 0; i < mButtons.length; i++) {
            for (int j = 0; j < mButtons[i].length; j++) {
                mButtons[i][j].setClickable(false);
                mButtons[i][j].setAlpha(mTicTacToeRepository.getFloatButtonsAlpha());
                mTicTacToeRepository.setBooleanButtonClickable(i, j, false);
            }
        }


    }

    private void setGamerName() {
        if (mSettingsRepository.getGamer1().length() > 0 && mSettingsRepository.getGamer2().length() > 0) {
            mRelativeLayoutGamerName.setVisibility(View.VISIBLE);
            mTextViewGamer1.setTextColor(Color.BLUE);
            mTextViewGamer1.setText("Gamer 1 : " + mSettingsRepository.getGamer1() + "\nScore : " + mSettingsRepository.getScoreGamer1());
            mTextViewGamer2.setText("Gamer 2 : " + mSettingsRepository.getGamer2() + "\nScore : " + mSettingsRepository.getScoreGamer2());
        }
    }
}