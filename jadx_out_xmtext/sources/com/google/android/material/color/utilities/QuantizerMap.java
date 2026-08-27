package com.google.android.material.color.utilities;

import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class QuantizerMap implements Quantizer {
    Map<Integer, Integer> colorToCount;

    @Override // com.google.android.material.color.utilities.Quantizer
    public QuantizerResult quantize(int[] iArr, int i) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i2 : iArr) {
            Integer num = (Integer) linkedHashMap.get(Integer.valueOf(i2));
            int iIntValue = 1;
            if (num != null) {
                iIntValue = 1 + num.intValue();
            }
            linkedHashMap.put(Integer.valueOf(i2), Integer.valueOf(iIntValue));
        }
        this.colorToCount = linkedHashMap;
        return new QuantizerResult(linkedHashMap);
    }

    public Map<Integer, Integer> getColorToCount() {
        return this.colorToCount;
    }
}
