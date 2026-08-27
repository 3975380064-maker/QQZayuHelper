package kotlin.io.encoding;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.AbstractList;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: Base64.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0017\u0018\u0000 <2\u00020\u0001:\u0002<=B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000¢\u0006\u0002\b\u0011J%\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b\u0017J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0015H\u0002J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u0015H\u0002J%\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b!J\"\u0010\"\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J\"\u0010\"\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J0\u0010#\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J4\u0010%\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J4\u0010%\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J%\u0010&\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b'J\"\u0010(\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J4\u0010)\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J5\u0010*\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b+J\u0015\u0010,\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u0015H\u0000¢\u0006\u0002\b-J=\u0010.\u001a\u0002H/\"\f\b\u0000\u0010/*\u000600j\u0002`12\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u0002H/2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015¢\u0006\u0002\u00102J\"\u00103\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J%\u00104\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b5J(\u00106\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u0015H\u0002J\b\u00108\u001a\u00020\u0003H\u0002J \u00109\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u0010\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\u0006H\u0007R\u0014\u0010\u0004\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006>"}, d2 = {"Lkotlin/io/encoding/Base64;", "", "isUrlSafe", "", "isMimeScheme", "paddingOption", "Lkotlin/io/encoding/Base64$PaddingOption;", "(ZZLkotlin/io/encoding/Base64$PaddingOption;)V", "isMimeScheme$kotlin_stdlib", "()Z", "isUrlSafe$kotlin_stdlib", "getPaddingOption$kotlin_stdlib", "()Lkotlin/io/encoding/Base64$PaddingOption;", "bytesToStringImpl", "", "source", "", "bytesToStringImpl$kotlin_stdlib", "charsToBytesImpl", "", "startIndex", "", "endIndex", "charsToBytesImpl$kotlin_stdlib", "checkDestinationBounds", "", "destinationSize", "destinationOffset", "capacityNeeded", "checkPaddingIsAllowed", "padIndex", "checkSourceBounds", "sourceSize", "checkSourceBounds$kotlin_stdlib", "decode", "decodeImpl", "destination", "decodeIntoByteArray", "decodeSize", "decodeSize$kotlin_stdlib", "encode", "encodeIntoByteArray", "encodeIntoByteArrayImpl", "encodeIntoByteArrayImpl$kotlin_stdlib", "encodeSize", "encodeSize$kotlin_stdlib", "encodeToAppendable", "A", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "([BLjava/lang/Appendable;II)Ljava/lang/Appendable;", "encodeToByteArray", "encodeToByteArrayImpl", "encodeToByteArrayImpl$kotlin_stdlib", "handlePaddingSymbol", "byteStart", "shouldPadOnEncode", "skipIllegalSymbolsIfMime", "withPadding", "option", "Default", "PaddingOption", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public class Base64 {
    private static final int bitsPerByte = 8;
    private static final int bitsPerSymbol = 6;
    public static final int bytesPerGroup = 3;
    private static final int mimeGroupsPerLine = 19;
    public static final int mimeLineLength = 76;
    public static final byte padSymbol = 61;
    public static final int symbolsPerGroup = 4;
    private final boolean isMimeScheme;
    private final boolean isUrlSafe;
    private final PaddingOption paddingOption;
    public static final Default Default = new Default(null);
    private static final byte[] mimeLineSeparatorSymbols = {13, 10};
    private static final Base64 UrlSafe = new Base64(true, false, PaddingOption.PRESENT);
    private static final Base64 Mime = new Base64(false, true, PaddingOption.PRESENT);

    public /* synthetic */ Base64(boolean z, boolean z2, PaddingOption paddingOption, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, paddingOption);
    }

    private Base64(boolean z, boolean z2, PaddingOption paddingOption) {
        this.isUrlSafe = z;
        this.isMimeScheme = z2;
        this.paddingOption = paddingOption;
        if (z && z2) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final boolean isUrlSafe$kotlin_stdlib() {
        return this.isUrlSafe;
    }

    public final boolean isMimeScheme$kotlin_stdlib() {
        return this.isMimeScheme;
    }

    public final PaddingOption getPaddingOption$kotlin_stdlib() {
        return this.paddingOption;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: Base64.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/io/encoding/Base64$PaddingOption;", "", "(Ljava/lang/String;I)V", "PRESENT", "ABSENT", "PRESENT_OPTIONAL", "ABSENT_OPTIONAL", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class PaddingOption {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ PaddingOption[] $VALUES;
        public static final PaddingOption PRESENT = new PaddingOption("PRESENT", 0);
        public static final PaddingOption ABSENT = new PaddingOption("ABSENT", 1);
        public static final PaddingOption PRESENT_OPTIONAL = new PaddingOption("PRESENT_OPTIONAL", 2);
        public static final PaddingOption ABSENT_OPTIONAL = new PaddingOption("ABSENT_OPTIONAL", 3);

        private static final /* synthetic */ PaddingOption[] $values() {
            return new PaddingOption[]{PRESENT, ABSENT, PRESENT_OPTIONAL, ABSENT_OPTIONAL};
        }

        public static EnumEntries<PaddingOption> getEntries() {
            return $ENTRIES;
        }

        public static PaddingOption valueOf(String str) {
            return (PaddingOption) Enum.valueOf(PaddingOption.class, str);
        }

        public static PaddingOption[] values() {
            return (PaddingOption[]) $VALUES.clone();
        }

        private PaddingOption(String str, int i) {
        }

        static {
            PaddingOption[] paddingOptionArr$values = $values();
            $VALUES = paddingOptionArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(paddingOptionArr$values);
        }
    }

    public final Base64 withPadding(PaddingOption option) {
        Intrinsics.checkNotNullParameter(option, "option");
        return this.paddingOption == option ? this : new Base64(this.isUrlSafe, this.isMimeScheme, option);
    }

    public static /* synthetic */ byte[] encodeToByteArray$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToByteArray");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return base64.encodeToByteArray(bArr, i, i2);
    }

    public final byte[] encodeToByteArray(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        return encodeToByteArrayImpl$kotlin_stdlib(source, i, i2);
    }

    public static /* synthetic */ int encodeIntoByteArray$default(Base64 base64, byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeIntoByteArray");
        }
        if ((i4 & 4) != 0) {
            i = 0;
        }
        if ((i4 & 8) != 0) {
            i2 = 0;
        }
        if ((i4 & 16) != 0) {
            i3 = bArr.length;
        }
        return base64.encodeIntoByteArray(bArr, bArr2, i, i2, i3);
    }

    public final int encodeIntoByteArray(byte[] source, byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        return encodeIntoByteArrayImpl$kotlin_stdlib(source, destination, i, i2, i3);
    }

    public static /* synthetic */ String encode$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encode");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return base64.encode(bArr, i, i2);
    }

    public final String encode(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        return new String(encodeToByteArrayImpl$kotlin_stdlib(source, i, i2), Charsets.ISO_8859_1);
    }

    public static /* synthetic */ Appendable encodeToAppendable$default(Base64 base64, byte[] bArr, Appendable appendable, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToAppendable");
        }
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = bArr.length;
        }
        return base64.encodeToAppendable(bArr, appendable, i, i2);
    }

    public final <A extends Appendable> A encodeToAppendable(byte[] source, A destination, int i, int i2) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        destination.append(new String(encodeToByteArrayImpl$kotlin_stdlib(source, i, i2), Charsets.ISO_8859_1));
        return destination;
    }

    public static /* synthetic */ byte[] decode$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return base64.decode(bArr, i, i2);
    }

    public final byte[] decode(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length, i, i2);
        int iDecodeSize$kotlin_stdlib = decodeSize$kotlin_stdlib(source, i, i2);
        byte[] bArr = new byte[iDecodeSize$kotlin_stdlib];
        if (decodeImpl(source, bArr, 0, i, i2) == iDecodeSize$kotlin_stdlib) {
            return bArr;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public static /* synthetic */ int decodeIntoByteArray$default(Base64 base64, byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
        }
        if ((i4 & 4) != 0) {
            i = 0;
        }
        if ((i4 & 8) != 0) {
            i2 = 0;
        }
        if ((i4 & 16) != 0) {
            i3 = bArr.length;
        }
        return base64.decodeIntoByteArray(bArr, bArr2, i, i2, i3);
    }

    public final int decodeIntoByteArray(byte[] source, byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        checkSourceBounds$kotlin_stdlib(source.length, i2, i3);
        checkDestinationBounds(destination.length, i, decodeSize$kotlin_stdlib(source, i2, i3));
        return decodeImpl(source, destination, i, i2, i3);
    }

    public static /* synthetic */ byte[] decode$default(Base64 base64, CharSequence charSequence, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = charSequence.length();
        }
        return base64.decode(charSequence, i, i2);
    }

    public final byte[] decode(CharSequence source, int i, int i2) {
        byte[] bArrCharsToBytesImpl$kotlin_stdlib;
        Intrinsics.checkNotNullParameter(source, "source");
        if (source instanceof String) {
            checkSourceBounds$kotlin_stdlib(source.length(), i, i2);
            String strSubstring = ((String) source).substring(i, i2);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
            bArrCharsToBytesImpl$kotlin_stdlib = strSubstring.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bArrCharsToBytesImpl$kotlin_stdlib, "getBytes(...)");
        } else {
            bArrCharsToBytesImpl$kotlin_stdlib = charsToBytesImpl$kotlin_stdlib(source, i, i2);
        }
        return decode$default(this, bArrCharsToBytesImpl$kotlin_stdlib, 0, 0, 6, (Object) null);
    }

    public static /* synthetic */ int decodeIntoByteArray$default(Base64 base64, CharSequence charSequence, byte[] bArr, int i, int i2, int i3, int i4, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
        }
        if ((i4 & 4) != 0) {
            i = 0;
        }
        if ((i4 & 8) != 0) {
            i2 = 0;
        }
        if ((i4 & 16) != 0) {
            i3 = charSequence.length();
        }
        return base64.decodeIntoByteArray(charSequence, bArr, i, i2, i3);
    }

    public final int decodeIntoByteArray(CharSequence source, byte[] destination, int i, int i2, int i3) {
        byte[] bArrCharsToBytesImpl$kotlin_stdlib;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        if (source instanceof String) {
            checkSourceBounds$kotlin_stdlib(source.length(), i2, i3);
            String strSubstring = ((String) source).substring(i2, i3);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(strSubstring, "null cannot be cast to non-null type java.lang.String");
            bArrCharsToBytesImpl$kotlin_stdlib = strSubstring.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(bArrCharsToBytesImpl$kotlin_stdlib, "getBytes(...)");
        } else {
            bArrCharsToBytesImpl$kotlin_stdlib = charsToBytesImpl$kotlin_stdlib(source, i2, i3);
        }
        return decodeIntoByteArray$default(this, bArrCharsToBytesImpl$kotlin_stdlib, destination, i, 0, 0, 24, (Object) null);
    }

    public final byte[] encodeToByteArrayImpl$kotlin_stdlib(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length, i, i2);
        byte[] bArr = new byte[encodeSize$kotlin_stdlib(i2 - i)];
        encodeIntoByteArrayImpl$kotlin_stdlib(source, bArr, 0, i, i2);
        return bArr;
    }

    public final int encodeIntoByteArrayImpl$kotlin_stdlib(byte[] source, byte[] destination, int i, int i2, int i3) {
        int i4 = i2;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        checkSourceBounds$kotlin_stdlib(source.length, i4, i3);
        checkDestinationBounds(destination.length, i, encodeSize$kotlin_stdlib(i3 - i4));
        byte[] bArr = this.isUrlSafe ? Base64Kt.base64UrlEncodeMap : Base64Kt.base64EncodeMap;
        int i5 = this.isMimeScheme ? 19 : Integer.MAX_VALUE;
        int i6 = i;
        while (i4 + 2 < i3) {
            int iMin = Math.min((i3 - i4) / 3, i5);
            for (int i7 = 0; i7 < iMin; i7++) {
                int i8 = source[i4] & UByte.MAX_VALUE;
                int i9 = i4 + 2;
                int i10 = source[i4 + 1] & UByte.MAX_VALUE;
                i4 += 3;
                int i11 = (i10 << 8) | (i8 << 16) | (source[i9] & UByte.MAX_VALUE);
                destination[i6] = bArr[i11 >>> 18];
                destination[i6 + 1] = bArr[(i11 >>> 12) & 63];
                int i12 = i6 + 3;
                destination[i6 + 2] = bArr[(i11 >>> 6) & 63];
                i6 += 4;
                destination[i12] = bArr[i11 & 63];
            }
            if (iMin == i5 && i4 != i3) {
                int i13 = i6 + 1;
                byte[] bArr2 = mimeLineSeparatorSymbols;
                destination[i6] = bArr2[0];
                i6 += 2;
                destination[i13] = bArr2[1];
            }
        }
        int i14 = i3 - i4;
        if (i14 == 1) {
            int i15 = i4 + 1;
            int i16 = (source[i4] & UByte.MAX_VALUE) << 4;
            destination[i6] = bArr[i16 >>> 6];
            int i17 = i6 + 2;
            destination[i6 + 1] = bArr[i16 & 63];
            if (shouldPadOnEncode()) {
                int i18 = i6 + 3;
                destination[i17] = padSymbol;
                i6 += 4;
                destination[i18] = padSymbol;
                i4 = i15;
            } else {
                i4 = i15;
                i6 = i17;
            }
        } else if (i14 == 2) {
            int i19 = i4 + 1;
            int i20 = source[i4] & UByte.MAX_VALUE;
            i4 += 2;
            int i21 = ((source[i19] & UByte.MAX_VALUE) << 2) | (i20 << 10);
            destination[i6] = bArr[i21 >>> 12];
            destination[i6 + 1] = bArr[(i21 >>> 6) & 63];
            int i22 = i6 + 3;
            destination[i6 + 2] = bArr[i21 & 63];
            if (shouldPadOnEncode()) {
                i6 += 4;
                destination[i22] = padSymbol;
            } else {
                i6 = i22;
            }
        }
        if (i4 == i3) {
            return i6 - i;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final int encodeSize$kotlin_stdlib(int i) {
        int i2 = i / 3;
        int i3 = i % 3;
        int i4 = i2 * 4;
        if (i3 != 0) {
            i4 += shouldPadOnEncode() ? 4 : i3 + 1;
        }
        if (this.isMimeScheme) {
            i4 += ((i4 - 1) / 76) * 2;
        }
        if (i4 >= 0) {
            return i4;
        }
        throw new IllegalArgumentException("Input is too big");
    }

    private final boolean shouldPadOnEncode() {
        return this.paddingOption == PaddingOption.PRESENT || this.paddingOption == PaddingOption.PRESENT_OPTIONAL;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00de, code lost:
    
        if (r7 == (-2)) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00e1, code lost:
    
        if (r7 == (-8)) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00e3, code lost:
    
        if (r4 != 0) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e9, code lost:
    
        if (r19.paddingOption == kotlin.io.encoding.Base64.PaddingOption.PRESENT) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00f3, code lost:
    
        throw new java.lang.IllegalArgumentException("The padding option is set to PRESENT, but the input is not properly padded");
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00f4, code lost:
    
        if (r8 != 0) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00f6, code lost:
    
        r3 = skipIllegalSymbolsIfMime(r20, r6, r24);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00fa, code lost:
    
        if (r3 < r24) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fe, code lost:
    
        return r9 - r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ff, code lost:
    
        r1 = r20[r3] & kotlin.UByte.MAX_VALUE;
        r4 = new java.lang.StringBuilder("Symbol '").append((char) r1).append("'(");
        r1 = java.lang.Integer.toString(r1, kotlin.text.CharsKt.checkRadix(r23));
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, "toString(...)");
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x013b, code lost:
    
        throw new java.lang.IllegalArgumentException(r4.append(r1).append(") at index ").append(r3 - 1).append(" is prohibited after the pad character").toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0143, code lost:
    
        throw new java.lang.IllegalArgumentException("The pad bits must be zeros");
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x014b, code lost:
    
        throw new java.lang.IllegalArgumentException("The last unit of input does not have enough bits");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final int decodeImpl(byte[] r20, byte[] r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.encoding.Base64.decodeImpl(byte[], byte[], int, int, int):int");
    }

    public final int decodeSize$kotlin_stdlib(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        int i3 = i2 - i;
        if (i3 == 0) {
            return 0;
        }
        if (i3 == 1) {
            throw new IllegalArgumentException("Input should have at least 2 symbols for Base64 decoding, startIndex: " + i + ", endIndex: " + i2);
        }
        if (this.isMimeScheme) {
            while (true) {
                if (i >= i2) {
                    break;
                }
                int i4 = Base64Kt.base64DecodeMap[source[i] & UByte.MAX_VALUE];
                if (i4 < 0) {
                    if (i4 == -2) {
                        i3 -= i2 - i;
                        break;
                    }
                    i3--;
                }
                i++;
            }
        } else if (source[i2 - 1] == 61) {
            i3 = source[i2 + (-2)] == 61 ? i3 - 2 : i3 - 1;
        }
        return (int) ((i3 * 6) / 8);
    }

    public final byte[] charsToBytesImpl$kotlin_stdlib(CharSequence source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length(), i, i2);
        byte[] bArr = new byte[i2 - i];
        int i3 = 0;
        while (i < i2) {
            char cCharAt = source.charAt(i);
            if (cCharAt <= 255) {
                bArr[i3] = (byte) cCharAt;
                i3++;
            } else {
                bArr[i3] = 63;
                i3++;
            }
            i++;
        }
        return bArr;
    }

    public final String bytesToStringImpl$kotlin_stdlib(byte[] source) {
        Intrinsics.checkNotNullParameter(source, "source");
        StringBuilder sb = new StringBuilder(source.length);
        for (byte b : source) {
            sb.append((char) b);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final int handlePaddingSymbol(byte[] bArr, int i, int i2, int i3) {
        if (i3 == -8) {
            throw new IllegalArgumentException("Redundant pad character at index " + i);
        }
        if (i3 == -6) {
            checkPaddingIsAllowed(i);
            return i + 1;
        }
        if (i3 != -4) {
            if (i3 == -2) {
                return i + 1;
            }
            throw new IllegalStateException("Unreachable".toString());
        }
        checkPaddingIsAllowed(i);
        int iSkipIllegalSymbolsIfMime = skipIllegalSymbolsIfMime(bArr, i + 1, i2);
        if (iSkipIllegalSymbolsIfMime == i2 || bArr[iSkipIllegalSymbolsIfMime] != 61) {
            throw new IllegalArgumentException("Missing one pad character at index " + iSkipIllegalSymbolsIfMime);
        }
        return iSkipIllegalSymbolsIfMime + 1;
    }

    private final void checkPaddingIsAllowed(int i) {
        if (this.paddingOption == PaddingOption.ABSENT) {
            throw new IllegalArgumentException("The padding option is set to ABSENT, but the input has a pad character at index " + i);
        }
    }

    private final int skipIllegalSymbolsIfMime(byte[] bArr, int i, int i2) {
        if (!this.isMimeScheme) {
            return i;
        }
        while (i < i2) {
            if (Base64Kt.base64DecodeMap[bArr[i] & UByte.MAX_VALUE] != -1) {
                break;
            }
            i++;
        }
        return i;
    }

    public final void checkSourceBounds$kotlin_stdlib(int i, int i2, int i3) {
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i2, i3, i);
    }

    private final void checkDestinationBounds(int i, int i2, int i3) {
        if (i2 < 0 || i2 > i) {
            throw new IndexOutOfBoundsException("destination offset: " + i2 + ", destination size: " + i);
        }
        int i4 = i2 + i3;
        if (i4 < 0 || i4 > i) {
            throw new IndexOutOfBoundsException("The destination array does not have enough capacity, destination offset: " + i2 + ", destination size: " + i + ", capacity needed: " + i3);
        }
    }

    /* compiled from: Base64.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0005R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlin/io/encoding/Base64$Default;", "Lkotlin/io/encoding/Base64;", "()V", "Mime", "getMime", "()Lkotlin/io/encoding/Base64;", "UrlSafe", "getUrlSafe", "bitsPerByte", "", "bitsPerSymbol", "bytesPerGroup", "mimeGroupsPerLine", "mimeLineLength", "mimeLineSeparatorSymbols", "", "getMimeLineSeparatorSymbols$kotlin_stdlib", "()[B", "padSymbol", "", "symbolsPerGroup", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Default extends Base64 {
        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private Default() {
            boolean z = false;
            super(z, z, PaddingOption.PRESENT, null);
        }

        public final byte[] getMimeLineSeparatorSymbols$kotlin_stdlib() {
            return Base64.mimeLineSeparatorSymbols;
        }

        public final Base64 getUrlSafe() {
            return Base64.UrlSafe;
        }

        public final Base64 getMime() {
            return Base64.Mime;
        }
    }
}
