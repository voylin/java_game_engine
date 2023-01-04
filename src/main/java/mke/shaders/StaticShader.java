package mke.shaders;

import mke.utils.MathStuff;
import org.joml.Matrix4f;


public class StaticShader extends ShaderProgram {
  private static final String VERTEX_FILE = "shaders/static_shader.vert",
                              FRAGMENT_FILE = "shaders/static_shader.frag";
  private int loc_transformationMatrix, loc_projectionMatrix, loc_viewMatrix;


  public StaticShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "tex_coords");
  }

  @Override
  protected void getAllUniformLocations() {
    loc_transformationMatrix = super.getUniformLocation("transformationMatrix");
    loc_projectionMatrix = super.getUniformLocation("projectionMatrix");
    loc_viewMatrix = super.getUniformLocation("viewMatrix");
  }

  public void loadTransformationMatrix(Matrix4f matrix) { super.loadMatrix(loc_transformationMatrix, matrix); }
  public void loadProjectionMatrix(Matrix4f projection) { super.loadMatrix(loc_projectionMatrix, projection); }
  public void loadViewMatrix() {
    Matrix4f viewMatrix = MathStuff.createViewMatrix();
    super.loadMatrix(loc_viewMatrix, viewMatrix);
  }
}
