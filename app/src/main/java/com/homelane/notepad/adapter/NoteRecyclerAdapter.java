package com.homelane.notepad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.homelane.notepad.MainActivity;
import com.homelane.notepad.R;
import com.homelane.notepad.fragments.DetailsFragment;
import com.homelane.notepad.pojo.Note;

import java.util.List;

/**
 * Created by Jayesh on 1/3/2018.
 */

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {

    private static  RecyclerView mRecyclerView;
    private AppCompatActivity mContext;
    private List<Note> noteList;

public static  class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView title, date, text;
    public IMyViewHolderClicks mListener;
    public NoteViewHolder(View view, IMyViewHolderClicks listener) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        date = (TextView) view.findViewById(R.id.date);
        text = (TextView) view.findViewById(R.id.text);
        mListener = listener;
        view.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        /*if (v instanceof ImageView){
            mListener.onBookmark(v);
        } else {
            mListener.onPotato(v, mRecyclerView.getChildAdapterPosition(v));
        }*/
        mListener.onPotato(v, mRecyclerView.getChildAdapterPosition(v));
    }

    /*@Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }*/


    public static interface IMyViewHolderClicks {
        public void onPotato(View caller, int position);
        public void onBookmark(View callerImage);
    }
}


    public NoteRecyclerAdapter(AppCompatActivity mContext, List<Note> albumList, RecyclerView rv) {
        this.mContext = mContext;
        this.noteList = albumList;
        this.mRecyclerView = rv;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        NoteViewHolder vh = new NoteViewHolder(v, new NoteViewHolder.IMyViewHolderClicks() {

            @Override
            public void onPotato(View caller, int position) {
                final FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
                Fragment fragment = DetailsFragment.newInstance(noteList.get(position).getId());
                ft.replace(R.id.content_frame, fragment, "DetailsFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }

            public void onBookmark(View callerImage) {
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        System.out.println("What is the note...."+note.toString());
        holder.date.setText(((MainActivity)mContext).convertDateToString(note.getDateTime()));
        holder.text.setText(note.getText());

        /*holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });*/
    }




    @Override
    public int getItemCount() {
        return noteList.size();
    }


}