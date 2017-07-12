package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Piasy{github.com/Piasy} on 07/07/2017.
 */
class DemoRenderer implements GLSurfaceView.Renderer {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";

    private final Context mContext;
    private final FloatBuffer mVertexData;

    private int mProgram;
    private int mColorLocation;
    private int mPositionLocation;

    DemoRenderer(final Context context) {
        mContext = context;

        float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f
        };

        mVertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mVertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSrc = Utils.loadShader(mContext, R.raw.vertex);
        String fragmentShaderSrc = Utils.loadShader(mContext, R.raw.fragment);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSrc);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSrc);

        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (!ShaderHelper.validateProgram(mProgram)) {
            return;
        }

        GLES20.glUseProgram(mProgram);
        mColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);
        mPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);

        mVertexData.position(0);
        GLES20.glVertexAttribPointer(mPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
                false, 0, mVertexData);
        GLES20.glEnableVertexAttribArray(mPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // rectangle
        GLES20.glUniform4f(mColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

        // divide line
        GLES20.glUniform4f(mColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        // points
        GLES20.glUniform4f(mColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        GLES20.glUniform4f(mColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}
