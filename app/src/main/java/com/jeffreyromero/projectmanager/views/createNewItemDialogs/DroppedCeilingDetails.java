package com.jeffreyromero.projectmanager.views.createNewItemDialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.itemTypes.DroppedCeiling;

import java.util.Locale;


public class DroppedCeilingDetails extends Fragment {

    private static final String PROJECT_ID_KEY = "projectIdKey";
    private EditText nameET;
    private EditText lengthET;
    private EditText widthET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dropped_ceiling_details, container, false);

        // Make this fragment capable of modifying the options menu.
        setHasOptionsMenu(true);

        // Get Views
        nameET = view.findViewById(R.id.name_et);
        lengthET = view.findViewById(R.id.length_et);
        widthET = view.findViewById(R.id.width_et);

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
            createItem();
            // Navigate to ItemCreatorFragment

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createItem(){
        // todo - input validation.

        String name = nameET.getText().toString();
        double length = Double.parseDouble(lengthET.getText().toString());
        double width = Double.parseDouble(widthET.getText().toString());

        String dims = String.format(
                Locale.US,
                "%.1fft x %.1fft  (%.0fSF)",
                width,
                length,
                length * width);

        DroppedCeiling droppedCeiling = new DroppedCeiling(name, getArguments().getInt(PROJECT_ID_KEY));
        droppedCeiling.setWidth(feetToInches(width));
        droppedCeiling.setLength(feetToInches(length));
    }

    private double feetToInches(double feet) {
        return feet * 12;
    }

}
