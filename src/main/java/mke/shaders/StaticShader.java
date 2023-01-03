package mke.shaders;


public class StaticShader extends ShaderProgram {
  private static final String VERTEX_FILE = "shaders/static_shader.vert";
  private static final String FRAGMENT_FILE = "shaders/static_shader.frag";


  public StaticShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void bindAttributes() {
    super.bindAttribute(0, "position");
    super.bindAttribute(1, "tex_coords");
  }
}
