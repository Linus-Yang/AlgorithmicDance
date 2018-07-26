package com.glsl_android;

import android.graphics.Shader;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
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
               +        "  layout(location = 1)uniform mat4 uMatrix;                 \n "
               +        "  layout(location = 0) in vec4 a_position;  \n"
               +        "  void main()                           \n"
               +        " {                                          \n"
               +        "    gl_Position = uMatrix * a_position; \n "
               +        " }                                          \n";

       private String mFragmentShader = "#version 300 es            \n "
               +        " precision mediump float;                  \n"
               +       "  layout(location = 0) out vec4 o_fragColor;  \n "
               +       " void main()                                  \n"
               +       " {                                            \n"
               +       "    o_fragColor  = vec4(1.0, 0.0f, 0.0f, 1.0f);                   \n"
               +       " } ";

       private int mProgram = -1;

       private int VBO[] = new int[1];
       private float[] vector = {-1.0f, -1.0f, 0.0f,
                                 1.0f, -1.0f, 0.0f,
                                 0.0f, 1.0f, 0.0f};



       private float scale = 0.0f;
       private FloatBuffer mVertexBuffer;
       private FloatBuffer mMatrix4Buffer;
       private int gWordLocation;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mVertexBuffer = ByteBuffer.allocateDirect(vector.length * 4).order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            mVertexBuffer.put(vector).position(0);


          int  vertexShader = ShaderUtil.loadShader(GLES30.GL_VERTEX_SHADER, mVertexShader);
          int  fragmentShader = ShaderUtil.loadShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShader);
          int program =  ShaderUtil.assembleProgram(vertexShader, fragmentShader);
          mProgram = program;
          GLES30.glGenBuffers(1,VBO, 0);
          GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO[0]);
          GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vector.length * 4, mVertexBuffer, GLES30.GL_STATIC_DRAW);
          GLES30.glUseProgram(mProgram);

            gWordLocation = GLES30.glGetUniformLocation(program, "uMatrix");

            float[] martix4 = new float[16];
            Matrix.setIdentityM(martix4, 0);
            Matrix.translateM(martix4, 0, 0.4f, 0.0f, 0.0f);
            printMatrix(martix4);

            mMatrix4Buffer = ByteBuffer.allocateDirect(martix4.length * 4).order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            mMatrix4Buffer.put(martix4).position(0);

            System.out.println(gWordLocation+"=======ss");


        }

        public void printMatrix(float matrxi[]) {
            System.out.println("1:"+ matrxi[0] + "   2 : " + matrxi[1]  +"  3  "+ matrxi[2] +"  4: "+ matrxi[3]);
            System.out.println("5:"+ matrxi[4] + "   6 : " + matrxi[5]  +"  7  "+ matrxi[6] +"  8: "+ matrxi[7]);
            System.out.println("9:"+ matrxi[8] + "   10 : " + matrxi[9]  +" 11  "+ matrxi[10] +"  12: "+ matrxi[11]);
            System.out.println("13:"+ matrxi[12] + "  14 : " + matrxi[13]  +"  15  "+ matrxi[14] +"  16: "+ matrxi[15]);


        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClearColor(0, 1, 0, 1);
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

            scale += 0.01;

            float[] martix4 = {1.0f, 0.0f, 0.0f, (float)Math.sin(scale),
                               0.0f, 1.0f, 0.0f,  0.0f,
                               0.0f, 0.0f , 1.0f ,0.0f,
                               0.0f, 0.0f , 0.0f , 1.0f};

//            float[] martix4 = new float[16];
//            Matrix.setIdentityM(martix4, 0);
//            Matrix.translateM(martix4, 0, (float)Math.sin(scale), 0.0f, 0.0f);

            GLES30.glUniformMatrix4fv(gWordLocation, 1, true, martix4, 0);
            GLES30.glEnableVertexAttribArray(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, VBO[0]);
            GLES30.glVertexAttribPointer ( 0, 3,
                    GLES30.GL_FLOAT, false, 3 * 4, 0 );
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
            GLES30.glDisableVertexAttribArray(0);
        }
    }
}
