#version 460 core

layout(location=0) in vec3 position;

layout(location=0) out vec3 color;

void main(void) {
    gl_Position = vec4(position.xyz, 1.0);
    color = vec3(position.x+0.5,1.0,position.y+0.5);
}