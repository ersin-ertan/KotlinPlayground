class LinkedList {

    data class Node(val value: Any, var nextNode: Node? = null)

    var head: Node? = null
    var tail: Node? = null

    var size = 0

    private fun findPrevNode(index: Int): Node = when {
        size == 0 -> throw IllegalStateException("findPrevNode($index) cannot return Node, LinkedList.size = 0")
        index == 1 -> head!!
        index == size -> tail!!
        else -> {
            var cur = head!!
            repeat(index - 1) { cur = cur.nextNode!! }
            cur
        }
    }

    fun add(value: Any, index: Int = size): Boolean = if (index < 0 || index > size) false
    else {
        if (size == 0) Node(value, head?.nextNode).let { head = it; tail = it }
        else if (index == 0) head = Node(value, head)
        else findPrevNode(index).let {
            it.nextNode = Node(value, it.nextNode)
            if (index == size) tail = it.nextNode
        }
        ++size
        true
    }

    fun remove(index: Int = size - 1): Boolean = if (
    index < 0 || index >= size) false
    else {
        if (index == 0) {
            head = head?.nextNode
            if (head == null) tail = null
        } else findPrevNode(index).let {
            it.nextNode = it.nextNode?.nextNode
            if (index == size - 1) tail = it
        }
        --size
        true
    }

    operator fun contains(value: Any): Boolean {
        var cur = head
        while (cur != null) {
            if (cur.value == value) return true
            cur = cur.nextNode
        }
        return false
    }

    override fun toString(): String = buildString {
        append("[")
        head?.let {
            append(it.value)
            var cur = it.nextNode
            while (cur != null) {
                append(", ")
                append(cur.value)
                cur = cur.nextNode
            }
        }
        append("]")
    }
}

fun main(args: Array<String>) {
    val l = LinkedList()
    l.add(1, 0)
    println(l.toString())
    l.add(2, 0)
    println(l.toString())
    l.remove()
    println(l.toString())
    l.add(3)
    println(l.toString())
    l.add(4)
    println(l.toString())
    l.remove(l.size - 1)
    println(l.toString())
    l.add(5)
    println(l.toString())

}

