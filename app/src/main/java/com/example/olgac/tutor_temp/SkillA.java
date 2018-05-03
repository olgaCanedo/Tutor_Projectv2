package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.db.AppDatabase;
import com.example.olgac.tutor_temp.views.SkillsAdapter;
import com.example.olgac.tutor_temp.views.SkillsFragment;

import java.util.ArrayList;
import java.util.List;

public class SkillA extends AppCompatActivity{

    private Context context;
    private Button btnAddSkill;
    private EditText txtSkillName;
    private Spinner spSubjects;
    private TextView lblTitle;
    private Boolean isForUpdate = false;
    private Intent intent;
    private String idAction=null;
    private int idC;
    private AppDatabase database;
    private Skills skillRec;
    private List<Subjects> subjects;
    private List<String> subjectsNames;
    private Subjects subSpi;
    private ArrayAdapter<String> dataAdapterS;
    private int subjectID = -1;
    private List<Skills> skills;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        database = AppDatabase.getInstance(context);
        context = this;

        btnAddSkill = (Button) findViewById(R.id.btnAddSkill);
        txtSkillName = (EditText) findViewById(R.id.txtSkillName);
        spSubjects= (Spinner) findViewById(R.id.spSubject);
        lblTitle= (TextView) findViewById(R.id.lblRegSkill);


        //TODO: Subjects Spinner
        subjects = database.subjectModel().findAllSubjectsSync();

        subjectsNames = new ArrayList<>();
        for(Subjects s: subjects){
            subjectsNames.add(s.getNameS());
        }
        dataAdapterS = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, subjectsNames);
        dataAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubjects.setAdapter(dataAdapterS);
        spSubjects.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                subjectID = subjects.get(position).getIDSubject();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        intent = this.getIntent();
        idAction = intent.getStringExtra("KEY_ACTION");
        idC = intent.getIntExtra("KEY_SKILLID",0);

        if(!(idAction==null))
        {
            lblTitle.setText("Update Skill");
            btnAddSkill.setText("Save Skill");
            skillRec = database.skillsModel().loadSkillsByID(idC);
            txtSkillName.setText(skillRec.getNameSkill());

            subSpi = database.subjectModel().loadSubjectsByID(skillRec.getIDSubject());
            for (int i = 0; i < spSubjects.getAdapter().getCount(); i++) {
                if (spSubjects.getAdapter().getItem(i).toString().equals( subSpi.getNameS())){

                    spSubjects.setSelection(i);
                    break;
                }
            }
            isForUpdate = true;
        }


        btnAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtSkillName.getText().toString().isEmpty()) {
                    if (isForUpdate) {
                        skillRec.setNameSkill(txtSkillName.getText().toString());
                        skillRec.setIDSubject(subjectID);
                        database.skillsModel().updateSkills(skillRec);
                        Toast.makeText(context, "Record updated",
                                Toast.LENGTH_LONG).show();
                    } else {
                        database.skillsModel().insertSkills(new Skills(String.valueOf(txtSkillName.getText()), subjectID));
                        Toast.makeText(getApplicationContext(), "Record saved",
                                Toast.LENGTH_LONG).show();
                    }
                    //Refresh RecyclerView
                    skills = database.skillsModel().findAllSkillsSync();
                    adapter = new SkillsAdapter(skills);
                    adapter.notifyDataSetChanged();
                    SkillsFragment.recyclerView.setAdapter(adapter);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Please complete information!",
                            Toast.LENGTH_LONG).show();
            }
        });
    }

}
