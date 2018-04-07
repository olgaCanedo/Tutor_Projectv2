package com.example.olgac.tutor_temp;

import android.content.Context;
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

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddUpdateTutor extends AppCompatActivity  {
    private Context context;
    private List<Campus> campuses;
    private AppDatabase database;
    private int campusID = -1;
    private int subjectID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        Toast.makeText(getApplicationContext(), "In AddUpdateTutor",
                Toast.LENGTH_LONG).show();

        database = AppDatabase.getInstance(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_tutor);

        Button btnAddTutor = (Button) findViewById(R.id.btnAddTutor);
        final EditText txtFirstNameT = (EditText) findViewById(R.id.txtFirstNameT);
        final EditText txtLastNameT = (EditText) findViewById(R.id.txtLastNameT);
        final EditText txtEmailT = (EditText) findViewById(R.id.txtEmailT);
        final EditText txtPhoneT = (EditText) findViewById(R.id.txtPhoneT);

        Spinner spCampus = (Spinner) findViewById(R.id.spCampus);
        //database.campusModel().insertCampus(new Campus("Hialeah"));
        //database.campusModel().insertCampus(new Campus("North"));

        campuses = database.campusModel().findAllCampusSync();
        List<String> campusNames = new ArrayList<String>();
        for(Campus c: campuses){
            campusNames.add(c.getNameC());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, campusNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCampus.setAdapter(dataAdapter);
        spCampus.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                campusID = campuses.get(position).getIDCampus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //TODO code for Subject spinner

        Spinner spSubject = (Spinner) findViewById(R.id.spSubject);
        //database.subjectModel().insertSubjects(new Subjects("Entec"));
        //database.subjectModel().insertSubjects(new Subjects("Math"));

        final List<Subjects> subjects = database.subjectModel().findAllSubjectsSync();
        List<String> subjectsNames = new ArrayList<>();
        for(Subjects s: subjects){
            subjectsNames.add(s.getNameS());
        }
        ArrayAdapter<String> dataAdapterS = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectsNames);
        dataAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubject.setAdapter(dataAdapterS);
        spSubject.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                subjectID = subjects.get(position).getIDSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(campusID != -1)
                    if(subjectID != -1) {
                        database.tutorModel().insertTutor(new Tutor(
                                txtFirstNameT.getText().toString(),
                                txtLastNameT.getText().toString(),
                                txtEmailT.getText().toString(),
                                txtPhoneT.getText().toString(),
                                campusID,
                                subjectID
                        ));
                        Toast.makeText(getApplicationContext(), "Record saved",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),TutorsManagement.class);
                        startActivity(intent);

                    }else
                        Toast.makeText(getApplicationContext(), "Please select a Subject!",
                                Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Please select a Campus!",
                            Toast.LENGTH_LONG).show();
            }
        });
    }


}
