package com.example.tictactoyvs4inrow.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tictactoyvs4inrow.R;
import com.example.tictactoyvs4inrow.controller.fragment.FourInRowFragment;
import com.example.tictactoyvs4inrow.controller.fragment.SettingFragment;
import com.example.tictactoyvs4inrow.controller.fragment.TicTacToeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button mButtonTicTac;
    Button mButtonSettings;
    Button mButtonFourInRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, TicTacToeFragment.newInstance()).commit();

    }
    private void findViews(){
        mButtonTicTac = findViewById(R.id.btn_start_tic_tac);
        mButtonFourInRow = findViewById(R.id.btn_start_in_row);
        mButtonSettings = findViewById(R.id.btn_setting);

        mButtonTicTac.setOnClickListener(this);
        mButtonFourInRow.setOnClickListener(this);
        mButtonSettings.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == mButtonTicTac){
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != TicTacToeFragment.newInstance())
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, TicTacToeFragment.newInstance()).commit();
        }

        if (v == mButtonFourInRow){
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != FourInRowFragment.newInstance() )
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FourInRowFragment.newInstance()).commit();
        }

        if (v == mButtonSettings){
            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) != SettingFragment.newInstance())
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SettingFragment.newInstance()).commit();
        }
    }
}