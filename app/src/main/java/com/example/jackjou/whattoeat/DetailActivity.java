package com.example.jackjou.whattoeat;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    EditText nameText, noteText;
    Button updateBtn,deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();

        final String name = i.getExtras().getString("NAME");
        final String note = i.getExtras().getString("NOTE");
        final int id = i.getExtras().getInt("ID");
        final String TBName = i.getExtras().getString("TB");

        updateBtn = (Button) findViewById(R.id.updateBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        nameText = (EditText) findViewById(R.id.nameEdit);
        noteText = (EditText) findViewById(R.id.noteEdit);

        //ASSIGN DATA TO THOSE VIEWS
        nameText.setText(name);
        noteText.setText(note);

        //update
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameText.length() <= 0){
                    Toast.makeText(DetailActivity.this, "店家名稱不能是空滴喔^3^", Toast.LENGTH_SHORT).show();
                }
                else if(nameText.getText().toString().trim().isEmpty()){
                    Toast.makeText(DetailActivity.this, "店家名稱不能是空格喔^3^", Toast.LENGTH_SHORT).show();
                }
                else {
                    update(TBName, id, nameText.getText().toString(), noteText.getText().toString());
                }

                finish();
            }
        });

        //DELETE
        //update
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(TBName, id);
            }
        });
    }

    private void update(String TBName, int id, String newName, String newNote){
        MyDatabase db = new MyDatabase(this);

        db.openDB();
        long result = db.UPDATE(TBName,id,newName,newNote);

        if(result > 0){
            nameText.setText(newName);
            nameText.setText(newNote);
            Snackbar.make(nameText,"Updated Sucesfully",Snackbar.LENGTH_SHORT).show();
        }
        else{
            Snackbar.make(nameText,"Unable to Update",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

    //DELETE
    private void delete(String TBName, int id) {
        MyDatabase db = new MyDatabase(this);
        db.openDB();
        long result = db.Delete(TBName, id);

        if(result > 0){
            this.finish();
        }
        else{
            Snackbar.make(nameText,"Unable to Update",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();
    }
}
