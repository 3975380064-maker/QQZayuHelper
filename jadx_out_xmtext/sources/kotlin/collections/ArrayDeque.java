package kotlin.collections;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* compiled from: ArrayDeque.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b&\b\u0007\u0018\u0000 [*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001[B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002¢\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b¢\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b¢\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000¢\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000¢\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0018\u0010>\u001a\u00020\u00172\u0006\u0010?\u001a\u00020\u00042\u0006\u0010@\u001a\u00020\u0004H\u0002J\u0010\u0010A\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\b\u0010B\u001a\u00020\u0017H\u0002J\u0015\u0010C\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u0016\u0010D\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010E\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010.J\u000b\u0010F\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010G\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u000b\u0010H\u001a\u00028\u0000¢\u0006\u0002\u0010+J\r\u0010I\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010+J\u0018\u0010J\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u0004H\u0014J\u0018\u0010M\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u0004H\u0002J\u0018\u0010N\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u0004H\u0002J\u0016\u0010O\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010P\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010QJ\u001d\u0010R\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u0004H\u0000¢\u0006\u0002\bSJ\u0017\u0010T\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0000¢\u0006\u0004\bU\u0010VJ)\u0010T\u001a\b\u0012\u0004\u0012\u0002HW0\u000b\"\u0004\b\u0001\u0010W2\f\u0010X\u001a\b\u0012\u0004\u0012\u0002HW0\u000bH\u0000¢\u0006\u0004\bU\u0010YJ\u0015\u0010Z\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0016¢\u0006\u0002\u0010VJ'\u0010Z\u001a\b\u0012\u0004\u0012\u0002HW0\u000b\"\u0004\b\u0001\u0010W2\f\u0010X\u001a\b\u0012\u0004\u0012\u0002HW0\u000bH\u0016¢\u0006\u0002\u0010YR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\\"}, d2 = {"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "nullifyNonEmpty", "internalFromIndex", "internalToIndex", "positiveMod", "registerModification", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "removeRange", "fromIndex", "toIndex", "removeRangeShiftPreceding", "removeRangeShiftSucceeding", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "testRemoveRange", "testRemoveRange$kotlin_stdlib", "testToArray", "testToArray$kotlin_stdlib", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toArray", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ArrayDeque<E> extends AbstractMutableList<E> {
    private static final int defaultMinCapacity = 10;
    private Object[] elementData;
    private int head;
    private int size;
    public static final Companion Companion = new Companion(null);
    private static final Object[] emptyElementData = new Object[0];

    @Override // kotlin.collections.AbstractMutableList
    public int getSize() {
        return this.size;
    }

    public ArrayDeque(int i) {
        Object[] objArr;
        if (i == 0) {
            objArr = emptyElementData;
        } else if (i > 0) {
            objArr = new Object[i];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        this.elementData = objArr;
    }

    public ArrayDeque() {
        this.elementData = emptyElementData;
    }

    public ArrayDeque(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Object[] array = elements.toArray(new Object[0]);
        this.elementData = array;
        this.size = array.length;
        if (array.length == 0) {
            this.elementData = emptyElementData;
        }
    }

    private final void ensureCapacity(int i) {
        if (i < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        Object[] objArr = this.elementData;
        if (i <= objArr.length) {
            return;
        }
        if (objArr == emptyElementData) {
            this.elementData = new Object[RangesKt.coerceAtLeast(i, 10)];
        } else {
            copyElements(AbstractList.Companion.newCapacity$kotlin_stdlib(this.elementData.length, i));
        }
    }

    private final void copyElements(int i) {
        Object[] objArr = new Object[i];
        Object[] objArr2 = this.elementData;
        ArraysKt.copyInto(objArr2, objArr, 0, this.head, objArr2.length);
        Object[] objArr3 = this.elementData;
        int length = objArr3.length;
        int i2 = this.head;
        ArraysKt.copyInto(objArr3, objArr, length - i2, 0, i2);
        this.head = 0;
        this.elementData = objArr;
    }

    private final E internalGet(int i) {
        return (E) this.elementData[i];
    }

    private final int positiveMod(int i) {
        Object[] objArr = this.elementData;
        return i >= objArr.length ? i - objArr.length : i;
    }

    private final int negativeMod(int i) {
        return i < 0 ? i + this.elementData.length : i;
    }

    private final int internalIndex(int i) {
        return positiveMod(this.head + i);
    }

    private final int incremented(int i) {
        if (i == ArraysKt.getLastIndex(this.elementData)) {
            return 0;
        }
        return i + 1;
    }

    private final int decremented(int i) {
        return i == 0 ? ArraysKt.getLastIndex(this.elementData) : i - 1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return size() == 0;
    }

    public final E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        return (E) this.elementData[this.head];
    }

    public final E firstOrNull() {
        if (isEmpty()) {
            return null;
        }
        return (E) this.elementData[this.head];
    }

    public final E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        return (E) this.elementData[positiveMod(this.head + CollectionsKt.getLastIndex(this))];
    }

    public final E lastOrNull() {
        if (isEmpty()) {
            return null;
        }
        return (E) this.elementData[positiveMod(this.head + CollectionsKt.getLastIndex(this))];
    }

    public final void addFirst(E e) {
        registerModification();
        ensureCapacity(size() + 1);
        int iDecremented = decremented(this.head);
        this.head = iDecremented;
        this.elementData[iDecremented] = e;
        this.size = size() + 1;
    }

    public final void addLast(E e) {
        registerModification();
        ensureCapacity(size() + 1);
        this.elementData[positiveMod(this.head + size())] = e;
        this.size = size() + 1;
    }

    public final E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        registerModification();
        Object[] objArr = this.elementData;
        int i = this.head;
        E e = (E) objArr[i];
        objArr[i] = null;
        this.head = incremented(i);
        this.size = size() - 1;
        return e;
    }

    public final E removeFirstOrNull() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    public final E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        registerModification();
        int iPositiveMod = positiveMod(this.head + CollectionsKt.getLastIndex(this));
        Object[] objArr = this.elementData;
        E e = (E) objArr[iPositiveMod];
        objArr[iPositiveMod] = null;
        this.size = size() - 1;
        return e;
    }

    public final E removeLastOrNull() {
        if (isEmpty()) {
            return null;
        }
        return removeLast();
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, size());
        if (i == size()) {
            addLast(e);
            return;
        }
        if (i == 0) {
            addFirst(e);
            return;
        }
        registerModification();
        ensureCapacity(size() + 1);
        int iPositiveMod = positiveMod(this.head + i);
        if (i < ((size() + 1) >> 1)) {
            int iDecremented = decremented(iPositiveMod);
            int iDecremented2 = decremented(this.head);
            int i2 = this.head;
            if (iDecremented >= i2) {
                Object[] objArr = this.elementData;
                objArr[iDecremented2] = objArr[i2];
                ArraysKt.copyInto(objArr, objArr, i2, i2 + 1, iDecremented + 1);
            } else {
                Object[] objArr2 = this.elementData;
                ArraysKt.copyInto(objArr2, objArr2, i2 - 1, i2, objArr2.length);
                Object[] objArr3 = this.elementData;
                objArr3[objArr3.length - 1] = objArr3[0];
                ArraysKt.copyInto(objArr3, objArr3, 0, 1, iDecremented + 1);
            }
            this.elementData[iDecremented] = e;
            this.head = iDecremented2;
        } else {
            int iPositiveMod2 = positiveMod(this.head + size());
            if (iPositiveMod < iPositiveMod2) {
                Object[] objArr4 = this.elementData;
                ArraysKt.copyInto(objArr4, objArr4, iPositiveMod + 1, iPositiveMod, iPositiveMod2);
            } else {
                Object[] objArr5 = this.elementData;
                ArraysKt.copyInto(objArr5, objArr5, 1, 0, iPositiveMod2);
                Object[] objArr6 = this.elementData;
                objArr6[0] = objArr6[objArr6.length - 1];
                ArraysKt.copyInto(objArr6, objArr6, iPositiveMod + 1, iPositiveMod, objArr6.length - 1);
            }
            this.elementData[iPositiveMod] = e;
        }
        this.size = size() + 1;
    }

    private final void copyCollectionElements(int i, Collection<? extends E> collection) {
        Iterator<? extends E> it = collection.iterator();
        int length = this.elementData.length;
        while (i < length && it.hasNext()) {
            this.elementData[i] = it.next();
            i++;
        }
        int i2 = this.head;
        for (int i3 = 0; i3 < i2 && it.hasNext(); i3++) {
            this.elementData[i3] = it.next();
        }
        this.size = size() + collection.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        if (elements.isEmpty()) {
            return false;
        }
        registerModification();
        ensureCapacity(size() + elements.size());
        copyCollectionElements(positiveMod(this.head + size()), elements);
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(i, size());
        if (elements.isEmpty()) {
            return false;
        }
        if (i == size()) {
            return addAll(elements);
        }
        registerModification();
        ensureCapacity(size() + elements.size());
        int iPositiveMod = positiveMod(this.head + size());
        int iPositiveMod2 = positiveMod(this.head + i);
        int size = elements.size();
        if (i < ((size() + 1) >> 1)) {
            int i2 = this.head;
            int length = i2 - size;
            if (iPositiveMod2 < i2) {
                Object[] objArr = this.elementData;
                ArraysKt.copyInto(objArr, objArr, length, i2, objArr.length);
                if (size >= iPositiveMod2) {
                    Object[] objArr2 = this.elementData;
                    ArraysKt.copyInto(objArr2, objArr2, objArr2.length - size, 0, iPositiveMod2);
                } else {
                    Object[] objArr3 = this.elementData;
                    ArraysKt.copyInto(objArr3, objArr3, objArr3.length - size, 0, size);
                    Object[] objArr4 = this.elementData;
                    ArraysKt.copyInto(objArr4, objArr4, 0, size, iPositiveMod2);
                }
            } else if (length >= 0) {
                Object[] objArr5 = this.elementData;
                ArraysKt.copyInto(objArr5, objArr5, length, i2, iPositiveMod2);
            } else {
                Object[] objArr6 = this.elementData;
                length += objArr6.length;
                int i3 = iPositiveMod2 - i2;
                int length2 = objArr6.length - length;
                if (length2 >= i3) {
                    ArraysKt.copyInto(objArr6, objArr6, length, i2, iPositiveMod2);
                } else {
                    ArraysKt.copyInto(objArr6, objArr6, length, i2, i2 + length2);
                    Object[] objArr7 = this.elementData;
                    ArraysKt.copyInto(objArr7, objArr7, 0, this.head + length2, iPositiveMod2);
                }
            }
            this.head = length;
            copyCollectionElements(negativeMod(iPositiveMod2 - size), elements);
        } else {
            int i4 = iPositiveMod2 + size;
            if (iPositiveMod2 < iPositiveMod) {
                int i5 = size + iPositiveMod;
                Object[] objArr8 = this.elementData;
                if (i5 <= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i4, iPositiveMod2, iPositiveMod);
                } else if (i4 >= objArr8.length) {
                    ArraysKt.copyInto(objArr8, objArr8, i4 - objArr8.length, iPositiveMod2, iPositiveMod);
                } else {
                    int length3 = iPositiveMod - (i5 - objArr8.length);
                    ArraysKt.copyInto(objArr8, objArr8, 0, length3, iPositiveMod);
                    Object[] objArr9 = this.elementData;
                    ArraysKt.copyInto(objArr9, objArr9, i4, iPositiveMod2, length3);
                }
            } else {
                Object[] objArr10 = this.elementData;
                ArraysKt.copyInto(objArr10, objArr10, size, 0, iPositiveMod);
                Object[] objArr11 = this.elementData;
                if (i4 >= objArr11.length) {
                    ArraysKt.copyInto(objArr11, objArr11, i4 - objArr11.length, iPositiveMod2, objArr11.length);
                } else {
                    ArraysKt.copyInto(objArr11, objArr11, 0, objArr11.length - size, objArr11.length);
                    Object[] objArr12 = this.elementData;
                    ArraysKt.copyInto(objArr12, objArr12, i4, iPositiveMod2, objArr12.length - size);
                }
            }
            copyCollectionElements(iPositiveMod2, elements);
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public E get(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        return (E) this.elementData[positiveMod(this.head + i)];
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public E set(int i, E e) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        int iPositiveMod = positiveMod(this.head + i);
        Object[] objArr = this.elementData;
        E e2 = (E) objArr[iPositiveMod];
        objArr[iPositiveMod] = e;
        return e2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        int i;
        int iPositiveMod = positiveMod(this.head + size());
        int length = this.head;
        if (length < iPositiveMod) {
            while (length < iPositiveMod) {
                if (Intrinsics.areEqual(obj, this.elementData[length])) {
                    i = this.head;
                } else {
                    length++;
                }
            }
            return -1;
        }
        if (length < iPositiveMod) {
            return -1;
        }
        int length2 = this.elementData.length;
        while (true) {
            if (length >= length2) {
                for (int i2 = 0; i2 < iPositiveMod; i2++) {
                    if (Intrinsics.areEqual(obj, this.elementData[i2])) {
                        length = i2 + this.elementData.length;
                        i = this.head;
                    }
                }
                return -1;
            }
            if (Intrinsics.areEqual(obj, this.elementData[length])) {
                i = this.head;
                break;
            }
            length++;
        }
        return length - i;
    }

    @Override // java.util.AbstractList, java.util.List
    public int lastIndexOf(Object obj) {
        int lastIndex;
        int i;
        int iPositiveMod = positiveMod(this.head + size());
        int i2 = this.head;
        if (i2 < iPositiveMod) {
            lastIndex = iPositiveMod - 1;
            if (i2 <= lastIndex) {
                while (!Intrinsics.areEqual(obj, this.elementData[lastIndex])) {
                    if (lastIndex != i2) {
                        lastIndex--;
                    }
                }
                i = this.head;
                return lastIndex - i;
            }
            return -1;
        }
        if (i2 > iPositiveMod) {
            int i3 = iPositiveMod - 1;
            while (true) {
                if (-1 < i3) {
                    if (Intrinsics.areEqual(obj, this.elementData[i3])) {
                        lastIndex = i3 + this.elementData.length;
                        i = this.head;
                        break;
                    }
                    i3--;
                } else {
                    lastIndex = ArraysKt.getLastIndex(this.elementData);
                    int i4 = this.head;
                    if (i4 <= lastIndex) {
                        while (!Intrinsics.areEqual(obj, this.elementData[lastIndex])) {
                            if (lastIndex != i4) {
                                lastIndex--;
                            }
                        }
                        i = this.head;
                    }
                }
            }
        }
        return -1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    @Override // kotlin.collections.AbstractMutableList
    public E removeAt(int i) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(i, size());
        ArrayDeque<E> arrayDeque = this;
        if (i == CollectionsKt.getLastIndex(arrayDeque)) {
            return removeLast();
        }
        if (i == 0) {
            return removeFirst();
        }
        registerModification();
        int iPositiveMod = positiveMod(this.head + i);
        E e = (E) this.elementData[iPositiveMod];
        if (i < (size() >> 1)) {
            int i2 = this.head;
            if (iPositiveMod >= i2) {
                Object[] objArr = this.elementData;
                ArraysKt.copyInto(objArr, objArr, i2 + 1, i2, iPositiveMod);
            } else {
                Object[] objArr2 = this.elementData;
                ArraysKt.copyInto(objArr2, objArr2, 1, 0, iPositiveMod);
                Object[] objArr3 = this.elementData;
                objArr3[0] = objArr3[objArr3.length - 1];
                int i3 = this.head;
                ArraysKt.copyInto(objArr3, objArr3, i3 + 1, i3, objArr3.length - 1);
            }
            Object[] objArr4 = this.elementData;
            int i4 = this.head;
            objArr4[i4] = null;
            this.head = incremented(i4);
        } else {
            int iPositiveMod2 = positiveMod(this.head + CollectionsKt.getLastIndex(arrayDeque));
            if (iPositiveMod <= iPositiveMod2) {
                Object[] objArr5 = this.elementData;
                ArraysKt.copyInto(objArr5, objArr5, iPositiveMod, iPositiveMod + 1, iPositiveMod2 + 1);
            } else {
                Object[] objArr6 = this.elementData;
                ArraysKt.copyInto(objArr6, objArr6, iPositiveMod, iPositiveMod + 1, objArr6.length);
                Object[] objArr7 = this.elementData;
                objArr7[objArr7.length - 1] = objArr7[0];
                ArraysKt.copyInto(objArr7, objArr7, 0, 1, iPositiveMod2 + 1);
            }
            this.elementData[iPositiveMod2] = null;
        }
        this.size = size() - 1;
        return e;
    }

    private final boolean filterInPlace(Function1<? super E, Boolean> function1) {
        int iPositiveMod;
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.elementData.length != 0) {
            int iPositiveMod2 = positiveMod(this.head + size());
            int i = this.head;
            if (i < iPositiveMod2) {
                iPositiveMod = i;
                while (i < iPositiveMod2) {
                    Object obj = this.elementData[i];
                    if (function1.invoke(obj).booleanValue()) {
                        this.elementData[iPositiveMod] = obj;
                        iPositiveMod++;
                    } else {
                        z = true;
                    }
                    i++;
                }
                ArraysKt.fill(this.elementData, (Object) null, iPositiveMod, iPositiveMod2);
            } else {
                int length = this.elementData.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.elementData;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (function1.invoke(obj2).booleanValue()) {
                        this.elementData[i2] = obj2;
                        i2++;
                    } else {
                        z2 = true;
                    }
                    i++;
                }
                iPositiveMod = positiveMod(i2);
                for (int i3 = 0; i3 < iPositiveMod2; i3++) {
                    Object[] objArr2 = this.elementData;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (function1.invoke(obj3).booleanValue()) {
                        this.elementData[iPositiveMod] = obj3;
                        iPositiveMod = incremented(iPositiveMod);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                registerModification();
                this.size = negativeMod(iPositiveMod - this.head);
            }
        }
        return z;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (!isEmpty()) {
            registerModification();
            nullifyNonEmpty(this.head, positiveMod(this.head + size()));
        }
        this.head = 0;
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (array.length < size()) {
            array = (T[]) ArraysKt.arrayOfNulls(array, size());
        }
        T[] tArr = array;
        int iPositiveMod = positiveMod(this.head + size());
        int i = this.head;
        if (i < iPositiveMod) {
            ArraysKt.copyInto$default(this.elementData, tArr, 0, i, iPositiveMod, 2, (Object) null);
        } else if (!isEmpty()) {
            Object[] objArr = this.elementData;
            ArraysKt.copyInto(objArr, tArr, 0, this.head, objArr.length);
            Object[] objArr2 = this.elementData;
            ArraysKt.copyInto(objArr2, tArr, objArr2.length - this.head, 0, iPositiveMod);
        }
        return (T[]) CollectionsKt.terminateCollectionToArray(size(), tArr);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    @Override // java.util.AbstractList
    protected void removeRange(int i, int i2) {
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(i, i2, size());
        int i3 = i2 - i;
        if (i3 == 0) {
            return;
        }
        if (i3 == size()) {
            clear();
            return;
        }
        if (i3 == 1) {
            remove(i);
            return;
        }
        registerModification();
        if (i < size() - i2) {
            removeRangeShiftPreceding(i, i2);
            int iPositiveMod = positiveMod(this.head + i3);
            nullifyNonEmpty(this.head, iPositiveMod);
            this.head = iPositiveMod;
        } else {
            removeRangeShiftSucceeding(i, i2);
            int iPositiveMod2 = positiveMod(this.head + size());
            nullifyNonEmpty(negativeMod(iPositiveMod2 - i3), iPositiveMod2);
        }
        this.size = size() - i3;
    }

    private final void removeRangeShiftPreceding(int i, int i2) {
        int iPositiveMod = positiveMod(this.head + (i - 1));
        int iPositiveMod2 = positiveMod(this.head + (i2 - 1));
        while (i > 0) {
            int i3 = iPositiveMod + 1;
            int iMin = Math.min(i, Math.min(i3, iPositiveMod2 + 1));
            Object[] objArr = this.elementData;
            int i4 = iPositiveMod2 - iMin;
            int i5 = iPositiveMod - iMin;
            ArraysKt.copyInto(objArr, objArr, i4 + 1, i5 + 1, i3);
            iPositiveMod = negativeMod(i5);
            iPositiveMod2 = negativeMod(i4);
            i -= iMin;
        }
    }

    private final void removeRangeShiftSucceeding(int i, int i2) {
        int iPositiveMod = positiveMod(this.head + i2);
        int iPositiveMod2 = positiveMod(this.head + i);
        int size = size();
        while (true) {
            size -= i2;
            if (size <= 0) {
                return;
            }
            Object[] objArr = this.elementData;
            i2 = Math.min(size, Math.min(objArr.length - iPositiveMod, objArr.length - iPositiveMod2));
            Object[] objArr2 = this.elementData;
            int i3 = iPositiveMod + i2;
            ArraysKt.copyInto(objArr2, objArr2, iPositiveMod2, iPositiveMod, i3);
            iPositiveMod = positiveMod(i3);
            iPositiveMod2 = positiveMod(iPositiveMod2 + i2);
        }
    }

    private final void nullifyNonEmpty(int i, int i2) {
        if (i < i2) {
            ArraysKt.fill(this.elementData, (Object) null, i, i2);
            return;
        }
        Object[] objArr = this.elementData;
        ArraysKt.fill(objArr, (Object) null, i, objArr.length);
        ArraysKt.fill(this.elementData, (Object) null, 0, i2);
    }

    private final void registerModification() {
        this.modCount++;
    }

    public final <T> T[] testToArray$kotlin_stdlib(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) toArray(array);
    }

    public final Object[] testToArray$kotlin_stdlib() {
        return toArray();
    }

    public final void testRemoveRange$kotlin_stdlib(int i, int i2) {
        removeRange(i, i2);
    }

    /* compiled from: ArrayDeque.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lkotlin/collections/ArrayDeque$Companion;", "", "()V", "defaultMinCapacity", "", "emptyElementData", "", "[Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final void internalStructure$kotlin_stdlib(Function2<? super Integer, ? super Object[], Unit> structure) {
        int i;
        Intrinsics.checkNotNullParameter(structure, "structure");
        structure.invoke(Integer.valueOf((isEmpty() || (i = this.head) < positiveMod(this.head + size())) ? this.head : i - this.elementData.length), toArray());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<? extends Object> elements) {
        int iPositiveMod;
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.elementData.length != 0) {
            int iPositiveMod2 = positiveMod(this.head + size());
            int i = this.head;
            if (i < iPositiveMod2) {
                iPositiveMod = i;
                while (i < iPositiveMod2) {
                    Object obj = this.elementData[i];
                    if (elements.contains(obj)) {
                        z = true;
                    } else {
                        this.elementData[iPositiveMod] = obj;
                        iPositiveMod++;
                    }
                    i++;
                }
                ArraysKt.fill(this.elementData, (Object) null, iPositiveMod, iPositiveMod2);
            } else {
                int length = this.elementData.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.elementData;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (elements.contains(obj2)) {
                        z2 = true;
                    } else {
                        this.elementData[i2] = obj2;
                        i2++;
                    }
                    i++;
                }
                iPositiveMod = positiveMod(i2);
                for (int i3 = 0; i3 < iPositiveMod2; i3++) {
                    Object[] objArr2 = this.elementData;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (elements.contains(obj3)) {
                        z2 = true;
                    } else {
                        this.elementData[iPositiveMod] = obj3;
                        iPositiveMod = incremented(iPositiveMod);
                    }
                }
                z = z2;
            }
            if (z) {
                registerModification();
                this.size = negativeMod(iPositiveMod - this.head);
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean retainAll(Collection<? extends Object> elements) {
        int iPositiveMod;
        Intrinsics.checkNotNullParameter(elements, "elements");
        boolean z = false;
        z = false;
        z = false;
        if (!isEmpty() && this.elementData.length != 0) {
            int iPositiveMod2 = positiveMod(this.head + size());
            int i = this.head;
            if (i < iPositiveMod2) {
                iPositiveMod = i;
                while (i < iPositiveMod2) {
                    Object obj = this.elementData[i];
                    if (elements.contains(obj)) {
                        this.elementData[iPositiveMod] = obj;
                        iPositiveMod++;
                    } else {
                        z = true;
                    }
                    i++;
                }
                ArraysKt.fill(this.elementData, (Object) null, iPositiveMod, iPositiveMod2);
            } else {
                int length = this.elementData.length;
                boolean z2 = false;
                int i2 = i;
                while (i < length) {
                    Object[] objArr = this.elementData;
                    Object obj2 = objArr[i];
                    objArr[i] = null;
                    if (elements.contains(obj2)) {
                        this.elementData[i2] = obj2;
                        i2++;
                    } else {
                        z2 = true;
                    }
                    i++;
                }
                iPositiveMod = positiveMod(i2);
                for (int i3 = 0; i3 < iPositiveMod2; i3++) {
                    Object[] objArr2 = this.elementData;
                    Object obj3 = objArr2[i3];
                    objArr2[i3] = null;
                    if (elements.contains(obj3)) {
                        this.elementData[iPositiveMod] = obj3;
                        iPositiveMod = incremented(iPositiveMod);
                    } else {
                        z2 = true;
                    }
                }
                z = z2;
            }
            if (z) {
                registerModification();
                this.size = negativeMod(iPositiveMod - this.head);
            }
        }
        return z;
    }
}
