package com.example.projectmain.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.projectmain.Adapter.PostAdapter;
import com.example.projectmain.Class.Post;
import com.example.projectmain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView recyclerView;
    PostAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);



    }
    ImageButton btnHeart;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.render);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter(getContext(),listPost());

        recyclerView.setAdapter(adapter);

//         btnHeart = (ImageButton) view.findViewById(R.id.btn_like);
//
//        btnHeart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    public List<Post> listPost(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(R.drawable.avatarquang, new Post(R.drawable.avatarduong, R.drawable.imgquang, "sugar", "Gia Đường", "2000", "Test", "10 ngày trước", null), "quang", "Trí Quang", "20", "Hello", "2 ngày trước"));
        posts.add(new Post(R.drawable.avatarquang, R.drawable.imgquang, "@wuang:", "Trí Quang", "1.1m", "đặc cầu tulen", "30 phút trước", null));
        posts.add(new Post(R.drawable.avatarduong, R.drawable.imgduong, "@Sugar:", "Gia Đường", "1.8m", "hello hi hi", "vài phút trước", null));
        posts.add(new Post(R.drawable.avatarvau, R.drawable.imgden, "@den:", "Đen Vâu", "1.1k", null, "1 ngày trước", null));
        posts.add(new Post(R.drawable.avatarquang, -1, "@wuang:", "Trí Quang", "1.1m", "Cùng với quá trình toàn cầu hóa và sự phát triển của công nghệ thông tin, mạng internet trên thế giới và Việt Nam ngày càng phát triển mạnh mẽ, nó đã bao trùm hầu như toàn bộ các lãnh thỗ trên thế giới và có khoảng 66% dân số trên thế giới đã và đang sử dụng internet, nhu cầu chia sẽ thông tin, kết nối bạn bè là nhu cầu thúc đẩy sự ra đời và phát triển của mạng xã hội.", "30 phút trước", "Đặc cầu"));
        posts.add(new Post(R.drawable.avatarduong, -1, "@Sugar:", "Gia Đường", "1.8m", "hello hi hi", "vài phút trước", "null"));
        posts.add(new Post(R.drawable.avatarvau, R.drawable.imgden, "@den:", "Đen Vâu", "1.1k", "nhạc anh bao cháy dìa dia", "1 ngày trước", "Đen thui luôn"));
        return posts;
    }
}