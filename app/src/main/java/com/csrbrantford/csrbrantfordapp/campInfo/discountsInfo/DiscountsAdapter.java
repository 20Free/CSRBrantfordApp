package com.csrbrantford.csrbrantfordapp.campInfo.discountsInfo;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.csrbrantford.csrbrantfordapp.R;
import com.csrbrantford.csrbrantfordapp.campInfo.PopupWindowCompat;

import java.util.List;

/**
 * Created by 20Free on 4/28/2017.
 */

class DiscountsAdapter extends ExpandableRecyclerAdapter<DiscountsGroupItem, DiscountsChildItem, DiscountsGroupHolder, DiscountsChildHolder> {

    private LayoutInflater inflater;
    private Context rootContext;
    private Fragment fragment;
    private PopupWindowCompat popupWindowCompat;
    private View popupView;
    private RecyclerView recyclerView;
    private DiscountsGroupHolder parentViewHolder;

    DiscountsAdapter(RecyclerView recyclerView, Context context, Fragment fragment, List<DiscountsGroupItem> discountsGroupItemList) {
        super(discountsGroupItemList);
        inflater = LayoutInflater.from(context);
        rootContext = context;
        this.fragment = fragment;

        this.recyclerView = recyclerView;
    }
    @NonNull
    @Override
    public DiscountsGroupHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View discountsGroupView = inflater.inflate(R.layout.discounts_list_group, parentViewGroup, false);

        return new DiscountsGroupHolder(discountsGroupView);
    }

    @NonNull
    @Override
    public DiscountsChildHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View discountsChildView = inflater.inflate(R.layout.discounts_list_item, childViewGroup, false);

        return new DiscountsChildHolder(discountsChildView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull final DiscountsGroupHolder parentViewHolder, int parentPosition, @NonNull DiscountsGroupItem parent) {
        this.parentViewHolder = parentViewHolder;

        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int flatPosition) {
        super.onBindViewHolder(holder, flatPosition);
    }

    @Override
    public void onBindChildViewHolder(@NonNull DiscountsChildHolder childViewHolder, final int parentPosition, int childPosition, @NonNull final DiscountsChildItem child){
        childViewHolder.bind(child);
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupView = LayoutInflater.from(rootContext).inflate(R.layout.faq_list_item_view_fragment, (ViewGroup) recyclerView.getRootView(), false);

                        popupWindowCompat = new PopupWindowCompat(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        TextView offerListItemButton = (TextView)popupView.findViewById(R.id.faq_list_item_button);
                        offerListItemButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindowCompat.dismiss();
                            }
                        });
                        Toolbar toolbar = (Toolbar)popupView.findViewById(R.id.toolbar);
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragment.getActivity().onBackPressed();
                            }
                        });
                        String title = child.getTitle();
                        String details = child.getDetails();

                        TextView titleView = (TextView)popupView.findViewById(R.id.faq_list_item_title2);
                        titleView.setText(title);
                        TextView detailsView = (TextView)popupView.findViewById(R.id.faq_list_item_details);
                        detailsView.setText(details);

                        Rect rect = new Rect();

                            Window window = fragment.getActivity().getWindow();
                            window.getDecorView().getWindowVisibleDisplayFrame(rect);

                            int statusBarHeight = rect.top;


                        popupWindowCompat.showAtLocation(fragment.getView(), Gravity.CENTER, 0, statusBarHeight);
                    }
                });

    }
}
