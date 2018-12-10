
package app.card.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import app.card.R;

public class CircleHeadImageView extends ImageView {
	//设置图片在View上的显示模式ScaleType CENTER_CROP：按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
	private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
	//ALPHA_8 代表8位Alpha位图
	//ARGB_4444 代表16位ARGB位图
	//ARGB_8888 代表32位ARGB位图
	//RGB_565 代表8位RGB位图
	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 2;
	
	private static final int DEFAULT_BORDER_WIDTH = 0;
	private static final int DEFAULT_BORDER_COLOR = Color.WHITE;
	private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
	private static final boolean DEFAULT_BORDER_OVERLAY = false;
	
	private final RectF mDrawableRect = new RectF();
	private final RectF mBorderRect = new RectF();
	
	private final Matrix mShaderMatrix = new Matrix();
	//图片画笔
	private final Paint mBitmapPaint = new Paint();
	//外部圆环画笔；
	private final Paint mBorderPaint = new Paint();
	private final Paint mFillPaint = new Paint();
	
	private int mBorderColor = DEFAULT_BORDER_COLOR;
	private int mBorderWidth = DEFAULT_BORDER_WIDTH;
	//图片背景颜色；
	private int mFillColor = DEFAULT_FILL_COLOR;
	
	private Bitmap mBitmap;
	private BitmapShader mBitmapShader;
	private int mBitmapWidth;
	private int mBitmapHeight;
	
	private float mDrawableRadius;
	private float mBorderRadius;
	
	private ColorFilter mColorFilter;
	
	private boolean mReady;
	private boolean mSetupPending;
	private boolean mBorderOverlay;
	private boolean mDisableCircularTransformation;
	
	public CircleHeadImageView(Context context) {
		super(context);
		System.out.println("Log_单参构造");
		init();
	}
	
