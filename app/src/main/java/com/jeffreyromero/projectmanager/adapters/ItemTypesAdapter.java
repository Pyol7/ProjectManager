package com.jeffreyromero.projectmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;

import java.util.ArrayList;
import java.util.List;

public class ItemTypesAdapter extends RecyclerView.Adapter<ItemTypesAdapter.ItemViewHolder> {

    // Initializes to empty array to avoid NPE
    private List<ItemType> itemTypes = new ArrayList<>();
    private int selectedItemTypeIndex;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int itemTypeID);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            // Inflate the item view and pass into view holder.
            return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.list_item_radio_textview, viewGroup, false));
        }
        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
            // Bind data to views in view holder
            viewHolder.nameTV.setText(itemTypes.get(i).getName());
            viewHolder.radioBtn.setChecked(selectedItemTypeIndex == i);
        }
        @Override
        public int getItemCount() {
            return itemTypes.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView nameTV;
            RadioButton radioBtn;
            ItemViewHolder(View itemView) {
                super(itemView);
                radioBtn = itemView.findViewById(R.id.radio_btn);
                nameTV = itemView.findViewById(R.id.name_tv);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(itemTypes.get(getAdapterPosition()).getId());
                        selectedItemTypeIndex = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                });
            }
        }

    public void setItemTypes(List<ItemType> itemTypes){
        this.itemTypes = itemTypes;
        notifyDataSetChanged();
    }

}
