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
import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 4/5/2018.
 */

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.ViewHolder>
{
    List<Campus> campus;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;
    private int campusID;

    public CampusAdapter(List<Campus> campus) {
        this.campus = campus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campus,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.campID.setText(campus.get(position).getIDCampus());
        holder.name.setText(campus.get(position).getNameC());
        //Get ID of campus
        campusID = campus.get(position).getIDCampus();
    }

    @Override
    public int getItemCount() {
        return campus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView campID;

        public ViewHolder(final View itemView) {
            super(itemView);
            //campID = itemView.findViewById(R.id.list_ID);
            name = itemView.findViewById(R.id.list_title);
            //campID.setText(campusID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context = v.getContext();

                    new AlertDialog.Builder(context)
                            .setTitle("Delete Campus")
                            .setMessage("Do you want to delete: " + name.getText().toString() + " ?")
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            db = AppDatabase.getInstance(context);
                                            db.campusModel().deleteCampus(campusID);
                                            Toast.makeText(context, "Record deleted",
                                                    Toast.LENGTH_LONG).show();

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
                }
            });
        }
    }
}