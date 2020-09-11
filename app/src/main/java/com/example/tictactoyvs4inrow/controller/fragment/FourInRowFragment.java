package com.example.tictactoyvs4inrow.controller.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tictactoyvs4inrow.Player;
import com.example.tictactoyvs4inrow.R;
import com.example.tictactoyvs4inrow.repository.FourInRowRepository;
import com.example.tictactoyvs4inrow.repository.SettingRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourInRowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class FourInRowFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mLinearLayoutMain;
    private RelativeLayout mRelativeLayoutGamerName;
    private TextView mTextViewGamer1, mTextViewGamer2;
    private FourInRowRepository mFourInRowRepository;
    private SettingRepository mSettingsRepository;
    private Button mButtonReset;
    private List<List<Button>> mButtonsList = new ArrayList<>();

    public static FourInRowFragment newInstance() {

        FourInRowFragment fragment = new FourInRowFragment();
        fragment.setArguments(new Bundle());

        FourInRowRepository.getInstance().resetGame();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFourInRowRepository = FourInRowRepository.getInstance();
        mSettingsRepository = SettingRepository.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four_in_row, container, false);
        findAllViews(view);
        setGamerName();
        addLayout();
        if (savedInstanceState != null) {

            for (int i = 0; i < mButtonsList.size(); i++) {
                for (int j = 0; j < mButtonsList.get(i).size(); j++) {
                    if (mFourInRowRepository.getButtons()[i][j] != Player.E) {
                        if (mFourInRowRepository.getButtons()[i][j] == Player.X)
                            mButtonsList.get(i).get(j).setBackground(getResources().getDrawable(R.drawable.btn_style_blue_shift));
                        else if (mFourInRowRepository.getButtons()[i][j] == Player.O)
                            mButtonsList.get(i).get(j).setBackground(getResources().getDrawable(R.drawable.btn_style_red_shift));
                    }
                    mButtonsList.get(i).get(j).setAlpha(mFourInRowRepository.getFloatButtonsAlpha());
                    mButtonsList.get(i).get(j).setClickable(mFourInRowRepository.getBooleanAllButtonsClickable()[i][j]);
                    mButtonReset.setVisibility(mFourInRowRepository.getViewButtonVisibility());
                }
            }

        }
        return view;
    }

    private void findAllViews(View view) {
        mLinearLayoutMain = view.findViewById(R.id.four_in_row_layout);
        mButtonReset = view.findViewById(R.id.reset_four_in_row);
        mRelativeLayoutGamerName = view.findViewById(R.id.four_gamer_layout);
        mTextViewGamer1 = view.findViewById(R.id.four_gamer1);
        mTextViewGamer2 = view.findViewById(R.id.four_gamer2);
        mButtonReset.setOnClickListener(this);
    }

    private void addLayout() {

        int row = mFourInRowRepository.getIntRow();
        int column = mFourInRowRepository.getIntColumn();

        mLinearLayoutMain.setWeightSum(row);
        LinearLayout[] linearLayouts = new LinearLayout[row];
        Button[] buttons = new Button[column];

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        buttonParams.weight = 1;
        layoutParams.weight = 1;


        for (int i = 0; i < row; i++) {
            linearLayouts[i] = new LinearLayout(getActivity());
            linearLayouts[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0));
            linearLayouts[i].setLayoutParams(layoutParams);
            linearLayouts[i].setGravity(Gravity.CENTER);
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayouts[i].setWeightSum(row + 2);
            List<Button> rowList = new ArrayList<>();
            for (int j = 0; j < column; j++) {
                buttons[j] = new Button(getActivity());
                buttons[j].setBackground(getResources().getDrawable(R.drawable.btn_style_default_four_in_row));
                buttons[j].setLayoutParams(buttonParams);
                linearLayouts[i].addView(buttons[j]);
                rowList.add(buttons[j]);
            }
            mButtonsList.add(rowList);
            mLinearLayoutMain.addView(linearLayouts[i]);
        }

        for (int i = 0; i < mButtonsList.size(); i++) {
            for (int j = 0; j < mButtonsList.get(i).size(); j++) {
                mButtonsList.get(i).get(j).setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View v) {

        for (int i = 0; i < mButtonsList.size(); i++) {
            for (int k = 0; k < mButtonsList.get(i).size(); k++) {
                if (v == mButtonsList.get(i).get(k)) {
                    for (int j = mButtonsList.size() - 1; j >= 0; j--) {
                        if (mFourInRowRepository.getButtons()[j][k].equals(Player.E)) {
                            setButtonsText(mButtonsList.get(j).get(k));
                            mFourInRowRepository.startGame(j, k);
                            isGameOver(v, j, k);
                            break;
                        }
                    }

                }
            }

        }

        if (v == mButtonReset) {
            mFourInRowRepository.setFloatButtonsAlpha(1);
            for (int i = 0; i < mButtonsList.size(); i++) {
                for (int j = 0; j < mButtonsList.get(i).size(); j++) {
                    mButtonsList.get(i).get(j).setClickable(true);
                    mButtonsList.get(i).get(j).setAlpha(1);
                    mFourInRowRepository.setBooleanButtonClickable(i, j, mButtonsList.get(i).get(j).isClickable());
                    mButtonsList.get(i).get(j).setBackground(getResources().getDrawable(R.drawable.btn_style_default_four_in_row));
                    mButtonsList.get(i).get(j).setAlpha(mFourInRowRepository.getFloatButtonsAlpha());
                }
            }
            mTextViewGamer1.setTextColor(Color.BLUE);
            mTextViewGamer2.setTextColor(Color.BLACK);

            mFourInRowRepository.setViewButtonVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(mFourInRowRepository.getViewButtonVisibility());
            mFourInRowRepository.resetGame();
        }


    }

    private void setButtonsText(Button button) {

        if (mFourInRowRepository.getButton().equals(Player.X)) {
            button.setBackground(getResources().getDrawable(R.drawable.btn_style_blue_shift));
            mTextViewGamer2.setTextColor(Color.RED);
            mTextViewGamer1.setTextColor(Color.BLACK);
        } else if (mFourInRowRepository.getButton().equals(Player.O)) {
            button.setBackground(getResources().getDrawable(R.drawable.btn_style_red_shift));
            mTextViewGamer1.setTextColor(Color.BLUE);
            mTextViewGamer2.setTextColor(Color.BLACK);
        }
    }

    private void isGameOver(View view, int i, int j) {

        if (mFourInRowRepository.isGameOver(i, j)) {

            String winner = mFourInRowRepository.getMessage();


            if (mSettingsRepository.getGamer1().length() > 0 && mSettingsRepository.getGamer2().length() > 0) {


                if (winner.contains("Blue")) {
                    winner = mSettingsRepository.getGamer1() + " wins!";
                    mSettingsRepository.setScoreGamer1(mSettingsRepository.getScoreGamer1() + 1);
                }
                else if (winner.contains("Red")) {
                    winner = mSettingsRepository.getGamer2() + " wins!";
                    mSettingsRepository.setScoreGamer2(mSettingsRepository.getScoreGamer2() + 1);
                }
                mTextViewGamer1.setText("Gamer 1 : " + mSettingsRepository.getGamer1() + "\nScore : " + mSettingsRepository.getScoreGamer1());
                mTextViewGamer2.setText("Gamer 2 : " + mSettingsRepository.getGamer2() + "\nScore : " + mSettingsRepository.getScoreGamer2());
            }
            Snackbar.make(view, winner, Snackbar.LENGTH_LONG).show();
            setAllButtonClickable();
            mFourInRowRepository.setViewButtonVisibility(View.VISIBLE);
            mButtonReset.setVisibility(mFourInRowRepository.getViewButtonVisibility());
        }
    }

    private void setAllButtonClickable() {
        mFourInRowRepository.setFloatButtonsAlpha(0.3f);
        for (int i = 0; i < mButtonsList.size(); i++) {
            for (int j = 0; j < mButtonsList.get(i).size(); j++) {
                mButtonsList.get(i).get(j).setClickable(false);
                mButtonsList.get(i).get(j).setAlpha(mFourInRowRepository.getFloatButtonsAlpha());
                mFourInRowRepository.setBooleanButtonClickable(i, j, false);
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