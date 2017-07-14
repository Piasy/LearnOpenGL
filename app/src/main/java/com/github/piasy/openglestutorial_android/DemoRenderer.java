package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Piasy{github.com/Piasy} on 07/07/2017.
 */
class DemoRenderer implements GLSurfaceView.Renderer {

    private static final int BYTES_PER_FLOAT = 4;

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)
                                      * BYTES_PER_FLOAT;

    private static final String U_MATRIX = "u_Matrix";
    private static final String A_COLOR = "a_Color";
    private static final String A_POSITION = "a_Position";

    private final Context mContext;
    private final FloatBuffer mVertexData;

    private final float[] mModelMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mTmpMatrix = new float[16];

    private int mProgram;
    private int mColorLocation;
    private int mPositionLocation;
    private int mMatrixLocation;

    DemoRenderer(final Context context) {
        mContext = context;

        float[] tableVerticesWithTriangles = {
                // Order of coordinates: X, Y, R, G, B

                // Triangle Fan
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

                // Line 1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,

                // Mallets
                0f, -0.4f, 0f, 0f, 1f,
                0f, 0.4f, 1f, 0f, 0f
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
        mColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR);
        mPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        mMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);

        mVertexData.position(0);
        GLES20.glVertexAttribPointer(mPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT,
                false, STRIDE, mVertexData);
        GLES20.glEnableVertexAttribArray(mPositionLocation);

        mVertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(mColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT,
                false, STRIDE, mVertexData);
        GLES20.glEnableVertexAttribArray(mColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        Matrix.perspectiveM(mProjectionMatrix, 0, 45, (float) width / height, 1f, 10f);

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 0f, 0f, -2.5f);
        Matrix.rotateM(mModelMatrix, 0, -60f, 1f, 0f, 0f);
        Matrix.multiplyMM(mTmpMatrix, 0, mProjectionMatrix, 0, mModelMatrix, 0);
        System.arraycopy(mTmpMatrix, 0, mProjectionMatrix, 0, mTmpMatrix.length);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glUniformMatrix4fv(mMatrixLocation, 1, false, mProjectionMatrix, 0);

        // rectangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        // divide line
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        // points
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
    }
}
