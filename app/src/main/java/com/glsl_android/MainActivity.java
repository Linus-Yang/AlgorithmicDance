package com.glsl_android;

import android.graphics.Shader;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.glslutil.ShaderUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLSurfaceView = findViewById(R.id.gl_surface_view);
        mGLSurfaceView.setEGLContextClientVersion(3);
        mGLSurfaceView.setRenderer(new GLRenderer());
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }


    private class GLRenderer implements GLSurfaceView.Renderer {

       private String mVertexShader = "#version 300 es        \n"
               +        "  layout(location = 0) in vec4 a_position;  \n"
               +        "  void main()                           \n"
               +        " {                                          \n"
               +        "       gl_Position = a_position;            \n "
               +        " }                                          \n";

       private String mFragmentShader = "#version 300 es            \n "
               +        " precision mediump float;                  \n"
               +       "  layout(location = 0) out vec4 o_fragColor;  \n "
               +       " void main()                                  \n"
               +       " {                                            \n"
               +       "    o_fragColor  = vec4(1.0, 0.0, 1.0, 0.0);  \n"
               +       " } ";


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
          int  vertexShader = ShaderUtil.loadShader(GLES30.GL_VERTEX_SHADER, mVertexShader);
          int  fragmentShader = ShaderUtil.loadShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShader);
          int program =  ShaderUtil.assembleProgram(vertexShader, fragmentShader);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}
