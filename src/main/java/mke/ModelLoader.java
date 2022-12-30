package mke;

import mke.types.Model;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class ModelLoader {
  private static List<Integer> vaos = new ArrayList<Integer>();
  private static List<Integer> vbos = new ArrayList<Integer>();


  public static Model loadToVAO(float[] pos, int[] indices) {
    // Create VAO:
    int vaoID = glGenVertexArrays();
    vaos.add(vaoID);
    glBindVertexArray(vaoID);

    // Bind indices
    int vboID = glGenBuffers();
    vbos.add(vboID);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

    storeDataInAttributeList(0, pos);
    unbindVAO();
    
    return new Model(vaoID, indices.length);
  }

  private static void storeDataInAttributeList(int attribNumber, float[] data) {
    int vboID = glGenBuffers();
    vbos.add(vboID);
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    glVertexAttribPointer(attribNumber, 3, GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  private static void unbindVAO() { glBindVertexArray(0); }

  public static void cleanUp() {
    unbindVAO();
    for(int vao:vaos) glDeleteVertexArrays(vao);
    for(int vbo:vbos) glDeleteBuffers(vbo);
    //for(int texture:textures) glDeleteTextures(texture);
  }
}
