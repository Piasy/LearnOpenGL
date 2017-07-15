precision mediump float;
uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit2;
varying vec2 v_TextureCoordinates2;

void main() {
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates) + 0.5 * texture2D(u_TextureUnit2, v_TextureCoordinates2);
}