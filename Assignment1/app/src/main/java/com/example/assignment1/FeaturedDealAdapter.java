package com.example.assignment1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class FeaturedDealAdapter extends RecyclerView.Adapter<FeaturedDealAdapter.FeaturedDealViewHolder> {

    private List<FeaturedDeal> dealList;



    public void setFilteredList(List<FeaturedDeal> filteredList) {
        this.dealList = filteredList;
        notifyDataSetChanged();
    }

    private Context context;

    public FeaturedDealAdapter(List<FeaturedDeal> deals, Context context) {
        this.dealList = (deals == null) ? new ArrayList<>() : new ArrayList<>(deals);
        this.context = context;
    }

    public static class FeaturedDealViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView cName;
        TextView dealCategory;
        TextView dealDescription;
        TextView dealPrice;

        public FeaturedDealViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            cName = itemView.findViewById(R.id.c_name);
            dealCategory = itemView.findViewById(R.id.dealCategory);
            dealDescription = itemView.findViewById(R.id.dealDescription);
            dealPrice = itemView.findViewById(R.id.dealPrice);

        }
    }

    void updateDealList(List<FeaturedDeal> newDeals) {
        if (newDeals != null) {
            dealList.clear();
            dealList.addAll(newDeals);
            notifyDataSetChanged();
        } else {
            Log.e("FeaturedDealAdapter", "Attempted to update with null lists"); // debugging statement
        }
    }


    @NonNull
    @Override
    public FeaturedDealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_deal_xml, parent, false);
        return new FeaturedDealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedDealViewHolder holder, int position) {
        if(dealList !=null && !dealList.isEmpty()) {
            FeaturedDeal deal = dealList.get(position);
            Log.d("AdapterDebug", "Binding: " + deal.getCategory()); // this is a debug log
            holder.dealCategory.setText(deal.category);
            holder.dealDescription.setText(deal.description);
            holder.cName.setText(deal.getTitle());
            holder.imageView.setImageResource(deal.getImages());
            holder.dealPrice.setText(String.format("$%.2f", deal.price));

            holder.itemView.setOnClickListener(v -> {
                String description = deal.getDescription();
                if (description == null || description.isEmpty()) {
                    description = "Unable to find description";
                }
                Toast.makeText(context, description + " booked!", Toast.LENGTH_SHORT).show();
            });
        } else {
            Log.e("FeaturedDealAdapter", "dealList is empty or null"); // debugging statement
        }

    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }
}
