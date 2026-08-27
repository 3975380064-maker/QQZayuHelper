package com.google.android.material.color.utilities;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class QuantizerCelebi {
    private QuantizerCelebi() {
    }

    public static Map<Integer, Integer> quantize(int[] iArr, int i) {
        Set<Integer> setKeySet = new QuantizerWu().quantize(iArr, i).colorToCount.keySet();
        int[] iArr2 = new int[setKeySet.size()];
        Iterator<Integer> it = setKeySet.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            iArr2[i2] = it.next().intValue();
            i2++;
        }
        return QuantizerWsmeans.quantize(iArr, iArr2, i);
    }
}
