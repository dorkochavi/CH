package com.hak.hacktest.activities.response;

import com.hak.hacktest.activities.objects.Project;

import java.util.ArrayList;


public class GetRemoteProjects {
    private boolean status;
    private ArrayList<Project> projectList;

    public GetRemoteProjects(boolean status, ArrayList<Project> projectList) {
        this.status = status;
        this.projectList = projectList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<Project> projectList) {
        this.projectList = projectList;
    }
}
