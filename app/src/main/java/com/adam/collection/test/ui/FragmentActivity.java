package com.adam.collection.test.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adam.collection.test.R;
import com.adam.collection.test.fragment.FirstFragment;
import com.adam.collection.test.fragment.SecondFragment;
import com.adam.collection.test.fragment.ThirdFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();

    }

    private void initView() {
        findViewById(R.id.fragment1).setOnClickListener(this);
        findViewById(R.id.fragment2).setOnClickListener(this);
        findViewById(R.id.fragment3).setOnClickListener(this);
        skipFagment(new FirstFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment1:
                skipFagment(new FirstFragment());
                break;
            case R.id.fragment2:
                skipFagment(new SecondFragment());
                break;
            case R.id.fragment3:
                skipFagment(new ThirdFragment());
                break;
        }
    }

    private void skipFagment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }


}