package com.hak.hacktest.activities.objects;

import java.io.Serializable;


public class ChatRoom implements Serializable {
    private String path;
    private String chatName;
    private String SellerName;
    private String UserName;
    private String projectID;
    private String roomID;
    private String description;
    private long lastVisit;


    public ChatRoom(String path, String chatName, String sellerName, String userName, String projectID, String roomID,String description ) {
        this.path = path;
        this.chatName = chatName;
        SellerName = sellerName;
        UserName = userName;
        this.projectID = projectID;
        this.roomID = roomID;
        this.description = description;
    }



    public ChatRoom() {
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }



    @Override
    public String toString() {
        return "ChatRoom{" +
                "path='" + path + '\'' +
                ", chatName='" + chatName + '\'' +
                ", SellerName='" + SellerName + '\'' +
                ", UserName='" + UserName + '\'' +
                ", projectID='" + projectID + '\'' +
                ", roomID='" + roomID + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(long lastVisit) {
        this.lastVisit = lastVisit;
    }
}
