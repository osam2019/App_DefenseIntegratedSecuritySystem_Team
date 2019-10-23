package com.rokdcc.diss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FirstAuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_auth);
    }
    public void onSubmitBtClicked(View v){
        startActivity(new Intent(getApplication(), PinAuthActivity.class));
        this.finish();//로딩이 끝난 후, ChoiceFunction 이동


    }
    public void onCloseBtClicked(View v){
        System.exit(0);
    }
}
