package mke.utils;

import mke.nodes.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathStuff {
  public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
    Matrix4f matrix = new Matrix4f().identity();
    matrix.setTranslation(translation);
    matrix.setRotationXYZ(rotation.x, rotation.y, rotation.z);
    matrix.scale(scale);
    matrix.translate(translation, matrix);
    return matrix;
  }


  public static Matrix4f createViewMatrix() {
    Camera camera = Camera.getCurrentCamera();
    Matrix4f viewMatrix = new Matrix4f();

    viewMatrix.identity();
    viewMatrix.setRotationXYZ(
        (float) Math.toRadians(camera.getPitch()),
        (float) Math.toRadians(camera.getYaw()),
        (float) Math.toRadians(camera.getRoll()));
    Vector3f cameraPos = camera.getPosition();
    Vector3f negCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
    viewMatrix.setTranslation(negCameraPos);
    return viewMatrix;
  }
}
