package kotlinx.coroutines.debug.internal;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.Job;

/*  JADX ERROR: Class process error: OutOfMemoryError
    java.lang.OutOfMemoryError: Failed to allocate a 32 byte allocation with 221016 free bytes and 215KB until OOM, target footprint 536870912, growth limit 536870912; giving up on allocation because <1% of heap free after GC.
    	at java.util.ArrayList.iterator(ArrayList.java:834)
    	at jadx.core.dex.nodes.InsnNode.getRegisterArgs(InsnNode.java:199)
    	at jadx.core.dex.visitors.CheckCode.checkInstructions(CheckCode.java:72)
    	at jadx.core.dex.visitors.CheckCode.visit(CheckCode.java:33)
    */
/* compiled from: DebugProbesImpl.kt */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\bÀ\u0002\u0018\u00002\u00020\u0014:\u0002\u0095\u0001B\t\b\u0002¢\u0006\u0004\b\u0001\u0010\u0002J3\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0004\b\u0015\u0010\u0016J@\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\"\b\b\u0000\u0010\u0017*\u00020\u00142\u001e\b\u0004\u0010\u001b\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00028\u00000\u0018H\u0082\b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u001e\u0010\u000eJ\u0013\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f¢\u0006\u0004\b \u0010\u0012J)\u0010$\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\u0006\u0010!\u001a\u00020\u00102\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000f¢\u0006\u0004\b$\u0010%J\u0015\u0010'\u001a\u00020&2\u0006\u0010!\u001a\u00020\u0010¢\u0006\u0004\b'\u0010(J5\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0\u000f2\u0006\u0010)\u001a\u00020&2\b\u0010+\u001a\u0004\u0018\u00010*2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b,\u0010-J?\u00102\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020.012\u0006\u0010/\u001a\u00020.2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"0\u00132\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b2\u00103J3\u00105\u001a\u00020.2\u0006\u00104\u001a\u00020.2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"0\u00132\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\b5\u00106J\u001d\u00109\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\f\u0018\u000107H\u0002¢\u0006\u0004\b9\u0010:J\u0015\u0010=\u001a\u00020&2\u0006\u0010<\u001a\u00020;¢\u0006\u0004\b=\u0010>J\r\u0010?\u001a\u00020\f¢\u0006\u0004\b?\u0010\u0002J%\u0010A\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\bA\u0010BJ\u001b\u0010D\u001a\u00020\f2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u0019H\u0002¢\u0006\u0004\bD\u0010EJ)\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010K\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bI\u0010JJ\u001b\u0010M\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bL\u0010JJ'\u0010P\u001a\b\u0012\u0004\u0012\u00020\"0\u000f\"\b\b\u0000\u0010\u0003*\u00020N2\u0006\u0010O\u001a\u00028\u0000H\u0002¢\u0006\u0004\bP\u0010QJ\u000f\u0010R\u001a\u00020\fH\u0002¢\u0006\u0004\bR\u0010\u0002J\u000f\u0010S\u001a\u00020\fH\u0002¢\u0006\u0004\bS\u0010\u0002J\r\u0010T\u001a\u00020\f¢\u0006\u0004\bT\u0010\u0002J\u001f\u0010V\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020U2\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bV\u0010WJ#\u0010X\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bX\u0010YJ/\u0010X\u001a\u00020\f2\n\u0010C\u001a\u0006\u0012\u0002\b\u00030\u00192\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010)\u001a\u00020&H\u0002¢\u0006\u0004\bX\u0010ZJ;\u0010b\u001a\u00020\f*\u00020;2\u0012\u0010]\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020\\0[2\n\u0010`\u001a\u00060^j\u0002`_2\u0006\u0010a\u001a\u00020&H\u0002¢\u0006\u0004\bb\u0010cJ\u0017\u0010d\u001a\u000208*\u0006\u0012\u0002\b\u00030\u0019H\u0002¢\u0006\u0004\bd\u0010eJ\u001d\u0010C\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0019*\u0006\u0012\u0002\b\u00030\u0004H\u0002¢\u0006\u0004\bC\u0010fJ\u001a\u0010C\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0019*\u00020UH\u0082\u0010¢\u0006\u0004\bC\u0010gJ\u0016\u0010h\u001a\u0004\u0018\u00010U*\u00020UH\u0082\u0010¢\u0006\u0004\bh\u0010iJ\u001b\u0010j\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020\"0\u000fH\u0002¢\u0006\u0004\bj\u0010kJ\u0013\u0010l\u001a\u00020&*\u00020\u0014H\u0002¢\u0006\u0004\bl\u0010mR\u0014\u0010n\u001a\u00020&8\u0002X\u0082T¢\u0006\u0006\n\u0004\bn\u0010oR \u0010q\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\\0p8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bq\u0010rR\u001e\u0010v\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190s8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bt\u0010uR$\u0010w\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u0002080p8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\bw\u0010rR\u0014\u0010y\u001a\u00020x8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\by\u0010zR\u0014\u0010|\u001a\u00020{8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b|\u0010}R\"\u0010~\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u00020\f\u0018\u0001078\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b~\u0010\u007fR)\u0010\u0080\u0001\u001a\u0002088\u0006@\u0006X\u0086\u000e¢\u0006\u0018\n\u0006\b\u0080\u0001\u0010\u0081\u0001\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\"\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0019\u0010\u0086\u0001\u001a\u00020.8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0017\u0010\u0089\u0001\u001a\u0002088@X\u0080\u0004¢\u0006\b\u001a\u0006\b\u0088\u0001\u0010\u0083\u0001R)\u0010\u008a\u0001\u001a\u0002088\u0006@\u0006X\u0086\u000e¢\u0006\u0018\n\u0006\b\u008a\u0001\u0010\u0081\u0001\u001a\u0006\b\u008b\u0001\u0010\u0083\u0001\"\u0006\b\u008c\u0001\u0010\u0085\u0001R\u001b\u0010\u008d\u0001\u001a\u0004\u0018\u00010*8\u0002@\u0002X\u0082\u000e¢\u0006\b\n\u0006\b\u008d\u0001\u0010\u008e\u0001R\"\u0010\u0092\u0001\u001a\u00020&*\u00020;8BX\u0082\u0004¢\u0006\u000f\u0012\u0006\b\u0090\u0001\u0010\u0091\u0001\u001a\u0005\b\u008f\u0001\u0010>R\u001b\u0010\u0093\u0001\u001a\u000208*\u00020\"8BX\u0082\u0004¢\u0006\b\u001a\u0006\b\u0093\u0001\u0010\u0094\u0001¨\u0006\u0096\u0001"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "<init>", "()V", "T", "Lkotlin/coroutines/Continuation;", "completion", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "frame", "createOwner", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/StackTraceFrame;)Lkotlin/coroutines/Continuation;", "Ljava/io/PrintStream;", "out", "", "dumpCoroutines", "(Ljava/io/PrintStream;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "dumpCoroutinesInfo", "()Ljava/util/List;", "", "", "dumpCoroutinesInfoAsJsonAndReferences", "()[Ljava/lang/Object;", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "Lkotlin/coroutines/CoroutineContext;", "create", "dumpCoroutinesInfoImpl", "(Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "dumpCoroutinesSynchronized", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpDebuggerInfo", "info", "Ljava/lang/StackTraceElement;", "coroutineTrace", "enhanceStackTraceWithThreadDump", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;Ljava/util/List;)Ljava/util/List;", "", "enhanceStackTraceWithThreadDumpAsJson", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;)Ljava/lang/String;", "state", "Ljava/lang/Thread;", "thread", "enhanceStackTraceWithThreadDumpImpl", "(Ljava/lang/String;Ljava/lang/Thread;Ljava/util/List;)Ljava/util/List;", "", "indexOfResumeWith", "actualTrace", "Lkotlin/Pair;", "findContinuationStartIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "frameIndex", "findIndexOfFrame", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "Lkotlin/Function1;", "", "getDynamicAttach", "()Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/Job;", "job", "hierarchyToString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "install", "frames", "printStackTrace", "(Ljava/io/PrintStream;Ljava/util/List;)V", "owner", "probeCoroutineCompleted", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)V", "probeCoroutineCreated$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "probeCoroutineCreated", "probeCoroutineResumed$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)V", "probeCoroutineResumed", "probeCoroutineSuspended$kotlinx_coroutines_core", "probeCoroutineSuspended", "", "throwable", "sanitizeStackTrace", "(Ljava/lang/Throwable;)Ljava/util/List;", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "uninstall", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateRunningState", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/String;)V", "updateState", "(Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "map", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "builder", "indent", "build", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "isFinished", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Z", "(Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "realCaller", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "toStackTraceFrame", "(Ljava/util/List;)Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "toStringWithQuotes", "(Ljava/lang/Object;)Ljava/lang/String;", "ARTIFICIAL_FRAME_MESSAGE", "Ljava/lang/String;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "callerInfoCache", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "getCapturedCoroutines", "()Ljava/util/Set;", "capturedCoroutines", "capturedCoroutinesMap", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "coroutineStateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Ljava/text/SimpleDateFormat;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dynamicAttach", "Lkotlin/jvm/functions/Function1;", "enableCreationStackTraces", "Z", "getEnableCreationStackTraces", "()Z", "setEnableCreationStackTraces", "(Z)V", "installations", "I", "isInstalled$kotlinx_coroutines_core", "isInstalled", "sanitizeStackTraces", "getSanitizeStackTraces", "setSanitizeStackTraces", "weakRefCleanerThread", "Ljava/lang/Thread;", "getDebugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "debugString", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class DebugProbesImpl {
    private static final String ARTIFICIAL_FRAME_MESSAGE = "Coroutine creation stacktrace";
    public static final DebugProbesImpl INSTANCE = null;
    private static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> callerInfoCache = null;
    private static final ConcurrentWeakMap<CoroutineOwner<?>, Boolean> capturedCoroutinesMap = null;
    private static final ReentrantReadWriteLock coroutineStateLock = null;
    private static final SimpleDateFormat dateFormat = null;
    private static final /* synthetic */ SequenceNumberRefVolatile debugProbesImpl$SequenceNumberRefVolatile = null;
    private static final Function1<Boolean, Unit> dynamicAttach = null;
    private static boolean enableCreationStackTraces;
    private static volatile int installations;
    private static boolean sanitizeStackTraces;
    private static final /* synthetic */ AtomicLongFieldUpdater sequenceNumber$FU = null;
    private static Thread weakRefCleanerThread;

    /* compiled from: DebugProbesImpl.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001e\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "T", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "frame", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    private static final class CoroutineOwner<T> implements Continuation<T>, CoroutineStackFrame {
        public final Continuation<T> delegate;
        private final CoroutineStackFrame frame;
        public final DebugCoroutineInfoImpl info;

        public CoroutineOwner(Continuation<? super T> v, DebugCoroutineInfoImpl v2, CoroutineStackFrame v3) {
            /*
                this = this;
                r0.<init>()
                r0.delegate = r1
                r0.info = r2
                r0.frame = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.<init>(kotlin.coroutines.Continuation, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl, kotlin.coroutines.jvm.internal.CoroutineStackFrame):void");
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        public CoroutineStackFrame getCallerFrame() {
            /*
                this = this;
                kotlin.coroutines.jvm.internal.CoroutineStackFrame r0 = r1.frame
                if (r0 == 0) goto L9
                kotlin.coroutines.jvm.internal.CoroutineStackFrame r0 = r0.getCallerFrame()
                return r0
            L9:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.getCallerFrame():kotlin.coroutines.jvm.internal.CoroutineStackFrame");
        }

        @Override // kotlin.coroutines.Continuation
        public CoroutineContext getContext() {
            /*
                this = this;
                kotlin.coroutines.Continuation<T> r0 = r1.delegate
                kotlin.coroutines.CoroutineContext r0 = r0.getContext()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.getContext():kotlin.coroutines.CoroutineContext");
        }

        @Override // kotlin.coroutines.jvm.internal.CoroutineStackFrame
        public StackTraceElement getStackTraceElement() {
            /*
                this = this;
                kotlin.coroutines.jvm.internal.CoroutineStackFrame r0 = r1.frame
                if (r0 == 0) goto L9
                java.lang.StackTraceElement r0 = r0.getStackTraceElement()
                return r0
            L9:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.getStackTraceElement():java.lang.StackTraceElement");
        }

        @Override // kotlin.coroutines.Continuation
        public void resumeWith(Object v) {
            /*
                this = this;
                kotlinx.coroutines.debug.internal.DebugProbesImpl r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE
                kotlinx.coroutines.debug.internal.DebugProbesImpl.access$probeCoroutineCompleted(r0, r1)
                kotlin.coroutines.Continuation<T> r0 = r1.delegate
                r0.resumeWith(r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.resumeWith(java.lang.Object):void");
        }

        public String toString() {
            /*
                this = this;
                kotlin.coroutines.Continuation<T> r0 = r1.delegate
                java.lang.String r0 = r0.toString()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner.toString():java.lang.String");
        }
    }

    static {
        /*
            kotlinx.coroutines.debug.internal.DebugProbesImpl r0 = new kotlinx.coroutines.debug.internal.DebugProbesImpl
            r0.<init>()
            kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE = r0
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.lang.String r2 = "yyyy/MM/dd HH:mm:ss"
            r1.<init>(r2)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.dateFormat = r1
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap r1 = new kotlinx.coroutines.debug.internal.ConcurrentWeakMap
            r2 = 0
            r3 = 0
            r4 = 1
            r1.<init>(r3, r4, r2)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap = r1
            kotlinx.coroutines.debug.internal.DebugProbesImpl$SequenceNumberRefVolatile r1 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$SequenceNumberRefVolatile
            r2 = 0
            r1.<init>(r2)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.debugProbesImpl$SequenceNumberRefVolatile = r1
            java.util.concurrent.locks.ReentrantReadWriteLock r1 = new java.util.concurrent.locks.ReentrantReadWriteLock
            r1.<init>()
            kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock = r1
            kotlinx.coroutines.debug.internal.DebugProbesImpl.sanitizeStackTraces = r4
            kotlinx.coroutines.debug.internal.DebugProbesImpl.enableCreationStackTraces = r4
            kotlin.jvm.functions.Function1 r0 = r0.getDynamicAttach()
            kotlinx.coroutines.debug.internal.DebugProbesImpl.dynamicAttach = r0
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap r0 = new kotlinx.coroutines.debug.internal.ConcurrentWeakMap
            r0.<init>(r4)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.callerInfoCache = r0
            java.lang.Class<kotlinx.coroutines.debug.internal.DebugProbesImpl$SequenceNumberRefVolatile> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.SequenceNumberRefVolatile.class
            java.lang.String r1 = "sequenceNumber"
            java.util.concurrent.atomic.AtomicLongFieldUpdater r0 = java.util.concurrent.atomic.AtomicLongFieldUpdater.newUpdater(r0, r1)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.sequenceNumber$FU = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.<clinit>():void");
    }

    private DebugProbesImpl() {
        /*
            this = this;
            r0.<init>()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.<init>():void");
    }

    public static final /* synthetic */ ConcurrentWeakMap access$getCallerInfoCache$p() {
        /*
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.callerInfoCache
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.access$getCallerInfoCache$p():kotlinx.coroutines.debug.internal.ConcurrentWeakMap");
    }

    public static final /* synthetic */ boolean access$isFinished(DebugProbesImpl v, CoroutineOwner v2) {
        /*
            boolean r0 = r0.isFinished(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.access$isFinished(kotlinx.coroutines.debug.internal.DebugProbesImpl, kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner):boolean");
    }

    public static final /* synthetic */ void access$probeCoroutineCompleted(DebugProbesImpl v, CoroutineOwner v2) {
        /*
            r0.probeCoroutineCompleted(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.access$probeCoroutineCompleted(kotlinx.coroutines.debug.internal.DebugProbesImpl, kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner):void");
    }

    private final void build(Job v, Map<Job, DebugCoroutineInfoImpl> v2, StringBuilder v3, String v4) {
        /*
            this = this;
            java.lang.Object r0 = r8.get(r7)
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r0 = (kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl) r0
            r1 = 9
            r2 = 10
            if (r0 != 0) goto L3e
            boolean r0 = r7 instanceof kotlinx.coroutines.internal.ScopeCoroutine
            if (r0 != 0) goto L8d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r3 = r6.getDebugString(r7)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r9.append(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r10 = r0.append(r10)
            java.lang.StringBuilder r10 = r10.append(r1)
            java.lang.String r10 = r10.toString()
            goto L8d
        L3e:
            java.util.List r3 = r0.lastObservedStackTrace()
            java.lang.Object r3 = kotlin.collections.CollectionsKt.firstOrNull(r3)
            java.lang.StackTraceElement r3 = (java.lang.StackTraceElement) r3
            java.lang.String r0 = r0.getState()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r4 = r4.append(r10)
            java.lang.String r5 = r6.getDebugString(r7)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ", continuation is "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r4 = " at line "
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r9.append(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r10 = r0.append(r10)
            java.lang.StringBuilder r10 = r10.append(r1)
            java.lang.String r10 = r10.toString()
        L8d:
            kotlin.sequences.Sequence r7 = r7.getChildren()
            java.util.Iterator r7 = r7.iterator()
        L95:
            boolean r0 = r7.hasNext()
            if (r0 == 0) goto La5
            java.lang.Object r0 = r7.next()
            kotlinx.coroutines.Job r0 = (kotlinx.coroutines.Job) r0
            r6.build(r0, r8, r9, r10)
            goto L95
        La5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.build(kotlinx.coroutines.Job, java.util.Map, java.lang.StringBuilder, java.lang.String):void");
    }

    private final <T> Continuation<T> createOwner(Continuation<? super T> v, StackTraceFrame v2) {
        /*
            this = this;
            boolean r0 = r4.isInstalled$kotlinx_coroutines_core()
            if (r0 != 0) goto L7
            return r5
        L7:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r0 = new kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl
            kotlin.coroutines.CoroutineContext r1 = r5.getContext()
            java.util.concurrent.atomic.AtomicLongFieldUpdater r2 = kotlinx.coroutines.debug.internal.DebugProbesImpl.sequenceNumber$FU
            kotlinx.coroutines.debug.internal.DebugProbesImpl$SequenceNumberRefVolatile r3 = kotlinx.coroutines.debug.internal.DebugProbesImpl.debugProbesImpl$SequenceNumberRefVolatile
            long r2 = r2.incrementAndGet(r3)
            r0.<init>(r1, r6, r2)
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r1 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r6 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r6
            r1.<init>(r5, r0, r6)
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap
            r6 = r5
            java.util.Map r6 = (java.util.Map) r6
            r0 = 1
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r6.put(r1, r0)
            boolean r6 = r4.isInstalled$kotlinx_coroutines_core()
            if (r6 != 0) goto L35
            r5.clear()
        L35:
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.createOwner(kotlin.coroutines.Continuation, kotlinx.coroutines.debug.internal.StackTraceFrame):kotlin.coroutines.Continuation");
    }

    private final <R> List<R> dumpCoroutinesInfoImpl(Function2<? super CoroutineOwner<?>, ? super CoroutineContext, ? extends R> v) {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L67
            boolean r5 = r4.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L67
            if (r5 == 0) goto L5b
            java.util.Set r4 = r4.getCapturedCoroutines()     // Catch: java.lang.Throwable -> L67
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.collections.CollectionsKt.asSequence(r4)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            java.util.Comparator r5 = (java.util.Comparator) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.sortedWith(r4, r5)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$1$3 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$1$3     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r7 = kotlin.sequences.SequencesKt.mapNotNull(r4, r5)     // Catch: java.lang.Throwable -> L67
            java.util.List r7 = kotlin.sequences.SequencesKt.toList(r7)     // Catch: java.lang.Throwable -> L67
        L4f:
            if (r3 >= r2) goto L57
            r1.lock()
            int r3 = r3 + 1
            goto L4f
        L57:
            r0.unlock()
            return r7
        L5b:
            java.lang.String r7 = "Debug probes are not installed"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L67
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L67
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L67
            throw r4     // Catch: java.lang.Throwable -> L67
        L67:
            r7 = move-exception
        L68:
            if (r3 >= r2) goto L70
            r1.lock()
            int r3 = r3 + 1
            goto L68
        L70:
            r0.unlock()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfoImpl(kotlin.jvm.functions.Function2):java.util.List");
    }

    private final void dumpCoroutinesSynchronized(PrintStream v) {
        /*
            Method dump skipped, instructions count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesSynchronized(java.io.PrintStream):void");
    }

    private final List<StackTraceElement> enhanceStackTraceWithThreadDumpImpl(String v, Thread v2, List<StackTraceElement> v3) {
        /*
            this = this;
            java.lang.String r0 = "RUNNING"
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            if (r7 == 0) goto Lb2
            if (r8 != 0) goto Lc
            goto Lb2
        Lc:
            kotlin.Result$Companion r7 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L1a
            r7 = r6
            kotlinx.coroutines.debug.internal.DebugProbesImpl r7 = (kotlinx.coroutines.debug.internal.DebugProbesImpl) r7     // Catch: java.lang.Throwable -> L1a
            java.lang.StackTraceElement[] r7 = r8.getStackTrace()     // Catch: java.lang.Throwable -> L1a
            java.lang.Object r7 = kotlin.Result.m134constructorimpl(r7)     // Catch: java.lang.Throwable -> L1a
            goto L25
        L1a:
            r7 = move-exception
            kotlin.Result$Companion r8 = kotlin.Result.Companion
            java.lang.Object r7 = kotlin.ResultKt.createFailure(r7)
            java.lang.Object r7 = kotlin.Result.m134constructorimpl(r7)
        L25:
            boolean r8 = kotlin.Result.m140isFailureimpl(r7)
            if (r8 == 0) goto L2c
            r7 = 0
        L2c:
            java.lang.StackTraceElement[] r7 = (java.lang.StackTraceElement[]) r7
            if (r7 != 0) goto L32
            goto Lb2
        L32:
            int r8 = r7.length
            r0 = 0
            r1 = r0
        L35:
            r2 = -1
            if (r1 >= r8) goto L62
            r3 = r7[r1]
            java.lang.String r4 = r3.getClassName()
            java.lang.String r5 = "kotlin.coroutines.jvm.internal.BaseContinuationImpl"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L5f
            java.lang.String r4 = r3.getMethodName()
            java.lang.String r5 = "resumeWith"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L5f
            java.lang.String r3 = r3.getFileName()
            java.lang.String r4 = "ContinuationImpl.kt"
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 == 0) goto L5f
            goto L63
        L5f:
            int r1 = r1 + 1
            goto L35
        L62:
            r1 = r2
        L63:
            kotlin.Pair r8 = r6.findContinuationStartIndex(r1, r7, r9)
            java.lang.Object r3 = r8.component1()
            java.lang.Number r3 = (java.lang.Number) r3
            int r3 = r3.intValue()
            java.lang.Object r8 = r8.component2()
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            if (r3 != r2) goto L7e
            goto Lb2
        L7e:
            int r2 = r9.size()
            int r2 = r2 + r1
            int r2 = r2 - r3
            int r2 = r2 + (-1)
            int r2 = r2 - r8
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>(r2)
            int r1 = r1 - r8
        L8d:
            if (r0 >= r1) goto L9a
            r8 = r4
            java.util.Collection r8 = (java.util.Collection) r8
            r2 = r7[r0]
            r8.add(r2)
            int r0 = r0 + 1
            goto L8d
        L9a:
            int r3 = r3 + 1
            int r7 = r9.size()
        La0:
            if (r3 >= r7) goto Laf
            r8 = r4
            java.util.Collection r8 = (java.util.Collection) r8
            java.lang.Object r0 = r9.get(r3)
            r8.add(r0)
            int r3 = r3 + 1
            goto La0
        Laf:
            java.util.List r4 = (java.util.List) r4
            return r4
        Lb2:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.enhanceStackTraceWithThreadDumpImpl(java.lang.String, java.lang.Thread, java.util.List):java.util.List");
    }

    private final Pair<Integer, Integer> findContinuationStartIndex(int v, StackTraceElement[] v2, List<StackTraceElement> v3) {
        /*
            this = this;
            r0 = 0
            r1 = r0
        L2:
            r2 = -1
            r3 = 3
            if (r1 >= r3) goto L21
            kotlinx.coroutines.debug.internal.DebugProbesImpl r3 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE
            int r4 = r6 + (-1)
            int r4 = r4 - r1
            int r3 = r3.findIndexOfFrame(r4, r7, r8)
            if (r3 == r2) goto L1e
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)
            kotlin.Pair r6 = kotlin.TuplesKt.to(r6, r7)
            return r6
        L1e:
            int r1 = r1 + 1
            goto L2
        L21:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            java.lang.Integer r7 = java.lang.Integer.valueOf(r0)
            kotlin.Pair r6 = kotlin.TuplesKt.to(r6, r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.findContinuationStartIndex(int, java.lang.StackTraceElement[], java.util.List):kotlin.Pair");
    }

    private final int findIndexOfFrame(int v, StackTraceElement[] v2, List<StackTraceElement> v3) {
        /*
            this = this;
            java.lang.Object r5 = kotlin.collections.ArraysKt.getOrNull(r6, r5)
            java.lang.StackTraceElement r5 = (java.lang.StackTraceElement) r5
            r6 = -1
            if (r5 != 0) goto La
            return r6
        La:
            java.util.Iterator r7 = r7.iterator()
            r0 = 0
        Lf:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L49
            java.lang.Object r1 = r7.next()
            java.lang.StackTraceElement r1 = (java.lang.StackTraceElement) r1
            java.lang.String r2 = r1.getFileName()
            java.lang.String r3 = r5.getFileName()
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 == 0) goto L46
            java.lang.String r2 = r1.getClassName()
            java.lang.String r3 = r5.getClassName()
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r2 == 0) goto L46
            java.lang.String r1 = r1.getMethodName()
            java.lang.String r2 = r5.getMethodName()
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 == 0) goto L46
            return r0
        L46:
            int r0 = r0 + 1
            goto Lf
        L49:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.findIndexOfFrame(int, java.lang.StackTraceElement[], java.util.List):int");
    }

    private final Set<CoroutineOwner<?>> getCapturedCoroutines() {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap
            java.util.Set r0 = r0.keySet()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getCapturedCoroutines():java.util.Set");
    }

    private final String getDebugString(Job v) {
        /*
            this = this;
            boolean r0 = r2 instanceof kotlinx.coroutines.JobSupport
            if (r0 == 0) goto Lb
            kotlinx.coroutines.JobSupport r2 = (kotlinx.coroutines.JobSupport) r2
            java.lang.String r2 = r2.toDebugString()
            return r2
        Lb:
            java.lang.String r2 = r2.toString()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getDebugString(kotlinx.coroutines.Job):java.lang.String");
    }

    private static /* synthetic */ void getDebugString$annotations(Job v) {
        /*
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getDebugString$annotations(kotlinx.coroutines.Job):void");
    }

    private final Function1<Boolean, Unit> getDynamicAttach() {
        /*
            this = this;
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch: java.lang.Throwable -> L2e
            r0 = r2
            kotlinx.coroutines.debug.internal.DebugProbesImpl r0 = (kotlinx.coroutines.debug.internal.DebugProbesImpl) r0     // Catch: java.lang.Throwable -> L2e
            java.lang.String r0 = "kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: java.lang.Throwable -> L2e
            java.lang.reflect.Constructor[] r0 = r0.getConstructors()     // Catch: java.lang.Throwable -> L2e
            r1 = 0
            r0 = r0[r1]     // Catch: java.lang.Throwable -> L2e
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r0 = r0.newInstance(r1)     // Catch: java.lang.Throwable -> L2e
            if (r0 == 0) goto L26
            r1 = 1
            java.lang.Object r0 = kotlin.jvm.internal.TypeIntrinsics.beforeCheckcastToFunctionOfArity(r0, r1)     // Catch: java.lang.Throwable -> L2e
            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0     // Catch: java.lang.Throwable -> L2e
            java.lang.Object r0 = kotlin.Result.m134constructorimpl(r0)     // Catch: java.lang.Throwable -> L2e
            goto L39
        L26:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException     // Catch: java.lang.Throwable -> L2e
            java.lang.String r1 = "null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L2e
            throw r0     // Catch: java.lang.Throwable -> L2e
        L2e:
            r0 = move-exception
            kotlin.Result$Companion r1 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m134constructorimpl(r0)
        L39:
            boolean r1 = kotlin.Result.m140isFailureimpl(r0)
            if (r1 == 0) goto L40
            r0 = 0
        L40:
            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getDynamicAttach():kotlin.jvm.functions.Function1");
    }

    private final boolean isFinished(CoroutineOwner<?> v) {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r0 = r4.info
            kotlin.coroutines.CoroutineContext r0 = r0.getContext()
            r1 = 0
            if (r0 == 0) goto L24
            kotlinx.coroutines.Job$Key r2 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Key r2 = (kotlin.coroutines.CoroutineContext.Key) r2
            kotlin.coroutines.CoroutineContext$Element r0 = r0.get(r2)
            kotlinx.coroutines.Job r0 = (kotlinx.coroutines.Job) r0
            if (r0 != 0) goto L16
            goto L24
        L16:
            boolean r0 = r0.isCompleted()
            if (r0 != 0) goto L1d
            return r1
        L1d:
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap
            r0.remove(r4)
            r4 = 1
            return r4
        L24:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.isFinished(kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner):boolean");
    }

    private final boolean isInternalMethod(StackTraceElement v) {
        /*
            this = this;
            java.lang.String r5 = r5.getClassName()
            r0 = 2
            r1 = 0
            java.lang.String r2 = "kotlinx.coroutines"
            r3 = 0
            boolean r5 = kotlin.text.StringsKt.startsWith$default(r5, r2, r3, r0, r1)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.isInternalMethod(java.lang.StackTraceElement):boolean");
    }

    private final CoroutineOwner<?> owner(Continuation<?> v) {
        /*
            this = this;
            boolean r0 = r3 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame
            r1 = 0
            if (r0 == 0) goto L8
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r3 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r3
            goto L9
        L8:
            r3 = r1
        L9:
            if (r3 == 0) goto L10
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r3 = r2.owner(r3)
            return r3
        L10:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.owner(kotlin.coroutines.Continuation):kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner");
    }

    private final CoroutineOwner<?> owner(CoroutineStackFrame v) {
        /*
            this = this;
        L0:
            boolean r0 = r2 instanceof kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner
            if (r0 == 0) goto L7
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r2 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r2
            return r2
        L7:
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r2 = r2.getCallerFrame()
            if (r2 == 0) goto Le
            goto L0
        Le:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.owner(kotlin.coroutines.jvm.internal.CoroutineStackFrame):kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner");
    }

    private final void printStackTrace(PrintStream v, List<StackTraceElement> v2) {
        /*
            this = this;
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.Iterator r5 = r5.iterator()
        L6:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L25
            java.lang.Object r0 = r5.next()
            java.lang.StackTraceElement r0 = (java.lang.StackTraceElement) r0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "\n\tat "
            r1.<init>(r2)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
            r4.print(r0)
            goto L6
        L25:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.printStackTrace(java.io.PrintStream, java.util.List):void");
    }

    private final void probeCoroutineCompleted(CoroutineOwner<?> v) {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap
            r0.remove(r2)
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r2 = r2.info
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r2 = r2.getLastObservedFrame$kotlinx_coroutines_core()
            if (r2 == 0) goto L19
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r2 = r1.realCaller(r2)
            if (r2 != 0) goto L14
            goto L19
        L14:
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl> r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.callerInfoCache
            r0.remove(r2)
        L19:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.probeCoroutineCompleted(kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner):void");
    }

    private final CoroutineStackFrame realCaller(CoroutineStackFrame v) {
        /*
            this = this;
        L0:
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r2 = r2.getCallerFrame()
            if (r2 != 0) goto L8
            r2 = 0
            return r2
        L8:
            java.lang.StackTraceElement r0 = r2.getStackTraceElement()
            if (r0 == 0) goto L0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.realCaller(kotlin.coroutines.jvm.internal.CoroutineStackFrame):kotlin.coroutines.jvm.internal.CoroutineStackFrame");
    }

    private final <T extends Throwable> List<StackTraceElement> sanitizeStackTrace(T v) {
        /*
            this = this;
            java.lang.StackTraceElement[] r9 = r9.getStackTrace()
            int r0 = r9.length
            int r1 = r9.length
            r2 = -1
            int r1 = r1 + r2
            if (r1 < 0) goto L21
        La:
            int r3 = r1 + (-1)
            r4 = r9[r1]
            java.lang.String r4 = r4.getClassName()
            java.lang.String r5 = "kotlin.coroutines.jvm.internal.DebugProbesKt"
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
            if (r4 == 0) goto L1c
            r2 = r1
            goto L21
        L1c:
            if (r3 >= 0) goto L1f
            goto L21
        L1f:
            r1 = r3
            goto La
        L21:
            boolean r1 = kotlinx.coroutines.debug.internal.DebugProbesImpl.sanitizeStackTraces
            java.lang.String r3 = "Coroutine creation stacktrace"
            if (r1 != 0) goto L44
            int r0 = r0 - r2
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
            r4 = 0
        L2e:
            if (r4 >= r0) goto L41
            if (r4 != 0) goto L37
            java.lang.StackTraceElement r5 = kotlinx.coroutines.internal.StackTraceRecoveryKt.artificialFrame(r3)
            goto L3b
        L37:
            int r5 = r4 + r2
            r5 = r9[r5]
        L3b:
            r1.add(r5)
            int r4 = r4 + 1
            goto L2e
        L41:
            java.util.List r1 = (java.util.List) r1
            return r1
        L44:
            java.util.ArrayList r1 = new java.util.ArrayList
            int r4 = r0 - r2
            int r4 = r4 + 1
            r1.<init>(r4)
            r4 = r1
            java.util.Collection r4 = (java.util.Collection) r4
            java.lang.StackTraceElement r3 = kotlinx.coroutines.internal.StackTraceRecoveryKt.artificialFrame(r3)
            r4.add(r3)
        L57:
            int r2 = r2 + 1
        L59:
            if (r2 >= r0) goto L9d
            r3 = r9[r2]
            boolean r3 = r8.isInternalMethod(r3)
            if (r3 == 0) goto L97
            r3 = r9[r2]
            r4.add(r3)
            int r3 = r2 + 1
        L6a:
            if (r3 >= r0) goto L77
            r5 = r9[r3]
            boolean r5 = r8.isInternalMethod(r5)
            if (r5 == 0) goto L77
            int r3 = r3 + 1
            goto L6a
        L77:
            int r5 = r3 + (-1)
            r6 = r5
        L7a:
            if (r6 <= r2) goto L87
            r7 = r9[r6]
            java.lang.String r7 = r7.getFileName()
            if (r7 != 0) goto L87
            int r6 = r6 + (-1)
            goto L7a
        L87:
            if (r6 <= r2) goto L90
            if (r6 >= r5) goto L90
            r2 = r9[r6]
            r4.add(r2)
        L90:
            r2 = r9[r5]
            r4.add(r2)
            r2 = r3
            goto L59
        L97:
            r3 = r9[r2]
            r4.add(r3)
            goto L57
        L9d:
            java.util.List r1 = (java.util.List) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.sanitizeStackTrace(java.lang.Throwable):java.util.List");
    }

    private final void startWeakRefCleanerThread() {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.DebugProbesImpl$startWeakRefCleanerThread$1 r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.AnonymousClass1.INSTANCE
            r6 = r0
            kotlin.jvm.functions.Function0 r6 = (kotlin.jvm.functions.Function0) r6
            r7 = 21
            r8 = 0
            r1 = 0
            r2 = 1
            r3 = 0
            java.lang.String r4 = "Coroutines Debugger Cleaner"
            r5 = 0
            java.lang.Thread r0 = kotlin.concurrent.ThreadsKt.thread$default(r1, r2, r3, r4, r5, r6, r7, r8)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.weakRefCleanerThread = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.startWeakRefCleanerThread():void");
    }

    private final void stopWeakRefCleanerThread() {
        /*
            this = this;
            java.lang.Thread r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.weakRefCleanerThread
            if (r0 != 0) goto L5
            return
        L5:
            r1 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl.weakRefCleanerThread = r1
            r0.interrupt()
            r0.join()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.stopWeakRefCleanerThread():void");
    }

    private final StackTraceFrame toStackTraceFrame(List<StackTraceElement> v) {
        /*
            this = this;
            boolean r0 = r4.isEmpty()
            r1 = 0
            if (r0 != 0) goto L24
            int r0 = r4.size()
            java.util.ListIterator r4 = r4.listIterator(r0)
        Lf:
            boolean r0 = r4.hasPrevious()
            if (r0 == 0) goto L24
            java.lang.Object r0 = r4.previous()
            java.lang.StackTraceElement r0 = (java.lang.StackTraceElement) r0
            kotlinx.coroutines.debug.internal.StackTraceFrame r2 = new kotlinx.coroutines.debug.internal.StackTraceFrame
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r1 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r1
            r2.<init>(r1, r0)
            r1 = r2
            goto Lf
        L24:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.toStackTraceFrame(java.util.List):kotlinx.coroutines.debug.internal.StackTraceFrame");
    }

    private final String toStringWithQuotes(Object v) {
        /*
            this = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "\""
            r0.<init>(r1)
            java.lang.StringBuilder r3 = r0.append(r3)
            r0 = 34
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.String r3 = r3.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.toStringWithQuotes(java.lang.Object):java.lang.String");
    }

    private final void updateRunningState(CoroutineStackFrame v, String v2) {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r1 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L5b
            boolean r2 = r1.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L5b
            if (r2 != 0) goto L15
            r0.unlock()
            return
        L15:
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl> r2 = kotlinx.coroutines.debug.internal.DebugProbesImpl.callerInfoCache     // Catch: java.lang.Throwable -> L5b
            java.lang.Object r3 = r2.remove(r6)     // Catch: java.lang.Throwable -> L5b
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r3 = (kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl) r3     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L20
            goto L3c
        L20:
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r3 = r1.owner(r6)     // Catch: java.lang.Throwable -> L5b
            if (r3 == 0) goto L57
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r3 = r3.info     // Catch: java.lang.Throwable -> L5b
            if (r3 != 0) goto L2b
            goto L57
        L2b:
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r4 = r3.getLastObservedFrame$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L5b
            if (r4 == 0) goto L36
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r4 = r1.realCaller(r4)     // Catch: java.lang.Throwable -> L5b
            goto L37
        L36:
            r4 = 0
        L37:
            if (r4 == 0) goto L3c
            r2.remove(r4)     // Catch: java.lang.Throwable -> L5b
        L3c:
            r4 = r6
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch: java.lang.Throwable -> L5b
            r3.updateState$kotlinx_coroutines_core(r7, r4)     // Catch: java.lang.Throwable -> L5b
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r6 = r1.realCaller(r6)     // Catch: java.lang.Throwable -> L5b
            if (r6 != 0) goto L4c
            r0.unlock()
            return
        L4c:
            java.util.Map r2 = (java.util.Map) r2     // Catch: java.lang.Throwable -> L5b
            r2.put(r6, r3)     // Catch: java.lang.Throwable -> L5b
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L5b
            r0.unlock()
            return
        L57:
            r0.unlock()
            return
        L5b:
            r6 = move-exception
            r0.unlock()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.updateRunningState(kotlin.coroutines.jvm.internal.CoroutineStackFrame, java.lang.String):void");
    }

    private final void updateState(Continuation<?> v, String v2) {
        /*
            this = this;
            boolean r0 = r4.isInstalled$kotlinx_coroutines_core()
            if (r0 != 0) goto L7
            goto L30
        L7:
            java.lang.String r0 = "RUNNING"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            if (r0 == 0) goto L2a
            kotlin.KotlinVersion r0 = kotlin.KotlinVersion.CURRENT
            r1 = 3
            r2 = 30
            r3 = 1
            boolean r0 = r0.isAtLeast(r3, r1, r2)
            if (r0 == 0) goto L2a
            boolean r0 = r5 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame
            if (r0 == 0) goto L22
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r5 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r5
            goto L23
        L22:
            r5 = 0
        L23:
            if (r5 != 0) goto L26
            goto L30
        L26:
            r4.updateRunningState(r5, r6)
            return
        L2a:
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r0 = r4.owner(r5)
            if (r0 != 0) goto L31
        L30:
            return
        L31:
            r4.updateState(r0, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.updateState(kotlin.coroutines.Continuation, java.lang.String):void");
    }

    private final void updateState(CoroutineOwner<?> v, Continuation<?> v2, String v3) {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r1 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L20
            boolean r1 = r1.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L20
            if (r1 != 0) goto L15
            r0.unlock()
            return
        L15:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r3 = r3.info     // Catch: java.lang.Throwable -> L20
            r3.updateState$kotlinx_coroutines_core(r5, r4)     // Catch: java.lang.Throwable -> L20
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L20
            r0.unlock()
            return
        L20:
            r3 = move-exception
            r0.unlock()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.updateState(kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner, kotlin.coroutines.Continuation, java.lang.String):void");
    }

    public final void dumpCoroutines(PrintStream v) {
        /*
            this = this;
            monitor-enter(r2)
            kotlinx.coroutines.debug.internal.DebugProbesImpl r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> La
            r0.dumpCoroutinesSynchronized(r2)     // Catch: java.lang.Throwable -> La
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> La
            monitor-exit(r2)
            return
        La:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutines(java.io.PrintStream):void");
    }

    public final List<DebugCoroutineInfo> dumpCoroutinesInfo() {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L67
            boolean r5 = r4.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L67
            if (r5 == 0) goto L5b
            java.util.Set r4 = r4.getCapturedCoroutines()     // Catch: java.lang.Throwable -> L67
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.collections.CollectionsKt.asSequence(r4)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            java.util.Comparator r5 = (java.util.Comparator) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.sortedWith(r4, r5)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.mapNotNull(r4, r5)     // Catch: java.lang.Throwable -> L67
            java.util.List r4 = kotlin.sequences.SequencesKt.toList(r4)     // Catch: java.lang.Throwable -> L67
        L4f:
            if (r3 >= r2) goto L57
            r1.lock()
            int r3 = r3 + 1
            goto L4f
        L57:
            r0.unlock()
            return r4
        L5b:
            java.lang.String r4 = "Debug probes are not installed"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L67
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L67
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L67
            throw r5     // Catch: java.lang.Throwable -> L67
        L67:
            r4 = move-exception
        L68:
            if (r3 >= r2) goto L70
            r1.lock()
            int r3 = r3 + 1
            goto L68
        L70:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfo():java.util.List");
    }

    public final Object[] dumpCoroutinesInfoAsJsonAndReferences() {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfoAsJsonAndReferences():java.lang.Object[]");
    }

    public final List<DebuggerInfo> dumpDebuggerInfo() {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L67
            boolean r5 = r4.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L67
            if (r5 == 0) goto L5b
            java.util.Set r4 = r4.getCapturedCoroutines()     // Catch: java.lang.Throwable -> L67
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.collections.CollectionsKt.asSequence(r4)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-12$$inlined$sortedBy$1     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            java.util.Comparator r5 = (java.util.Comparator) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.sortedWith(r4, r5)     // Catch: java.lang.Throwable -> L67
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1 r5 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpDebuggerInfo$$inlined$dumpCoroutinesInfoImpl$1     // Catch: java.lang.Throwable -> L67
            r5.<init>()     // Catch: java.lang.Throwable -> L67
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5     // Catch: java.lang.Throwable -> L67
            kotlin.sequences.Sequence r4 = kotlin.sequences.SequencesKt.mapNotNull(r4, r5)     // Catch: java.lang.Throwable -> L67
            java.util.List r4 = kotlin.sequences.SequencesKt.toList(r4)     // Catch: java.lang.Throwable -> L67
        L4f:
            if (r3 >= r2) goto L57
            r1.lock()
            int r3 = r3 + 1
            goto L4f
        L57:
            r0.unlock()
            return r4
        L5b:
            java.lang.String r4 = "Debug probes are not installed"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L67
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L67
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L67
            throw r5     // Catch: java.lang.Throwable -> L67
        L67:
            r4 = move-exception
        L68:
            if (r3 >= r2) goto L70
            r1.lock()
            int r3 = r3 + 1
            goto L68
        L70:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpDebuggerInfo():java.util.List");
    }

    public final List<StackTraceElement> enhanceStackTraceWithThreadDump(DebugCoroutineInfo v, List<StackTraceElement> v2) {
        /*
            this = this;
            java.lang.String r0 = r2.getState()
            java.lang.Thread r2 = r2.getLastObservedThread()
            java.util.List r2 = r1.enhanceStackTraceWithThreadDumpImpl(r0, r2, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.enhanceStackTraceWithThreadDump(kotlinx.coroutines.debug.internal.DebugCoroutineInfo, java.util.List):java.util.List");
    }

    public final String enhanceStackTraceWithThreadDumpAsJson(DebugCoroutineInfo v) {
        /*
            this = this;
            java.util.List r0 = r12.lastObservedStackTrace()
            java.util.List r12 = r11.enhanceStackTraceWithThreadDump(r12, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r0 = (java.util.List) r0
            java.util.Iterator r12 = r12.iterator()
        L13:
            boolean r1 = r12.hasNext()
            if (r1 == 0) goto L72
            java.lang.Object r1 = r12.next()
            java.lang.StackTraceElement r1 = (java.lang.StackTraceElement) r1
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "\n                {\n                    \"declaringClass\": \""
            r2.<init>(r3)
            java.lang.String r3 = r1.getClassName()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\",\n                    \"methodName\": \""
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r1.getMethodName()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = "\",\n                    \"fileName\": "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r1.getFileName()
            if (r3 == 0) goto L4d
            java.lang.String r3 = r11.toStringWithQuotes(r3)
            goto L4e
        L4d:
            r3 = 0
        L4e:
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = ",\n                    \"lineNumber\": "
            java.lang.StringBuilder r2 = r2.append(r3)
            int r1 = r1.getLineNumber()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r2 = "\n                }\n                "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r1 = kotlin.text.StringsKt.trimIndent(r1)
            r0.add(r1)
            goto L13
        L72:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r1 = "["
            r12.<init>(r1)
            r2 = r0
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            r9 = 63
            r10 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            java.lang.String r0 = kotlin.collections.CollectionsKt.joinToString$default(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            java.lang.StringBuilder r12 = r12.append(r0)
            r0 = 93
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.enhanceStackTraceWithThreadDumpAsJson(kotlinx.coroutines.debug.internal.DebugCoroutineInfo):java.lang.String");
    }

    public final boolean getEnableCreationStackTraces() {
        /*
            this = this;
            boolean r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.enableCreationStackTraces
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getEnableCreationStackTraces():boolean");
    }

    public final boolean getSanitizeStackTraces() {
        /*
            this = this;
            boolean r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.sanitizeStackTraces
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.getSanitizeStackTraces():boolean");
    }

    public final String hierarchyToString(Job v) {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> Lc8
            boolean r5 = r4.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> Lc8
            if (r5 == 0) goto Lbc
            java.util.Set r4 = r4.getCapturedCoroutines()     // Catch: java.lang.Throwable -> Lc8
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch: java.lang.Throwable -> Lc8
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lc8
            r5.<init>()     // Catch: java.lang.Throwable -> Lc8
            java.util.Collection r5 = (java.util.Collection) r5     // Catch: java.lang.Throwable -> Lc8
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> Lc8
        L3c:
            boolean r6 = r4.hasNext()     // Catch: java.lang.Throwable -> Lc8
            if (r6 == 0) goto L5d
            java.lang.Object r6 = r4.next()     // Catch: java.lang.Throwable -> Lc8
            r7 = r6
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r7 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r7     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.Continuation<T> r7 = r7.delegate     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.CoroutineContext r7 = r7.getContext()     // Catch: java.lang.Throwable -> Lc8
            kotlinx.coroutines.Job$Key r8 = kotlinx.coroutines.Job.Key     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.CoroutineContext$Key r8 = (kotlin.coroutines.CoroutineContext.Key) r8     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.CoroutineContext$Element r7 = r7.get(r8)     // Catch: java.lang.Throwable -> Lc8
            if (r7 == 0) goto L3c
            r5.add(r6)     // Catch: java.lang.Throwable -> Lc8
            goto L3c
        L5d:
            java.util.List r5 = (java.util.List) r5     // Catch: java.lang.Throwable -> Lc8
            java.lang.Iterable r5 = (java.lang.Iterable) r5     // Catch: java.lang.Throwable -> Lc8
            r4 = 10
            int r4 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r5, r4)     // Catch: java.lang.Throwable -> Lc8
            int r4 = kotlin.collections.MapsKt.mapCapacity(r4)     // Catch: java.lang.Throwable -> Lc8
            r6 = 16
            int r4 = kotlin.ranges.RangesKt.coerceAtLeast(r4, r6)     // Catch: java.lang.Throwable -> Lc8
            java.util.LinkedHashMap r6 = new java.util.LinkedHashMap     // Catch: java.lang.Throwable -> Lc8
            r6.<init>(r4)     // Catch: java.lang.Throwable -> Lc8
            java.util.Map r6 = (java.util.Map) r6     // Catch: java.lang.Throwable -> Lc8
            java.util.Iterator r4 = r5.iterator()     // Catch: java.lang.Throwable -> Lc8
        L7c:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> Lc8
            if (r5 == 0) goto L9b
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> Lc8
            r7 = r5
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r7 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r7     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.Continuation<T> r7 = r7.delegate     // Catch: java.lang.Throwable -> Lc8
            kotlin.coroutines.CoroutineContext r7 = r7.getContext()     // Catch: java.lang.Throwable -> Lc8
            kotlinx.coroutines.Job r7 = kotlinx.coroutines.JobKt.getJob(r7)     // Catch: java.lang.Throwable -> Lc8
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r5 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r5     // Catch: java.lang.Throwable -> Lc8
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r5 = r5.info     // Catch: java.lang.Throwable -> Lc8
            r6.put(r7, r5)     // Catch: java.lang.Throwable -> Lc8
            goto L7c
        L9b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lc8
            r4.<init>()     // Catch: java.lang.Throwable -> Lc8
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r7 = ""
            r5.build(r10, r6, r4, r7)     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r10 = r4.toString()     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r4 = "StringBuilder().apply(builderAction).toString()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r4)     // Catch: java.lang.Throwable -> Lc8
        Lb0:
            if (r3 >= r2) goto Lb8
            r1.lock()
            int r3 = r3 + 1
            goto Lb0
        Lb8:
            r0.unlock()
            return r10
        Lbc:
            java.lang.String r10 = "Debug probes are not installed"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lc8
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> Lc8
            r4.<init>(r10)     // Catch: java.lang.Throwable -> Lc8
            throw r4     // Catch: java.lang.Throwable -> Lc8
        Lc8:
            r10 = move-exception
        Lc9:
            if (r3 >= r2) goto Ld1
            r1.lock()
            int r3 = r3 + 1
            goto Lc9
        Ld1:
            r0.unlock()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.hierarchyToString(kotlinx.coroutines.Job):java.lang.String");
    }

    public final void install() {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L6b
            int r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.installations     // Catch: java.lang.Throwable -> L6b
            r6 = 1
            int r5 = r5 + r6
            kotlinx.coroutines.debug.internal.DebugProbesImpl.installations = r5     // Catch: java.lang.Throwable -> L6b
            int r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.installations     // Catch: java.lang.Throwable -> L6b
            if (r5 <= r6) goto L3b
        L2f:
            if (r3 >= r2) goto L37
            r1.lock()
            int r3 = r3 + 1
            goto L2f
        L37:
            r0.unlock()
            return
        L3b:
            r4.startWeakRefCleanerThread()     // Catch: java.lang.Throwable -> L6b
            kotlinx.coroutines.debug.internal.AgentInstallationType r4 = kotlinx.coroutines.debug.internal.AgentInstallationType.INSTANCE     // Catch: java.lang.Throwable -> L6b
            boolean r4 = r4.isInstalledStatically$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L6b
            if (r4 == 0) goto L52
        L46:
            if (r3 >= r2) goto L4e
            r1.lock()
            int r3 = r3 + 1
            goto L46
        L4e:
            r0.unlock()
            return
        L52:
            kotlin.jvm.functions.Function1<java.lang.Boolean, kotlin.Unit> r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.dynamicAttach     // Catch: java.lang.Throwable -> L6b
            if (r4 == 0) goto L5d
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r6)     // Catch: java.lang.Throwable -> L6b
            r4.invoke(r5)     // Catch: java.lang.Throwable -> L6b
        L5d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L6b
        L5f:
            if (r3 >= r2) goto L67
            r1.lock()
            int r3 = r3 + 1
            goto L5f
        L67:
            r0.unlock()
            return
        L6b:
            r4 = move-exception
        L6c:
            if (r3 >= r2) goto L74
            r1.lock()
            int r3 = r3 + 1
            goto L6c
        L74:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.install():void");
    }

    public final boolean isInstalled$kotlinx_coroutines_core() {
        /*
            this = this;
            int r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.installations
            if (r0 <= 0) goto L6
            r0 = 1
            return r0
        L6:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.isInstalled$kotlinx_coroutines_core():boolean");
    }

    public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(Continuation<? super T> v) {
        /*
            this = this;
            boolean r0 = r2.isInstalled$kotlinx_coroutines_core()
            if (r0 != 0) goto L7
            goto Ld
        L7:
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r0 = r2.owner(r3)
            if (r0 == 0) goto Le
        Ld:
            return r3
        Le:
            boolean r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.enableCreationStackTraces
            if (r0 == 0) goto L22
            java.lang.Exception r0 = new java.lang.Exception
            r0.<init>()
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            java.util.List r0 = r2.sanitizeStackTrace(r0)
            kotlinx.coroutines.debug.internal.StackTraceFrame r0 = r2.toStackTraceFrame(r0)
            goto L26
        L22:
            r0 = 0
            r1 = r0
            kotlinx.coroutines.debug.internal.StackTraceFrame r1 = (kotlinx.coroutines.debug.internal.StackTraceFrame) r1
        L26:
            kotlin.coroutines.Continuation r3 = r2.createOwner(r3, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.probeCoroutineCreated$kotlinx_coroutines_core(kotlin.coroutines.Continuation):kotlin.coroutines.Continuation");
    }

    public final void probeCoroutineResumed$kotlinx_coroutines_core(Continuation<?> v) {
        /*
            this = this;
            java.lang.String r0 = "RUNNING"
            r1.updateState(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.probeCoroutineResumed$kotlinx_coroutines_core(kotlin.coroutines.Continuation):void");
    }

    public final void probeCoroutineSuspended$kotlinx_coroutines_core(Continuation<?> v) {
        /*
            this = this;
            java.lang.String r0 = "SUSPENDED"
            r1.updateState(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.probeCoroutineSuspended$kotlinx_coroutines_core(kotlin.coroutines.Continuation):void");
    }

    public final void setEnableCreationStackTraces(boolean v) {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.DebugProbesImpl.enableCreationStackTraces = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.setEnableCreationStackTraces(boolean):void");
    }

    public final void setSanitizeStackTraces(boolean v) {
        /*
            this = this;
            kotlinx.coroutines.debug.internal.DebugProbesImpl.sanitizeStackTraces = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.setSanitizeStackTraces(boolean):void");
    }

    public final void uninstall() {
        /*
            this = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = kotlinx.coroutines.debug.internal.DebugProbesImpl.coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L12
            int r2 = r0.getReadHoldCount()
            goto L13
        L12:
            r2 = r3
        L13:
            r4 = r3
        L14:
            if (r4 >= r2) goto L1c
            r1.unlock()
            int r4 = r4 + 1
            goto L14
        L1c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.INSTANCE     // Catch: java.lang.Throwable -> L87
            boolean r5 = r4.isInstalled$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L87
            if (r5 == 0) goto L7b
            int r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.installations     // Catch: java.lang.Throwable -> L87
            int r5 = r5 + (-1)
            kotlinx.coroutines.debug.internal.DebugProbesImpl.installations = r5     // Catch: java.lang.Throwable -> L87
            int r5 = kotlinx.coroutines.debug.internal.DebugProbesImpl.installations     // Catch: java.lang.Throwable -> L87
            if (r5 == 0) goto L41
        L35:
            if (r3 >= r2) goto L3d
            r1.lock()
            int r3 = r3 + 1
            goto L35
        L3d:
            r0.unlock()
            return
        L41:
            r4.stopWeakRefCleanerThread()     // Catch: java.lang.Throwable -> L87
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.capturedCoroutinesMap     // Catch: java.lang.Throwable -> L87
            r4.clear()     // Catch: java.lang.Throwable -> L87
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl> r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.callerInfoCache     // Catch: java.lang.Throwable -> L87
            r4.clear()     // Catch: java.lang.Throwable -> L87
            kotlinx.coroutines.debug.internal.AgentInstallationType r4 = kotlinx.coroutines.debug.internal.AgentInstallationType.INSTANCE     // Catch: java.lang.Throwable -> L87
            boolean r4 = r4.isInstalledStatically$kotlinx_coroutines_core()     // Catch: java.lang.Throwable -> L87
            if (r4 == 0) goto L62
        L56:
            if (r3 >= r2) goto L5e
            r1.lock()
            int r3 = r3 + 1
            goto L56
        L5e:
            r0.unlock()
            return
        L62:
            kotlin.jvm.functions.Function1<java.lang.Boolean, kotlin.Unit> r4 = kotlinx.coroutines.debug.internal.DebugProbesImpl.dynamicAttach     // Catch: java.lang.Throwable -> L87
            if (r4 == 0) goto L6d
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r3)     // Catch: java.lang.Throwable -> L87
            r4.invoke(r5)     // Catch: java.lang.Throwable -> L87
        L6d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L87
        L6f:
            if (r3 >= r2) goto L77
            r1.lock()
            int r3 = r3 + 1
            goto L6f
        L77:
            r0.unlock()
            return
        L7b:
            java.lang.String r4 = "Agent was not installed"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L87
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L87
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L87
            throw r5     // Catch: java.lang.Throwable -> L87
        L87:
            r4 = move-exception
        L88:
            if (r3 >= r2) goto L90
            r1.lock()
            int r3 = r3 + 1
            goto L88
        L90:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.uninstall():void");
    }
}
