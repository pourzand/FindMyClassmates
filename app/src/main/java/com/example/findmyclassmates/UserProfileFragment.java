package com.example.findmyclassmates;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class UserProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    EditText nameEditText;
    Spinner roleSpinner;
    EditText uscIdEditText;
    Button updateButton;
    ImageView profileImageView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String currentUsername = UserSession.getInstance().getUsername(); //get username
        // Initialize DatabaseReference
        Log.println(Log.INFO, "Inside user profile", "hmmm: " + currentUsername);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("profiles").child(currentUsername);


        nameEditText = view.findViewById(R.id.nameEditText);
        roleSpinner = view.findViewById(R.id.roleSpinner);
        uscIdEditText = view.findViewById(R.id.uscIdEditText);
        updateButton = view.findViewById(R.id.updateButton);
        profileImageView = view.findViewById(R.id.profileImageView);

        // Populate the role spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.roles,
                R.layout.custom_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        Log.println(Log.ASSERT, "test","currUser  is " + currentUsername);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEditText.getText().toString();

                String newRole = roleSpinner.getSelectedItem().toString();
                String newUscId = uscIdEditText.getText().toString();

                // Call a function to update user data
                updateUser(newName, newRole, newUscId);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });

        // Attach a ValueEventListener to retrieve user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve user data from the database
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String role = dataSnapshot.child("role").getValue(String.class);
                    String uscId = dataSnapshot.child("ID").getValue(String.class);

                    // Update the UI with the retrieved data
                    nameEditText.setText(name);
                    roleSpinner.setSelection(getIndexFromRoleSpinner(role));
                    uscIdEditText.setText(uscId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });

        return view;
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dispatchTakePictureIntent();
                        break;
                    case 1:
                        openGallery();
                        break;
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImageView.setImageBitmap(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                profileImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private int getIndexFromRoleSpinner(String roleToFind) {
        int index = 0; // Default index if role is not found

        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) roleSpinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(roleToFind)) {
                index = i;
                break; // Exit the loop when the role is found
            }
        }

        return index;
    }


    private void updateUser(String newName, String newRole, String newUscId) {
        // Implement this function to update user data in your data source
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("profiles").child(UserSession.getInstance().getUsername());

        userRef.child("name").setValue(newName);
        userRef.child("role").setValue(newRole);
        userRef.child("uscId").setValue(newUscId);

        Toast.makeText(requireContext(), "User profile updated successfully", Toast.LENGTH_SHORT).show();



    }
}