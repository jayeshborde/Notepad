package com.homelane.notepad.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.homelane.notepad.R;
import com.homelane.notepad.database.DatabaseAccess;
import com.homelane.notepad.pojo.Note;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.homelane.notepad.utils.Imageutils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNoteFragment extends Fragment implements Imageutils.ImageAttachmentListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ImageView iv_attachment;
    Imageutils imageutils;
    private EditText titleEditText;
    private EditText textEditText;
    private Bitmap bitmap;
    private String file_name = null;
    private Button saveButton;

    public NewNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewNoteFragment newInstance(String param1, String param2) {
        NewNoteFragment fragment = new NewNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);
        titleEditText = (EditText)view.findViewById(R.id.title);
        textEditText = (EditText)view.findViewById(R.id.text);
        imageutils =new Imageutils(getActivity(),this,true);
        iv_attachment = (ImageView)view.findViewById(R.id.select_image);
        iv_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageutils.imagepicker(1);
            }
        });
        saveButton = (Button)view.findViewById(R.id.save_note);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptInsert();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        return view;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptInsert() {

        // Reset errors.
        titleEditText.setError(null);
        textEditText.setError(null);

        // Store values at the time of the login attempt.
        String title = titleEditText.getText().toString();
        String text = textEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(title)) {
            titleEditText.setError(getString(R.string.error_field_required));
            focusView = titleEditText;
            cancel = true;
        } else if (TextUtils.isEmpty(text)) {
            textEditText.setError(getString(R.string.error_field_required));
            focusView = textEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            if(bitmap!= null && file_name!=null){
                String path =  Environment.getExternalStorageDirectory() + File.separator + "notepad" + File.separator;
                imageutils.createImage(bitmap,file_name,path,false);
            }
            // add record to db
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            String formattedDate = df.format(c.getTime());

            DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
            db.open();
            db.addNote(new Note(1, title,text,file_name,formattedDate));
            db.close();
            if (mListener != null) {
                mListener.onFragmentInteraction(null);
            }
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("Fragment", "onRequestPermissionsResult: "+requestCode);
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        bitmap = file;
        file_name = filename;
        iv_attachment.setImageBitmap(file);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Fragment", "onActivityResult: ");
        imageutils.onActivityResult(requestCode, resultCode, data);

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
