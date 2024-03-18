package com.example.projectmain.Refactoring.Decorator;

import android.content.Context;
import android.widget.Toast;

import com.example.projectmain.Database.DB;
import com.example.projectmain.Refactoring.Singleton.GlobalUser;

public class GoldCrownDecorator extends Decorator{
    public GoldCrownDecorator(IDecorator decorator) {
        super(decorator);
    }

    @Override
    public void addItem(Context context) {
        super.addItem(context);
        DB myDB = new DB(context);
        GlobalUser user = GlobalUser.getInstance(context);
        myDB.buyCrown(user.getUser().getId());
        Toast.makeText(context, "Id user :" + String.valueOf(user.getUser().getId()), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Mua thành công Vương miệng", Toast.LENGTH_SHORT).show();
    }
}
