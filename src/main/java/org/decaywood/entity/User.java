package org.decaywood.entity;

/**
 * Created by decaywood on 2015/5/24.
 */
public class User {

    private String userID;
    private String userLoginName;
    private String userPassWord;
    private String userName;
    private String userRight;
    private String userRole;
    private String userLastLoginTime;
    private String userIPAddress;
    private String userStatus;
    private String userLogo;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserNickName() {
        return userLoginName;
    }

    public void setUserNickName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRight() {
        return userRight;
    }

    public void setUserRight(String userRight) {
        this.userRight = userRight;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserLastLoginTime() {
        return userLastLoginTime;
    }

    public void setUserLastLoginTime(String userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public void setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }
}
