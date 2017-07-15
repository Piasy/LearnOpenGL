package com.github.piasy.openglestutorial_android;

/**
 * Created by Piasy{github.com/Piasy} on 15/07/2017.
 */

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VertexArray {
    private final FloatBuffer mFloatBuffer;

    public VertexArray(float[] vertexData) {
        mFloatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * Utils.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
            int componentCount, int stride) {
        mFloatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false,
                stride, mFloatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        mFloatBuffer.position(0);
    }
}
