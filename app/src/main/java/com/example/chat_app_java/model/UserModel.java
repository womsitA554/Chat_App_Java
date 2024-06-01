package com.example.chat_app_java.model;

import com.google.firebase.Timestamp;

public class UserModel {
    private String phone;
    private String username;
    private Timestamp createTimestamp;
    private String userId;
    private String fcmToken;

    // Constructor không tham số
    public UserModel() {
        // Bắt buộc để Firestore có thể tạo đối tượng này
    }

    // Constructor có tham số
    public UserModel(String phone, String username, Timestamp createTimestamp, String userId) {
        this.phone = phone;
        this.username = username;
        this.createTimestamp = createTimestamp;
        this.userId = userId;
    }

    // Getter và Setter cho các trường dữ liệu
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
