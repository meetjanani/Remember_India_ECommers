package com.rememberindia.remember4u.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rememberindia.remember4u.Bean.DB_Helper_Offline.Order_Summery_Db_Helper;
import com.rememberindia.remember4u.Bean.DB_Helper_Offline.Order_Summery_Ofline_Bean;
import com.rememberindia.remember4u.Bean.Product_Details_Bean;
import com.rememberindia.remember4u.Bean.Product_List_Bean;
import com.rememberindia.remember4u.R;
import com.rememberindia.remember4u.Rest.ApiClient;
import com.rememberindia.remember4u.Rest.ApiInterface;
import com.rememberindia.remember4u.Utility.Session_Manager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product_Details_Activity extends AppCompatActivity {

    public static Order_Summery_Db_Helper dbhelper;
    final Context context = this;
    public String Product_ID;
    int Avaiable_Stock = 0;
    TextView txt_product_name, txt_product_price, txt_product_quantity, Tv_product_description, Tv_Amazon_Link, Tv_FlipKart_Link, Tv_Is_In_Stock;
    WebView txt_product_description;
    LinearLayout isPincodeIsValid;
    ImageView img_product_image;
    Button btn_cart;
    RelativeLayout lyt_checkout;
    double resp_tax;
    String resp_currency_code;
    List<Product_Details_Bean> Records;
    String AmazonLink, FlipKartLink;
    private String product_name, product_image, category_name, product_status, currency_code, product_description;
    private double product_price;
    private int product_quantity;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details_);

        txt_product_name = findViewById(R.id.product_name);
        img_product_image = findViewById(R.id.product_image);
        txt_product_price = findViewById(R.id.product_price);
        txt_product_description = findViewById(R.id.product_description);
        txt_product_quantity = findViewById(R.id.product_quantity);
        Tv_product_description = findViewById(R.id.Tv_product_description);
        Tv_Amazon_Link = findViewById(R.id.Tv_Amazon_Link);
        Tv_FlipKart_Link = findViewById(R.id.Tv_FlipKart_Link);
        Tv_Is_In_Stock = findViewById(R.id.Tv_Is_In_Stock);
        btn_cart = findViewById(R.id.btn_add_cart);
        lyt_checkout = findViewById(R.id.lyt_checkout);
        isPincodeIsValid = findViewById(R.id.isPincodeIsValid);

        dbhelper = new Order_Summery_Db_Helper(Product_Details_Activity.this);

        Product_ID = getIntent().getStringExtra("Product_ID");
        Product_By_ID_API_Call(Product_ID);
        setupToolbar();
        if(!Session_Manager.getisPincodeValid(Product_Details_Activity.this)){
            Tv_Amazon_Link.setVisibility(View.GONE);
            Tv_FlipKart_Link.setVisibility(View.GONE);
        }

        Tv_Amazon_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AmazonLink.length() > 10) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AmazonLink));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(context, "Sorry, this product is Not Avaiable On Amazon", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Tv_FlipKart_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AmazonLink.length() > 10) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FlipKartLink));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(context, "Sorry, this product is Not Avaiable On FlipKart", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.cart:
                Intent intent = new Intent(getApplicationContext(), Cart_Activity.class);
                intent.putExtra("tax", resp_tax);
                intent.putExtra("currency_code", resp_currency_code);
                startActivity(intent);
                break;

            case R.id.share:
                requestStoragePermission();
                break;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return true;
    }


    @TargetApi(16)
    private void requestStoragePermission() {
        Dexter.withActivity(Product_Details_Activity.this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            (new ShareTask(Product_Details_Activity.this)).execute(product_image);
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Product_Details_Activity.this);
        builder.setTitle(R.string.permission_msg);
        builder.setMessage(R.string.permission_storage);
        builder.setPositiveButton(R.string.dialog_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(R.string.dialog_option_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void Product_By_ID_API_Call(String Product_ID) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Product_List_Bean> call = apiService.Display_Product_By_ID("Display_Product_BY_ID", Product_ID);
        call.enqueue(new Callback<Product_List_Bean>() {
            @SuppressLint({"ResourceAsColor", "NewApi"})
            @Override
            public void onResponse(Call<Product_List_Bean> call, Response<Product_List_Bean> response) {

                if (response.body().getStatus() == 1) {
                    Records = response.body().getData();
                    Avaiable_Stock = Integer.parseInt(Records.get(0).getAvaiableStock());
                    if (Avaiable_Stock > 0) {
                        Tv_Is_In_Stock.setTextColor(getColor(R.color.green));
                        Tv_Is_In_Stock.setText("Currently in Stock");
                        // Tv_Is_In_Stock.setText("Currently " + Avaiable_Stock + " unit is in Stock");
                        lyt_checkout.setVisibility(View.VISIBLE);
                    } else {
                        Tv_Is_In_Stock.setTextColor(getColor(R.color.red));
                        Tv_Is_In_Stock.setText("Currently Out of Stock");
                    }

                    product_image = Records.get(0).getURL1();
                    product_name = Records.get(0).getName();

                    txt_product_name.setText(product_name);

                    Picasso.with(Product_Details_Activity.this)
                            .load(Records.get(0).getURL1() + "")
                            .placeholder(R.mipmap.ic_launcher_foreground)
                            .into(img_product_image);

                    img_product_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), Image_Details_Activity.class);
                            intent.putExtra("image", product_image);
                            startActivity(intent);
                        }
                    });

//                    if (Config.ENABLE_DECIMAL_ROUNDING) {
//                        String price = String.format(Locale.GERMAN, "%1$,.0f", product_price);
//                        txt_product_price.setText(price + " " + currency_code);
//                    } else {
//                        txt_product_price.setText(product_price + " " + currency_code);
//                    }
                    txt_product_price.setText(Records.get(0).getMRP2() + " ₹");
                    txt_product_quantity.setText(Integer.parseInt(Records.get(0).getMinQty()) + " " + getString(R.string.txt_qty));

                    //    if (product_status.equals("Available")) {
                    txt_product_description.setBackgroundColor(Color.parseColor("#ffffff"));
                    txt_product_description.setFocusableInTouchMode(false);
                    txt_product_description.setFocusable(false);
                    txt_product_description.getSettings().setDefaultTextEncodingName("UTF-8");

                    WebSettings webSettings = txt_product_description.getSettings();
                    Resources res = getResources();
                    int fontSize = res.getInteger(R.integer.font_size);
                    webSettings.setDefaultFontSize(fontSize);
                    webSettings.setJavaScriptEnabled(true);

                    String mimeType = "text/html; charset=UTF-8";
                    String encoding = "utf-8";
                    String htmlText = product_description;

                    String text = "<html><head>"
                            + "<style type=\"text/css\">body{color: #525252;}"
                            + "</style></head>"
                            + "<body>"
                            + htmlText + "\n" + Records.get(0).getURL1() + "\n" + Records.get(0).getURL1() + "\n" + Records.get(0).getURL1()
                            + "</body></html>";

                    String text_rtl = "<html dir='rtl'><head>"
                            + "<style type=\"text/css\">body{color: #525252;}"
                            + "</style></head>"
                            + "<body>"
                            + htmlText + "\n" + Records.get(0).getURL1() + "\n" + Records.get(0).getURL1() + "\n" + Records.get(0).getURL1()
                            + "</body></html>";

//                    if (Config.ENABLE_RTL_MODE) {
//                        txt_product_description.loadDataWithBaseURL(null, text_rtl, mimeType, encoding, null);
//                    } else {
//                        txt_product_description.loadDataWithBaseURL(null, text, mimeType, encoding, null);
//                    }
                    Tv_product_description.setText(Records.get(0).getDescription());
                    txt_product_description.loadDataWithBaseURL(null, text + "\n" + text_rtl, mimeType, encoding, null);
                    AmazonLink = Records.get(0).getAmazonLink();
                    FlipKartLink = Records.get(0).getFlipKartLink();
                } else {
                    Toast.makeText(Product_Details_Activity.this, response.body().getMessage() + "", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Product_List_Bean> call, Throwable t) {

            }
        });

    }

    public void inputDialog() {

//        try {
//            dbhelper.openDataBase();
//        } catch (SQLException sqle) {
//            throw sqle;
//        }

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);

        View mView = layoutInflaterAndroid.inflate(R.layout.pattern_input_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(mView);

        final EditText edtQuantity = mView.findViewById(R.id.userInputDialog);
        final Spinner sp_Quantity = mView.findViewById(R.id.sp_qty);


        final ArrayList<String> SP_Patch_Items = new ArrayList<String>();

        product_quantity = Integer.parseInt(Records.get(0).getMinQty());

        for (int i = 1; i < 10; i++) {
            SP_Patch_Items.add((product_quantity * i) + "");
        }
        ArrayAdapter aa = new ArrayAdapter(Product_Details_Activity.this, android.R.layout.simple_spinner_item, SP_Patch_Items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Quantity.setAdapter(aa);


        alert.setCancelable(false);
        int maxLength = 3;
        edtQuantity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        edtQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        alert.setPositiveButton(R.string.dialog_option_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String temp = edtQuantity.getText().toString();
                temp = sp_Quantity.getSelectedItem().toString();
                int quantity = 0;

                if (!temp.equalsIgnoreCase("")) {

                    quantity = Integer.parseInt(temp);

                    if (quantity < product_quantity) {
                        Toast.makeText(getApplicationContext(), "Minimum Qty " + product_quantity + " Or More", Toast.LENGTH_SHORT).show(); //+ R.string.msg_stock_below_0
                    }
                    // logic for Avaiable Product
                    if (quantity > Avaiable_Stock) {
                        Tv_Is_In_Stock.setText("Currently " + Avaiable_Stock + " unit is in Stock");
                    } else {
                        Double Product_Total = 0.0;
                        Double EX_GST_MRP = 0.0;
                        Double GST_MRP = 0.0;
                        if (dbhelper.get_Qty_By_Item_ID(Integer.parseInt(Records.get(0).getID())) > 0) {
                            quantity = quantity + dbhelper.get_Qty_By_Item_ID(Integer.parseInt(Records.get(0).getID()));
                            if (quantity <= Avaiable_Stock) {
                                // logic for Avaiable Product after newly added product count
                                Product_Total = quantity * Double.parseDouble(Records.get(0).getMRP2());
                                GST_MRP = quantity * ((Double.parseDouble(Records.get(0).getMRP2()) *Double.parseDouble( Records.get(0).getGST_Percentage())) / (100 + Double.parseDouble( Records.get(0).getGST_Percentage())));
                                EX_GST_MRP = Product_Total - GST_MRP;

                                dbhelper.updateOrder(new Order_Summery_Ofline_Bean("0",
                                        "",
                                        "",
                                        quantity + "",
                                        "",
                                        Product_Total + "", // total bill with GST
                                        Records.get(0).getGST_Percentage(),
                                        EX_GST_MRP.toString(), // without GST price
                                        GST_MRP.toString(), // GST Price
                                        Records.get(0).getURL1()), Records.get(0).getID());
                                Toast.makeText(getApplicationContext(), R.string.msg_success_add_cart, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Avaiable Stock is " + Avaiable_Stock + " Unit Qty ", Toast.LENGTH_SHORT).show(); //+ R.string.msg_stock_below_0
                            }
                        } else {
                            if (quantity <= Avaiable_Stock) {
                                Product_Total = quantity * Double.parseDouble(Records.get(0).getMRP2());
                                GST_MRP = quantity * ((Double.parseDouble(Records.get(0).getMRP2()) *Double.parseDouble( Records.get(0).getGST_Percentage())) / (100 + Double.parseDouble( Records.get(0).getGST_Percentage())));
                                EX_GST_MRP = Product_Total - GST_MRP;
                                dbhelper.addOrder(new Order_Summery_Ofline_Bean("0",
                                        Records.get(0).getID(),
                                        Records.get(0).getName(),
                                        quantity + "",
                                        Records.get(0).getMRP2(),
                                        Product_Total + "", // total bill with GST
                                        Records.get(0).getGST_Percentage(),
                                        EX_GST_MRP.toString(), // without GST price
                                        GST_MRP.toString(), // GST Price
                                        Records.get(0).getURL1()), Records.get(0).getID());
                                Toast.makeText(getApplicationContext(), R.string.msg_success_add_cart, Toast.LENGTH_SHORT).show();
                            } else {
                                Tv_Is_In_Stock.setText("Currently " + Avaiable_Stock + " unit is in Stock");
                            }
                        }

                    }

                } else {
                    dialog.cancel();
                }
            }
        });

        alert.setNegativeButton(R.string.dialog_option_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    public void setupToolbar() {

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(category_name);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Product_By_ID_API_Call(Product_ID);
    }

    public class ShareTask extends AsyncTask<String, String, String> {
        URL myFileUrl;
        Bitmap bmImg = null;
        File file;
        private Context context;
        private ProgressDialog pDialog;

        public ShareTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage(getString(R.string.loading_msg));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                myFileUrl = new URL(args[0]); //
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                String path = myFileUrl.getPath();
                String idStr = path.substring(path.lastIndexOf('/') + 1);
                File filepath = Environment.getExternalStorageDirectory();
                File dir = new File(filepath.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/");
                dir.mkdirs();
                String fileName = idStr;
                file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bmImg.compress(Bitmap.CompressFormat.PNG, 99, fos);
                fos.flush();
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String args) {

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_product_section_one) + " " + product_name + " " + getString(R.string.share_product_section_two) + " " + product_price + " " + currency_code + getString(R.string.share_product_section_three) + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, "Share Image"));
            pDialog.dismiss();

        }
    }
}