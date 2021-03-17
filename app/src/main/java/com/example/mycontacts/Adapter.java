package com.example.mycontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactViewHolder> {
    List<Contact> mContacts;

    public Adapter(List<Contact> Contacts) {
        mContacts = Contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView number;
        private TextView address;

        public ContactViewHolder(@NonNull View ContactView) {
            super(ContactView);
            name = ContactView.findViewById(R.id.name);
            number = ContactView.findViewById(R.id.phone_num);
            address = ContactView.findViewById(R.id.address);
        }

        public void bind(Contact contact) {
            name.setText(contact.getName());
            number.setText(String.valueOf(contact.getPhoneNum()));
            address.setText(contact.getAddress());
        }
    }

}
