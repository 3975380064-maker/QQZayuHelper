package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class StateSet {
    private static final boolean DEBUG = false;
    public static final String TAG = "ConstraintLayoutStates";
    ConstraintSet mDefaultConstraintSet;
    int mDefaultState = -1;
    int mCurrentStateId = -1;
    int mCurrentConstraintNumber = -1;
    private SparseArray<State> mStateList = new SparseArray<>();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray<>();
    private ConstraintsChangedListener mConstraintsChangedListener = null;

    public StateSet(Context context, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        load(context, xmlPullParser);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0088 A[Catch: IOException -> 0x00a9, XmlPullParserException -> 0x00ae, TryCatch #2 {IOException -> 0x00a9, XmlPullParserException -> 0x00ae, blocks: (B:8:0x0024, B:18:0x0038, B:40:0x00a4, B:21:0x0044, B:22:0x004c, B:38:0x0088, B:24:0x0050, B:26:0x0058, B:28:0x005f, B:29:0x0063, B:32:0x006a, B:35:0x0073, B:37:0x007b, B:39:0x00a1), top: B:47:0x0024 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void load(android.content.Context r6, org.xmlpull.v1.XmlPullParser r7) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r5 = this;
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r7)
            int[] r1 = androidx.constraintlayout.widget.R.styleable.StateSet
            android.content.res.TypedArray r0 = r6.obtainStyledAttributes(r0, r1)
            int r1 = r0.getIndexCount()
            r2 = 0
        Lf:
            if (r2 >= r1) goto L24
            int r3 = r0.getIndex(r2)
            int r4 = androidx.constraintlayout.widget.R.styleable.StateSet_defaultState
            if (r3 != r4) goto L21
            int r4 = r5.mDefaultState
            int r3 = r0.getResourceId(r3, r4)
            r5.mDefaultState = r3
        L21:
            int r2 = r2 + 1
            goto Lf
        L24:
            int r0 = r7.getEventType()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            r1 = 0
        L29:
            r2 = 1
            if (r0 == r2) goto Lb2
            if (r0 == 0) goto La1
            r2 = 2
            java.lang.String r3 = "StateSet"
            if (r0 == r2) goto L44
            r2 = 3
            if (r0 == r2) goto L38
            goto La4
        L38:
            java.lang.String r0 = r7.getName()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            boolean r0 = r3.equals(r0)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r0 == 0) goto La4
            goto Lb2
        L44:
            java.lang.String r0 = r7.getName()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            int r2 = r0.hashCode()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            switch(r2) {
                case 80204913: goto L73;
                case 1301459538: goto L6a;
                case 1382829617: goto L63;
                case 1901439077: goto L50;
                default: goto L4f;
            }     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
        L4f:
            goto L88
        L50:
            java.lang.String r2 = "Variant"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r2 == 0) goto L88
            androidx.constraintlayout.widget.StateSet$Variant r0 = new androidx.constraintlayout.widget.StateSet$Variant     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            r0.<init>(r6, r7)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r1 == 0) goto La4
            r1.add(r0)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            goto La4
        L63:
            boolean r2 = r0.equals(r3)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r2 == 0) goto L88
            goto La4
        L6a:
            java.lang.String r2 = "LayoutDescription"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r2 == 0) goto L88
            goto La4
        L73:
            java.lang.String r2 = "State"
            boolean r2 = r0.equals(r2)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            if (r2 == 0) goto L88
            androidx.constraintlayout.widget.StateSet$State r1 = new androidx.constraintlayout.widget.StateSet$State     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            r1.<init>(r6, r7)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            android.util.SparseArray<androidx.constraintlayout.widget.StateSet$State> r0 = r5.mStateList     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            int r2 = r1.mId     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            r0.put(r2, r1)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            goto La4
        L88:
            java.lang.String r2 = "ConstraintLayoutStates"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            r3.<init>()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            java.lang.String r4 = "unknown tag "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            java.lang.String r0 = r0.toString()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            android.util.Log.v(r2, r0)     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            goto La4
        La1:
            r7.getName()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
        La4:
            int r0 = r7.next()     // Catch: java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lae
            goto L29
        La9:
            r6 = move-exception
            r6.printStackTrace()
            goto Lb2
        Lae:
            r6 = move-exception
            r6.printStackTrace()
        Lb2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.load(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public boolean needsToChange(int i, float f, float f2) {
        int i2 = this.mCurrentStateId;
        if (i2 != i) {
            return true;
        }
        State stateValueAt = i == -1 ? this.mStateList.valueAt(0) : this.mStateList.get(i2);
        return (this.mCurrentConstraintNumber == -1 || !stateValueAt.mVariants.get(this.mCurrentConstraintNumber).match(f, f2)) && this.mCurrentConstraintNumber != stateValueAt.findMatch(f, f2);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
    }

    public int stateGetConstraintID(int i, int i2, int i3) {
        return updateConstraints(-1, i, i2, i3);
    }

    public int convertToConstraintSet(int i, int i2, float f, float f2) {
        State state = this.mStateList.get(i2);
        if (state == null) {
            return i2;
        }
        if (f == -1.0f || f2 == -1.0f) {
            if (state.mConstraintID != i) {
                Iterator<Variant> it = state.mVariants.iterator();
                while (it.hasNext()) {
                    if (i == it.next().mConstraintID) {
                    }
                }
                return state.mConstraintID;
            }
        } else {
            Iterator<Variant> it2 = state.mVariants.iterator();
            Variant variant = null;
            while (it2.hasNext()) {
                Variant next = it2.next();
                if (next.match(f, f2)) {
                    if (i != next.mConstraintID) {
                        variant = next;
                    }
                }
            }
            if (variant != null) {
                return variant.mConstraintID;
            }
            return state.mConstraintID;
        }
        return i;
    }

    public int updateConstraints(int i, int i2, float f, float f2) {
        State stateValueAt;
        int iFindMatch;
        if (i != i2) {
            State state = this.mStateList.get(i2);
            if (state == null) {
                return -1;
            }
            int iFindMatch2 = state.findMatch(f, f2);
            return iFindMatch2 == -1 ? state.mConstraintID : state.mVariants.get(iFindMatch2).mConstraintID;
        }
        if (i2 == -1) {
            stateValueAt = this.mStateList.valueAt(0);
        } else {
            stateValueAt = this.mStateList.get(this.mCurrentStateId);
        }
        if (stateValueAt == null) {
            return -1;
        }
        return ((this.mCurrentConstraintNumber == -1 || !stateValueAt.mVariants.get(i).match(f, f2)) && i != (iFindMatch = stateValueAt.findMatch(f, f2))) ? iFindMatch == -1 ? stateValueAt.mConstraintID : stateValueAt.mVariants.get(iFindMatch).mConstraintID : i;
    }

    static class State {
        int mConstraintID;
        int mId;
        boolean mIsLayout;
        ArrayList<Variant> mVariants = new ArrayList<>();

        public State(Context context, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
            this.mConstraintID = -1;
            this.mIsLayout = false;
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
                        this.mIsLayout = true;
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
        int mId;
        boolean mIsLayout;
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
            this.mIsLayout = false;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.Variant_constraints) {
                    this.mConstraintID = typedArrayObtainStyledAttributes.getResourceId(index, this.mConstraintID);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.mConstraintID);
                    context.getResources().getResourceName(this.mConstraintID);
                    if ("layout".equals(resourceTypeName)) {
                        this.mIsLayout = true;
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
}
