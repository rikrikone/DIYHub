package com.example.diyhub.Notifications;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.diyhub.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NotificationPromo extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView notif;

    List<PromoProducts> mProducts;
    DatabaseReference reference;

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView backButton;
    NotificationPromoAdapter notificationPromoAdapter;
    List<NotificationPromoList> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_promo);


        backButton = findViewById(R.id.backButtonNotifPage);

        recyclerView = findViewById(R.id.recyclerNotifList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Notifications").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    NotificationPromoList notiflist = snapshot.getValue(NotificationPromoList.class);
                    list.add(notiflist);

                }

                notificationPromoAdapter = new NotificationPromoAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(notificationPromoAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String id = UUID.randomUUID().toString();
        String cutid = id.substring(0,11);




        /*
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> map = new HashMap<>();
        map.put("IsSeen", "false");
        map.put("NotifDateAndTime", "05/04/2022 10:57PM");
        map.put("NotifDescription", "John Ford Kilat is now following you");
        map.put("NotifHeader", "Follow Notifications");
        map.put("NotifID", cutid);
        map.put("NotifImage","https://logos.flamingtext.com/Word-Logos/following-design-amped-logo.webp");
        reference.child("Notifications").child(user.getUid()).child(cutid).setValue(map);

         */








    }



}