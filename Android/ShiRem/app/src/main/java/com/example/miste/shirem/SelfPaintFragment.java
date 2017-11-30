package com.example.miste.shirem;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.larswerkman.holocolorpicker.ColorPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelfPaintFragment extends Fragment{

    DrawField dField;
    View myView;
    Button colorBtn;

    public SelfPaintFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_self_paint, container, false);
        initGui();
        return myView;
    }

    private void initGui(){
        dField = (DrawField) myView.findViewById(R.id.drawField);
        colorBtn = (Button) myView.findViewById(R.id.btnColorDraw);
        colorBtn.setBackgroundColor(Model.getInstance().getAccDrawColor());
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(true)
                        .setDialogId(0)
                        .setShowAlphaSlider(false)
                        .show(getActivity());
            }
        });
        Button setAll = (Button) myView.findViewById(R.id.btnDrawSetAll);
        setAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setAllColor();
                dField.invalidate();
            }
        });

        Button resetAll = (Button) myView.findViewById(R.id.btnDrawReset);
        resetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().resetFieldColor();
                dField.invalidate();
            }
        });


    }


    public void onColorSelected(int color) {
        colorBtn.setBackgroundColor(color);
        Model.getInstance().setAccDrawColor(color);
    }



}
