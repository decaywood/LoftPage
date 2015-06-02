package org.decaywood.entity;

/**
 * Created by decaywood on 2015/5/24.
 */
public class User {

    private String userID;
    private String userLoginName;
    private String userPassWord;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userRight;
    private String userRole;
    private String userLastLoginTime;
    private String userIPAddress;
    private String userStatus;
    private String userLogo;

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public User setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public User setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public User setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getUserNickName() {
        return userLoginName;
    }

    public User setUserNickName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public User setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserRight() {
        return userRight;
    }

    public User setUserRight(String userRight) {
        this.userRight = userRight;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public User setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getUserLastLoginTime() {
        return userLastLoginTime;
    }

    public User setUserLastLoginTime(String userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
        return this;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public User setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public User setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public User setUserLogo(String userLogo) {
        this.userLogo = userLogo;
        return this;
    }
}
