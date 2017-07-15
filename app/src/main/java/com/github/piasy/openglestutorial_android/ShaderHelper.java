package com.github.piasy.openglestutorial_android;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by Piasy{github.com/Piasy} on 07/07/2017.
 */

public final class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    private ShaderHelper() {
        // util
    }

    public static int compileVertexShader(String shader) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shader);
    }

    public static int compileFragmentShader(String shader) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shader);
    }

    public static int linkProgram(int vertexShader, int fragmentShader) {
        int program = GLES20.glCreateProgram();

        if (program == 0) {
            Log.e(TAG, "create program fail, " + GLES20.glGetProgramInfoLog(program));

            return 0;
        }

        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);

        if (linkStatus[0] == GLES20.GL_FALSE) {
            Log.e(TAG, "link program fail, " + GLES20.glGetProgramInfoLog(program));

            return 0;
        }

        return program;
    }

    public static boolean validateProgram(int program) {
        GLES20.glValidateProgram(program);

        int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(program, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);

        Log.i(TAG, "validateProgram: " + validateStatus[0] + ", "
                   + GLES20.glGetProgramInfoLog(program));

        return validateStatus[0] == GLES20.GL_TRUE;
    }

    private static int compileShader(int type, String shader) {
        int shaderObj = GLES20.glCreateShader(type);

        if (shaderObj == 0) {
            Log.e(TAG, "create shader obj fail: " + type);
            return 0;
        }

        GLES20.glShaderSource(shaderObj, shader);
        GLES20.glCompileShader(shaderObj);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObj, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == GLES20.GL_FALSE) {
            Log.e(TAG, "compile shader code fail: " + type + ", "
                       + GLES20.glGetShaderInfoLog(shaderObj));

            GLES20.glDeleteShader(shaderObj);
            return 0;
        }

        return shaderObj;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;

        // Compile the shaders.
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        // Link them into a shader program.
        program = linkProgram(vertexShader, fragmentShader);

        validateProgram(program);

        return program;
    }
}
