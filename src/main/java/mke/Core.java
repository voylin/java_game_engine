package mke;

import mke.nodes.Camera;
import mke.nodes.Object;
import mke.shaders.StaticShader;
import mke.types.Version;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.Objects;

import static mke.ModelLoader.loadToVAO;
import static mke.utils.Logger.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glGetError;
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
      throw new IllegalStateException(printError("Unable to initialize GLFW!"));

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
      throw new RuntimeException(printError("Failed to create GLFW window!"));

    glfwMakeContextCurrent(window_id);
    glfwSwapInterval(1); // V-sync
    glfwShowWindow(window_id);
    GL.createCapabilities();
    static_shader = new StaticShader();

    // Callbacks
    printEngine("Setting callbacks ...");
    glfwSetFramebufferSizeCallback(window_id, Core::frameBufferSizeCallback);
    glfwSetWindowCloseCallback(window_id, Core::windowCloseCallback);

    Camera.setCurrentCamera(new Camera());

    /* TEMPORARY START */
    float[] vertices = {
        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,0.5f,-0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,-0.5f,0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        0.5f,0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,0.5f,-0.5f,
        -0.5f,-0.5f,-0.5f,
        -0.5f,-0.5f,0.5f,
        -0.5f,0.5f,0.5f,

        -0.5f,0.5f,0.5f,
        -0.5f,0.5f,-0.5f,
        0.5f,0.5f,-0.5f,
        0.5f,0.5f,0.5f,

        -0.5f,-0.5f,0.5f,
        -0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,-0.5f,
        0.5f,-0.5f,0.5f
    };
    int[] indices = { 0,1,3,
        3,1,2,
        4,5,7,
        7,5,6,
        8,9,11,
        11,9,10,
        12,13,15,
        15,13,14,
        16,17,19,
        19,17,18,
        20,21,23,
        23,21,22
    };
    float[] tex_coords = {
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0,
        0,0,
        0,1,
        1,1,
        1,0 };
    Object object = new Object(
        loadToVAO(vertices, indices, tex_coords, "wall.jpg"),
        new Vector3f(0,-0.5f,-0.22f),
        new Vector3f(0,0,0),
        1);
    /* TEMPORARY END */


    // Game loop
    printEngine("Starting loop!");
    while (!glfwWindowShouldClose(window_id)) {
      Renderer.prepare();

      // Game logic
      Camera.getCurrentCamera().move();
      object.increaseRotation(0,0.02f,0.02f);

      // Render
      static_shader.start();
      static_shader.loadViewMatrix();
      Renderer.render(object);
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
    Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    printEngine("Closing complete!");
  }


  private static void frameBufferSizeCallback(long window, int _width, int _height) {
    window_width = _width;
    window_height = _height;
    glViewport(0, 0, window_width, window_height);

    // Creating a new projection matrix
    float aspectRatio = (float) window_width / (float) window_height;
    float y_scale = (float)(1f / Math.tan(Math.toRadians(Renderer.FOV/2f))) * aspectRatio;
    float x_scale = y_scale / aspectRatio;
    float frustum_length = Renderer.FAR_PLANE - Renderer.NEAR_PLANE;

    Renderer.projectionMatrix = new Matrix4f();
    Renderer.projectionMatrix.m00(x_scale);
    Renderer.projectionMatrix.m11(y_scale);
    Renderer.projectionMatrix.m22(-((Renderer.FAR_PLANE + Renderer.NEAR_PLANE) / frustum_length));
    Renderer.projectionMatrix.m23(-1f);
    Renderer.projectionMatrix.m32(-((2 * Renderer.NEAR_PLANE * Renderer.FAR_PLANE) / frustum_length));
    Renderer.projectionMatrix.m33(0);

    static_shader.start();
    static_shader.loadProjectionMatrix(Renderer.projectionMatrix);
    static_shader.stop();
  }


  private static void windowCloseCallback(long window) {
    glfwSetWindowShouldClose(window, true);
  }
}
