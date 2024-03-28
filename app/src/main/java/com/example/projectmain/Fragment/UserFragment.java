package com.example.projectmain.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.projectmain.Adapter.UserPostAdapter;
import com.example.projectmain.Refactoring.SingletonColorChange.ColorManager;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.example.projectmain.Refactoring.Proxy.UserManager;
import com.example.projectmain.Refactoring.Proxy.UserProxy;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import android.annotation.SuppressLint;
import android.widget.Toast;

import java.util.ArrayList;

public class UserFragment extends Fragment {


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    SharedPreferences sharedPreferences;
    UserProxy proxy;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IMAGE_LINK = "linkImage";

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    TextView mtvUsername, mtvFollowingCount, mtvFollowerCount, mtvPostCount, mtvDes;
    Button mbtnLogout;
    DB db;
    User user;
    ImageView avatarMain;
    ArrayList<Post> posts;
//    int[] imageRes = {R.drawable.imgquang, R.drawable.meo, R.drawable.imgcrew, R.drawable.imgpc};
    String name;
    TabLayout tlPostType;

    ImageButton imageButton;
    TextView userFollow ,userFollowing, posted , tvborder1 , tvborder2;

    TextView tvContent;
    RecyclerView rcvPosts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DB(getContext());




//        for (int r : imageRes) {
//            list.add(new Image(r));
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
    UserPostAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proxy = new UserProxy(new UserManager(getActivity(), user), getActivity());
        tlPostType = view.findViewById(R.id.tlPostType);
        avatarMain = view.findViewById(R.id.avatar_main);
        mtvUsername = view.findViewById(R.id.tvName);
        mtvFollowingCount = view.findViewById(R.id.tvFollowing);
        mtvFollowerCount = view.findViewById(R.id.tvFollowerCount);
        mtvPostCount = view.findViewById(R.id.tvPostCount);
        mtvDes = view.findViewById(R.id.tvDes);
        db = new DB(getActivity());
        user = GlobalUser.getInstance(getActivity()).getUser();
        name = user.getName();
        String email = user.getName(); //email của bố m đâu????
        posts = new ArrayList<Post>();


        rcvPosts = view.findViewById(R.id.rcvPosts);

        tvborder1 = view.findViewById(R.id.tvborder1);
        tvborder2 = view.findViewById(R.id.tvborder2);

        userFollow = view.findViewById(R.id.followuser);
        userFollowing = view.findViewById(R.id.following);
        posted = view.findViewById(R.id.post);
        imageButton = view.findViewById(R.id.imageButton2);


