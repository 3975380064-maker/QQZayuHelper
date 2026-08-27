jadx.core.utils.exceptions.JadxRuntimeException: Failed to generate code for class: kotlin.collections.unsigned.UArraysKt___UArraysKt
	at jadx.core.ProcessClass.generateCode(ProcessClass.java:123)
	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
Caused by: java.lang.OutOfMemoryError: Failed to allocate a 2478664 byte allocation with 2439696 free bytes and 2382KB until OOM, target footprint 536870912, growth limit 536870912
	at java.util.Arrays.copyOf(Arrays.java:3257)
	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:448)
	at java.lang.StringBuilder.append(StringBuilder.java:137)

