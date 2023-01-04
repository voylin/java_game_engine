package mke.shaders;

import mke.utils.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
  private final int vertex_shader_id, fragment_shader_id, program_id;
  private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


  public ShaderProgram(String vertFile, String fragFile) {
    vertex_shader_id = loadShader(vertFile, GL_VERTEX_SHADER);
    fragment_shader_id = loadShader(fragFile, GL_FRAGMENT_SHADER);
    program_id = glCreateProgram();

    glAttachShader(program_id, vertex_shader_id);
    glAttachShader(program_id, fragment_shader_id);
    bindAttributes();
    glLinkProgram(program_id);
    glValidateProgram(program_id);
    getAllUniformLocations();
  }

  public void start() { glUseProgram(program_id); }
  public void stop() { glUseProgram(0); }

  public void cleanUp() {
    stop();
    glDetachShader(program_id, vertex_shader_id);
    glDetachShader(program_id, fragment_shader_id);
    glDeleteShader(vertex_shader_id);
    glDeleteShader(fragment_shader_id);
    glDeleteProgram(program_id);
  }

  protected abstract void bindAttributes();
  protected void bindAttribute(int attribute, String varName) {
    glBindAttribLocation(program_id, attribute, varName);
  }

  protected abstract void getAllUniformLocations();
  protected int getUniformLocation(String name) {
    return glGetUniformLocation(program_id, name);
  }


  protected void loadFloat(int loc, float value) { glUniform1f(loc, value); }
  protected void loadVector(int loc, Vector3f value) { glUniform3f(loc, value.x, value.y, value.z); }
  protected void loadBool(int loc, boolean value) { glUniform1f(loc, value?1:0); }
  protected void loadMatrix(int loc, Matrix4f value) {
    value.get(matrixBuffer);
    glUniformMatrix4fv(loc, false, matrixBuffer);
  }


  private static int loadShader(String file, int type) {
    StringBuilder shaderSource = new StringBuilder();
    try {
      String line;
      InputStream stream = ShaderProgram.class.getClassLoader().getResourceAsStream(file);
      assert stream != null;
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

      while((line = reader.readLine()) != null)
        shaderSource.append(line).append("//\n");

      reader.close();
    } catch(IOException e) {
      e.printStackTrace();
      throw new IllegalStateException(Logger.printError("Could not find shader file!"));
    } catch (NullPointerException e) {
      e.printStackTrace();
      throw new IllegalStateException("Error loading file");
    }

    int shaderID = glCreateShader(type);
    glShaderSource(shaderID, shaderSource);
    glCompileShader(shaderID);
    if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE){
      System.out.println(glGetShaderInfoLog(shaderID, 500));
      throw new IllegalStateException(Logger.printError("Could not compile shader!"));
    }
    return shaderID;
  }
}
