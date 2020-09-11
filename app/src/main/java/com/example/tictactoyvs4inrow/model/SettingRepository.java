package com.example.tictactoyvs4inrow.model;

public class SettingRepository {
    private static SettingRepository sRepository;

    public static SettingRepository getInstance(){
        if (sRepository == null)
            sRepository = new SettingRepository();
        return sRepository;
    }

    private SettingRepository(){}

    private int mScoreGamer1, mScoreGamer2;
    private String gamer1 = "", gamer2 = "";
    private boolean mBooleanGamerCheck;


    public boolean getBooleanGamerCheck() {
        return mBooleanGamerCheck;
    }

    public void setBooleanGamerCheck(boolean booleanGamerCheck) {
        mBooleanGamerCheck = booleanGamerCheck;
    }


    public int getScoreGamer1() {
        return mScoreGamer1;
    }

    public void setScoreGamer1(int scoreGamer1) {
        mScoreGamer1 = scoreGamer1;
    }

    public int getScoreGamer2() {
        return mScoreGamer2;
    }

    public void setScoreGamer2(int scoreGamer2) {
        mScoreGamer2 = scoreGamer2;
    }

    public String getGamer1() {
        return gamer1;
    }

    public void setGamer1(String gamer1) {
        this.gamer1 = gamer1;
    }

    public String getGamer2() {
        return gamer2;
    }

    public void setGamer2(String gamer2) {
        this.gamer2 = gamer2;
    }


}
