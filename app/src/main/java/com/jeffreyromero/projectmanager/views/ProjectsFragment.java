package com.jeffreyromero.projectmanager.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.adapters.ProjectsAdapter;
import com.jeffreyromero.projectmanager.models.Project;
import com.jeffreyromero.projectmanager.viewModels.ProjectsViewModel;

import java.util.List;

/**
 * Displays the list of projects
 */
public class ProjectsFragment extends Fragment implements
        ProjectsAdapter.OnItemClickListener {

    private static final String PROJECT_ID_KEY = "projectIdKey";
    private ProjectsViewModel mViewModel;
    private ProjectsAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.projects_fragment, container, false);

        // Set up list adapter
        adapter = new ProjectsAdapter();
        adapter.setOnItemClickListener(this);

        // Setup the recyclerView and adapter
        RecyclerView rv = view.findViewById(R.id.projects_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the mViewModel and scope it to this fragment.
        mViewModel = ViewModelProviders.of(this).get(ProjectsViewModel.class);

        // Get all projects, setup a observer on it and update the list adapter when ever it changes
        mViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                adapter.setProjects(projects);
            }
        });
        // Setup a listener on the fab to show the add project fragment
        FloatingActionButton fab = view.findViewById(R.id.add_project_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_projectsFragment_to_addProjectFragment);
            }
        });

    }

    @Override
    public void onItemClick(int projectID) {
        // Show project fragment
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID_KEY, projectID);
        Navigation.findNavController(getView()).navigate(R.id.action_projectsFragment_to_projectFragment, bundle);
    }

    @Override
    public void onItemLongClick(Project project) {
        mViewModel.delete(project);
    }
}

//        Toast.makeText(getActivity(), "Project: " + project, Toast.LENGTH_SHORT).show();
