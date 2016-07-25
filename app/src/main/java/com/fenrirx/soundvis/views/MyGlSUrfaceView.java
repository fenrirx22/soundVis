package com.fenrirx.soundvis.views;

import android.content.Context;
import android.opengl.GLSurfaceView;


import com.fenrirx.soundvis.MyGlRenderer;

/**
 * Created by Bolec on 2016-07-24.
 */
public class MyGlSUrfaceView extends GLSurfaceView {
    private MyGlRenderer glRenderer;
    public MyGlSUrfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        glRenderer = new MyGlRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(glRenderer);
    }

}
