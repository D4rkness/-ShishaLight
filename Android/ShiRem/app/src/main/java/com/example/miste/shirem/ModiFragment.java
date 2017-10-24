package com.example.miste.shirem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ModiFragment extends Fragment {

    private View customView;
    public ModiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        customView = inflater.inflate(R.layout.fragment_modi, container, false);
        initBtns();
        return customView;
    }

    public void initBtns(){
        Button btnSolid = (Button) customView.findViewById(R.id.btnSolid);
        Button btnRainbow = (Button) customView.findViewById(R.id.btnRainbow);
        Button btnFade = (Button) customView.findViewById(R.id.btnFade);
        Button btnLoading = (Button) customView.findViewById(R.id.btnLoading);
        Button btnRunning = (Button) customView.findViewById(R.id.btnRunning);
        Button btnLightning = (Button) customView.findViewById(R.id.btnLightning);
        Button btnBreath = (Button) customView.findViewById(R.id.btnBreath);
        Button btnJoggling = (Button) customView.findViewById(R.id.btnJoggling);

        btnSolid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.SOLID);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRainbow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.RAINBOW);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.FADE);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.LOADING);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.RUNNING);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLightning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.LIGHTNING);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.BREATH);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnJoggling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Controller.getInstance().setMode(Mode.JOGGLING);
                } catch (BluetoothException e) {
                    Toast.makeText(customView.getContext(),
                            "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
