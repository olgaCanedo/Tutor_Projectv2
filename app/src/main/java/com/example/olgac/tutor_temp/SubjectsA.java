package com.example.olgac.tutor_temp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.CampusSubject;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.db.AppDatabase;
import com.example.olgac.tutor_temp.views.SubjectsAdapter;
import com.example.olgac.tutor_temp.views.SubjectsFragment;

import java.util.ArrayList;
import java.util.List;

public class SubjectsA extends AppCompatActivity {

    private Context context;
    private Button btnAddSubject;
    private EditText txtSubjectName;
    private AppDatabase database;
    private List<Campus> campus;
    private List<Subjects> subjects;
    private ArrayList<CheckBox> checkBoxes;
    private LinearLayout contCampus;
    private CheckBox cb;
    private Subjects recSubject;
    private List<Campus> recCampus;
    private TextView lblTitle;
    private Boolean isForUpdate = false;
    private Intent intent;
    private String idAction=null;
    private int idS;
    private RecyclerView.Adapter adapter;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        contCampus = (LinearLayout) findViewById(R.id.contenCampus);
        checkBoxes=new ArrayList<CheckBox>();
        database = AppDatabase.getInstance(context);
        campus=database.campusModel().findAllCampusSync();
        context = this;

        //Add checkbox for each campus
        for(Campus c:campus){
            cb = new CheckBox(getApplicationContext());
            cb.setText(c.getNameC());
            cb.setId(c.getIDCampus());
            cb.setTextColor(Color.BLACK);
            cb.setTextSize(12);
            contCampus.addView(cb);
            checkBoxes.add(cb);
        }

        btnAddSubject = (Button) findViewById(R.id.btnAddSubject);
        txtSubjectName = (EditText) findViewById(R.id.txtSubjectName);
        lblTitle= (TextView) findViewById(R.id.lblRegSubjects);

        intent = this.getIntent();
        idAction = intent.getStringExtra("KEY_ACTION");
        idS = intent.getIntExtra("KEY_SUBJECTID",0);

        if(!(idAction==null))
        {
            lblTitle.setText("Update Lab");
            btnAddSubject.setText("Save Lab");
            recSubject = database.subjectModel().loadSubjectsByID(idS);
            txtSubjectName.setText(recSubject.getNameS());

            //Load campus by subject, and add mark
            recCampus=database.subjectModel().loadCampusBySubjectID(idS);
            for(Campus nameCampus:recCampus)
                for (CheckBox cbMark : checkBoxes)
                    if (cbMark.getText().equals(nameCampus.getNameC()))
                        cbMark.setChecked(true);
            isForUpdate = true;
        }


        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtSubjectName.getText().toString().isEmpty()) {
                    if (isForUpdate) {
                        recSubject.setNameS(txtSubjectName.getText().toString());
                        database.subjectModel().updateSubjects(recSubject);
                        Toast.makeText(context, "Record updated",
                                Toast.LENGTH_LONG).show();
                        database.campusSubjectModel().deleteAllBySubjectID(recSubject.getIDSubject());
                        for (CheckBox cbMark : checkBoxes) {
                            if (cbMark.isChecked())
                                database.subjectModel().insertCampusSubjects(new CampusSubject(cbMark.getId(), recSubject.getIDSubject()));
                        }

                    } else {

                        database.subjectModel().insertSubjects(
                                new Subjects(String.valueOf(txtSubjectName.getText())));
                        subjects = database.subjectModel().findAllSubjectsSync();
                        recSubject = subjects.get(subjects.size() - 1);
                        Toast.makeText(getApplicationContext(), "Record Saved ",
                                Toast.LENGTH_LONG).show();

                        for (CheckBox cbMark : checkBoxes) {
                            if (cbMark.isChecked())
                                database.subjectModel().insertCampusSubjects(
                                        new CampusSubject(cbMark.getId(), recSubject.getIDSubject()));
                        }
                    }
                    subjects = database.subjectModel().findAllSubjectsSync();
                    adapter = new SubjectsAdapter(subjects);
                    adapter.notifyDataSetChanged();
                    SubjectsFragment.recyclerView.setAdapter(adapter);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Please complete information!",
                            Toast.LENGTH_LONG).show();
            }
        });
    }
}
