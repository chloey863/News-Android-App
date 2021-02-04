package com.chloeproject.newstime.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chloeproject.newstime.R; // the Android resource lookup
import com.chloeproject.newstime.databinding.SearchNewsItemBinding;
import com.chloeproject.newstime.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {
    /**
     * Use the itemCallback to inform the implementer the onOpenDetails event when an item is
     * clicked. In SearchNewsAdapter.onBindViewHolder we call onOpenDetails whenever the item is
     * clicked.
     */
    interface ItemCallback {
        /**
         * onOpenDetails is to be implemented for opening a new fragment for article details.
         */
        void onOpenDetails(Article article);
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }


    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);

        // notifyDataSetChanged() is a base adapter class
        notifyDataSetChanged();
    }

    // 2. Adapter overrides:
    /**
     * onCreateViewHolder is for providing the generated item views
     */
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }


    /**
     * onBindViewHolder is for binding the data with a view
     */
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        // display news text
        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        holder.itemTitleTextView.setText(article.title);

        // display news images using Picasso Library
        Picasso.get().load(article.urlToImage).into(holder.itemImageView);

        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    /**
     * getItemCount is for providing the current data collection size for the recycle view
     */
    @Override
    public int getItemCount() {
        return articles.size();
    }

    // 3. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);

            // use promise trick
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            favoriteImageView = binding.searchItemFavorite;
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }
}
