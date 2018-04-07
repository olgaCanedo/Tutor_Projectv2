package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

/**
 * Created by olgac on 4/6/2018.
 */

public class CampusS extends AppCompatActivity {

    Context context;
    Button btnAddCampus;
    EditText txtCampusName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus);

        context = this;

        btnAddCampus = (Button) findViewById(R.id.btnAddCampus);
        txtCampusName = (EditText) findViewById(R.id.txtCampusName);

        btnAddCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase database = AppDatabase.getInstance(context);
                database.campusModel().insertCampus(new Campus(String.valueOf(txtCampusName.getText())));
                Toast.makeText(getApplicationContext(), "Record saved",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),TutorsManagement.class);
                startActivity(intent);
            }
        });
    }
}

