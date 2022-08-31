/*
39. Combination Sum
https://leetcode.com/problems/combination-sum/
*/

fun main(args: Array<String>) {
    val tester = CombinationSum()
    println(tester.combinationSum(intArrayOf(100, 200, 4, 12), 400))
}

class CombinationSum {

    private val memo: HashMap<IncludedList, HashSet<IncludedList>> = hashMapOf()

    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val toReturn: MutableList<List<Int>> = mutableListOf()
        val solutions = helper(candidates, target, IncludedList(IntArray(candidates.size)))
        solutions.forEach { solution ->
            val toAdd: MutableList<Int> = mutableListOf()
            solution.includedList.forEachIndexed { index, value ->
                for (i in 0 until value) {
                    toAdd.add(candidates[index])
                }
            }
            toReturn.add(toAdd)
        }
        return toReturn
    }

    private fun helper(candidates: IntArray, target: Int, currIncluded: IncludedList): HashSet<IncludedList> {
        if (target == 0)
            return hashSetOf(currIncluded)
        if (target < 0)
            return hashSetOf()
        if(memo.containsKey(currIncluded)){
            return memo[currIncluded]!!
        }
        val toReturn: HashSet<IncludedList> = hashSetOf()
        candidates.forEachIndexed { index, it ->
            val newIncluded = currIncluded.includedList.copyOf()
            newIncluded[index] += 1
            toReturn.addAll(helper(candidates, target-it, IncludedList(newIncluded)))
        }
        memo[currIncluded] = toReturn
        return toReturn
    }

    data class IncludedList(
        val includedList: IntArray
    ) {
        override fun equals(other: Any?): Boolean {
            if (other !is IncludedList || this.includedList.size != other.includedList.size)
                return false
            includedList.forEachIndexed { index, value ->
                if (value != other.includedList[index])
                    return false
            }
            return true
        }

        override fun hashCode(): Int {
            return includedList.contentHashCode()
        }
    }
}