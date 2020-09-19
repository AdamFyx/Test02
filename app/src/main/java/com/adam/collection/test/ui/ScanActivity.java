package com.adam.collection.test.ui;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;

import com.adam.collection.test.scan.DecodeData;
import com.adam.collection.test.scan.DecodeRunable;
import com.adam.collection.test.scan.FinderView;
import com.adam.collection.test.scan.WeakHandler;
import com.adam.collection.test.R;
import com.adam.collection.test.util.PermissionUtil;
import com.google.zxing.BarcodeFormat;
import com.jerryjin.kit.ImmersiveHelper;
import com.jerryjin.kit.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.statusBar.OnOptimizeCallback;
import com.jerryjin.kit.statusBar.StatusBarHelper;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.core.OnNotchCallBack;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 扫描页面
 * @author Administrator
 */
public class ScanActivity extends Activity implements SurfaceHolder.Callback {
//	public static final String TAG = "ZbarFinderActivity";
//	public static final String ResultType = "ResultType";
//	public static final String ResultContent = "ResultContent";
	private Camera mCamera;
	private SurfaceHolder mHolder;
	protected SurfaceView surface_view;
	protected FinderView finder_view;
	private ImageView imageView;
	private Handler autoFocusHandler;
	private ThreadPoolExecutor fixPool;
	private LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
	private boolean reciveReuslt = false;
	private Activity activity;
	private RelativeLayout top, bottom;
	private LinearLayout.LayoutParams bottomParams;
	private TextView tvTitle;
	private int navBarHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		//打开返回
		activity = this;
		PermissionUtil.requestPermissions(this, com.adam.collection.test.ui.Constants.REQUEST_PERMISSIONS, Manifest.permission.CAMERA);
		init();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == com.adam.collection.test.ui.Constants.REQUEST_PERMISSIONS) {
			if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(activity, "必须授权以后方可使用扫码功能！", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		ImmersiveHelper.optimize(this, new OnNotchCallBack() {
			@Override
			public void onNotchReady(NotchProperty notchProperty) {
				if (notchProperty.isNotchEnable()) {
					LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) top.getLayoutParams();
					params.topMargin = notchProperty.getNotchHeight() + StatusBarHelper.DEFAULT_OFFSET;
					top.setLayoutParams(params);
				}
			}
		}, Constants.TOOLBAR_COLOR, new OnOptimizeCallback() {
			@Override
			public void onOptimized(int statusBarHeight, boolean isNavBarShow, int navigationBarHeight) {
				navBarHeight = navigationBarHeight;
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (ImmersiveHelper.hasNotch(this) && NavigationBarHelper.isNavBarShow(this)) {
			bottomParams.bottomMargin = navBarHeight;
			bottom.setLayoutParams(bottomParams);
		}
	}

	@SuppressWarnings("deprecation")
	private void init() {
		top = findViewById(R.id.top);
		tvTitle = findViewById(R.id.text_title);
		bottom = findViewById(R.id.bottom);
		bottomParams = (LinearLayout.LayoutParams) bottom.getLayoutParams();
		imageView = (ImageView) findViewById(R.id.iv_back);
		Drawable drawable = imageView.getDrawable();
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		Drawable wrapped = DrawableCompat.wrap(drawable);
		DrawableCompat.setTint(wrapped, Color.parseColor("#2c2d2e"));
		imageView.setImageDrawable(wrapped);
		surface_view = (SurfaceView) findViewById(R.id.preview_view);
		finder_view = (FinderView) findViewById(R.id.viewfinder_view);
		//扫描
		mHolder = surface_view.getHolder();
		//在2.3的系统中需要
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mHolder.addCallback(this);
		autoFocusHandler = new Handler();

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

//	private boolean roate90 = false;

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (mHolder.getSurface() == null) {
			return;
		}
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
		}
		try {
//			if (width < height) {
//				roate90 = true;
//				mCamera.setDisplayOrientation(90);
//			} else {
//				roate90 = false;
//				mCamera.setDisplayOrientation(0);
//			}
			mCamera.setDisplayOrientation(90);
			mCamera.setPreviewDisplay(mHolder);
			mCamera.setPreviewCallback(previewCallback);
			mCamera.startPreview();
			mCamera.autoFocus(autoFocusCallback);
		} catch (Exception e) {

		}
	}

	public boolean isReciveReuslt() {
		return reciveReuslt;
	}

	public void setReciveReuslt(boolean reciveReuslt) {
		this.reciveReuslt = reciveReuslt;
	}

	/**
	 * 结果
	 */
	private QrActivityHandler handler = new QrActivityHandler(this) {
		@Override
		public void handleMessage(Message msg) {
			if (activiceReference.get() != null) {
				if (msg.what == 0) {
					if (!fixPool.isShutdown()) {
						fixPool.shutdownNow();
					}
//					Intent intent = new Intent(MainActivity.ACTION_SAO_RESULT);
//					intent.putExtras(msg.getData());
//					startActivity(intent);
//					finish();
					String content="";
					Bundle data = msg.getData();
					if (null == data) {
//						Toast.makeText(ScanActivity.this, "没有扫描到任何结果", Toast.LENGTH_SHORT).show();
					}
					Serializable serializable = data.getSerializable("BarcodeFormat");
					if (serializable instanceof BarcodeFormat) {
						BarcodeFormat barcodeFormat = (BarcodeFormat) serializable;
						if (barcodeFormat == BarcodeFormat.QR_CODE) {
							content = data.getString("text");
							Log.d("我是扫描后展示的结果",content);
//							Toast.makeText(ScanActivity.this, text, Toast.LENGTH_SHORT).show();
						}/* else {
//							Toast.makeText(ScanActivity.this, "该类型不是二维码", Toast.LENGTH_SHORT).show();
						}*/
					}/* else {
						Toast.makeText(ScanActivity.this, "扫描结果错误", Toast.LENGTH_SHORT).show();
					}*/
					setResult(200,getIntent().putExtra("result",content));
					finish();
					overridePendingTransition(R.anim.right_slide_none,R.anim.right_slide_out);
				}
			}
		}
	};
	/**
	 * 预览数据
	 */
	@SuppressWarnings("deprecation")
	private PreviewCallback previewCallback = new PreviewCallback() {
		public void onPreviewFrame(byte[] data, Camera camera) {
			if (!reciveReuslt && !fixPool.isShutdown() && fixPool.getActiveCount() < 5) {
				Camera.Parameters parameters = camera.getParameters();
				Size size = parameters.getPreviewSize();
				//获取预览图的大小
				Rect preRect = finder_view.getScanImageRect(size);
				DecodeData decodeData = new DecodeData(data, size, preRect);
//				imageView.setImageBitmap(ZxingTools.getBarCodeBitMap(decodeData, roate90));
				Runnable command = new DecodeRunable(handler, decodeData, false);
				fixPool.execute(command);
			}
		}
	};

	private static class QrActivityHandler extends WeakHandler<ScanActivity> {

		public QrActivityHandler(ScanActivity qrFinderActivity) {
			super(qrFinderActivity);
		}
	}

	/**
	 * 自动对焦回调
	 */
	AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
		public void onAutoFocus(boolean success, Camera camera) {
			autoFocusHandler.postDelayed(doAutoFocus, 1000);
		}
	};

	//自动对焦
	private Runnable doAutoFocus = new Runnable() {
		public void run() {
			if (null == mCamera || null == autoFocusCallback) {
				return;
			}
			mCamera.autoFocus(autoFocusCallback);
		}
	};

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open();
			fixPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, workQueue);
		} catch (Exception e) {
			mCamera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
		if (null != fixPool && !fixPool.isShutdown()) {
			fixPool.shutdownNow();
		}
	}

	@Override
	public void onBackPressed() {
		ScanActivity.this.finish();
		overridePendingTransition(R.anim.alpha_null, R.anim.alpha_out);
	}
}
