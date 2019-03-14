package com.jeffreyromero.projectmanager.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MaterialsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MaterialsAdapter";
    //View types
    private final int ITEM_VIEW = 0;
    private final int TOTAL_VIEW = 1;
    // Initializes to empty array to avoid NPE
    private List<Material> materials = new ArrayList<>();
    private MaterialsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Material material);
        void onItemLongClick(Material material);
    }

    public void setOnItemClickListener(MaterialsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public void setMaterials(List<Material> materials){
        this.materials = materials;
        notifyDataSetChanged();
    }

    public double calcTotal(List<Material> materials) {
        //Sum price from every material in the list.
        double total = 0;
        for (Material material : materials) {
            total += material.getQuantity() * material.getUnitPrice();
        }
        return total;
    }

    @Override
    public int getItemCount() {
        return materials.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() - 1) {
            return ITEM_VIEW;
        } else {
            return TOTAL_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_VIEW) {
            // Inflate the ITEM_VIEW.
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.material_list_adapter_list_item, parent, false));

        } else if (viewType == TOTAL_VIEW) {
            // Inflate the TOTAL_VIEW.
            return new TotalViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.material_list_adapter_list_item_total, parent, false));

        } else {
            //Throw exception if view type is not found.
            throw new RuntimeException("View type not found");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case ITEM_VIEW:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Material material = materials.get(position);
                // Set background color
                if (position % 2 == 0) {
//                    itemViewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                } else {
//                    itemViewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                }
                // Set values
                itemViewHolder.nameTV.setText(material.getName());
                String q = String.format(
                        Locale.US,
                        "%.1f",
                        material.getQuantity());
                itemViewHolder.quantityTV.setText(q);
                String u = String.format(
                        Locale.US,
                        "$%.2f",
                        material.getUnitPrice());
                itemViewHolder.unitPriceTV.setText(u);
                itemViewHolder.priceTV.setText(String.format(
                        Locale.US,
                        "$%.2f",
                        material.getUnitPrice() * material.getQuantity()));
                break;

            case TOTAL_VIEW:
                TotalViewHolder totalViewHolder = (TotalViewHolder) holder;
                totalViewHolder.totalTV.setText(String.format(
                        Locale.US,
                        "$%.2f",
                        calcTotal(materials)));
                break;

            default:
                break;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView quantityTV;
        TextView unitPriceTV;
        TextView priceTV;

        ItemViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.toolbarTitle);
            quantityTV = itemView.findViewById(R.id.quantityTV);
            unitPriceTV = itemView.findViewById(R.id.unitPriceTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    listener.onItemClick(materials.get(getAdapterPosition()));
                    Toast.makeText(v.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class TotalViewHolder extends RecyclerView.ViewHolder {
        TextView totalTV;

        TotalViewHolder(View itemView) {
            super(itemView);
            totalTV = itemView.findViewById(R.id.totalTV);
        }
    }
}
