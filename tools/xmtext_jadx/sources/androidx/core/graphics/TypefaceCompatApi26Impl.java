package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

/* loaded from: classes.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final String ABORT_CREATION_METHOD = "abortCreation";
    private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
    private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
    private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
    private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
    private static final String FREEZE_METHOD = "freeze";
    private static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String TAG = "TypefaceCompatApi26Impl";
    protected final Method mAbortCreation;
    protected final Method mAddFontFromAssetManager;
    protected final Method mAddFontFromBuffer;
    protected final Method mCreateFromFamiliesWithDefault;
    protected final Class<?> mFontFamily;
    protected final Constructor<?> mFontFamilyCtor;
    protected final Method mFreeze;

    public TypefaceCompatApi26Impl() {
        Class<?> clsObtainFontFamily;
        Constructor<?> constructorObtainFontFamilyCtor;
        Method methodObtainAddFontFromAssetManagerMethod;
        Method methodObtainAddFontFromBufferMethod;
        Method methodObtainFreezeMethod;
        Method methodObtainAbortCreationMethod;
        Method methodObtainCreateFromFamiliesWithDefaultMethod;
        try {
            clsObtainFontFamily = obtainFontFamily();
            constructorObtainFontFamilyCtor = obtainFontFamilyCtor(clsObtainFontFamily);
            methodObtainAddFontFromAssetManagerMethod = obtainAddFontFromAssetManagerMethod(clsObtainFontFamily);
            methodObtainAddFontFromBufferMethod = obtainAddFontFromBufferMethod(clsObtainFontFamily);
            methodObtainFreezeMethod = obtainFreezeMethod(clsObtainFontFamily);
            methodObtainAbortCreationMethod = obtainAbortCreationMethod(clsObtainFontFamily);
            methodObtainCreateFromFamiliesWithDefaultMethod = obtainCreateFromFamiliesWithDefaultMethod(clsObtainFontFamily);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            clsObtainFontFamily = null;
            constructorObtainFontFamilyCtor = null;
            methodObtainAddFontFromAssetManagerMethod = null;
            methodObtainAddFontFromBufferMethod = null;
            methodObtainFreezeMethod = null;
            methodObtainAbortCreationMethod = null;
            methodObtainCreateFromFamiliesWithDefaultMethod = null;
        }
        this.mFontFamily = clsObtainFontFamily;
        this.mFontFamilyCtor = constructorObtainFontFamilyCtor;
        this.mAddFontFromAssetManager = methodObtainAddFontFromAssetManagerMethod;
        this.mAddFontFromBuffer = methodObtainAddFontFromBufferMethod;
        this.mFreeze = methodObtainFreezeMethod;
        this.mAbortCreation = methodObtainAbortCreationMethod;
        this.mCreateFromFamiliesWithDefault = methodObtainCreateFromFamiliesWithDefaultMethod;
    }

    private boolean isFontFamilyPrivateAPIAvailable() {
        if (this.mAddFontFromAssetManager == null) {
            Log.w(TAG, "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return this.mAddFontFromAssetManager != null;
    }

    private Object newFamily() {
        try {
            return this.mFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    private boolean addFontFromAssetManager(Context context, Object obj, String str, int i, int i2, int i3, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.mAddFontFromAssetManager.invoke(obj, context.getAssets(), str, 0, false, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private boolean addFontFromBuffer(Object obj, ByteBuffer byteBuffer, int i, int i2, int i3) {
        try {
            return ((Boolean) this.mAddFontFromBuffer.invoke(obj, byteBuffer, Integer.valueOf(i), null, Integer.valueOf(i2), Integer.valueOf(i3))).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    protected Typeface createFromFamiliesWithDefault(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NegativeArraySizeException {
        try {
            Object objNewInstance = Array.newInstance(this.mFontFamily, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.mCreateFromFamiliesWithDefault.invoke(null, objNewInstance, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return null;
        }
    }

    private boolean freeze(Object obj) {
        try {
            return ((Boolean) this.mFreeze.invoke(obj, new Object[0])).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException unused) {
            return false;
        }
    }

    private void abortCreation(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.mAbortCreation.invoke(obj, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException unused) {
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, fontFamilyFilesResourceEntry, resources, i);
        }
        Object objNewFamily = newFamily();
        if (objNewFamily == null) {
            return null;
        }
        FontResourcesParserCompat.FontFileResourceEntry[] entries = fontFamilyFilesResourceEntry.getEntries();
        int length = entries.length;
        int i2 = 0;
        while (i2 < length) {
            FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = entries[i2];
            Context context2 = context;
            if (!addFontFromAssetManager(context2, objNewFamily, fontFileResourceEntry.getFileName(), fontFileResourceEntry.getTtcIndex(), fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic() ? 1 : 0, FontVariationAxis.fromFontVariationSettings(fontFileResourceEntry.getVariationSettings()))) {
                abortCreation(objNewFamily);
                return null;
            }
            i2++;
            context = context2;
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily);
        }
        return null;
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fontInfoArr, int i) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        Typeface typefaceCreateFromFamiliesWithDefault;
        Object obj;
        if (fontInfoArr.length < 1) {
            return null;
        }
        if (!isFontFamilyPrivateAPIAvailable()) {
            FontsContractCompat.FontInfo fontInfoFindBestInfo = findBestInfo(fontInfoArr, i);
            try {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(fontInfoFindBestInfo.getUri(), "r", cancellationSignal);
                if (parcelFileDescriptorOpenFileDescriptor == null) {
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                    return null;
                }
                try {
                    Typeface typefaceBuild = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).setWeight(fontInfoFindBestInfo.getWeight()).setItalic(fontInfoFindBestInfo.isItalic()).build();
                    if (parcelFileDescriptorOpenFileDescriptor != null) {
                        parcelFileDescriptorOpenFileDescriptor.close();
                    }
                    return typefaceBuild;
                } finally {
                }
            } catch (IOException unused) {
                return null;
            }
        } else {
            Map<Uri, ByteBuffer> fontInfoIntoByteBuffer = TypefaceCompatUtil.readFontInfoIntoByteBuffer(context, fontInfoArr, cancellationSignal);
            Object objNewFamily = newFamily();
            if (objNewFamily == null) {
                return null;
            }
            int length = fontInfoArr.length;
            int i2 = 0;
            boolean z = false;
            while (i2 < length) {
                FontsContractCompat.FontInfo fontInfo = fontInfoArr[i2];
                ByteBuffer byteBuffer = fontInfoIntoByteBuffer.get(fontInfo.getUri());
                if (byteBuffer == null) {
                    obj = objNewFamily;
                } else {
                    boolean zAddFontFromBuffer = addFontFromBuffer(objNewFamily, byteBuffer, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic() ? 1 : 0);
                    obj = objNewFamily;
                    if (!zAddFontFromBuffer) {
                        abortCreation(obj);
                        return null;
                    }
                    z = true;
                }
                i2++;
                objNewFamily = obj;
                z = z;
            }
            Object obj2 = objNewFamily;
            if (!z) {
                abortCreation(obj2);
                return null;
            }
            if (freeze(obj2) && (typefaceCreateFromFamiliesWithDefault = createFromFamiliesWithDefault(obj2)) != null) {
                return Typeface.create(typefaceCreateFromFamiliesWithDefault, i);
            }
            return null;
        }
    }

    @Override // androidx.core.graphics.TypefaceCompatBaseImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, i, str, i2);
        }
        Object objNewFamily = newFamily();
        if (objNewFamily == null) {
            return null;
        }
        if (!addFontFromAssetManager(context, objNewFamily, str, 0, -1, -1, null)) {
            abortCreation(objNewFamily);
            return null;
        }
        if (freeze(objNewFamily)) {
            return createFromFamiliesWithDefault(objNewFamily);
        }
        return null;
    }

    protected Class<?> obtainFontFamily() throws ClassNotFoundException {
        return Class.forName(FONT_FAMILY_CLASS);
    }

    protected Constructor<?> obtainFontFamilyCtor(Class<?> cls) throws NoSuchMethodException {
        return cls.getConstructor(new Class[0]);
    }

    protected Method obtainAddFontFromAssetManagerMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(ADD_FONT_FROM_ASSET_MANAGER_METHOD, AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
    }

    protected Method obtainAddFontFromBufferMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(ADD_FONT_FROM_BUFFER_METHOD, ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
    }

    protected Method obtainFreezeMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(FREEZE_METHOD, new Class[0]);
    }

    protected Method obtainAbortCreationMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod(ABORT_CREATION_METHOD, new Class[0]);
    }

    protected Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> cls) throws NoSuchMethodException, SecurityException {
        Method declaredMethod = Typeface.class.getDeclaredMethod(CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD, Array.newInstance(cls, 1).getClass(), Integer.TYPE, Integer.TYPE);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    @Override // androidx.core.graphics.TypefaceCompatApi21Impl, androidx.core.graphics.TypefaceCompatBaseImpl
    Typeface createWeightStyle(Context context, Typeface typeface, int i, boolean z) {
        Typeface typefaceCreateWeightStyle;
        try {
            typefaceCreateWeightStyle = WeightTypefaceApi26.createWeightStyle(typeface, i, z);
        } catch (RuntimeException unused) {
            typefaceCreateWeightStyle = null;
        }
        return typefaceCreateWeightStyle == null ? super.createWeightStyle(context, typeface, i, z) : typefaceCreateWeightStyle;
    }
}
