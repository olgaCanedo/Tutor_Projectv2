package com.example.olgac.tutor_temp.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olgac.tutor_temp.R;
import com.example.olgac.tutor_temp.model.Campus;
import com.example.olgac.tutor_temp.model.db.AppDatabase;

import java.util.List;

/**
 * Created by olgac on 3/29/2018.
 */


public class CampusFragment extends Fragment{
    public static RecyclerView recyclerView;
    public static RecyclerView.Adapter adapter;
    private List<Campus> campus;
    private AppDatabase db;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = this.getContext();

        db = AppDatabase.getInstance(context);
        campus = db.campusModel().findAllCampusSync();
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CampusAdapter(campus);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return recyclerView;
    }
}