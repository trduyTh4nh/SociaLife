package com.example.projectmain;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.projectmain.Refactoring.Decorator.BlueTickDecorator;
import com.example.projectmain.Refactoring.Decorator.Decorator;
import com.example.projectmain.Refactoring.Decorator.GoldCrownDecorator;
import com.example.projectmain.Refactoring.Decorator.IDecorator;
import com.example.projectmain.Refactoring.Decorator.PurpleFrameDecorator;


public class PaymentActivity extends AppCompatActivity {

    Button  btnBuyGoldFrameAndCrown, btnGreenTick, btnByAll;
    ImageButton btn_Exit;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnGreenTick = findViewById(R.id.btnTickGreen);
        btnBuyGoldFrameAndCrown = findViewById(R.id.btnGoldFrameAndCrown);
        btnByAll = findViewById(R.id.btnGoldFrameAndCrownAndTick);
        btn_Exit = findViewById(R.id.btn_exit);


        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnGreenTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn mua Tick Xanh?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                IDecorator normalUser = null;
                                Decorator buyBlueTick = new BlueTickDecorator(normalUser);
                                buyBlueTick.addItem(PaymentActivity.this);

                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        btnBuyGoldFrameAndCrown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn mua Khung VIP và Vương miệng?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                IDecorator normalUser = null;
                                Decorator buyFrameAndCrown = new GoldCrownDecorator(new PurpleFrameDecorator(normalUser));
                                buyFrameAndCrown.addItem(PaymentActivity.this);
//                                IDecorator buyFrame = new BlueFrameDecorator();
//                                IDecorator buyCrown = new CrownDecorator();
//                                buyFrame.addItem(PaymentActivity.this);
//                                buyCrown.addItem(PaymentActivity.this);

                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        btnByAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn mua Tất cả các gói?")
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                IDecorator normalUser = null;
                                Decorator buyAll =
                                        new BlueTickDecorator(
                                             new PurpleFrameDecorator(
                                                new GoldCrownDecorator(normalUser)));
                                buyAll.addItem(PaymentActivity.this);


//                                IDecorator buyFrame = new BlueFrameDecorator(new CrownDecorator(new GreenTickDecorator()));
//                                IDecorator buyCrown = new CrownDecorator();
//                                IDecorator buyTick = new CrownDecorator();
//                                buyFrame.addItem(PaymentActivity.this);
//                                buyCrown.addItem(PaymentActivity.this);
//                                buyTick.addItem(PaymentActivity.this);


                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

    }





}