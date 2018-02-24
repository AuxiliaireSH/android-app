package com.laxen.auxiliaire;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.laxen.auxiliaire.models.Job;

import java.util.Date;

/**
 * Created by laxen on 5/14/16.
 */
public class AddActivity extends Activity {

    private View background;

    private Button addButton;
    private ImageView backButton;
    private EditText nameEdit;
    private Spinner categories;
    private EditText titleEdit;
    private EditText priceEdit;

    private TextView descText;


    final int[] startColors = {   R.color.colorHandiworkStart, R.color.colorComputerStart,
            R.color.colorStudiesStart, R.color.colorCookingStart, R.color.colorOtherStart};

    final int[] endColors = {   R.color.colorHandiworkEnd, R.color.colorComputerEnd,
            R.color.colorStudiesEnd, R.color.colorCookingEnd, R.color.colorOtherEnd};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initUI();
    }

    public void initUI() {

        background = findViewById(R.id.add_background);
        backButton = (ImageView) findViewById(R.id.back_button);
        addButton = (Button) findViewById(R.id.addBtn);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        priceEdit = (EditText) findViewById(R.id.priceEdit);
        descText = (EditText) findViewById(R.id.descEdit);
        categories = (Spinner) findViewById(R.id.categories);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, R.layout.cat_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        categories.setAdapter(adapter);
        categories.setSelection(0);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                updateBackgroundColor(startColors[position], endColors[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(Activity.RESULT_CANCELED, null);
                finish();
            }
        });



        // TODO send result back

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Job job = new Job(0,
                        nameEdit.getText().toString(),
                        Integer.parseInt(priceEdit.getText().toString()),
                        categories.getSelectedItem().toString(),
                        11.974560,
                        57.708870,
                        new Date(),
                        new Date(),
                        descText.getText().toString(),
                        titleEdit.getText().toString());


                Intent returnIntent = new Intent();
                returnIntent.putExtra("jobresult", job);

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


        /*addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Job job = new Job(0,
                        nameEdit.getText().toString(),
                        Integer.parseInt(priceEdit.getText().toString()),
                        categories.getSelectedItem().toString(),
                        11.974560,
                        57.708870,
                        new Date(),
                        new Date(),
                        descText.getText().toString(),
                        titleEdit.getText().toString());


            }
        });*/
    }

    @UiThread
    public void updateBackgroundColor(int colorStart, int colorEnd) {

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TR_BL,
                new int[] {getResources().getColor(colorStart), getResources().getColor(colorEnd)});

        Drawable[] grads = {background.getBackground(), gd};

        TransitionDrawable transitionDrawable = new TransitionDrawable(grads);
        background.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);


    }


}
