package com.example.projectmain.Fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmain.Adapter.CategoryAdapter;
import com.example.projectmain.Adapter.PostAdapter;
import com.example.projectmain.Adapter.SearchPostAdapter;
import com.example.projectmain.Adapter.UserSearchAdapter;
import com.example.projectmain.Model.Category;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Refactoring.Singleton.GlobalReactionRegistry;
import com.example.projectmain.StrategyDB.CustomSearch;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.example.projectmain.StrategyDB.SearchByContent;
import com.example.projectmain.StrategyDB.SearchByName;

import java.util.ArrayList;
import java.util.List;

public class SreachFragment extends Fragment  {

    // Khai báo biến để xác định loại tìm kiếm
    private boolean searchByNameSelected = true;
    private Spinner spinner;
    private CategoryAdapter categoryAdapter;
    public static DB db;
    AutoCompleteTextView sview;

    ArrayList<User> arrUser = new ArrayList<User>();
    ArrayList<Post> posts=new ArrayList<Post>();
    ArrayAdapter<String> a;
    RecyclerView r, rcvSearchByPost;
    //SearchPostAdapter searchPostAdapter;
    TextView tvSearch;
    public CustomSearch searchByName, searchByContent;
    public SreachFragment() {

    }


    public static SreachFragment newInstance(String param1, String param2) {
        SreachFragment fragment = new SreachFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sreach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        r = view.findViewById(R.id.rcvSearch);
        rcvSearchByPost=view.findViewById(R.id.rcvSearchByPost);
        spinner=view.findViewById(R.id.spinner);
        db = new DB(getContext().getApplicationContext());
        tvSearch = view.findViewById(R.id.tvResultCount);
        sview = (AutoCompleteTextView) view.findViewById(R.id.searchView);


        List<String> listName = db.getListName();

        String[] names = new String[listName.size()];

        for (int i =0 ;i < names.length;i++){
            names[i] = listName.get(i);
        }
        a = new ArrayAdapter<String>(getContext(), R.layout.item_list_searchview, listName);
        sview.setAdapter(a);
        a.notifyDataSetChanged();

        //adapter
        UserSearchAdapter adap = new UserSearchAdapter(getActivity(), arrUser);
       // searchPostAdapter=new SearchPostAdapter(posts,getContext());
        PostAdapter postAdapter=new PostAdapter(getActivity(),posts, GlobalReactionRegistry.getInstance().getRegistry());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager g = new LinearLayoutManager(view.getContext());
        rcvSearchByPost.setLayoutManager(layoutManager);
        rcvSearchByPost.setAdapter(postAdapter);

        r.setLayoutManager(g);
        r.setAdapter(adap);
        a.notifyDataSetChanged();

        //refactoring
         searchByName=new CustomSearch(new SearchByName(getContext()));
         searchByContent=new CustomSearch(new SearchByContent(getContext()));

         spinner=view.findViewById(R.id.spinner);
         categoryAdapter=new CategoryAdapter(getContext(),R.layout.item_search_selected,getListCaterogy());
         spinner.setAdapter(categoryAdapter);
         spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Toast.makeText(getContext(), categoryAdapter.getItem(position).getName() , Toast.LENGTH_SHORT).show();
                 String selectedCategory = categoryAdapter.getItem(position).getName();
                 if(selectedCategory.equals("Tìm kiếm theo tên")){
                     searchByNameSelected = true;
                     r.setVisibility(View.VISIBLE);
                     rcvSearchByPost.setVisibility(View.GONE);
                     sview.setHint("Tìm kiếm theo tên người dùng");
                     sview.setText("");
                     sview.requestFocus();


                 }else if (selectedCategory.equals("Tìm kiếm theo bài viết")) {
                     searchByNameSelected = false;
                     rcvSearchByPost.setVisibility(View.VISIBLE);
                     r.setVisibility(View.GONE);
                     sview.setHint("Tìm kiếm theo nội dung bài viết");
                     sview.setText("");

                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

//        sview.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //tvRead.setText(autoCl.getText().toString());
//
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(sview.getText().toString().equals("")){
//                    tvSearch.setText("Vui lòng nhập từ khóa.");
//                    arrUser.clear();
//                    posts.clear();
//                    searchPostAdapter.notifyDataSetChanged();
//                    adap.notifyDataSetChanged();
//                    return;
//                }
//                updateDataPost(sview.getText().toString());
//                updateData(sview.getText().toString());
//                adap.notifyDataSetChanged();
//                searchPostAdapter.notifyDataSetChanged();
//                tvSearch.setText("Đang hiển thị " + arrUser.size() + " kết quả khớp với từ khóa \"" + sview.getText().toString() + "\"");
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });





        sview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //tvRead.setText(autoCl.getText().toString());

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(sview.getText().toString().equals("")){
                    tvSearch.setText("Vui lòng nhập từ khóa.");
                    arrUser.clear();
                    if (searchByNameSelected){
                        adap.notifyDataSetChanged();
                    }
                    else {
                        posts.clear();
                        postAdapter.notifyDataSetChanged();
                    }
                    return;
                }

                if (searchByNameSelected) {
                    updateData(sview.getText().toString());
                    adap.notifyDataSetChanged();
                    tvSearch.setText("Đang hiển thị " + arrUser.size() + " kết quả khớp với từ khóa \"" + sview.getText().toString() + "\"");
                } else {
                    updateDataPost(sview.getText().toString());
                    postAdapter.notifyDataSetChanged();
                    tvSearch.setText("Đang hiển thị " + posts.size() + " kết quả khớp với từ khóa \"" + sview.getText().toString() + "\"");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }

    private List<Category> getListCaterogy() {
        List<Category> list=new ArrayList<>();
        list.add(new Category("Tìm kiếm theo tên"));
        list.add(new Category("Tìm kiếm theo bài viết"));
        return list;
    }


    @SuppressLint("Range")
    void updateDataPost(String k){

        if(posts != null){
            posts.clear();
        } else {
            posts = new ArrayList<Post>();
        }
//        Cursor c = db.getUserFromSearch(k);
        Cursor c= searchByContent.performSearch(k);

        while(c.moveToNext()){
            Post post = new Post();
            post.setId(c.getInt(0));
            post.setIduser(c.getInt(1));
            post.setImgPost(c.getString(3));
            post.setContent(c.getString(2));
            post.setUsername(c.getString(10));
            post.setName(c.getString(10));
            post.setAvatar(c.getString(11));
            posts.add(post);
        }
        Log.d("Length", String.valueOf(arrUser.size()));
    }

    @SuppressLint("Range")
    void updateData(String k){

        if(arrUser != null){
            arrUser.clear();
        } else {
            arrUser = new ArrayList<User>();
        }
//        Cursor c = db.getUserFromSearch(k);
        Cursor c= searchByName.performSearch(k);
        while(c.moveToNext()){
            arrUser.add(new User(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("description"))));
        }
        Log.d("Length", String.valueOf(arrUser.size()));
    }
    
}