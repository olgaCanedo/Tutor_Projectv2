package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import OpenHelper.SQLite_OpenHelper;

public class Users extends AppCompatActivity {
    public static final String INDEX_TAB = "index_tab";
    public static final String EXTRA_POSITION = "position";
    Context context;
    Button btnAddUser;
    EditText txtUserName, txtUserPassword;

    SQLite_OpenHelper helper = new
            SQLite_OpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        context = this;

        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserPassword = (EditText) findViewById(R.id.txtUserPassword);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase database = AppDatabase.getInstance(context);
                database.userModel().insertUser(new User(String.valueOf(txtUserName.getText()),
                        String.valueOf(txtUserPassword.getText())));
                //database.close();
                Toast.makeText(getApplicationContext(), "Record saved",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
