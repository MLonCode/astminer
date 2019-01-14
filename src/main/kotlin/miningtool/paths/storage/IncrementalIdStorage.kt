package miningtool.paths.storage

class IncrementalIdStorage<T> {
    private var keyCounter = 0L
    val idPerItem: MutableMap<T, Long> = HashMap()
    private val idCountMap: MutableMap<Long, Long> = HashMap()

    private fun putAndIncrementKey(item: T): Long {
        idPerItem[item] = ++keyCounter
        return keyCounter
    }

    private fun incrementIdCount(id: Long) {
        idCountMap[id] = idCountMap.getOrDefault(id, 0) + 1
    }

    fun record(item: T): Long {
        val id = idPerItem.getOrDefault(item, putAndIncrementKey(item))
        incrementIdCount(id)
        return id
    }

    fun getIdCount(id: Long) = idCountMap.getOrDefault(id, 0)

    fun lookUpValue(id: Long): T? {
        return idPerItem.entries.firstOrNull { it.value == id }?.key
    }
}