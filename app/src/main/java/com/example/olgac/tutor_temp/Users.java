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

import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

public class Users extends AppCompatActivity {

    private Context context;
    private Button btnAddUser;
    private EditText txtUserName, txtUserPassword;
    private TextView lblTitle;
    private Boolean isForUpdate = false;
    private Intent intent;
    private String idAction=null;
    private int idU;
    private AppDatabase database;
    private User userRec;
    private List<User> users;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        context = this;
        database = AppDatabase.getInstance(context);

        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtUserPassword = (EditText) findViewById(R.id.txtUserPassword);
        lblTitle= (TextView) findViewById(R.id.lblRegUser);

        intent = this.getIntent();
        idAction = intent.getStringExtra("KEY_ACTION");
        idU = intent.getIntExtra("KEY_USERID",0);

        if(!(idAction==null))
        {
            lblTitle.setText("Update User");
            btnAddUser.setText("Save User");
            userRec = database.userModel().loadUserByID(idU);
            txtUserName.setText(userRec.getNameU());
            txtUserPassword.setText(userRec.getPassword());
            isForUpdate = true;
        }

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtUserName.getText().toString().isEmpty()&&
                        !txtUserPassword.getText().toString().isEmpty()   ) {
                    if (isForUpdate) {
                        userRec.setNameU(txtUserName.getText().toString());
                        userRec.setPassword(txtUserPassword.getText().toString());
                        database.userModel().updateUser(userRec);
                        Toast.makeText(context, "Record updated",
                                Toast.LENGTH_LONG).show();
                    } else {
                        database.userModel().insertUser(new User(String.valueOf(txtUserName.getText()),
                                String.valueOf(txtUserPassword.getText())));
                        Toast.makeText(getApplicationContext(), "Record saved",
                                Toast.LENGTH_LONG).show();
                    }
                /*users = database.userModel().findAllUserSync();
                adapter = new UserAdapter(users);
                adapter.notifyDataSetChanged();
                UsersFragment.recyclerView.setAdapter(adapter);
                finish();*/
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), "Please complete information!",
                            Toast.LENGTH_LONG).show();
            }
        });
    }
}
