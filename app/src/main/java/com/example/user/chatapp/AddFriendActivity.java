package com.example.user.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {
    private EditText et_search;
    private Button bt_search;
    ListView mListView;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<UserData> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        mListView = (ListView) findViewById(R.id.userData);
        mAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,list);
        mListView.setAdapter(mAdapter);
        et_search = (EditText) findViewById(R.id.et_search);
        bt_search = (Button) findViewById(R.id.bt_search);

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                searchByUsername();
            }
        });

    }


    protected void searchByUsername() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        String search = et_search.getText().toString();
        reference.orderByChild("username").equalTo(search).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserData data = dataSnapshot.getValue(UserData.class);
                String username = data.username;
                list.add(username);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}