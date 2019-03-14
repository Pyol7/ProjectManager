package com.jeffreyromero.projectmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffreyromero.projectmanager.R;
import com.jeffreyromero.projectmanager.models.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    // Initializes to empty array to avoid NPE
    private List<Project> projects = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(int projectID);
        void onItemLongClick(Project project);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.projects_adapter_list_item, viewGroup, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder projectViewHolder, int i) {
        Project currentProject = projects.get(i);
        projectViewHolder.projectNameTV.setText(currentProject.getName());
        projectViewHolder.dateCreatedTV.setText(currentProject.getDateCreatedAsString());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {

        private TextView projectNameTV;
        private TextView dateCreatedTV;

        ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNameTV = itemView.findViewById(R.id.project_name_tv);
            dateCreatedTV = itemView.findViewById(R.id.date_created_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(projects.get(getAdapterPosition()).getId());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(projects.get(getAdapterPosition()));
                    return true;
                }

            });
        }
    }
}
