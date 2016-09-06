package phlx.jikong.ccc_ui.wnd;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import phlx.jikong.R;
import phlx.jikong.utils.Strings;

public class WndVideo extends LinearLayout implements WndBase, SurfaceHolder.Callback 
{
	public static final int ACTION_PLAY_PAUSE = 0;
	public static final int ACTION_OVERLAY_SHOW = 2;
	public static final int ACTION_OVERLAY_HIDE = 3;

	public interface IWndVideo 
	{
		void wndSurfaceChanged(Surface surface);
		void wndSurfaceDestroyed();
		void PlayAction(int action);
	}

	private IWndVideo  m_listener = null;
	public static final String TAG_MOVIE_NAME = "tag_movie_name";
	public static final String TAG_MOVIE_TYPE = "tag_movie_type";

	private static final int FADE_OUT = 1;
	
	private static final int SURFACE_BEST_FIT = 0;
	private static final int SURFACE_FIT_HORIZONTAL = 1;
	private static final int SURFACE_FIT_VERTICAL = 2;
	private static final int SURFACE_FILL = 3;
	private static final int SURFACE_ORIGINAL = 4;

	/** Overlay */
	private View mOverlayHeader;
	private View mOverlayOption;
	private View mOverlayProgress;

	private TextView mTitle;
	private TextView mSysTime;
	private ImageButton mPlayPause;
	private ImageButton mSize;
	
	private ImageButton mLock;
	private TextView mTime;
	private SeekBar mSeekbar;
	private TextView mLength;
	private static final int OVERLAY_TIMEOUT = 4000;
	private static final int OVERLAY_INFINITE = 3600000;
	private boolean mShowing = false;
	private boolean mIsLocked = false;
	private boolean mDragging = false;
	private boolean mCanSeek;

	private SurfaceView surfaceView = null;
	private SurfaceHolder surfaceHolder = null;
	private FrameLayout mSurfaceFrame;
	private int mCurrentSize = SURFACE_BEST_FIT;

	public WndVideo(Context context) {
		super(context);

		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.wnd_video, this);

		mOverlayHeader = findViewById(R.id.player_overlay_header);
		mOverlayOption = findViewById(R.id.option_overlay);
		mOverlayProgress = findViewById(R.id.progress_overlay);

		/* 头部 */
		mTitle = (TextView) findViewById(R.id.player_overlay_title);
		mSysTime = (TextView) findViewById(R.id.player_overlay_systime);

		// Position and remaining time
		mLock = (ImageButton) findViewById(R.id.lock_overlay_button);
		mLock.setOnClickListener(mLockListener);
		mTime = (TextView) findViewById(R.id.player_overlay_time);
		mLength = (TextView) findViewById(R.id.player_overlay_length);
		mSeekbar = (SeekBar) findViewById(R.id.player_overlay_seekbar);
		// mSeekbar.setOnSeekBarChangeListener(mSeekListener);

		mSize = (ImageButton) findViewById(R.id.player_overlay_size);
		mSize.setOnClickListener(mSizeListener);

		mPlayPause = (ImageButton) findViewById(R.id.player_overlay_play);
		mPlayPause.setOnClickListener(mPlayPauseListener);
		// the info textView is not on the overlay
		
		InitSurface();

