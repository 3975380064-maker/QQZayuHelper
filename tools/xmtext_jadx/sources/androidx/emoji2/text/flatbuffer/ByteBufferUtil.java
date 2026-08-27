package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class ByteBufferUtil {
    public static int getSizePrefix(ByteBuffer byteBuffer) {
        return byteBuffer.getInt(byteBuffer.position());
    }

    public static ByteBuffer removeSizePrefix(ByteBuffer byteBuffer) {
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.position(byteBufferDuplicate.position() + 4);
        return byteBufferDuplicate;
    }
}
