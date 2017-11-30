package com.example.miste.shirem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment implements View.OnTouchListener{

    private ColorPicker picker;
    private View customView;
    public ColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        customView = inflater.inflate(R.layout.fragment_color, container, false);
        setUpColorWheel();
        initBtn();
        return customView;
    }

    private void setUpColorWheel(){
        picker = (ColorPicker) customView.findViewById(R.id.picker);
        ValueBar valueBar = (ValueBar) customView.findViewById(R.id.valuebar);
        SaturationBar saturationBar = (SaturationBar) customView.findViewById(R.id.saturationbar);
        picker.setShowOldCenterColor(false);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

    }


    private  void initBtn(){
        Button btnSend = (Button) customView.findViewById(R.id.btnSendColor);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = picker.getColor();
                String hexColor = String.format("0x%06X", (0xFFFFFF & color));
                Log.d("color",hexColor);
                Log.d("Color Fragment","HexNumber:" + hexColor);
                try {
                    Controller.getInstance().setColor(hexColor);
                } catch (BluetoothException e) {
                    if(e.getErr() == BluetoothError.NO_CONNECTION){
                        Toast.makeText(customView.getContext(), "Lamp not connected",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
