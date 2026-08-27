package androidx.constraintlayout.motion.utils;

/* loaded from: classes.dex */
public class LinearCurveFit extends CurveFit {
    private static final String TAG = "LinearCurveFit";
    private double[] mT;
    private double mTotalLength;
    private double[][] mY;

    public LinearCurveFit(double[] dArr, double[][] dArr2) {
        this.mTotalLength = Double.NaN;
        int length = dArr.length;
        int length2 = dArr2[0].length;
        this.mT = dArr;
        this.mY = dArr2;
        if (length2 <= 2) {
            return;
        }
        int i = 0;
        double d = 0.0d;
        while (true) {
            double d2 = d;
            if (i < dArr.length) {
                double d3 = dArr2[i][0];
                if (i > 0) {
                    Math.hypot(d3 - d, d3 - d2);
                }
                i++;
                d = d3;
            } else {
                this.mTotalLength = 0.0d;
                return;
            }
        }
    }

    private double getLength2D(double d) {
        if (Double.isNaN(this.mTotalLength)) {
            return 0.0d;
        }
        double[] dArr = this.mT;
        int length = dArr.length;
        if (d <= dArr[0]) {
            return 0.0d;
        }
        int i = length - 1;
        if (d >= dArr[i]) {
            return this.mTotalLength;
        }
        double dHypot = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i2 = 0;
        while (i2 < i) {
            double[] dArr2 = this.mY[i2];
            double d4 = dArr2[0];
            double d5 = dArr2[1];
            if (i2 > 0) {
                dHypot += Math.hypot(d4 - d2, d5 - d3);
            }
            double[] dArr3 = this.mT;
            double d6 = dArr3[i2];
            if (d == d6) {
                return dHypot;
            }
            int i3 = i2 + 1;
            double d7 = dArr3[i3];
            if (d < d7) {
                double d8 = (d - d6) / (d7 - d6);
                double[][] dArr4 = this.mY;
                double[] dArr5 = dArr4[i2];
                double d9 = dArr5[0];
                double[] dArr6 = dArr4[i3];
                double d10 = 1.0d - d8;
                return dHypot + Math.hypot(d5 - ((dArr5[1] * d10) + (dArr6[1] * d8)), d4 - ((d9 * d10) + (dArr6[0] * d8)));
            }
            i2 = i3;
            d2 = d4;
            d3 = d5;
        }
        return 0.0d;
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, double[] dArr) {
        double[] dArr2 = this.mT;
        int length = dArr2.length;
        int i = 0;
        int length2 = this.mY[0].length;
        if (d <= dArr2[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                dArr[i2] = this.mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr2[i3]) {
            while (i < length2) {
                dArr[i] = this.mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    dArr[i5] = this.mY[i4][i5];
                }
            }
            double[] dArr3 = this.mT;
            int i6 = i4 + 1;
            double d2 = dArr3[i6];
            if (d < d2) {
                double d3 = dArr3[i4];
                double d4 = (d - d3) / (d2 - d3);
                while (i < length2) {
                    double[][] dArr4 = this.mY;
                    dArr[i] = (dArr4[i4][i] * (1.0d - d4)) + (dArr4[i6][i] * d4);
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public void getPos(double d, float[] fArr) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int i = 0;
        int length2 = this.mY[0].length;
        if (d <= dArr[0]) {
            for (int i2 = 0; i2 < length2; i2++) {
                fArr[i2] = (float) this.mY[0][i2];
            }
            return;
        }
        int i3 = length - 1;
        if (d >= dArr[i3]) {
            while (i < length2) {
                fArr[i] = (float) this.mY[i3][i];
                i++;
            }
            return;
        }
        int i4 = 0;
        while (i4 < i3) {
            if (d == this.mT[i4]) {
                for (int i5 = 0; i5 < length2; i5++) {
                    fArr[i5] = (float) this.mY[i4][i5];
                }
            }
            double[] dArr2 = this.mT;
            int i6 = i4 + 1;
            double d2 = dArr2[i6];
            if (d < d2) {
                double d3 = dArr2[i4];
                double d4 = (d - d3) / (d2 - d3);
                while (i < length2) {
                    double[][] dArr3 = this.mY;
                    fArr[i] = (float) ((dArr3[i4][i] * (1.0d - d4)) + (dArr3[i6][i] * d4));
                    i++;
                }
                return;
            }
            i4 = i6;
        }
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double getPos(double d, int i) {
        double[] dArr = this.mT;
        int length = dArr.length;
        int i2 = 0;
        if (d <= dArr[0]) {
            return this.mY[0][i];
        }
        int i3 = length - 1;
        if (d >= dArr[i3]) {
            return this.mY[i3][i];
        }
        while (i2 < i3) {
            double[] dArr2 = this.mT;
            double d2 = dArr2[i2];
            if (d == d2) {
                return this.mY[i2][i];
            }
            int i4 = i2 + 1;
            double d3 = dArr2[i4];
            if (d < d3) {
                double d4 = (d - d2) / (d3 - d2);
                double[][] dArr3 = this.mY;
                return (dArr3[i2][i] * (1.0d - d4)) + (dArr3[i4][i] * d4);
            }
            i2 = i4;
        }
        return 0.0d;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000f A[PHI: r4
      0x000f: PHI (r4v5 double) = (r4v0 double), (r4v2 double) binds: [B:3:0x000d, B:6:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.constraintlayout.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getSlope(double r13, double[] r15) {
        /*
            r12 = this;
            double[] r0 = r12.mT
            int r1 = r0.length
            double[][] r2 = r12.mY
            r3 = 0
            r2 = r2[r3]
            int r2 = r2.length
            r4 = r0[r3]
            int r6 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r6 > 0) goto L11
        Lf:
            r13 = r4
            goto L1a
        L11:
            int r4 = r1 + (-1)
            r4 = r0[r4]
            int r0 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r0 < 0) goto L1a
            goto Lf
        L1a:
            r0 = r3
        L1b:
            int r4 = r1 + (-1)
            if (r0 >= r4) goto L41
            double[] r4 = r12.mT
            int r5 = r0 + 1
            r6 = r4[r5]
            int r8 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r8 > 0) goto L3f
            r13 = r4[r0]
            double r6 = r6 - r13
        L2c:
            if (r3 >= r2) goto L41
            double[][] r13 = r12.mY
            r14 = r13[r0]
            r8 = r14[r3]
            r13 = r13[r5]
            r10 = r13[r3]
            double r10 = r10 - r8
            double r10 = r10 / r6
            r15[r3] = r10
            int r3 = r3 + 1
            goto L2c
        L3f:
            r0 = r5
            goto L1b
        L41:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.utils.LinearCurveFit.getSlope(double, double[]):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x000a A[PHI: r3
      0x000a: PHI (r3v4 double) = (r3v0 double), (r3v2 double) binds: [B:3:0x0008, B:6:0x0012] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.constraintlayout.motion.utils.CurveFit
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public double getSlope(double r8, int r10) {
        /*
            r7 = this;
            double[] r0 = r7.mT
            int r1 = r0.length
            r2 = 0
            r3 = r0[r2]
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 >= 0) goto Lc
        La:
            r8 = r3
            goto L15
        Lc:
            int r3 = r1 + (-1)
            r3 = r0[r3]
            int r0 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r0 < 0) goto L15
            goto La
        L15:
            int r0 = r1 + (-1)
            if (r2 >= r0) goto L35
            double[] r0 = r7.mT
            int r3 = r2 + 1
            r4 = r0[r3]
            int r6 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r6 > 0) goto L33
            r8 = r0[r2]
            double r4 = r4 - r8
            double[][] r8 = r7.mY
            r9 = r8[r2]
            r0 = r9[r10]
            r8 = r8[r3]
            r9 = r8[r10]
            double r9 = r9 - r0
            double r9 = r9 / r4
            return r9
        L33:
            r2 = r3
            goto L15
        L35:
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.utils.LinearCurveFit.getSlope(double, int):double");
    }

    @Override // androidx.constraintlayout.motion.utils.CurveFit
    public double[] getTimePoints() {
        return this.mT;
    }
}
