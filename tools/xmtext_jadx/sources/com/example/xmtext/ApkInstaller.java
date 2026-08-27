package com.example.xmtext;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.example.xmtext.ApkInstaller;
import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ApkInstaller.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\"#B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003JP\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u00152\u001a\u0010\u0018\u001a\u0016\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00170\u0019J\u0006\u0010\u001a\u001a\u00020\u0017J\u0006\u0010\u001b\u001a\u00020\u000fJ\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\rH\u0002J\u0018\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/example/xmtext/ApkInstaller;", "", "<init>", "()V", "TAG", "", "APK_NAME", "MIME_APK", "POLL_INTERVAL_MS", "", "mainHandler", "Landroid/os/Handler;", "session", "Lcom/example/xmtext/ApkInstaller$DownloadSession;", "startDownload", "", "context", "Landroid/content/Context;", "url", "fileName", "onProgress", "Lkotlin/Function1;", "", "", "onComplete", "Lkotlin/Function2;", "cancelCurrent", "isDownloading", "cancelSession", "s", "startPolling", "installApk", "file", "Ljava/io/File;", "DownloadSession", "CompletionReceiver", "app_release"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ApkInstaller {
    private static final String APK_NAME = "xmtext_update.apk";
    private static final String MIME_APK = "application/vnd.android.package-archive";
    private static final long POLL_INTERVAL_MS = 500;
    private static final String TAG = "ApkInstaller";
    private static volatile DownloadSession session;
    public static final ApkInstaller INSTANCE = new ApkInstaller();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private ApkInstaller() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ApkInstaller.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0015\b\u0002\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u001a\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u000b0\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e¢\u0006\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR%\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u000f\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0010\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u001e\"\u0004\b\"\u0010 ¨\u0006#"}, d2 = {"Lcom/example/xmtext/ApkInstaller$DownloadSession;", "", "context", "Landroid/content/Context;", "downloadId", "", "fileName", "", "onProgress", "Lkotlin/Function1;", "", "", "onComplete", "Lkotlin/Function2;", "", "receiverRegistered", "polling", "<init>", "(Landroid/content/Context;JLjava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;ZZ)V", "getContext", "()Landroid/content/Context;", "getDownloadId", "()J", "getFileName", "()Ljava/lang/String;", "getOnProgress", "()Lkotlin/jvm/functions/Function1;", "getOnComplete", "()Lkotlin/jvm/functions/Function2;", "getReceiverRegistered", "()Z", "setReceiverRegistered", "(Z)V", "getPolling", "setPolling", "app_release"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    static final class DownloadSession {
        private final Context context;
        private final long downloadId;
        private final String fileName;
        private final Function2<Boolean, String, Unit> onComplete;
        private final Function1<Integer, Unit> onProgress;
        private boolean polling;
        private boolean receiverRegistered;

        /* JADX WARN: Multi-variable type inference failed */
        public DownloadSession(Context context, long j, String fileName, Function1<? super Integer, Unit> onProgress, Function2<? super Boolean, ? super String, Unit> onComplete, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Intrinsics.checkNotNullParameter(onProgress, "onProgress");
            Intrinsics.checkNotNullParameter(onComplete, "onComplete");
            this.context = context;
            this.downloadId = j;
            this.fileName = fileName;
            this.onProgress = onProgress;
            this.onComplete = onComplete;
            this.receiverRegistered = z;
            this.polling = z2;
        }

        public /* synthetic */ DownloadSession(Context context, long j, String str, Function1 function1, Function2 function2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, j, str, function1, function2, (i & 32) != 0 ? true : z, (i & 64) != 0 ? true : z2);
        }

        public final Context getContext() {
            return this.context;
        }

        public final long getDownloadId() {
            return this.downloadId;
        }

        public final String getFileName() {
            return this.fileName;
        }

        public final Function1<Integer, Unit> getOnProgress() {
            return this.onProgress;
        }

        public final Function2<Boolean, String, Unit> getOnComplete() {
            return this.onComplete;
        }

        public final boolean getReceiverRegistered() {
            return this.receiverRegistered;
        }

        public final void setReceiverRegistered(boolean z) {
            this.receiverRegistered = z;
        }

        public final boolean getPolling() {
            return this.polling;
        }

        public final void setPolling(boolean z) {
            this.polling = z;
        }
    }

    public static /* synthetic */ boolean startDownload$default(ApkInstaller apkInstaller, Context context, String str, String str2, Function1 function1, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            str2 = APK_NAME;
        }
        return apkInstaller.startDownload(context, str, str2, function1, function2);
    }

    public final boolean startDownload(Context context, String url, String fileName, Function1<? super Integer, Unit> onProgress, Function2<? super Boolean, ? super String, Unit> onComplete) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(onProgress, "onProgress");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        DownloadSession downloadSession = session;
        if (downloadSession != null) {
            INSTANCE.cancelSession(downloadSession);
        }
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if (file.exists()) {
            file.delete();
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setMimeType(MIME_APK);
        request.setTitle("正在下载 xmtext 更新");
        request.setDescription("下载完成后自动弹出安装");
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setAllowedNetworkTypes(3);
        request.setAllowedOverRoaming(true);
        request.setNotificationVisibility(0);
        Object systemService = context.getSystemService("download");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
        try {
            long jEnqueue = ((DownloadManager) systemService).enqueue(request);
            Log.d(TAG, "download enqueued, id=" + jEnqueue + ", file=" + file);
            DownloadSession downloadSession2 = new DownloadSession(context, jEnqueue, fileName, onProgress, onComplete, false, false, 96, null);
            session = downloadSession2;
            CompletionReceiver completionReceiver = new CompletionReceiver(downloadSession2);
            try {
                if (Build.VERSION.SDK_INT >= 33) {
                    context.registerReceiver(completionReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"), 4);
                } else {
                    context.registerReceiver(completionReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
                }
                downloadSession2.setReceiverRegistered(true);
            } catch (Exception e) {
                Log.w(TAG, "registerReceiver failed: " + e.getMessage());
                downloadSession2.setReceiverRegistered(false);
            }
            startPolling(downloadSession2);
            return true;
        } catch (Exception e2) {
            Log.w(TAG, "enqueue failed: " + e2.getMessage());
            Toast.makeText(context, "无法开始下载: " + e2.getMessage(), 1).show();
            return false;
        }
    }

    public final void cancelCurrent() {
        DownloadSession downloadSession = session;
        if (downloadSession != null) {
            INSTANCE.cancelSession(downloadSession);
        }
        session = null;
    }

    public final boolean isDownloading() {
        return session != null;
    }

    private final void cancelSession(DownloadSession downloadSession) {
        downloadSession.setPolling(false);
        if (downloadSession.getReceiverRegistered()) {
            try {
                downloadSession.getContext().unregisterReceiver(new CompletionReceiver(downloadSession));
            } catch (Exception unused) {
            }
            downloadSession.setReceiverRegistered(false);
        }
        try {
            Object systemService = downloadSession.getContext().getSystemService("download");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
            ((DownloadManager) systemService).remove(downloadSession.getDownloadId());
        } catch (Exception unused2) {
        }
        session = null;
    }

    private final void startPolling(DownloadSession downloadSession) {
        Object systemService = downloadSession.getContext().getSystemService("download");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
        mainHandler.post(new ApkInstaller$startPolling$pollTask$1(downloadSession, (DownloadManager) systemService));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ApkInstaller.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/example/xmtext/ApkInstaller$CompletionReceiver;", "Landroid/content/BroadcastReceiver;", "session", "Lcom/example/xmtext/ApkInstaller$DownloadSession;", "<init>", "(Lcom/example/xmtext/ApkInstaller$DownloadSession;)V", "onReceive", "", "ctx", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    static final class CompletionReceiver extends BroadcastReceiver {
        private final DownloadSession session;

        public CompletionReceiver(DownloadSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            this.session = session;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(final Context ctx, Intent intent) throws IOException {
            final File file;
            final String str;
            String str2;
            String str3;
            File file2;
            Intrinsics.checkNotNullParameter(ctx, "ctx");
            Intrinsics.checkNotNullParameter(intent, "intent");
            long longExtra = intent.getLongExtra("extra_download_id", -1L);
            if (longExtra != this.session.getDownloadId()) {
                return;
            }
            final boolean z = false;
            this.session.setPolling(false);
            if (this.session.getReceiverRegistered()) {
                try {
                    ctx.unregisterReceiver(this);
                } catch (Exception unused) {
                }
                this.session.setReceiverRegistered(false);
            }
            Object systemService = ctx.getSystemService("download");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
            boolean z2 = true;
            Cursor cursorQuery = ((DownloadManager) systemService).query(new DownloadManager.Query().setFilterById(longExtra));
            if (cursorQuery != null) {
                Cursor cursor = cursorQuery;
                try {
                    Cursor cursor2 = cursor;
                    if (cursor2.moveToFirst() && cursor2.getInt(cursor2.getColumnIndexOrThrow(NotificationCompat.CATEGORY_STATUS)) == 8) {
                        file2 = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), this.session.getFileName());
                        if (!file2.exists()) {
                            str2 = "下载文件丢失";
                        } else {
                            str3 = null;
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(cursor, null);
                            file = file2;
                            str = str3;
                            z = z2;
                        }
                    } else {
                        str2 = "下载失败";
                    }
                    z2 = false;
                    str3 = str2;
                    file2 = null;
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor, null);
                    file = file2;
                    str = str3;
                    z = z2;
                } finally {
                }
            } else {
                file = null;
                str = null;
            }
            ApkInstaller apkInstaller = ApkInstaller.INSTANCE;
            ApkInstaller.session = null;
            ApkInstaller.mainHandler.post(new Runnable() { // from class: com.example.xmtext.ApkInstaller$CompletionReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ApkInstaller.CompletionReceiver.onReceive$lambda$1(z, file, this, ctx, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onReceive$lambda$1(boolean z, File file, CompletionReceiver completionReceiver, Context context, String str) {
            if (z && file != null) {
                completionReceiver.session.getOnProgress().invoke(100);
                ApkInstaller.INSTANCE.installApk(context, file);
                completionReceiver.session.getOnComplete().invoke(true, null);
                return;
            }
            completionReceiver.session.getOnComplete().invoke(false, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void installApk(Context context, File file) {
        try {
            Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(uriForFile, MIME_APK);
            intent.addFlags(268435456);
            intent.addFlags(1);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.w(TAG, "install failed: " + e.getMessage());
            Toast.makeText(context, "无法启动安装器: " + e.getMessage(), 1).show();
        }
    }
}
