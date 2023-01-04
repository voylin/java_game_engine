package mke.nodes;

import mke.types.Model;
import org.joml.Vector3f;


public class Object extends Node3D {
  private Model model;


  public Object(Model model, Vector3f position, Vector3f rotation, float scale) {
    super(position, rotation, scale);
    this.model = model;
  }

  public Model getModel() { return model; }
  public void setModel(Model model) { this.model = model; }
}
