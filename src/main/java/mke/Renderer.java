package mke;

import mke.nodes.Object;
import mke.types.Model;
import org.joml.Matrix4f;

import static mke.Core.static_shader;
import static mke.utils.MathStuff.createTransformationMatrix;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
  public static final float FOV = 70, NEAR_PLANE = 0.1f, FAR_PLANE = 1000f;
  public static Matrix4f projectionMatrix;


  public static void prepare() {
    glEnable(GL_DEPTH_TEST);
    glClearColor(0.f,1.f,0.f,1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  public static void render(Object object) {
    Model model = object.getModel();

    glBindVertexArray(model.getVaoID());
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    static_shader.loadTransformationMatrix(
        createTransformationMatrix(object.getPosition(), object.getRotation(), object.getScale()));

    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, 1);
    glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);
    glBindTexture(GL_TEXTURE_2D, 0);

    // Uncomment line bellow to check if errors are present:
    //Logger.printError(String.valueOf(glGetError()));
  }
}
