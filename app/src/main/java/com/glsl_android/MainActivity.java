package com.glsl_android;

import android.graphics.Shader;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.glslutil.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

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
               +       "    o_fragColor  = vec4(1.0, 0.0, 0.0, 1.0);  \n"
               +       " } ";

       private int mProgram = -1;

       private int VBO[] = new int[1];
       private float[] vector = {-1.0f, -1.0f, 0.0f,
                                 1.0f, -1.0f, 0.0f,
                                 0.0f, 1.0f, 0.0f};

       private FloatBuffer mVertexBuffer;


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mVertexBuffer = ByteBuffer.allocateDirect(vector.length * 4).order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            mVertexBuffer.put(vector).position(0);
          int  vertexShader = ShaderUtil.loadShader(GLES30.GL_VERTEX_SHADER, mVertexShader);
          int  fragmentShader = ShaderUtil.loadShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShader);
          int program =  ShaderUtil.assembleProgram(vertexShader, fragmentShader);
          mProgram = program;
         int a = GLES30.glGetAttribLocation(mProgram, "a_position");
         GLES30.glGenBuffers(1,VBO, 0);
         GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO[0]);
         GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vector.length * 4, mVertexBuffer, GLES30.GL_STATIC_DRAW);
         GLES30.glUseProgram(mProgram);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClearColor(0, 1, 0, 1);
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO[0]);
            GLES30.glVertexAttribPointer ( 0, 3,
                    GLES30.GL_FLOAT, false, 3 * 4, 0 );
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
            GLES30.glDisableVertexAttribArray(0);
        }
    }
}
