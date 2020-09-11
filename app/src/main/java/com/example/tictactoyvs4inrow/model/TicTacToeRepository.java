package com.example.tictactoyvs4inrow.model;

import android.view.View;

import com.example.tictactoyvs4inrow.Player;

import java.util.Arrays;

public class TicTacToeRepository {
    private static TicTacToeRepository sTicTacToeRepository;

    public static TicTacToeRepository getInstance(){
        if (sTicTacToeRepository == null)
            sTicTacToeRepository = new TicTacToeRepository();
        return sTicTacToeRepository;
    }


    private TicTacToeRepository() {
        initialRepository();
    }

    private Player[][] mElements;
    private Player mElement;
    private String mMessage = "";
    private boolean[][] mBooleanAllButtonsClickable;
    private int mViewButtonVisibility = View.INVISIBLE;
    private float mFloatButtonsAlpha = 1;

    private void initialRepository(){

        mElements = new Player[3][3];

        mBooleanAllButtonsClickable = new boolean[3][3];

        for (int i = 0; i < 3; i++) {
            Arrays.fill(mElements[i], Player.E);
            Arrays.fill(mBooleanAllButtonsClickable[i], true);
        }
        mElement = Player.X;
    }

    public String getMessage() {
        return mMessage;
    }

    public void startGame(int i, int j) {


        mElements[i][j] = mElement;

        if (isGameOver())
            return;


        if (mElement == Player.X)
            mElement = Player.O;
        else
            mElement = Player.X;


    }

    public Player getElement(){
        return mElement;
    }

    public boolean isGameOver() {

        if (getWinner(mElement)) {
            mMessage = mElement + " Wins!";
            return true;
        }


        if (isGameEqual()) {
            mMessage = "Game Finished! Your match is equal.";
            return true;
        }

        return false;
    }

    private boolean isGameEqual() {
        for (int i = 0; i < mElements.length; i++) {
            for (int j = 0; j < mElements[i].length; j++) {
                if (!mElements[i][j].equals(Player.X) && !mElements[i][j].equals(Player.O))
                    return false;
            }
        }
        return true;
    }

    private boolean getWinner(Player player) {



        outer:
        for (int i = 0; i < mElements.length; i++) {
            for (int j = 0; j < mElements[i].length; j++) {
                if (mElements[i][j] != player)
                    continue outer;
            }
            return true;
        }

        outer:
        for (int i = 0; i < mElements.length; i++) {
            for (int j = 0; j < mElements[i].length; j++) {
                if (mElements[j][i] != player)
                    continue outer;
            }
            return true;
        }

        boolean zeroToDown = true;
        for (int i = 0; i < mElements.length; i++) {
            if (mElements[i][i] == player)
                continue;
            zeroToDown = false;
            break;
        }


        boolean zeroToUp = true;
        for (int i = 0, j = 2; i < mElements.length; i++, j--) {
            if (mElements[i][j] == player)
                continue;
            zeroToUp = false;
            break;
        }

        return zeroToDown || zeroToUp;
    }

    public void resetGame(){
        mMessage = "";
        initialRepository();
    }

    public Player[][] getElements(){
        return mElements;
    }

    public boolean[][] getBooleanAllButtonsClickable() {
        return mBooleanAllButtonsClickable;
    }

    public void setBooleanButtonClickable(int i, int j, boolean booleanButtonClickable) {
        mBooleanAllButtonsClickable[i][j] = booleanButtonClickable;
    }

    public int getViewButtonVisibility() {
        return mViewButtonVisibility;
    }

    public void setViewButtonVisibility(int viewButtonVisibility) {
        mViewButtonVisibility = viewButtonVisibility;
    }

    public float getFloatButtonsAlpha(){return mFloatButtonsAlpha;}

    public void setFloatButtonsAlpha(float floatButtonsAlpha){mFloatButtonsAlpha = floatButtonsAlpha;}
}

