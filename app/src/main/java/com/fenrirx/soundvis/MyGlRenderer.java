package com.fenrirx.soundvis;


import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bolec on 2016-07-24.
 */
public class MyGlRenderer implements GLSurfaceView.Renderer {
    Triangle triangle;

    @Override
    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
//        GLES20.glClearColor(0.8f, 0.0f, 1.0f, 1.0f);
        triangle = new Triangle();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        GLES20.glClearColor(0.8f, 0.0f, 0.0f, 1.0f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        triangle.draw();
    }


}
