package com.rememberindia.remember4u.Utility;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberindia.remember4u.Bean.Common_Insert_Response_Bean;
import com.rememberindia.remember4u.Bean.Login_User_Bean.Login_User_List_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_Profile_Activity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText ET_Full_Name , ET_Degree , ET_Registration_No ,
            ET_Mobile_No , ET_Email , ET_City , ET_State , ET_Full_Address, ET_Pincode;
    TextView txtTermService;
    TextInputLayout textInputPassword, textInputConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up_);

        setTitle("My Profile");
        btnCreateAccount = (Button)findViewById(R.id.btnCreateAccount);

        ET_Full_Name = (EditText)findViewById(R.id.ET_Full_Name);
        ET_Degree = (EditText)findViewById(R.id.ET_Degree);
        ET_Registration_No = (EditText)findViewById(R.id.ET_Registration_No);
        ET_Mobile_No = (EditText)findViewById(R.id.ET_Mobile_No);
        ET_Email = (EditText)findViewById(R.id.ET_Email);
        ET_City = (EditText)findViewById(R.id.ET_City);
        ET_State = (EditText)findViewById(R.id.ET_State);
        ET_Full_Address = (EditText)findViewById(R.id.ET_Full_Address);
        ET_Pincode = (EditText)findViewById(R.id.ET_Pincode);
        txtTermService = (TextView)findViewById(R.id.txtTermService);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);

        SignIn_API_Call(Session_Manager.getMobile_No(My_Profile_Activity.this) , Session_Manager.getPassword(My_Profile_Activity.this));

        txtTermService.setVisibility(View.GONE);
        textInputPassword.setVisibility(View.GONE);
        textInputConfirmPassword.setVisibility(View.GONE);

        btnCreateAccount.setText("Update Profile");
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUp_Update_API_Call();
            }
        });
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
                if (response.body().getStatus() == 1) {
                    ET_Full_Name.setText(response.body().getData().get(0).getName() + "");
                    ET_Degree.setText(response.body().getData().get(0).getDegree() + "");
                    ET_Registration_No.setText(response.body().getData().get(0).getRegistrationNo() + "");
                    ET_Mobile_No.setText(response.body().getData().get(0).getMobileNO() + "");
                    ET_Email.setText(response.body().getData().get(0).getEmailID() + "");
                    ET_City.setText(response.body().getData().get(0).getCity() + "");
                    ET_State.setText(response.body().getData().get(0).getState() + "");
                    ET_Full_Address.setText(response.body().getData().get(0).getAddress1() + "");
                    ET_Pincode.setText(response.body().getData().get(0).getPincode() + "");


                } else {
                }
            }

            @Override
            public void onFailure(Call<Login_User_List_Bean> call, Throwable t) {

            }
        });

    }


    public void SignUp_Update_API_Call()
    {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//
        Call<Common_Insert_Response_Bean> call = apiService.SignUp_Update_User("Update_User_Profile",
                Session_Manager.getUser_ID(My_Profile_Activity.this),
                ET_Full_Name.getText().toString() + "",
                ET_Degree.getText().toString() + "",
                ET_Registration_No.getText().toString() + "",
                ET_Mobile_No.getText().toString() + "",
                ET_Email.getText().toString() + "",
                ET_City.getText().toString() + "",
                ET_State.getText().toString() + "",
                ET_Full_Address.getText().toString() + "",
                ET_Pincode.getText().toString() + "");
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {
                if (response.body().getStatus() == 1) {

                    Session_Manager.Save_Login_Data(My_Profile_Activity.this , "True" , Session_Manager.getUser_ID(My_Profile_Activity.this) , ET_Full_Name.getText().toString() ,
                            Session_Manager.getPassword(My_Profile_Activity.this) , ET_Degree.getText().toString() , ET_Registration_No.getText().toString() ,
                            ET_Mobile_No.getText().toString() , ET_Email.getText().toString(), ET_City.getText().toString() ,
                            ET_State.getText().toString() , ET_Full_Address.getText().toString(), ET_Pincode.getText().toString());

                    Toast.makeText(My_Profile_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                    checkPincode();

                } else {
                }


            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {

            }
        });

    }

    public void checkPincode() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Common_Insert_Response_Bean> call = apiService.CheckPincode("Pincode_Is_Valid",
                Session_Manager.getPincode(My_Profile_Activity.this).toString());
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                if (response.body().getStatus() == 1) {
                    Session_Manager.setisPincodeValid(My_Profile_Activity.this, true);
                } else {
                    Session_Manager.setisPincodeValid(My_Profile_Activity.this, false);
                }

            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {
            }
        });
    }
}
