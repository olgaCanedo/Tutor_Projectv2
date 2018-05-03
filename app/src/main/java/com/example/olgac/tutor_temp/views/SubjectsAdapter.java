package com.example.olgac.tutor_temp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olgac.tutor_temp.R;
import com.example.olgac.tutor_temp.SubjectsA;
import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 4/6/2018.
 */

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder>{
    private List<Subjects> subjects;
    private List<Campus> subjectCampus;
    private List<Tutor> listSubTutor;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private String namesCampus="";

    public SubjectsAdapter(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    @Override
    public SubjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject,parent,false);
        db = AppDatabase.getInstance(context);
        return new SubjectsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Subjects clsSubjects = subjects.get(position);

        if (clsSubjects.getNameS() != null) {
            namesCampus="";
            subjectCampus= db.subjectModel().loadCampusBySubjectID(clsSubjects.getIDSubject());
            for(Campus nameCampus:subjectCampus)
            {
                namesCampus = namesCampus + " - " + nameCampus.getNameC();
            }
            holder.name.setText(clsSubjects.getNameS());
            holder.campuSubject.setText(namesCampus);
        } else {
            holder.name.setText("No Name");
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Update")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(v.getContext(),SubjectsA.class);
                        intent.putExtra("KEY_SUBJECTID",clsSubjects.getIDSubject());
                        intent.putExtra("KEY_ACTION","Update");
                        v.getContext().startActivity(intent);
                        return false;
                    }
                });
                menu.add(0, v.getId(), 0, "Delete")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        context = v.getContext();

                        new AlertDialog.Builder(context)
                                .setTitle("Delete Subject")
                                .setMessage("Do you want to delete: " + clsSubjects.getNameS() + " ?")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                listSubTutor=db.tutorModel().findTutorBySubject(clsSubjects.getIDSubject());
                                                if (listSubTutor.size()>0){

                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Warning!")
                                                            .setMessage("The record cannot be deleted, it has tutors related.")
                                                            .setPositiveButton(android.R.string.ok,
                                                                    new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                        }
                                                                    })
                                                            .create().show();

                                                }else {
                                                    db.campusSubjectModel().deleteAllBySubjectID(clsSubjects.getIDSubject());
                                                    db.subjectModel().deleteSubject(clsSubjects.getIDSubject());

                                                    Toast.makeText(context, "Record deleted",
                                                            Toast.LENGTH_LONG).show();
                                                }
                                                    subjects = db.subjectModel().findAllSubjectsSync();
                                                    adapter = new SubjectsAdapter(subjects);
                                                    adapter.notifyDataSetChanged();
                                                    SubjectsFragment.recyclerView.setAdapter(adapter);

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
        return subjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView campuSubject;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            campuSubject = itemView.findViewById(R.id.list_campus);
        }
    }
}
