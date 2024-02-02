package com.example.qualityfood.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qualityfood.R;
import com.example.qualityfood.model.CocktailModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<List<String>> list;
    public IngredientsAdapter(List<List<String>> list) {
        this.list = list;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView quantity;
        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            quantity = (TextView) view.findViewById(R.id.quantity);
        }
        public TextView getNameView() {
            return name;
        }
        public TextView getQuantityView() {
            return quantity;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ingredients_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.name.setText(list.get(position).get(0));
        viewHolder.quantity.setText(list.get(position).get(1));
    }

    @Override
    public int getItemCount() {
        Log.i("Muskan Adapter", String.valueOf(list.size()));
        return list.size();
    }
}
