package com.example.diyhub.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diyhub.Adapter;
import com.example.diyhub.Buyer.ItemViewModelCat1;
import com.example.diyhub.Buyer.ItemViewModelCat2;
import com.example.diyhub.Buyer.ItemViewModelCat3;
import com.example.diyhub.Buyer.ItemViewModelCustomSpecsText;
import com.example.diyhub.R;
import com.example.diyhub.ViewPageAdapterProductDetailsStandard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailCustomPagePreview extends AppCompatActivity {

    Spinner firstSpinner;
    Spinner secondSpinner;
    List<String> list;
    ArrayAdapter<String> adapter;
    List<String> listSecond;
    ArrayAdapter<String> adapterSecond;
    ViewPager viewPager;
    String prodID;

    TextView customPrevFirstTxt;
    ImageView customPrevFirstImage;

    List<OrderCustomizationsSpecs> specsList;


    List<ProductDetailsImagesList> prodImagesList;

    List<OrderCustomerUploads> uploadsList;

    ImageView customPrevSecondImage;
    ImageView customPrevThirdImage;

    List<OrderAllItemSpecs> allItemSpecs;
    List<String> allItemSpecsList;

    ImageView backButton;
    String prodImage;

    ImageView productImage;

    View view;

    Spinner category1Spinner, category2Spinner, category3Spinner;
    List<OrderCustomListSeller> listCat1;
    List<OrderCustomListSeller> listCat2;
    List<OrderCustomListSeller> listCat3;

    AddCustomSpecsSellerPreviewAdapter adapterCat1;
    AddCustomSpecsSellerPreviewAdapter adapterCat2;
    AddCustomSpecsSellerPreviewAdapter adapterCat3;

    String sellerID;
    ImageView productImageImageView;

    FloatingActionButton pickImage1;
    FloatingActionButton pickImage2;
    private static final int SELECT_PHOTOGOV_VARIATIONCAT1 = 1;
    private static final int SELECT_PHOTOGOV_VARIATIONCAT2 = 2;
    private static final int SELECT_PHOTOGOV_VARIATIONCAT3 = 3;

    Uri imageUriVariationCat1;
    Uri imageUriVariationCat2;
    Uri imageUriVariationCat3;



    private ArrayList<Uri> ImageListVariation = new ArrayList<Uri>();
    StorageReference firebaseStorage;
    int uploadsVariation = 0;
    int uploadsProduct = 0;

    List<String> fileNameListVar;

    ImageView imageview1,imageView2;
    String bookfrom;
    double rating;
    double sold;
    int stock;
    String description;
    String shopName;
    TextView descriptionProductCustom;
    RatingBar ratingBarBuyerCustom;
    TextView ratingNumCustom;
    TextView productSoldStandardProductBuyerCustom;
    TextView stockProductCustom;
    TextView reviewsCustom;
    EditText customSpecsTxtBuyerCustom;
    TextView bookFromProductCustom;

    String category1Equi,category2Equi,category3Equi;
    Spinner thirdSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_custom_page_preview);

        firstSpinner = findViewById(R.id.firstSpinnerCustomPreview);
        secondSpinner = findViewById(R.id.secondSpinnerCustomPreview);
        thirdSpinner = findViewById(R.id.thirdSpinnerCustomPreview);
        productImage = findViewById(R.id.customPreviewProductImage);
        customPrevFirstImage = findViewById(R.id.customPreviewFirstImage);
        customPrevSecondImage = findViewById(R.id.customPrevSecondImage);
        customPrevThirdImage = findViewById(R.id.customPrevThirdImage);
        backButton = findViewById(R.id.backButtonOrderDetailCustomPreview);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            prodID = extras.getString("ProductID");
            prodImage = extras.getString("ProductImage");
        }

        Glide.with(getApplicationContext()).load(prodImage).into(productImage);


        listCat1 = new ArrayList<>();
        listCat2 = new ArrayList<>();
        listCat3 = new ArrayList<>();
        fileNameListVar = new ArrayList<>();



        showDataCat1();
        showDataCat2();
        showDataCat3();
    }
    public void showDataCat1()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders").child(user.getUid()).child(prodID).child("OrderCustomizationsSpecs").child("Category-1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCat1.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    OrderCustomListSeller addCustomSpecsSellerList = snapshot.getValue(OrderCustomListSeller.class);
                    Glide.with(getApplicationContext()).load(addCustomSpecsSellerList.getSpecsName()).into(customPrevFirstImage);
                    listCat1.add(addCustomSpecsSellerList);
                }
                adapterCat1 = new AddCustomSpecsSellerPreviewAdapter(getApplicationContext(), listCat1);
                firstSpinner.setAdapter(adapterCat1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDataCat2()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders").child(user.getUid()).child(prodID).child("OrderCustomizationsSpecs").child("Category-2");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCat2.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    OrderCustomListSeller addCustomSpecsSellerList = snapshot.getValue(OrderCustomListSeller.class);
                    Glide.with(getApplicationContext()).load(addCustomSpecsSellerList.getSpecsName()).into(customPrevSecondImage);
                    listCat2.add(addCustomSpecsSellerList);
                }
                adapterCat2 = new AddCustomSpecsSellerPreviewAdapter(getApplicationContext(), listCat2);
                secondSpinner.setAdapter(adapterCat2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDataCat3()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders").child(user.getUid()).child(prodID).child("OrderCustomizationsSpecs").child("Category-3");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCat3.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    OrderCustomListSeller addCustomSpecsSellerList = snapshot.getValue(OrderCustomListSeller.class);
                    Glide.with(getApplicationContext()).load(addCustomSpecsSellerList.getSpecsName()).into(customPrevThirdImage);
                    listCat3.add(addCustomSpecsSellerList);
                }
                adapterCat3 = new AddCustomSpecsSellerPreviewAdapter(getApplicationContext(), listCat3);
                thirdSpinner.setAdapter(adapterCat3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}