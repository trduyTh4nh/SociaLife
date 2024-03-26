package com.example.projectmain.Refactoring.Decorator;

import android.content.Context;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

public class BlueTickDecorator extends Decorator{
    public BlueTickDecorator(IDecorator decorator) {
        super(decorator);
    }
    @Override
    public void addItem(Context context) {
        super.addItem(context);
        DB myDB = new DB(context);
        GlobalUser user = GlobalUser.getInstance(context);
        myDB.buyTickGreen(user.getUser().getId());
      //  Toast.makeText(context, "Id user :" + String.valueOf(user.getUser().getId()), Toast.LENGTH_SHORT);
        Toast.makeText(context, "Mua thành công tick xanh", Toast.LENGTH_SHORT).show();
    }
}
