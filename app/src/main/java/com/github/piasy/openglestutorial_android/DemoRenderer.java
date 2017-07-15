package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Piasy{github.com/Piasy} on 07/07/2017.
 */
class DemoRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];
    private final float[] mTmpMatrix = new float[16];

    private Table mTable;
    private Mallet mMallet;
    private TextureShaderProgram mTextureShaderProgram;
    private ColorShaderProgram mColorShaderProgram;
    private int mTexture;

    DemoRenderer(final Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mTable = new Table();
        mMallet = new Mallet();
        mTextureShaderProgram = new TextureShaderProgram(mContext);
        mColorShaderProgram = new ColorShaderProgram(mContext);
        mTexture = Utils.loadTexture(mContext, R.drawable.air_hockey_surface);
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
        // Clear the rendering surface.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Draw the table.
        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(mProjectionMatrix, mTexture);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();

        // Draw the mallets.
        mColorShaderProgram.useProgram();
        mColorShaderProgram.setUniforms(mProjectionMatrix);
        mMallet.bindData(mColorShaderProgram);
        mMallet.draw();
    }
}
