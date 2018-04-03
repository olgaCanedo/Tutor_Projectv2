package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import OpenHelper.SQLite_OpenHelper;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences sharedPrefer;
    private TextView txtRegister;
    private Button btnLogIn;
    private EditText txtUserSign;
    private EditText txtPasswordS;
    private String userSF;
    private String passwordSF;
    private AppDatabase db;

    SQLite_OpenHelper helper = new
            SQLite_OpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        sharedPrefer = getSharedPreferences("UserPrefer", Context.MODE_PRIVATE);
        //Reading the username from the shared preferences
        String  savedUserName = sharedPrefer.getString("userName", null);
        String  savedPassword = sharedPrefer.getString("password", null);

        txtUserSign = (EditText) findViewById(R.id.txtNameSign);
        txtPasswordS = (EditText) findViewById(R.id.txtPasswordSign);
        txtUserSign.setText(savedUserName);
        txtPasswordS.setText(savedPassword);


        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor myEditor = sharedPrefer.edit();
                myEditor.putString("userName","");
                myEditor.putString("password","");
                myEditor.apply();

                Intent intent = new Intent(getApplicationContext(), Users.class);
                startActivity(intent);
            }
        });


        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    userSF= txtUserSign.getText().toString();
                    passwordSF= txtPasswordS.getText().toString();

                    SharedPreferences.Editor myEditor = sharedPrefer.edit();
                    myEditor.putString("userName",userSF);
                    myEditor.putString("password",passwordSF);
                    myEditor.apply();


                    db = AppDatabase.getInstance(context);
                    User byName = db.userModel().findByName(userSF);


                    if (byName != null) {
                        Intent intent = new Intent(getApplicationContext(), TutorsManagement.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "User does not exist",
                                Toast.LENGTH_LONG).show();
                    }
                    txtUserSign.setText("");
                    txtPasswordS.setText("");
                    txtUserSign.findFocus();
                }catch (SQLException e){
                    e.printStackTrace();

                }
            }
        });
    }
}
