package com.jeffreyromero.projectmanager.views.itemCreator;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.adapters.MaterialsAdapter;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.ItemType;
import com.jeffreyromero.projectmanager.models.ItemTypeMaterialJoin;
import com.jeffreyromero.projectmanager.models.Material;
import com.jeffreyromero.projectmanager.utilities.Converters;
import com.jeffreyromero.projectmanager.viewModels.ItemTypeMaterialJoinViewModel;
import com.jeffreyromero.projectmanager.viewModels.ItemTypeViewModel;
import com.jeffreyromero.projectmanager.viewModels.ItemViewModel;
import com.jeffreyromero.projectmanager.viewModels.MaterialViewModel;

import java.util.List;

public class ShowNewItemFragment extends Fragment {

    private static final String NEW_ITEM_KEY = "newItem";
    private Item newItem;
    private MaterialsAdapter materialsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make this fragment capable of modifying the options menu.
        setHasOptionsMenu(true);
        // Get the passed in Item
        if (getArguments() != null) {
            String newItemJson = getArguments().getString(NEW_ITEM_KEY);
            newItem = Converters.toItem(newItemJson);
        }

        materialsAdapter = new MaterialsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_new_item_fragment, container, false);

        // Get name and type
        TextView nameTV = view.findViewById(R.id.name_tv);
        nameTV.setText(newItem.getName());
        final TextView typeTV = view.findViewById(R.id.type_tv);
        ItemTypeViewModel itemTypeViewModel = ViewModelProviders.of(this).get(ItemTypeViewModel.class);
        itemTypeViewModel.getById(newItem.getItemTypeId()).observe(this, new Observer<ItemType>() {
            @Override
            public void onChanged(ItemType itemType) {
                typeTV.setText(itemType.getName());
            }
        });

        // Get the materials for this item
        ItemTypeMaterialJoinViewModel itemTypeMaterialJoinViewModel = ViewModelProviders.of(this).get(ItemTypeMaterialJoinViewModel.class);
        itemTypeMaterialJoinViewModel.getMaterialsForItemType(newItem.getItemTypeId()).observe(this, new Observer<List<Material>>() {
            @Override
            public void onChanged(List<Material> materials) {
                materialsAdapter.setMaterials(materials);
            }
        });

        // Display the current newItem's material list
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(materialsAdapter);

//        calculateQuantities();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Empty the option menu and add the save button.
        menu.clear();
        MenuItem saveItem = menu.add(Menu.NONE, R.id.action_save, 10, R.string.action_save);
        saveItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        saveItem.setIcon(R.drawable.ic_save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            // Save new item to project
            ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
            itemViewModel.insert(newItem);
            // Navigate to ProjectFragment and remove back navigation for created item
            Navigation.findNavController(getView()).navigate(R.id.action_createItemFragment_to_projectFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
