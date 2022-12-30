package mke;

import mke.types.Model;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
  public static void prepare() {
    glEnable(GL_DEPTH_TEST);
    glClearColor(0.f,1.f,0.f,1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  public static void render(Model model) {
    glBindVertexArray(model.getVaoID());
    glEnableVertexAttribArray(0);
    glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
    glDisableVertexAttribArray(0);
    glBindVertexArray(0);
  }
}
