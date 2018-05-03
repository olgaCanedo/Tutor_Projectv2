package com.example.olgac.tutor_temp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

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
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            position = savedInstanceState.getInt("Position");
        }

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

        //displays a video file
        //getWindow().setFormat(PixelFormat.UNKNOWN);
        videoView = (VideoView) findViewById(R.id.videoView);

        String uriPath = "android.resource://com.example.olgac.tutor_temp/"+R.raw.mdc;

        if (mediaController == null) {
            mediaController = new MediaController(this);
        }
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(uriPath));
        videoView.requestFocus();
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    //videoView.pause();
                    videoView.seekTo(position);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", videoView.getCurrentPosition());
        videoView.pause();
    }
}
