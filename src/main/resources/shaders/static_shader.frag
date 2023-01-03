#version 460 core

layout(location=0) in vec2 pass_tex_coords;

out vec4 out_color;

uniform sampler2D textureSampler;

void main(void) {
    out_color = texture(textureSampler, pass_tex_coords);
}