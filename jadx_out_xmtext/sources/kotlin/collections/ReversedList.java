package kotlin.collections;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableListIterator;

/* compiled from: ReversedViews.kt */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010)\n\u0000\n\u0002\u0010+\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u001d\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u000bH\u0016J\u0016\u0010\u0010\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\u0011J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0096\u0002J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\f\u001a\u00020\u0007H\u0016J\u0015\u0010\u0016\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010\u0011J\u001e\u0010\u0017\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0018R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0019"}, d2 = {"Lkotlin/collections/ReversedList;", "T", "Lkotlin/collections/AbstractMutableList;", "delegate", "", "(Ljava/util/List;)V", "size", "", "getSize", "()I", "add", "", "index", "element", "(ILjava/lang/Object;)V", "clear", "get", "(I)Ljava/lang/Object;", "iterator", "", "listIterator", "", "removeAt", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
final class ReversedList<T> extends AbstractMutableList<T> {
    private final List<T> delegate;

    public ReversedList(List<T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // kotlin.collections.AbstractMutableList
    public int getSize() {
        return this.delegate.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        return this.delegate.get(CollectionsKt__ReversedViewsKt.reverseElementIndex$CollectionsKt__ReversedViewsKt(this, i));
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.delegate.clear();
    }

    @Override // kotlin.collections.AbstractMutableList
    public T removeAt(int i) {
        return this.delegate.remove(CollectionsKt__ReversedViewsKt.reverseElementIndex$CollectionsKt__ReversedViewsKt(this, i));
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public T set(int i, T t) {
        return this.delegate.set(CollectionsKt__ReversedViewsKt.reverseElementIndex$CollectionsKt__ReversedViewsKt(this, i), t);
    }

    @Override // kotlin.collections.AbstractMutableList, java.util.AbstractList, java.util.List
    public void add(int i, T t) {
        this.delegate.add(CollectionsKt__ReversedViewsKt.reversePositionIndex$CollectionsKt__ReversedViewsKt(this, i), t);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<T> iterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    /* compiled from: ReversedViews.kt */
    @Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0010+\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\bJ\t\u0010\t\u001a\u00020\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\nH\u0016J\u000e\u0010\f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\r\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\rJ\b\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0006H\u0016J\u0015\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0014"}, d2 = {"kotlin/collections/ReversedList$listIterator$1", "", "delegateIterator", "getDelegateIterator", "()Ljava/util/ListIterator;", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "", "previous", "previousIndex", "remove", "set", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* renamed from: kotlin.collections.ReversedList$listIterator$1, reason: invalid class name */
    public static final class AnonymousClass1 implements ListIterator<T>, KMutableListIterator {
        private final ListIterator<T> delegateIterator;
        final /* synthetic */ ReversedList<T> this$0;

        AnonymousClass1(ReversedList<T> reversedList, int i) {
            this.this$0 = reversedList;
            this.delegateIterator = ((ReversedList) reversedList).delegate.listIterator(CollectionsKt__ReversedViewsKt.reversePositionIndex$CollectionsKt__ReversedViewsKt(reversedList, i));
        }

        public final ListIterator<T> getDelegateIterator() {
            return this.delegateIterator;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.delegateIterator.hasPrevious();
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.delegateIterator.hasNext();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public T next() {
            return this.delegateIterator.previous();
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return CollectionsKt__ReversedViewsKt.reverseIteratorIndex$CollectionsKt__ReversedViewsKt(this.this$0, this.delegateIterator.previousIndex());
        }

        @Override // java.util.ListIterator
        public T previous() {
            return this.delegateIterator.next();
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return CollectionsKt__ReversedViewsKt.reverseIteratorIndex$CollectionsKt__ReversedViewsKt(this.this$0, this.delegateIterator.nextIndex());
        }

        @Override // java.util.ListIterator
        public void add(T t) {
            this.delegateIterator.add(t);
            this.delegateIterator.previous();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            this.delegateIterator.remove();
        }

        @Override // java.util.ListIterator
        public void set(T t) {
            this.delegateIterator.set(t);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<T> listIterator(int i) {
        return new AnonymousClass1(this, i);
    }
}
