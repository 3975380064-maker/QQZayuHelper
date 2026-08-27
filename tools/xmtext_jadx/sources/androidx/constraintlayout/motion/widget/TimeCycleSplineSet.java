package androidx.constraintlayout.motion.widget;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.widget.ConstraintAttribute;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

/* loaded from: classes.dex */
public abstract class TimeCycleSplineSet {
    private static final int CURVE_OFFSET = 2;
    private static final int CURVE_PERIOD = 1;
    private static final int CURVE_VALUE = 0;
    private static final String TAG = "SplineSet";
    private static float VAL_2PI = 6.2831855f;
    private int count;
    long last_time;
    protected CurveFit mCurveFit;
    private String mType;
    protected int mWaveShape = 0;
    protected int[] mTimePoints = new int[10];
    protected float[][] mValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 10, 3);
    private float[] mCache = new float[3];
    protected boolean mContinue = false;
    float last_cycle = Float.NaN;

    public abstract boolean setProperty(View view, float f, long j, KeyCache keyCache);

    public String toString() {
        String str = this.mType;
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        for (int i = 0; i < this.count; i++) {
            str = str + "[" + this.mTimePoints[i] + " , " + decimalFormat.format(this.mValues[i]) + "] ";
        }
        return str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public float get(float f, long j, View view, KeyCache keyCache) {
        this.mCurveFit.getPos(f, this.mCache);
        float[] fArr = this.mCache;
        float f2 = fArr[1];
        if (f2 == 0.0f) {
            this.mContinue = false;
            return fArr[2];
        }
        if (Float.isNaN(this.last_cycle)) {
            float floatValue = keyCache.getFloatValue(view, this.mType, 0);
            this.last_cycle = floatValue;
            if (Float.isNaN(floatValue)) {
                this.last_cycle = 0.0f;
            }
        }
        float f3 = (float) ((this.last_cycle + (((j - this.last_time) * 1.0E-9d) * f2)) % 1.0d);
        this.last_cycle = f3;
        keyCache.setFloatValue(view, this.mType, 0, f3);
        this.last_time = j;
        float f4 = this.mCache[0];
        float fCalcWave = (calcWave(this.last_cycle) * f4) + this.mCache[2];
        this.mContinue = (f4 == 0.0f && f2 == 0.0f) ? false : true;
        return fCalcWave;
    }

    protected float calcWave(float f) {
        float fAbs;
        switch (this.mWaveShape) {
            case 1:
                return Math.signum(f * VAL_2PI);
            case 2:
                fAbs = Math.abs(f);
                break;
            case 3:
                return (((f * 2.0f) + 1.0f) % 2.0f) - 1.0f;
            case 4:
                fAbs = ((f * 2.0f) + 1.0f) % 2.0f;
                break;
            case 5:
                return (float) Math.cos(f * VAL_2PI);
            case 6:
                float fAbs2 = 1.0f - Math.abs(((f * 4.0f) % 4.0f) - 2.0f);
                fAbs = fAbs2 * fAbs2;
                break;
            default:
                return (float) Math.sin(f * VAL_2PI);
        }
        return 1.0f - fAbs;
    }

    public CurveFit getCurveFit() {
        return this.mCurveFit;
    }

    static TimeCycleSplineSet makeCustomSpline(String str, SparseArray<ConstraintAttribute> sparseArray) {
        return new CustomSet(str, sparseArray);
    }

    static TimeCycleSplineSet makeSpline(String str, long j) {
        TimeCycleSplineSet rotationXset;
        str.hashCode();
        switch (str) {
            case "rotationX":
                rotationXset = new RotationXset();
                break;
            case "rotationY":
                rotationXset = new RotationYset();
                break;
            case "translationX":
                rotationXset = new TranslationXset();
                break;
            case "translationY":
                rotationXset = new TranslationYset();
                break;
            case "translationZ":
                rotationXset = new TranslationZset();
                break;
            case "progress":
                rotationXset = new ProgressSet();
                break;
            case "scaleX":
                rotationXset = new ScaleXset();
                break;
            case "scaleY":
                rotationXset = new ScaleYset();
                break;
            case "rotation":
                rotationXset = new RotationSet();
                break;
            case "elevation":
                rotationXset = new ElevationSet();
                break;
            case "transitionPathRotate":
                rotationXset = new PathRotate();
                break;
            case "alpha":
                rotationXset = new AlphaSet();
                break;
            default:
                return null;
        }
        rotationXset.setStartTime(j);
        return rotationXset;
    }

    protected void setStartTime(long j) {
        this.last_time = j;
    }

    public void setPoint(int i, float f, float f2, int i2, float f3) {
        int[] iArr = this.mTimePoints;
        int i3 = this.count;
        iArr[i3] = i;
        float[] fArr = this.mValues[i3];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        this.mWaveShape = Math.max(this.mWaveShape, i2);
        this.count++;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setup(int r12) {
        /*
            r11 = this;
            int r0 = r11.count
            if (r0 != 0) goto L1b
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r0 = "Error no points added to "
            r12.<init>(r0)
            java.lang.String r0 = r11.mType
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            java.lang.String r0 = "SplineSet"
            android.util.Log.e(r0, r12)
            return
        L1b:
            int[] r1 = r11.mTimePoints
            float[][] r2 = r11.mValues
            r3 = 1
            int r0 = r0 - r3
            r4 = 0
            androidx.constraintlayout.motion.widget.TimeCycleSplineSet.Sort.doubleQuickSort(r1, r2, r4, r0)
            r0 = r3
            r1 = r4
        L27:
            int[] r2 = r11.mTimePoints
            int r5 = r2.length
            if (r0 >= r5) goto L39
            r5 = r2[r0]
            int r6 = r0 + (-1)
            r2 = r2[r6]
            if (r5 == r2) goto L36
            int r1 = r1 + 1
        L36:
            int r0 = r0 + 1
            goto L27
        L39:
            if (r1 != 0) goto L3c
            r1 = r3
        L3c:
            double[] r0 = new double[r1]
            r2 = 2
            int[] r5 = new int[r2]
            r6 = 3
            r5[r3] = r6
            r5[r4] = r1
            java.lang.Class r1 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r1, r5)
            double[][] r1 = (double[][]) r1
            r5 = r4
            r6 = r5
        L50:
            int r7 = r11.count
            if (r5 >= r7) goto L88
            if (r5 <= 0) goto L61
            int[] r7 = r11.mTimePoints
            r8 = r7[r5]
            int r9 = r5 + (-1)
            r7 = r7[r9]
            if (r8 != r7) goto L61
            goto L85
        L61:
            int[] r7 = r11.mTimePoints
            r7 = r7[r5]
            double r7 = (double) r7
            r9 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            double r7 = r7 * r9
            r0[r6] = r7
            r7 = r1[r6]
            float[][] r8 = r11.mValues
            r8 = r8[r5]
            r9 = r8[r4]
            double r9 = (double) r9
            r7[r4] = r9
            r9 = r8[r3]
            double r9 = (double) r9
            r7[r3] = r9
            r8 = r8[r2]
            double r8 = (double) r8
            r7[r2] = r8
            int r6 = r6 + 1
        L85:
            int r5 = r5 + 1
            goto L50
        L88:
            androidx.constraintlayout.motion.utils.CurveFit r12 = androidx.constraintlayout.motion.utils.CurveFit.get(r12, r0, r1)
            r11.mCurveFit = r12
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.TimeCycleSplineSet.setup(int):void");
    }

    static class ElevationSet extends TimeCycleSplineSet {
        ElevationSet() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setElevation(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class AlphaSet extends TimeCycleSplineSet {
        AlphaSet() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setAlpha(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class RotationSet extends TimeCycleSplineSet {
        RotationSet() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotation(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class RotationXset extends TimeCycleSplineSet {
        RotationXset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class RotationYset extends TimeCycleSplineSet {
        RotationYset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setRotationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class PathRotate extends TimeCycleSplineSet {
        PathRotate() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            return this.mContinue;
        }

        public boolean setPathRotate(View view, KeyCache keyCache, float f, long j, double d, double d2) {
            view.setRotation(get(f, j, view, keyCache) + ((float) Math.toDegrees(Math.atan2(d2, d))));
            return this.mContinue;
        }
    }

    static class ScaleXset extends TimeCycleSplineSet {
        ScaleXset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setScaleX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class ScaleYset extends TimeCycleSplineSet {
        ScaleYset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setScaleY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class TranslationXset extends TimeCycleSplineSet {
        TranslationXset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setTranslationX(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class TranslationYset extends TimeCycleSplineSet {
        TranslationYset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setTranslationY(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class TranslationZset extends TimeCycleSplineSet {
        TranslationZset() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            view.setTranslationZ(get(f, j, view, keyCache));
            return this.mContinue;
        }
    }

    static class CustomSet extends TimeCycleSplineSet {
        String mAttributeName;
        float[] mCache;
        SparseArray<ConstraintAttribute> mConstraintAttributeList;
        float[] mTempValues;
        SparseArray<float[]> mWaveProperties = new SparseArray<>();

        public CustomSet(String str, SparseArray<ConstraintAttribute> sparseArray) {
            this.mAttributeName = str.split(",")[1];
            this.mConstraintAttributeList = sparseArray;
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setup(int i) {
            int size = this.mConstraintAttributeList.size();
            int iNoOfInterpValues = this.mConstraintAttributeList.valueAt(0).noOfInterpValues();
            double[] dArr = new double[size];
            int i2 = iNoOfInterpValues + 2;
            this.mTempValues = new float[i2];
            this.mCache = new float[iNoOfInterpValues];
            double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, size, i2);
            for (int i3 = 0; i3 < size; i3++) {
                int iKeyAt = this.mConstraintAttributeList.keyAt(i3);
                ConstraintAttribute constraintAttributeValueAt = this.mConstraintAttributeList.valueAt(i3);
                float[] fArrValueAt = this.mWaveProperties.valueAt(i3);
                dArr[i3] = iKeyAt * 0.01d;
                constraintAttributeValueAt.getValuesToInterpolate(this.mTempValues);
                int i4 = 0;
                while (true) {
                    if (i4 < this.mTempValues.length) {
                        dArr2[i3][i4] = r8[i4];
                        i4++;
                    }
                }
                double[] dArr3 = dArr2[i3];
                dArr3[iNoOfInterpValues] = fArrValueAt[0];
                dArr3[iNoOfInterpValues + 1] = fArrValueAt[1];
            }
            this.mCurveFit = CurveFit.get(i, dArr, dArr2);
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public void setPoint(int i, float f, float f2, int i2, float f3) {
            throw new RuntimeException("don't call for custom attribute call setPoint(pos, ConstraintAttribute,...)");
        }

        public void setPoint(int i, ConstraintAttribute constraintAttribute, float f, int i2, float f2) {
            this.mConstraintAttributeList.append(i, constraintAttribute);
            this.mWaveProperties.append(i, new float[]{f, f2});
            this.mWaveShape = Math.max(this.mWaveShape, i2);
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) {
            this.mCurveFit.getPos(f, this.mTempValues);
            float[] fArr = this.mTempValues;
            float f2 = fArr[fArr.length - 2];
            float f3 = fArr[fArr.length - 1];
            this.last_cycle = (float) ((this.last_cycle + (((j - this.last_time) * 1.0E-9d) * f2)) % 1.0d);
            this.last_time = j;
            float fCalcWave = calcWave(this.last_cycle);
            this.mContinue = false;
            for (int i = 0; i < this.mCache.length; i++) {
                this.mContinue |= ((double) this.mTempValues[i]) != 0.0d;
                this.mCache[i] = (this.mTempValues[i] * fCalcWave) + f3;
            }
            this.mConstraintAttributeList.valueAt(0).setInterpolatedValue(view, this.mCache);
            if (f2 != 0.0f) {
                this.mContinue = true;
            }
            return this.mContinue;
        }
    }

    static class ProgressSet extends TimeCycleSplineSet {
        boolean mNoMethod = false;

        ProgressSet() {
        }

        @Override // androidx.constraintlayout.motion.widget.TimeCycleSplineSet
        public boolean setProperty(View view, float f, long j, KeyCache keyCache) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            ProgressSet progressSet;
            Method method;
            if (view instanceof MotionLayout) {
                progressSet = this;
                ((MotionLayout) view).setProgress(get(f, j, view, keyCache));
            } else {
                progressSet = this;
                if (progressSet.mNoMethod) {
                    return false;
                }
                try {
                    method = view.getClass().getMethod("setProgress", Float.TYPE);
                } catch (NoSuchMethodException unused) {
                    progressSet.mNoMethod = true;
                    method = null;
                }
                if (method != null) {
                    try {
                        method.invoke(view, Float.valueOf(progressSet.get(f, j, view, keyCache)));
                    } catch (IllegalAccessException e) {
                        Log.e(TimeCycleSplineSet.TAG, "unable to setProgress", e);
                    } catch (InvocationTargetException e2) {
                        Log.e(TimeCycleSplineSet.TAG, "unable to setProgress", e2);
                    }
                }
            }
            return progressSet.mContinue;
        }
    }

    private static class Sort {
        private Sort() {
        }

        static void doubleQuickSort(int[] iArr, float[][] fArr, int i, int i2) {
            int[] iArr2 = new int[iArr.length + 10];
            iArr2[0] = i2;
            iArr2[1] = i;
            int i3 = 2;
            while (i3 > 0) {
                int i4 = iArr2[i3 - 1];
                int i5 = i3 - 2;
                int i6 = iArr2[i5];
                if (i4 < i6) {
                    int iPartition = partition(iArr, fArr, i4, i6);
                    iArr2[i5] = iPartition - 1;
                    iArr2[i3 - 1] = i4;
                    int i7 = i3 + 1;
                    iArr2[i3] = i6;
                    i3 += 2;
                    iArr2[i7] = iPartition + 1;
                } else {
                    i3 = i5;
                }
            }
        }

        private static int partition(int[] iArr, float[][] fArr, int i, int i2) {
            int i3 = iArr[i2];
            int i4 = i;
            while (i < i2) {
                if (iArr[i] <= i3) {
                    swap(iArr, fArr, i4, i);
                    i4++;
                }
                i++;
            }
            swap(iArr, fArr, i4, i2);
            return i4;
        }

        private static void swap(int[] iArr, float[][] fArr, int i, int i2) {
            int i3 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i3;
            float[] fArr2 = fArr[i];
            fArr[i] = fArr[i2];
            fArr[i2] = fArr2;
        }
    }
}
