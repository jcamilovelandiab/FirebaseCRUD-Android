package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebasecrud.model.Person;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText et_first_name, et_last_name, et_email, et_password;
    ListView lv_people;
    List<Person> peopleList = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Person personSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectModelWithView();
        initFirebase();
        listData();
        lv_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personSelected = (Person)
                        parent.getItemAtPosition(position);
                et_first_name.setText(personSelected.getFirst_name());
                et_last_name.setText(personSelected.getLast_name());
                et_email.setText(personSelected.getEmail());
                et_password.setText(personSelected.getPassword());
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listData(){
        databaseReference.child("Person").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                peopleList.clear();
                for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                    Person p = objSnaptshot.getValue(Person.class);
                    peopleList.add(p);
                }
                ArrayAdapter<Person> arrayAdapterPeople = new ArrayAdapter<Person>(MainActivity.this,android.R.layout.simple_list_item_1,peopleList);
                lv_people.setAdapter(arrayAdapterPeople);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_add:{
                if(validateFields()){
                    addPerson();
                    Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
                    cleanFields();
                }
                break;
            }
            case R.id.menu_main_update:{
                if(validateFields()){
                    updatePerson();
                    Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
                    cleanFields();
                }
                break;
            }
            case R.id.menu_main_delete:{
                deletePerson();
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
                cleanFields();
                break;
            }
        }
        return true;
    }

    private void connectModelWithView(){
        et_first_name = findViewById(R.id.main_et_first_name);
        et_last_name = findViewById(R.id.main_et_last_name);
        et_email = findViewById(R.id.main_et_email);
        et_password = findViewById(R.id.main_et_password);
        lv_people = findViewById(R.id.main_lv_people);
    }

    private void cleanFields(){
        et_first_name.setText("");
        et_last_name.setText("");
        et_email.setText("");
        et_password.setText("");
    }

    private void addPerson(){
        Person p = new Person();
        p.setUid(UUID.randomUUID().toString());
        p.setFirst_name(et_first_name.getText().toString());
        p.setLast_name(et_last_name.getText().toString());
        p.setEmail(et_email.getText().toString());
        p.setPassword(et_password.getText().toString());
        databaseReference.child("Person").child(p.getUid()).setValue(p);
    }

    private void updatePerson(){
        Person p = new Person();
        p.setUid(personSelected.getUid());
        p.setFirst_name(et_first_name.getText().toString());
        p.setLast_name(et_last_name.getText().toString());
        p.setEmail(et_email.getText().toString());
        p.setPassword(et_password.getText().toString());
        databaseReference.child("Person").child(p.getUid()).setValue(p);
    }

    private void deletePerson(){
        Person p = new Person();
        p.setUid(personSelected.getUid());
        databaseReference.child("Person").child(p.getUid())
                .removeValue();
    }

    private boolean validateFields(){
        String first_name = et_first_name.getText().toString();
        String last_name = et_last_name.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        if(first_name.trim().equals("")) {
            et_first_name.setError("Required field");
            return false;
        }else if(last_name.trim().equals("")) {
            et_last_name.setError("Required field");
            return false;
        }else if(email.trim().equals("")) {
            et_email.setError("Required field");
            return false;
        }else if(password.trim().equals("")) {
            et_password.setError("Required field");
            return false;
        }
        return true;
    }

}