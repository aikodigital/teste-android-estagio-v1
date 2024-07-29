package me.patrick.aikodigital.pontocerto.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import me.patrick.aikodigital.pontocerto.R;

public class DrawableUtils {

    public static BitmapDescriptor imageToBitmapDescriptor(Context context, int drawableResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable resource not found");
        }
        int width = 100, height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);

        return icon;
    }

    public static void showLayout(View layout) {
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
            layout.setTranslationY(layout.getHeight());
            ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", layout.getHeight(), 0);
            animator.setDuration(300);
            animator.start();
        }
    }

    public static void hideLayout(View layout, Button button) {
        if (layout.getVisibility() == View.VISIBLE) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(layout, "translationY", 0, layout.getHeight());
            animator.setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layout.setVisibility(View.GONE);
                    button.setText(R.string.show_lines);
                }
            });
            animator.start();
        }
    }

}
