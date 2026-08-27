package kotlinx.coroutines.flow.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: Merge.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003Bx\u0012B\u0010\u0004\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0005¢\u0006\u0002\b\r\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015ø\u0001\u0000¢\u0006\u0002\u0010\u0016J&\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00010\u00192\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u001f\u0010\u001a\u001a\u00020\u000b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006H\u0094@ø\u0001\u0000¢\u0006\u0002\u0010\u001cRO\u0010\u0004\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0005¢\u0006\u0002\b\rX\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowTransformLatest;", "T", "R", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "transform", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlin/jvm/functions/Function3;Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "Lkotlin/jvm/functions/Function3;", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flowCollect", "collector", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ChannelFlowTransformLatest<T, R> extends ChannelFlowOperator<T, R> {
    private final Function3<FlowCollector<? super R>, T, Continuation<? super Unit>, Object> transform;

    public /* synthetic */ ChannelFlowTransformLatest(Function3 function3, Flow flow, EmptyCoroutineContext emptyCoroutineContext, int i, BufferOverflow bufferOverflow, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(function3, flow, (i2 & 4) != 0 ? EmptyCoroutineContext.INSTANCE : emptyCoroutineContext, (i2 & 8) != 0 ? -2 : i, (i2 & 16) != 0 ? BufferOverflow.SUSPEND : bufferOverflow);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ChannelFlowTransformLatest(Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> function3, Flow<? extends T> flow, CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        super(flow, coroutineContext, i, bufferOverflow);
        this.transform = function3;
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlow
    protected ChannelFlow<R> create(CoroutineContext coroutineContext, int i, BufferOverflow bufferOverflow) {
        return new ChannelFlowTransformLatest(this.transform, this.flow, coroutineContext, i, bufferOverflow);
    }

    /* compiled from: Merge.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3", f = "Merge.kt", i = {}, l = {27}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ FlowCollector<R> $collector;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ChannelFlowTransformLatest<T, R> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass3(ChannelFlowTransformLatest<T, R> channelFlowTransformLatest, FlowCollector<? super R> flowCollector, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.this$0 = channelFlowTransformLatest;
            this.$collector = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, this.$collector, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Ref.ObjectRef objectRef = new Ref.ObjectRef();
                this.label = 1;
                if (this.this$0.flow.collect(new AnonymousClass1(objectRef, coroutineScope, this.this$0, this.$collector), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* compiled from: Merge.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "", "T", "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        /* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1, reason: invalid class name */
        static final class AnonymousClass1<T> implements FlowCollector {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ FlowCollector<R> $collector;
            final /* synthetic */ Ref.ObjectRef<Job> $previousFlow;
            final /* synthetic */ ChannelFlowTransformLatest<T, R> this$0;

            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Ref.ObjectRef<Job> objectRef, CoroutineScope coroutineScope, ChannelFlowTransformLatest<T, R> channelFlowTransformLatest, FlowCollector<? super R> flowCollector) {
                this.$previousFlow = objectRef;
                this.$$this$coroutineScope = coroutineScope;
                this.this$0 = channelFlowTransformLatest;
                this.$collector = flowCollector;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) throws java.lang.Throwable {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1
                    if (r0 == 0) goto L14
                    r0 = r9
                    kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L14
                    int r9 = r0.label
                    int r9 = r9 - r2
                    r0.label = r9
                    goto L19
                L14:
                    kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1
                    r0.<init>(r7, r9)
                L19:
                    java.lang.Object r9 = r0.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L3c
                    if (r2 != r3) goto L34
                    java.lang.Object r8 = r0.L$2
                    kotlinx.coroutines.Job r8 = (kotlinx.coroutines.Job) r8
                    java.lang.Object r8 = r0.L$1
                    java.lang.Object r0 = r0.L$0
                    kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest.AnonymousClass3.AnonymousClass1) r0
                    kotlin.ResultKt.throwOnFailure(r9)
                    goto L61
                L34:
                    java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                    java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                    r8.<init>(r9)
                    throw r8
                L3c:
                    kotlin.ResultKt.throwOnFailure(r9)
                    kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r9 = r7.$previousFlow
                    T r9 = r9.element
                    kotlinx.coroutines.Job r9 = (kotlinx.coroutines.Job) r9
                    if (r9 == 0) goto L60
                    kotlinx.coroutines.flow.internal.ChildCancelledException r2 = new kotlinx.coroutines.flow.internal.ChildCancelledException
                    r2.<init>()
                    java.util.concurrent.CancellationException r2 = (java.util.concurrent.CancellationException) r2
                    r9.cancel(r2)
                    r0.L$0 = r7
                    r0.L$1 = r8
                    r0.L$2 = r9
                    r0.label = r3
                    java.lang.Object r9 = r9.join(r0)
                    if (r9 != r1) goto L60
                    return r1
                L60:
                    r0 = r7
                L61:
                    kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r9 = r0.$previousFlow
                    kotlinx.coroutines.CoroutineScope r1 = r0.$$this$coroutineScope
                    kotlinx.coroutines.CoroutineStart r3 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
                    kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2 r2 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2
                    kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest<T, R> r4 = r0.this$0
                    kotlinx.coroutines.flow.FlowCollector<R> r0 = r0.$collector
                    r5 = 0
                    r2.<init>(r4, r0, r8, r5)
                    r4 = r2
                    kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
                    r5 = 1
                    r6 = 0
                    r2 = 0
                    kotlinx.coroutines.Job r8 = kotlinx.coroutines.BuildersKt.launch$default(r1, r2, r3, r4, r5, r6)
                    r9.element = r8
                    kotlin.Unit r8 = kotlin.Unit.INSTANCE
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest.AnonymousClass3.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            /* compiled from: Merge.kt */
            @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
            @DebugMetadata(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2", f = "Merge.kt", i = {}, l = {34}, m = "invokeSuspend", n = {}, s = {})
            /* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2, reason: invalid class name */
            static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ FlowCollector<R> $collector;
                final /* synthetic */ T $value;
                int label;
                final /* synthetic */ ChannelFlowTransformLatest<T, R> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                AnonymousClass2(ChannelFlowTransformLatest<T, R> channelFlowTransformLatest, FlowCollector<? super R> flowCollector, T t, Continuation<? super AnonymousClass2> continuation) {
                    super(2, continuation);
                    this.this$0 = channelFlowTransformLatest;
                    this.$collector = flowCollector;
                    this.$value = t;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new AnonymousClass2(this.this$0, this.$collector, this.$value, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws Throwable {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Function3 function3 = ((ChannelFlowTransformLatest) this.this$0).transform;
                        FlowCollector<R> flowCollector = this.$collector;
                        T t = this.$value;
                        this.label = 1;
                        if (function3.invoke(flowCollector, t, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }
        }
    }

    @Override // kotlinx.coroutines.flow.internal.ChannelFlowOperator
    protected Object flowCollect(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
        if (DebugKt.getASSERTIONS_ENABLED() && !(flowCollector instanceof SendingCollector)) {
            throw new AssertionError();
        }
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass3(this, flowCollector, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }
}
