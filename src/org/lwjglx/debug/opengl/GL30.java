/*
 * (C) Copyright 2017 Kai Burjack

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */
package org.lwjglx.debug.opengl;

import static org.lwjglx.debug.Context.*;
import static org.lwjglx.debug.RT.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjglx.debug.Context;
import org.lwjglx.debug.Properties;
import org.lwjglx.debug.Context.VAO;

public class GL30 {

    public static String glGetStringi(int name, int index) {
        if (Properties.PROFILE.enabled && name == org.lwjgl.opengl.GL11.GL_EXTENSIONS) {
            int numExtensions = org.lwjgl.opengl.GL11.glGetInteger(org.lwjgl.opengl.GL30.GL_NUM_EXTENSIONS);
            int GREMEDY_string_marker_index = numExtensions;
            int GREMEDY_frame_terminator_index = numExtensions + 1;
            if (index == GREMEDY_string_marker_index) {
                return "GREMEDY_string_marker";
            } else if (index == GREMEDY_frame_terminator_index) {
                return "GREMEDY_frame_terminator";
            }
        }
        return org.lwjgl.opengl.GL30.glGetStringi(name, index);
    }

    public static void glGenerateMipmap(int target) {
        org.lwjgl.opengl.GL30.glGenerateMipmap(target);
        if (Properties.PROFILE.enabled) {
            generateMipmap(target);
        }
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, ByteBuffer pointer) {
        if (Properties.VALIDATE.enabled && index > -1) {
            CURRENT_CONTEXT.get().currentVao.initializedVertexArrays[index] = pointer != null;
        }
        org.lwjgl.opengl.GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, long pointer) {
        if (Properties.VALIDATE.enabled && index > -1) {
            int vbo = org.lwjgl.opengl.GL11.glGetInteger(org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER_BINDING);
            if (vbo != 0) {
                CURRENT_CONTEXT.get().currentVao.initializedVertexArrays[index] = true;
            }
        }
        org.lwjgl.opengl.GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, ShortBuffer pointer) {
        if (Properties.VALIDATE.enabled && index > -1) {
            CURRENT_CONTEXT.get().currentVao.initializedVertexArrays[index] = pointer != null;
        }
        org.lwjgl.opengl.GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    public static void glVertexAttribIPointer(int index, int size, int type, int stride, IntBuffer pointer) {
        if (Properties.VALIDATE.enabled && index > -1) {
            CURRENT_CONTEXT.get().currentVao.initializedVertexArrays[index] = pointer != null;
        }
        org.lwjgl.opengl.GL30.glVertexAttribIPointer(index, size, type, stride, pointer);
    }

    public static void glGenVertexArrays(IntBuffer arrays) {
        org.lwjgl.opengl.GL30.glGenVertexArrays(arrays);
        if (Properties.VALIDATE.enabled) {
            Context context = CURRENT_CONTEXT.get();
            int position = arrays.position();
            for (int i = 0; i < arrays.remaining(); i++) {
                VAO vao = new VAO(context.GL_MAX_VERTEX_ATTRIBS);
                context.vaos.put(arrays.get(position + i), vao);
            }
        }
    }

    public static int glGenVertexArrays() {
        int index = org.lwjgl.opengl.GL30.glGenVertexArrays();
        if (Properties.VALIDATE.enabled) {
            Context context = CURRENT_CONTEXT.get();
            VAO vao = new VAO(context.GL_MAX_VERTEX_ATTRIBS);
            context.vaos.put(index, vao);
        }
        return index;
    }

    public static void glGenVertexArrays(int[] arrays) {
        org.lwjgl.opengl.GL30.glGenVertexArrays(arrays);
        if (Properties.VALIDATE.enabled) {
            Context context = CURRENT_CONTEXT.get();
            for (int i = 0; i < arrays.length; i++) {
                VAO vao = new VAO(context.GL_MAX_VERTEX_ATTRIBS);
                context.vaos.put(arrays[i], vao);
            }
        }
    }

    public static void glBindVertexArray(int index) {
        if (Properties.VALIDATE.enabled) {
            Context ctx = CURRENT_CONTEXT.get();
            VAO vao = ctx.vaos.get(index);
            if (vao == null && ctx.shareGroup != null) {
                for (Context c : ctx.shareGroup.contexts) {
                    if (c.vaos.containsKey(index)) {
                        throwISEOrLogError("Trying to bind unknown VAO [" + index + "] from shared context [" + c.counter + "]");
                    }
                }
            }
            ctx.currentVao = vao;
        }
        org.lwjgl.opengl.GL30.glBindVertexArray(index);
    }

    public static void glDeleteVertexArrays(int[] indices) {
        org.lwjgl.opengl.GL30.glDeleteVertexArrays(indices);
        if (Properties.VALIDATE.enabled) {
            deleteVertexArrays(indices);
        }
    }

    public static void glDeleteVertexArrays(IntBuffer indices) {
        org.lwjgl.opengl.GL30.glDeleteVertexArrays(indices);
        if (Properties.VALIDATE.enabled) {
            deleteVertexArrays(indices);
        }
    }

    public static void glDeleteVertexArrays(int index) {
        org.lwjgl.opengl.GL30.glDeleteVertexArrays(index);
        if (Properties.VALIDATE.enabled) {
            deleteVertexArray(index);
        }
    }

    public static void glGenFramebuffers(IntBuffer framebuffers) {
        org.lwjgl.opengl.GL30.glGenFramebuffers(framebuffers);
        if (Properties.VALIDATE.enabled) {
            Context ctx = CURRENT_CONTEXT.get();
            int pos = framebuffers.position();
            for (int i = 0; i < framebuffers.remaining(); i++) {
                int handle = framebuffers.get(pos + i);
                FBO fbo = new FBO(handle);
                ctx.fbos.put(handle, fbo);
            }
        }
    }

    public static int glGenFramebuffers() {
        int handle = org.lwjgl.opengl.GL30.glGenFramebuffers();
        if (Properties.VALIDATE.enabled) {
            FBO fbo = new FBO(handle);
            Context ctx = CURRENT_CONTEXT.get();
            ctx.fbos.put(handle, fbo);
        }
        return handle;
    }

    public static void glGenFramebuffers(int[] framebuffers) {
        org.lwjgl.opengl.GL30.glGenFramebuffers(framebuffers);
        if (Properties.VALIDATE.enabled) {
            Context ctx = CURRENT_CONTEXT.get();
            for (int i = 0; i < framebuffers.length; i++) {
                int handle = framebuffers[i];
                FBO fbo = new FBO(handle);
                ctx.fbos.put(handle, fbo);
            }
        }
    }

    public static void glBindFramebuffer(int target, int framebuffer) {
        if (Properties.VALIDATE.enabled) {
            Context ctx = CURRENT_CONTEXT.get();
            FBO fbo = ctx.fbos.get(framebuffer);
            if (fbo == null && ctx.shareGroup != null) {
                for (Context c : ctx.shareGroup.contexts) {
                    if (c.fbos.containsKey(framebuffer)) {
                        throwISEOrLogError("Trying to bind unknown FBO [" + framebuffer + "] from shared context [" + c.counter + "]");
                    }
                }
            }
            ctx.currentFbo = fbo;
        }
        org.lwjgl.opengl.GL30.glBindFramebuffer(target, framebuffer);
    }

    public static void glDeleteFramebuffers(IntBuffer framebuffers) {
        org.lwjgl.opengl.GL30.glDeleteFramebuffers(framebuffers);
        if (Properties.VALIDATE.enabled) {
            Context context = CURRENT_CONTEXT.get();
            int pos = framebuffers.position();
            for (int i = 0; i < framebuffers.remaining(); i++) {
                int framebuffer = framebuffers.get(pos + i);
                if (framebuffer == 0)
                    continue;
                FBO fbo = context.fbos.get(framebuffer);
                if (fbo != null && fbo == context.currentFbo) {
                    context.currentFbo = context.defaultFbo;
                }
                context.fbos.remove(framebuffer);
            }
        }
    }

    public static void glDeleteFramebuffers(int framebuffer) {
        org.lwjgl.opengl.GL30.glDeleteFramebuffers(framebuffer);
        if (Properties.VALIDATE.enabled) {
            if (framebuffer == 0)
                return;
            Context context = CURRENT_CONTEXT.get();
            FBO fbo = context.fbos.get(framebuffer);
            if (fbo != null && fbo == context.currentFbo) {
                context.currentFbo = context.defaultFbo;
            }
            context.fbos.remove(framebuffer);
        }
    }

    public static void glDeleteFramebuffers(int[] framebuffers) {
        org.lwjgl.opengl.GL30.glDeleteFramebuffers(framebuffers);
        if (Properties.VALIDATE.enabled) {
            Context context = CURRENT_CONTEXT.get();
            for (int i = 0; i < framebuffers.length; i++) {
                int framebuffer = framebuffers[i];
                if (framebuffer == 0)
                    continue;
                FBO fbo = context.fbos.get(framebuffer);
                if (fbo != null && fbo == context.currentFbo) {
                    context.currentFbo = context.defaultFbo;
                }
                context.fbos.remove(framebuffer);
            }
        }
    }

}
