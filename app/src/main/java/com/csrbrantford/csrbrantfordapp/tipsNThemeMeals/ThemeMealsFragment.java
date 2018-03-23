package com.csrbrantford.csrbrantfordapp.tipsNThemeMeals;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.asyncTask.AsyncResponse;
import com.csrbrantford.csrbrantfordapp.asyncTask.JSONAsyncTask;
import com.csrbrantford.csrbrantfordapp.buttonCanvases.PlayButtonDrawer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jonathan on 7/4/2016.
 */
public class ThemeMealsFragment extends ListFragment {

    ThemeMealsAdapter adapter;
    ArrayList<ThemeMeal> themeMeals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.theme_meals_tab_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        themeMeals = new ArrayList<>();
        getActivity().findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        JSONAsyncTask themeMealsAsync = new JSONAsyncTask(new AsyncResponse() {
            @Override
            public void processFinish(Object output) {
                try {
                    Log.i("JSON", output.toString());
                    JSONObject themeMealsObject = new JSONObject((String) output);
                    int i = 1;
                    int totalWidth = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);
                    int totalHeight = (int)getResources().getDimension(R.dimen.csr_logo_bottompadding);

                    while(themeMealsObject.has("week"+i)) {
                        Bitmap playButtonBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
                        PlayButtonDrawer playButtonDrawer = new PlayButtonDrawer();
                        Canvas playButtonCanvas = playButtonDrawer.drawPlayButton(totalWidth, totalHeight, playButtonBitmap);
                        playButtonCanvas.drawBitmap(playButtonBitmap,0,0,null);
                        ThemeMeal themeMeal = new ThemeMeal(themeMealsObject.getJSONObject("week"+i).getString("nameOfSection"), new BitmapDrawable(getResources(), playButtonBitmap), themeMealsObject.getJSONObject("week"+i).getJSONArray("items").getJSONObject(0));
                        themeMeals.add(themeMeal);
                        i++;
                    }
                    adapter.notifyDataSetChanged();
                    getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        themeMealsAsync.execute(getString(R.string.theme_meals_json_url));

        Resources res = getResources();
        adapter = new ThemeMealsAdapter(getActivity(), themeMeals, res, this);
        View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.theme_meals_footer_layout, getListView(), false);
        getListView().addFooterView(footerView);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void onItemClick(int pos){
        ThemeMeal themeMeal = themeMeals.get(pos);
        Intent intent = new Intent(getActivity(), ThemeMealActivity.class);
        for(int i = 0; i < themeMeals.size(); i++) {
            try {
                intent.putExtra("title", themeMeal.getTitle());
                intent.putExtra("name", themeMeal.getThemeMealObject().getString("nameOfItem"));
                intent.putExtra("desc", themeMeal.getThemeMealObject().getString("descriptionOfItem"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;

        View view = null;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}