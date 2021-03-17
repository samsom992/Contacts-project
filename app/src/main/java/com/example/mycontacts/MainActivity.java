package com.example.mycontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Contact> contacts;
    private FirebaseFirestore firestore;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();
        contacts = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Adapter(contacts);
        recyclerView.setAdapter(adapter);
        getItems();
    }

    public void onAddClicked(View view) {
        addContactDialog();
    }

    private void addContactDialog() {
        AlertDialog alertDialog;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null, false);
        EditText name = dialogView.findViewById(R.id.name_edittext);
        EditText phoneNum = dialogView.findViewById(R.id.phone_num_edittext);
        EditText address = dialogView.findViewById(R.id.address_edittext);
        Button saveButton = dialogView.findViewById(R.id.save_button);
        alertDialog = new AlertDialog.Builder(this).setView(dialogView).create();
        alertDialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName, sPhone, sAddress;
                sName = name.getText().toString();
                sPhone = phoneNum.getText().toString();
                sAddress = address.getText().toString();
                if (sName.isEmpty() || sPhone.isEmpty() || sAddress.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill all fields please..", Toast.LENGTH_SHORT).show();
                } else {
                    Contact contact = new Contact(sName, sPhone, sAddress);
                    saveContactToFirestore(contact);
                    alertDialog.dismiss();
                }
            }
        });

    }

    private void saveContactToFirestore(Contact contact) {
        firestore.collection("contacts")
                .document(contact.getPhoneNum())
                .set(contact)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            contacts.add(0, contact);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Added successfully..", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getItems() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading data..");
        progressDialog.show();
        firestore.collection("contacts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> items = task.getResult().getDocuments();
                for (DocumentSnapshot i : items) {
                    Contact item = i.toObject(Contact.class);
                    assert item != null;
                    contacts.add(item);
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }
        });
    }
}