package com.example.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO initialize the variables with UI here
        btnAdd = findViewById(R.id.buttonAdd);
        btnEdit = findViewById(R.id.buttonEdit);
        btnRetrieve = findViewById(R.id.buttonRetrieve);
        tvDBContent = findViewById(R.id.textViewDBContent);
        etContent = findViewById(R.id.editTextContent);

        al = new ArrayList<>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etContent.getText().toString();
                DBHelper db = new DBHelper(MainActivity.this);
                long inserted_id = db.insertNote(data);
                db.close();

                if(inserted_id != -1) {
                    Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(db.getAllNotes());
                db.close();

                String text = "";
                for(int i = 0; i < al.size(); i++) {
                    Note tmp = al.get(i);
                    text += "ID: " + tmp.getId() + ", " + tmp.getNoteContent() + "\n";
                    tvDBContent.setText(text);
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note target = al.get(0);

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data", target);
                startActivityForResult(intent, 9);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 9) {
            btnRetrieve.performClick();
        }
    }
}
