package com.example.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_first_name, et_last_name, et_email, et_password;
    ListView lv_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectModelWithView();
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
                Toast.makeText(this, "Added", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.menu_main_update:{
                Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.menu_main_delete:{
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
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

}