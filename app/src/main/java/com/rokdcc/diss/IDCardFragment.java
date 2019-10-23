package com.rokdcc.diss;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class IDCardFragment extends Fragment {
    private int FLAG=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idcard,
                container, false);
        final Button button = view.findViewById(R.id.CardViewBt);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(FLAG==0){
                    button.setBackgroundResource(R.drawable.cardrearimg);
                    FLAG=1;
                }
                else{
                    button.setBackgroundResource(R.drawable.cardfrontimg);
                    FLAG=0;
                }
            }
        });
        return view;

    }

}