#version 460 core

layout(location=0) in vec3 position;
layout(location=1) in vec2 tex_coords;

layout(location=0) out vec2 pass_tex_coords;

void main(void) {
    gl_Position = vec4(position.xyz, 1.0);
    pass_tex_coords = tex_coords;
}