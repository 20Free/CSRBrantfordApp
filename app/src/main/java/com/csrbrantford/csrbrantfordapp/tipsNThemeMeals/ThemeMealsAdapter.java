package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.csrbrantford.csrbrantfordapp.R;

import java.util.ArrayList;

/**
 * Created by Jonathan on 7/11/2016.
 */
class ThemeMealsAdapter extends BaseAdapter{

    private ListFragment listFragment;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    private ThemeMeal themeMeal;

    ThemeMealsAdapter(Context context, ArrayList data, Resources res, ListFragment listFragment) {
        this.data = data;
        this.res = res;
        this.listFragment = listFragment;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size() <= 0){
            return 1;
        }
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ThemeMealsHolder holder;

        if(row == null) {
            row = inflater.inflate(R.layout.theme_meals_row_layout, parent, false);

            holder = new ThemeMealsHolder();
            holder.title = (TextView)row.findViewById(R.id.themeMealTitle);
            holder.openThemeMeal = (ImageButton)row.findViewById(R.id.openThemeMeal);

            row.setTag(holder);
        } else {
            holder = (ThemeMealsHolder)row.getTag();
        }

        if(data.size() <= 0){
            holder.title.setText("No Theme Meals could be found");
        } else {
            themeMeal = null;
            themeMeal = (ThemeMeal) data.get(position);

            holder.title.setText(themeMeal.getTitle());
            holder.openThemeMeal.setBackground(themeMeal.getOpenThemeMeal());
            holder.openThemeMeal.setOnClickListener(new OnItemClickListener(position));
            row.setOnClickListener(new OnItemClickListener(position));
        }

        return row;
    }

    private static class ThemeMealsHolder {
        TextView title;
        ImageButton openThemeMeal;
    }

    private class OnItemClickListener implements OnClickListener {
        private int pos;

        OnItemClickListener(int pos){
            this.pos = pos;
        }

        @Override
        public void onClick(View view) {
            ThemeMealsFragment themeMealsFragment = (ThemeMealsFragment) listFragment;
            themeMealsFragment.onItemClick(pos);
        }
    }
}
