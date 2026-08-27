package kotlin.uuid;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.security.SecureRandom;
import kotlin.Metadata;

/* compiled from: UuidJVM.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/uuid/SecureRandomHolder;", "", "()V", "instance", "Ljava/security/SecureRandom;", "getInstance", "()Ljava/security/SecureRandom;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
final class SecureRandomHolder {
    public static final SecureRandomHolder INSTANCE = new SecureRandomHolder();
    private static final SecureRandom instance = new SecureRandom();

    private SecureRandomHolder() {
    }

    public final SecureRandom getInstance() {
        return instance;
    }
}
