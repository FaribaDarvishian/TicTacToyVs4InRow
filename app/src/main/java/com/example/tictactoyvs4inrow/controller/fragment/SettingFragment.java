package com.example.tictactoyvs4inrow.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.tictactoyvs4inrow.R;
import com.example.tictactoyvs4inrow.repository.FourInRowRepository;
import com.example.tictactoyvs4inrow.repository.SettingRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    LinearLayout mLinearLayoutGame;
    private SettingRepository mSettingsRepository;
    private FourInRowRepository mFourInRowRepository;
    private CheckBox mCheckBoxGamer;
    private EditText mEditTextGamer1, mEditTextGamer2;
    private NumberPicker mNumberPickerRows, mNumberPickerColumns;


    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingsRepository = SettingRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        findAllViews(view);
        initialization();
        setListeners();
        return view;
    }

    private void findAllViews(View view) {

        mCheckBoxGamer = view.findViewById(R.id.checkBox_gamer);
        mEditTextGamer1 = view.findViewById(R.id.editText_gamer1);
        mEditTextGamer2 = view.findViewById(R.id.editText_gamer2);
        mLinearLayoutGame = view.findViewById(R.id.gamer_layout_settings);

        mNumberPickerRows = view.findViewById(R.id.number_picker_row);
        mNumberPickerColumns = view.findViewById(R.id.number_picker_column);

    }

    private void setListeners() {
        mNumberPickerRows.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mSettingsRepository.setNumberOfRows(newVal);
                mFourInRowRepository.setIntRow(newVal);
            }
        });

        mNumberPickerColumns.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mSettingsRepository.setNumberOfColumns(newVal);
                mFourInRowRepository.setIntColumn(newVal);
            }
        });


        mCheckBoxGamer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mSettingsRepository.setBooleanGamerCheck(isChecked);

                if (isChecked) {
                    mEditTextGamer1.setEnabled(true);
                    mEditTextGamer2.setEnabled(true);
                }
                else {
                    mEditTextGamer1.setEnabled(false);
                    mEditTextGamer2.setEnabled(false);
                }
                mEditTextGamer1.setText("");
                mEditTextGamer2.setText("");
                mSettingsRepository.setScoreGamer1(0);
                mSettingsRepository.setScoreGamer2(0);
                mSettingsRepository.setGamer1("");
                mSettingsRepository.setGamer2("");

            }
        });

        mEditTextGamer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0)
                    mSettingsRepository.setGamer1(s.toString());
                else
                    mEditTextGamer1.setError("TextBox cannot be empty.");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextGamer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0)
                    mSettingsRepository.setGamer2(s.toString());
                else
                    mEditTextGamer2.setError("TextBox cannot be empty.");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void initialization(){

        // min and max of NumberPicker
        mNumberPickerRows.setMinValue(5);
        mNumberPickerRows.setMaxValue(8);
        mNumberPickerColumns.setMinValue(5);
        mNumberPickerColumns.setMaxValue(8);

        mNumberPickerRows.setValue(mFourInRowRepository.getIntRow());
        mNumberPickerColumns.setValue(mFourInRowRepository.getIntColumn());
        mCheckBoxGamer.setChecked(mSettingsRepository.getBooleanGamerCheck());

        mEditTextGamer1.setText(mSettingsRepository.getGamer1());
        mEditTextGamer2.setText(mSettingsRepository.getGamer2());
        mEditTextGamer1.setEnabled(mSettingsRepository.getBooleanGamerCheck());
        mEditTextGamer2.setEnabled(mSettingsRepository.getBooleanGamerCheck());


    }
}