		System.out.printf("------  WndVideo WndVideo\n");

	}

	private void InitSurface() {
		mSurfaceFrame = (FrameLayout) findViewById(R.id.player_surface_frame);

		surfaceView = (SurfaceView) findViewById(R.id.sv_player);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.setFormat(PixelFormat.RGBX_8888);
		surfaceHolder.addCallback(this);
	}

	/**
	 * Lock screen rotation
	 */
	private void lockScreen() {
		// showInfo(R.string.locked, 1000);
		mLock.setBackgroundResource(R.drawable.ic_locked);
		mTime.setEnabled(false);
		mSeekbar.setEnabled(false);
		mLength.setEnabled(false);
		hideOverlay(true);
	}

	/**
	 * Remove screen lock
	 */
	private void unlockScreen() {
		// showInfo(R.string.unlocked, 1000);
		mLock.setBackgroundResource(R.drawable.ic_lock);
		mTime.setEnabled(true);
		mSeekbar.setEnabled(true);
		mLength.setEnabled(true);
		mShowing = false;
		showOverlay();
	}

	private void hideOverlay(boolean fromUser) {
		if (mShowing) {
			// jia --- m_playerHandler.removeMessages(SHOW_PROGRESS);
			// Log.i(TAG, "remove View!");
			// if (mOverlayTips != null)
			// mOverlayTips.setVisibility(View.INVISIBLE);

			mOverlayHeader.setVisibility(View.INVISIBLE);
			mOverlayOption.setVisibility(View.INVISIBLE);
			mOverlayProgress.setVisibility(View.INVISIBLE);
			mPlayPause.setVisibility(View.INVISIBLE);
			mShowing = false;
			// dimStatusBar(true);
		}
	}

	private void showOverlay(int timeout) {
		// m_playerHandler.sendEmptyMessage(SHOW_PROGRESS);
		if (!mShowing) {
			mShowing = true;
			if (!mIsLocked) {
				mOverlayHeader.setVisibility(View.VISIBLE);
				mOverlayOption.setVisibility(View.VISIBLE);
				mPlayPause.setVisibility(View.VISIBLE);
				
				// mMenu.setVisibility(View.VISIBLE);
				// dimStatusBar(false);

				System.out.println("WndVideo showOverlay----");
			}
			mOverlayProgress.setVisibility(View.VISIBLE);
			// if (mPresentation != null)
			// mOverlayBackground.setVisibility(View.VISIBLE);
		}
		// Message msg = m_playerHandler.obtainMessage(FADE_OUT);
		if (timeout != 0) {
			// m_playerHandler.removeMessages(FADE_OUT);
			// m_playerHandler.sendMessageDelayed(msg, timeout);
		}
		// updateOverlayPausePlay();
	}

	/**
	 * show overlay the the default timeout
	 */
	private void showOverlay() {
		showOverlay(OVERLAY_TIMEOUT);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.printf("WndVideo onTouchEvent = %d\n", event.getAction());
		// if (mIsLocked)
		{
			// locked, only handle show/hide & ignore all actions
			// 只有按下事件，收不到别的事件
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (!mShowing) {
					showOverlay();
					this.m_listener.PlayAction(ACTION_OVERLAY_SHOW);
				} else {
					hideOverlay(true);
					this.m_listener.PlayAction(ACTION_OVERLAY_HIDE);
				}

				// mShowing = !mShowing;
			}
			// return false;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.printf("------  WndVideo surfaceCreated\n");

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out
				.printf("------  WndVideo surfaceChanged width = %d, height = %d. format = %d\n",
						width, height, format);
		m_listener.wndSurfaceChanged(holder.getSurface());
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.printf("------  WndVideo surfaceDestroyed\n");
		m_listener.wndSurfaceDestroyed();

	}

	private final OnClickListener mPlayPauseListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			m_listener.PlayAction(ACTION_PLAY_PAUSE);
		}
	};
	
	private final OnClickListener mSizeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (mCurrentSize < SURFACE_ORIGINAL) {
				mCurrentSize++;
			} else {
				mCurrentSize = 0;
			}
			// changeSurfaceSize();
		}
	};
	private final OnClickListener mLockListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mIsLocked) {
				mIsLocked = false;
				unlockScreen();
			} else {
				mIsLocked = true;
				lockScreen();
			}
		}
	};

	private void changeSurfaceSize(int width, int height) {
		// get screen size
		int dw = getWidth();
		int dh = getHeight();

		if (dw * dh == 0)
			return;

		// calculate aspect ratio
		double ar = (double) width / (double) height;
		// calculate display aspect ratio
		double dar = (double) dw / (double) dh;

		switch (mCurrentSize) {
		case SURFACE_BEST_FIT:
			if (dar < ar)
				dh = (int) (dw / ar);
			else
				dw = (int) (dh * ar);
			break;
		case SURFACE_FIT_HORIZONTAL:
			dh = (int) (dw / ar);
			break;
		case SURFACE_FIT_VERTICAL:
			dw = (int) (dh * ar);
			break;
		case SURFACE_FILL:
			break;

		case SURFACE_ORIGINAL:
			dh = height;
			dw = width;
			break;
		}

		surfaceHolder.setFixedSize(width, height);
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) surfaceView
				.getLayoutParams();
		lp.width = dw;
		lp.height = dh;
		surfaceView.setLayoutParams(lp);

		lp = (FrameLayout.LayoutParams) mSurfaceFrame.getLayoutParams();
		lp.width = (int) Math.floor(dw);
		lp.height = (int) Math.floor(dh);
		mSurfaceFrame.setLayoutParams(lp);

		surfaceView.invalidate();
		// System.out.printf("surfaceView.invalidate() picture status %d\n",
		// mCurrentSize);
	}

	public void SetSize(int width, int height) {
		changeSurfaceSize(width, height);
	}

	public int setOverlayProgress(int time, int length) {

		mSeekbar.setMax(length);
		mSeekbar.setProgress(time);
		// mSysTime.setText(DateFormat.getTimeFormat(this).format(new
		// Date(System.currentTimeMillis())));
		if (time >= 0)
			mTime.setText(Strings.millisToString(time));
		if (length >= 0)
			mLength.setText(Strings.millisToString(length));

		// System.out.printf("setOverlayProgress %s\n",
		// DateFormat.getTimeFormat(this).format(new
		// Date(System.currentTimeMillis())));
		return time;
	}

	public void SetTitle(String title) {
		mTitle.setText(title);
	}

	public boolean SetVideoListener(IWndVideo l) {
		m_listener = l;
		return true;
	}

	
	/* ******************  接口 WndBase  ****************** */
	@Override
	public void Clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetSize(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetVisible(boolean b_visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetEnable(boolean b_enable) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View GetOwner() {return this;}
	@Override
	public void addCallback(Callback callback, Object uiBase) {
	}
}
