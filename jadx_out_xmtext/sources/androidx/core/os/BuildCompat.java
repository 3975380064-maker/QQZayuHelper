package androidx.core.os;

import android.os.Build;
import android.os.ext.SdkExtensions;
import java.util.Locale;
import kotlin.time.DurationKt;

/* loaded from: classes.dex */
public class BuildCompat {
    public static final int AD_SERVICES_EXTENSION_INT;
    public static final int R_EXTENSION_INT;
    public static final int S_EXTENSION_INT;
    public static final int T_EXTENSION_INT;

    public @interface PrereleaseSdkCheck {
    }

    @Deprecated
    public static boolean isAtLeastN() {
        return true;
    }

    private BuildCompat() {
    }

    protected static boolean isAtLeastPreReleaseCodename(String str, String str2) {
        return !"REL".equals(str2) && str2.toUpperCase(Locale.ROOT).compareTo(str.toUpperCase(Locale.ROOT)) >= 0;
    }

    @Deprecated
    public static boolean isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    @Deprecated
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @Deprecated
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @Deprecated
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @Deprecated
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @Deprecated
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @Deprecated
    public static boolean isAtLeastS() {
        if (Build.VERSION.SDK_INT < 31) {
            return Build.VERSION.SDK_INT >= 30 && isAtLeastPreReleaseCodename("S", Build.VERSION.CODENAME);
        }
        return true;
    }

    @Deprecated
    public static boolean isAtLeastSv2() {
        if (Build.VERSION.SDK_INT < 32) {
            return Build.VERSION.SDK_INT >= 31 && isAtLeastPreReleaseCodename("Sv2", Build.VERSION.CODENAME);
        }
        return true;
    }

    public static boolean isAtLeastT() {
        if (Build.VERSION.SDK_INT < 33) {
            return Build.VERSION.SDK_INT >= 32 && isAtLeastPreReleaseCodename("Tiramisu", Build.VERSION.CODENAME);
        }
        return true;
    }

    public static boolean isAtLeastU() {
        return Build.VERSION.SDK_INT >= 33 && isAtLeastPreReleaseCodename("UpsideDownCake", Build.VERSION.CODENAME);
    }

    static {
        R_EXTENSION_INT = Build.VERSION.SDK_INT >= 30 ? Extensions30Impl.R : 0;
        S_EXTENSION_INT = Build.VERSION.SDK_INT >= 30 ? Extensions30Impl.S : 0;
        T_EXTENSION_INT = Build.VERSION.SDK_INT >= 30 ? Extensions30Impl.TIRAMISU : 0;
        AD_SERVICES_EXTENSION_INT = Build.VERSION.SDK_INT >= 30 ? Extensions30Impl.AD_SERVICES : 0;
    }

    private static final class Extensions30Impl {
        static final int R = SdkExtensions.getExtensionVersion(30);
        static final int S = SdkExtensions.getExtensionVersion(31);
        static final int TIRAMISU = SdkExtensions.getExtensionVersion(33);
        static final int AD_SERVICES = SdkExtensions.getExtensionVersion(DurationKt.NANOS_IN_MILLIS);

        private Extensions30Impl() {
        }
    }
}
