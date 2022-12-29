package com.voylinslife.mke;

import com.voylinslife.mke.types.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.opengl.GL;

import static com.voylinslife.mke.utils.Logger.printEngine;
import static com.voylinslife.mke.utils.Logger.printError;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;


// Core is responsible for window creation

public class Core {
  static final String ENGINE_NAME = "Meikyuu Engine";
  static final Version ENGINE_VERSION = new Version(0,1,1);
  static final Version OPENGL_VERSION = new Version(4,6);
  static String GAME_NAME;
  static Version GAME_VERSION;

  // Window variables
  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  public static long window_id;


  public static void runEngine(String game_name, Version game_version) {
    GAME_NAME = game_name; GAME_VERSION = game_version;
    printEngine("Engine name: " + ENGINE_NAME);
    printEngine("Engine v.: " + ENGINE_VERSION);
    printEngine("LWJGL version: " + org.lwjgl.Version.getVersion());
    printEngine("OpenGL version: " + OPENGL_VERSION.toStringSmall());
    printEngine("Game name: " + GAME_NAME);
    printEngine("Game version: " + GAME_VERSION);

    GLFWErrorCallback.createPrint(System.err).set();
    if ( !glfwInit() )
      throw new IllegalStateException(printError("Unable to initialize GLFW!"));

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, OPENGL_VERSION.major);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, OPENGL_VERSION.minor);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    window_id = glfwCreateWindow(
        WINDOW_WIDTH,
        WINDOW_HEIGHT,
        GAME_NAME + " - " + GAME_VERSION.toString(),
        NULL, NULL);
    if (window_id == NULL)
      throw new RuntimeException(printError("Failed to create GLFW window!"));

    glfwMakeContextCurrent(window_id);
    glfwSwapInterval(1); // V-sync
    glfwShowWindow(window_id);
    GL.createCapabilities();

    // Callbacks
    // glfwSetFramebufferSizeCallback(window_id, frameBufferSizeCallback);

    // Game loop
    while (!glfwWindowShouldClose(window_id)) {
      // Game logic
      // Render
    }
  }


  //private static void frameBufferSizeCallback(long window, int _width, int _height) {
  //  this.width = _width;
  //  this.height = _height;
  //  glViewport(0, 0, width, height);

  //  // Creating a new projection matrix
  //  float aspectRatio = (float) width / (float) height;
  //  float y_scale = (float)(1f / Math.tan(Math.toRadians(Renderer.FOV/2f))) * aspectRatio;
  //  float x_scale = y_scale / aspectRatio;
  //  float frustum_length = Renderer.FAR_PLANE - Renderer.NEAR_PLANE;

  //  Renderer.projectionMatrix = new Matrix4f();
  //  Renderer.projectionMatrix.m00(x_scale);
  //  Renderer.projectionMatrix.m11(y_scale);
  //  Renderer.projectionMatrix.m22(-((Renderer.FAR_PLANE + Renderer.NEAR_PLANE) / frustum_length));
  //  Renderer.projectionMatrix.m23(-1f);
  //  Renderer.projectionMatrix.m32(-((2 * Renderer.NEAR_PLANE * Renderer.FAR_PLANE) / frustum_length));
  //  Renderer.projectionMatrix.m33(0);
  //  staticShader.start();
  //  staticShader.loadProjectionMatrix(Renderer.projectionMatrix);
  //  staticShader.stop();
  //}
}
