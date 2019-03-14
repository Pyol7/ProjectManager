package com.jeffreyromero.projectmanager.views.itemCreator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.utilities.Converters;

import java.util.List;


/**
 * This is the start destination for the create new item navigation group.
 * This class routes the user to the appropriate details menu based on item type selection.
 */
public class ItemTypeSelectionFragment extends Fragment {

    private static final String PROJECT_ID_KEY = "projectId";
    private static final String SELECTED_ITEM_SUBTYPE_KEY = "selectedItemSubType";
    private List<String> itemSubTypeList;
    private String selectedItemSubType;
    private int destination;
    private int projectID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the passed in project id
        if (getArguments() != null) {
            projectID = getArguments().getInt(PROJECT_ID_KEY, 0);
        }

        setHasOptionsMenu(true);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (savedInstanceState == null){
            // Check shared preferences for a value
            selectedItemSubType = sharedPref.getString(SELECTED_ITEM_SUBTYPE_KEY, "Dropped Ceiling");
        } else {
            // Get the current value from the savedInstanceState bundle
            selectedItemSubType = savedInstanceState.getString(SELECTED_ITEM_SUBTYPE_KEY);
        }

        itemSubTypeList = Converters.toStringList(sharedPref.getString("itemSubTypeList", null));

        setDestination(selectedItemSubType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_select_item_type, container, false);
        // Setup the recyclerView
        RecyclerView rv = view.findViewById(R.id.itemTypes_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(new ItemTypesAdapter(itemSubTypeList));
        return view;
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
            bundle.putInt(PROJECT_ID_KEY, projectID);
            Navigation.findNavController(getView()).navigate(getDestination(), bundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_ITEM_SUBTYPE_KEY, selectedItemSubType);
    }

    private void setDestination(String selectedItemSubType) {
        switch(selectedItemSubType) {
            case "Dropped Ceiling":
                destination = R.id.action_itemTypeSelectionFragment_to_droppedCeilingCreatorFragment;
                break;
            case "Drywall Ceiling":
                destination = R.id.action_selectItemType_to_drywallCeilingDetails;
                break;
            case "Drywall Partition":
                destination = R.id.action_selectItemType_to_drywallPartitionDetails;
                break;
            default:
                // Return to ProjectFragment
                Navigation.findNavController(getView()).popBackStack();
        }
    }

    private int getDestination() {
        return destination;
    }

    public class ItemTypesAdapter extends RecyclerView.Adapter<ItemTypesAdapter.ItemViewHolder> {

        private List<String> itemTypes;

        ItemTypesAdapter(List<String> itemTypes) {
            this.itemTypes = itemTypes;
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
            viewHolder.nameTV.setText(itemTypes.get(i));
            viewHolder.radioBtn.setChecked(selectedItemSubType.equals(itemTypes.get(i)));
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
                        selectedItemSubType = itemTypes.get(getAdapterPosition());
                        setDestination(selectedItemSubType);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
