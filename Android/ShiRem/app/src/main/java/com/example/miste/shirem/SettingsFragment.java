package com.example.miste.shirem;


import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements Observer{

    private View customView;
    private Button btnStatus;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        customView = inflater.inflate(R.layout.fragment_settings, container, false);
        initGui();
        BluetoothService.getInstance().addObserver(this);
        return customView;
    }

    private  void initGui(){
        Button btnSearch = (Button) customView.findViewById(R.id.btnSearchSettings);
        btnStatus = (Button) customView.findViewById(R.id.btnConnectionStatus);
        final ListView listBoundDevices = (ListView) customView.findViewById(R.id.listBoundDevices);
        listBoundDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(customView.getContext(),
                        "Trying to connect", Toast.LENGTH_SHORT).show();
                try {
                    Controller.getInstance().connect(position);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "Connection is Running stop hitting idiot", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> s = new ArrayList<String>();
                try {
                    for(String bt:  Controller.getInstance().searchDevices()){
                        s.add(bt);
                    }
                } catch (BluetoothException e) {
                    if(e.getErr() == BluetoothError.BLUETOOTH_OFF){
                        // Make Toast here that bluetooth is not enasbled
                    }else{
                        e.printStackTrace();
                    }

                }
                listBoundDevices.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, s));

            }
        });


    }

    @Override
    public void update(Observable o, Object arg) {
        Handler mainHandler = new Handler(getContext().getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                boolean isConnected = BluetoothService.getInstance().getIsConnected();
                if (isConnected) {
                    btnStatus.setBackgroundColor(Color.GREEN);
                    btnStatus.setText("Connected");
                } else {
                    btnStatus.setBackgroundColor(Color.RED);
                    btnStatus.setText("No Connection");
                }
            }
        };
        mainHandler.post(myRunnable);


    }

}
