package com.buffet_user.pojo;

/**
 * Created by akshaybmsa96 on 31/10/17.
 */

public class MenuPojo {

    private CategoryPojo message;
    private boolean error;

    public CategoryPojo getMessage() {
        return message;
    }

    public void setMessage(CategoryPojo message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
