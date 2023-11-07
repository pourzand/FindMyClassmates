package com.example.findmyclassmates;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RatingFormFragment extends Fragment {

    private EditText prompt1EditText;
    private EditText prompt2EditText;
    private EditText prompt3EditText;
    private EditText prompt4EditText;
    private EditText prompt5EditText;
    private Spinner sixthRatingSpinner;
    private RatingFormListener mListener;

    public interface RatingFormListener {
        void onRatingFormSubmit(String concatenatedResponses);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RatingFormListener) {
            mListener = (RatingFormListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_form, container, false);

        prompt1EditText = view.findViewById(R.id.prompt1EditText);
        prompt2EditText = view.findViewById(R.id.prompt2EditText);
        prompt3EditText = view.findViewById(R.id.prompt3EditText);
        prompt4EditText = view.findViewById(R.id.prompt4EditText);
        prompt5EditText = view.findViewById(R.id.prompt5EditText);
        sixthRatingSpinner = view.findViewById(R.id.sixthRatingSpinner);

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    if (validateForm()) {
                        String response1 = prompt1EditText.getText().toString();
                        String response2 = prompt2EditText.getText().toString();
                        String response3 = prompt3EditText.getText().toString();
                        String response4 = prompt4EditText.getText().toString();
                        String response5 = prompt5EditText.getText().toString();
                        String rating = sixthRatingSpinner.getSelectedItem().toString();

                        String concatenatedResponses =
                                "1. The workload of the class: " + response1 + "\n" +
                                        "2. The score they would like to give to this class: " + response2 + "\n" +
                                        "3. If the professor checks attendance: " + response3 + "\n" +
                                        "4. If the professor allows late homework submission: " + response4 + "\n" +
                                        "5. Other comments: " + response5 + "\n" +
                                        "6. Rating: " + rating;
                        Log.d(rating,"rating");
                        Log.d(concatenatedResponses,"c");
                        mListener.onRatingFormSubmit(concatenatedResponses);

                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please fill out all prompts", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

    private boolean validateForm() {
        String response1 = prompt1EditText.getText().toString();
        String response2 = prompt2EditText.getText().toString();
        String response3 = prompt3EditText.getText().toString();
        String response4 = prompt4EditText.getText().toString();
        String response5 = prompt5EditText.getText().toString();

        return !response1.isEmpty() && !response2.isEmpty() && !response3.isEmpty()
                && !response4.isEmpty() && !response5.isEmpty();
    }
}
