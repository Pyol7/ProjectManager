package com.jeffreyromero.projectmanager.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.adapters.ItemsAdapter;
import com.jeffreyromero.projectmanager.models.Item;
import com.jeffreyromero.projectmanager.models.Project;
import com.jeffreyromero.projectmanager.viewModels.ItemViewModel;
import com.jeffreyromero.projectmanager.viewModels.ProjectViewModel;

import java.util.List;

public class ProjectFragment extends Fragment implements
        ItemsAdapter.OnItemClickListener {

    private static final String PROJECT_ID_KEY = "projectId";
    private static final String ITEM_ID_KEY = "itemId";
    private ItemsAdapter adapter;
    private int projectID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the passed in project id
        if (getArguments() != null) {
            projectID = getArguments().getInt(PROJECT_ID_KEY, 0);
        }

        // Set up list adapter
        adapter = new ItemsAdapter();
        adapter.setOnItemClickListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_fragment, container, false);

        // Setup the recyclerView and adapter
        RecyclerView rv = view.findViewById(R.id.items_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get View Models
        ProjectViewModel projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Get and observe the passed in project
        projectViewModel.getById(projectID).observe(this, new Observer<Project>() {
            @Override
            public void onChanged(Project project) {
                Toast.makeText(getActivity(), "project changed", Toast.LENGTH_SHORT).show();
                // Set the project name as the Title
                TextView projectNameTV = view.findViewById(R.id.project_name_tv);
                projectNameTV.setText(project.getName());
            }
        });

        // Get, observe and set the items to the itemsAdapter
        itemViewModel.getByProjectId(projectID).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
            }
        });

        // Setup a listener on the fab to show the add item fragment
        FloatingActionButton fab = view.findViewById(R.id.add_item_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to ItemTypeSelectionFragment
                Bundle bundle = new Bundle();
                bundle.putInt(PROJECT_ID_KEY, projectID);
                Navigation.findNavController(view).navigate(R.id.action_projectFragment_to_item_type_selection_fragment, bundle);
            }
        });
    }

    @Override
    public void onItemClick(int itemID) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITEM_ID_KEY, itemID);
        Navigation.findNavController(getView()).navigate(R.id.action_projectFragment_to_itemFragment, bundle);
    }

    @Override
    public void onItemLongClick(Item item) {
        Toast.makeText(getActivity(), "long clicked", Toast.LENGTH_SHORT).show();
    }
}
