package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.os.ParcelFileDescriptor;
import android.system.Os;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.util.Map;

/* loaded from: classes.dex */
final class ColorResourcesLoaderCreator {
    private static final String TAG = "ColorResLoaderCreator";

    private ColorResourcesLoaderCreator() {
    }

    static ResourcesLoader create(Context context, Map<Integer, Integer> map) throws Throwable {
        FileDescriptor fileDescriptorMemfd_create;
        try {
            byte[] bArrCreate = ColorResourcesTableCreator.create(context, map);
            Log.i(TAG, "Table created, length: " + bArrCreate.length);
            if (bArrCreate.length == 0) {
                return null;
            }
            try {
                fileDescriptorMemfd_create = Os.memfd_create("temp.arsc", 0);
            } catch (Throwable th) {
                th = th;
                fileDescriptorMemfd_create = null;
            }
            try {
                if (fileDescriptorMemfd_create == null) {
                    Log.w(TAG, "Cannot create memory file descriptor.");
                    if (fileDescriptorMemfd_create != null) {
                        Os.close(fileDescriptorMemfd_create);
                    }
                    return null;
                }
                FileOutputStream fileOutputStream = new FileOutputStream(fileDescriptorMemfd_create);
                try {
                    fileOutputStream.write(bArrCreate);
                    ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileDescriptorMemfd_create);
                    try {
                        ResourcesLoader resourcesLoader = new ResourcesLoader();
                        resourcesLoader.addProvider(ResourcesProvider.loadFromTable(parcelFileDescriptorDup, null));
                        if (parcelFileDescriptorDup != null) {
                            parcelFileDescriptorDup.close();
                        }
                        fileOutputStream.close();
                        if (fileDescriptorMemfd_create != null) {
                            Os.close(fileDescriptorMemfd_create);
                        }
                        return resourcesLoader;
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileDescriptorMemfd_create != null) {
                    Os.close(fileDescriptorMemfd_create);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to create the ColorResourcesTableCreator.", e);
            return null;
        }
    }
}
