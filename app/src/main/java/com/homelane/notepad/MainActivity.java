package com.homelane.notepad;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.homelane.notepad.fragments.DetailsFragment;
import com.homelane.notepad.fragments.MainActivityFragment;
import com.homelane.notepad.fragments.NewNoteFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NewNoteFragment.OnFragmentInteractionListener,
        DetailsFragment.OnDetailsFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new NewNoteFragment(), "NewNoteFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new MainActivityFragment());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
    }

    public String convertDateToString(String dateStr){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
            SimpleDateFormat timeFormat = new SimpleDateFormat("d MMM yyyy, h:mm a", Locale.US);
            return timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException npe){
           return null;
        }
        return null;
    }

    @Override
    public void onDetailsFragmentInteraction(Uri uri) {
        Toast.makeText(this,"Note Deleted", Toast.LENGTH_SHORT).show();
    }
}
