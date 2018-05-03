package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.Schedules;
import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.TutorsSkill;
import com.example.olgac.tutor_temp.model.db.AppDatabase;
import com.example.olgac.tutor_temp.views.TutorAdapter;
import com.example.olgac.tutor_temp.views.TutorsFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddUpdateTutor extends AppCompatActivity{
    private Context context;
    private ImageView imgPicture;
    private List<Campus> campuses;
    private AppDatabase database;
    private int campusID = -1;
    private int subjectID = -1;
    private Spinner spSubject;
    private List<Subjects> subjects;
    private List<String> subjectsNames;
    private String[] skillNames;
    private Integer[] skillID;
    private List<Integer> mSelectedItems;
    private List<Skills> skills;
    private List<Tutor> tutors;
    private Tutor recTutor;
    private Tutor recTutorU;
    private ArrayAdapter<String> dataAdapterS;
    private ArrayList<Schedules> scheTutor;
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    public static RecyclerView.Adapter adapter;
    public Intent intent;
    private String idAction=null;
    private long idT;
    private Boolean isForUpdate = false;
    private Campus campusSpi;
    private Subjects subjectSpi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;

        database = AppDatabase.getInstance(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_tutor);

        Button btnAddTutor = (Button) findViewById(R.id.btnAddTutor);
        Button btnSkills = (Button) findViewById(R.id.btnSkills);
        Button btnSchedule = (Button) findViewById(R.id.btnSchedule);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        final EditText txtFirstNameT = (EditText) findViewById(R.id.txtFirstNameT);
        final EditText txtLastNameT = (EditText) findViewById(R.id.txtLastNameT);
        final EditText txtEmailT = (EditText) findViewById(R.id.txtEmailT);
        final EditText txtPhoneT = (EditText) findViewById(R.id.txtPhoneT);


        //TODO: Campus Spinner
        Spinner spCampus = (Spinner) findViewById(R.id.spCampus);
        spSubject = (Spinner) findViewById(R.id.spSubject);

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

                //TODO: Subjects Spinner
                subjects = database.subjectModel().loadSubjectsByCampusID(campusID);
                subjectsNames = new ArrayList<>();
                for(Subjects s: subjects){
                    subjectsNames.add(s.getNameS());
                }
                dataAdapterS = new ArrayAdapter<String>(getApplicationContext(),
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //TODO Code for update operation
        intent = this.getIntent();
        idAction = intent.getStringExtra("KEY_ACTION");
        idT = intent.getLongExtra("KEY_TUTORID",0);

        if(!(idAction==null))
        {
            btnAddTutor.setText("Save Tutor");
            recTutorU = database.tutorModel().loadTutorByTutorID(idT);
            txtFirstNameT.setText(recTutorU.getFirstName());
            txtLastNameT.setText(recTutorU.getLastName());
            txtEmailT.setText(recTutorU.getEmail());
            txtPhoneT.setText(recTutorU.getPhone());
            //Load Picture
            byte[] image = recTutorU.getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            imgPicture.setImageBitmap(bitmap);

            //Load Spinner Campus
            campusSpi = database.campusModel().loadCampusByID(recTutorU.getCampusID());
            for (int i = 0; i < spCampus.getAdapter().getCount(); i++) {
                if (spCampus.getAdapter().getItem(i).toString().equals( campusSpi.getNameC())){
                    spCampus.setSelection(i);
                    campusID = campusSpi.getIDCampus();
                    break;
                }
            }
            spCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //TODO: Subjects Spinner for Update
                    subjects = database.subjectModel().loadSubjectsByCampusID(campusID);
                    subjectsNames = new ArrayList<>();
                    for(Subjects s: subjects){
                        subjectsNames.add(s.getNameS());
                    }
                    dataAdapterS = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, subjectsNames);
                    dataAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spSubject.setAdapter(dataAdapterS);

                    //Load Spinner Subjects
                    subjectSpi = database.subjectModel().loadSubjectsByID(recTutorU.getSubjectID());
                    for (int i = 0; i < spSubject.getAdapter().getCount(); i++) {
                        if (spSubject.getAdapter().getItem(i).toString().equals( subjectSpi.getNameS())){
                            spSubject.setSelection(i);
                            subjectID = subjectSpi.getIDSubject();
                            break;
                        }
                    }
                    spSubject.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            subjectID = subjects.get(position).getIDSubject();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            isForUpdate = true;
        }


        // TODO Add a new Tutor
        btnAddTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(campusID != -1)
                    if(subjectID != -1)
                        if (mSelectedItems!= null && scheTutor!=null
                                && !txtFirstNameT.getText().toString().isEmpty()) {

                            if (isForUpdate) {
                                recTutorU.setFirstName(txtFirstNameT.getText().toString());
                                recTutorU.setLastName(txtLastNameT.getText().toString());
                                recTutorU.setEmail(txtEmailT.getText().toString());
                                recTutorU.setPhone(txtPhoneT.getText().toString());
                                recTutorU.setCampusID(campusID);
                                recTutorU.setSubjectID(subjectID);
                                recTutorU.setPicture(imageViewToByte(imgPicture));
                                database.tutorModel().updateTutor(recTutorU);

                                database.tutorsSkillModel().deleteTutorWithSkill(recTutorU.getTutorId());

                                //TODO Insert tutors - skills
                                for (int pos = 0; pos < mSelectedItems.size(); pos++) {
                                    database.tutorsSkillModel().insertTutorSkills(
                                            new TutorsSkill(recTutorU.getTutorId(), skillID[pos]));
                                }
                                //TODO Insert tutors - schedules
                                database.scheduleModel().deleteTutorWithSched(recTutorU.getTutorId());

                                for (int i = 0; i < scheTutor.size(); i++)
                                    database.scheduleModel().insertSchedules(new Schedules(
                                            recTutorU.getTutorId(), scheTutor.get(i).getDay(),
                                            scheTutor.get(i).getHourStart(), scheTutor.get(i).getMinStart(),
                                            scheTutor.get(i).getHourEnd(), scheTutor.get(i).getMinEnd(),
                                            scheTutor.get(i).getRoomNumber()));

                                Toast.makeText(context, "Record updated",
                                        Toast.LENGTH_LONG).show();

                            }else {

                                //TODO Insert tutors
                                database.tutorModel().insertTutor(new Tutor(
                                        txtFirstNameT.getText().toString(),
                                        txtLastNameT.getText().toString(),
                                        txtEmailT.getText().toString(),
                                        txtPhoneT.getText().toString(),
                                        campusID,
                                        subjectID, imageViewToByte(imgPicture)
                                ));

                                //TODO Insert tutors - skills
                                tutors = database.tutorModel().findAllTutorSync();
                                recTutor = tutors.get(tutors.size() - 1);
                                for (int pos = 0; pos < mSelectedItems.size(); pos++) {
                                    database.tutorsSkillModel().insertTutorSkills(
                                            new TutorsSkill(recTutor.getTutorId(), skillID[pos]));
                                }
                                //TODO Insert tutors - schedules
                                for (int i = 0; i < scheTutor.size(); i++)
                                    database.scheduleModel().insertSchedules(new Schedules(
                                            recTutor.getTutorId(), scheTutor.get(i).getDay(),
                                            scheTutor.get(i).getHourStart(), scheTutor.get(i).getMinStart(),
                                            scheTutor.get(i).getHourEnd(), scheTutor.get(i).getMinEnd(),
                                            scheTutor.get(i).getRoomNumber()));

                                Toast.makeText(getApplicationContext(), "Record saved",
                                        Toast.LENGTH_LONG).show();
                            }
                            tutors = database.tutorModel().findAllTutorSync();
                            adapter = new TutorAdapter(tutors);
                            adapter.notifyDataSetChanged();
                            TutorsFragment.recyclerView.setAdapter(adapter);
                            finish();
                        }else
                            Toast.makeText(getApplicationContext(), "Please complete information!",
                                    Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Please select a Subject!",
                                Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Please select a Campus!",
                            Toast.LENGTH_LONG).show();
            }
        });

        //TODO SKILLS
        btnSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                mSelectedItems = new ArrayList();

                skills = database.skillsModel().loadSkillsBySubjectID(subjectID);
                skillNames = new String[skills.size()];

                for(Skills s: skills){
                    skillNames[i]=s.getNameSkill();
                    i++;
                }

                new AlertDialog.Builder(context)
                        .setTitle("Pick Skills")
                        .setMultiChoiceItems(skillNames, null,
                                new DialogInterface.OnMultiChoiceClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            mSelectedItems.add(which);
                                        } else if (mSelectedItems.contains(which)) {
                                            // Else, if the item is already in the array, remove it
                                            mSelectedItems.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        skillID = new Integer[mSelectedItems.size()];

                                        for(Skills s: skills){
                                            for (int i = 0; i < mSelectedItems.size(); i++) {
                                                if(s.getNameSkill().equals(skillNames[mSelectedItems.get(i)]))
                                                    skillID[i]=s.getIDSkill();
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });

        //TODO Schedule - Tutors Button
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SchedulesA.class);
                startActivityForResult(intent,PICK_CONTACT_REQUEST);
            }
        });
    }

    private byte[] imageViewToByte(ImageView imgPicture) {
        Bitmap bitmap = ((BitmapDrawable)imgPicture.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                scheTutor = data.getParcelableArrayListExtra("KEY_SCHED");
            }
        }
        //Check for picture
        if(requestCode==10)
        {
            if (RESULT_OK==resultCode)
            try {
                Uri path= data.getData();
                InputStream inputStream = getContentResolver().openInputStream(path);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            else
                finish();
        }
    }

    public void loadPicture(View view) {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select app"), 10);
    }
}
