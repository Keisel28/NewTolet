package com.zaaibo.tolet.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zaaibo.tolet.R;

public class fragmentProfile extends Fragment {
    TextView textViewId, textViewUsername, textViewEmail, textViewUserType;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public fragmentProfile() {

    }
    public static fragmentProfile newInstance(String param1, String param2) {
        fragmentProfile fragment = new fragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,
                container, false);

            if (!SharedPrefManager2.getInstance(getActivity().getApplicationContext()).isLoggedIn()) {
                getActivity().finish();
                startActivity(new Intent(getActivity().getApplicationContext(), LogIn.class));
            }

            textViewId = (TextView) view.findViewById(R.id.textViewId);
            textViewUsername = (TextView) view.findViewById(R.id.textViewUsername);
            textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
            textViewUserType = (TextView) view.findViewById(R.id.textViewUserType);


            //getting the current user
            User user = SharedPrefManager2.getInstance(getActivity().getApplicationContext()).getUser();

            //setting the values to the textviews
            textViewId.setText(String.valueOf(user.getId()));
            textViewUsername.setText(user.getUsername());
            textViewEmail.setText(user.getEmail());
            textViewUserType.setText(user.getUserType());

            //when the user presses logout button
            //calling the logout method
            view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                    SharedPrefManager2.getInstance(getActivity().getApplicationContext()).logout();
                }

            });
        view.findViewById(R.id.btm_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));
            }

        });

        return view;
    }
}