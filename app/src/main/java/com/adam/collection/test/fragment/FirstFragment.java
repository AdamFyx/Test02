package com.adam.collection.test.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adam.collection.test.R;
import com.adam.collection.test.bean.Linker;
import com.adam.collection.test.camera.LogUtil;
import com.adam.collection.test.ui.ThirdActivity;
import com.adam.collection.test.util.LogUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.toolsfinal.adapter.ViewHolderAdapter;

/**
 * -----------------------------------------------------
 * 项目： ProPertyAnimatorDemo
 * 作者： wd_zhu
 * 日期： 2016/7/29 15:24
 * 邮箱： zhushengping@wdit.com.cn
 * 描述：
 * ------------------------------------------------------
 */
public class FirstFragment extends Fragment  {
    private Activity activity;
    private  View view;
    private static final String TAG = "FirstFragment";
    Button skipToActivityBtn;
    Button skipToFragmentBtn;
    private List<Linker> linkerLists=new ArrayList<>();
    private ViewHolderAdapter adapter;
    private ListView listView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.first_fragment,container,false);
        skipToActivityBtn=view.findViewById(R.id.skipToActivity);
        skipToFragmentBtn=view.findViewById(R.id.skipToFragment);
        listView=view.findViewById(R.id.listview);
        //滑动头部
        View header=new View(activity);
        header.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(
                        R.dimen.gf_title_bar_height
                )
        ));
        //初始化联系人列表
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            linkerLists=readContacts();
            //linkerLists.clear();
            adapter=new ViewHolderAdapter(activity,linkerLists);
            listView.setAdapter(adapter);
//            listView.addHeaderView(header);
            //默认显示时第几个item,可以用于操作数据之后 返回列表 定位位置
            listView.setSelection(0);
            //可以在数据为空的时候设置图片显示
            listView.setEmptyView(view.findViewById(R.id.empty_view));
            adapter.notifyDataSetChanged();
            showSomeThing();

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                    switch (i){
                        case SCROLL_STATE_IDLE:
                            //滑动停止了
                            LogUtils.d("setOnScrollListener","滑动停止了");
                            break;
                            case SCROLL_STATE_TOUCH_SCROLL:
                                //正在滚动
                                LogUtils.d("setOnScrollListener","正在滚动");
                                break;
                        case    SCROLL_STATE_FLING:
                            //手指抛动时，即手指用力滑动
                            LogUtils.d("setOnScrollListener","惯性滑动");
                                break;
                    }
                }
                    int lastId=0;
                @Override
                public void onScroll(AbsListView absListView, int firstId, int countItem, int countListview) {
                    /*
                     firstId 当前能看到到第一个item的id(从0开始)
                     countItem 当前能看到的Item总数
                     countListview 整个listview的item总数
                     */
                    LogUtils.d("onScroll","滚动时一直在调用");
                    LogUtils.d("onScroll","i-->"+firstId);
                    LogUtils.d("onScroll","i-->"+countItem);
                    LogUtils.d("onScroll","i-->"+countListview);
                    if(firstId+countItem==countListview&&countListview>0){
                        //滚动到最后一行了
                    }
                    if(firstId>lastId){
                        //上滑
                    }else  if(firstId<lastId){
                        //下滑
                    }
                    lastId=firstId;
                }

            });
        }
        return view;
    }

    private  void showSomeThing(){
        //可视区域里面的第一个和最后一个的id
        LogUtils.d("showSomeThing",listView.getLastVisiblePosition()+"");
        LogUtils.d("showSomeThing",listView.getFirstVisiblePosition()+"");

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        skipToActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, ThirdActivity.class));
            }
        });
        skipToFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            skipFagment(new SecondFragment());
            }
        });
    }
    private void skipFagment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    //读取手机联系人
    private  List<Linker> readContacts() {
        Cursor cursor = null;
        cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Linker linker ;
            while (cursor.moveToNext()) {
                linker= new Linker();
                //获取联系人姓名
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                //获取联系人手机号
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                linker.setName(displayName);
                linker.setNumber(number);
                LogUtils.d("readContacts", linker.toString());
                linkerLists.add(linker);

            }
        }
        return linkerLists;

    }


    public  class ViewHolderAdapter extends BaseAdapter{

        private  List<Linker> linkerLists;
        private  LayoutInflater mlayoutInflater;

        public  ViewHolderAdapter(Context context,List<Linker> data){
            this.linkerLists=data;
            LogUtils.d("ViewHolderAdapter",linkerLists.size()+"");
            mlayoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return linkerLists.size();
        }

        @Override
        public Object getItem(int position) {
            return linkerLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            //判断是否缓存
            if(convertView==null){
                holder=new ViewHolder();
                //通过LayoutInflater实例化布局
                convertView=mlayoutInflater.inflate(R.layout.viewholder_item,null);
                holder.name=convertView.findViewById(R.id.name);
                holder.number=convertView.findViewById(R.id.number);
                convertView.setTag(holder);
            }else{
                //通过tag找到缓存的布局
                holder= (ViewHolder) convertView.getTag();
            }

            holder.name.setText(linkerLists.get(i).getName());
            holder.number.setText(linkerLists.get(i).getNumber());
            return convertView;
        }

        public  final class ViewHolder{
            public TextView name;
            public  TextView number;
        }

    }
}
