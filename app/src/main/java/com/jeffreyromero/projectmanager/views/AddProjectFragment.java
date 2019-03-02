package com.jeffreyromero.projectmanager.views;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.Project;
import com.jeffreyromero.projectmanager.viewModels.ProjectsViewModel;


public class AddProjectFragment extends Fragment {

    public AddProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return   inflater.inflate(R.layout.add_project_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText nameET = view.findViewById(R.id.name_et);

        // Get the projectsViewModel and scope it to this fragment.
        final ProjectsViewModel projectsViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);

        view.findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                projectsViewModel.insert(new Project(nameET.getText().toString()));

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                Navigation.findNavController(view).navigate(R.id.action_addProjectFragment_pop);
            }
        });

    }
}
