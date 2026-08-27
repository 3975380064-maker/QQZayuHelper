package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintLayoutStates {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    private final ConstraintLayout mConstraintLayout;
    ConstraintSet mDefaultConstraintSet;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    ConstraintLayoutStates(Context context, ConstraintLayout constraintLayout, int i) throws XmlPullParserException, Resources.NotFoundException, IOException, NumberFormatException {
        this.mConstraintLayout = constraintLayout;
        load(context, i);
    }

    public boolean needsToChange(int i, float f, float f2) {
        int i2 = this.mCurrentStateId;
        if (i2 != i) {
            return true;
        }
        State stateValueAt = i == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i2);
        return (this.mCurrentConstraintNumber == -1 || !stateValueAt.mVariants.get(this.mCurrentConstraintNumber).match(f, f2)) && this.mCurrentConstraintNumber != stateValueAt.findMatch(f, f2);
    }

    public void updateConstraints(int i, float f, float f2) {
        ConstraintSet constraintSet;
        int i2;
        State stateValueAt;
        int iFindMatch;
        ConstraintSet constraintSet2;
        int i3;
        int i4 = this.mCurrentStateId;
        if (i4 == i) {
            if (i == -1) {
                stateValueAt = this.mStateList.valueAt(0);
            } else {
                stateValueAt = this.mStateList.get(i4);
            }
            if ((this.mCurrentConstraintNumber == -1 || !stateValueAt.mVariants.get(this.mCurrentConstraintNumber).match(f, f2)) && this.mCurrentConstraintNumber != (iFindMatch = stateValueAt.findMatch(f, f2))) {
                if (iFindMatch == -1) {
                    constraintSet2 = this.mDefaultConstraintSet;
                } else {
                    constraintSet2 = stateValueAt.mVariants.get(iFindMatch).mConstraintSet;
                }
                if (iFindMatch == -1) {
                    i3 = stateValueAt.mConstraintID;
                } else {
                    i3 = stateValueAt.mVariants.get(iFindMatch).mConstraintID;
                }
                if (constraintSet2 == null) {
                    return;
                }
                this.mCurrentConstraintNumber = iFindMatch;
                ConstraintsChangedListener constraintsChangedListener = this.mConstraintsChangedListener;
                if (constraintsChangedListener != null) {
                    constraintsChangedListener.preLayoutChange(-1, i3);
                }
                constraintSet2.applyTo(this.mConstraintLayout);
                ConstraintsChangedListener constraintsChangedListener2 = this.mConstraintsChangedListener;
                if (constraintsChangedListener2 != null) {
                    constraintsChangedListener2.postLayoutChange(-1, i3);
                    return;
                }
                return;
            }
            return;
        }
        this.mCurrentStateId = i;
        State state = this.mStateList.get(i);
        int iFindMatch2 = state.findMatch(f, f2);
        if (iFindMatch2 == -1) {
            constraintSet = state.mConstraintSet;
        } else {
            constraintSet = state.mVariants.get(iFindMatch2).mConstraintSet;
        }
        if (iFindMatch2 == -1) {
            i2 = state.mConstraintID;
        } else {
            i2 = state.mVariants.get(iFindMatch2).mConstraintID;
        }
        if (constraintSet == null) {
            Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + i + ", dim =" + f + ", " + f2);
            return;
        }
        this.mCurrentConstraintNumber = iFindMatch2;
        ConstraintsChangedListener constraintsChangedListener3 = this.mConstraintsChangedListener;
        if (constraintsChangedListener3 != null) {
            constraintsChangedListener3.preLayoutChange(i, i2);
        }
        constraintSet.applyTo(this.mConstraintLayout);
        ConstraintsChangedListener constraintsChangedListener4 = this.mConstraintsChangedListener;
        if (constraintsChangedListener4 != null) {
            constraintsChangedListener4.postLayoutChange(i, i2);
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    static class State {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.mConstraintID = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.State);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.State_android_id) {
                    this.mId = typedArrayObtainStyledAttributes.getResourceId(index, this.mId);
                } else if (index == R.styleable.State_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        void add(Variant variant) {
            this.mVariants.add(variant);
        }

        public int findMatch(float f, float f2) {
            for (int i = 0; i < this.mVariants.size(); i++) {
                if (this.mVariants.get(i).match(f, f2)) {
                    return i;
                }
            }
            return -1;
        }
    }

    static class Variant {
        int mConstraintID;
        ConstraintSet mConstraintSet;
        int mId;
        float mMaxHeight;
        float mMaxWidth;
        float mMinHeight;
        float mMinWidth;

        public Variant(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.mMinWidth = Float.NaN;
            this.mMinHeight = Float.NaN;
            this.mMaxWidth = Float.NaN;
            this.mMaxHeight = Float.NaN;
            this.mConstraintID = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.Variant_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.clone(context, this.mConstraintID);
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.mMaxHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxHeight);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.mMinHeight = typedArrayObtainStyledAttributes.getDimension(index, this.mMinHeight);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.mMaxWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMaxWidth);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.mMinWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mMinWidth);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        boolean match(float f, float f2) {
            if (!Float.isNaN(this.mMinWidth) && f < this.mMinWidth) {
                return false;
            }
            if (!Float.isNaN(this.mMinHeight) && f2 < this.mMinHeight) {
                return false;
            }
            if (Float.isNaN(this.mMaxWidth) || f <= this.mMaxWidth) {
                return Float.isNaN(this.mMaxHeight) || f2 <= this.mMaxHeight;
            }
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0069 A[Catch: IOException -> 0x008a, XmlPullParserException -> 0x008f, TryCatch #2 {IOException -> 0x008a, XmlPullParserException -> 0x008f, blocks: (B:3:0x0008, B:32:0x0085, B:10:0x0017, B:11:0x001f, B:30:0x0069, B:13:0x0023, B:15:0x002b, B:17:0x0032, B:18:0x0036, B:21:0x003f, B:24:0x0048, B:26:0x0050, B:27:0x005d, B:29:0x0065, B:31:0x0082), top: B:39:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r6, int r7) throws org.xmlpull.v1.XmlPullParserException, android.content.res.Resources.NotFoundException, java.io.IOException, java.lang.NumberFormatException {
        /*
            r5 = this;
            android.content.res.Resources r0 = r6.getResources()
            android.content.res.XmlResourceParser r7 = r0.getXml(r7)
            int r0 = r7.getEventType()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r1 = 0
        Ld:
            r2 = 1
            if (r0 == r2) goto L93
            if (r0 == 0) goto L82
            r2 = 2
            if (r0 == r2) goto L17
            goto L85
        L17:
            java.lang.String r0 = r7.getName()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            int r2 = r0.hashCode()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            switch(r2) {
                case -1349929691: goto L5d;
                case 80204913: goto L48;
                case 1382829617: goto L3f;
                case 1657696882: goto L36;
                case 1901439077: goto L23;
                default: goto L22;
            }     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        L22:
            goto L69
        L23:
            java.lang.String r2 = "Variant"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r2 == 0) goto L69
            androidx.constraintlayout.widget.ConstraintLayoutStates$Variant r0 = new androidx.constraintlayout.widget.ConstraintLayoutStates$Variant     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r0.<init>(r6, r7)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r1 == 0) goto L85
            r1.add(r0)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L85
        L36:
            java.lang.String r2 = "layoutDescription"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r2 == 0) goto L69
            goto L85
        L3f:
            java.lang.String r2 = "StateSet"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r2 == 0) goto L69
            goto L85
        L48:
            java.lang.String r2 = "State"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r2 == 0) goto L69
            androidx.constraintlayout.widget.ConstraintLayoutStates$State r1 = new androidx.constraintlayout.widget.ConstraintLayoutStates$State     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r1.<init>(r6, r7)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            android.util.SparseArray<androidx.constraintlayout.widget.ConstraintLayoutStates$State> r0 = r5.mStateList     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            int r2 = r1.mId     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r0.put(r2, r1)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L85
        L5d:
            java.lang.String r2 = "ConstraintSet"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            if (r2 == 0) goto L69
            r5.parseConstraintSet(r6, r7)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L85
        L69:
            java.lang.String r2 = "ConstraintLayoutStates"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            r3.<init>()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            java.lang.String r4 = "unknown tag "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            java.lang.String r0 = r0.toString()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            android.util.Log.v(r2, r0)     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto L85
        L82:
            r7.getName()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
        L85:
            int r0 = r7.next()     // Catch: java.io.IOException -> L8a org.xmlpull.v1.XmlPullParserException -> L8f
            goto Ld
        L8a:
            r6 = move-exception
            r6.printStackTrace()
            goto L93
        L8f:
            r6 = move-exception
            r6.printStackTrace()
        L93:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayoutStates.load(android.content.Context, int):void");
    }

    private void parseConstraintSet(Context context, XmlPullParser xmlPullParser) throws NumberFormatException {
        ConstraintSet constraintSet = new ConstraintSet();
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ("id".equals(xmlPullParser.getAttributeName(i))) {
                String attributeValue = xmlPullParser.getAttributeValue(i);
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1) {
                    if (attributeValue != null && attributeValue.length() > 1) {
                        identifier = Integer.parseInt(attributeValue.substring(1));
                    } else {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    }
                }
                constraintSet.load(context, xmlPullParser);
                this.mConstraintSetMap.put(identifier, constraintSet);
                return;
            }
        }
    }
}
