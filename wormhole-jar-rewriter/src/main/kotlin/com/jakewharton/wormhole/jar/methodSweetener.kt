package com.jakewharton.wormhole.jar

import org.objectweb.asm.Opcodes.ACC_PUBLIC

internal val methodSweetener = mapOf<String, (Method) -> Method>(
    "java/lang/String#isBlank()Z" to {
      it.copy(access = ACC_PUBLIC)
    },
    "java/lang/String#repeat(I)Ljava/lang/String;" to {
      it.copy(access = ACC_PUBLIC)
    },
    "java/lang/String#strip()Ljava/lang/String;" to {
      it.copy(access = ACC_PUBLIC)
    },
    "java/lang/String#stripLeading()Ljava/lang/String;" to {
      it.copy(access = ACC_PUBLIC)
    },
    "java/lang/String#stripTrailing()Ljava/lang/String;" to {
      it.copy(access = ACC_PUBLIC)
    },
    "java/util/List#copyOf(Ljava/util/Collection;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(Ljava/util/Collection<TE;>;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of()Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>()Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/List<TE;>;")
    },
    "java/util/List#of([Ljava/lang/Object;)Ljava/util/List;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>([TE;)Ljava/util/List<TE;>;")
    },
    "java/util/Map#copyOf(Ljava/util/Map;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(Ljava/util/Map<TK;TV;>;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#entry(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map\$Entry;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;)Ljava/util/Map\$Entry<TK;TV;>;")
    },
    "java/util/Map#of()Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>()Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>(TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;TK;TV;)Ljava/util/Map<TK;TV;>;")
    },
    "java/util/Map#ofEntries([Ljava/util/Map\$Entry;)Ljava/util/Map;" to {
      it.copy(signature = "<K:Ljava/lang/Object;V:Ljava/lang/Object;>([Ljava/util/Map\$Entry<TK;TV;>;)Ljava/util/Map<TK;TV;>;")
    },
    // TODO java/util/Objects#compare(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;)I
    "java/util/Objects#requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;" to {
      it.copy(signature = "<T:Ljava/lang/Object;>(TT;)TT;")
    },
    "java/util/Objects#requireNonNull(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;" to {
      it.copy(signature = "<T:Ljava/lang/Object;>(TT;Ljava/lang/String;)TT;")
    },
    // TODO java/util/Objects#requireNonNullElse(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    // TODO java/util/Objects#requireNonNullElseGet(Ljava/lang/Object;Ljava/util/function/Supplier;)Ljava/lang/Object;
    "java/util/Set#copyOf(Ljava/util/Collection;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(Ljava/util/Collection<TE;>;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of()Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>()Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>(TE;TE;TE;TE;TE;TE;TE;TE;TE;TE;)Ljava/util/Set<TE;>;")
    },
    "java/util/Set#of([Ljava/lang/Object;)Ljava/util/Set;" to {
      it.copy(signature = "<E:Ljava/lang/Object;>([TE;)Ljava/util/Set<TE;>;")
    }
)
