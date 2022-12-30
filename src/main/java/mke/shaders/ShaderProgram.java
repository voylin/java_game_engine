package mke.shaders;

import mke.utils.Logger;

import static mke.Core.*;

import java.io.*;

import static mke.utils.Logger.printEngine;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {
  private int vertexShaderID, fragmentShaderID, programID;


  public ShaderProgram(String vertFile, String fragFile) {
    vertexShaderID = loadShader(vertFile, GL_VERTEX_SHADER);
    fragmentShaderID = loadShader(fragFile, GL_FRAGMENT_SHADER);
    programID = glCreateProgram();

    glAttachShader(programID, vertexShaderID);
    glAttachShader(programID, fragmentShaderID);
    bindAttributes();
    glLinkProgram(programID);
    glValidateProgram(programID);
  }

  public void start() { glUseProgram(programID); }
  public void stop() { glUseProgram(0); }

  public void cleanUp() {
    stop();
    glDetachShader(programID, vertexShaderID);
    glDetachShader(programID, fragmentShaderID);
    glDeleteShader(vertexShaderID);
    glDeleteShader(fragmentShaderID);
    glDeleteProgram(programID);
  }

  protected abstract void bindAttributes();
  protected void bindAttribute(int attribute, String varName) {
    glBindAttribLocation(programID, attribute, varName);
  }

  private static int loadShader(String file, int type) {
    StringBuilder shaderSource = new StringBuilder();
    try {
      String line;
      InputStream stream = ShaderProgram.class.getClassLoader().getResourceAsStream(file);
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
