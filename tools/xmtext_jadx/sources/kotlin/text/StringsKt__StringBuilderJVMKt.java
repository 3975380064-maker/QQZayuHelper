package kotlin.text;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.IOException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: StringBuilderJVM.kt */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\u0010\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001d\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u001f\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0007H\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\tH\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u000bH\u0087\b\u001a\u001d\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a%\u0010\u0006\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a-\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0087\b\u001a-\u0010\f\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0087\b\u001a\u0014\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013*\u00060\u0012j\u0002`\u0013H\u0007\u001a\u001d\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013*\u00060\u0012j\u0002`\u00132\u0006\u0010\u0003\u001a\u00020\u0014H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013*\u00060\u0012j\u0002`\u00132\b\u0010\u0003\u001a\u0004\u0018\u00010\u0010H\u0087\b\u001a\u0014\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001f\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0007H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0015H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0016H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\rH\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0010H\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\bH\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\tH\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u000bH\u0087\b\u001a\u001d\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a\u001f\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0017H\u0087\b\u001a%\u0010\u0011\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u000e\u0010\u0003\u001a\n\u0018\u00010\u0001j\u0004\u0018\u0001`\u0002H\u0087\b\u001a\u0014\u0010\u0018\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002H\u0007\u001a\u001d\u0010\u0019\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\nH\u0087\b\u001a%\u0010\u001b\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0087\b\u001a%\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a%\u0010\u001c\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0005H\u0087\b\u001a5\u0010\u001d\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0087\b\u001a5\u0010\u001d\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00102\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0087\b\u001a!\u0010\u001e\u001a\u00020\u001f*\u00060\u0001j\u0002`\u00022\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0014H\u0087\n\u001a-\u0010 \u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0017H\u0087\b\u001a7\u0010!\u001a\u00020\u001f*\u00060\u0001j\u0002`\u00022\u0006\u0010\"\u001a\u00020\r2\b\b\u0002\u0010#\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u000f\u001a\u00020\nH\u0087\b¨\u0006$"}, d2 = {"append", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "value", "", "", "appendLine", "Ljava/lang/StringBuffer;", "", "", "", "", "appendRange", "", "startIndex", "endIndex", "", "appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "", "", "", "", "clear", "deleteAt", "index", "deleteRange", "insert", "insertRange", "set", "", "setRange", "toCharArray", "destination", "destinationOffset", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/text/StringsKt")
/* loaded from: classes.dex */
public class StringsKt__StringBuilderJVMKt extends StringsKt__RegexExtensionsKt {
    private static final StringBuilder append(StringBuilder sb, byte b) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) b);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    private static final StringBuilder append(StringBuilder sb, short s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) s);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    private static final StringBuilder insert(StringBuilder sb, int i, byte b) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbInsert = sb.insert(i, (int) b);
        Intrinsics.checkNotNullExpressionValue(sbInsert, "insert(...)");
        return sbInsert;
    }

    private static final StringBuilder insert(StringBuilder sb, int i, short s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbInsert = sb.insert(i, (int) s);
        Intrinsics.checkNotNullExpressionValue(sbInsert, "insert(...)");
        return sbInsert;
    }

    public static final StringBuilder clear(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        sb.setLength(0);
        return sb;
    }

    private static final void set(StringBuilder sb, int i, char c) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        sb.setCharAt(i, c);
    }

    private static final StringBuilder setRange(StringBuilder sb, int i, int i2, String value) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbReplace = sb.replace(i, i2, value);
        Intrinsics.checkNotNullExpressionValue(sbReplace, "replace(...)");
        return sbReplace;
    }

    private static final StringBuilder deleteAt(StringBuilder sb, int i) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbDeleteCharAt = sb.deleteCharAt(i);
        Intrinsics.checkNotNullExpressionValue(sbDeleteCharAt, "deleteCharAt(...)");
        return sbDeleteCharAt;
    }

    private static final StringBuilder deleteRange(StringBuilder sb, int i, int i2) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbDelete = sb.delete(i, i2);
        Intrinsics.checkNotNullExpressionValue(sbDelete, "delete(...)");
        return sbDelete;
    }

    static /* synthetic */ void toCharArray$default(StringBuilder sb, char[] destination, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = sb.length();
        }
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        sb.getChars(i2, i3, destination, i);
    }

    private static final void toCharArray(StringBuilder sb, char[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        sb.getChars(i2, i3, destination, i);
    }

    private static final StringBuilder appendRange(StringBuilder sb, char[] value, int i, int i2) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbAppend = sb.append(value, i, i2 - i);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    private static final StringBuilder appendRange(StringBuilder sb, CharSequence value, int i, int i2) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbAppend = sb.append(value, i, i2);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    private static final StringBuilder insertRange(StringBuilder sb, int i, char[] value, int i2, int i3) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbInsert = sb.insert(i, value, i2, i3 - i2);
        Intrinsics.checkNotNullExpressionValue(sbInsert, "insert(...)");
        return sbInsert;
    }

    private static final StringBuilder insertRange(StringBuilder sb, int i, CharSequence value, int i2, int i3) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbInsert = sb.insert(i, value, i2, i3);
        Intrinsics.checkNotNullExpressionValue(sbInsert, "insert(...)");
        return sbInsert;
    }

    private static final StringBuilder appendLine(StringBuilder sb, StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(stringBuffer);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, StringBuilder sb2) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((CharSequence) sb2);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, int i) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(i);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, short s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) s);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, byte b) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) b);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, long j) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(j);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, float f) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(f);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    private static final StringBuilder appendLine(StringBuilder sb, double d) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(d);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append('\n');
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        return sbAppend2;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine()", imports = {}))
    public static final Appendable appendln(Appendable appendable) throws IOException {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendableAppend = appendable.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return appendableAppend;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final Appendable appendln(Appendable appendable, CharSequence charSequence) throws IOException {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendableAppend = appendable.append(charSequence);
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return StringsKt.appendln(appendableAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final Appendable appendln(Appendable appendable, char c) throws IOException {
        Intrinsics.checkNotNullParameter(appendable, "<this>");
        Appendable appendableAppend = appendable.append(c);
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        return StringsKt.appendln(appendableAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine()", imports = {}))
    public static final StringBuilder appendln(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return sbAppend;
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, StringBuffer stringBuffer) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(stringBuffer);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(charSequence);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, String str) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(str);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, Object obj) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(obj);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, StringBuilder sb2) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((CharSequence) sb2);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, char[] value) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        StringBuilder sbAppend = sb.append(value);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, char c) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(c);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, boolean z) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(z);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, int i) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(i);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, short s) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) s);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, byte b) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append((int) b);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, long j) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(j);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, float f) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(f);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use appendLine instead. Note that the new method always appends the line feed character '\\n' regardless of the system line separator.", replaceWith = @ReplaceWith(expression = "appendLine(value)", imports = {}))
    private static final StringBuilder appendln(StringBuilder sb, double d) {
        Intrinsics.checkNotNullParameter(sb, "<this>");
        StringBuilder sbAppend = sb.append(d);
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        return StringsKt.appendln(sbAppend);
    }
}
