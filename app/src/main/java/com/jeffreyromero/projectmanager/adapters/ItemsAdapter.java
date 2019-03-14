package com.jeffreyromero.projectmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    // Initializes to empty array to avoid NPE
    private List<Item> items = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int itemID);
        void onItemLongClick(Item item);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_adapter_list_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item currentItem = items.get(i);
        itemViewHolder.leftTV.setText(currentItem.getName());
        itemViewHolder.rightTV.setText(String.valueOf(currentItem.getItemTypeId()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView leftTV;
        private TextView rightTV;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            leftTV = itemView.findViewById(R.id.left_tv);
            rightTV = itemView.findViewById(R.id.right_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(items.get(getAdapterPosition()).getId());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(items.get(getAdapterPosition()));
                    return true;
                }

            });
        }
    }

    public void setItems(List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

}
