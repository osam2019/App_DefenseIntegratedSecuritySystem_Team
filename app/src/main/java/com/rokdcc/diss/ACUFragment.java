package com.rokdcc.diss;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ACUFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acu, container, false);

        final ListView lv = (ListView) view.findViewById(R.id.aculistview);
        String[] fruits = new String[] {
                "2019-10-24 08:31:11        입영      정문",
                "2019-10-23 18:21:12        퇴영      후문",
                "2019-10-23 08:31:12        입영      정문",
                "2019-10-22 18:31:12        퇴영      후문",
                "2019-10-22 08:31:12        입영      정문",
                "2019-10-21 18:31:12        퇴영      후문",
                "2019-10-21 08:31:12        입영      정문",
                "2019-10-20 18:31:12        퇴영      후문",
                "2019-10-20 08:31:12        입영      정문",
        };
        final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));
        final ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, fruits_list);
        lv.setAdapter(arrayAdapter);

        return view;
    }
}
