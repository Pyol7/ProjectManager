package com.jeffreyromero.projectmanager.views.createNewItemDialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.itemTypes.ItemType;
import com.jeffreyromero.projectmanager.viewModels.ItemTypesViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the First menu that shows when creating a new item.
 * Current nested menus are:
 *      SelectItemType
 *      DialogDroppedCeilingDetails
 *      DialogDrywallCeilingDetails
 *      DialogDrywallPartitionDetails
 *      DialogCreateItem
 * DialogCreateItem finally passes the newly created item back to Project.
 */
public class SelectItemType extends Fragment {

    private static final String PROJECT_ID_KEY = "projectIdKey";
    private static final String SELECTED_ITEM_TYPE_KEY = "selectedItemTypeKey";
    private ItemTypesAdapter itemTypesAdapter;
    private int selectedItemTypePosition;
    private int destination;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make this fragment capable of modifying the options menu.
        setHasOptionsMenu(true);

        itemTypesAdapter = new ItemTypesAdapter();

        // Get selectedItemType from shared preferences else return default value of 0
        selectedItemTypePosition = getActivity().getPreferences(0).getInt(SELECTED_ITEM_TYPE_KEY, 0);
        // Set the initial destination before anything is selected
        setDestination(selectedItemTypePosition + 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dialog_select_item_type, container, false);
        // Setup the recyclerView
        RecyclerView rv = view.findViewById(R.id.itemTypes_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(itemTypesAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup view model
        ItemTypesViewModel itemTypesViewModel = ViewModelProviders.of(this).get(ItemTypesViewModel.class);
        // Get the list of item types and set to adapter
        itemTypesViewModel.getAllItemTypes().observe(this, new Observer<List<ItemType>>() {
            @Override
            public void onChanged(List<ItemType> itemTypes) {
                // Whenever the list is received we add it to the list adapter
                itemTypesAdapter.setItemTypes(itemTypes);
            }
        });
    }

    private void setDestination(int selectedItemTypeID) {
        switch(selectedItemTypeID) {
            case 1:
                destination = R.id.action_selectItemType_to_droppedCeilingDetails;
                break;
            case 2:
                // Show DialogDrywallCeilingDetails
                Toast.makeText(getActivity(), "Drywall Ceiling", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                // Show DialogDrywallPartitionDetails
                Toast.makeText(getActivity(), "Drywall Partition", Toast.LENGTH_SHORT).show();
                break;
            default:
                // selectedItemType is 0 at this point
                // todo - determine how to handle this
                Toast.makeText(getActivity(),
                        "Destination not found based on item type id: " + selectedItemTypeID,
                        Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Empty the option menu and add the next button.
        menu.clear();
        MenuItem nextItem = menu.add(Menu.NONE, R.id.action_next, 10, R.string.action_next);
        nextItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        nextItem.setIcon(R.drawable.ic_navigate_next);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_next) {
            // Navigate to chosen type for details
            Bundle bundle = new Bundle();
            bundle.putInt(PROJECT_ID_KEY, getArguments().getInt(PROJECT_ID_KEY));
            Navigation.findNavController(view).navigate(destination, bundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ItemTypesAdapter extends RecyclerView.Adapter<ItemTypesAdapter.ItemViewHolder> {

        // Initialize to prevent NPE while we fetch the list from Room
        private List<ItemType> itemTypes = new ArrayList<>();

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
            viewHolder.radioBtn.setChecked(selectedItemTypePosition == i);
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
                        // Set a new selection index
                        selectedItemTypePosition = getAdapterPosition();
                        // Set the destination based on the item's id
                        setDestination(itemTypes.get(selectedItemTypePosition).getId());
                        notifyDataSetChanged();
                    }
                });
            }
        }

        public void setItemTypes(List<ItemType> itemTypes){
            this.itemTypes = itemTypes;
            notifyDataSetChanged();
        }

        public List<ItemType> getItemTypes() {
            return itemTypes;
        }

    }
}
