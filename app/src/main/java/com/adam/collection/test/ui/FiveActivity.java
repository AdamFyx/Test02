package com.adam.collection.test.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.adam.collection.test.R;
import com.adam.collection.test.Thread.ThreadPoolManager;
import com.adam.collection.test.bean.ImageBean;
import com.adam.collection.test.util.BitmapHelper;
import com.adam.collection.test.util.DiskLruCacheUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FiveActivity extends AppCompatActivity {
    Context context;
    DiskLruCacheUtil mDiskLruCacheUtil;
    ViewHolderAdapter adapter;
    ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();
    ListView listView;
    Handler handler = new Handler();
    List<String> imageList2 = imageList();
    private int j;
    private boolean scrState = true;

    public static List<String> imageList() {
        List<String> mUrls = new ArrayList<String>();
        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
        mUrls.add("http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg");
        mUrls.add("http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg");
        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg");
        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg");
        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");
        mUrls.add("http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg");
        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg");
        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg");
        mUrls.add("http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg");
        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg");
        mUrls.add("http://img2.xkhouse.com/bbs/hfhouse/data/attachment/forum/corebbs/2009-11/2009113011534566298.jpg");
        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg");
        mUrls.add("http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg");
        mUrls.add("http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg");
        mUrls.add("http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg");
        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg");
        mUrls.add("http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg");
        mUrls.add("http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg");
        mUrls.add("http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg");
        return mUrls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        context = this;
        mDiskLruCacheUtil = new DiskLruCacheUtil(this, "Images");
        listView = findViewById(R.id.listview);
        adapter = new ViewHolderAdapter(this, imageList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < imageList2.size(); i++) {
            j = i;
            if ((mDiskLruCacheUtil.getBytesCache(i + "")) != null) {
                ImageBean imageBean = new ImageBean();
                imageBean.setBitmap(BitmapHelper.ByteToBitmap(mDiskLruCacheUtil.getBytesCache(i + "")));
                imageList.add(imageBean);
                adapter.notifyDataSetChanged();
            } else {
                //使用线程池继续来加载网络上的图片
                ThreadPoolManager.getInstance().execute(new DownImageTask(j + "", imageList2.get(j)));
            }
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        Log.d("onScrollStateChanged", "SCROLL_STATE_IDLE");
                        adapter.notifyDataSetChanged();
                        scrState = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        Log.d("onScrollStateChanged", "SCROLL_STATE_FLING");
                        scrState = false;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.d("onScrollStateChanged", "SCROLL_STATE_TOUCH_SCROLL");
                        scrState = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    //模仿下载任务
    class DownImageTask implements Runnable {
        private String tag;
        private String url;

        public DownImageTask(String TAG, String URL) {
            super();
            this.tag = TAG;
            this.url = URL;
        }

        @Override
        public void run() {
            FutureTarget<Bitmap> futureBitmap = Glide.with(context).asBitmap()
                    .load(url)
                    .submit();
            try {
                Bitmap bitmap = futureBitmap.get();
                mDiskLruCacheUtil.put(tag, BitmapHelper.BitmapToByte(bitmap));
                ImageBean imageBean = new ImageBean();
                imageBean.setBitmap(bitmap);
                imageList.add(imageBean);
                if (scrState) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class ViewHolderAdapter extends BaseAdapter {

        private LayoutInflater mlayoutInflater;

        public ViewHolderAdapter(Context context, ArrayList<ImageBean> data) {
            imageList = data;
            mlayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int position) {
            return imageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            FiveActivity.ViewHolderAdapter.ViewHolder holder = null;
            //判断是否缓存
            if (convertView == null) {
                holder = new FiveActivity.ViewHolderAdapter.ViewHolder();
                //通过LayoutInflater实例化布局
                convertView = mlayoutInflater.inflate(R.layout.viewholder_item1, null);
                holder.imageView = convertView.findViewById(R.id.image3);

                convertView.setTag(holder);
            } else {
                //通过tag找到缓存的布局
                holder = (FiveActivity.ViewHolderAdapter.ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageBitmap(imageList.get(i).getBitmap());

            return convertView;

        }

        public final class ViewHolder {
            public ImageView imageView;
        }

    }
}