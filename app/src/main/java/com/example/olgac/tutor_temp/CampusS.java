package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.db.AppDatabase;
import com.example.olgac.tutor_temp.views.CampusAdapter;
import com.example.olgac.tutor_temp.views.CampusFragment;

import java.util.List;

/**
 * Created by olgac on 4/6/2018.
 */

public class CampusS extends AppCompatActivity {

    private Context context;
    private Button btnAddCampus;
    private EditText txtCampusName;
    private EditText txtCampusPhone;
    private EditText txtCampusRoom;
    private EditText txtCampusLoc;
    private TextView lblTitle;
    private Boolean isForUpdate = false;
    private Intent intent;
    private String idAction=null;
    private int idC;
    private AppDatabase database;
    private Campus campusRec;
    private List<Campus> campus;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);
        database = AppDatabase.getInstance(context);
        context = this;

        btnAddCampus = (Button) findViewById(R.id.btnAddCampus);
        txtCampusName = (EditText) findViewById(R.id.txtCampusName);
        txtCampusPhone = (EditText) findViewById(R.id.txtCampustPhone);
        txtCampusRoom = (EditText) findViewById(R.id.txtCampusRoom);
        txtCampusLoc = (EditText) findViewById(R.id.txtCampusLoc);
        lblTitle= (TextView) findViewById(R.id.lblRegCampus);

        intent = this.getIntent();
        idAction = intent.getStringExtra("KEY_ACTION");
        idC = intent.getIntExtra("KEY_CAMPUSID",0);

        if(!(idAction==null))
        {
            lblTitle.setText("Update Campus");
            btnAddCampus.setText("Save Campus");
            campusRec = database.campusModel().loadCampusByID(idC);
            txtCampusName.setText(campusRec.getNameC());
            txtCampusPhone.setText(campusRec.getPhoneC());
            txtCampusRoom.setText(campusRec.getRoomC());
            txtCampusLoc.setText(campusRec.getLocationC());
            isForUpdate = true;
        }

        btnAddCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtCampusName.getText().toString().isEmpty()) {
                    if (isForUpdate) {
                        campusRec.setNameC(txtCampusName.getText().toString());
                        campusRec.setPhoneC(txtCampusPhone.getText().toString());
                        campusRec.setRoomC(txtCampusRoom.getText().toString());
                        campusRec.setLocationC(txtCampusLoc.getText().toString());
                        database.campusModel().updateCampus(campusRec);
                        Toast.makeText(context, "Record updated",
                                Toast.LENGTH_LONG).show();
                    } else {
                        database.campusModel().insertCampus(
                                new Campus(String.valueOf(txtCampusName.getText()),
                                        String.valueOf(txtCampusPhone.getText()),
                                        String.valueOf(txtCampusRoom.getText()),
                                        String.valueOf(txtCampusLoc.getText())));
                        Toast.makeText(getApplicationContext(), "Record saved",
                                Toast.LENGTH_LONG).show();
                    }
                    campus = database.campusModel().findAllCampusSync();
                    adapter = new CampusAdapter(campus);
                    adapter.notifyDataSetChanged();
                    CampusFragment.recyclerView.setAdapter(adapter);
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "Please complete information!",
                            Toast.LENGTH_LONG).show();
            }
        });
    }
}

