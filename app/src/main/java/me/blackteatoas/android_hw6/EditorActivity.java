package me.blackteatoas.android_hw6;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

    DBOpenHelper openHelper;
    long id;
    EditText titleEditText;
    EditText contentEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        openHelper = new DBOpenHelper(getApplicationContext());
        titleEditText = (EditText)findViewById(R.id.editText);
        contentEditText = (EditText)findViewById(R.id.editText2);

        Intent intent = this.getIntent();
        id = intent.getLongExtra("id", 0);

        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.query("note", new String[] {"title", "content"}, "id = ?", new String[]{String.valueOf(id)}, null, null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            titleEditText.setText(cursor.getString(titleIndex));
            contentEditText.setText(cursor.getString(contentIndex));
            Log.d("cc", "onCreate: get");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", titleEditText.getText().toString());
        contentValues.put("content", contentEditText.getText().toString());
        db.update("note", contentValues, "id = ?", new String[] {String.valueOf(id)});
    }
}
