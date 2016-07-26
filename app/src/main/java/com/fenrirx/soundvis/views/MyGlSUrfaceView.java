package com.fenrirx.soundvis.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


import com.fenrirx.soundvis.MyGlRenderer;

/**
 * Created by fenrirx22 on 2016-07-24.
 */
public class MyGlSUrfaceView extends GLSurfaceView {
    private MyGlRenderer glRenderer;
    private final float TOUCH_SCALE_FACTOR = 90.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGlSUrfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);

        glRenderer = new MyGlRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(glRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        setRenderer(new Particle(getContext()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                glRenderer.setAngle(
                        glRenderer.getAngle() -
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
