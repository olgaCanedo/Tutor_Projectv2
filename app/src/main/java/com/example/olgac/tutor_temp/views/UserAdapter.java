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
import com.example.olgac.tutor_temp.Users;
import com.example.olgac.tutor_temp.model.User;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 3/29/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private List<User> users;
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
        final User clsUsers = users.get(position);
        if (clsUsers.getNameU() != null) {
            holder.name.setText(clsUsers.getNameU());
        } else {
            holder.name.setText("No Name");
        }
        if (clsUsers.getPassword()!= null) {
            holder.password.setText(clsUsers.getPassword());
        } else {
            holder.password.setText("No Password");
        }

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Update").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(v.getContext(), Users.class);
                        intent.putExtra("KEY_USERID",clsUsers.getIDUser());
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
                                .setTitle("Delete User")
                                .setMessage("Do you want to delete: " + clsUsers.getNameU() + " ?")
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                db = AppDatabase.getInstance(context);
                                                db.userModel().deleteUser(clsUsers.getIDUser());
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
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView password;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_title);
            password = itemView.findViewById(R.id.list_desc);
        }
    }
}