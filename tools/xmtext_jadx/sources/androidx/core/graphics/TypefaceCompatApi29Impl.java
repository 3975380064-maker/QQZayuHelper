package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class TypefaceCompatApi29Impl extends TypefaceCompatBaseImpl {
    private static int getMatchScore(FontStyle fontStyle, FontStyle fontStyle2) {
        return (Math.abs(fontStyle.getWeight() - fontStyle2.getWeight()) / 100) + (fontStyle.getSlant() == fontStyle2.getSlant() ? 0 : 2);
    }

    private Font findBaseFont(FontFamily fontFamily, int i) {
        FontStyle fontStyle = new FontStyle((i & 1) != 0 ? 700 : 400, (i & 2) != 0 ? 1 : 0);
        Font font = fontFamily.getFont(0);
        int matchScore = getMatchScore(fontStyle, font.getStyle());
        for (int i2 = 1; i2 < fontFamily.getSize(); i2++) {
            Font font2 = fontFamily.getFont(i2);
            int matchScore2 = getMatchScore(fontStyle, font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
        }
        return font;
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    protected Typeface createFromInputStream(Context context, InputStream inputStream) {
        throw new RuntimeException("Do not use this function in API 29 or later.");
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001a A[Catch: IOException -> 0x005a, Exception -> 0x007a, PHI: r3
      0x001a: PHI (r3v5 android.graphics.fonts.FontFamily$Builder) = (r3v3 android.graphics.fonts.FontFamily$Builder), (r3v1 android.graphics.fonts.FontFamily$Builder) binds: [B:15:0x004b, B:8:0x0018] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {IOException -> 0x005a, blocks: (B:6:0x000c, B:9:0x001a, B:23:0x0059, B:22:0x0056), top: B:31:0x000c }] */
    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r9, android.os.CancellationSignal r10, androidx.core.provider.FontsContractCompat.FontInfo[] r11, int r12) throws java.io.IOException {
        /*
            r8 = this;
            android.content.ContentResolver r9 = r9.getContentResolver()
            r0 = 0
            int r1 = r11.length     // Catch: java.lang.Exception -> L7a
            r2 = 0
            r3 = r0
        L8:
            if (r2 >= r1) goto L5d
            r4 = r11[r2]     // Catch: java.lang.Exception -> L7a
            android.net.Uri r5 = r4.getUri()     // Catch: java.io.IOException -> L5a java.lang.Exception -> L7a
            java.lang.String r6 = "r"
            android.os.ParcelFileDescriptor r5 = r9.openFileDescriptor(r5, r6, r10)     // Catch: java.io.IOException -> L5a java.lang.Exception -> L7a
            if (r5 != 0) goto L1e
            if (r5 == 0) goto L5a
        L1a:
            r5.close()     // Catch: java.io.IOException -> L5a java.lang.Exception -> L7a
            goto L5a
        L1e:
            android.graphics.fonts.Font$Builder r6 = new android.graphics.fonts.Font$Builder     // Catch: java.lang.Throwable -> L4e
            r6.<init>(r5)     // Catch: java.lang.Throwable -> L4e
            int r7 = r4.getWeight()     // Catch: java.lang.Throwable -> L4e
            android.graphics.fonts.Font$Builder r6 = r6.setWeight(r7)     // Catch: java.lang.Throwable -> L4e
            boolean r7 = r4.isItalic()     // Catch: java.lang.Throwable -> L4e
            android.graphics.fonts.Font$Builder r6 = r6.setSlant(r7)     // Catch: java.lang.Throwable -> L4e
            int r4 = r4.getTtcIndex()     // Catch: java.lang.Throwable -> L4e
            android.graphics.fonts.Font$Builder r4 = r6.setTtcIndex(r4)     // Catch: java.lang.Throwable -> L4e
            android.graphics.fonts.Font r4 = r4.build()     // Catch: java.lang.Throwable -> L4e
            if (r3 != 0) goto L48
            android.graphics.fonts.FontFamily$Builder r6 = new android.graphics.fonts.FontFamily$Builder     // Catch: java.lang.Throwable -> L4e
            r6.<init>(r4)     // Catch: java.lang.Throwable -> L4e
            r3 = r6
            goto L4b
        L48:
            r3.addFont(r4)     // Catch: java.lang.Throwable -> L4e
        L4b:
            if (r5 == 0) goto L5a
            goto L1a
        L4e:
            r4 = move-exception
            if (r5 == 0) goto L59
            r5.close()     // Catch: java.lang.Throwable -> L55
            goto L59
        L55:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch: java.io.IOException -> L5a java.lang.Exception -> L7a
        L59:
            throw r4     // Catch: java.io.IOException -> L5a java.lang.Exception -> L7a
        L5a:
            int r2 = r2 + 1
            goto L8
        L5d:
            if (r3 != 0) goto L60
            return r0
        L60:
            android.graphics.fonts.FontFamily r9 = r3.build()     // Catch: java.lang.Exception -> L7a
            android.graphics.Typeface$CustomFallbackBuilder r10 = new android.graphics.Typeface$CustomFallbackBuilder     // Catch: java.lang.Exception -> L7a
            r10.<init>(r9)     // Catch: java.lang.Exception -> L7a
            android.graphics.fonts.Font r9 = r8.findBaseFont(r9, r12)     // Catch: java.lang.Exception -> L7a
            android.graphics.fonts.FontStyle r9 = r9.getStyle()     // Catch: java.lang.Exception -> L7a
            android.graphics.Typeface$CustomFallbackBuilder r9 = r10.setStyle(r9)     // Catch: java.lang.Exception -> L7a
            android.graphics.Typeface r9 = r9.build()     // Catch: java.lang.Exception -> L7a
            return r9
        L7a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi29Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, androidx.core.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) throws IOException {
        try {
            FontFamily.Builder builder = null;
            for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry : fontFamilyFilesResourceEntry.getEntries()) {
                try {
                    Font fontBuild = new Font.Builder(resources, fontFileResourceEntry.getResourceId()).setWeight(fontFileResourceEntry.getWeight()).setSlant(fontFileResourceEntry.isItalic() ? 1 : 0).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings()).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(fontBuild);
                    } else {
                        builder.addFont(fontBuild);
                    }
                } catch (IOException unused) {
                }
            }
            if (builder == null) {
                return null;
            }
            FontFamily fontFamilyBuild = builder.build();
            return new Typeface.CustomFallbackBuilder(fontFamilyBuild).setStyle(findBaseFont(fontFamilyBuild, i).getStyle()).build();
        } catch (Exception unused2) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) throws IOException {
        try {
            Font fontBuild = new Font.Builder(resources, i).build();
            return new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).build()).setStyle(fontBuild.getStyle()).build();
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    Typeface createWeightStyle(Context context, Typeface typeface, int i, boolean z) {
        return Typeface.create(typeface, i, z);
    }
}
