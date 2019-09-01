package com.rememberindia.shoppingportal.Utility;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberindia.shoppingportal.Bean.Common_Insert_Response_Bean;
import com.rememberindia.shoppingportal.Bean.Login_User_Bean.Login_User_List_Bean;
import com.rememberindia.shoppingportal.R;
import com.rememberindia.shoppingportal.Rest.ApiClient;
import com.rememberindia.shoppingportal.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class My_Profile_Activity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText ET_Full_Name , ET_Password , ET_Confirm_Password , ET_Degree , ET_Registration_No ,
            ET_Mobile_No , ET_Email , ET_City , ET_State , ET_Full_Address;
    TextView txtTermService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up_);

        setTitle("My Profile");
        btnCreateAccount = (Button)findViewById(R.id.btnCreateAccount);

        ET_Full_Name = (EditText)findViewById(R.id.ET_Full_Name);
        ET_Password = (EditText)findViewById(R.id.ET_Password);
        ET_Confirm_Password = (EditText)findViewById(R.id.ET_Confirm_Password);
        ET_Degree = (EditText)findViewById(R.id.ET_Degree);
        ET_Registration_No = (EditText)findViewById(R.id.ET_Registration_No);
        ET_Mobile_No = (EditText)findViewById(R.id.ET_Mobile_No);
        ET_Email = (EditText)findViewById(R.id.ET_Email);
        ET_City = (EditText)findViewById(R.id.ET_City);
        ET_State = (EditText)findViewById(R.id.ET_State);
        ET_Full_Address = (EditText)findViewById(R.id.ET_Full_Address);
        txtTermService = (TextView)findViewById(R.id.txtTermService);

        SignIn_API_Call(Session_Manager.getMobile_No(My_Profile_Activity.this) , Session_Manager.getPassword(My_Profile_Activity.this));

        ET_Password.setVisibility(View.GONE);
        ET_Confirm_Password.setVisibility(View.GONE);
        txtTermService.setVisibility(View.GONE);

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

                Toast.makeText(My_Profile_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {
                    ET_Full_Name.setText(response.body().getData().get(0).getName() + "");
                    ET_Degree.setText(response.body().getData().get(0).getDegree() + "");
                    ET_Registration_No.setText(response.body().getData().get(0).getRegistrationNo() + "");
                    ET_Mobile_No.setText(response.body().getData().get(0).getMobileNO() + "");
                    ET_Email.setText(response.body().getData().get(0).getEmailID() + "");
                    ET_City.setText(response.body().getData().get(0).getCity() + "");
                    ET_State.setText(response.body().getData().get(0).getState() + "");
                    ET_Full_Address.setText(response.body().getData().get(0).getAddress1() + "");


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
                ET_Full_Address.getText().toString() + "");
        call.enqueue(new Callback<Common_Insert_Response_Bean>() {
            @Override
            public void onResponse(Call<Common_Insert_Response_Bean> call, Response<Common_Insert_Response_Bean> response) {

                Toast.makeText(My_Profile_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                if (response.body().getStatus() == 1) {

                    Session_Manager.Save_Login_Data(My_Profile_Activity.this , "True" , Session_Manager.getUser_ID(My_Profile_Activity.this) , ET_Full_Name.getText().toString() ,
                            Session_Manager.getPassword(My_Profile_Activity.this) , ET_Degree.getText().toString() , ET_Registration_No.getText().toString() ,
                            ET_Mobile_No.getText().toString() , ET_Email.getText().toString(), ET_City.getText().toString() ,
                            ET_State.getText().toString() , ET_Full_Address.getText().toString());

                    Toast.makeText(My_Profile_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();

                } else {
                }


            }

            @Override
            public void onFailure(Call<Common_Insert_Response_Bean> call, Throwable t) {

            }
        });

    }
}
