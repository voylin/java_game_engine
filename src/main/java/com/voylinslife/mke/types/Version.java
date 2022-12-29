package com.voylinslife.mke.types;

public class Version {
  public int major, minor, patch;

  public Version(int major, int minor, int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }
  public Version(int major, int minor) {
    this.major = major;
    this.minor = minor;
    this.patch = 0;
  }

  public String toString() { return major + "." + minor + "." + patch; }
  public String toStringSmall() { return major + "." + minor; }
}
