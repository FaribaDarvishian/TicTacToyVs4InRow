package com.example.tictactoyvs4inrow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class FourInRowFragment extends Fragment {


    public static final String BUNDLE_IS_PLAYED = "BundleIsPlayed";
    public static final String BUNDLE_PLAYERS_TURN = "BundlePlayersTurn";
    private Button[] mButtons = new Button[25];
    private int[] mIsPlayed = new int[25];

    private int playersTurn = 0;


    public FourInRowFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_in_row,container,false);
        findView(view);
        setListeners();

        if (savedInstanceState!=null){
            playersTurn = savedInstanceState.getInt(BUNDLE_PLAYERS_TURN);
            mIsPlayed = savedInstanceState.getIntArray(BUNDLE_IS_PLAYED);
            for (int i = 0; i < 25 ; i++) {
                if (mIsPlayed[i]==0) {
                    mButtons[i].setBackgroundColor(Color.RED);
                    mButtons[i].setEnabled(false);
                } else if (mIsPlayed[i]==1){
                    mButtons[i].setBackgroundColor(Color.BLUE);
                    mButtons[i].setEnabled(false);
                }
            }
        }
        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(BUNDLE_IS_PLAYED, mIsPlayed);
        outState.putInt(BUNDLE_PLAYERS_TURN,playersTurn);
    }

    private void findView(View view){

        mButtons = new Button[]{view.findViewById(R.id.btn0),
                                view.findViewById(R.id.btn1),
                                view.findViewById(R.id.btn2),
                                view.findViewById(R.id.btn3),
                                view.findViewById(R.id.btn4),
                                view.findViewById(R.id.btn5),
                                view.findViewById(R.id.btn6),
                                view.findViewById(R.id.btn7),
                                view.findViewById(R.id.btn8),
                                view.findViewById(R.id.btn9),
                                view.findViewById(R.id.btn10),
                                view.findViewById(R.id.btn11),
                                view.findViewById(R.id.btn12),
                                view.findViewById(R.id.btn13),
                                view.findViewById(R.id.btn14),
                                view.findViewById(R.id.btn15),
                                view.findViewById(R.id.btn16),
                                view.findViewById(R.id.btn17),
                                view.findViewById(R.id.btn18),
                                view.findViewById(R.id.btn19),
                                view.findViewById(R.id.btn20),
                                view.findViewById(R.id.btn21),
                                view.findViewById(R.id.btn22),
                                view.findViewById(R.id.btn23),
                                view.findViewById(R.id.btn24)};

    }



    private void setListeners(){

        for (int i = 0; i < 25 ; i++) {
            final int currentChoose = i;
            mButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mButtons[currentChoose].isEnabled()){

                        if (playersTurn%2==0){
                            mButtons[currentChoose].setBackgroundColor(Color.RED);
                        } else {
                            mButtons[currentChoose].setBackgroundColor(Color.BLUE);
                        }

                        mIsPlayed[currentChoose] = playersTurn % 2;
                        playersTurn++;
                        mButtons[currentChoose].setEnabled(false);


                    }
                }
            });
        }
    }
}