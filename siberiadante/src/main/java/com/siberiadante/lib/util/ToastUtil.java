package com.siberiadante.lib.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.siberiadante.lib.R;
import com.siberiadante.lib.SiberiaDanteLib;
import com.siberiadante.lib.exception.SiberiaDanteLibException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiberiaDante on 2017/5/4.
 *
 * @Created SiberiaDante
 * @Describe：
 * @Time: 2017/5/14
 * @UpDate: 2017/09/20——
 * @Email: 994537867@qq.com
 * @GitHub: https://github.com/SiberiaDante
 * 使用单例ToastUtil时注意一点：比如使用了带位置的方法后，下次再使用不带位置的单例方法时，会显示成上次方法的Toast的位置
 * 所以强烈建议：全局统一的Toast使用该类中的单例方法，一旦使用了一种以上的方法，需要在不常使用的方法调用后调用resetToast()方法，重置Toast位置等（不适重置Toast对象）
 * 举例：
 * 全局一般使用的Toast是底部弹出一行简单的文字，调用：
 * ToastUtils.toast("常规的Toast方法)；
 * 然后我们有特殊需要，要居中显示一个Toast提示用户，调用：
 * ToastUtil.showSingletonText("居中显示",Toast.LENGTH_SHORT,Gravity.CENTER);
 * 这个方法调用完，其实相当于是更改了Toast的对象。不再是第一个我们常规使用的方法中所创建的，所以，
 * 我们需要重置Toast对象，其实就是创建一个新的常规对象
 */

public class ToastUtil {
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");

    @ColorInt
    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    @ColorInt
    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    @ColorInt
    private static final int WARNING_COLOR = Color.parseColor("#FFA900");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";
    private static final int mPositionNull = -1;//不设置位置，使用默认位置
    private static final int mDurationNull = -1;//不设置显示时间，使用默认时间
    private static final int TEXT_SIZE_NULL = -1;//不设置显示时间，使用默认时间
    private static final int TEXT_COLOR_NULL = -1;//不设置显示时间，使用默认时间
    @ColorInt
    private static int DEFAULT_TEXT_COLOR_BLACK = R.color.black;
    @Size
    private static float DEFAULT_TEXT_SIZE = 16f;
    private static Toast currentToast;
    private static Toast toast;
    private static Toast mCenterToast;
    private static List<Toast> toastList = new ArrayList<>();

    private static boolean isNeedReset = false;//加入自动判断，是否需要重置Toast对象的各种设置——后期开发

    public ToastUtil() {
        new SiberiaDanteLibException(ToastUtil.class.getName());
    }

    /**
     * @param content
     */
    public static void toast(String content) {
        showSingleToast(content, mDurationNull).show();
    }

    /**
     * @param content
     * @param duration
     */
    public static void toast(String content, int duration) {
        showSingleToast(content, duration).show();
    }

    @SuppressLint("ShowToast")
    @CheckResult
    private static Toast showSingleToast(String content, int duration) {
        if (null == toast) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        toastList.add(toast);
        toast.setText(content);
        if (mDurationNull == duration) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(duration);
        }

        return toast;
    }

    /**
     * Toast 无背景透明的文本
     *
     * @param content
     */
    public static void toastTranslucent(String content) {
        showTranslucentText(content, mDurationNull, DEFAULT_TEXT_SIZE, DEFAULT_TEXT_COLOR).show();
    }


    /**
     * Toast 无背景透明的文本
     *
     * @param content
     * @param duration
     */
    public static void toastTranslucent(String content, int duration) {
        showTranslucentText(content, duration, DEFAULT_TEXT_SIZE, DEFAULT_TEXT_COLOR).show();
    }

    /**
     * @param content
     * @param duration
     * @param textSize
     * @param textColor
     */
    public static void toastTranslucent(String content, int duration, float textSize, int textColor) {
        showTranslucentText(content, duration, textSize, textColor).show();
    }

    /**
     * Toast 无背景透明的文本
     *
     * @param content  内容
     * @param duration 时长
     */
    @CheckResult
    private static Toast showTranslucentText(String content, int duration, float textSize, int textColor) {
        if (toast == null) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(SiberiaDanteLib.getContext());//创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        TextView textView = new TextView(SiberiaDanteLib.getContext());
        if (textSize == TEXT_SIZE_NULL) {
            textView.setTextSize(DEFAULT_TEXT_SIZE);
        } else {
            textView.setTextSize(textSize);
        }
        if (textColor == TEXT_COLOR_NULL) {
            textView.setTextColor(DEFAULT_TEXT_COLOR_BLACK);
        } else {
            textView.setTextColor(textColor);
        }
        textView.setText(content);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        if (mDurationNull == duration) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(duration);
        }
        toast.show();
        return toast;
    }

    /**
     * Toast 多行文本
     *
     * @param textSize  字体大小
     * @param textColor 字体颜色
     * @param contents  list 形式的文本内容
     */
    @CheckResult
    private static Toast showLinesText(List<String> contents, int textSize, int textColor) {
        if (null == toast) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(SiberiaDanteLib.getContext());//创建线性布局
        linearLayoutTop.setBackgroundColor(ContextCompat.getColor(SiberiaDanteLib.getContext(), R.color.gray));
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(SiberiaDanteLib.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(textSize);
            textView.setTextColor(textColor);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        resetToast();
        return toast;
    }


//
//    /**
//     * 吐司文本内容
//     * 非单例模式,吐司时间短
//     *
//     * @param content 吐司内容
//     */
//    public static void showTextShort(String content) {
//        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
//        toastList.add(toast);
//        toast.setText(content);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    /**
//     * 吐司文本内容
//     * 非单例模式,吐司时间短
//     *
//     * @param content 吐司内容
//     */
//    public static void showTextLong(String content) {
//        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
//        toastList.add(toast);
//        toast.setText(content);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.show();
//    }
//
//    /**
//     * 单例模式，吐司时间短, 吐司文本内容
//     *
//     * @param content 吐司内容
//     */
//    public static void showSingletonShort(String content) {
//        if (toast == null) {
//            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_SHORT);
//        }
//        toast.setText(content);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    /**
//     * 吐司文本内容
//     * 单例模式,吐司时间长
//     *
//     * @param content 吐司内容
//     */
//    public static void showSingletonLong(String content) {
//        if (toast == null) {
//            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
//        }
//        toast.setText(content);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    /**
//     * 吐司文本内容 自定义位置、时间
//     * 单例模式
//     *
//     * @param content  吐司内容
//     * @param duration 显示的时间
//     * @param position 显示的位置
//     */
//    public static void showSingletonText(String content, int duration, int position) {
//        if (toast == null) {
//            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
//        }
//        toast.setText(content);
//        toast.setDuration(duration);
//        toast.setGravity(position, 0, 0);
//        toast.show();
//    }


    /**
     * Toast图片
     * 单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showSingletonImageCenter(int resId, int duration) {
        if (toast == null) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showImageCenter(int resId, int duration) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());//创建图片控件
        imageView.setImageResource(resId);//给控件设置图片
        toast.setView(imageView);//把图片绑定到Toast上
        toast.setDuration(duration);//Toast显示的时间;
        //设置图片显示的位置：三个参数
        //第一个：位置，可以用|添加并列位置，第二个：相对于X的偏移量，第三个：相对于Y轴的偏移量
        //注意一点：第二和第三个参数是相对于第一个参数设定的位置偏移的
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();//显示Toast
    }

    /**
     * Toast图片
     * 单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showSingletonImage(int resId, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());//创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(int resId, int duration, int position) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());//创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间,自定义位置
     *
     * @param bitmap   图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(Bitmap bitmap, int duration, int position) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());//创建图片控件
        imageView.setImageBitmap(bitmap);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示图文结合的Toast
     *
     * @param resId    图片id
     * @param content  文本内容
     * @param duration toast时长
     * @param position toast位置
     */
    public static void showIT(int resId, String content, int duration, int position) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(SiberiaDanteLib.getContext());//创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        ImageView imageView = new ImageView(SiberiaDanteLib.getContext());//创建图片控件
        imageView.setImageResource(resId);//给控件设置图片
        TextView textView = new TextView(SiberiaDanteLib.getContext());//创建文本控件
        textView.setText(content);//设置文本内容
        linearLayout.addView(imageView);//添加图片控件到布局中
        linearLayout.addView(textView);//添加文本控件到布局中。注意添加顺序会影响图片在前还是为本在前
        toast.setView(linearLayout);//把布局绑定到Toast上
        toast.setDuration(duration);//Toast显示的时间;
        /**
         * position：显示位置
         * 第二个参数：相对X的偏移量
         * 第三个参数：相对Y的偏移量
         * 第二和第三个参数是相对于第一个参数设定的位置偏移的
         */
        toast.setGravity(position, 0, 0);
        toast.show();//显示Toast
        resetToast();
    }

    /**
     * Toast 多行文本
     *
     * @param size     字体大小
     * @param contents list 形式的文本内容
     */
    public static void showLines(List<String> contents, int size) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(SiberiaDanteLib.getContext());//创建线性布局
        linearLayoutTop.setBackgroundColor(ContextCompat.getColor(SiberiaDanteLib.getContext(), R.color.gray));
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(SiberiaDanteLib.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(size);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        resetToast();
    }


    /**
     * Toast 自定义布局 非单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showLayout(View view, int duration, int position) {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();
        resetToast();

    }

    /**
     * Toast 自定义布局 单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showSingletonLayout(View view, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();

    }

    /**
     * 异步线程下载图片并Toast
     *
     * @param url
     */
