#version 460 core

layout(location=0) in vec3 color;

out vec4 out_color;

void main(void) {
    out_color = vec4(color, 1.0);
}