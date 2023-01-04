package mke.nodes;

import org.joml.Vector3f;

public class Node3D {
  private Vector3f position, rotation;
  private float scale;

  public Node3D(Vector3f position, Vector3f rotation, float scale) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
  }


  public void increasePosition(Vector3f direction) { this.position = this.position.add(direction); }
  public void increasePosition(float dx, float dy, float dz) { increasePosition(new Vector3f(dx, dy, dz)); }

  public void increaseRotation(Vector3f direction) { this.rotation = this.rotation.add(direction); }
  public void increaseRotation(float dx, float dy, float dz) { increaseRotation(new Vector3f(dx, dy, dz)); }

  public Vector3f getPosition() { return position; }
  public void setPosition(Vector3f position) { this.position = position; }

  public Vector3f getRotation() { return rotation; }
  public void setRotation(Vector3f rotation) { this.rotation = rotation; }

  public float getScale() { return scale; }
  public void setScale(float scale) { this.scale = scale; }
}
