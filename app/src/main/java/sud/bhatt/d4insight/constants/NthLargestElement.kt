package sud.bhatt.d4insight.constants

import java.util.Random


fun main() {
    val arr = arrayOf(2, 4, 1, 3, 6, 7)
//    println(arr[0])
//    arr.sort()
//    print(arr[2])

//    val N = arr.size
//    val stdRandom = Random()
    for(i in arr.indices){
        val random = arr.random()//stdRandom.nextInt(i)
        println(random)
    }

    arr.forEachIndexed{index, element->
        println("------")
        val random = arr.random()

        println(random)

    }
    for (i in 1..4){

    }

}