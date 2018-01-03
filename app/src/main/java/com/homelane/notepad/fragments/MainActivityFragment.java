package com.homelane.notepad.fragments;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.homelane.notepad.R;
import com.homelane.notepad.adapter.NoteRecyclerAdapter;
import com.homelane.notepad.database.DatabaseAccess;
import com.homelane.notepad.pojo.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    DatabaseAccess db;
    private RecyclerView mRecyclerView;
    List<Note> noteList;
    private Toolbar toolbar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.meetings_list);
        mRecyclerView.setHasFixedSize(true);
        Context mContext = getContext();
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        db = DatabaseAccess.getInstance(getActivity());
        db.open();
        noteList = db.getNoteList();
        db.close();
        NoteRecyclerAdapter mAdapter = new NoteRecyclerAdapter((AppCompatActivity) getActivity(), noteList,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLayoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        /*toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(mContext,R.array.filter, R.layout.spinner_dropdown_item);
        Spinner navigationSpinner = new Spinner(getActivity());
        navigationSpinner.setAdapter(spinnerAdapter);
        navigationSpinner.setGravity(Gravity.RIGHT);
        toolbar.addView(navigationSpinner, 0);

        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        return view;
    }
}
