package com.example.don.listviewanddatabase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final int EDIT = 0, DELETE = 1;   // Created at 13.7

    EditText nameTxt, genderTxt, emailTxt, addressTxt;        // Created at 1.2
    Button addBtn;
    List<Contact> Contacts = new ArrayList<Contact>();        // Created at 3.3
    ListView contactListView;				// Created at 5.1
    ImageView contactImageImgView;			// Created at 6.1
    Uri imageUri = Uri.parse("android.resource://com.example.don.listviewanddatabase/drawable/ic_user_picture_180_holo_light.png");
    DatabaseHandler dbHandler; 				// Created at 11.1
    int longClickedItemIndex;                         // Created at 13.1
    ArrayAdapter<Contact> contactAdapter;     // Created at 13.2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        nameTxt = (EditText) findViewById(R.id.txtName);          // Created at 1.3
        genderTxt = (EditText) findViewById(R.id.txtGender);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        addressTxt = (EditText) findViewById(R.id.txtAddress);
        contactListView = (ListView) findViewById(R.id.listView);    // Created at 5.2
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);	// Created at 6.2
        dbHandler = new DatabaseHandler(getApplicationContext());     // Created at 11.2

        addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {                    // Created at 2.3
            @Override
            public void onClick(View view) {

                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameTxt.getText()), String.valueOf(genderTxt.getText()), String.valueOf(emailTxt.getText()), String.valueOf(addressTxt.getText()), imageUri);

                if (!contactExists(contact)) {
                    dbHandler.createContact(contact);   // Part-11.4
                    Contacts.add(contact);             // Part-11.5

                    contactAdapter.notifyDataSetChanged();

                    //populateList();

                    Toast.makeText(getApplicationContext(), " You Added " + String.valueOf(nameTxt.getText())  , Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " already Exists, Please use a different name", Toast.LENGTH_SHORT).show();
            }
        });


        registerForContextMenu(contactListView);     // Created at 13.4

        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // Created at 13.5
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);    // Created at 2.2


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);


        nameTxt.addTextChangedListener(new TextWatcher() {                  // Created at 2.1
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(String.valueOf(nameTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        contactImageImgView.setOnClickListener(new View.OnClickListener() {  // Created at 6.3
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }

        });
        if (dbHandler.getContactsCount() != 0)                 // Created at 12.2
            Contacts.addAll(dbHandler.getAllContacts());

        populateList();

    }

    public boolean onContextItemSelected(MenuItem item) {   // Created at 13.8
        switch (item.getItemId()) {
            case EDIT:
                // TODO: Implement editing a contact
                break;
            case DELETE:
                dbHandler.deleteContact(Contacts.get(longClickedItemIndex));
                Contacts.remove(longClickedItemIndex);
                contactAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }


    private boolean contactExists(Contact contact) {                  // Created at 12.1
        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }
        return false;
    }


    public void onActivityResult(int reqCode, int resCode, Intent data) {    // Created at 6.4
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                contactImageImgView.setImageURI(data.getData());
            }
        }
    }


    private void populateList() {   					// Created at 5.3  // Created at 13.3
        contactAdapter = new ContactListAdapter();
        contactListView.setAdapter(contactAdapter);
        contactListView.setOnItemClickListener(this);
    }




    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) { // Created at 13.6
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.ic_action_edit);
        menu.setHeaderTitle("User Options");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Edit User");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete User");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = Contacts.get(position).getName();
        String gender = Contacts.get(position).getGender();
        String email = Contacts.get(position).getEmail();
        String address = Contacts.get(position).getAddress();

        Intent intent = new Intent(this, FormDetails.class);
        intent.putExtra("name", name);
        intent.putExtra("gender", gender);
        intent.putExtra("email", email);
        intent.putExtra("address", address);

        startActivity(intent);

    }


    //4.2 deleted
    private class ContactListAdapter extends ArrayAdapter<Contact> {            // Created at 3.4
        public ContactListAdapter() {
            super(SignUp.this, R.layout.listview_item, Contacts);
        }
        @Override                                                           	// Created at 4.1
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());
            TextView gender = (TextView) view.findViewById(R.id.Gender);
            gender.setText(currentContact.getGender());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());
            TextView address = (TextView) view.findViewById(R.id.cAddress);
            address.setText(currentContact.getAddress());

            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);  // Created at 7.1
            ivContactImage.setImageURI(currentContact.getImageURI());

            return view;
        }

    }

}
