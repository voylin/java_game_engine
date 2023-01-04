package mke;

import mke.types.Model;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mke.utils.Logger.printError;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.stb.STBImage.*;


public class ModelLoader {
  private static final List<Integer>
      vaos = new ArrayList<>(),
      vbos = new ArrayList<>(),
      textures = new ArrayList<>();


  public static Model loadToVAO(float[] pos, int[] indices, float[] tex_coords, String tex_file) {
    // Create VAO:
    int vaoID = glGenVertexArrays();
    vaos.add(vaoID);
    glBindVertexArray(vaoID);

    // Bind indices
    int vboID = glGenBuffers();
    vbos.add(vboID);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

    // Position
    storeDataInAttributeList(0, 3, pos);
    storeDataInAttributeList(1, 2, tex_coords);

    // Texture
    int texture = loadTexture(tex_file);

    unbindVAO();
    
    return new Model(vaoID, indices.length, texture);
  }

  public static int loadTexture(String file) {
    int texture = glGenTextures();
    textures.add(texture);
    glBindTexture(GL_TEXTURE_2D, texture);

    // Wrapping + Filtering:
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    // Load image and create texture:
    int[] width = new int[1], height = new int[1], nr_channels = new int[1];
    stbi_set_flip_vertically_on_load(true);

    String file_path = Objects.requireNonNull(ModelLoader.class.getClassLoader().getResource("textures/" + file)).getPath();
    ByteBuffer data = stbi_load(file_path, width, height, nr_channels, 0);

    if (data == null)
      throw new IllegalStateException(printError("Failed to load texture '" + file + "'"));

    if (file.contains(".png") || file.contains(".PNG"))
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    else
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width[0], height[0], 0, GL_RGB, GL_UNSIGNED_BYTE, data);

    glGenerateMipmap(GL_TEXTURE_2D);

    stbi_image_free(data);
    glBindTexture(GL_TEXTURE_2D, 0);
    return texture;
  }

  private static void storeDataInAttributeList(int attribNumber, int coordSize, float[] data) {
    int vboID = glGenBuffers();
    vbos.add(vboID);
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    glVertexAttribPointer(attribNumber, coordSize, GL_FLOAT, false, 0, 0);
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  private static void unbindVAO() { glBindVertexArray(0); }

  public static void cleanUp() {
    unbindVAO();

    for(int vao:vaos) glDeleteVertexArrays(vao);
    for(int vbo:vbos) glDeleteBuffers(vbo);
    for(int texture:textures) glDeleteTextures(texture);
  }
}
