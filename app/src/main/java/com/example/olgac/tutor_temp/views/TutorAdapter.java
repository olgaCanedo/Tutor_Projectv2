package com.example.olgac.tutor_temp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.AddUpdateTutor;
import com.example.olgac.tutor_temp.R;
import com.example.olgac.tutor_temp.model.Schedules;
import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 4/4/2018.
 */

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.ViewHolder>
{
    private List<Tutor> tutors;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private long tutorID;
    private String namesSkills="";
    private List<Skills> tutorSkills;
    private List<Schedules> tutorSched;
    private String tutorSchedule;

    public TutorAdapter(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor,parent,false);
        db = AppDatabase.getInstance(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tutor clsTutors = tutors.get(position);
        tutorID= clsTutors.getTutorId();
        //Show image tutor
        byte[] picture = clsTutors.getPicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture,0,picture.length);
        holder.picImage.setImageBitmap(bitmap);

        if (clsTutors.getFirstName() != null && clsTutors.getLastName() != null) {
            holder.name.setText(clsTutors.getFirstName()+ " "+clsTutors.getLastName());
        } else {
            holder.name.setText("No Name");
        }

        if (clsTutors.getEmail() != null) {
            holder.email.setText(clsTutors.getEmail());
        } else {
            holder.email.setText("No Email");
        }
        if (clsTutors.getPhone() != null) {
            holder.phone.setText(clsTutors.getPhone());
        } else {
            holder.phone.setText("No Phone");
        }

        //Skills
        namesSkills="";
        tutorSkills= db.tutorModel().getSkillsByTutorID(clsTutors.getTutorId());
        for(Skills nameSkill: tutorSkills)
        {
            namesSkills = namesSkills + " - " + nameSkill.getNameSkill();
        }
        holder.skillTutor.setText(namesSkills);

        //Schedule
        tutorSchedule="";
        tutorSched= db.scheduleModel().loadScheduleByIDTutor(clsTutors.getTutorId());
        for(Schedules scheduleData: tutorSched)
        {

            tutorSchedule = tutorSchedule + " " + scheduleData.getDay() + " " +
                    scheduleData.getHourStart() + ":" + scheduleData.getMinStart()+ "-" +
                    scheduleData.getHourEnd() + ":" + scheduleData.getMinEnd();
        }
        holder.txtTSchedule.setText(tutorSchedule);

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Update").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(v.getContext(),AddUpdateTutor.class);
                        intent.putExtra("KEY_TUTORID",clsTutors.getTutorId());
                        intent.putExtra("KEY_ACTION","Update");
                        v.getContext().startActivity(intent);
                        return false;
                    }
                });
                menu.add(0, v.getId(), 0, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        context = v.getContext();

                        new AlertDialog.Builder(context)
                                .setTitle("Delete Tutor")
                                .setMessage("Do you want to delete: " + clsTutors.getFirstName() +
                                        " " + clsTutors.getLastName() + " ?")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db.tutorModel().deleteTutor(clsTutors.getTutorId());
                                                db.tutorsSkillModel().deleteTutorWithSkill(clsTutors.getTutorId());
                                                db.scheduleModel().deleteTutorWithSched(clsTutors.getTutorId());
                                                Toast.makeText(context, "Record deleted",
                                                        Toast.LENGTH_LONG).show();

                                                //Reload recyclerView without tutor deleted
                                                tutors = db.tutorModel().findAllTutorSync();
                                                adapter = new TutorAdapter(tutors);
                                                adapter.notifyDataSetChanged();
                                                TutorsFragment.recyclerView.setAdapter(adapter);
                                            }
                                        })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView email;
        public TextView phone;
        public TextView skillTutor;
        public TextView txtTSchedule;
        public ImageView picImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            email= itemView.findViewById(R.id.list_email);
            phone= itemView.findViewById(R.id.list_phone);
            skillTutor = itemView.findViewById(R.id.list_skills);
            txtTSchedule = itemView.findViewById(R.id.list_schedule);
            picImage = itemView.findViewById(R.id.imgPicRecycler);
        }
    }
}