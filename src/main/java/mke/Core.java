package mke;

import mke.shaders.StaticShader;
import mke.types.Model;
import mke.types.Version;
import mke.utils.Logger;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.net.URL;

import static mke.utils.Logger.printDetails;
import static mke.utils.Logger.printEngine;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;


// Core is responsible for window creation

public class Core {
  public static final String ENGINE_NAME = "Meikyuu Engine";
  public static final Version ENGINE_VERSION = new Version(0,1,1);
  public static final Version OPENGL_VERSION = new Version(4,6);
  public static String GAME_NAME;
  public static Version GAME_VERSION;

  // Window variables
  private static int window_width = 1920, window_height = 1080;
  public static long window_id;

  // Shaders
  public static StaticShader static_shader;


  public static void runEngine(String game_name, Version game_version) {
    GAME_NAME = game_name; GAME_VERSION = game_version;
    printDetails();


    printEngine("Creating window ...");
    GLFWErrorCallback.createPrint(System.err).set();
    if ( !glfwInit() )
      throw new IllegalStateException(Logger.printError("Unable to initialize GLFW!"));

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, OPENGL_VERSION.major);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, OPENGL_VERSION.minor);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    window_id = glfwCreateWindow(
        window_width, window_height,
        GAME_NAME + " - " + GAME_VERSION.toString(),
        NULL, NULL);
    if (window_id == NULL)
      throw new RuntimeException(Logger.printError("Failed to create GLFW window!"));

    glfwMakeContextCurrent(window_id);
    glfwSwapInterval(1); // V-sync
    glfwShowWindow(window_id);
    GL.createCapabilities();
    static_shader = new StaticShader();

    // Callbacks
    printEngine("Setting callbacks ...");
    glfwSetFramebufferSizeCallback(window_id, Core::frameBufferSizeCallback);
    glfwSetWindowCloseCallback(window_id, Core::windowCloseCallback);


    /* TEMPORARY START */
    float[] vertices = {
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f
    };
    int[] indices = {
        0,1,3,
        3,1,2
    };
    Model model = ModelLoader.loadToVAO(vertices, indices);
    /* TEMPORARY END */


    // Game loop
    printEngine("Starting loop!");
    while (!glfwWindowShouldClose(window_id)) {
      Renderer.prepare();

      // Game logic

      // Render
      static_shader.start();
      Renderer.render(model);
      static_shader.stop();

      // Update display
      glfwSwapBuffers(window_id);
      glfwPollEvents();
    }

    // Shutdown
    printEngine("Closing Engine ...");
    static_shader.cleanUp();
    ModelLoader.cleanUp();

    glfwFreeCallbacks(window_id);
    glfwDestroyWindow(window_id);
    glfwTerminate();
    glfwSetErrorCallback(null).free();
    printEngine("Closing complete!");
  }


  private static void frameBufferSizeCallback(long window, int _width, int _height) {
    window_width = _width;
    window_height = _height;
    glViewport(0, 0, window_width, window_height);

    // Creating a new projection matrix
    // float aspectRatio = (float) window_width / (float) height;
    // float y_scale = (float)(1f / Math.tan(Math.toRadians(Renderer.FOV/2f))) * aspectRatio;
    // float x_scale = y_scale / aspectRatio;
    // float frustum_length = Renderer.FAR_PLANE - Renderer.NEAR_PLANE;

    // Renderer.projectionMatrix = new Matrix4f();
    // Renderer.projectionMatrix.m00(x_scale);
    // Renderer.projectionMatrix.m11(y_scale);
    // Renderer.projectionMatrix.m22(-((Renderer.FAR_PLANE + Renderer.NEAR_PLANE) / frustum_length));
    // Renderer.projectionMatrix.m23(-1f);
    // Renderer.projectionMatrix.m32(-((2 * Renderer.NEAR_PLANE * Renderer.FAR_PLANE) / frustum_length));
    // Renderer.projectionMatrix.m33(0);
    // staticShader.start();
    // staticShader.loadProjectionMatrix(Renderer.projectionMatrix);
    // staticShader.stop();
  }


  private static void windowCloseCallback(long window) {
    glfwSetWindowShouldClose(window, true);
  }
}
