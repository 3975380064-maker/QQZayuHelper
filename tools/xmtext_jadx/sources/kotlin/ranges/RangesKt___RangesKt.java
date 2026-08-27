package kotlin.ranges;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import kotlin.ranges.CharProgression;
import kotlin.ranges.IntProgression;
import kotlin.ranges.LongProgression;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Ranges.kt */
@Metadata(d1 = {"\u0000t\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001d\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\tH\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020!2\u0006\u0010\u001a\u001a\u00020\nH\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\n\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\bH\u0087\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010\u0014\u001a\u00020\u0015*\u00020#2\u0006\u0010\u001a\u001a\u00020\nH\u0087\n\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060%2\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0%2\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0%2\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\u00052\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020**\u00020\u00182\u0006\u0010(\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\b2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\b2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\t2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020)*\u00020\n2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010&\u001a\u00020'*\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\f\u0010+\u001a\u00020\u0018*\u00020*H\u0007\u001a\f\u0010+\u001a\u00020\b*\u00020'H\u0007\u001a\f\u0010+\u001a\u00020\t*\u00020)H\u0007\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020*H\u0007¢\u0006\u0002\u0010-\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\b*\u00020'H\u0007¢\u0006\u0002\u0010.\u001a\u0013\u0010,\u001a\u0004\u0018\u00010\t*\u00020)H\u0007¢\u0006\u0002\u0010/\u001a\f\u00100\u001a\u00020\u0018*\u00020*H\u0007\u001a\f\u00100\u001a\u00020\b*\u00020'H\u0007\u001a\f\u00100\u001a\u00020\t*\u00020)H\u0007\u001a\u0013\u00101\u001a\u0004\u0018\u00010\u0018*\u00020*H\u0007¢\u0006\u0002\u0010-\u001a\u0013\u00101\u001a\u0004\u0018\u00010\b*\u00020'H\u0007¢\u0006\u0002\u0010.\u001a\u0013\u00101\u001a\u0004\u0018\u00010\t*\u00020)H\u0007¢\u0006\u0002\u0010/\u001a\r\u00102\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u00102\u001a\u00020\u0018*\u00020\u00162\u0006\u00102\u001a\u000203H\u0007\u001a\r\u00102\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u00102\u001a\u00020\b*\u00020!2\u0006\u00102\u001a\u000203H\u0007\u001a\r\u00102\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u00102\u001a\u00020\t*\u00020#2\u0006\u00102\u001a\u000203H\u0007\u001a\u0014\u00104\u001a\u0004\u0018\u00010\u0018*\u00020\u0016H\u0087\b¢\u0006\u0002\u00105\u001a\u001b\u00104\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u00102\u001a\u000203H\u0007¢\u0006\u0002\u00106\u001a\u0014\u00104\u001a\u0004\u0018\u00010\b*\u00020!H\u0087\b¢\u0006\u0002\u00107\u001a\u001b\u00104\u001a\u0004\u0018\u00010\b*\u00020!2\u0006\u00102\u001a\u000203H\u0007¢\u0006\u0002\u00108\u001a\u0014\u00104\u001a\u0004\u0018\u00010\t*\u00020#H\u0087\b¢\u0006\u0002\u00109\u001a\u001b\u00104\u001a\u0004\u0018\u00010\t*\u00020#2\u0006\u00102\u001a\u000203H\u0007¢\u0006\u0002\u0010:\u001a\n\u0010;\u001a\u00020**\u00020*\u001a\n\u0010;\u001a\u00020'*\u00020'\u001a\n\u0010;\u001a\u00020)*\u00020)\u001a\u0015\u0010<\u001a\u00020**\u00020*2\u0006\u0010<\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010<\u001a\u00020'*\u00020'2\u0006\u0010<\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010<\u001a\u00020)*\u00020)2\u0006\u0010<\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u0010?\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u0010@\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u0010A\u001a\u0013\u0010=\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u0010B\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u0010D\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u0010E\u001a\u0013\u0010C\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u0010F\u001a\u0013\u0010G\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u0010H\u001a\u0013\u0010G\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010I\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010K\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010L\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010M\u001a\u0013\u0010J\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010N\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\u00052\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\u00052\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020\u0016*\u00020\u00182\u0006\u0010(\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\b2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\b2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\t2\u0006\u0010(\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020#*\u00020\n2\u0006\u0010(\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010O\u001a\u00020!*\u00020\n2\u0006\u0010(\u001a\u00020\nH\u0086\u0004¨\u0006P"}, d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "Lkotlin/ranges/OpenEndRange;", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "first", "firstOrNull", "(Lkotlin/ranges/CharProgression;)Ljava/lang/Character;", "(Lkotlin/ranges/IntProgression;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongProgression;)Ljava/lang/Long;", "last", "lastOrNull", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/ranges/RangesKt")
/* loaded from: classes.dex */
public class RangesKt___RangesKt extends RangesKt__RangesKt {
    public static final byte coerceAtLeast(byte b, byte b2) {
        return b < b2 ? b2 : b;
    }

    public static final double coerceAtLeast(double d, double d2) {
        return d < d2 ? d2 : d;
    }

    public static final float coerceAtLeast(float f, float f2) {
        return f < f2 ? f2 : f;
    }

    public static final int coerceAtLeast(int i, int i2) {
        return i < i2 ? i2 : i;
    }

    public static final long coerceAtLeast(long j, long j2) {
        return j < j2 ? j2 : j;
    }

    public static final short coerceAtLeast(short s, short s2) {
        return s < s2 ? s2 : s;
    }

    public static final byte coerceAtMost(byte b, byte b2) {
        return b > b2 ? b2 : b;
    }

    public static final double coerceAtMost(double d, double d2) {
        return d > d2 ? d2 : d;
    }

    public static final float coerceAtMost(float f, float f2) {
        return f > f2 ? f2 : f;
    }

    public static final int coerceAtMost(int i, int i2) {
        return i > i2 ? i2 : i;
    }

    public static final long coerceAtMost(long j, long j2) {
        return j > j2 ? j2 : j;
    }

    public static final short coerceAtMost(short s, short s2) {
        return s > s2 ? s2 : s;
    }

    public static final int first(IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + intProgression + " is empty.");
        }
        return intProgression.getFirst();
    }

    public static final long first(LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + longProgression + " is empty.");
        }
        return longProgression.getFirst();
    }

    public static final char first(CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + charProgression + " is empty.");
        }
        return charProgression.getFirst();
    }

    public static final Integer firstOrNull(IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            return null;
        }
        return Integer.valueOf(intProgression.getFirst());
    }

    public static final Long firstOrNull(LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            return null;
        }
        return Long.valueOf(longProgression.getFirst());
    }

    public static final Character firstOrNull(CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            return null;
        }
        return Character.valueOf(charProgression.getFirst());
    }

    public static final int last(IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + intProgression + " is empty.");
        }
        return intProgression.getLast();
    }

    public static final long last(LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + longProgression + " is empty.");
        }
        return longProgression.getLast();
    }

    public static final char last(CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + charProgression + " is empty.");
        }
        return charProgression.getLast();
    }

    public static final Integer lastOrNull(IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        if (intProgression.isEmpty()) {
            return null;
        }
        return Integer.valueOf(intProgression.getLast());
    }

    public static final Long lastOrNull(LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        if (longProgression.isEmpty()) {
            return null;
        }
        return Long.valueOf(longProgression.getLast());
    }

    public static final Character lastOrNull(CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        if (charProgression.isEmpty()) {
            return null;
        }
        return Character.valueOf(charProgression.getLast());
    }

    private static final int random(IntRange intRange) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.random(intRange, Random.Default);
    }

    private static final long random(LongRange longRange) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.random(longRange, Random.Default);
    }

    private static final char random(CharRange charRange) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return RangesKt.random(charRange, Random.Default);
    }

    public static final int random(IntRange intRange, Random random) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return RandomKt.nextInt(random, intRange);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final long random(LongRange longRange, Random random) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return RandomKt.nextLong(random, longRange);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final char random(CharRange charRange, Random random) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return (char) random.nextInt(charRange.getFirst(), charRange.getLast() + 1);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    private static final Integer randomOrNull(IntRange intRange) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.randomOrNull(intRange, Random.Default);
    }

    private static final Long randomOrNull(LongRange longRange) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.randomOrNull(longRange, Random.Default);
    }

    private static final Character randomOrNull(CharRange charRange) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return RangesKt.randomOrNull(charRange, Random.Default);
    }

    public static final Integer randomOrNull(IntRange intRange, Random random) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if (intRange.isEmpty()) {
            return null;
        }
        return Integer.valueOf(RandomKt.nextInt(random, intRange));
    }

    public static final Long randomOrNull(LongRange longRange, Random random) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if (longRange.isEmpty()) {
            return null;
        }
        return Long.valueOf(RandomKt.nextLong(random, longRange));
    }

    public static final Character randomOrNull(CharRange charRange, Random random) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if (charRange.isEmpty()) {
            return null;
        }
        return Character.valueOf((char) random.nextInt(charRange.getFirst(), charRange.getLast() + 1));
    }

    private static final boolean contains(IntRange intRange, Integer num) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return num != null && intRange.contains(num.intValue());
    }

    private static final boolean contains(LongRange longRange, Long l) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return l != null && longRange.contains(l.longValue());
    }

    private static final boolean contains(CharRange charRange, Character ch) {
        Intrinsics.checkNotNullParameter(charRange, "<this>");
        return ch != null && charRange.contains(ch.charValue());
    }

    public static final boolean intRangeContains(ClosedRange<Integer> closedRange, byte b) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Integer.valueOf(b));
    }

    public static final boolean longRangeContains(ClosedRange<Long> closedRange, byte b) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Long.valueOf(b));
    }

    public static final boolean shortRangeContains(ClosedRange<Short> closedRange, byte b) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Short.valueOf(b));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean doubleRangeContains(ClosedRange closedRange, byte b) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Double.valueOf(b));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean floatRangeContains(ClosedRange closedRange, byte b) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Float.valueOf(b));
    }

    public static final boolean intRangeContains(OpenEndRange<Integer> openEndRange, byte b) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Integer.valueOf(b));
    }

    public static final boolean longRangeContains(OpenEndRange<Long> openEndRange, byte b) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Long.valueOf(b));
    }

    public static final boolean shortRangeContains(OpenEndRange<Short> openEndRange, byte b) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Short.valueOf(b));
    }

    private static final boolean contains(IntRange intRange, byte b) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>) intRange, b);
    }

    private static final boolean contains(LongRange longRange, byte b) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>) longRange, b);
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean intRangeContains(ClosedRange closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer intExactOrNull = RangesKt.toIntExactOrNull(d);
        if (intExactOrNull != null) {
            return closedRange.contains(intExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean longRangeContains(ClosedRange closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Long longExactOrNull = RangesKt.toLongExactOrNull(d);
        if (longExactOrNull != null) {
            return closedRange.contains(longExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean byteRangeContains(ClosedRange closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(d);
        if (byteExactOrNull != null) {
            return closedRange.contains(byteExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean shortRangeContains(ClosedRange closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(d);
        if (shortExactOrNull != null) {
            return closedRange.contains(shortExactOrNull);
        }
        return false;
    }

    public static final boolean floatRangeContains(ClosedRange<Float> closedRange, double d) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Float.valueOf((float) d));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean intRangeContains(ClosedRange closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer intExactOrNull = RangesKt.toIntExactOrNull(f);
        if (intExactOrNull != null) {
            return closedRange.contains(intExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean longRangeContains(ClosedRange closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Long longExactOrNull = RangesKt.toLongExactOrNull(f);
        if (longExactOrNull != null) {
            return closedRange.contains(longExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean byteRangeContains(ClosedRange closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(f);
        if (byteExactOrNull != null) {
            return closedRange.contains(byteExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean shortRangeContains(ClosedRange closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(f);
        if (shortExactOrNull != null) {
            return closedRange.contains(shortExactOrNull);
        }
        return false;
    }

    public static final boolean doubleRangeContains(ClosedRange<Double> closedRange, float f) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Double.valueOf(f));
    }

    public static final boolean doubleRangeContains(OpenEndRange<Double> openEndRange, float f) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Double.valueOf(f));
    }

    public static final boolean longRangeContains(ClosedRange<Long> closedRange, int i) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Long.valueOf(i));
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> closedRange, int i) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(i);
        if (byteExactOrNull != null) {
            return closedRange.contains(byteExactOrNull);
        }
        return false;
    }

    public static final boolean shortRangeContains(ClosedRange<Short> closedRange, int i) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(i);
        if (shortExactOrNull != null) {
            return closedRange.contains(shortExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean doubleRangeContains(ClosedRange closedRange, int i) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Double.valueOf(i));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean floatRangeContains(ClosedRange closedRange, int i) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Float.valueOf(i));
    }

    public static final boolean longRangeContains(OpenEndRange<Long> openEndRange, int i) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Long.valueOf(i));
    }

    public static final boolean byteRangeContains(OpenEndRange<Byte> openEndRange, int i) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(i);
        if (byteExactOrNull != null) {
            return openEndRange.contains(byteExactOrNull);
        }
        return false;
    }

    public static final boolean shortRangeContains(OpenEndRange<Short> openEndRange, int i) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(i);
        if (shortExactOrNull != null) {
            return openEndRange.contains(shortExactOrNull);
        }
        return false;
    }

    private static final boolean contains(LongRange longRange, int i) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>) longRange, i);
    }

    public static final boolean intRangeContains(ClosedRange<Integer> closedRange, long j) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Integer intExactOrNull = RangesKt.toIntExactOrNull(j);
        if (intExactOrNull != null) {
            return closedRange.contains(intExactOrNull);
        }
        return false;
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> closedRange, long j) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(j);
        if (byteExactOrNull != null) {
            return closedRange.contains(byteExactOrNull);
        }
        return false;
    }

    public static final boolean shortRangeContains(ClosedRange<Short> closedRange, long j) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(j);
        if (shortExactOrNull != null) {
            return closedRange.contains(shortExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean doubleRangeContains(ClosedRange closedRange, long j) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Double.valueOf(j));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean floatRangeContains(ClosedRange closedRange, long j) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Float.valueOf(j));
    }

    public static final boolean intRangeContains(OpenEndRange<Integer> openEndRange, long j) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Integer intExactOrNull = RangesKt.toIntExactOrNull(j);
        if (intExactOrNull != null) {
            return openEndRange.contains(intExactOrNull);
        }
        return false;
    }

    public static final boolean byteRangeContains(OpenEndRange<Byte> openEndRange, long j) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(j);
        if (byteExactOrNull != null) {
            return openEndRange.contains(byteExactOrNull);
        }
        return false;
    }

    public static final boolean shortRangeContains(OpenEndRange<Short> openEndRange, long j) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Short shortExactOrNull = RangesKt.toShortExactOrNull(j);
        if (shortExactOrNull != null) {
            return openEndRange.contains(shortExactOrNull);
        }
        return false;
    }

    private static final boolean contains(IntRange intRange, long j) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>) intRange, j);
    }

    public static final boolean intRangeContains(ClosedRange<Integer> closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Integer.valueOf(s));
    }

    public static final boolean longRangeContains(ClosedRange<Long> closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Long.valueOf(s));
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(s);
        if (byteExactOrNull != null) {
            return closedRange.contains(byteExactOrNull);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean doubleRangeContains(ClosedRange closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Double.valueOf(s));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean floatRangeContains(ClosedRange closedRange, short s) {
        Intrinsics.checkNotNullParameter(closedRange, "<this>");
        return closedRange.contains(Float.valueOf(s));
    }

    public static final boolean intRangeContains(OpenEndRange<Integer> openEndRange, short s) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Integer.valueOf(s));
    }

    public static final boolean longRangeContains(OpenEndRange<Long> openEndRange, short s) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        return openEndRange.contains(Long.valueOf(s));
    }

    public static final boolean byteRangeContains(OpenEndRange<Byte> openEndRange, short s) {
        Intrinsics.checkNotNullParameter(openEndRange, "<this>");
        Byte byteExactOrNull = RangesKt.toByteExactOrNull(s);
        if (byteExactOrNull != null) {
            return openEndRange.contains(byteExactOrNull);
        }
        return false;
    }

    private static final boolean contains(IntRange intRange, short s) {
        Intrinsics.checkNotNullParameter(intRange, "<this>");
        return RangesKt.intRangeContains((ClosedRange<Integer>) intRange, s);
    }

    private static final boolean contains(LongRange longRange, short s) {
        Intrinsics.checkNotNullParameter(longRange, "<this>");
        return RangesKt.longRangeContains((ClosedRange<Long>) longRange, s);
    }

    public static final IntProgression downTo(int i, byte b) {
        return IntProgression.Companion.fromClosedRange(i, b, -1);
    }

    public static final LongProgression downTo(long j, byte b) {
        return LongProgression.Companion.fromClosedRange(j, b, -1L);
    }

    public static final IntProgression downTo(byte b, byte b2) {
        return IntProgression.Companion.fromClosedRange(b, b2, -1);
    }

    public static final IntProgression downTo(short s, byte b) {
        return IntProgression.Companion.fromClosedRange(s, b, -1);
    }

    public static final CharProgression downTo(char c, char c2) {
        return CharProgression.Companion.fromClosedRange(c, c2, -1);
    }

    public static final IntProgression downTo(int i, int i2) {
        return IntProgression.Companion.fromClosedRange(i, i2, -1);
    }

    public static final LongProgression downTo(long j, int i) {
        return LongProgression.Companion.fromClosedRange(j, i, -1L);
    }

    public static final IntProgression downTo(byte b, int i) {
        return IntProgression.Companion.fromClosedRange(b, i, -1);
    }

    public static final IntProgression downTo(short s, int i) {
        return IntProgression.Companion.fromClosedRange(s, i, -1);
    }

    public static final LongProgression downTo(int i, long j) {
        return LongProgression.Companion.fromClosedRange(i, j, -1L);
    }

    public static final LongProgression downTo(long j, long j2) {
        return LongProgression.Companion.fromClosedRange(j, j2, -1L);
    }

    public static final LongProgression downTo(byte b, long j) {
        return LongProgression.Companion.fromClosedRange(b, j, -1L);
    }

    public static final LongProgression downTo(short s, long j) {
        return LongProgression.Companion.fromClosedRange(s, j, -1L);
    }

    public static final IntProgression downTo(int i, short s) {
        return IntProgression.Companion.fromClosedRange(i, s, -1);
    }

    public static final LongProgression downTo(long j, short s) {
        return LongProgression.Companion.fromClosedRange(j, s, -1L);
    }

    public static final IntProgression downTo(byte b, short s) {
        return IntProgression.Companion.fromClosedRange(b, s, -1);
    }

    public static final IntProgression downTo(short s, short s2) {
        return IntProgression.Companion.fromClosedRange(s, s2, -1);
    }

    public static final IntProgression reversed(IntProgression intProgression) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        return IntProgression.Companion.fromClosedRange(intProgression.getLast(), intProgression.getFirst(), -intProgression.getStep());
    }

    public static final LongProgression reversed(LongProgression longProgression) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        return LongProgression.Companion.fromClosedRange(longProgression.getLast(), longProgression.getFirst(), -longProgression.getStep());
    }

    public static final CharProgression reversed(CharProgression charProgression) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        return CharProgression.Companion.fromClosedRange(charProgression.getLast(), charProgression.getFirst(), -charProgression.getStep());
    }

    public static final IntProgression step(IntProgression intProgression, int i) {
        Intrinsics.checkNotNullParameter(intProgression, "<this>");
        RangesKt.checkStepIsPositive(i > 0, Integer.valueOf(i));
        IntProgression.Companion companion = IntProgression.Companion;
        int first = intProgression.getFirst();
        int last = intProgression.getLast();
        if (intProgression.getStep() <= 0) {
            i = -i;
        }
        return companion.fromClosedRange(first, last, i);
    }

    public static final LongProgression step(LongProgression longProgression, long j) {
        Intrinsics.checkNotNullParameter(longProgression, "<this>");
        RangesKt.checkStepIsPositive(j > 0, Long.valueOf(j));
        LongProgression.Companion companion = LongProgression.Companion;
        long first = longProgression.getFirst();
        long last = longProgression.getLast();
        if (longProgression.getStep() <= 0) {
            j = -j;
        }
        return companion.fromClosedRange(first, last, j);
    }

    public static final CharProgression step(CharProgression charProgression, int i) {
        Intrinsics.checkNotNullParameter(charProgression, "<this>");
        RangesKt.checkStepIsPositive(i > 0, Integer.valueOf(i));
        CharProgression.Companion companion = CharProgression.Companion;
        char first = charProgression.getFirst();
        char last = charProgression.getLast();
        if (charProgression.getStep() <= 0) {
            i = -i;
        }
        return companion.fromClosedRange(first, last, i);
    }

    public static final Byte toByteExactOrNull(int i) {
        if (-128 > i || i >= 128) {
            return null;
        }
        return Byte.valueOf((byte) i);
    }

    public static final Byte toByteExactOrNull(long j) {
        if (-128 > j || j >= 128) {
            return null;
        }
        return Byte.valueOf((byte) j);
    }

    public static final Byte toByteExactOrNull(short s) {
        if (-128 > s || s >= 128) {
            return null;
        }
        return Byte.valueOf((byte) s);
    }

    public static final Byte toByteExactOrNull(double d) {
        if (-128.0d > d || d > 127.0d) {
            return null;
        }
        return Byte.valueOf((byte) d);
    }

    public static final Byte toByteExactOrNull(float f) {
        if (-128.0f > f || f > 127.0f) {
            return null;
        }
        return Byte.valueOf((byte) f);
    }

    public static final Integer toIntExactOrNull(long j) {
        if (-2147483648L > j || j >= 2147483648L) {
            return null;
        }
        return Integer.valueOf((int) j);
    }

    public static final Integer toIntExactOrNull(double d) {
        if (-2.147483648E9d > d || d > 2.147483647E9d) {
            return null;
        }
        return Integer.valueOf((int) d);
    }

    public static final Integer toIntExactOrNull(float f) {
        if (-2.14748365E9f > f || f > 2.14748365E9f) {
            return null;
        }
        return Integer.valueOf((int) f);
    }

    public static final Long toLongExactOrNull(double d) {
        if (-9.223372036854776E18d > d || d > 9.223372036854776E18d) {
            return null;
        }
        return Long.valueOf((long) d);
    }

    public static final Long toLongExactOrNull(float f) {
        if (-9.223372E18f > f || f > 9.223372E18f) {
            return null;
        }
        return Long.valueOf((long) f);
    }

    public static final Short toShortExactOrNull(int i) {
        if (-32768 > i || i >= 32768) {
            return null;
        }
        return Short.valueOf((short) i);
    }

    public static final Short toShortExactOrNull(long j) {
        if (-32768 > j || j >= 32768) {
            return null;
        }
        return Short.valueOf((short) j);
    }

    public static final Short toShortExactOrNull(double d) {
        if (-32768.0d > d || d > 32767.0d) {
            return null;
        }
        return Short.valueOf((short) d);
    }

    public static final Short toShortExactOrNull(float f) {
        if (-32768.0f > f || f > 32767.0f) {
            return null;
        }
        return Short.valueOf((short) f);
    }

    public static final IntRange until(int i, byte b) {
        return new IntRange(i, b - 1);
    }

    public static final LongRange until(long j, byte b) {
        return new LongRange(j, b - 1);
    }

    public static final IntRange until(byte b, byte b2) {
        return new IntRange(b, b2 - 1);
    }

    public static final IntRange until(short s, byte b) {
        return new IntRange(s, b - 1);
    }

    public static final CharRange until(char c, char c2) {
        return Intrinsics.compare((int) c2, 0) <= 0 ? CharRange.Companion.getEMPTY() : new CharRange(c, (char) (c2 - 1));
    }

    public static final IntRange until(int i, int i2) {
        if (i2 <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(i, i2 - 1);
    }

    public static final LongRange until(long j, int i) {
        return new LongRange(j, i - 1);
    }

    public static final IntRange until(byte b, int i) {
        if (i <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(b, i - 1);
    }

    public static final IntRange until(short s, int i) {
        if (i <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange(s, i - 1);
    }

    public static final LongRange until(int i, long j) {
        if (j <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(i, j - 1);
    }

    public static final LongRange until(long j, long j2) {
        if (j2 <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(j, j2 - 1);
    }

    public static final LongRange until(byte b, long j) {
        if (j <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(b, j - 1);
    }

    public static final LongRange until(short s, long j) {
        if (j <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange(s, j - 1);
    }

    public static final IntRange until(int i, short s) {
        return new IntRange(i, s - 1);
    }

    public static final LongRange until(long j, short s) {
        return new LongRange(j, s - 1);
    }

    public static final IntRange until(byte b, short s) {
        return new IntRange(b, s - 1);
    }

    public static final IntRange until(short s, short s2) {
        return new IntRange(s, s2 - 1);
    }

    public static final <T extends Comparable<? super T>> T coerceAtLeast(T t, T minimumValue) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(minimumValue, "minimumValue");
        return t.compareTo(minimumValue) < 0 ? minimumValue : t;
    }

    public static final <T extends Comparable<? super T>> T coerceAtMost(T t, T maximumValue) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(maximumValue, "maximumValue");
        return t.compareTo(maximumValue) > 0 ? maximumValue : t;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0053 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0054 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T extends java.lang.Comparable<? super T>> T coerceIn(T r2, T r3, T r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            if (r3 == 0) goto L42
            if (r4 == 0) goto L42
            int r0 = r3.compareTo(r4)
            if (r0 > 0) goto L1d
            int r0 = r2.compareTo(r3)
            if (r0 >= 0) goto L16
            goto L4a
        L16:
            int r3 = r2.compareTo(r4)
            if (r3 <= 0) goto L54
            goto L53
        L1d:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Cannot coerce value to an empty range: maximum "
            r0.<init>(r1)
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r0 = " is less than minimum "
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.StringBuilder r3 = r4.append(r3)
            r4 = 46
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L42:
            if (r3 == 0) goto L4b
            int r0 = r2.compareTo(r3)
            if (r0 >= 0) goto L4b
        L4a:
            return r3
        L4b:
            if (r4 == 0) goto L54
            int r3 = r2.compareTo(r4)
            if (r3 <= 0) goto L54
        L53:
            return r4
        L54:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.ranges.RangesKt___RangesKt.coerceIn(java.lang.Comparable, java.lang.Comparable, java.lang.Comparable):java.lang.Comparable");
    }

    public static final byte coerceIn(byte b, byte b2, byte b3) {
        if (b2 <= b3) {
            return b < b2 ? b2 : b > b3 ? b3 : b;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) b3) + " is less than minimum " + ((int) b2) + '.');
    }

    public static final short coerceIn(short s, short s2, short s3) {
        if (s2 <= s3) {
            return s < s2 ? s2 : s > s3 ? s3 : s;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) s3) + " is less than minimum " + ((int) s2) + '.');
    }

    public static final int coerceIn(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i3 + " is less than minimum " + i2 + '.');
    }

    public static final long coerceIn(long j, long j2, long j3) {
        if (j2 <= j3) {
            return j < j2 ? j2 : j > j3 ? j3 : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + j3 + " is less than minimum " + j2 + '.');
    }

    public static final float coerceIn(float f, float f2, float f3) {
        if (f2 <= f3) {
            return f < f2 ? f2 : f > f3 ? f3 : f;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + f3 + " is less than minimum " + f2 + '.');
    }

    public static final double coerceIn(double d, double d2, double d3) {
        if (d2 <= d3) {
            return d < d2 ? d2 : d > d3 ? d3 : d;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + d3 + " is less than minimum " + d2 + '.');
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T t, ClosedFloatingPointRange<T> range) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return (!range.lessThanOrEquals(t, range.getStart()) || range.lessThanOrEquals(range.getStart(), t)) ? (!range.lessThanOrEquals(range.getEndInclusive(), t) || range.lessThanOrEquals(t, range.getEndInclusive())) ? t : range.getEndInclusive() : range.getStart();
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T t, ClosedRange<T> range) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return (T) RangesKt.coerceIn((Comparable) t, (ClosedFloatingPointRange) range);
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return t.compareTo(range.getStart()) < 0 ? (T) range.getStart() : t.compareTo(range.getEndInclusive()) > 0 ? (T) range.getEndInclusive() : t;
    }

    public static final int coerceIn(int i, ClosedRange<Integer> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Integer.valueOf(i), (ClosedFloatingPointRange<Integer>) range)).intValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return i < ((Number) range.getStart()).intValue() ? ((Number) range.getStart()).intValue() : i > ((Number) range.getEndInclusive()).intValue() ? ((Number) range.getEndInclusive()).intValue() : i;
    }

    public static final long coerceIn(long j, ClosedRange<Long> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Long.valueOf(j), (ClosedFloatingPointRange<Long>) range)).longValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return j < ((Number) range.getStart()).longValue() ? ((Number) range.getStart()).longValue() : j > ((Number) range.getEndInclusive()).longValue() ? ((Number) range.getEndInclusive()).longValue() : j;
    }
}