//    public static void showThread(String url) {
//        final String mUrl = url;
//        Observable.create(new ObservableOnSubscribe<Bitmap>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Bitmap> e) throws Exception {
//                URL url = new URL(mUrl);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setConnectTimeout(8000);
//                connection.setReadTimeout(8000);
//                InputStream inputStream = connection.getInputStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                e.onNext(bitmap);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Bitmap>() {
//                    @Override
//                    public void accept(@NonNull Bitmap bitmap) throws Exception {
//                        ToastUtil.showImage(bitmap, Toast.LENGTH_LONG, Gravity.CENTER);
//                    }
//                });
//    }

    /**
     * 重置Toast对象
     */
    public static void resetToast() {
        toast = Toast.makeText(SiberiaDanteLib.getContext(), "", Toast.LENGTH_LONG);
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void cancelAll() {
        for (int i = 0; i < toastList.size(); i++) {
            if (toastList.get(i) != null) {
                toastList.get(i).cancel();
            }
        }
    }

    // TODO: 2017/9/20


    /**
     * @param message
     */
    public static void normal(@NonNull String message) {
        normal(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, null, false).show();
    }

    /**
     * @param context
     * @param message
     * @return
     */
    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    /**
     * @param message
     * @param icon
     */
    public static void normal(@NonNull String message, Drawable icon) {
        normal(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, icon, true).show();
    }

    /**
     * @param context
     * @param message
     * @param icon
     * @return
     */
    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    /**
     * @param message
     * @param duration
     */
    public static void normal(@NonNull String message, int duration) {
        normal(SiberiaDanteLib.getContext(), message, duration, null, false).show();
    }

    /**
     * @param context
     * @param message
     * @param duration
     * @return
     */
    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration) {
        return normal(context, message, duration, null, false);
    }

    /**
     * @param message
     * @param duration
     * @param icon
     */
    public static void normal(@NonNull String message, int duration, Drawable icon) {
        normal(SiberiaDanteLib.getContext(), message, duration, icon, true).show();
    }

    /**
     * @param context
     * @param message
     * @param duration
     * @param icon
     * @return
     */
    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    /**
     * @param message
     * @param duration
     * @param icon
     * @param withIcon
     * @return
     */
    public static Toast normal(@NonNull String message, int duration, Drawable icon, boolean withIcon) {
        return custom(SiberiaDanteLib.getContext(), message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    /**
     * @param context
     * @param message
     * @param duration
     * @param icon
     * @param withIcon
     * @return
     */
    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull String message, int duration, Drawable icon, boolean withIcon) {
        return custom(context, message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }


    public static void warning(@NonNull String message) {
        warning(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    public static void warning(@NonNull String message, int duration) {
        warning(SiberiaDanteLib.getContext(), message, duration, true).show();
    }

    public static Toast warning(@NonNull String message, int duration, boolean withIcon) {
        return custom(SiberiaDanteLib.getContext(), message, getDrawable(R.drawable.ic_error_outline_white_48dp), DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    public static void info(@NonNull String message) {
        info(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    public static void info(@NonNull String message, int duration) {
        info(SiberiaDanteLib.getContext(), message, duration, true).show();
    }

    public static Toast info(@NonNull String message, int duration, boolean withIcon) {
        return custom(SiberiaDanteLib.getContext(), message, getDrawable(R.drawable.ic_info_outline_white_48dp), DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    public static void success(@NonNull String message) {
        success(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, true).show();
    }

    public static void success(@NonNull String message, int duration) {
        success(SiberiaDanteLib.getContext(), message, duration, true).show();
    }

    public static Toast success(@NonNull String message, int duration, boolean withIcon) {
        return custom(SiberiaDanteLib.getContext(), message, getDrawable(R.drawable.ic_check_white_48dp), DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    public static void error(@NonNull String message) {
        error(SiberiaDanteLib.getContext(), message, Toast.LENGTH_SHORT, true).show();
    }
    //===========================================使用ApplicationContext 方法=========================

    //*******************************************常规方法********************************************

    public static void error(@NonNull String message, int duration) {
        error(SiberiaDanteLib.getContext(), message, duration, true).show();
    }

    public static Toast error(@NonNull String message, int duration, boolean withIcon) {
        return custom(SiberiaDanteLib.getContext(), message, getDrawable(R.drawable.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }


    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message, int duration) {
        return warning(context, message, duration, true);
    }

    @CheckResult
    public static Toast warning(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(R.drawable.ic_error_outline_white_48dp), DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message, int duration) {
        return info(context, message, duration, true);
    }

    @CheckResult
    public static Toast info(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(R.drawable.ic_info_outline_white_48dp), DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message, int duration) {
        return success(context, message, duration, true);
    }

    @CheckResult
    public static Toast success(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(R.drawable.ic_check_white_48dp), DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    //===========================================常规方法============================================

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration) {
        return error(context, message, duration, true);
    }

    @CheckResult
    public static Toast error(@NonNull Context context, @NonNull String message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(R.drawable.ic_clear_white_48dp), DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon,
                               @ColorInt int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }

    //*******************************************内需方法********************************************

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, @DrawableRes int iconRes,
                               @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        return custom(context, message, getDrawable(iconRes), textColor, tintColor, duration, withIcon, shouldTint);
    }

    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull String message, Drawable icon, @ColorInt int textColor, @ColorInt int tintColor, int duration, boolean withIcon, boolean shouldTint) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        } else {
            drawableFrame = getDrawable(R.drawable.toast_frame);
        }
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null)
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            setBackground(toastIcon, icon);
        } else
            toastIcon.setVisibility(View.GONE);

        toastTextView.setTextColor(textColor);
        toastTextView.setText(message);
        toastTextView.setTypeface(Typeface.create(TOAST_TYPEFACE, Typeface.NORMAL));

        currentToast.setView(toastLayout);
        currentToast.setDuration(duration);
        return currentToast;
    }

    public static final Drawable tint9PatchDrawableFrame(@NonNull Context context, @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }


    public static final void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static final Drawable getDrawable(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return SiberiaDanteLib.getContext().getDrawable(id);
        else
            return SiberiaDanteLib.getContext().getResources().getDrawable(id);
    }

//    /**
//     * 封装了Toast的方法 :需要等待
//     *
//     * @param context Context
//     * @param str     要显示的字符串
//     * @param isLong  Toast.LENGTH_LONG / Toast.LENGTH_SHORT
//     */
//    public static void showToast(Context context, String str, boolean isLong) {
//        if (isLong) {
//            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    /**
//     * 封装了Toast的方法 :需要等待
//     */
//    public static void showToastShort(String str) {
//        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * 封装了Toast的方法 :需要等待
//     */
//    public static void showToastShort(int resId) {
//        Toast.makeText(getContext(), getContext().getString(resId), Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * 封装了Toast的方法 :需要等待
//     */
//    public static void showToastLong(String str) {
//        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
//    }
//
//    /**
//     * 封装了Toast的方法 :需要等待
//     */
//    public static void showToastLong(int resId) {
//        Toast.makeText(getContext(), getContext().getString(resId), Toast.LENGTH_LONG).show();
//    }
//
//    /**
//     * Toast 替代方法 ：立即显示无需等待
//     *
//     * @param msg 显示内容
//     */
//    public static void showToast(String msg) {
//        if (mToast == null) {
//            mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
//        } else {
//            mToast.setText(msg);
//        }
//        mToast.show();
//    }
//
//    /**
//     * Toast 替代方法 ：立即显示无需等待
//     *
//     * @param resId String资源ID
//     */
//    public static void showToast(int resId) {
//        if (mToast == null) {
//            mToast = Toast.makeText(getContext(), getContext().getString(resId), Toast.LENGTH_LONG);
//        } else {
//            mToast.setText(getContext().getString(resId));
//        }
//        mToast.show();
//    }
//
//    /**
//     * Toast 替代方法 ：立即显示无需等待
//     *
//     * @param context  实体
//     * @param resId    String资源ID
//     * @param duration 显示时长
//     */
//    public static void showToast(Context context, int resId, int duration) {
//        showToast(context, context.getString(resId), duration);
//    }
//    //===========================================Toast 替代方法======================================
//
//    /**
//     * Toast 替代方法 ：立即显示无需等待
//     *
//     * @param context  实体
//     * @param msg      要显示的字符串
//     * @param duration 显示时长
//     */
//    public static void showToast(Context context, String msg, int duration) {
//        if (mToast == null) {
//            mToast = Toast.makeText(context, msg, duration);
//        } else {
//            mToast.setText(msg);
//        }
//        mToast.show();
//    }

//    public static boolean doubleClickExit() {
//        if ((System.currentTimeMillis() - mExitTime) > 2000) {
//            RxToast.normal("再按一次退出");
//            mExitTime = System.currentTimeMillis();
//            return false;
//        }
//        return true;
//    }
}
