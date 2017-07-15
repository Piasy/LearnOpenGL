package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by Piasy{github.com/Piasy} on 15/07/2017.
 */

public class TextureShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int mUMatrixLocation;
    private final int mUTextureUnitLocation;

    // Attribute locations
    private final int mAPositionLocation;
    private final int mATextureCoordinatesLocation;

    public TextureShaderProgram(final Context context) {
        super(context, R.raw.texture_vertex, R.raw.texture_fragment);

        // Retrieve uniform locations for the shader program.
        mUMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        mUTextureUnitLocation = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT);
        // Retrieve attribute locations for the shader program.
        mAPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        mATextureCoordinatesLocation = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        // Pass the matrix into the shader program.
        GLES20.glUniformMatrix4fv(mUMatrixLocation, 1, false, matrix, 0);

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        GLES20.glUniform1i(mUTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return mAPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return mATextureCoordinatesLocation;
    }
}
