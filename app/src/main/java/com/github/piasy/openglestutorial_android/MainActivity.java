package com.github.piasy.openglestutorial_android;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;
    private DemoRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Utils.supportGlEs20(this)) {
            Toast.makeText(this, "GLES 2.0 not supported!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.surface);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mRenderer = new DemoRenderer(this);
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.setRenderer(mRenderer);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }
}
