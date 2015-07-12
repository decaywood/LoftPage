package org.decaywood.utils;

/**
 * Created by decaywood on 2015/5/24.
 */
public enum NameDomainMapper {

    USER_ID,
    USER_LOGIN_NAME("userName"),
    USER_PASSWORD("password"),
    USER_NICKNAME("userNickName"),
    USER_EMAIL("userEmail"),
    USER_PHONE("userPhone"),
    USER_ROLE,
    USER_LAST_LOGIN_TIME,
    USER_STATUS,
    USER_LOGO,
    VALIDATE_CODE,

    SESSION_USER_LOGIN_NAME,

    SESSION_SECURITY_CODE,
    SYSTEM_NAME,
    REQUEST_DATAS,

    ROLE_USER,
    ROLE_ADMINISTRATOR,

    STATUS_LOGIN,

    LOGO_PATH,

    BACK_SLASH("\\"),

    ERROR_INFO("error! details as follow-> "),
    ERROR_INFO1("validate code does not match!"),
    ERROR_INFO2("password or userName is fault!"),
    ERROR_INFO3("Image Upload Faild!"),

    LOGIN_PAGE("login"),
    MAIN_PAGE("2048"),
    REGISTER_PAGE("register");

    private String enumName;

    NameDomainMapper() {
    }

    NameDomainMapper(String enumName) {
        this.enumName = enumName;
    }

    public String getName() {
        return enumName == null ? name() : enumName;
    }

}
