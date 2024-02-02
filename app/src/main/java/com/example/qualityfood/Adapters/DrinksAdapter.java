package com.example.qualityfood.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qualityfood.DetailsActivity;
import com.example.qualityfood.MainActivity;
import com.example.qualityfood.R;
import com.example.qualityfood.model.CocktailModel;

import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.ViewHolder> {
    List<CocktailModel> list;
    Context context;

    public DrinksAdapter(Context context,List<CocktailModel> list) {
        this.context=context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView category;
        private final ImageView img1;

        private final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.prodName1);
            category = (TextView) view.findViewById(R.id.category1);
            img1 = (ImageView) view.findViewById(R.id.Img1);
            cardView = (CardView) view.findViewById(R.id.cardView1);
        }

        public TextView getNameView() {
            return name;
        }

        public TextView getCategoryView() {
            return category;
        }

        public ImageView getImageView() {
            return img1;
        }
    }

    @Override
    public DrinksAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.drinks_adapter, viewGroup, false);
        return new DrinksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrinksAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.name.setText(list.get(position).getStrDrink());
        viewHolder.category.setText(list.get(position).getStrCategory());
        Glide.with(context)
                .load(list.get(position).getStrDrinkThumb())
                .error(R.drawable.error_img)
                .override(200, 200)
                .centerCrop()
                .into(viewHolder.img1);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Muskan DrinksAdapter","Item clicked");
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("drink id", list.get(position).getIdDrink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("Muskan Adapter", String.valueOf(list.size()));
        return list.size();
    }

}