	public CircleHeadImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}
	
	public CircleHeadImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		System.out.println("Log_多参构造");
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
		
		mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, DEFAULT_BORDER_WIDTH);
		mBorderColor = a.getColor(R.styleable.CircleImageView_civ_border_color, DEFAULT_BORDER_COLOR);
		mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_civ_border_overlay, DEFAULT_BORDER_OVERLAY);
		mFillColor = a.getColor(R.styleable.CircleImageView_civ_fill_color, DEFAULT_FILL_COLOR);
		
		a.recycle();
		
		init();
	}
	
	private void init() {
		super.setScaleType(SCALE_TYPE);
		mReady = true;
		
		if (mSetupPending) {
			setup();
			mSetupPending = false;
		}
	}
	
	@Override
	public ScaleType getScaleType() {
		return SCALE_TYPE;
	}
	
	/**
	 *  如果设置的scaleType不是ScaleType.CENTER_CROP，抛出异常，只支持ScaleType.CENTER_CROP；
	 */
	@Override
	public void setScaleType(ScaleType scaleType) {
		if (scaleType != SCALE_TYPE) {
			throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
		}
	}
	
	/**
	 * adjustViewBounds属性为是否保持宽高比。需要与maxWidth、MaxHeight一起使用，否则单独使用没有效果。
	 * 当前控件不支持设置保持宽高比；
	 * @param adjustViewBounds
	 */
	@Override
	public void setAdjustViewBounds(boolean adjustViewBounds) {
		if (adjustViewBounds) {
			throw new IllegalArgumentException("adjustViewBounds not supported.");
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 是否允许转换成圆形设置
		if (mDisableCircularTransformation) {
			super.onDraw(canvas);
			return;
		}
		
		if (mBitmap == null) {
			return;
		}
		
		//如果设置了图片底色，绘制图片底色。
		if (mFillColor != Color.TRANSPARENT) {
			canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mFillPaint);
		}
		//画内部图片区域（我们给mBitmapPaint设置了Shader，给Shader设置了LocalMatrix，通过ShaderMatrix设置了缩放比，及平移操作完成功能）；
		canvas.drawCircle(mDrawableRect.centerX(), mDrawableRect.centerY(), mDrawableRadius, mBitmapPaint);
		//如果设置了BorderWidth宽度，绘制；
		if (mBorderWidth > 0) {
			canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, mBorderPaint);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setup();
	}
	
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom);
		setup();
	}
	
	@Override
	public void setPaddingRelative(int start, int top, int end, int bottom) {
		super.setPaddingRelative(start, top, end, bottom);
		setup();
	}
	
	public int getBorderColor() {
		return mBorderColor;
	}
	
	public void setBorderColor(@ColorInt int borderColor) {
		if (borderColor == mBorderColor) {
			return;
		}
		
		mBorderColor = borderColor;
		mBorderPaint.setColor(mBorderColor);
		invalidate();
	}
	
	/**
	 * @deprecated Use {@link #setBorderColor(int)} instead
	 */
	@Deprecated
	public void setBorderColorResource(@ColorRes int borderColorRes) {
		setBorderColor(getContext().getResources().getColor(borderColorRes));
	}
	
	/**
	 * Return the color drawn behind the circle-shaped drawable.
	 *
	 * @return The color drawn behind the drawable
	 * @deprecated Fill color support is going to be removed in the future
	 */
	@Deprecated
	public int getFillColor() {
		return mFillColor;
	}
	
	/**
	 * Set a color to be drawn behind the circle-shaped drawable. Note that
	 * this has no effect if the drawable is opaque or no drawable is set.
	 *
	 * @param fillColor The color to be drawn behind the drawable
	 * @deprecated Fill color support is going to be removed in the future
	 */
	@Deprecated
	public void setFillColor(@ColorInt int fillColor) {
		if (fillColor == mFillColor) {
			return;
		}
		System.out.println("Log_setFillColor()");
		mFillColor = fillColor;
		mFillPaint.setColor(fillColor);
		invalidate();
	}
	
	/**
	 * Set a color to be drawn behind the circle-shaped drawable. Note that
	 * this has no effect if the drawable is opaque or no drawable is set.
	 *
	 * @param fillColorRes The color resource to be resolved to a color and
	 *                     drawn behind the drawable
	 * @deprecated Fill color support is going to be removed in the future
	 */
	@Deprecated
	public void setFillColorResource(@ColorRes int fillColorRes) {
		setFillColor(getContext().getResources().getColor(fillColorRes));
	}
	
	public int getBorderWidth() {
		return mBorderWidth;
	}
	
	public void setBorderWidth(int borderWidth) {
		if (borderWidth == mBorderWidth) {
			return;
		}
		
		mBorderWidth = borderWidth;
		setup();
	}
	
	public boolean isBorderOverlay() {
		return mBorderOverlay;
	}
	
	public void setBorderOverlay(boolean borderOverlay) {
		if (borderOverlay == mBorderOverlay) {
			return;
		}
		
		mBorderOverlay = borderOverlay;
		setup();
	}
	
	public boolean isDisableCircularTransformation() {
		return mDisableCircularTransformation;
	}
	
	public void setDisableCircularTransformation(boolean disableCircularTransformation) {
		if (mDisableCircularTransformation == disableCircularTransformation) {
			return;
		}
		
		mDisableCircularTransformation = disableCircularTransformation;
		initializeBitmap();
	}
	
	/**
	 * 以下四个函数都是
	 * 复写ImageView的setImageXxx()方法
	 * PS：如果我们在XML中设置了android:src属性，下边这个（setImageBitmap）函数先于构造函数调用之前调用。
	 *
	 * @param bm
	 */
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		System.out.println("Log_setImageBitmap()");
		initializeBitmap();
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		System.out.println("Log_setImageDrawable()");
		initializeBitmap();
	}
	
	@Override
	public void setImageResource(@DrawableRes int resId) {
		super.setImageResource(resId);
		System.out.println("Log_setImageResource()");
		initializeBitmap();
	}
	
	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		initializeBitmap();
	}
	
	/**
	 * 设置颜色过滤器；
	 * ColorMatrixColorFilter：颜色矩阵过滤器；
	 * LightingColorFilter：“光照色彩过滤器”，模拟一个光照照过图像所产生的效果；
	 * PorterDuffColorFilter：PorterDuff混合模式的色彩过滤器。
	 *
	 * @param cf
	 */
	@Override
	public void setColorFilter(ColorFilter cf) {
		if (cf == mColorFilter) {
			return;
		}
		
		mColorFilter = cf;
		applyColorFilter();
		invalidate();
	}
	
	/**
	 * 获取颜色过滤器，如果没有设置，返回null；
	 *
	 * @return
	 */
	@Override
	public ColorFilter getColorFilter() {
		return mColorFilter;
	}
	
	/**
	 * 应用颜色过滤器，没有的话mColorFilter=null;
	 */
	private void applyColorFilter() {
		if (mBitmapPaint != null) {
			mBitmapPaint.setColorFilter(mColorFilter);
		}
	}
	
	/**
	 * 从设置的drawable中转换为Bitmap返回；
	 *
	 * @param drawable
	 * @return
	 */
	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		
		try {
			Bitmap bitmap;
			
			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}
			
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 初始化Bitmap,内部判断是否允许转换成圆形设置（mDisableCircularTransformation）。
	 */
	private void initializeBitmap() {
		if (mDisableCircularTransformation) {
			mBitmap = null;
		} else {
			mBitmap = getBitmapFromDrawable(getDrawable());
		}
		setup();
	}
	
	/**
	 * 整个控件的关键实现部分，初始化BitmapShader,设置各paint，计算各边框等操作；
	 */
	private void setup() {
		if (!mReady) {
			mSetupPending = true;
			return;
		}
		
		if (getWidth() == 0 && getHeight() == 0) {
			return;
		}
		
		if (mBitmap == null) {
			invalidate();
			return;
		}
		//        TileMode：（一共有三种）
		//        CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
		//        REPEAT ：横向和纵向的重复渲染器图片，平铺。
		//        MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT重复方式不一样，他是以镜像方式平铺。
		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		//抗锯齿
		mBitmapPaint.setAntiAlias(true);
		
		mBitmapPaint.setShader(mBitmapShader);
		
		//Paint.Style.FILL：填充内部
		//Paint.Style.FILL_AND_STROKE  ：填充内部和描边
		//Paint.Style.STROKE  ：描边
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);
		
		mFillPaint.setStyle(Paint.Style.FILL);
		mFillPaint.setAntiAlias(true);
		mFillPaint.setColor(mFillColor);
		//取的原图片的宽高
		mBitmapHeight = mBitmap.getHeight();
		mBitmapWidth = mBitmap.getWidth();
		mBorderRect.set(calculateBounds());
		//计算整个圆形带Border部分的最小半径，取mBorderRect的宽高减去一个边缘大小的一半的较小值
		mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2.0f, (mBorderRect.width() - mBorderWidth) / 2.0f);
		//初始图片显示区域为mBorderRect（CircleImageView中图片区域的实际大小）
		mDrawableRect.set(mBorderRect);
		if (!mBorderOverlay && mBorderWidth > 0) {
			//到现在图片区域Rect（mDrawableRect）与整个View所用Rect（mBorderRadius）相同【mDrawableRect.set(mBorderRect)设置】，
			//如果在xml中设置app:civ_border_overlay="false"（边框不覆盖图片）并且外框宽度大于0，将图片显示区域Rect向内（缩小）mBorderWidth-1.0f。
			// inset（）方法参数为正数表示缩小，为复数表示扩大区域。
			mDrawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f);
		}
		//计算内圆最小半径，即去除边框后的Rect（内部图片Rect->mDrawableRect）宽度的半径
		mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);
		
		applyColorFilter();
		updateShaderMatrix();
		invalidate();
	}
	
	/**
	 * 计算整个CircleImageView绘制的RectF，设置边框显示区域；
	 *
	 * @return
	 */
	private RectF calculateBounds() {
		//获取当前CircleImageView视图除去PaddingLeft与PaddingRight后剩余的可用宽度
		// （如果你设置的PaddingLeft+PaddingRight>+当前控件的宽度，当前控件会显示不出来）；
		int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		//获取当前CircleImageView视图除去PaddingTop与PaddingBottom后剩余的可用高度；
		int availableHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		//获取除去Padding后宽高剩余可用空间较小的一个值。
		int sideLength = Math.min(availableWidth, availableHeight);
		//如果最后得到的availableWidth与availableHeight不一样（我们在代码中设置的原因），大的要向小的靠齐，
		// 最终得到的RectF为正方形。
		float left = getPaddingLeft() + (availableWidth - sideLength) / 2f;
		float top = getPaddingTop() + (availableHeight - sideLength) / 2f;
		return new RectF(left, top, left + sideLength, top + sideLength);
	}
	
	/**
	 * 设置BitmapShader的Matrix参数，设置最小缩放比例，平移参数。
	 * 作用：保证图片损失度最小和始终绘制图片正中央的那部分
	 */
	private void updateShaderMatrix() {
		float scale;
		float dx = 0;
		float dy = 0;
		
		mShaderMatrix.set(null);
		//比较图片和所绘区域宽缩放比、高缩放比，那个小。取小的，作为矩阵的缩放比。
		//等价于(mBitmapWidth / mDrawableRect.width()) > (mBitmapHeight / mDrawableRect.height())
		if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
			scale = mDrawableRect.height() / (float) mBitmapHeight;
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
			scale = mDrawableRect.width() / (float) mBitmapWidth;
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}
		//设置缩放比，对矩阵设置平移.
		mShaderMatrix.setScale(scale, scale);
		//平移操作，（dx + 0.5f）的处理，是四舍五入
		mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);
		
		mBitmapShader.setLocalMatrix(mShaderMatrix);
	}
	
}
