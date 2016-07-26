package com.fenrirx.soundvis;


import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by fenrirx22 on 2016-07-24.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;
    private static final String TAG = "Triangle";
    private int mvpMatrixHandle;
    private float vertices[] = {
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f
    };

    private float color[] = {0.0f, 0.6f, 1.0f, 0.6f};

    private final String vertexShaderCode =
            "#version 300 es 			                     \n" +
                    "uniform mat4 uMVPMatrix;                \n" +
                    "in vec4 vPosition;                      \n" +
                    "void main() {                           \n" +
                    "  gl_Position = uMVPMatrix * vPosition; \n" +
                    "}                                       \n";

    private final String fragmentShaderCode =
            "#version 300 es		 			             \n"
                    + "precision mediump float;	             \n"
                    + "uniform vec4 vColor;                  \n"
                    + "out vec4 fragColor;	             	 \n"
                    + "void main()                           \n"
                    + "{                                     \n"
                    + "  fragColor = vColor;                 \n"
                    + "}                                     \n";

    private int shaderProgram;

    public static int loadShader(int type, String shaderCode) {
        int compiled[] = new int[1];
        int shader = GLES30.glCreateShader(type);
        if (shader == 0) {
            return 0;
        }
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, GLES30.glGetShaderInfoLog(shader));
            return 0;
        }
        return shader;
    }

    public Triangle() {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);
        int[] linked = new int[1];

        shaderProgram = GLES30.glCreateProgram();
        if (shaderProgram == 0) {
            return;
        }
        GLES30.glAttachShader(shaderProgram, vertexShader);
        GLES30.glAttachShader(shaderProgram, fragmentShader);
        GLES30.glLinkProgram(shaderProgram);

        GLES30.glGetProgramiv(shaderProgram, GLES30.GL_LINK_STATUS, linked, 0);

        if (linked[0] == 0) {
            Log.e(TAG, "Error linking program:");
            Log.e(TAG, GLES30.glGetProgramInfoLog(shaderProgram));
            GLES30.glDeleteProgram(shaderProgram);
            return;
        }
    }

    public void draw(float[] mvpMatrix) {
        GLES30.glUseProgram(shaderProgram);
        int positionAttribute = GLES30.glGetAttribLocation(shaderProgram, "vPosition");
        // get handle to shape's transformation matrix
        mvpMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES30.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES30.glEnableVertexAttribArray(positionAttribute);
        GLES30.glVertexAttribPointer(positionAttribute, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);

        int colorLocation = GLES30.glGetUniformLocation(shaderProgram, "vColor");
        GLES30.glUniform4fv(colorLocation, 1, color, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertices.length / 3);
        GLES30.glDisableVertexAttribArray(positionAttribute);
    }

}
