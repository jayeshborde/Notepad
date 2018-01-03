package com.homelane.notepad.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.homelane.notepad.MainActivity;
import com.homelane.notepad.R;
import com.homelane.notepad.database.DatabaseAccess;
import com.homelane.notepad.pojo.Note;

import com.homelane.notepad.utils.CustomImageView;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment.OnDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "note_id";

    // TODO: Rename and change types of parameters
    private int mParam1;

    private OnDetailsFragmentInteractionListener mListener;
    private TextView titleTextView;
    private TextView textTextView;
    private TextView dateTextView;
    private CustomImageView noteImageView;
    private Note note;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(int param1) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        titleTextView = (TextView) view.findViewById(R.id.title);
        textTextView = (TextView) view.findViewById(R.id.text);
        dateTextView = (TextView) view.findViewById(R.id.date);
        noteImageView = (CustomImageView) view.findViewById(R.id.note_image);

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();
        note = db.getNote(mParam1);
        titleTextView.setText(note.getTitle());
        textTextView.setText(note.getText());
        System.out.println("What is the date...."+note.toString());
        dateTextView.setText(((MainActivity)getActivity()).convertDateToString(note.getDateTime()));
        if(note.getImage()!=null){
            String pathName = Environment.getExternalStorageDirectory().toString() + File.separator + "notepad"+ File.separator +note.getImage();
            File file = new File(pathName);
            if(file.exists()){
                Bitmap bmp = BitmapFactory.decodeFile(pathName);
                noteImageView.setImageBitmap(bmp);
            }
        }
        db.close();

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDetailsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsFragmentInteractionListener) {
            mListener = (OnDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDetailsFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
            DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
            db.open();
            int delValue = db.deleteNote(mParam1);
            db.close();
            if(note.getImage()!=null){
                String pathName = Environment.getExternalStorageDirectory().toString() + File.separator + "notepad"+ File.separator +note.getImage();
                File file = new File(pathName);
                if(file.exists()){
                    file.delete();
                }
            }
            getActivity().getSupportFragmentManager().popBackStack();
            if (mListener != null) {
                mListener.onDetailsFragmentInteraction(null);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
