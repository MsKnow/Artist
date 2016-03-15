package com.example.know.model;

/**
 * Created by know on 2016/2/21.
 */
public class SelfieCard extends Card{

    private boolean blur = false;

    public boolean isBlur() {
        return blur;
    }

    public void setBlur(boolean blur) {
        this.blur = blur;
    }

    @Override
    public String toString() {
        return super.toString()+"SelfieCard{" +
                "blur=" + blur +
                '}';
    }
}
