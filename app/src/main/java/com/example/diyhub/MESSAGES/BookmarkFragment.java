package com.example.diyhub.MESSAGES;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diyhub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookmarkFragment extends Fragment {


    private BookmarkAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private List<ChatList> usersList;

    RecyclerView recyclerView;

    EditText searchMessage;

    ArrayList<User> filterlist = new ArrayList<>();

    private List<String> userslist1;

    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);



        recyclerView = view.findViewById(R.id.recyclerViewBookmarks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        searchView = view.findViewById(R.id.searchBookmark);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Bookmarks").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    usersList.add(chatList);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }


    private void chatList() {

        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    for (ChatList chatList : usersList) {
                        if (user.getId().equals(chatList.getId())) {
                            mUsers.add(user);
                        }
                    }
                }

                userAdapter = new BookmarkAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        if(searchView != null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String text) {
        ArrayList<User> filterList = new ArrayList<>();
        for(User list : mUsers)
        {
            if(list.getUsername().toLowerCase().contains(text.toLowerCase()))
            {
                filterList.add(list);
            }
        }
        BookmarkAdapter adapter = new BookmarkAdapter(getContext(),filterList);
        recyclerView.setAdapter(adapter);

    }
}