package com.education.adapter;

import java.util.List;

import com.education.eddura.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {
	 
    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID,
                List<DrawerItem> listItems) {
          super(context, layoutResourceID, listItems);
          this.context = context;
          this.drawerItemList = listItems;
          this.layoutResID = layoutResourceID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
          // TODO Auto-generated method stub

          DrawerItemHolder drawerHolder;
          View view = convertView;

          if (view == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                drawerHolder = new DrawerItemHolder();

                view = inflater.inflate(layoutResID, parent, false);
                drawerHolder.ItemName = (TextView) view
                            .findViewById(R.id.drawer_itemName);
                drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
                drawerHolder.sub_icon = (ImageView) view.findViewById(R.id.drawer_sub_icon);
                drawerHolder.drawer_line = (View) view.findViewById(R.id.drawer_line);

                view.setTag(drawerHolder);

          } else {
                drawerHolder = (DrawerItemHolder) view.getTag();

          }

    	  DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

          if (position == 1 || position == 2 || position == 4 || position == 5 || position == 8 || position == 9) {
        	  drawerHolder.sub_icon.setVisibility(View.VISIBLE);
        	  drawerHolder.icon.setVisibility(View.GONE);
        	  
              drawerHolder.sub_icon.setImageDrawable(view.getResources().getDrawable(
                          dItem.getImgResID()));
              
          } else {
        	  drawerHolder.sub_icon.setVisibility(View.GONE);
        	  drawerHolder.icon.setVisibility(View.VISIBLE);
        	  
              drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
                          dItem.getImgResID()));
          }
          
          
          if (position == 1 || position == 4 || position == 8) {
        	  drawerHolder.drawer_line.setVisibility(View.GONE);
              
          } else {
        	  drawerHolder.drawer_line.setVisibility(View.VISIBLE);
          }
          
          
          drawerHolder.ItemName.setText(dItem.getItemName());

          return view;
    }

    private static class DrawerItemHolder {
          TextView ItemName;
          ImageView icon, sub_icon;
          View drawer_line;
    }
}