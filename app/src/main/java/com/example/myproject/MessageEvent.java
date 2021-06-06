package com.example.myproject;

import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

public class MessageEvent {
    private Toolbar toolbar;
    private int menuId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public MessageEvent( int menuId) {
        this.menuId = menuId;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

}
//    Menu menu = toolbar.getMenu();
//        menu.clear();
//                toolbar.inflateMenu(id);