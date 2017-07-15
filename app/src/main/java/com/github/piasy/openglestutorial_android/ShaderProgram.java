package com.github.piasy.openglestutorial_android;

import android.content.Context;
import android.opengl.GLES20;
import android.support.annotation.RawRes;

/**
 * Created by Piasy{github.com/Piasy} on 15/07/2017.
 */

public abstract class ShaderProgram {
    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // Shader program
    protected final int mProgram;

    protected ShaderProgram(Context context, @RawRes int vertexShaderResourceId,
            @RawRes int fragmentShaderResourceId) {
        // Compile the shaders and link the program.
        mProgram = ShaderHelper.buildProgram(
                Utils.loadShader(context, vertexShaderResourceId),
                Utils.loadShader(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        // Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(mProgram);
    }
}
