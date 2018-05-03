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
import com.example.olgac.tutor_temp.SkillA;
import com.example.olgac.tutor_temp.model.Skills;
import com.example.olgac.tutor_temp.model.Subjects;
import com.example.olgac.tutor_temp.model.TutorsSkill;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>
{

    private List<Skills> skills;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private Subjects subject;
    private List<TutorsSkill> listTutorSkill;


    public SkillsAdapter(List<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public SkillsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill,parent,false);
        db = AppDatabase.getInstance(context);
        return new SkillsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Skills clsSkills = skills.get(position);
        if (clsSkills.getNameSkill() != null) {
            holder.name.setText(clsSkills.getNameSkill());
        } else {
            holder.name.setText("No Name");
        }
        subject = db.subjectModel().loadSubjectsByID(clsSkills.getIDSubject());
        if (clsSkills.getIDSubject()>0) {
            holder.skillSub.setText(subject.getNameS());
        } else {
            holder.skillSub.setText("No Subject");
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Update")
                        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                Intent intent = new Intent(v.getContext(),SkillA.class);
                                intent.putExtra("KEY_SKILLID",clsSkills.getIDSkill());
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
                                        .setTitle("Delete Skill")
                                        .setMessage("Do you want to delete: " + clsSkills.getNameSkill() + " ?")
                                        .setPositiveButton(android.R.string.ok,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        db = AppDatabase.getInstance(context);
                                                        listTutorSkill=db.tutorsSkillModel().findAllTutorsBySkillSync(clsSkills.getIDSkill());
                                                        if (listTutorSkill.size()>0){

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
                                                            db.skillsModel().deleteSkill(clsSkills.getIDSkill());
                                                            Toast.makeText(context, "Record deleted",
                                                                    Toast.LENGTH_LONG).show();
                                                        }

                                                        skills = db.skillsModel().findAllSkillsSync();
                                                        adapter = new SkillsAdapter(skills);
                                                        adapter.notifyDataSetChanged();
                                                        SkillsFragment.recyclerView.setAdapter(adapter);
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
        return skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView skillSub;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            skillSub = itemView.findViewById(R.id.list_subject);
        }
    }

}
