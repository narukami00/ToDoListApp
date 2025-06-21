package com.example.gottado;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class SlideOutRightItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, view.getWidth());
        animator.setDuration(300); // Adjust duration as needed
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchRemoveFinished(holder);
                view.setTranslationX(0);
            }
        });
        animator.start();
        return true;
    }
}
