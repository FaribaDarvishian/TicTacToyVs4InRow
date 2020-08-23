package com.example.tictactoyvs4inrow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class TicTacToeFragment extends Fragment {


    public static final String BUNDLE_IS_PLAYED = "BundleIsPlayed";
    public static final String BUNDLE_PLAYERS_TURN = "BundlePlayersTurn";
    private Button[] mButtons = new Button[9];

    private int[] mIsChoose = {10,10,10,10,10,10,10,10,10};
    private int mPlayersTurn = 0;


    public TicTacToeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe,container,false);
        findView(view);
        setListeners();

        if (savedInstanceState!=null){
            mPlayersTurn = savedInstanceState.getInt(BUNDLE_PLAYERS_TURN);
            mIsChoose = savedInstanceState.getIntArray(BUNDLE_IS_PLAYED);
            for (int i = 0; i < 9 ; i++) {
                if (mIsChoose[i]==0) {
                    mButtons[i].setText("+");
                    mButtons[i].setEnabled(false);
                } else if (mIsChoose[i]==1){
                    mButtons[i].setText("o");
                    mButtons[i].setEnabled(false);
                }
            }
        }
        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(BUNDLE_IS_PLAYED, mIsChoose);
        outState.putInt(BUNDLE_PLAYERS_TURN, mPlayersTurn);
    }

    private void findView(View view){

        mButtons = new Button[]{view.findViewById(R.id.btn1),
                                view.findViewById(R.id.btn2),
                                view.findViewById(R.id.btn3),
                                view.findViewById(R.id.btn4),
                                view.findViewById(R.id.btn5),
                                view.findViewById(R.id.btn6),
                                view.findViewById(R.id.btn7),
                                view.findViewById(R.id.btn8),
                                view.findViewById(R.id.btn9)};

    }

    private void setListeners(){

        for (int i = 0; i < 9 ; i++) {
            final int currentIndex = i;
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mButtons[currentIndex].isEnabled()){

                        if (mPlayersTurn %2==0){
                            mButtons[currentIndex].setText("+");
                        } else {
                            mButtons[currentIndex].setText("o");
                        }


                        mIsChoose[currentIndex] = mPlayersTurn %2;
                        mPlayersTurn++;
                        mButtons[currentIndex].setEnabled(false);
                        checkGameOver();

                    }
                }
            });
        }

    }

    private void checkGameOver(){

        if (isWins(0)) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "player1 Win",
                    Snackbar.LENGTH_LONG).show();

            resetGame();
        } else if (isWins(3)){
            Snackbar.make(getActivity().findViewById(android.R.id.content), "player2 win",
                    Snackbar.LENGTH_LONG).show();

            resetGame();
        }

    }
    private boolean isWins(int checkSum) {
        if (    mIsChoose[0] + mIsChoose[1] + mIsChoose[2] == checkSum ||
                mIsChoose[3] + mIsChoose[4] + mIsChoose[5] == checkSum ||
                mIsChoose[6] + mIsChoose[7] + mIsChoose[8] == checkSum ||
                mIsChoose[0] + mIsChoose[3] + mIsChoose[6] == checkSum ||
                mIsChoose[1] + mIsChoose[4] + mIsChoose[7] == checkSum ||
                mIsChoose[2] + mIsChoose[5] + mIsChoose[8] == checkSum ||
                mIsChoose[0] + mIsChoose[4] + mIsChoose[8] == checkSum ||
                mIsChoose[2] + mIsChoose[4] + mIsChoose[6] == checkSum)
            return true;
        else
            return false;
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            mButtons[i].setEnabled(false);

        }
    }

}