package com.hak.hacktest.activities.response;



public class Response_add_project {
    private boolean status;
    private String path;
    private String project_id;

    public Response_add_project(boolean status, String path , String project_id) {
        this.status = status;
        this.path = path;
        this.project_id = project_id;
    }

    public Response_add_project() {
    }

    public boolean isStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public String getProject_id() {
        return project_id;
    }
}
