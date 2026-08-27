package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import java.util.Map;

/* loaded from: classes.dex */
final class ResourcesLoaderUtils {
    static boolean isColorResource(int i) {
        return 28 <= i && i <= 31;
    }

    private ResourcesLoaderUtils() {
    }

    static boolean addResourcesLoaderToContext(Context context, Map<Integer, Integer> map) throws Throwable {
        ResourcesLoader resourcesLoaderCreate = ColorResourcesLoaderCreator.create(context, map);
        if (resourcesLoaderCreate == null) {
            return false;
        }
        context.getResources().addLoaders(resourcesLoaderCreate);
        return true;
    }
}
