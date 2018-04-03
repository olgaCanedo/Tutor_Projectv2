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
import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 3/29/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    List<User> users;
    private static Context context;
    public static RecyclerView.Adapter adapter;
    private AppDatabase db;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.name.setText(users.get(position).getNameU());
        holder.password.setText(users.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView password;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            password= itemView.findViewById(R.id.list_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context = v.getContext();

                    new AlertDialog.Builder(context)
                            .setTitle("Delete User")
                            .setMessage("Do you want to delete: " + name.getText().toString()
                            + " , " + password.getText().toString() + " ?")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db = AppDatabase.getInstance(context);
                                    db.userModel().deleteUser(name.getText().toString());
                                    //database.close();
                                    Toast.makeText(context, "Record deleted",
                                            Toast.LENGTH_LONG).show();

                                    users = db.userModel().findAllUserSync();
                                    adapter = new UserAdapter(users);

                                    adapter.notifyDataSetChanged();
                                    UsersFragment.recyclerView.setAdapter(adapter);
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