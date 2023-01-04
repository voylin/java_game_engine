package mke.nodes;

import org.joml.Vector3f;

import static mke.Core.window_id;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {
  private static Camera current_camera = new Camera();
  private Vector3f position = new Vector3f(0);
  private float pitch, yaw, roll;

  public Camera() {}
  public Camera(Vector3f position, float pitch, float yaw, float roll) {
    this.position = position;
    this.pitch = pitch;
    this.yaw = yaw;
    this.roll = roll;
  }

  public Camera(Vector3f position, float pitch, float yaw, float roll, boolean set_current) {
    this(position, pitch, yaw, roll);
    if (set_current)
      current_camera = this;
  }

  public void move() {
    float camera_move_speed = 0.1f;
    if (glfwGetKey(window_id, GLFW_KEY_W) == GLFW_PRESS) position.z -= camera_move_speed;
    if (glfwGetKey(window_id, GLFW_KEY_S) == GLFW_PRESS) position.z += camera_move_speed;
    if (glfwGetKey(window_id, GLFW_KEY_A) == GLFW_PRESS) position.x -= camera_move_speed;
    if (glfwGetKey(window_id, GLFW_KEY_D) == GLFW_PRESS) position.x += camera_move_speed;
    if (glfwGetKey(window_id, GLFW_KEY_Q) == GLFW_PRESS) position.y += camera_move_speed;
    if (glfwGetKey(window_id, GLFW_KEY_E) == GLFW_PRESS) position.y -= camera_move_speed;
  }


  public static Camera getCurrentCamera() { return current_camera; }
  public static void setCurrentCamera(Camera new_camera) { current_camera = new_camera; }


  public float getPitch() { return pitch; }
  public float getYaw() { return yaw; }
  public float getRoll() { return roll; }
  public Vector3f getPosition() { return position; }
}
