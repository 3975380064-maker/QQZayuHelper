package com.example.xmtext;

import android.app.DownloadManager;
import android.database.Cursor;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import com.example.xmtext.ApkInstaller;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;

/* compiled from: ApkInstaller.kt */
@Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"com/example/xmtext/ApkInstaller$startPolling$pollTask$1", "Ljava/lang/Runnable;", "run", "", "app_release"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ApkInstaller$startPolling$pollTask$1 implements Runnable {
    final /* synthetic */ DownloadManager $dm;
    final /* synthetic */ ApkInstaller.DownloadSession $s;

    ApkInstaller$startPolling$pollTask$1(ApkInstaller.DownloadSession downloadSession, DownloadManager downloadManager) {
        this.$s = downloadSession;
        this.$dm = downloadManager;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.$s.getPolling()) {
            try {
                Cursor cursorQuery = this.$dm.query(new DownloadManager.Query().setFilterById(this.$s.getDownloadId()));
                if (cursorQuery != null) {
                    Cursor cursor = cursorQuery;
                    final ApkInstaller.DownloadSession downloadSession = this.$s;
                    try {
                        Cursor cursor2 = cursor;
                        if (cursor2.moveToFirst()) {
                            int i = cursor2.getInt(cursor2.getColumnIndexOrThrow(NotificationCompat.CATEGORY_STATUS));
                            if (i == 2) {
                                long j = cursor2.getLong(cursor2.getColumnIndexOrThrow("bytes_so_far"));
                                long j2 = cursor2.getLong(cursor2.getColumnIndexOrThrow("total_size"));
                                final int i2 = j2 > 0 ? (int) ((j * 100) / j2) : 0;
                                ApkInstaller.mainHandler.post(new Runnable() { // from class: com.example.xmtext.ApkInstaller$startPolling$pollTask$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ApkInstaller$startPolling$pollTask$1.run$lambda$2$lambda$0(downloadSession, i2);
                                    }
                                });
                            } else if (i == 16) {
                                downloadSession.setPolling(false);
                                final int i3 = cursor2.getInt(cursor2.getColumnIndexOrThrow("reason"));
                                ApkInstaller.mainHandler.post(new Runnable() { // from class: com.example.xmtext.ApkInstaller$startPolling$pollTask$1$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ApkInstaller$startPolling$pollTask$1.run$lambda$2$lambda$1(downloadSession, i3);
                                    }
                                });
                                ApkInstaller apkInstaller = ApkInstaller.INSTANCE;
                                ApkInstaller.session = null;
                                CloseableKt.closeFinally(cursor, null);
                                return;
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                    }
                }
            } catch (Exception unused) {
            }
            if (this.$s.getPolling()) {
                ApkInstaller.mainHandler.postDelayed(this, 500L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$2$lambda$0(ApkInstaller.DownloadSession downloadSession, int i) {
        downloadSession.getOnProgress().invoke(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void run$lambda$2$lambda$1(ApkInstaller.DownloadSession downloadSession, int i) {
        downloadSession.getOnComplete().invoke(false, "下载失败(reason=" + i + ")");
    }
}
