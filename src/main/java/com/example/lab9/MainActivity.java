package com.example.lab9;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Software selectedItem;
    private ArrayAdapter<Software> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabHost();
    }

    protected void initTabHost(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Каталог");
        tabHost.addTab(tabSpec);
        initCatalog();

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Редактирование");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    protected void initCatalog(){

        ListView listView = findViewById(R.id.MainListView);

        Software[] softwares = loadFromDB();

        adapter = new SoftwareAdapter(this, softwares);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                selectedItem = softwares[position];

                EditText editText = (EditText) findViewById(R.id.editTextTextName);
                editText.setText(selectedItem.getName());

                editText = (EditText) findViewById(R.id.editTextTextCost);
                editText.setText(Float.toString(selectedItem.getCost()));

                editText = (EditText) findViewById(R.id.editTextDate);
                editText.setText(selectedItem.getDate());

                editText = (EditText) findViewById(R.id.editTextTextVersion);
                editText.setText(selectedItem.getVersion());

                editText = (EditText) findViewById(R.id.editTextTextCategories);
                editText.setText(selectedItem.getCategories());

                editText = (EditText) findViewById(R.id.editTextTextSubcategories);
                editText.setText(selectedItem.getSubcategories());

                editText = (EditText) findViewById(R.id.editTextTextDescription);
                editText.setText(selectedItem.getDescription());
            }
        });

    }

    private Software[] loadFromDB(){

        db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF EXISTS softwares");
        db.execSQL("CREATE TABLE IF NOT EXISTS softwares (id INTEGER,name TEXT,description TEXT,cost FLOAT,date TEXT, version TEXT, categories TEXT, subcategories TEXT)");


        Cursor query = db.rawQuery("SELECT * FROM softwares ORDER BY categories, subcategories;", null);//

        ArrayList<Software> softwares = new ArrayList<Software>();

        String categories = "", subcategories = "";

        while (query.moveToNext()){
            if(!categories.equals(query.getString(6))){
                categories = query.getString(6);
                softwares.add(new Software(-1,"", "", 0,"", "", categories, ""));
            }
            if(!subcategories.equals(query.getString(7))){
                subcategories = query.getString(7);
                softwares.add(new Software(-1,"", "", 0,"", "", "", subcategories));
            }
            softwares.add(new Software(query.getInt(0),
                    query.getString(1),
                    query.getString(2),
                    query.getFloat(3),
                    query.getString(4),
                    query.getString(5),
                    query.getString(6),
                    query.getString(7)));
        }

        return softwares.toArray(new Software[0]);
    }

    public void addElement(View view) {
        String value = Integer.toString(Software.Id) + ",";
        Software.Id += 1;

        for (EditText editText : getEdits()) {
            value += "'" + editText.getText() + "',";
        }

        db.execSQL("INSERT OR IGNORE INTO softwares VALUES (" + value.substring(0, value.length() - 1) + ");");
        initCatalog();
    }

    public void changeElement(View view){

        String value = "";
        value += ((EditText) findViewById(R.id.editTextTextName)).getText().length() != 0 ? "name = '" + ((EditText) findViewById(R.id.editTextTextName)).getText() + "'," : "";
        value += (((EditText) findViewById(R.id.editTextTextDescription)).getText()).length() != 0 ? "description = '" + ((EditText) findViewById(R.id.editTextTextDescription)).getText() + "'," : "";
        value += ((EditText) findViewById(R.id.editTextTextCost)).getText().length() != 0 ? "cost = " + ((EditText) findViewById(R.id.editTextTextCost)).getText() + "," : "";
        value += ((EditText) findViewById(R.id.editTextDate)).getText().length() != 0 ? "date = '" + ((EditText) findViewById(R.id.editTextDate)).getText() + "'," : "";
        value += ((EditText) findViewById(R.id.editTextTextVersion)).getText().length() != 0 ? "version = '" + ((EditText) findViewById(R.id.editTextTextVersion)).getText() + "'," : "";
        value += ((EditText) findViewById(R.id.editTextTextCategories)).getText().length() != 0 ? "categories = '" + ((EditText) findViewById(R.id.editTextTextCategories)).getText() + "'," : "";
        value += ((EditText) findViewById(R.id.editTextTextSubcategories)).getText().length() != 0 ? "subcategories = '" + ((EditText) findViewById(R.id.editTextTextSubcategories)).getText() + "'," : "";

        value = value.charAt(value.length() - 1) == ',' ? value.substring(0, value.length() - 1) : value;

        db.execSQL("UPDATE softwares SET " + value + " WHERE id=" + selectedItem.getId());
        initCatalog();
    }

    public void deleteElement(View view){
        db.execSQL("DELETE FROM softwares WHERE id=" + selectedItem.getId() + ";");
        initCatalog();
    }

    private EditText[] getEdits(){
        return new EditText[]{
                (EditText) findViewById(R.id.editTextTextName),
                (EditText) findViewById(R.id.editTextTextDescription),
                (EditText) findViewById(R.id.editTextTextCost),
                (EditText) findViewById(R.id.editTextDate),
                (EditText) findViewById(R.id.editTextTextVersion),
                (EditText) findViewById(R.id.editTextTextCategories),
                (EditText) findViewById(R.id.editTextTextSubcategories),
        };
    }

}