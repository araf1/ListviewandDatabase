package com.example.don.listviewanddatabase;

import android.net.Uri;

/**
 * Created by DON on 16-Aug-15.
 */
public class Contact {
    private String _name, _gender, _email, _address;
    private Uri _imageURI;
    private int _id;

    public Contact( int id, String name, String phone, String email, String address, Uri imageURI) {
        _id = id;
        _name = name;
        _gender = phone;
        _email = email;
        _address = address;
        _imageURI = imageURI;
    }

    public int getId() { return _id; }

    public String getName() {
        return _name;
    }

    public String getGender() {
        return _gender;
    }

    public String getEmail() {
        return _email;
    }

    public String getAddress() {
        return _address;
    }

    public Uri getImageURI() { return _imageURI; }
}
