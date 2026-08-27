package kotlin.ranges;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import kotlin.ranges.UIntProgression;
import kotlin.ranges.ULongProgression;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _URanges.kt */
@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u001b\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007¢\u0006\u0004\b\u0006\u0010\u0007\u001a\u001b\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007¢\u0006\u0004\b\t\u0010\n\u001a\u001b\u0010\u0000\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007¢\u0006\u0004\b\f\u0010\r\u001a\u001b\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007¢\u0006\u0004\b\u0010\u0010\u0004\u001a\u001b\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007¢\u0006\u0004\b\u0011\u0010\u0007\u001a\u001b\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007¢\u0006\u0004\b\u0012\u0010\n\u001a\u001b\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007¢\u0006\u0004\b\u0013\u0010\r\u001a#\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007¢\u0006\u0004\b\u0015\u0010\u0016\u001a#\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007¢\u0006\u0004\b\u0017\u0010\u0018\u001a!\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007¢\u0006\u0004\b\u001b\u0010\u001c\u001a#\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007¢\u0006\u0004\b\u001d\u0010\u001e\u001a!\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007¢\u0006\u0004\b\u001f\u0010 \u001a#\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007¢\u0006\u0004\b!\u0010\"\u001a\u001c\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002¢\u0006\u0004\b'\u0010(\u001a\u001c\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\n¢\u0006\u0002\b*\u001a\u001c\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002¢\u0006\u0004\b+\u0010,\u001a\u001c\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002¢\u0006\u0004\b-\u0010.\u001a\u001c\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002¢\u0006\u0004\b0\u00101\u001a\u001c\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0004\b2\u00103\u001a\u001c\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\b4\u001a\u001c\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002¢\u0006\u0004\b5\u00106\u001a\u001c\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004¢\u0006\u0004\b:\u0010;\u001a\u001c\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004¢\u0006\u0004\b<\u0010=\u001a\u001c\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004¢\u0006\u0004\b?\u0010@\u001a\u001c\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004¢\u0006\u0004\bA\u0010B\u001a\u0011\u0010C\u001a\u00020\u0005*\u000208H\u0007¢\u0006\u0002\u0010D\u001a\u0011\u0010C\u001a\u00020\b*\u00020>H\u0007¢\u0006\u0002\u0010E\u001a\u000e\u0010F\u001a\u0004\u0018\u00010\u0005*\u000208H\u0007\u001a\u000e\u0010F\u001a\u0004\u0018\u00010\b*\u00020>H\u0007\u001a\u0011\u0010G\u001a\u00020\u0005*\u000208H\u0007¢\u0006\u0002\u0010D\u001a\u0011\u0010G\u001a\u00020\b*\u00020>H\u0007¢\u0006\u0002\u0010E\u001a\u000e\u0010H\u001a\u0004\u0018\u00010\u0005*\u000208H\u0007\u001a\u000e\u0010H\u001a\u0004\u0018\u00010\b*\u00020>H\u0007\u001a\u0012\u0010I\u001a\u00020\u0005*\u00020%H\u0087\b¢\u0006\u0002\u0010J\u001a\u0019\u0010I\u001a\u00020\u0005*\u00020%2\u0006\u0010I\u001a\u00020KH\u0007¢\u0006\u0002\u0010L\u001a\u0012\u0010I\u001a\u00020\b*\u00020/H\u0087\b¢\u0006\u0002\u0010M\u001a\u0019\u0010I\u001a\u00020\b*\u00020/2\u0006\u0010I\u001a\u00020KH\u0007¢\u0006\u0002\u0010N\u001a\u000f\u0010O\u001a\u0004\u0018\u00010\u0005*\u00020%H\u0087\b\u001a\u0016\u0010O\u001a\u0004\u0018\u00010\u0005*\u00020%2\u0006\u0010I\u001a\u00020KH\u0007\u001a\u000f\u0010O\u001a\u0004\u0018\u00010\b*\u00020/H\u0087\b\u001a\u0016\u0010O\u001a\u0004\u0018\u00010\b*\u00020/2\u0006\u0010I\u001a\u00020KH\u0007\u001a\f\u0010P\u001a\u000208*\u000208H\u0007\u001a\f\u0010P\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010Q\u001a\u000208*\u0002082\u0006\u0010Q\u001a\u00020RH\u0087\u0004\u001a\u0015\u0010Q\u001a\u00020>*\u00020>2\u0006\u0010Q\u001a\u00020SH\u0087\u0004\u001a\u001c\u0010T\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004¢\u0006\u0004\bU\u0010V\u001a\u001c\u0010T\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004¢\u0006\u0004\bW\u0010X\u001a\u001c\u0010T\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004¢\u0006\u0004\bY\u0010Z\u001a\u001c\u0010T\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004¢\u0006\u0004\b[\u0010\\¨\u0006]"}, d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", "range", "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", "value", "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "first", "(Lkotlin/ranges/UIntProgression;)I", "(Lkotlin/ranges/ULongProgression;)J", "firstOrNull", "last", "lastOrNull", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "randomOrNull", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/ranges/URangesKt")
/* loaded from: classes.dex */
public class URangesKt___URangesKt {
    public static final int first(UIntProgression uIntProgression) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        if (uIntProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + uIntProgression + " is empty.");
        }
        return uIntProgression.m1358getFirstpVg5ArA();
    }

    public static final long first(ULongProgression uLongProgression) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        if (uLongProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + uLongProgression + " is empty.");
        }
        return uLongProgression.m1367getFirstsVKNKU();
    }

    public static final UInt firstOrNull(UIntProgression uIntProgression) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        if (uIntProgression.isEmpty()) {
            return null;
        }
        return UInt.m224boximpl(uIntProgression.m1358getFirstpVg5ArA());
    }

    public static final ULong firstOrNull(ULongProgression uLongProgression) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        if (uLongProgression.isEmpty()) {
            return null;
        }
        return ULong.m303boximpl(uLongProgression.m1367getFirstsVKNKU());
    }

    public static final int last(UIntProgression uIntProgression) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        if (uIntProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + uIntProgression + " is empty.");
        }
        return uIntProgression.m1359getLastpVg5ArA();
    }

    public static final long last(ULongProgression uLongProgression) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        if (uLongProgression.isEmpty()) {
            throw new NoSuchElementException("Progression " + uLongProgression + " is empty.");
        }
        return uLongProgression.m1368getLastsVKNKU();
    }

    public static final UInt lastOrNull(UIntProgression uIntProgression) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        if (uIntProgression.isEmpty()) {
            return null;
        }
        return UInt.m224boximpl(uIntProgression.m1359getLastpVg5ArA());
    }

    public static final ULong lastOrNull(ULongProgression uLongProgression) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        if (uLongProgression.isEmpty()) {
            return null;
        }
        return ULong.m303boximpl(uLongProgression.m1368getLastsVKNKU());
    }

    private static final int random(UIntRange uIntRange) {
        Intrinsics.checkNotNullParameter(uIntRange, "<this>");
        return URangesKt.random(uIntRange, Random.Default);
    }

    private static final long random(ULongRange uLongRange) {
        Intrinsics.checkNotNullParameter(uLongRange, "<this>");
        return URangesKt.random(uLongRange, Random.Default);
    }

    public static final int random(UIntRange uIntRange, Random random) {
        Intrinsics.checkNotNullParameter(uIntRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandomKt.nextUInt(random, uIntRange);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final long random(ULongRange uLongRange, Random random) {
        Intrinsics.checkNotNullParameter(uLongRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return URandomKt.nextULong(random, uLongRange);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    private static final UInt randomOrNull(UIntRange uIntRange) {
        Intrinsics.checkNotNullParameter(uIntRange, "<this>");
        return URangesKt.randomOrNull(uIntRange, Random.Default);
    }

    private static final ULong randomOrNull(ULongRange uLongRange) {
        Intrinsics.checkNotNullParameter(uLongRange, "<this>");
        return URangesKt.randomOrNull(uLongRange, Random.Default);
    }

    public static final UInt randomOrNull(UIntRange uIntRange, Random random) {
        Intrinsics.checkNotNullParameter(uIntRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if (uIntRange.isEmpty()) {
            return null;
        }
        return UInt.m224boximpl(URandomKt.nextUInt(random, uIntRange));
    }

    public static final ULong randomOrNull(ULongRange uLongRange, Random random) {
        Intrinsics.checkNotNullParameter(uLongRange, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        if (uLongRange.isEmpty()) {
            return null;
        }
        return ULong.m303boximpl(URandomKt.nextULong(random, uLongRange));
    }

    /* renamed from: contains-biwQdVI, reason: not valid java name */
    private static final boolean m1395containsbiwQdVI(UIntRange contains, UInt uInt) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return uInt != null && contains.m1363containsWZ4Q5Ns(uInt.m282unboximpl());
    }

    /* renamed from: contains-GYNo2lE, reason: not valid java name */
    private static final boolean m1391containsGYNo2lE(ULongRange contains, ULong uLong) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return uLong != null && contains.m1372containsVKZWuLQ(uLong.m361unboximpl());
    }

    /* renamed from: contains-68kG9v0, reason: not valid java name */
    public static final boolean m1390contains68kG9v0(UIntRange contains, byte b) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m1363containsWZ4Q5Ns(UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: contains-ULb-yJY, reason: not valid java name */
    public static final boolean m1393containsULbyJY(ULongRange contains, byte b) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m1372containsVKZWuLQ(ULong.m309constructorimpl(b & 255));
    }

    /* renamed from: contains-Gab390E, reason: not valid java name */
    public static final boolean m1392containsGab390E(ULongRange contains, int i) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m1372containsVKZWuLQ(ULong.m309constructorimpl(i & 4294967295L));
    }

    /* renamed from: contains-fz5IDCE, reason: not valid java name */
    public static final boolean m1396containsfz5IDCE(UIntRange contains, long j) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return ULong.m309constructorimpl(j >>> 32) == 0 && contains.m1363containsWZ4Q5Ns(UInt.m230constructorimpl((int) j));
    }

    /* renamed from: contains-ZsK3CEQ, reason: not valid java name */
    public static final boolean m1394containsZsK3CEQ(UIntRange contains, short s) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m1363containsWZ4Q5Ns(UInt.m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: contains-uhHAxoY, reason: not valid java name */
    public static final boolean m1397containsuhHAxoY(ULongRange contains, short s) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return contains.m1372containsVKZWuLQ(ULong.m309constructorimpl(s & 65535));
    }

    /* renamed from: downTo-Kr8caGY, reason: not valid java name */
    public static final UIntProgression m1400downToKr8caGY(byte b, byte b2) {
        return UIntProgression.Companion.m1360fromClosedRangeNkh28Cs(UInt.m230constructorimpl(b & UByte.MAX_VALUE), UInt.m230constructorimpl(b2 & UByte.MAX_VALUE), -1);
    }

    /* renamed from: downTo-J1ME1BU, reason: not valid java name */
    public static final UIntProgression m1399downToJ1ME1BU(int i, int i2) {
        return UIntProgression.Companion.m1360fromClosedRangeNkh28Cs(i, i2, -1);
    }

    /* renamed from: downTo-eb3DHEI, reason: not valid java name */
    public static final ULongProgression m1401downToeb3DHEI(long j, long j2) {
        return ULongProgression.Companion.m1369fromClosedRange7ftBX0g(j, j2, -1L);
    }

    /* renamed from: downTo-5PvTz6A, reason: not valid java name */
    public static final UIntProgression m1398downTo5PvTz6A(short s, short s2) {
        return UIntProgression.Companion.m1360fromClosedRangeNkh28Cs(UInt.m230constructorimpl(s & UShort.MAX_VALUE), UInt.m230constructorimpl(s2 & UShort.MAX_VALUE), -1);
    }

    public static final UIntProgression reversed(UIntProgression uIntProgression) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        return UIntProgression.Companion.m1360fromClosedRangeNkh28Cs(uIntProgression.m1359getLastpVg5ArA(), uIntProgression.m1358getFirstpVg5ArA(), -uIntProgression.getStep());
    }

    public static final ULongProgression reversed(ULongProgression uLongProgression) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        return ULongProgression.Companion.m1369fromClosedRange7ftBX0g(uLongProgression.m1368getLastsVKNKU(), uLongProgression.m1367getFirstsVKNKU(), -uLongProgression.getStep());
    }

    public static final UIntProgression step(UIntProgression uIntProgression, int i) {
        Intrinsics.checkNotNullParameter(uIntProgression, "<this>");
        RangesKt.checkStepIsPositive(i > 0, Integer.valueOf(i));
        UIntProgression.Companion companion = UIntProgression.Companion;
        int iM1358getFirstpVg5ArA = uIntProgression.m1358getFirstpVg5ArA();
        int iM1359getLastpVg5ArA = uIntProgression.m1359getLastpVg5ArA();
        if (uIntProgression.getStep() <= 0) {
            i = -i;
        }
        return companion.m1360fromClosedRangeNkh28Cs(iM1358getFirstpVg5ArA, iM1359getLastpVg5ArA, i);
    }

    public static final ULongProgression step(ULongProgression uLongProgression, long j) {
        Intrinsics.checkNotNullParameter(uLongProgression, "<this>");
        RangesKt.checkStepIsPositive(j > 0, Long.valueOf(j));
        ULongProgression.Companion companion = ULongProgression.Companion;
        long jM1367getFirstsVKNKU = uLongProgression.m1367getFirstsVKNKU();
        long jM1368getLastsVKNKU = uLongProgression.m1368getLastsVKNKU();
        if (uLongProgression.getStep() <= 0) {
            j = -j;
        }
        return companion.m1369fromClosedRange7ftBX0g(jM1367getFirstsVKNKU, jM1368getLastsVKNKU, j);
    }

    /* renamed from: until-Kr8caGY, reason: not valid java name */
    public static final UIntRange m1404untilKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b2 & UByte.MAX_VALUE, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.m230constructorimpl(b & UByte.MAX_VALUE), UInt.m230constructorimpl(UInt.m230constructorimpl(r3) - 1), null);
    }

    /* renamed from: until-J1ME1BU, reason: not valid java name */
    public static final UIntRange m1403untilJ1ME1BU(int i, int i2) {
        return Integer.compare(i2 ^ Integer.MIN_VALUE, 0 ^ Integer.MIN_VALUE) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(i, UInt.m230constructorimpl(i2 - 1), null);
    }

    /* renamed from: until-eb3DHEI, reason: not valid java name */
    public static final ULongRange m1405untileb3DHEI(long j, long j2) {
        return Long.compare(j2 ^ Long.MIN_VALUE, 0 ^ Long.MIN_VALUE) <= 0 ? ULongRange.Companion.getEMPTY() : new ULongRange(j, ULong.m309constructorimpl(j2 - ULong.m309constructorimpl(1 & 4294967295L)), null);
    }

    /* renamed from: until-5PvTz6A, reason: not valid java name */
    public static final UIntRange m1402until5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s2 & UShort.MAX_VALUE, 0) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(UInt.m230constructorimpl(s & UShort.MAX_VALUE), UInt.m230constructorimpl(UInt.m230constructorimpl(r3) - 1), null);
    }

    /* renamed from: coerceAtLeast-J1ME1BU, reason: not valid java name */
    public static final int m1377coerceAtLeastJ1ME1BU(int i, int i2) {
        return Integer.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) < 0 ? i2 : i;
    }

    /* renamed from: coerceAtLeast-eb3DHEI, reason: not valid java name */
    public static final long m1379coerceAtLeasteb3DHEI(long j, long j2) {
        return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) < 0 ? j2 : j;
    }

    /* renamed from: coerceAtLeast-Kr8caGY, reason: not valid java name */
    public static final byte m1378coerceAtLeastKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & UByte.MAX_VALUE, b2 & UByte.MAX_VALUE) < 0 ? b2 : b;
    }

    /* renamed from: coerceAtLeast-5PvTz6A, reason: not valid java name */
    public static final short m1376coerceAtLeast5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) < 0 ? s2 : s;
    }

    /* renamed from: coerceAtMost-J1ME1BU, reason: not valid java name */
    public static final int m1381coerceAtMostJ1ME1BU(int i, int i2) {
        return Integer.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) > 0 ? i2 : i;
    }

    /* renamed from: coerceAtMost-eb3DHEI, reason: not valid java name */
    public static final long m1383coerceAtMosteb3DHEI(long j, long j2) {
        return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) > 0 ? j2 : j;
    }

    /* renamed from: coerceAtMost-Kr8caGY, reason: not valid java name */
    public static final byte m1382coerceAtMostKr8caGY(byte b, byte b2) {
        return Intrinsics.compare(b & UByte.MAX_VALUE, b2 & UByte.MAX_VALUE) > 0 ? b2 : b;
    }

    /* renamed from: coerceAtMost-5PvTz6A, reason: not valid java name */
    public static final short m1380coerceAtMost5PvTz6A(short s, short s2) {
        return Intrinsics.compare(s & UShort.MAX_VALUE, 65535 & s2) > 0 ? s2 : s;
    }

    /* renamed from: coerceIn-WZ9TVnA, reason: not valid java name */
    public static final int m1386coerceInWZ9TVnA(int i, int i2, int i3) {
        if (Integer.compare(i2 ^ Integer.MIN_VALUE, i3 ^ Integer.MIN_VALUE) <= 0) {
            return Integer.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) < 0 ? i2 : Integer.compare(i ^ Integer.MIN_VALUE, i3 ^ Integer.MIN_VALUE) > 0 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UInt.m276toStringimpl(i3)) + " is less than minimum " + ((Object) UInt.m276toStringimpl(i2)) + '.');
    }

    /* renamed from: coerceIn-sambcqE, reason: not valid java name */
    public static final long m1388coerceInsambcqE(long j, long j2, long j3) {
        if (Long.compare(j2 ^ Long.MIN_VALUE, j3 ^ Long.MIN_VALUE) <= 0) {
            return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) < 0 ? j2 : Long.compare(j ^ Long.MIN_VALUE, j3 ^ Long.MIN_VALUE) > 0 ? j3 : j;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) ULong.m355toStringimpl(j3)) + " is less than minimum " + ((Object) ULong.m355toStringimpl(j2)) + '.');
    }

    /* renamed from: coerceIn-b33U2AM, reason: not valid java name */
    public static final byte m1387coerceInb33U2AM(byte b, byte b2, byte b3) {
        int i = b2 & UByte.MAX_VALUE;
        int i2 = b3 & UByte.MAX_VALUE;
        if (Intrinsics.compare(i, i2) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UByte.m196toStringimpl(b3)) + " is less than minimum " + ((Object) UByte.m196toStringimpl(b2)) + '.');
        }
        int i3 = b & UByte.MAX_VALUE;
        return Intrinsics.compare(i3, i) < 0 ? b2 : Intrinsics.compare(i3, i2) > 0 ? b3 : b;
    }

    /* renamed from: coerceIn-VKSA0NQ, reason: not valid java name */
    public static final short m1385coerceInVKSA0NQ(short s, short s2, short s3) {
        int i = s2 & UShort.MAX_VALUE;
        int i2 = s3 & UShort.MAX_VALUE;
        if (Intrinsics.compare(i, i2) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((Object) UShort.m460toStringimpl(s3)) + " is less than minimum " + ((Object) UShort.m460toStringimpl(s2)) + '.');
        }
        int i3 = 65535 & s;
        return Intrinsics.compare(i3, i) < 0 ? s2 : Intrinsics.compare(i3, i2) > 0 ? s3 : s;
    }

    /* renamed from: coerceIn-wuiCnnA, reason: not valid java name */
    public static final int m1389coerceInwuiCnnA(int i, ClosedRange<UInt> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((UInt) RangesKt.coerceIn(UInt.m224boximpl(i), (ClosedFloatingPointRange<UInt>) range)).m282unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return Integer.compare(i ^ Integer.MIN_VALUE, ((UInt) range.getStart()).m282unboximpl() ^ Integer.MIN_VALUE) < 0 ? ((UInt) range.getStart()).m282unboximpl() : Integer.compare(i ^ Integer.MIN_VALUE, ((UInt) range.getEndInclusive()).m282unboximpl() ^ Integer.MIN_VALUE) > 0 ? ((UInt) range.getEndInclusive()).m282unboximpl() : i;
    }

    /* renamed from: coerceIn-JPwROB0, reason: not valid java name */
    public static final long m1384coerceInJPwROB0(long j, ClosedRange<ULong> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((ULong) RangesKt.coerceIn(ULong.m303boximpl(j), (ClosedFloatingPointRange<ULong>) range)).m361unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        return Long.compare(j ^ Long.MIN_VALUE, ((ULong) range.getStart()).m361unboximpl() ^ Long.MIN_VALUE) < 0 ? ((ULong) range.getStart()).m361unboximpl() : Long.compare(j ^ Long.MIN_VALUE, ((ULong) range.getEndInclusive()).m361unboximpl() ^ Long.MIN_VALUE) > 0 ? ((ULong) range.getEndInclusive()).m361unboximpl() : j;
    }
}
