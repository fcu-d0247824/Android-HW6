package me.blackteatoas.android_hw6;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> listAdapter;
    DBOpenHelper openHelper;
    String[] list = {"123","456"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DBOpenHelper(this);

        Button button = (Button)findViewById(R.id.button);
        ListView listView = (ListView)findViewById(R.id.noteList);
        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,getNoteList());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + listAdapter.getItem(position).id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("id", listAdapter.getItem(position).id);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = openHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", "New Note");
                contentValues.put("content", "");
                contentValues.put("created_at", new Date().getTime());
                contentValues.put("updated_at", new Date().getTime());
                long id = db.insert("note", null, contentValues);
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Note> getNoteList() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, content ,id from note", null);
        ArrayList<Note> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            list.add(new Note(cursor.getLong(idIndex),cursor.getString(titleIndex), cursor.getString(contentIndex)));
        }
        cursor.close();
        return list;
    }

    class Note {
        public long id;
        public String title;
        public String content;
        public Note(long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }
        public String toString() {
            return String.valueOf(title);
        }
    }

}
