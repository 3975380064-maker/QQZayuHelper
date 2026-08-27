package kotlin.sequences;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\bø\u0001\u0000\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001ab\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f2\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00050\u000eH\u0000\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\u000e\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\u000e\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000e\u001a=\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000eH\u0007¢\u0006\u0002\u0010\u0014\u001a+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0017\"\u0002H\u0002¢\u0006\u0002\u0010\u0018\u001a\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\b0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00050\u000eH\u0002¢\u0006\u0002\b\u001c\u001a)\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001d0\u0001H\u0007¢\u0006\u0002\b\u001e\u001a\"\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a&\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010#\u001a\u00020$H\u0007\u001a@\u0010%\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020'\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0'0&\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0&0\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006("}, d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "flatMapIndexed", "R", "C", "source", "transform", "Lkotlin/Function2;", "", "Lkotlin/Function1;", "generateSequence", "", "nextFunction", "seedFunction", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "shuffled", "random", "Lkotlin/random/Random;", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/sequences/SequencesKt")
/* loaded from: classes.dex */
public class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
    private static final <T> Sequence<T> Sequence(final Function0<? extends Iterator<? extends T>> iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return new Sequence<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.Sequence.1
            @Override // kotlin.sequences.Sequence
            public Iterator<T> iterator() {
                return iterator.invoke();
            }
        };
    }

    public static final <T> Sequence<T> asSequence(final Iterator<? extends T> it) {
        Intrinsics.checkNotNullParameter(it, "<this>");
        return SequencesKt.constrainOnce(new Sequence<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<T> iterator() {
                return it;
            }
        });
    }

    public static final <T> Sequence<T> sequenceOf(T... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return ArraysKt.asSequence(elements);
    }

    public static final <T> Sequence<T> emptySequence() {
        return EmptySequence.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T> Sequence<T> orEmpty(Sequence<? extends T> sequence) {
        return sequence == 0 ? SequencesKt.emptySequence() : sequence;
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1", f = "Sequences.kt", i = {}, l = {69, 71}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1, reason: invalid class name and case insensitive filesystem */
    static final class C00421<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function0<Sequence<T>> $defaultValue;
        final /* synthetic */ Sequence<T> $this_ifEmpty;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C00421(Sequence<? extends T> sequence, Function0<? extends Sequence<? extends T>> function0, Continuation<? super C00421> continuation) {
            super(2, continuation);
            this.$this_ifEmpty = sequence;
            this.$defaultValue = function0;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00421 c00421 = new C00421(this.$this_ifEmpty, this.$defaultValue, continuation);
            c00421.L$0 = obj;
            return c00421;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super T> sequenceScope, Continuation<? super Unit> continuation) {
            return ((C00421) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
        
            if (r6.yieldAll(r1, r5) == r0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
        
            if (r6.yieldAll(r5.$defaultValue.invoke(), r5) == r0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) throws java.lang.Throwable {
            /*
                r5 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1b
                if (r1 == r3) goto L17
                if (r1 != r2) goto Lf
                goto L17
            Lf:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r0)
                throw r6
            L17:
                kotlin.ResultKt.throwOnFailure(r6)
                goto L4e
            L1b:
                kotlin.ResultKt.throwOnFailure(r6)
                java.lang.Object r6 = r5.L$0
                kotlin.sequences.SequenceScope r6 = (kotlin.sequences.SequenceScope) r6
                kotlin.sequences.Sequence<T> r1 = r5.$this_ifEmpty
                java.util.Iterator r1 = r1.iterator()
                boolean r4 = r1.hasNext()
                if (r4 == 0) goto L3a
                r2 = r5
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r5.label = r3
                java.lang.Object r6 = r6.yieldAll(r1, r2)
                if (r6 != r0) goto L4e
                goto L4d
            L3a:
                kotlin.jvm.functions.Function0<kotlin.sequences.Sequence<T>> r1 = r5.$defaultValue
                java.lang.Object r1 = r1.invoke()
                kotlin.sequences.Sequence r1 = (kotlin.sequences.Sequence) r1
                r3 = r5
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                r5.label = r2
                java.lang.Object r6 = r6.yieldAll(r1, r3)
                if (r6 != r0) goto L4e
            L4d:
                return r0
            L4e:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt__SequencesKt.C00421.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static final <T> Sequence<T> ifEmpty(Sequence<? extends T> sequence, Function0<? extends Sequence<? extends T>> defaultValue) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        return SequencesKt.sequence(new C00421(sequence, defaultValue, null));
    }

    public static final <T> Sequence<T> flatten(Sequence<? extends Sequence<? extends T>> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return flatten$SequencesKt__SequencesKt(sequence, new Function1<Sequence<? extends T>, Iterator<? extends T>>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.1
            @Override // kotlin.jvm.functions.Function1
            public final Iterator<T> invoke(Sequence<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.iterator();
            }
        });
    }

    public static final <T> Sequence<T> flattenSequenceOfIterable(Sequence<? extends Iterable<? extends T>> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return flatten$SequencesKt__SequencesKt(sequence, new Function1<Iterable<? extends T>, Iterator<? extends T>>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.2
            @Override // kotlin.jvm.functions.Function1
            public final Iterator<T> invoke(Iterable<? extends T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.iterator();
            }
        });
    }

    private static final <T, R> Sequence<R> flatten$SequencesKt__SequencesKt(Sequence<? extends T> sequence, Function1<? super T, ? extends Iterator<? extends R>> function1) {
        if (sequence instanceof TransformingSequence) {
            return ((TransformingSequence) sequence).flatten$kotlin_stdlib(function1);
        }
        return new FlatteningSequence(sequence, new Function1<T, T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.flatten.3
            @Override // kotlin.jvm.functions.Function1
            public final T invoke(T t) {
                return t;
            }
        }, function1);
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(Sequence<? extends Pair<? extends T, ? extends R>> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Pair<? extends T, ? extends R> pair : sequence) {
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    public static final <T> Sequence<T> shuffled(Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return SequencesKt.shuffled(sequence, Random.Default);
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$shuffled$1", f = "Sequences.kt", i = {0, 0}, l = {145}, m = "invokeSuspend", n = {"$this$sequence", "buffer"}, s = {"L$0", "L$1"})
    /* renamed from: kotlin.sequences.SequencesKt__SequencesKt$shuffled$1, reason: invalid class name and case insensitive filesystem */
    static final class C00431<T> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Random $random;
        final /* synthetic */ Sequence<T> $this_shuffled;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C00431(Sequence<? extends T> sequence, Random random, Continuation<? super C00431> continuation) {
            super(2, continuation);
            this.$this_shuffled = sequence;
            this.$random = random;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00431 c00431 = new C00431(this.$this_shuffled, this.$random, continuation);
            c00431.L$0 = obj;
            return c00431;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super T> sequenceScope, Continuation<? super Unit> continuation) {
            return ((C00431) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            List mutableList;
            SequenceScope sequenceScope;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                mutableList = SequencesKt.toMutableList(this.$this_shuffled);
                sequenceScope = sequenceScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutableList = (List) this.L$1;
                SequenceScope sequenceScope3 = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                sequenceScope = sequenceScope3;
            }
            while (!mutableList.isEmpty()) {
                int iNextInt = this.$random.nextInt(mutableList.size());
                Object objRemoveLast = CollectionsKt.removeLast(mutableList);
                if (iNextInt < mutableList.size()) {
                    objRemoveLast = mutableList.set(iNextInt, objRemoveLast);
                }
                this.L$0 = sequenceScope;
                this.L$1 = mutableList;
                this.label = 1;
                if (sequenceScope.yield(objRemoveLast, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    public static final <T> Sequence<T> shuffled(Sequence<? extends T> sequence, Random random) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        Intrinsics.checkNotNullParameter(random, "random");
        return SequencesKt.sequence(new C00431(sequence, random, null));
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* compiled from: Sequences.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u008a@"}, d2 = {"<anonymous>", "", "T", "C", "R", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$flatMapIndexed$1", f = "Sequences.kt", i = {0, 0}, l = {350}, m = "invokeSuspend", n = {"$this$sequence", "index"}, s = {"L$0", "I$0"})
    /* renamed from: kotlin.sequences.SequencesKt__SequencesKt$flatMapIndexed$1, reason: invalid class name and case insensitive filesystem */
    static final class C00381<R> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function1<C, Iterator<R>> $iterator;
        final /* synthetic */ Sequence<T> $source;
        final /* synthetic */ Function2<Integer, T, C> $transform;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C00381(Sequence<? extends T> sequence, Function2<? super Integer, ? super T, ? extends C> function2, Function1<? super C, ? extends Iterator<? extends R>> function1, Continuation<? super C00381> continuation) {
            super(2, continuation);
            this.$source = sequence;
            this.$transform = function2;
            this.$iterator = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00381 c00381 = new C00381(this.$source, this.$transform, this.$iterator, continuation);
            c00381.L$0 = obj;
            return c00381;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super R> sequenceScope, Continuation<? super Unit> continuation) {
            return ((C00381) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            SequenceScope sequenceScope;
            int i;
            Iterator it;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                sequenceScope = (SequenceScope) this.L$0;
                i = 0;
                it = this.$source.iterator();
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i3 = this.I$0;
                it = (Iterator) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                i = i3;
            }
            while (it.hasNext()) {
                Object next = it.next();
                Function2<Integer, T, C> function2 = this.$transform;
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Object objInvoke = function2.invoke(Boxing.boxInt(i), next);
                this.L$0 = sequenceScope;
                this.L$1 = it;
                this.I$0 = i4;
                this.label = 1;
                if (sequenceScope.yieldAll(this.$iterator.invoke(objInvoke), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                i = i4;
            }
            return Unit.INSTANCE;
        }
    }

    public static final <T, C, R> Sequence<R> flatMapIndexed(Sequence<? extends T> source, Function2<? super Integer, ? super T, ? extends C> transform, Function1<? super C, ? extends Iterator<? extends R>> iterator) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(transform, "transform");
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        return SequencesKt.sequence(new C00381(source, transform, iterator, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Sequence<T> constrainOnce(Sequence<? extends T> sequence) {
        Intrinsics.checkNotNullParameter(sequence, "<this>");
        return sequence instanceof ConstrainedOnceSequence ? sequence : new ConstrainedOnceSequence(sequence);
    }

    public static final <T> Sequence<T> generateSequence(final Function0<? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return SequencesKt.constrainOnce(new GeneratorSequence(nextFunction, new Function1<T, T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final T invoke(T it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return nextFunction.invoke();
            }
        }));
    }

    public static final <T> Sequence<T> generateSequence(final T t, Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        if (t == null) {
            return EmptySequence.INSTANCE;
        }
        return new GeneratorSequence(new Function0<T>() { // from class: kotlin.sequences.SequencesKt__SequencesKt.generateSequence.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final T invoke() {
                return t;
            }
        }, nextFunction);
    }

    public static final <T> Sequence<T> generateSequence(Function0<? extends T> seedFunction, Function1<? super T, ? extends T> nextFunction) {
        Intrinsics.checkNotNullParameter(seedFunction, "seedFunction");
        Intrinsics.checkNotNullParameter(nextFunction, "nextFunction");
        return new GeneratorSequence(seedFunction, nextFunction);
    }
}
