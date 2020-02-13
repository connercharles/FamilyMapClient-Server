package com.example.familymapclient.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.familymapclient.R;
import com.example.familymapclient.activities.MainActivity;
import com.example.familymapclient.datacache.DataCache;
import com.example.familymapclient.datacache.ServerProxy;
import com.example.shared.request.LoginRequest;
import com.example.shared.request.RegisterRequest;
import com.example.shared.result.LoginResult;
import com.example.shared.result.RegisterResult;

public class LoginFragment extends Fragment {
    private static final String LOG_TAG = "LoginFragment";

    private Button signInBtn;
    private Button registerBtn;
    private EditText serverHostTxt;
    private EditText serverPortTxt;
    private EditText usernameTxt;
    private EditText passwordTxt;
    private EditText firstnameTxt;
    private EditText lastnameTxt;
    private EditText emailTxt;
    private ToggleButton toggleGenderBtn;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // all the buttons and stuff
        signInBtn = v.findViewById(R.id.signInBtn);
        registerBtn = v.findViewById(R.id.registerBtn);
        serverHostTxt = v.findViewById(R.id.serverHost);
        serverPortTxt = v.findViewById(R.id.serverPort);
        usernameTxt = v.findViewById(R.id.username);
        passwordTxt = v.findViewById(R.id.password);
        firstnameTxt = v.findViewById(R.id.firstname);
        lastnameTxt = v.findViewById(R.id.lastname);
        emailTxt = v.findViewById(R.id.email);
        toggleGenderBtn = v.findViewById(R.id.toggleGender);

        // for all the txt listeners to call
        TextWatcher txtListener = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (areAllFilled()){
                    signInBtn.setEnabled(true);
                    registerBtn.setEnabled(true);
                } else if (areTxtFilled()){
                    signInBtn.setEnabled(true);
                    registerBtn.setEnabled(false);
                } else {
                    signInBtn.setEnabled(false);
                    registerBtn.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        serverHostTxt.addTextChangedListener(txtListener);
        serverPortTxt.addTextChangedListener(txtListener);
        usernameTxt.addTextChangedListener(txtListener);
        passwordTxt.addTextChangedListener(txtListener);
        firstnameTxt.addTextChangedListener(txtListener);
        lastnameTxt.addTextChangedListener(txtListener);
        emailTxt.addTextChangedListener(txtListener);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServerInfo();

                // get username and password
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                LoginRequest request = new LoginRequest(username, password);

                try {
                    LoginTask task = new LoginTask();
                    task.execute(request);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }


            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServerInfo();

                // get username and password
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String firstName = firstnameTxt.getText().toString();
                String lastName = lastnameTxt.getText().toString();
                String email = emailTxt.getText().toString();
                String gender = toggleGenderBtn.getText().toString();

                RegisterRequest request = new RegisterRequest();
                request.setUserName(username);
                request.setPassword(password);
                request.setFirstName(firstName);
                request.setLastName(lastName);
                request.setEmail(email);
                // to keep all the data the same
                if (gender.equals("Male")){
                    request.setGender("m");
                } else {
                    request.setGender("f");
                }

                try {
                    RegisterTask task = new RegisterTask();
                    task.execute(request);
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        });

        return v;
    }


    // checks some of the txts to see if it's filled
    private boolean areTxtFilled(){
        boolean isFull;
        isFull = !(serverHostTxt.getText().toString().isEmpty()
                || serverPortTxt.getText().toString().isEmpty()
                || usernameTxt.getText().toString().isEmpty()
                || passwordTxt.getText().toString().isEmpty());
        return isFull;
    }

    // checks is all the txts are filled
    private boolean areAllFilled(){
        boolean isFull;
        isFull = areTxtFilled() && !(firstnameTxt.getText().toString().isEmpty()
                || lastnameTxt.getText().toString().isEmpty()
                || emailTxt.getText().toString().isEmpty());
        return isFull;
    }


    private void getServerInfo(){
        // get port and host stuff
        DataCache.getInstance().serverHost = serverHostTxt.getText().toString();
        DataCache.getInstance().serverPort = serverPortTxt.getText().toString();
    }

    private class LoginTask extends AsyncTask<LoginRequest, Integer, LoginResult> {

        @Override
        protected LoginResult doInBackground(LoginRequest... requests) {

            ServerProxy proxy = new ServerProxy();
            LoginResult result = proxy.login(requests[0]);

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            // check if it worked, there should be no message
            if (result.getMessage() == null) {
                String message = "Signed in: " + DataCache.getInstance().user.getFirstName()
                        + " " + DataCache.getInstance().user.getLastName();
                Toast.makeText(getContext(), message , Toast.LENGTH_LONG).show();

                ((MainActivity)getActivity()).callMap();
            } else {
                Toast.makeText(getContext(), "Sign in Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class RegisterTask extends AsyncTask<RegisterRequest, Integer, RegisterResult> {

        @Override
        protected RegisterResult doInBackground(RegisterRequest... requests) {

            ServerProxy proxy = new ServerProxy();
            RegisterResult result = proxy.register(requests[0]);

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(RegisterResult result) {
            // check if it worked, there should be no message
            if (result.getMessage() == null) {
                String message = "Registered: " + DataCache.getInstance().user.getFirstName()
                        + " " + DataCache.getInstance().user.getLastName();
                Toast.makeText(getContext(), message , Toast.LENGTH_LONG).show();

                ((MainActivity)getActivity()).callMap();
            } else {
                Toast.makeText(getContext(), "Register Failed", Toast.LENGTH_LONG).show();
            }
        }


    }

}