        tvContent = view.findViewById(R.id.tvContent);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị menu khi người dùng nhấn vào ImageButton
                showPopupMenu(v);
            }
        });
        try{
            listImgPost();
        } catch (NullPointerException exception){
            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Uri link = null;
        int quantityCountFollowing = db.CountFollowing(user.getId());
        int myfolloerCount = db.CountMyFollower(user.getId());
        String strImageAvatar = db.getImagefor(user.getId()); //???? //code dirty kinh bây
//        if (linkImage == null) {
//            link = null;
//        } else
        if (name != null) {
            if (strImageAvatar == null) {
                avatarMain.setImageResource(R.drawable.def);
            } else{
                link = Uri.parse(strImageAvatar);
                avatarMain.setImageURI(link);
            }
            mtvUsername.setText(user.getName());
            mtvDes.setText(user.getDescription());
            mtvPostCount.setText(String.valueOf(CountPost(db.getIduser(name))));
            mtvFollowingCount.setText(String.valueOf(quantityCountFollowing));
            mtvFollowerCount.setText(String.valueOf(myfolloerCount));
        }

        //   ArrayList<String> list = (ArrayList<String>) ListImgPost(db.getIduser(name));

        adapter = new UserPostAdapter(getActivity().getApplicationContext(), posts, getActivity());
        ViewPager2 r = getView().findViewById(R.id.vpPosts);
        r.setNestedScrollingEnabled(false);
        r.setAdapter(adapter);
        new TabLayoutMediator(tlPostType, r, ((tab, position) -> {
            if(position == 0){
                tab.setIcon(R.drawable.mountain_sun_solid);
            } else {
                tab.setIcon(R.drawable.font_solid);
            }
        })).attach();
        tlPostType.setTabIconTintResource(isColorDark(ColorManager.getInstance().getBackgroundColor()) ? R.color.white : R.color.text);
        setFragmentBackgroundColor(ColorManager.getInstance().getBackgroundColor());
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Xử lý sự kiện khi người dùng chọn một mục từ menu
                switch (item.getItemId()) {
                    case R.id.color_white:
                        ColorManager.getInstance().setBackgroundColor(Color.WHITE);
                        setFragmentBackgroundColor(ColorManager.getInstance().getBackgroundColor());
                        tlPostType.setTabIconTintResource(isColorDark(ColorManager.getInstance().getBackgroundColor()) ? R.color.white : R.color.text);
                        break;
                    case R.id.color_yellow:
                        ColorManager.getInstance().setBackgroundColor(Color.YELLOW);
                        setFragmentBackgroundColor(ColorManager.getInstance().getBackgroundColor());
                        tlPostType.setTabIconTintResource(isColorDark(ColorManager.getInstance().getBackgroundColor()) ? R.color.white : R.color.text);
                        break;
                    case R.id.color_red:
                        ColorManager.getInstance().setBackgroundColor(Color.RED);
                        setFragmentBackgroundColor(ColorManager.getInstance().getBackgroundColor());
                        tlPostType.setTabIconTintResource(isColorDark(ColorManager.getInstance().getBackgroundColor()) ? R.color.white : R.color.text);
                        break;

                    case R.id.color_blue:
                        ColorManager.getInstance().setBackgroundColor(Color.BLUE);
                        setFragmentBackgroundColor(ColorManager.getInstance().getBackgroundColor());
                        tlPostType.setTabIconTintResource(isColorDark(ColorManager.getInstance().getBackgroundColor()) ? R.color.white : R.color.text);
                        break;
                    default:
                        return false;
                }
                adapter.notifyItemChanged(0);
                adapter.notifyItemChanged(1);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_color);
        popupMenu.show();


    }

    public void setFragmentBackgroundColor(int color) {
        getView().setBackgroundColor(color);
        tlPostType.setBackgroundColor(color);


        int textColor = Color.BLACK; // Mặc định là màu đen
        if (isColorDark(color)) {
            textColor = Color.WHITE; // Chuyển sang màu trắng nếu nền là màu tối
        }
        mtvUsername.setTextColor(textColor);
        mtvFollowingCount.setTextColor(textColor);
        mtvFollowerCount.setTextColor(textColor);
        mtvPostCount.setTextColor(textColor);
        mtvDes.setTextColor(textColor);
        userFollow.setTextColor(textColor);
        userFollowing.setTextColor(textColor);
        posted.setTextColor(textColor);

        tvborder1.setBackgroundColor(textColor);
        tvborder2.setBackgroundColor(textColor);




    }
    private boolean isColorDark(int color) {
        // Lấy giá trị trung bình của các thành phần màu (R, G, B) để xác định màu sáng hay màu tối
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5; // Trả về true nếu màu là màu tối, ngược lại trả về false
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Không làm gì ở đây để tránh hiển thị menu mặc định
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        String strImageAvatar = db.getImagefor(user.getId());
//        if (linkImage == null) {
//            link = null;
//        } else
        Uri link = null;
        if (strImageAvatar == null) {
            avatarMain.setImageResource(R.drawable.def);
        } else{
            link = Uri.parse(strImageAvatar);
            avatarMain.setImageURI(link);
        }
    }

    public int CountPost(int idUser) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursorCount = database.query("post", null, "idUser = ?", new String[]{String.valueOf(idUser)}, null, null, null);
        int count = 0;
        while (cursorCount.moveToNext()) {
            count++;
        }

        return count;
    }

    @SuppressLint("Range")
    public void listImgPost() {
        ArrayList<Post> temp = new ArrayList<>();
        temp = proxy.getOwnPosts();
        if(temp == null){
            throw new NullPointerException("Người dùng không có quyền truy cập bài đăng.");
        } else {
            posts = temp;
        }
    }



}