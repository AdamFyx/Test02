package com.adam.collection.test.bean;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/12 18:37
 * <br/>
 *
 * @since
 */
public class Category {
    private  int id;
    private String categoryName;
    private  int categoryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
