package org.decaywood.exceptions;

/**
 * @author: decaywood
 * @date: 2015/6/7 21:53
 */
public class UserConflictException extends Exception {

    public UserConflictException(String errorInfo) {
        super(errorInfo);
    }
}
