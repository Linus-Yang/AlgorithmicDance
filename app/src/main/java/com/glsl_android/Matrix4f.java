package com.glsl_android;

import android.opengl.Matrix;

public class Matrix4f {

    private float[] mMatrix = new float[16];

    public void init() {
        mMatrix[0] = 1.0f; mMatrix[1] = 0.0f;  mMatrix[2] = 0.0f; mMatrix[3] = 0.0f;
        mMatrix[4] = 0.0f; mMatrix[5] = 1.0f;   mMatrix[6] = 0.0f; mMatrix[7] =0.0f;
        mMatrix[8] = 0.0f; mMatrix[9] = 0.0f ;  mMatrix[10] = 1.0f; mMatrix[11] = 0.0f;
        mMatrix[12] = 0.0f; mMatrix[13] = 0.0f; mMatrix[14] = 0.0f; mMatrix[15] = 1.0f;
    }

    public float[] getMatrix () {
        return mMatrix;
    }


    public void transform(float x, float y, float z) {
        mMatrix[12] = x;  mMatrix[13] = y; mMatrix[14] = z;
     }

     public void rotate(float angle, float x ,float y , float z){
        float[] rotate = new float[16];
        if(z == 1) {
            rotate[0] = (float) Math.cos(angle); rotate[1] = -(float) Math.sin(angle); rotate[2] = 0.0f;  rotate[3] = 0.0f;
            rotate[4] = (float) Math.sin(angle); rotate[5] = (float)Math.cos(angle); rotate[6]  = 0.0f;  rotate[7] = 0.0f;
            rotate[8] = 0.0f;  rotate[9] = 0.0f; rotate[10] = 1.0f;  rotate[11] = 0.0f;
            rotate[12] = 0.0f;  rotate[13] = 0.0f; rotate[14] = 0.0f;  rotate[15] =  1.0f;
            Matrix.multiplyMM(mMatrix, 0, mMatrix, 0, rotate, 0);
        }

     }
}
