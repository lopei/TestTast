package com.milestns.testtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.milestns.testtask.model.Exhibit;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by alan on 13.06.17.
 */

public class ExhibitsAdapter extends RecyclerView.Adapter<ExhibitsAdapter.ViewHolderItem> {
    private List<Exhibit> exhibits;


    public ExhibitsAdapter(List<Exhibit> exhibits) {
        this.exhibits = exhibits;
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exhibit, parent, false);
        return new ViewHolderItem(contactView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderItem holder, int position) {
        Exhibit exhibit = exhibits.get(position);
        holder.container.removeAllViews();
        for (String photo : exhibit.getImages()) {
            View photoView = LayoutInflater.from(holder.getContext()).inflate(R.layout.sub_list_item_photo, holder.container, false);
            ViewHolderSubItem holderSubItem = new ViewHolderSubItem(photoView);
            holderSubItem.title.setText(exhibit.getTitle());
            Picasso.with(holder.getContext()).load(photo).into(holderSubItem.image);
            holder.container.addView(photoView);
        }
    }

    @Override
    public int getItemCount() {
        return exhibits.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        View view;
        @BindView(R.id.container)
        ViewGroup container;

        ViewHolderItem(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public Context getContext() {
            return view.getContext();
        }
    }

    public static class ViewHolderSubItem {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;

        ViewHolderSubItem(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
