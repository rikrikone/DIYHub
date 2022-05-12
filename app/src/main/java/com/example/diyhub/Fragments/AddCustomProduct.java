package com.example.diyhub.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.diyhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddCustomProduct extends AppCompatActivity {

    Button addCustomSpecs;

    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    EditText productName,productQuantity,productStocks;
    String playImageStatus = "https://firebasestorage.googleapis.com/v0/b/diy-hub-847fb.appspot.com/o/johnfordtapales8%40gmail.com%2Fillust58-7479-01-removebg-preview.png?alt=media&token=a322e775-e3fd-4fb3-8d77-3767a348120d";
    String pauseImageStatus = "https://firebasestorage.googleapis.com/v0/b/diy-hub-847fb.appspot.com/o/johnfordtapales8%40gmail.com%2Fpause__video__stop-removebg-preview.png?alt=media&token=ec3433a8-9579-4aca-8faa-6fe1c58d8474";
    String id;
    String cutid;

    Button addVariations;

    EditText prodDescriptionTxt,prodMaterialTxt,prodPriceTxt,prodSoldTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_product);


        productName = findViewById(R.id.addProdNameCustom);
        productQuantity = findViewById(R.id.addProdQuantityCustom);
        productStocks = findViewById(R.id.addProdStocksCustom);
        addVariations = findViewById(R.id.addVariationsCustom);
        prodDescriptionTxt = findViewById(R.id.productDescriptionTxtAddCustom);
        prodMaterialTxt = findViewById(R.id.productMaterialsUsedAddCustom);
        prodPriceTxt = findViewById(R.id.productPriceAddCustom);
        prodSoldTxt = findViewById(R.id.productSoldAddCustom);
        addCustomSpecs = findViewById(R.id.addSpecificationCustom);



        id = UUID.randomUUID().toString();
        cutid = id.substring(0,11);



        addVariations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prodName = productName.getText().toString().trim().isEmpty() ? "" : productName.getText().toString().trim();
                String pQuans = productQuantity.getText().toString().trim().isEmpty() ? "0" : productQuantity.getText().toString().trim();
                String pStocks = productStocks.getText().toString().trim().isEmpty() ? "0" : productStocks.getText().toString().trim();
                String description = prodDescriptionTxt.getText().toString().trim();
                String materialUsed = prodMaterialTxt.getText().toString().trim();
                double price = prodPriceTxt.getText().toString().trim().isEmpty() ? 0 : Double.parseDouble(prodPriceTxt.getText().toString().trim());
                double sold = prodSoldTxt.getText().toString().trim().isEmpty() ? 0 : Double.parseDouble(prodSoldTxt.getText().toString().trim());

                int pQUan = Integer.parseInt(pQuans);
                int pSTock = Integer.parseInt(pStocks);

                if(pQUan== 0)
                {
                    productQuantity.setError("Product Quantity should be greater than 0");
                    productQuantity.requestFocus();
                }
                else if(pSTock == 0)
                {
                    productStocks.setError("Product Stocks should greater than 0");
                    productStocks.requestFocus();
                }
                else if(productName.getText().toString().trim().isEmpty())
                {
                    productName.setError("Product Name is Required");
                    productName.requestFocus();
                    return;
                }
                else if(productQuantity.getText().toString().trim().isEmpty())
                {
                    productQuantity.setError("Product Quantity is Required");
                    productQuantity.requestFocus();
                    return;
                }
                else if(productStocks.getText().toString().trim().isEmpty())
                {
                    productStocks.setError("Product Stocks is Required");
                    productStocks.requestFocus();
                    return;
                }
                else if(prodDescriptionTxt.getText().toString().trim().isEmpty())
                {
                    prodDescriptionTxt.setError("Product Description is Required");
                    prodDescriptionTxt.requestFocus();
                    return;
                }
                else if(prodMaterialTxt.getText().toString().trim().isEmpty())
                {
                    prodMaterialTxt.setError("Product Material is Required");
                    prodMaterialTxt.requestFocus();
                    return;
                }
                else if(prodPriceTxt.getText().toString().trim().isEmpty())
                {
                    prodPriceTxt.setError("Product Price is Required");
                    prodPriceTxt.requestFocus();
                    return;
                }
                else if(prodSoldTxt.getText().toString().trim().isEmpty())
                {
                    prodSoldTxt.setError("Product Sold is Required");
                    prodSoldTxt.requestFocus();
                    return;
                }
                else if(pQUan > pSTock)
                {
                    productQuantity.setError("Product Quantity should not be greater than Product Stocks");
                    productQuantity.requestFocus();
                    return;
                }
                else {

                    if(pQUan == pSTock)
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Map<String,Object> sellerProductsfb = new HashMap<>();
                        sellerProductsfb.put("ProductName", prodName);
                        sellerProductsfb.put("ProductQuantity", pQUan);
                        sellerProductsfb.put("ProductStocks", pSTock);
                        sellerProductsfb.put("ProductID", cutid);
                        sellerProductsfb.put("ProductStatus", "Hold");
                        sellerProductsfb.put("ProductType", "Customizable");
                        sellerProductsfb.put("ProductStatusImage", playImageStatus);
                        sellerProductsfb.put("ProductDescription", description);
                        sellerProductsfb.put("ProductMaterial", materialUsed);
                        sellerProductsfb.put("ProductPrice", price);
                        sellerProductsfb.put("ProductSold", sold);

                        reference.child("SellerProducts").child(user.getUid()).child(cutid).setValue(sellerProductsfb);
                    }
                    else
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        Map<String,Object> sellerProductsfb = new HashMap<>();
                        sellerProductsfb.put("ProductName", prodName);
                        sellerProductsfb.put("ProductQuantity", pQUan);
                        sellerProductsfb.put("ProductStocks", pSTock);
                        sellerProductsfb.put("ProductID", cutid);
                        sellerProductsfb.put("ProductStatus", "Active");
                        sellerProductsfb.put("ProductStatusImage", pauseImageStatus);
                        sellerProductsfb.put("ProductType", "Customizable");
                        sellerProductsfb.put("ProductDescription", description);
                        sellerProductsfb.put("ProductMaterial", materialUsed);
                        sellerProductsfb.put("ProductPrice", price);
                        sellerProductsfb.put("ProductSold", sold);
                        reference.child("SellerProducts").child(user.getUid()).child(cutid).setValue(sellerProductsfb);
                    }

                    Intent intent = new Intent(AddCustomProduct.this, AddVariationsCustomizePage.class);
                    intent.putExtra("itemid", cutid);
                    intent.putExtra("ProductName", prodName);
                    intent.putExtra("ProductStocks", pSTock);
                    intent.putExtra("ProductQuantity", pQUan);
                    startActivity(intent);
                }

            }
        });

        addCustomSpecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCustomProduct.this, AddCustomSpecificationPage.class);
                startActivity(intent);
            }
        });




    }
}