package com.example.projectmain.Adapter;

import static com.example.projectmain.Fragment.DiscoverFragment.recyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmain.Database.DB;
import com.example.projectmain.EditPostActivity;
import com.example.projectmain.ImageActivity;
import com.example.projectmain.LikeActivity;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.Model.TimeHelper;
import com.example.projectmain.PostDetailActitivty;
import com.example.projectmain.R;
import com.example.projectmain.Refactoring.Command.Command;
import com.example.projectmain.Refactoring.Command.DeleteCommand;
import com.example.projectmain.Refactoring.Command.UndoCommand;
import com.example.projectmain.Refactoring.Prototype.IReaction;
import com.example.projectmain.Refactoring.Prototype.IReactionRegistry;
import com.example.projectmain.Refactoring.Prototype.ReactionRegistry;
import com.example.projectmain.UserActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kotlin.Unit;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Cursor likes;

    public PostAdapter(Context context, List<Post> posts, IReactionRegistry reactionRegistry) {
        this.posts = posts;
        this.context = context;
        if (posts.size() == 0) {
            Size = 1;
        } else {
            Size = posts.size();
        }
        this.reactionRegistry = reactionRegistry;
    }

    IReactionRegistry reactionRegistry = new ReactionRegistry();
    int Size;
    Context context;
    List<Post> posts;
    List<User> users;
    List<String> listName;
    DB db;
    CheckBox btnLike;
    User user;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_IMAGE_LINK = "linkImage";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    String email = null;
    String name = null;




    /*
     * Lấy kiểu view
     * Hàm này được dùng để lấy kiểu view
     * Tham số: position: int: Dùng để lấy vị trí hiện tại đang xét
     * Các kiểu view:
     * 0: Post có hình nhưng ko caption
     * 1: Post có caption ngắn
     * 2: Post có caption dài
     * 3: Post vừa có hình, vừa có caption
     * */

    @Override
    public int getItemViewType(int position) {
        if (posts.size() == 0)
            return -2;
        if (position >= posts.size()) {
            return -3;
        }
        String postContent = posts.get(position).getContent();

        String img = posts.get(position).getImgPost();
        Post childPost = posts.get(position).getSharedPost();

        if (childPost != null) {
            return 5;
        }
        if (postContent == null && !img.equals("null")) {
            return 0;
        }
        if (postContent.length() <= 50 && img.equals("null")) {
            return 1;
        }
        if (postContent.length() >= 50 && img.equals("null")) {
            return 2;
        }
        if (postContent != null && !img.equals("null")) {
            return 3;
        }
        return 4;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        visited = new Boolean[posts.size()];
        if (posts.size() == 0) {
            return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.error, parent, false));
        }
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_img_notext, parent, false);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_small_paragraph, parent, false);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_large_paragraph, parent, false);
        } else if (viewType == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        } else if (viewType == 5) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_post, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error, parent, false);

        return new PostViewHolder(view);
    }

    @SuppressLint({"SuspiciousIndentation", "SetTextI18n", "Range"})
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (getItemViewType(position) == -3) {
            return;
        }
        if (posts.size() == 0) {
            holder.tvError.setText("Không có bài viết");
            holder.tvErrorMsg.setText("Chưa có ai đăng bài ở đây cả!.");
            return;
        }
        db = new DB(context.getApplicationContext());
        Post childPost = posts.get(position).getSharedPost();
        Post post = posts.get(position);
        //   User user = users.get(position);
        int type = getItemViewType(position);
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE_LINK, post.getImgPost());
        email = sharedPreferences.getString(KEY_EMAIL, null);
        name = sharedPreferences.getString(KEY_NAME, null);

        user = db.getUser(email);
        editor.apply();

        if (post == null)
            return;

        String ava = post.getAvatar();
        if (ava == null || ava.equals("null")) {
            holder.avatar.setImageResource(R.drawable.def);
        } else
            holder.avatar.setImageURI(Uri.parse(ava));
        // holder.imgPost.setImageURI(Uri.parse(post.getImgPost()));
        holder.name.setText(post.getName());
        holder.userName.setText(post.getUsername());
        holder.numberLike.setText(String.valueOf(db.getLike(post.getId()).getCount()));
        holder.content.setText(post.getContent());

        Time now = new Time(position);
        String state = post.getStatePost() == 1 ? " (Đã chỉnh sửa)" : "";
        holder.time.setText(post.getTime() + state);

        holder.btnShowProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, UserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                b.putInt("idUser", post.getIduser());
                i.putExtras(b);
                context.startActivity(i);
            }
        });
        if (type == 0) {
            //View 0: Hình ko caption
            /*
             * Hình ko caption, chỉ cần setImage và setOnClick cho imgPost, không cần set cho tvContent thứ khác (sẽ gây nullPointerException)
             */
            //  holder.imgPost.setImageResource(Integer.parseInt(post.getImgPost()));


            holder.imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ImageActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bd = new Bundle();
                    bd.putString("ImgRes", post.getImgPost());
                    bd.putString("ImgPoster", post.getName());
                    bd.putString("ImgUsername", post.getUsername());
                    bd.putString("ImgPfp", ava);
                    bd.putInt("idPost", post.getId());
                    i.putExtras(bd);
                    context.startActivity(i);
                }
            });
        } else if (type == 5) {
            if (post.getId() == 0) {
                holder.tvTime.setText("Bài đăng không tồn tại");
                holder.ivSharedImage.setImageResource(R.drawable.circle_question_solid);
                holder.tvSharedOwner.setText("Bài đăng không tồn tại");
                holder.time.setText("Bài đăng không tồn tại");
                holder.tvSharedCaption.setText("Bài đăng không tồn tại!");
                holder.ivSharedImage.setVisibility(View.GONE);
                holder.btnComment.setVisibility(View.GONE);
                holder.btnLike.setVisibility(View.GONE);
                holder.numberLike.setText("-1");
                return;
            }
            int id = db.getIduser(name);
            holder.ivSharedImage.setVisibility(View.VISIBLE);
            holder.btnComment.setVisibility(View.VISIBLE);
            holder.btnLike.setVisibility(View.VISIBLE);
            holder.numberLike.setText(String.valueOf(db.getLike(post.getId()).getCount()));
            holder.tvSharedOwner.setText("@" + childPost.getUsername());
            holder.tvTime.setText(TimeHelper.getTime(childPost.getTime()));
            // holder.tvSharedCaption.setText(childPost.getContent());
            Log.d("idPost", String.valueOf(post.getId()));

            holder.tvSharedLikeCount.setText(childPost.getNumber_like());
            Uri u;
            if (!childPost.getImgPost().equals("null")) {
                u = Uri.parse(childPost.getImgPost());
                holder.ivSharedImage.setImageURI(u);
            } else {
                holder.ivSharedImage.setVisibility(View.GONE);
            }
            holder.ivSharedImage.setOnClickListener(v -> {

            });

            holder.tvSharedCaption.setText(childPost.getContent());
            // holder.ivSharedImage.setImageURI(Uri.parse(childPost.getImgPost()));
            //holder.content.setText(childPost.getContent());
            //    holder.nameUserPost.setText(childPost.getUsername());

            holder.avatar.setImageURI(Uri.parse(post.getAvatar()));
            holder.name.setText(post.getName());
            holder.userName.setText(post.getUsername());
            holder.time.setText(post.getTime());

            holder.btnComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, String.valueOf(childPost.getId()), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, PostDetailActitivty.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bn = new Bundle();
                    if (type == 0) {
                        bn.putString("Img", post.getImgPost());
                    } else if (type == 1 || type == 2) {
                        bn.putString("Content", post.getContent());
                    } else if (type == 3) {
                        bn.putString("Content", post.getContent());
                        bn.putString("Img", post.getImgPost());
                    }
                    bn.putString("Username", post.getUsername());
                    bn.putString("Pfp", post.getAvatar());
                    bn.putString("Name", post.getName());
                    bn.putBoolean("IsCmt", true);
                    bn.putInt("ViewType", type);
                    i.putExtras(bn);
                    context.startActivity(i);
                }
            });


        } else if (type == 1 || type == 2) {
            //View 1, View 2: Có caption nhưng ko có hình
            /*
             * Post chỉ có mỗi caption, nên chỉ setText cho mỗi caption, cố gắng setImage sẽ gây nullPointerException
             */
            holder.content.setText(post.getContent());
        } else if (type == 3) {
            //View 1, View 2: Có caption và hình
            /*
             * Post có cả 2 caption và hình, setImageResource và setText cho imgPost và content bình tường
             */
            holder.imgPost.setImageURI(Uri.parse(post.getImgPost()));
            Log.d("d", post.getImgPost());
            holder.content.setText(post.getContent());
            holder.imgPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ImageActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bd = new Bundle();
                    bd.putString("ImgRes", post.getImgPost());
                    bd.putString("ImgPoster", post.getName());
                    bd.putString("ImgUsername", post.getUsername());
                    bd.putString("ImgPfp", ava);
                    bd.putString("content", post.getContent());
                    bd.putInt("idPost", post.getId());
                    i.putExtras(bd);
                    context.startActivity(i);
                }
            });
        }


        int idUserFollow = posts.get(position).getIduser();
        int idUser = user.getId();
        //Log.d("IDFollower: ", email);

        if (db.CheckNameinFollowing(idUserFollow, idUser)) {
            if (idUser >= 0) {
                holder.flo.setVisibility(View.GONE);
                holder.tvFollowed.setVisibility(View.VISIBLE);
            }
        } else {
            holder.flo.setVisibility(View.VISIBLE);
            holder.tvFollowed.setVisibility(View.GONE);
        }
        if (holder.blueTick != null) {
            if (db.CheckTick(post.getIduser())) {
                holder.blueTick.setVisibility(View.VISIBLE);
            }
        }
        if (db.CheckFrameAndCrown(post.getIduser())) {
            int strokeColor = ContextCompat.getColor(context, R.color.border_frame);
            holder.avatar.setStrokeColor(ColorStateList.valueOf(strokeColor));

            holder.crown.setVisibility(View.VISIBLE);
        }
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetailActitivty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bn = new Bundle();
                if (type == 0) {
                    bn.putString("Img", post.getImgPost());
                } else if (type == 1 || type == 2) {
                    bn.putString("Content", post.getContent());
                } else if (type == 3) {
                    bn.putString("Content", post.getContent());
                    bn.putString("Img", post.getImgPost());
                }
                bn.putInt("idPost", post.getId());
                bn.putString("Username", post.getUsername());
                bn.putInt("idUser", post.getIduser());
                bn.putString("Pfp", ava);
                bn.putString("Name", post.getName());
                bn.putBoolean("IsCmt", true);
                bn.putInt("ViewType", type);
                bn.putInt("idUser", post.getIduser());
                bn.putString("Time", holder.time.getText().toString());
                bn.putString("Like", String.valueOf(db.getLike(post.getId()).getCount()));
                if (type == 5) {
                    bn.putInt("childID", childPost.getId());
                }
                intent.putExtras(bn);
                context.startActivity(intent);
            }
        });

        int iduser = db.getIduser(name);
        int idpost = post.getId();
        likes = db.CheckLike(iduser, idpost);
        IReaction reaction;
        if (likes.getCount() == 0) {
            holder.btnLike.setBackgroundResource(R.drawable.favorite_svgrepo_com);
            holder.btnLike.setText("");
            holder.btnLike.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
        } else {
            holder.btnLike.setBackgroundResource(0);
            if (likes.moveToNext()) {

                reaction = reactionRegistry.getByEmoji(likes.getString(likes.getColumnIndex("liketype")));
                holder.btnLike.setText(reaction.getEmoji());
                holder.btnLike.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
            }
        }

        holder.btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                likes = db.CheckLike(iduser, idpost);
                if (likes.getCount() == 0) {
                    Boolean insertLike = db.insertLikes(iduser, idpost);
                    if (insertLike && holder.btnLike.isChecked()) {
                        holder.btnLike.setChecked(false);
                        //holder.btnLike.setBackgroundResource(R.drawable.outline_favorite_24);
                        holder.numberLike.setText(String.valueOf(db.getLike(idpost).getCount()));
                        likes = db.CheckLike(iduser, idpost);
                        notifyItemChanged(position);
                        IReaction reaction;
                        if (likes.moveToNext()) {
                            reaction = reactionRegistry.getByEmoji(likes.getString(likes.getColumnIndex("liketype")));
                            holder.btnLike.setText(reaction.getEmoji());
                        }
                    }
                } else {
                    db.Unlike(iduser, idpost);
                    holder.btnLike.setBackgroundResource(R.drawable.favorite_svgrepo_com);
                    holder.numberLike.setText(String.valueOf(db.getLike(idpost).getCount()));
                    notifyItemChanged(position);
                }
            }

        });


        // menu


        if (idUserFollow != idUser) {
            if (idUser > 0)
                holder.btnOpenMenu.setVisibility(View.GONE);

        } else {
            holder.btnOpenMenu.setVisibility(View.VISIBLE);
            holder.flo.setVisibility(View.GONE);
            holder.tvFollowed.setVisibility(View.GONE);
        }
        ReactionAdapter adap = reactionRegistry.prepareAdapter(context, posts.get(position).getId());
        adap.setOnReaction(v -> {
            holder.reactionDialog.setVisibility(View.GONE);
            notifyItemChanged(position);
        });
        holder.rcvReactions.setAdapter(adap);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(RecyclerView.HORIZONTAL);
        holder.rcvReactions.setLayoutManager(llm);
        holder.btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), v);
                refreshView(position);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit_post:
                                if (type == 5) {
                                    Toast.makeText(context, "Chức năng edit không hỗ trợ bài viết Share.", Toast.LENGTH_SHORT).show();
                                    return false;
                                }
                                Intent i = new Intent(context, EditPostActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Bundle bd = new Bundle();
                                bd.putInt("idPost", post.getId());
                                i.putExtras(bd);
                                context.startActivity(i);
                                break;
                            case R.id.remove_post:
                                AlertDialog.Builder b = new AlertDialog.Builder(v.getRootView().getContext());
                                b.setTitle("Xóa bài viết").setMessage("Bạn có chắc là bạn muốn xóa bài viết này? Hành động này sẽ không thể đảo ngược.");
                                b.setPositiveButton("Ok, hãy xóa nó cho tôi.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //này tự hiểu
//                                        if(type == 5){
//                                            db.RemoveSharedPost(post.getId());
//                                        } else
//                                            db.removePost(post.getId());
//                                        posts.remove(position);
//                                        notifyItemRemoved(position);
//                                        // cập nhật lại position của recyclerview
//                                        notifyItemChanged(position);
//                                        //quan trọng hơn hết: cập nhật lại size của recyclerview để position nó đúng
//                                        Size = posts.size();

//                                        Post deletedPost = posts.get(position);
//                                        Command deleteCommand = new DeleteCommand(deletedPost, posts, recyclerView.getAdapter(), position);
//                                        deleteCommand.execute();
                                        //  showSnackbar(holder.itemView, "Bạn có muốn Undo bài viết", 5000, deletedPost, posts, position, recyclerView.getAdapter());


                                        Post deletedPost = posts.get(position);
                                        Command deleteCommand = new DeleteCommand(deletedPost, posts, recyclerView.getAdapter(), position);
                                        deleteCommand.execute();
                                        holder.showSnackbar(recyclerView.getRootView(), "Bạn có muốn Undo bài viết", 10000, deletedPost, posts, position, recyclerView.getAdapter());
                                        //showSnackbar(View view, String message, int duration, Post deletedPost, List<Post> posts, int position, RecyclerView.Adapter adapter) {


                                    }
                                });
                                b.setNegativeButton("Hủy, đừng xóa nó", null);
                                AlertDialog a = b.create();
                                a.show();
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.menu_option_post);
                popupMenu.show();
            }
        });

        holder.flo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                visited = new Boolean[posts.size()];
                holder.flo.setVisibility(View.GONE);
                holder.tvFollowed.setVisibility(View.VISIBLE);

                String UserName = db.getName(user.getId());
                int idUserFollow = followUser(post.getUsername());
                String UserNameFollow = db.getName(idUserFollow);

