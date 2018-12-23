package com.liu.jpa;

import com.liu.core.CodeAndText;

/**
 * @author liu
 * @date 2018/12/23 15:41
 */
public enum Role  implements CodeAndText {

    User("User", "User"),
    Admin("Admin", "Admin");

    Role(String code, String textChs) {
        this.code = code;
        this.textChs = textChs;
    }

    private final String code;

    private final String textChs;



    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTextChs() {
        return textChs;
    }
}
