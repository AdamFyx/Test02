package com.adam.collection.test.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: BabyWeekend Inc.
 * <br/>
 * Created at: 2020/10/16 17:03
 * <br/>
 *
 * @since
 */
public class Linker  {
    private  String name;
    private  String number;
    public Linker linker;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



}
