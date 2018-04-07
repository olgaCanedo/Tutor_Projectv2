package com.example.olgac.tutor_temp.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.olgac.tutor_temp.R;
import com.example.olgac.tutor_temp.model.Tutor;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 4/4/2018.
 */

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.ViewHolder>
{
    List<Tutor> tutors;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private static long tutorID;

    public TutorAdapter(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(tutors.get(position).getFirstName()+ " " +
                            tutors.get(position).getLastName());
        holder.email.setText(tutors.get(position).getEmail());
        holder.phone.setText(tutors.get(position).getPhone());
        //Get ID of tutor
        tutorID = tutors.get(position).getTutorId();
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView email;
        public TextView phone;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            email= itemView.findViewById(R.id.list_email);
            phone= itemView.findViewById(R.id.list_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context = v.getContext();

                    new AlertDialog.Builder(context)
                            .setTitle("Delete User")
                            .setMessage("Do you want to delete: " + name.getText().toString()
                                    + " , " + email.getText().toString() + " ?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db = AppDatabase.getInstance(context);
                                    db.tutorModel().deleteTutor(tutorID);

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
                }
            });
        }
    }
}