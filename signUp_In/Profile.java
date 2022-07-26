package com.example.portal;

import android.content.Intent;
import android.os.Bundle;
import com.example.portal.SharedPrefer;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.view.View.OnClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment implements OnClickListener{
    TextView userN, first, last,phone, email,gender;
    Button logOut;
    Context context;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        // Inflate the layout for this fragment

        context = container.getContext();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        if (SharedPrefer.getInstance(context).isLogged()) {

            userN = v.findViewById(R.id.user);
            first = v.findViewById(R.id.first);
            last = v.findViewById(R.id.last);
            phone = v.findViewById(R.id.phone);
            email = v.findViewById(R.id.email);
            gender = v.findViewById(R.id.gender);
            logOut = (Button) v.findViewById(R.id.logT);
            User user = SharedPrefer.getInstance(context).getUser();

            userN.setText(String.valueOf(user.getUsername()));
            first.setText(user.getFirstname());
            last.setText(user.getLastname());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            gender.setText(user.getGender());

            logOut.setOnClickListener(this);

        } else {
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);

        }
        return v;
    }

            public void onClick(View view) {
        if(view.equals(logOut)){

                SharedPrefer.getInstance(context).logout();
            }

    }
}