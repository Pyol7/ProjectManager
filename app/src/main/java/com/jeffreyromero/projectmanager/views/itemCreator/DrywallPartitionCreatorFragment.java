package com.jeffreyromero.projectmanager.views.itemCreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.Item;

public class DrywallPartitionCreatorFragment extends Fragment {

    private static final int ITEM_TYPE_ID = 3;
    private static final String PROJECT_ID_KEY = "projectId";
    private EditText nameET, lengthET, heightET, length1ET, height1ET, length2ET, height2ET;
    private RadioButton doubleLayerBoardRadio;
    private int projectID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make this fragment capable of modifying the options menu.
        setHasOptionsMenu(true);
        // Get the passed in project id
        if (getArguments() != null) {
            projectID = getArguments().getInt(PROJECT_ID_KEY, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.drywall_partition_details, container, false);
        // Get Views
        nameET = view.findViewById(R.id.name_et);
        lengthET = view.findViewById(R.id.length_et);
        heightET = view.findViewById(R.id.height_et);
        doubleLayerBoardRadio = view.findViewById(R.id.double_Layer_board_radio);
        length1ET = view.findViewById(R.id.opening_1_length_et);
        height1ET = view.findViewById(R.id.opening_1_height_et);
        length2ET = view.findViewById(R.id.opening_2_length_et);
        height2ET = view.findViewById(R.id.opening_2_height_et);
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

            // Navigate to ShowNewItemFragment
            Navigation.findNavController(getView()).navigate(
                    R.id.action_drywallPartitionDetails_to_createItemFragment,
                    bundleUserInput()
            );

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Bundle bundleUserInput(){
        // todo - Validate input
        String name = nameET.getText().toString();
        double length = Double.parseDouble(lengthET.getText().toString());
        double height = Double.parseDouble(heightET.getText().toString());

        // Todo - Only collect data and pass it on to ShowNewItemFragment including Item type.

        Bundle bundle = new Bundle();
        bundle.putInt("itemType", ITEM_TYPE_ID);
        //.......



        return bundle;
    }

    private double feetToInches(double feet) {
        return feet * 12;
    }

    private int getBoardLayers(){
        int layer = 1;
        if (doubleLayerBoardRadio.isChecked()) {
            layer = 2;
        }
        return layer;
    }

    private double calcTotalOpeningArea(){
        double length1 = Double.parseDouble(length1ET.getText().toString());
        double height1 = Double.parseDouble(height1ET.getText().toString());
        double length2 = Double.parseDouble(length2ET.getText().toString());
        double height2 = Double.parseDouble(height2ET.getText().toString());
        return  (length1*height1) + (length2*height2);
    }
}
