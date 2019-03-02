package com.jeffreyromero.projectmanager.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.itemTypes.DroppedCeiling;
import com.jeffreyromero.projectmanager.viewModels.ItemsViewModel;


public class AddItemFragment extends Fragment {

    private static final String PROJECT_ID_KEY = "projectIdKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return   inflater.inflate(R.layout.add_item_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText nameET = view.findViewById(R.id.name_et);
        final ItemsViewModel itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel.class);
        view.findViewById(R.id.add_item_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsViewModel.insert(new DroppedCeiling(nameET.getText().toString(), getArguments().getInt(PROJECT_ID_KEY)));
//                Navigation.findNavController(view).navigate(R.id.action_addItemFragment_pop);
            }
        });

    }
}
