package com.example.tictactoyvs4inrow;


import android.view.View;

import java.util.Arrays;

public class FourInRowRepository {

    private static FourInRowRepository sRepository;
    public static FourInRowRepository getInstance() {
        if (sRepository == null)
            sRepository = new FourInRowRepository();
        return sRepository;
    }

    private int mIntRow = 5, mIntColumn = 5;
    private Player[][] mButtons;
    private Player mButton;
    private String mMessage = "";
    private boolean[][] mBooleanAllButtonsClickable;
    private int mViewButtonVisibility = View.INVISIBLE;
    private float mFloatButtonsAlpha = 1;

    private FourInRowRepository() {
        initialRepository();
    }

    private void initialRepository(){

        mButtons = new Player[mIntRow][mIntColumn];
        mBooleanAllButtonsClickable = new boolean[mIntRow][mIntColumn];

        for (int i = 0; i < mIntRow; i++) {
            Arrays.fill(mButtons[i], Player.E);
            Arrays.fill(mBooleanAllButtonsClickable[i], true);
        }
        mButton = Player.X;
    }

    public int getIntRow() {
        return mIntRow;
    }

    public void setIntRow(int intRow) {
        mIntRow = intRow;
        initialRepository();
    }

    public int getIntColumn() {
        return mIntColumn;
    }

    public void setIntColumn(int intColumn) {
        mIntColumn = intColumn;
        initialRepository();
    }

    public void startGame(int i, int j) {

        mButtons[i][j] = mButton;

        if (isGameOver(i, j))
            return;

        if (mButton == Player.X)
            mButton = Player.O;
        else
            mButton = Player.X;
    }

    public boolean isGameOver(int i, int j) {

        if (getWinner(mButton, i, j)) {
            if (mButton == Player.X)
                mMessage = "Blue wins!";
            else
                mMessage = "Red wins!";
            return true;
        }
        else if (isGameEqual()) {
            mMessage = "Game Finished! Your match is equal.";
            return true;
        }
        return false;
    }

    private boolean isGameEqual() {

        for (Player[] button : mButtons) {
            for (Player player : button) {
                if (!player.equals(Player.X) && !player.equals(Player.O))
                    return false;
            }
        }
        return true;
    }

    private boolean getWinner(Player player, int currentI, int currentJ) {


        if (checkWinnerInRow(player, currentI)) return true;

        if (checkWinnerInColumn(player, currentJ)) return true;

        if (checkWinnerInMultiplyLeftToRight(player, currentI, currentJ)) return true;


        if (checkWinnerInMultiplyRightToLeft(player, currentI, currentJ)) return true;

        return false;
    }

    private boolean checkWinnerInRow(Player player, int currentI) {
        int counterRow = 0;

        for (int j = 0; j < mIntColumn; j++) {
            if (mButtons[currentI][j] == player)
                counterRow++;
            else
                counterRow = 0;

            if (counterRow == 4)
                return true;

        }
        return false;
    }
    private boolean checkWinnerInColumn(Player player, int currentJ) {
        int counterColumn = 0;

        for (int i = 0; i < mIntRow; i++) {
            if (mButtons[i][currentJ] == player)
                counterColumn++;
            else
                counterColumn = 0;

            if (counterColumn == 4)
                return true;

        }
        return false;
    }
    private boolean checkWinnerInMultiplyRightToLeft(Player player, int currentI, int currentJ) {
        int counterMultiply = 0;
        int minDiff = mIntColumn - 1 - currentJ;
        if (minDiff > currentI)
            minDiff = currentI;

        int endI = currentI - minDiff;
        int endJ = currentJ + minDiff;


        for (int i = endI, j = endJ; i < mIntRow && j >= 0; i++, j--) {
            if (mButtons[i][j] == player)
                counterMultiply++;
            else
                counterMultiply = 0;

            if (counterMultiply == 4)
                return true;
        }
        return false;
    }
    private boolean checkWinnerInMultiplyLeftToRight(Player player, int currentI, int currentJ) {
        int minInput = Math.min(currentI, currentJ);
        int startI = currentI - minInput;
        int startJ = currentJ - minInput;
        int counterMultiply = 0;

        for (int i = startI, j = startJ; i < mIntRow && j < mIntColumn; i++, j++) {
            if (mButtons[i][j] == player)
                counterMultiply++;
            else
                counterMultiply = 0;

            if (counterMultiply == 4)
                return true;
        }
        return false;
    }

    public void resetGame() {
        mMessage = "";
        initialRepository();
    }

    public void setBooleanButtonClickable(int i, int j, boolean booleanButtonClickable) {
        mBooleanAllButtonsClickable[i][j] = booleanButtonClickable;
    }

    public String getMessage() {
        return mMessage;
    }

    public Player getButton() {
        return mButton;
    }

    public Player[][] getButtons() {
        return mButtons;
    }

    public int getViewButtonVisibility() {
        return mViewButtonVisibility;
    }

    public void setViewButtonVisibility(int viewButtonVisibility) {
        mViewButtonVisibility = viewButtonVisibility;
    }

    public boolean[][] getBooleanAllButtonsClickable() {
        return mBooleanAllButtonsClickable;
    }

    public float getFloatButtonsAlpha(){return mFloatButtonsAlpha;}

    public void setFloatButtonsAlpha(float floatButtonsAlpha){mFloatButtonsAlpha = floatButtonsAlpha;}

}

