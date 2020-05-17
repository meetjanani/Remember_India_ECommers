package com.rememberindia.shoppingportal.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rememberindia.shoppingportal.Activity.MainActivity;
import com.rememberindia.shoppingportal.Bean.Login_User_Bean.Login_User_List_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;
import com.rememberindia.shoppingportal.Utility.Session_Manager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Fragment extends Fragment implements View.OnClickListener{

    Activity referenceActivity;
    View parentHolder;
    Button Btn_SignIn;
    EditText Et_Mobile_Email , ET_Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_login_, container, false);

        Et_Mobile_Email = (EditText)parentHolder.findViewById(R.id.Et_Mobile_Email);
        ET_Password = (EditText)parentHolder.findViewById(R.id.ET_Password);
        Btn_SignIn = (Button)parentHolder.findViewById(R.id.Btn_SignIn);

        Btn_SignIn.setOnClickListener(this);

        try
        {
            if (Session_Manager.getIs_Login(referenceActivity).equals("True"))
            {
                SignIn_API_Call(Session_Manager.getMobile_No(getActivity()) ,
                        Session_Manager.getPassword(getActivity()));
            }
        }
        catch (Exception e)
        {

        }

        return parentHolder;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Btn_SignIn:
                if (Check_Validation())
                {
                    SignIn_API_Call(Et_Mobile_Email.getText().toString() , ET_Password.getText().toString());
                }
                break;
        }
    }


    public void SignIn_API_Call(String mob_email , String pass)
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//
        Call<Login_User_List_Bean> call = apiService.SignIn_User("Login_User",
                mob_email,
                pass);
        call.enqueue(new Callback<Login_User_List_Bean>() {
            @Override
            public void onResponse(Call<Login_User_List_Bean> call, Response<Login_User_List_Bean> response) {

                Toast.makeText(referenceActivity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {

                    Session_Manager.Save_Login_Data(getContext() , "True" , response.body().getData().get(0).getID() , response.body().getData().get(0).getName() ,
                            response.body().getData().get(0).getPassword() , response.body().getData().get(0).getDegree() , response.body().getData().get(0).getRegistrationNo() ,
                            response.body().getData().get(0).getMobileNO() ,response.body().getData().get(0).getEmailID() , response.body().getData().get(0).getCity() ,
                            response.body().getData().get(0).getState() , response.body().getData().get(0).getAddress1());
                    //Toast.makeText(referenceActivity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    referenceActivity.finish();
                    Intent in = new Intent(referenceActivity , MainActivity.class);
                    referenceActivity.startActivity(in);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Login_User_List_Bean> call, Throwable t) {

            }
        });

    }

    public boolean Check_Validation() {

        boolean Flag = true;

        if (Et_Mobile_Email.getText().length() < 4) {
            Et_Mobile_Email.setError("Enter Valid Mobile No. OR Email");
            Flag = false;
        }

        if (ET_Password.getText().length() < 4)
        {
            ET_Password.setError("Enter Password AtLeast 4 Charcter");
            Flag = false;
        }



        return Flag;
    }
}

