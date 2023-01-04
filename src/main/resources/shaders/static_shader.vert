#version 460 core

layout(location=0) in vec3 position;
layout(location=1) in vec2 tex_coords;

layout(location=0) out vec2 pass_tex_coords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;


void main(void) {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    pass_tex_coords = tex_coords;
}