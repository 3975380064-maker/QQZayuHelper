package androidx.emoji2.text.flatbuffer;

import androidx.emoji2.text.flatbuffer.Utf8;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class Utf8Safe extends Utf8 {
    private static int computeEncodedLength(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) < 128) {
            i++;
        }
        int iEncodedLengthGeneral = length;
        while (true) {
            if (i < length) {
                char cCharAt = charSequence.charAt(i);
                if (cCharAt >= 2048) {
                    iEncodedLengthGeneral += encodedLengthGeneral(charSequence, i);
                    break;
                }
                iEncodedLengthGeneral += (127 - cCharAt) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (iEncodedLengthGeneral >= length) {
            return iEncodedLengthGeneral;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (iEncodedLengthGeneral + 4294967296L));
    }

    private static int encodedLengthGeneral(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (i < length) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt < 2048) {
                i2 += (127 - cCharAt) >>> 31;
            } else {
                i2 += 2;
                if (55296 <= cCharAt && cCharAt <= 57343) {
                    if (Character.codePointAt(charSequence, i) < 65536) {
                        throw new UnpairedSurrogateException(i, length);
                    }
                    i++;
                }
            }
            i++;
        }
        return i2;
    }

    public static String decodeUtf8Array(byte[] bArr, int i, int i2) throws IllegalArgumentException {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte b = bArr[i];
            if (!Utf8.DecodeUtil.isOneByte(b)) {
                break;
            }
            i++;
            Utf8.DecodeUtil.handleOneByte(b, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte b2 = bArr[i];
            if (Utf8.DecodeUtil.isOneByte(b2)) {
                int i7 = i5 + 1;
                Utf8.DecodeUtil.handleOneByte(b2, cArr, i5);
                int i8 = i6;
                while (i8 < i3) {
                    byte b3 = bArr[i8];
                    if (!Utf8.DecodeUtil.isOneByte(b3)) {
                        break;
                    }
                    i8++;
                    Utf8.DecodeUtil.handleOneByte(b3, cArr, i7);
                    i7++;
                }
                i5 = i7;
                i = i8;
            } else if (Utf8.DecodeUtil.isTwoBytes(b2)) {
                if (i6 >= i3) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                i += 2;
                Utf8.DecodeUtil.handleTwoBytes(b2, bArr[i6], cArr, i5);
                i5++;
            } else if (Utf8.DecodeUtil.isThreeBytes(b2)) {
                if (i6 >= i3 - 1) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i9 = i + 2;
                i += 3;
                Utf8.DecodeUtil.handleThreeBytes(b2, bArr[i6], bArr[i9], cArr, i5);
                i5++;
            } else {
                if (i6 >= i3 - 2) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                byte b4 = bArr[i6];
                int i10 = i + 3;
                byte b5 = bArr[i + 2];
                i += 4;
                Utf8.DecodeUtil.handleFourBytes(b2, b4, b5, bArr[i10], cArr, i5);
                i5 += 2;
            }
        }
        return new String(cArr, 0, i5);
    }

    public static String decodeUtf8Buffer(ByteBuffer byteBuffer, int i, int i2) throws IllegalArgumentException {
        if ((i | i2 | ((byteBuffer.limit() - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer limit=%d, index=%d, limit=%d", Integer.valueOf(byteBuffer.limit()), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte b = byteBuffer.get(i);
            if (!Utf8.DecodeUtil.isOneByte(b)) {
                break;
            }
            i++;
            Utf8.DecodeUtil.handleOneByte(b, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte b2 = byteBuffer.get(i);
            if (Utf8.DecodeUtil.isOneByte(b2)) {
                int i7 = i5 + 1;
                Utf8.DecodeUtil.handleOneByte(b2, cArr, i5);
                int i8 = i6;
                while (i8 < i3) {
                    byte b3 = byteBuffer.get(i8);
                    if (!Utf8.DecodeUtil.isOneByte(b3)) {
                        break;
                    }
                    i8++;
                    Utf8.DecodeUtil.handleOneByte(b3, cArr, i7);
                    i7++;
                }
                i5 = i7;
                i = i8;
            } else if (Utf8.DecodeUtil.isTwoBytes(b2)) {
                if (i6 >= i3) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                i += 2;
                Utf8.DecodeUtil.handleTwoBytes(b2, byteBuffer.get(i6), cArr, i5);
                i5++;
            } else if (Utf8.DecodeUtil.isThreeBytes(b2)) {
                if (i6 >= i3 - 1) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                int i9 = i + 2;
                i += 3;
                Utf8.DecodeUtil.handleThreeBytes(b2, byteBuffer.get(i6), byteBuffer.get(i9), cArr, i5);
                i5++;
            } else {
                if (i6 >= i3 - 2) {
                    throw new IllegalArgumentException("Invalid UTF-8");
                }
                byte b4 = byteBuffer.get(i6);
                int i10 = i + 3;
                byte b5 = byteBuffer.get(i + 2);
                i += 4;
                Utf8.DecodeUtil.handleFourBytes(b2, b4, b5, byteBuffer.get(i10), cArr, i5);
                i5 += 2;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public int encodedLength(CharSequence charSequence) {
        return computeEncodedLength(charSequence);
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public String decodeUtf8(ByteBuffer byteBuffer, int i, int i2) throws IllegalArgumentException {
        if (byteBuffer.hasArray()) {
            return decodeUtf8Array(byteBuffer.array(), byteBuffer.arrayOffset() + i, i2);
        }
        return decodeUtf8Buffer(byteBuffer, i, i2);
    }

    private static void encodeUtf8Buffer(CharSequence charSequence, ByteBuffer byteBuffer) {
        int length = charSequence.length();
        int iPosition = byteBuffer.position();
        int i = 0;
        while (i < length) {
            try {
                char cCharAt = charSequence.charAt(i);
                if (cCharAt >= 128) {
                    break;
                }
                byteBuffer.put(iPosition + i, (byte) cCharAt);
                i++;
            } catch (IndexOutOfBoundsException unused) {
                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)));
            }
        }
        if (i == length) {
            byteBuffer.position(iPosition + i);
            return;
        }
        iPosition += i;
        while (i < length) {
            char cCharAt2 = charSequence.charAt(i);
            if (cCharAt2 < 128) {
                byteBuffer.put(iPosition, (byte) cCharAt2);
            } else if (cCharAt2 < 2048) {
                int i2 = iPosition + 1;
                try {
                    byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> 6) | 192));
                    byteBuffer.put(i2, (byte) ((cCharAt2 & '?') | 128));
                    iPosition = i2;
                } catch (IndexOutOfBoundsException unused2) {
                    iPosition = i2;
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)));
                }
            } else if (cCharAt2 < 55296 || 57343 < cCharAt2) {
                int i3 = iPosition + 1;
                byteBuffer.put(iPosition, (byte) ((cCharAt2 >>> '\f') | 224));
                iPosition += 2;
                byteBuffer.put(i3, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                byteBuffer.put(iPosition, (byte) ((cCharAt2 & '?') | 128));
            } else {
                int i4 = i + 1;
                if (i4 != length) {
                    try {
                        char cCharAt3 = charSequence.charAt(i4);
                        if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                            int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                            int i5 = iPosition + 1;
                            try {
                                byteBuffer.put(iPosition, (byte) ((codePoint >>> 18) | 240));
                                int i6 = iPosition + 2;
                                try {
                                    byteBuffer.put(i5, (byte) (((codePoint >>> 12) & 63) | 128));
                                    iPosition += 3;
                                    byteBuffer.put(i6, (byte) (((codePoint >>> 6) & 63) | 128));
                                    byteBuffer.put(iPosition, (byte) ((codePoint & 63) | 128));
                                    i = i4;
                                } catch (IndexOutOfBoundsException unused3) {
                                    i = i4;
                                    iPosition = i6;
                                    throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)));
                                }
                            } catch (IndexOutOfBoundsException unused4) {
                                iPosition = i5;
                                i = i4;
                                throw new ArrayIndexOutOfBoundsException("Failed writing " + charSequence.charAt(i) + " at index " + (byteBuffer.position() + Math.max(i, (iPosition - byteBuffer.position()) + 1)));
                            }
                        } else {
                            i = i4;
                        }
                    } catch (IndexOutOfBoundsException unused5) {
                    }
                }
                throw new UnpairedSurrogateException(i, length);
            }
            i++;
            iPosition++;
        }
        byteBuffer.position(iPosition);
    }

    private static int encodeUtf8Array(CharSequence charSequence, byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        char cCharAt;
        int length = charSequence.length();
        int i5 = i2 + i;
        int i6 = 0;
        while (i6 < length && (i4 = i6 + i) < i5 && (cCharAt = charSequence.charAt(i6)) < 128) {
            bArr[i4] = (byte) cCharAt;
            i6++;
        }
        if (i6 == length) {
            return i + length;
        }
        int i7 = i + i6;
        while (i6 < length) {
            char cCharAt2 = charSequence.charAt(i6);
            if (cCharAt2 < 128 && i7 < i5) {
                bArr[i7] = (byte) cCharAt2;
                i7++;
            } else if (cCharAt2 < 2048 && i7 <= i5 - 2) {
                int i8 = i7 + 1;
                bArr[i7] = (byte) ((cCharAt2 >>> 6) | 960);
                i7 += 2;
                bArr[i8] = (byte) ((cCharAt2 & '?') | 128);
            } else {
                if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i7 > i5 - 3) {
                    if (i7 <= i5 - 4) {
                        int i9 = i6 + 1;
                        if (i9 != charSequence.length()) {
                            char cCharAt3 = charSequence.charAt(i9);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                bArr[i7] = (byte) ((codePoint >>> 18) | 240);
                                bArr[i7 + 1] = (byte) (((codePoint >>> 12) & 63) | 128);
                                int i10 = i7 + 3;
                                bArr[i7 + 2] = (byte) (((codePoint >>> 6) & 63) | 128);
                                i7 += 4;
                                bArr[i10] = (byte) ((codePoint & 63) | 128);
                                i6 = i9;
                            } else {
                                i6 = i9;
                            }
                        }
                        throw new UnpairedSurrogateException(i6 - 1, length);
                    }
                    if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i3 = i6 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3)))) {
                        throw new UnpairedSurrogateException(i6, length);
                    }
                    throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + i7);
                }
                bArr[i7] = (byte) ((cCharAt2 >>> '\f') | 480);
                int i11 = i7 + 2;
                bArr[i7 + 1] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                i7 += 3;
                bArr[i11] = (byte) ((cCharAt2 & '?') | 128);
            }
            i6++;
        }
        return i7;
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            int iArrayOffset = byteBuffer.arrayOffset();
            byteBuffer.position(encodeUtf8Array(charSequence, byteBuffer.array(), byteBuffer.position() + iArrayOffset, byteBuffer.remaining()) - iArrayOffset);
        } else {
            encodeUtf8Buffer(charSequence, byteBuffer);
        }
    }

    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int i, int i2) {
            super("Unpaired surrogate at index " + i + " of " + i2);
        }
    }
}
