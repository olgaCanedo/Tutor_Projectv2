package com.example.olgac.tutor_temp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Schedules;

import java.util.ArrayList;
import java.util.List;

public class SchedulesA extends AppCompatActivity {
    private EditText[] day1, day2, day3, day4, day5, day6;
    private Button btnAddSchedule;
    private List<String> days;
    private String dayRec1,dayRec2,dayRec3,dayRec4,dayRec5,dayRec6;
    private ArrayList<Schedules> scheRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_schedule);

        day1 = new EditText[]{
                (EditText)findViewById(R.id.hS1),
                (EditText)findViewById(R.id.mS1),
                (EditText)findViewById(R.id.hE1),
                (EditText)findViewById(R.id.mE1),
                (EditText)findViewById(R.id.Room1),
        };
        day2 = new EditText[]{
                (EditText)findViewById(R.id.hS2),
                (EditText)findViewById(R.id.mS2),
                (EditText)findViewById(R.id.hE2),
                (EditText)findViewById(R.id.mE2),
                (EditText)findViewById(R.id.Room2),
        };
        day3 = new EditText[]{
                (EditText)findViewById(R.id.hS3),
                (EditText)findViewById(R.id.mS3),
                (EditText)findViewById(R.id.hE3),
                (EditText)findViewById(R.id.mE3),
                (EditText)findViewById(R.id.Room3),
        };
        day4 = new EditText[]{
                (EditText)findViewById(R.id.hS4),
                (EditText)findViewById(R.id.mS4),
                (EditText)findViewById(R.id.hE4),
                (EditText)findViewById(R.id.mE4),
                (EditText)findViewById(R.id.Room4),
        };
        day5 = new EditText[]{
                (EditText)findViewById(R.id.hS5),
                (EditText)findViewById(R.id.mS5),
                (EditText)findViewById(R.id.hE5),
                (EditText)findViewById(R.id.mE5),
                (EditText)findViewById(R.id.Room5),
        };
        day6 = new EditText[]{
                (EditText)findViewById(R.id.hS6),
                (EditText)findViewById(R.id.mS6),
                (EditText)findViewById(R.id.hE6),
                (EditText)findViewById(R.id.mE6),
                (EditText)findViewById(R.id.Room6),
        };

        btnAddSchedule = (Button) findViewById(R.id.btnAddSchedule);
        //TODO: Days Spinner
        Spinner spDays1 = (Spinner) findViewById(R.id.spDay1);
        Spinner spDays2 = (Spinner) findViewById(R.id.spDay2);
        Spinner spDays3 = (Spinner) findViewById(R.id.spDay3);
        Spinner spDays4 = (Spinner) findViewById(R.id.spDay4);
        Spinner spDays5 = (Spinner) findViewById(R.id.spDay5);
        Spinner spDays6 = (Spinner) findViewById(R.id.spDay6);
        days = new ArrayList<String>();
        days.add(" ");
        days.add("Mon");
        days.add("Tue");
        days.add("Wed");
        days.add("Thu");
        days.add("Fri");
        days.add("Sat");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, days);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDays1.setAdapter(dataAdapter);
        spDays1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec1=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec1="";
            }
        });
        spDays2.setAdapter(dataAdapter);
        spDays2.setSelection(0);
        spDays2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec2=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec2="";
            }
        });
        spDays3.setAdapter(dataAdapter);
        spDays3.setSelection(0);
        spDays3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec3=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec3="";
            }
        });
        spDays4.setAdapter(dataAdapter);
        spDays4.setSelection(0);
        spDays4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec4=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec4="";
            }
        });
        spDays5.setAdapter(dataAdapter);
        spDays5.setSelection(0);
        spDays5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec5=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec5="";
            }
        });
        spDays6.setAdapter(dataAdapter);
        spDays6.setSelection(0);
        spDays6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dayRec6=days.get(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dayRec6="";
            }
        });

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scheRec=new ArrayList<Schedules>();


                if(dayRec1!=" " && !day1[0].getText().toString().isEmpty() && !day1[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec1,
                            day1[0].getText().toString(),
                            day1[1].getText().toString(),
                            day1[2].getText().toString(),
                            day1[3].getText().toString(),
                            day1[4].getText().toString()));
                }
                if(dayRec2!=" " && !day2[0].getText().toString().isEmpty() && !day2[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec2,
                            day2[0].getText().toString(),
                            day2[1].getText().toString(),
                            day2[2].getText().toString(),
                            day2[3].getText().toString(),
                            day2[4].getText().toString()));
                }
                if(dayRec3!=" " && !day3[0].getText().toString().isEmpty() && !day3[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec3,
                            day3[0].getText().toString(),
                            day3[1].getText().toString(),
                            day3[2].getText().toString(),
                            day3[3].getText().toString(),
                            day3[4].getText().toString()));
                }
                if(dayRec4!=" " && !day4[0].getText().toString().isEmpty() && !day4[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec4,
                            day4[0].getText().toString(),
                            day4[1].getText().toString(),
                            day4[2].getText().toString(),
                            day4[3].getText().toString(),
                            day4[4].getText().toString()));
                }
                if(dayRec5!=" " && !day5[0].getText().toString().isEmpty() && !day5[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec5,
                            day5[0].getText().toString(),
                            day5[1].getText().toString(),
                            day5[2].getText().toString(),
                            day5[3].getText().toString(),
                            day5[4].getText().toString()));
                }
                if(dayRec6!=" " && !day6[0].getText().toString().isEmpty() && !day6[2].getText().toString().isEmpty()) {
                    scheRec.add(new Schedules(0, dayRec6,
                            day6[0].getText().toString(),
                            day6[1].getText().toString(),
                            day6[2].getText().toString(),
                            day6[3].getText().toString(),
                            day6[4].getText().toString()));
                }

                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("KEY_SCHED",  scheRec);
                setResult(RESULT_OK,intent);
                finish();

                Toast.makeText(getApplicationContext(), "Record saved",
                    Toast.LENGTH_LONG).show();
            }
        });
    }
}