//                if (!db.CheckNameinFollower(idUserFollow)) {
                db.insertDataFollow(idUser, idUserFollow);
                ArrayList<Integer> listUserFollowed = db.listIdUserOf(user.getId());
                int index;
                for (int i = 0; i < listUserFollowed.size(); i++) {
                    Log.d("IDFollower: ", String.valueOf(listUserFollowed.get(i)));
                    db = new DB(context);
                    if (idUserFollow == listUserFollowed.get(i)) {
                        holder.flo.setVisibility(View.GONE);
                        holder.tvFollowed.setVisibility(View.VISIBLE);
                    } else {
                        holder.flo.setVisibility(View.VISIBLE);
                        holder.tvFollowed.setVisibility(View.GONE);
                    }
                    //  refreshView(listUserFollowed.get(i));
                    for (int k = 0; k < 100; k++) {
                        refreshView(k);
                    }
                    for (int k = 100; k > 0; k--) {
                        refreshView(k);
                    }
                }
                Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(v.getRootView().getContext());
                b.setTitle("Hủy theo dõi");
                b.setMessage("Bạn có muốn hủy theo dõi người dùng " + post.getName() + "? Bạn sẽ không thể thấy thông báo khi họ đăng bài.");
                b.setPositiveButton("Hủy theo dõi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        holder.flo.setVisibility(View.VISIBLE);
                        holder.tvFollowed.setVisibility(View.GONE);

                        user = db.getUser(email);
                        int idUser = user.getId();
                        String UserName = db.getName(user.getId());
                        int idUserFollow = followUser(post.getUsername());
                        String UserNameFollow = db.getName(idUserFollow);

                        if (db.CheckNameinFollower(idUserFollow)) {
                            if (idUser > 0) {
                                db.UnFollower(idUserFollow);
                                ArrayList<Integer> listUserFollowed = db.listIdUserOf(user.getId());

                                for (int j = 0; j < listUserFollowed.size(); j++) {
                                    Log.d("IDFollower: ", String.valueOf(listUserFollowed.get(j)));
                                    if (idUserFollow == listUserFollowed.get(j)) {
                                        holder.flo.setVisibility(View.GONE);
                                        holder.tvFollowed.setVisibility(View.VISIBLE);

                                    } else {
                                        holder.flo.setVisibility(View.VISIBLE);
                                        holder.tvFollowed.setVisibility(View.GONE);
                                    }
                                    //refreshView(j);
                                }
                                for (int k = 0; k < 100; k++) {
                                    refreshView(k);
                                }
                                for (int k = 100; k > 0; k--) {
                                    refreshView(k);
                                }

                                Toast.makeText(context, "UnFollowed", Toast.LENGTH_SHORT).show();


                            } else
                                Toast.makeText(context, "Bạn chưa có tài khoản .-.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Followed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                b.setNegativeButton("Hủy", null);
                AlertDialog a = b.create();
                a.show();
            }
        });


        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(context, SettingActivity.class);
            }
        });
        holder.likeWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, LikeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                b.putInt("idPost", posts.get(position).getId());
                i.putExtras(b);
                context.startActivity(i);
            }
        });
        holder.btnLike.setOnLongClickListener(v -> {
            if (holder.reactionDialog.getVisibility() == View.VISIBLE)
                holder.reactionDialog.setVisibility(View.GONE);
            else
                holder.reactionDialog.setVisibility(View.VISIBLE);
            return true;
        });
        holder.btnCloseReaction.setOnClickListener(v -> {
            holder.reactionDialog.setVisibility(View.GONE);
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Chia sẻ bài viết");
                builder.setMessage("Bạn có muốn chia sẻ bài viết của " + post.getUsername() + " không?");
                builder.setNegativeButton("Không", null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int currentIDpost = post.getId();
                        int myID = idUser;
                        Date currentTime = Calendar.getInstance().getTime();
                        db.saveShare(myID, currentIDpost, String.valueOf(currentTime));
                    }
                });
                builder.create().show();

            }
        });

    }


    public int followUser(String name) {
        SQLiteDatabase mydb = db.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM post p JOIN user u on u.id = p.iduser WHERE u.name = ?", new String[]{name});
        List<Integer> list = null;
        while (cursor.moveToNext()) {
            if (list == null)
                list = new ArrayList<>();
            list.add(cursor.getInt(1));
        }
        assert list != null;
        return list.get(0);
    }

    public ArrayList<Integer> ListFollowUserID(int idCurrentUser) {
        SQLiteDatabase mydb = db.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM follower WHERE iduser = ?", new String[]{String.valueOf(idCurrentUser)});
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            list.add(cursor.getInt(0));
        }

        return list;
    }

    public ArrayList<Integer> ListPostID() {
        SQLiteDatabase mydb = db.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM post ", null);
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (cursor.moveToNext()) {
            list.add(cursor.getInt(0));
        }

        return list;
    }


    @Override
    public int getItemCount() {
        if (posts != null)
            return Size;
        return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageButton btnOpenMenu;

        private TextView tvFollowed;

        private Button flo;
        private CheckBox btnLike;
        private ImageButton btnComment, btnShare;
        private ShapeableImageView avatar;
        private ImageView imgPost, ivSharedImage;
        private TextView name, userName, numberLike, content, time, nameUserPost, tvSharedOwner, tvTime, tvSharedCaption, tvSharedLikeCount;
        private LinearLayout btnShowProfile, likeWrapper;
        private ImageView blueTick;
        private ImageView crown;
        LinearLayout llUser;
        TextView tvErrorMsg, tvError;
        RecyclerView rcvReactions;
        LinearLayout reactionDialog;
        ImageButton btnCloseReaction;
        LinearLayout lnWrapPost;

        public PostViewHolder(@NonNull View view) {
            super(view);
            btnCloseReaction = view.findViewById(R.id.btn_close_reaction);
            reactionDialog = view.findViewById(R.id.reactions);
            if (reactionDialog != null)
                reactionDialog.setVisibility(View.GONE);
            rcvReactions = view.findViewById(R.id.rcvReactions);
            likeWrapper = view.findViewById(R.id.likeWrapper);
            avatar = (ShapeableImageView) view.findViewById(R.id.avatar);
            imgPost = (ImageView) view.findViewById(R.id.img_post);
            name = (TextView) view.findViewById(R.id.name);
            userName = (TextView) view.findViewById(R.id.nameu_user);
            // nameUserPost = (TextView) view.findViewById(R.id.nameuser_post);
            numberLike = (TextView) view.findViewById(R.id.number_like);
            content = (TextView) view.findViewById(R.id.content_post);
            time = (TextView) view.findViewById(R.id.time_post);
            btnComment = (ImageButton) view.findViewById(R.id.btn_Pcomment);
            btnOpenMenu = (ImageButton) view.findViewById(R.id.btnOptions);
            btnLike = (CheckBox) view.findViewById(R.id.btn_like);
            tvFollowed = (TextView) view.findViewById(R.id.tvFollowed);
            flo = (Button) view.findViewById(R.id.btnFlolow);
            btnShowProfile = view.findViewById(R.id.btnShowProfile);
            // nameUserPost = view.findViewById(R.id.)
            //
            ivSharedImage = view.findViewById(R.id.ivSharedImage);
            tvSharedLikeCount = view.findViewById(R.id.tvSharedLikeCount);
            tvSharedOwner = view.findViewById(R.id.tvSharedOwner);
            tvTime = view.findViewById(R.id.tvTime);
            tvSharedCaption = view.findViewById(R.id.tvSharedCaption);
            tvSharedLikeCount = view.findViewById(R.id.tvSharedLikeCount);
            btnShare = view.findViewById(R.id.btn_Pshare);
            tvErrorMsg = itemView.findViewById(R.id.tvErrorMsg);
            tvError = itemView.findViewById(R.id.tvError);
            blueTick = view.findViewById(R.id.blueTick);
            crown = view.findViewById(R.id.crownIcon);
            lnWrapPost = view.findViewById(R.id.wrapPostAll);
        }


        public void showSnackbar(View view, String message, int duration, Post deletedPost, List<Post> posts, int position, RecyclerView.Adapter adapter) {
            final Snackbar snackbar = Snackbar.make(view, message, duration);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Command undoCommand = new UndoCommand(deletedPost, posts, adapter, position);
                    undoCommand.execute();
                    Toast.makeText(view.getContext(), "Undo", Toast.LENGTH_SHORT).show();
                }
            });

            snackbar.show();
        }

    }


    public void refreshView(int position) {
        notifyItemChanged(position);
    }

    Boolean[] visited;

    public int getIndexOfPost(int idFollower) {
        Arrays.fill(visited, false);
        int index = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (posts.get(i).getIduser() == idFollower) {
                if (visited[i]) {
                    continue;
                }
                Log.d("indexFollower", String.valueOf(i));
                visited[i] = true;
                return i;
            }
        }
        visited = new Boolean[posts.size()];
        return index;
    }
}
