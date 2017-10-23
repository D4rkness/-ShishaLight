package com.example.miste.shirem;


import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private View customView;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        customView = inflater.inflate(R.layout.fragment_settings, container, false);
        initGui();
        return customView;
    }

    private  void initGui(){
        Button btnSearch = (Button) customView.findViewById(R.id.btnSearchSettings);
        final ListView listBoundDevices = (ListView) customView.findViewById(R.id.listBoundDevices);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> s = new ArrayList<String>();
                for(BluetoothDevice bt:  Controller.getInstance().searchDevices()){
                    s.add(bt.getName());
                }
                listBoundDevices.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, s));

            }
        });

    }

}
