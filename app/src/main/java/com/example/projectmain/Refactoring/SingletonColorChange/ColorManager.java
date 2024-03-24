package com.example.projectmain.Refactoring.SingletonColorChange;

import android.content.SharedPreferences;
import android.graphics.Color;

public class ColorManager {
    private static ColorManager instance;
    private int backgroundColor;
    private int textColor;




    private ColorManager() {
        // Khởi tạo màu mặc định
        backgroundColor = Color.WHITE;
        textColor = Color.BLACK; // Màu chữ mặc định
    }

    public static ColorManager getInstance() {

        if (instance == null) {
            instance = new ColorManager();
        }
        if(instance.isColorDark()){
            instance.setTextColor(Color.WHITE);
        } else {
            instance.setTextColor(Color.BLACK);
        }
        return instance;
    }
    public boolean isColorDark() {
        // Lấy giá trị trung bình của các thành phần màu (R, G, B) để xác định màu sáng hay màu tối
        double darkness = 1 - (0.299 * Color.red(backgroundColor) + 0.587 * Color.green(backgroundColor) + 0.114 * Color.blue(backgroundColor)) / 255;
        return darkness >= 0.5; // Trả về true nếu màu là màu tối, ngược lại trả về false
    }
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int color) {
        backgroundColor = color;


    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int color) {
        textColor = color;
    }
}
