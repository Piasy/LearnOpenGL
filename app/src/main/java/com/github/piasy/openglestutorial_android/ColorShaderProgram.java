package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by Piasy{github.com/Piasy} on 15/07/2017.
 */

public class ColorShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int mUMatrixLocation;

    // Attribute locations
    private final int mAPositionLocation;
    private final int mAColorLocation;

    public ColorShaderProgram(final Context context) {
        super(context, R.raw.vertex, R.raw.fragment);

        // Retrieve uniform locations for the shader program.
        mUMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        // Retrieve attribute locations for the shader program.
        mAPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        mAColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(mUMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return mAPositionLocation;
    }

    public int getColorAttributeLocation() {
        return mAColorLocation;
    }
}
