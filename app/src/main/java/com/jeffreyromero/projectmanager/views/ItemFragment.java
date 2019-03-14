package com.jeffreyromero.projectmanager.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.adapters.MaterialsAdapter;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.ItemType;
import com.jeffreyromero.projectmanager.models.Material;
import com.jeffreyromero.projectmanager.viewModels.ItemTypeMaterialJoinViewModel;
import com.jeffreyromero.projectmanager.viewModels.ItemTypeViewModel;
import com.jeffreyromero.projectmanager.viewModels.ItemViewModel;
import com.jeffreyromero.projectmanager.viewModels.MaterialViewModel;

import java.util.List;

public class ItemFragment extends Fragment implements MaterialsAdapter.OnItemClickListener {

    private static final String TAG = "ItemFragment";
    private static final String ITEM_ID_KEY = "itemId";
    private MaterialsAdapter adapter;
    private int itemId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the passed in item id
        if (getArguments() != null) {
            itemId = getArguments().getInt(ITEM_ID_KEY, 0);
        }

        // Set up list adapter
        adapter = new MaterialsAdapter();
        adapter.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_new_item_fragment, container, false);

        // Setup the recyclerView and adapter
        RecyclerView rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get View Models
        ItemViewModel itemViewModel = ViewModelProviders
                .of(this)
                .get(ItemViewModel.class);
        final ItemTypeViewModel itemTypeViewModel = ViewModelProviders
                .of(ItemFragment.this)
                .get(ItemTypeViewModel.class);
        final ItemTypeMaterialJoinViewModel itemTypeMaterialJoinViewModel = ViewModelProviders
                .of(ItemFragment.this)
                .get(ItemTypeMaterialJoinViewModel.class);

        // Get the passed in item
        itemViewModel.getById(itemId).observe(this, new Observer<Item>() {
            @Override
            public void onChanged(final Item item) {
                // Set the project name as the Title
                TextView nameTV = view.findViewById(R.id.name_tv);
                nameTV.setText(item.getName());

                // Get the itemType
                itemTypeViewModel.getById(item.getItemTypeId())
                        .observe(ItemFragment.this, new Observer<ItemType>() {
                    @Override
                    public void onChanged(ItemType itemType) {
                        TextView typeTV = view.findViewById(R.id.type_tv);
                        typeTV.setText(itemType.getName());
                    }
                });

                // Get the materials with itemTypeId and set it to the materialsAdapter
                itemTypeMaterialJoinViewModel.getMaterialsForItemType(item.getItemTypeId())
                        .observe(ItemFragment.this, new Observer<List<Material>>() {
                    @Override
                    public void onChanged(List<Material> materials) {
                        adapter.setMaterials(materials);
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(Material material) {
        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(Material material) {
        Toast.makeText(getActivity(), "long clicked", Toast.LENGTH_SHORT).show();
    }
}
