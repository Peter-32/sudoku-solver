package sudoku

import scala.collection.mutable.ArrayBuffer
//throw new Error(s"Update to block $idx uses invalid row and column: ($i)($j)")
/**
  * Created by Peter on 6/9/2016.
  */

/**
  * Define your Sudoku puzzle here
  */
object Puzzle {
  /**
    * Purpose: This is the puzzle you're trying to solve.  The zero values are unknown elements.
    */
  val value = Array(
    Array(8,0,0,0,0,5,4,0,0),
    Array(0,0,0,9,0,1,0,3,0),
    Array(0,7,0,0,2,0,8,9,0),
    Array(0,0,0,0,0,9,0,5,2),
    Array(0,0,8,0,0,0,3,0,0),
    Array(1,9,0,6,0,0,0,0,0),
    Array(0,2,5,0,1,0,0,8,0),
    Array(0,8,0,2,0,4,0,0,0),
    Array(0,0,6,5,0,0,0,0,3))
}

object puzzleUtils {
  /**
    * Purpose: To get the Puzzle.value indices of nine elements given a block number.  The blocks are numbered like:
    *            0 1 2
    *            3 4 5
    *            6 7 8
    */
  val blockIdxToIndices = {
    Map(1 -> Array((0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)),
      2 -> Array((0, 3), (0, 4), (0, 5), (1, 3), (1, 4), (1, 5), (2, 3), (2, 4), (2, 5)),
      3 -> Array((0, 6), (0, 7), (0, 8), (1, 6), (1, 7), (1, 8), (2, 6), (2, 7), (2, 8)),
      4 -> Array((3, 0), (3, 1), (3, 2), (4, 0), (4, 1), (4, 2), (5, 0), (5, 1), (5, 2)),
      5 -> Array((3, 3), (3, 4), (3, 5), (4, 3), (4, 4), (4, 5), (5, 3), (5, 4), (5, 5)),
      6 -> Array((3, 6), (3, 7), (3, 8), (4, 6), (4, 7), (4, 8), (5, 6), (5, 7), (5, 8)),
      7 -> Array((6, 0), (6, 1), (6, 2), (7, 0), (7, 1), (7, 2), (8, 0), (8, 1), (8, 2)),
      8 -> Array((6, 3), (6, 4), (6, 5), (7, 3), (7, 4), (7, 5), (8, 3), (8, 4), (8, 5)),
      9 -> Array((6, 6), (6, 7), (6, 8), (7, 6), (7, 7), (7, 8), (8, 6), (8, 7), (8, 8)))
  }
}

/**
  * Purpose: A constructor to make block objects
  * @param idx is the block number of a sudoku puzzle
  *            there are 9 blocks and they are positioned like this:
  *            0 1 2
  *            3 4 5
  *            6 7 8
  */
class block(val idx: Int) {
  /**
    *  Purpose: Get the i, j locations
    */
  val puzzleIndices: Array[(Int,Int)] = puzzleUtils.blockIdxToIndices(idx)

  /**
    *  Purpose: Get the values at i, j locations
    *          So if "elementValues" will become Array(1,3,2,4,5,6,7,8,9) if the block has values
    *          1 3 2
    *          4 5 6
    *          7 8 9
    */
  var elementValues: Array[Int] = Array()

  /**
    *  Purpose: Hold a list of the remaining unknown values.
    */
  var remainingUnknownValues: ArrayBuffer[Int] = ArrayBuffer()

  /**
    *  Purpose: Call when ready to test for duplicates within the block
    */
  private def blockHasDuplicates = {
    (elementValues count(_ == 1)) <= 1 ||
      (elementValues count(_ == 2)) <= 1 ||
      (elementValues count(_ == 3)) <= 1 ||
      (elementValues count(_ == 4)) <= 1 ||
      (elementValues count(_ == 5)) <= 1 ||
      (elementValues count(_ == 6)) <= 1 ||
      (elementValues count(_ == 7)) <= 1 ||
      (elementValues count(_ == 8)) <= 1 ||
      (elementValues count(_ == 9)) <= 1
  }

  /**
    *  Purpose: Call when ready to check if the block is completed
    */
  private def isCompleted: Boolean = elementValues.sum == 45

  // If true the block is completed
  var complete: Boolean = false

  /**
    *  Purpose: The puzzle is updated and we need to sync up with it.
    */
  def thisBlockHasBeenUpdated: Unit = {
    // Get the newest values
    elementValues = for ((i,j) <- puzzleIndices) yield Puzzle.value(i)(j)
    // Find the remaining values
    remainingUnknownValues = ArrayBuffer(1,2,3,4,5,6,7,8,9) --= elementValues
    // Test for duplicates
    assert(blockHasDuplicates)
    // Test if block is completed
    if (isCompleted) complete = true
  }

  // Upon construction of this object, call the function "theBlockHasBeenUpdated"
  // in order to populate elementValues, populate remainingUnknownValues, run a
  // test for duplication, and see if the block is already completed.
  thisBlockHasBeenUpdated








}


object sudoku extends App {


  /**
    * Purpose: This will print out the values in object myPuzzle.puzzle
    * This generates an output of the values in the sudoku puzzle.
    */
  def printPuzzle: Unit = {
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      if (j == 3 || j == 6) print("|")
      print(" " + Puzzle.value(i)(j) + " ")
      if (j == 8) println()
      if (j == 8 && (i == 2 || i == 5)) println(" - - - -   - - - -   - - - - ")
    }
  }

  printPuzzle

  val block1 = new block(1)
  println("block1 is complete:")
  println(block1.complete)
  println("block1 has element values:")
  block1.elementValues.foreach(println)
  println("block1 is block index:")
  println(block1.idx)
  println("block1 has puzzle indices:")
  block1.puzzleIndices.foreach(println)






}
