package tomerbu.edu.sqlitefirebase;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public String getTitleEt() {
        return etTitle.getText().toString();
    }

    public String getDescription() {
        return etDescription.getText().toString();
    }

    public void loadTodos(View view) {
        Cursor cursor = getContentResolver().query(TodosContentProvider.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();

        do {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);

            Toast.makeText(ScrollingActivity.this, id + " " + title + " " + description, Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext());
    }

    public void saveTodo(View view) {
        ContentValues values = new ContentValues();
        values.put("Title", getTitleEt());
        values.put("Description", getDescription());
        Uri insert = getContentResolver().insert(TodosContentProvider.CONTENT_URI, values);
        Toast.makeText(ScrollingActivity.this, insert.toString(), Toast.LENGTH_SHORT).show();
    }

    public void updateTodo(View view) {
        ContentValues values = new ContentValues();
        values.put("Title", getTitleEt());
        values.put("Description", getDescription());
        //getContentResolver().update(TodosContentProvider.CONTENT_URI.buildUpon().appendEncodedPath("2").build(), values, null, null);
        getContentResolver().update(TodosContentProvider.CONTENT_URI, values, "_ID=?", new String[]{"3"});
    }

    public void deleteTodo(View view) {
        //int delete = getContentResolver().delete(TodosContentProvider.CONTENT_URI, "_id=?", new String[]{"1"});
        int delete = getContentResolver().delete(TodosContentProvider.CONTENT_URI.buildUpon().appendEncodedPath("4").build(), null, null);
        Toast.makeText(ScrollingActivity.this, "" + delete, Toast.LENGTH_SHORT).show();
    }

    public void loadOneTodo(View view) {
        // Cursor cursor = getContentResolver().query(TodosContentProvider.CONTENT_URI.buildUpon().appendEncodedPath("2").build(), null, null, null, null);
        Cursor cursor = getContentResolver().query(TodosContentProvider.CONTENT_URI, null, "_id=?", new String[]{"2"}, null);
        cursor.moveToFirst();

        do {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);

            Toast.makeText(ScrollingActivity.this, id + " " + title + " " + description, Toast.LENGTH_SHORT).show();
        }
        while (cursor.moveToNext());
    }
}
