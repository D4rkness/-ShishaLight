package com.example.miste.shirem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorFragment extends Fragment {

    private ColorPicker picker;
    private SVBar svBar;
    private OpacityBar opacityBar;
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


    public void onColorChanged(int color) {
        //gives the color when it's changed.
    }


}
