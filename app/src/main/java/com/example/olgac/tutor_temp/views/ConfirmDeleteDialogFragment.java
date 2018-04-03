package com.example.olgac.tutor_temp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.olgac.tutor_temp.model.User;


public class ConfirmDeleteDialogFragment extends DialogFragment {
    private static final String userData = "user";
    private User user;
    private OnDeleteConfirmedListener mListener;

    public ConfirmDeleteDialogFragment() {
        // Required empty public constructor
    }

    public static ConfirmDeleteDialogFragment newInstance() {
        ConfirmDeleteDialogFragment fragment = new ConfirmDeleteDialogFragment();
        //Bundle args = new Bundle();
        //args.putParcelable(userData, ap);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(userData);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment galleryFragment = this.getParentFragment();

        if (galleryFragment instanceof OnDeleteConfirmedListener) {
            mListener = (OnDeleteConfirmedListener) galleryFragment;
        } else {
            throw new RuntimeException(galleryFragment.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // set dialog icon
                .setIcon(android.R.drawable.btn_dialog)
                // set Dialog Title
                .setTitle("Important!")
                // Set Dialog Message
                .setMessage("Do you really want to remove this user?")

                // positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                           // mListener.onDeleteConfirmed(user);
                        }
                        dialog.dismiss();
                    }
                })
                // negative button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnDeleteConfirmedListener {
        void onDeleteConfirmed(User user);
    }
}