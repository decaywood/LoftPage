package org.decaywood.entity;

import java.sql.Date;

/**
 * Created by decaywood on 2015/5/24.
 */
public class User {

    //======================= PADDING ======================
    private String userID;
    private String userRole;
    private Date userLastLoginTime;
    private Date userRegisterTime;
    private String userStatus;
    //======================= PADDING ======================

    private String userLoginName;
    private String userPassword;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userIPAddress;
    private String userLogoURL;

    private String highestScore;


    private String userHighestScore;


    public String getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(String highestScore) {
        this.highestScore = highestScore;
    }

    public String getUserID() {
        return userID;
    }

    public User setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public String getUserHighestScore() {
        return userHighestScore;
    }

    public User setUserHighestScore(String userHighestScore) {
        this.userHighestScore = userHighestScore;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public User setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public Date getUserLastLoginTime() {
        return userLastLoginTime;
    }

    public User setUserLastLoginTime(Date userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
        return this;
    }

    public Date getUserRegisterTime() {
        return userRegisterTime;
    }

    public User setUserRegisterTime(Date userRegisterTime) {
        this.userRegisterTime = userRegisterTime;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public User setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public User setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public User setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public User setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public User setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
        return this;
    }

    public String getUserLogoURL() {
        return userLogoURL;
    }

    public User setUserLogoURL(String userLogoURL) {
        this.userLogoURL = userLogoURL;
        return this;
    }
}
