package mke.types;

public class Model {
  private final int vao_id, vertexCount, texture_id;


  public Model(int vao_id, int vertexCount, int texture_id) {
    this.vao_id = vao_id;
    this.vertexCount = vertexCount;
    this.texture_id = texture_id;
  }

  public int getVaoID() { return vao_id; }
  public int getVertexCount() { return vertexCount; }
  public int getTextureID() { return texture_id; }
}
