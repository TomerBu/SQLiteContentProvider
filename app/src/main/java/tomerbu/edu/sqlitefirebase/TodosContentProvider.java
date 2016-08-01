package tomerbu.edu.sqlitefirebase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class TodosContentProvider extends ContentProvider {
    public static final String AUTHORITY = "tomerbu.edu.sqlitefirebase";
    public static final int TODOS = 10;
    public static final int TODOS_ID = 11;

    private UriMatcher matcher;

    private String MIME_TODOS = "vnd.android.cursor.dir/vnd.tomerbu.edu.sqlitefirebase.Todos";
    private String MIME_SINGLE_TODOS = "vnd.android.cursor.item/vnd.tomerbu.edu.sqlitefirebase.Todos";
    private TodosDBHelper helper;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + "Todos");

    public TodosContentProvider() {
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (matcher.match(uri)) {
            case TODOS_ID:
                selection = getModifiedSelectionWithID(uri, selection);
            case TODOS:
                break;
            default:
                throw new UnsupportedOperationException("No Such URI");
        }

        return helper.getWritableDatabase().query("Todos", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @NonNull
    private String getModifiedSelectionWithID(Uri uri, String selection) {
        if (!TextUtils.isEmpty(selection))
            selection = selection.concat(" AND _ID = " + uri.getLastPathSegment());
        else
            selection = "_ID = " + uri.getLastPathSegment();
        return selection;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        switch (matcher.match(uri)) {
            case TODOS:
                break;
            case TODOS_ID:
                selection = getModifiedSelectionWithID(uri, selection);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }

        int rowsDeleted = helper.getWritableDatabase().delete("Todos", selection, selectionArgs);
        notifyChange(uri);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case TODOS:
                return MIME_TODOS;
            case TODOS_ID:
                return MIME_SINGLE_TODOS;
        }
        return uri.getLastPathSegment();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)) {
            case TODOS:
                long id = helper.getWritableDatabase().insert("Todos", null, values);
                notifyChange(uri);
                return uri.buildUpon().appendPath(id + "").build();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void notifyChange(Uri uri) {
        getContext();
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    @Override
    public boolean onCreate() {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "Todos", TODOS);
        matcher.addURI(AUTHORITY, "Todos/#", TODOS_ID);
        helper = new TodosDBHelper(getContext());
        return false;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (matcher.match(uri)) {
            case TODOS:
                break;
            case TODOS_ID:
                selection = getModifiedSelectionWithID(uri, selection);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        int updatedRows = helper.getWritableDatabase().update("Todos", values, selection, selectionArgs);
        notifyChange(uri);
        return updatedRows;
    }
}
