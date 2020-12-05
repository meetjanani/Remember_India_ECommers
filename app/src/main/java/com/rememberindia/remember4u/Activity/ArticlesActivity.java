package com.rememberindia.remember4u.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.rememberindia.remember4u.Activity.Share_post_Activity.PDFView_Activity;
import com.rememberindia.remember4u.R;

public class ArticlesActivity extends AppCompatActivity {

    LinearLayout LL_About_Colloidal_Silver_Technology, LL_Report_of_silver_as_disinfectant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        LL_About_Colloidal_Silver_Technology = findViewById(R.id.LL_About_Colloidal_Silver_Technology);
        LL_Report_of_silver_as_disinfectant = findViewById(R.id.LL_Report_of_silver_as_disinfectant);

        LL_About_Colloidal_Silver_Technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ArticlesActivity.this, PDFView_Activity.class);
                in.putExtra("PDF_Name", "SCIENCE_TECHNOLOGY_SILVER.pdf");
                startActivity(in);
            }
        });

        LL_Report_of_silver_as_disinfectant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ArticlesActivity.this, PDFView_Activity.class);
                in.putExtra("PDF_Name", "External_Review_Draft.pdf");
                startActivity(in);
            }
        });
    }
}