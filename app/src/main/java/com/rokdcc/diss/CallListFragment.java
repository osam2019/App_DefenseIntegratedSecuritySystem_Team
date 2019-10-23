package com.rokdcc.diss;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CallListFragment extends Fragment {
    ArrayList<SampleData> movieDataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_list,
                container, false);
        ListView listView = (ListView)view.findViewById(R.id.listView);
        this.InitializeMovieData();

        final MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), movieDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getActivity().getApplicationContext(),
                        myAdapter.getItem(position).getMovieName(),
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + myAdapter.getItem(position).getGrade()));
                startActivity(intent);
            }
        });
        return view;
    }
    public void InitializeMovieData()
    {
        movieDataList = new ArrayList<SampleData>();

        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "사령부 지휘통제실","02-0434-2312"));
        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "본근대 지휘통제실","02-1234-2231"));
        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "본근대 행정반","02-1231-2342"));
        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "정문 위병소","02-1232-2142"));
        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "탄약고","02-1221-2742"));
        movieDataList.add(new SampleData(R.drawable.phoneclickedimg, "사령부 행정실","02-1221-2742"));
    }
}
