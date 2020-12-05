package com.rememberindia.remember4u.Fragment;

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

import com.rememberindia.remember4u.Activity.MainActivity;
import com.rememberindia.remember4u.Bean.Login_User_Bean.Login_User_List_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;
import com.rememberindia.remember4u.Utility.Session_Manager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp_Fragment extends Fragment implements View.OnClickListener{

    Activity referenceActivity;
    View parentHolder;
    Button btnCreateAccount;
    EditText ET_Full_Name , ET_Password , ET_Confirm_Password , ET_Degree , ET_Registration_No ,
             ET_Mobile_No , ET_Email , ET_City , ET_State , ET_Full_Address, ET_Pincode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_sign_up_, container, false);

        btnCreateAccount = (Button)parentHolder.findViewById(R.id.btnCreateAccount);
        ET_Full_Name = (EditText)parentHolder.findViewById(R.id.ET_Full_Name);
        ET_Password = (EditText)parentHolder.findViewById(R.id.ET_Password);
        ET_Confirm_Password = (EditText)parentHolder.findViewById(R.id.ET_Confirm_Password);
        ET_Degree = (EditText)parentHolder.findViewById(R.id.ET_Degree);
        ET_Registration_No = (EditText)parentHolder.findViewById(R.id.ET_Registration_No);
        ET_Mobile_No = (EditText)parentHolder.findViewById(R.id.ET_Mobile_No);
        ET_Email = (EditText)parentHolder.findViewById(R.id.ET_Email);
        ET_City = (EditText)parentHolder.findViewById(R.id.ET_City);
        ET_State = (EditText)parentHolder.findViewById(R.id.ET_State);
        ET_Full_Address = (EditText)parentHolder.findViewById(R.id.ET_Full_Address);
        ET_Pincode = (EditText)parentHolder.findViewById(R.id.ET_Pincode);

        btnCreateAccount.setOnClickListener(this);




        return parentHolder;
    }



    public void SignUp__API_Call()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//
        Call<Login_User_List_Bean> call = apiService.SignUp_New_User("Insert_New_User",
                ET_Full_Name.getText().toString() + "",
                ET_Password.getText().toString() + "",
                ET_Degree.getText().toString() + "",
                ET_Registration_No.getText().toString() + "",
                ET_Mobile_No.getText().toString() + "",
                ET_Email.getText().toString() + "",
                ET_City.getText().toString() + "",
                ET_State.getText().toString() + "",
                ET_Full_Address.getText().toString() + "",
                ET_Pincode.getText().toString() + "");
        call.enqueue(new Callback<Login_User_List_Bean>() {
            @Override
            public void onResponse(Call<Login_User_List_Bean> call, Response<Login_User_List_Bean> response) {

                Toast.makeText(referenceActivity, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {
                    Session_Manager.Save_Login_Data(getContext() , "True" , response.body().getData().get(0).getID() , response.body().getData().get(0).getName() ,
                            response.body().getData().get(0).getPassword() , response.body().getData().get(0).getDegree() , response.body().getData().get(0).getRegistrationNo() ,
                            response.body().getData().get(0).getMobileNO() ,response.body().getData().get(0).getEmailID() , response.body().getData().get(0).getCity() ,
                            response.body().getData().get(0).getState() , response.body().getData().get(0).getAddress1(), response.body().getData().get(0).getPincode());
                    referenceActivity.finish();
                    Intent in = new Intent(referenceActivity , MainActivity.class);
                    referenceActivity.startActivity(in);
                } else {
                }
                btnCreateAccount.setEnabled(true);

            }

            @Override
            public void onFailure(Call<Login_User_List_Bean> call, Throwable t) {
                btnCreateAccount.setEnabled(true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:
                if (Check_Validation())
                {
                    btnCreateAccount.setEnabled(false);
                    SignUp__API_Call();
                }

                break;
        }
    }

    public boolean Check_Validation() {

        boolean Flag = true;

        if (ET_Full_Name.getText().length() == 0) {
            ET_Full_Name.setError("Enter Full Name");
            Flag = false;
        }

        if (ET_Password.getText().length() < 4)
        {
            ET_Password.setError("Enter Password AtLeast 4 Charcter");
            Flag = false;
        }

        if (ET_Confirm_Password.getText().length() < 4)
        {
            ET_Confirm_Password.setError("Enter Password AtLeast 4 Charcter");
            Flag = false;
        }

        if (!ET_Password.getText().toString().equals(ET_Confirm_Password.getText().toString()))
        {
            ET_Confirm_Password.setError("Password Did Not Matched");
            Flag = false;
        }

//        if (ET_Degree.getText().length() == 0) {
//            ET_Degree.setError("Enter Degree Name");
//            Flag = false;
//        }
//
//        if (ET_Registration_No.getText().length() == 0) {
//            ET_Registration_No.setError("Enter Registration No");
//            Flag = false;
//        }


        if (ET_Mobile_No.getText().length() != 10) {
            ET_Mobile_No.setError("Enter Mobile No. Of 10 Degits");
            Flag = false;
        }

        if (ET_Email.getText().length() == 0) {
            ET_Email.setError("Enter Email ID");
            Flag = false;
        }

        if (ET_City.getText().length() == 0) {
            ET_City.setError("Enter City Name");
            Flag = false;
        }

        if (ET_State.getText().length() == 0) {
            ET_State.setError("Enter State No");
            Flag = false;
        }


        if (ET_Full_Address.getText().length() == 0) {
            ET_Full_Address.setError("Enter Full Valid Address");
            Flag = false;
        }

         if (ET_Pincode.getText().length() == 0) {
             ET_Pincode.setError("Enter Valid Pincode");
            Flag = false;
        }



        return Flag;
    }
}
