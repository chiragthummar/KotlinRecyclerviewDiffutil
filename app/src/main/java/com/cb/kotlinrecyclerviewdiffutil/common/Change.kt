package com.cb.kotlinrecyclerviewdiffutil.common

data class Change<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payload:List<Change<T>>) : Change<T> {
    assert(payload.isNotEmpty())

    val firstChange = payload.first()
    val lastChange = payload.last()

    return Change(firstChange.oldData,lastChange.newData)
}
