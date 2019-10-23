package com.rokdcc.diss;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_setting, container, false);

        Button helpcallbt=(Button) view.findViewById(R.id.helpcallBt);
        Button resetbt=(Button) view.findViewById(R.id.resetBt);
        Button AppInfobt=(Button) view.findViewById(R.id.AppInfoBt);

        helpcallbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1303"));
                startActivity(intent);
            }
        });
        resetbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("앱정보 초기화").setMessage("정말로 앱 내의 모든 데이터를 초기화 하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(view.getContext().getApplicationContext(), "앱이 초기화 됩니다.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(view.getContext(), FirstAuthActivity.class));
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(view.getContext().getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        AppInfobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("앱정보").setMessage("국방통합보안체계 1.0.0 Defense Integrated Security System");

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
        });
        return view;
    }
}
