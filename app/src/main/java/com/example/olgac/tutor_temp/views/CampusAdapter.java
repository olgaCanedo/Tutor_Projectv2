package com.example.olgac.tutor_temp.views;

import android.annotation.SuppressLint;
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

import com.example.olgac.tutor_temp.CampusS;
import com.example.olgac.tutor_temp.R;
import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.CampusSubject;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 4/5/2018.
 */

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.ViewHolder>
{
    private List<Campus> campus;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private List<CampusSubject> listCamSub;

    public CampusAdapter(List<Campus> campus) {
        this.campus = campus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campus,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Campus clsCampus = campus.get(position);
        if (clsCampus.getNameC() != null) {
            holder.name.setText(clsCampus.getNameC());
        } else {
            holder.name.setText("No Name");
        }

        if (clsCampus.getPhoneC() != null) {
            holder.phone.setText(clsCampus.getPhoneC());
        } else {
            holder.phone.setText("No Phone");
        }

        if (clsCampus.getRoomC() != null) {
            holder.room.setText(clsCampus.getRoomC());
        } else {
            holder.room.setText("No Room");
        }

        if (clsCampus.getLocationC() != null) {
            holder.location.setText(clsCampus.getLocationC());
        } else {
            holder.location.setText("No Location");
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Update")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(v.getContext(),CampusS.class);
                        intent.putExtra("KEY_CAMPUSID",clsCampus.getIDCampus());
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
                                .setTitle("Delete Campus")
                                .setMessage("Do you want to delete: " + clsCampus.getNameC() + " ?")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db = AppDatabase.getInstance(context);
                                                listCamSub=db.campusSubjectModel().findAllCampusSync(clsCampus.getIDCampus());
                                                if (listCamSub.size()>0){

                                                    new AlertDialog.Builder(context)
                                                            .setTitle("Warning!")
                                                            .setMessage("The record cannot be deleted, it has subjects and related tutors.")
                                                            .setPositiveButton(android.R.string.ok,
                                                                    new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                        }
                                                                    })
                                                            .create().show();

                                                }else {
                                                    db.campusModel().deleteCampus(clsCampus.getIDCampus());
                                                    Toast.makeText(context, "Record deleted",
                                                            Toast.LENGTH_LONG).show();
                                                }

                                                campus = db.campusModel().findAllCampusSync();
                                                adapter = new CampusAdapter(campus);
                                                adapter.notifyDataSetChanged();
                                                CampusFragment.recyclerView.setAdapter(adapter);
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
        return campus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView room;
        public TextView location;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            phone = itemView.findViewById(R.id.list_phone);
            room = itemView.findViewById(R.id.list_room);
            location = itemView.findViewById(R.id.list_loc);
        }
    }
}