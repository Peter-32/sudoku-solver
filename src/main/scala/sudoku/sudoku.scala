package sudoku
import scala.collection.mutable.ArrayBuffer
/**
  * Created by Peter on 6/9/2016.
  */

/**
  * Define your Sudoku puzzle here
  */
object Input {
  /**
    * This is the puzzle you're trying to solve.  The zero values are unknown elements.
    *
    */
  val value = Array(
    Array(2,0,0,1,0,3,0,6,5),
    Array(9,0,3,0,0,0,0,0,0),
    Array(0,5,0,0,0,4,0,0,0),
    Array(3,2,0,8,0,9,5,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,1,4,0,5,0,7,6),
    Array(0,0,0,2,0,0,0,5,0),
    Array(0,0,0,0,0,0,8,0,1),
    Array(1,4,0,5,0,7,0,0,9))

   // Use this template to recreate a new puzzle
/*  val value = Array(
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0),
    Array(0,0,0,0,0,0,0,0,0))*/
}

object sudoku extends App {
  /**
    * Used to create element objects.  Each small square on the sudoku puzzle is an element object.
    */
  class elementObject {
    var i: Int = 0
    var j: Int = 0
    var value = 0
    var hasValue: Boolean = Input.value(i)(j) != 0
    var remainingValues: ArrayBuffer[Int] = ArrayBuffer(1,2,3,4,5,6,7,8,9)
  }

  /**
    * Given i, j, what block number is it?
    *
    * @param i is the row.
    * @param j is the column.
    * @return returns block 0 to 8
    */
  def indexToBlockNumber(i: Int, j: Int) = {
    if (i < 3) {
      if (j < 3) 0 else if (j < 6) 1 else 2
    } else if (i < 6) {
      if (j < 3) 3 else if (j < 6) 4 else 5
    } else {
      if (j < 3) 6 else if (j < 6) 7 else 8
    }
  }

  /**
    * A Map that takes a key="Block Index" and returns a value="Array of i, j indices".
    * There are 9 blocks and these are their index positions in the puzzle.
    *            0 1 2
    *            3 4 5
    *            6 7 8
    * Element indices within a block have the same layout, left to right, top to bottom, from 0 to 8.
    */
  val blockIdxToIndices = {
    Map(0 -> Array((0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)),
      1 -> Array((0, 3), (0, 4), (0, 5), (1, 3), (1, 4), (1, 5), (2, 3), (2, 4), (2, 5)),
      2 -> Array((0, 6), (0, 7), (0, 8), (1, 6), (1, 7), (1, 8), (2, 6), (2, 7), (2, 8)),
      3 -> Array((3, 0), (3, 1), (3, 2), (4, 0), (4, 1), (4, 2), (5, 0), (5, 1), (5, 2)),
      4 -> Array((3, 3), (3, 4), (3, 5), (4, 3), (4, 4), (4, 5), (5, 3), (5, 4), (5, 5)),
      5 -> Array((3, 6), (3, 7), (3, 8), (4, 6), (4, 7), (4, 8), (5, 6), (5, 7), (5, 8)),
      6 -> Array((6, 0), (6, 1), (6, 2), (7, 0), (7, 1), (7, 2), (8, 0), (8, 1), (8, 2)),
      7 -> Array((6, 3), (6, 4), (6, 5), (7, 3), (7, 4), (7, 5), (8, 3), (8, 4), (8, 5)),
      8 -> Array((6, 6), (6, 7), (6, 8), (7, 6), (7, 7), (7, 8), (8, 6), (8, 7), (8, 8)))
  }

  /**
    * A Map that takes a key="Row Index" and returns a value="Array of i, j indices".
    * The order of the indices are from left to right.
    */
  val rowIdxToIndices = {
    Map(0 -> Array((0, 0), (0, 1), (0, 2), (0, 3), (0, 4), (0, 5), (0, 6), (0, 7), (0, 8)),
      1 -> Array((1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8)),
      2 -> Array((2, 0), (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8)),
      3 -> Array((3, 0), (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8)),
      4 -> Array((4, 0), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8)),
      5 -> Array((5, 0), (5, 1), (5, 2), (5, 3), (5, 4), (5, 5), (5, 6), (5, 7), (5, 8)),
      6 -> Array((6, 0), (6, 1), (6, 2), (6, 3), (6, 4), (6, 5), (6, 6), (6, 7), (6, 8)),
      7 -> Array((7, 0), (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6), (7, 7), (7, 8)),
      8 -> Array((8, 0), (8, 1), (8, 2), (8, 3), (8, 4), (8, 5), (8, 6), (8, 7), (8, 8)))
  }

  /**
    * A Map that takes a key="Column Index" and returns a value="Array of i, j indices".
    * The order of the indices are from top to bottom.
    */
  val columnIdxToIndices = {
    Map(0 -> Array((0, 0), (1, 0), (2, 0), (3, 0), (4, 0), (5, 0), (6, 0), (7, 0), (8, 0)),
      1 -> Array((0, 1), (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1)),
      2 -> Array((0, 2), (1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (7, 2), (8, 2)),
      3 -> Array((0, 3), (1, 3), (2, 3), (3, 3), (4, 3), (5, 3), (6, 3), (7, 3), (8, 3)),
      4 -> Array((0, 4), (1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4), (8, 4)),
      5 -> Array((0, 5), (1, 5), (2, 5), (3, 5), (4, 5), (5, 5), (6, 5), (7, 5), (8, 5)),
      6 -> Array((0, 6), (1, 6), (2, 6), (3, 6), (4, 6), (5, 6), (6, 6), (7, 6), (8, 6)),
      7 -> Array((0, 7), (1, 7), (2, 7), (3, 7), (4, 7), (5, 7), (6, 7), (7, 7), (8, 7)),
      8 -> Array((0, 8), (1, 8), (2, 8), (3, 8), (4, 8), (5, 8), (6, 8), (7, 8), (8, 8)))
  }

  // This is a 2 dimensional array that stores an object.  Each object is one of 81 boxes in sudoku.
  val elementObjectArray: Array[Array[elementObject]] = Array(
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject),
    Array.fill(9)(new elementObject))

  // This fills in new initial values for the objects in elementObjectArray.
  for {
    i <- 0 to 8
    j <- 0 to 8
  } {
    elementObjectArray(i)(j).i = i
    elementObjectArray(i)(j).j = j
    elementObjectArray(i)(j).value = Input.value(i)(j)
    elementObjectArray(i)(j).hasValue = Input.value(i)(j) != 0
  }

  /**
    * Look at all values in a column and return an ArrayBuffer of all values found.
    * @param i is the row.
    * @param j is the column.
    * @return values found in that column.
    */
  def findAllValuesInColumnExcludeIJ(puzzle: Array[Array[elementObject]], i: Int, j: Int): ArrayBuffer[Int] = {
    val x: ArrayBuffer[Int] = ArrayBuffer()
    for {
      r <- 0 to 8
      if r != i
      if puzzle(r)(j).hasValue
    } {
      x += puzzle(r)(j).value
    }
    x
  }

  /**
    * Look at all values in a row and return an ArrayBuffer of all values found.
    * @param i is the row.
    * @param j is the column.
    * @return values found in that row.
    */
  def findAllValuesInRowExcludeIJ(puzzle: Array[Array[elementObject]], i: Int, j: Int): ArrayBuffer[Int] = {
    val x: ArrayBuffer[Int] = ArrayBuffer()
    for {
      c <- 0 to 8
      if c != j
      if puzzle(i)(c).hasValue
    } {
      x += puzzle(i)(c).value
    }
    x
  }

  /**
    * Look at all values in a row and return an ArrayBuffer of all values found.
    * @param i is the row.
    * @param j is the column.
    * @return values found in that row.
    */
  def findAllValuesInBlockExcludeIJ(puzzle: Array[Array[elementObject]], i: Int, j: Int): ArrayBuffer[Int] = {
    val x: ArrayBuffer[Int] = ArrayBuffer()

    // Get the block index
    val b = indexToBlockNumber(i,j)

    val indices = blockIdxToIndices(b)
    for {
      (r, c) <- indices
      if (r, c) != (i, j)
      if puzzle(r)(c).hasValue
    } {
      x += puzzle(r)(c).value
    }
    x
  }

  /**
    * Updates the field remainingValues in the puzzle.  We can shorten the ArrayBuffer length
    * by removing numbers that already appear in the column, row, or block.
    * Returns true if a contradiction is found.
    */
  def updateAll81RemainingValues(puzzle: Array[Array[elementObject]]): Boolean = {
    var foundContradiction = false

    var x: ArrayBuffer[Int] = ArrayBuffer()
    var y: ArrayBuffer[Int] = ArrayBuffer()
    var z: ArrayBuffer[Int] = ArrayBuffer()
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      if (!puzzle(i)(j).hasValue) {
        // Find the values inside the column, row, and block of this element object.
        x = findAllValuesInColumnExcludeIJ(puzzle,i,j)
        y = findAllValuesInRowExcludeIJ(puzzle,i,j)
        z = findAllValuesInBlockExcludeIJ(puzzle,i, j)

        /*
        x = findAllValuesInColumn(j)
        y = findAllValuesInRow(i)
        z = findAllValuesInBlock(indexToBlockNumber(i, j))
        */

        // Remove these values from the remaining values property.
        // You're left with the remaining values for that element object.
        puzzle(i)(j).remainingValues --= x --= y --= z
        if (puzzle(i)(j).remainingValues.isEmpty) {
          foundContradiction = true
        }
      } else {
        //
        puzzle(i)(j).remainingValues = ArrayBuffer(puzzle(i)(j).value)
      }
    }
    if (foundContradiction) println("Found contradiction in update all 81 remaining values function.")
    foundContradiction

  }

  /**
    * This first algorithm will be simple.  Check if each element only has one remaining value.
    * If so, update the appropriate properties in the object.
    * Returns true if a contradiction is found.
    */
  def algorithm1(puzzle: Array[Array[elementObject]]): Boolean = {
    var foundContradiction = false
    for {
      i <- 0 to 8
      j <- 0 to 8
      if !puzzle(i)(j).hasValue
      if !foundContradiction
    } {
      if (puzzle(i)(j).remainingValues.length == 1) {
        puzzle(i)(j).value = puzzle(i)(j).remainingValues(0)
        puzzle(i)(j).hasValue = true
        if(updateAll81RemainingValues(puzzle)) foundContradiction = true
      } else if (puzzle(i)(j).remainingValues.isEmpty) {
        foundContradiction = true
      }
    }
    foundContradiction
  }

  /**
    * This is slightly more complicated than the last algorithm.  For a given single square in sudoku, check
    * if there is a "remianingValue" in that square that don't show up in the square's column, row, and block.
    * Returns true if a contradiction is found.
    */
  def algorithm2(puzzle: Array[Array[elementObject]]): Boolean = {
    var foundContradiction = false
    for {
      i <- 0 to 8
      j <- 0 to 8
      if !puzzle(i)(j).hasValue
      if !foundContradiction
    } {
      //// Find the index of each square that is in the column, row, or block of this square
      val blockIndices = blockIdxToIndices(indexToBlockNumber(i,j))
      val columnIndices = columnIdxToIndices(j)
      val rowIndices = rowIdxToIndices(i)
      //var totalIndices: ArrayBuffer[(Int, Int)] = ArrayBuffer() ++= blockIndices ++= columnIndices ++= rowIndices
      //totalIndices = totalIndices.distinct
      // accumulation array buffer.  Stores all numbers found in remainingValues in all squares along the row,
      // column, and block; does not store the remainingValues of the square focused on in the loop.
      var accBlock: ArrayBuffer[Int] = ArrayBuffer()
      var accColumn: ArrayBuffer[Int] = ArrayBuffer()
      var accRow: ArrayBuffer[Int] = ArrayBuffer()
      // Copy the ramining values into another variable
      var copyRemainingValues1: ArrayBuffer[Int] = puzzle(i)(j).remainingValues.clone
      var copyRemainingValues2: ArrayBuffer[Int] = puzzle(i)(j).remainingValues.clone
      var copyRemainingValues3: ArrayBuffer[Int] = puzzle(i)(j).remainingValues.clone

      ////// Check inside the block
      for {
        (r, c) <- blockIndices
        if (r, c) != (i, j)  // guard against using the index of the outer loop.
      } {
        accBlock ++= puzzle(r)(c).remainingValues
      }
      //// Use these remaining values to potentially update the index focused on in the loop.
      //// We use a copy of the remainingValues so we don't alert the object property
      copyRemainingValues1 --= accBlock
      if (copyRemainingValues1.length > 1) {
        foundContradiction = true
      } else if (copyRemainingValues1.length == 1) {
        puzzle(i)(j).value = copyRemainingValues1(0)
        puzzle(i)(j).hasValue = true
        if(updateAll81RemainingValues(puzzle)) foundContradiction = true
      }

      ////// Check inside the row
      for {
        (r, c) <- rowIndices
        if (r, c) != (i, j)  // guard against using the index of the outer loop.
      } {
        accRow ++= puzzle(r)(c).remainingValues
      }
      //// Use these remaining values to potentially update the index focused on in the loop.
      //// We use a copy of the remainingValues so we don't alert the object property
      copyRemainingValues2 --= accRow
      if (copyRemainingValues2.length > 1) {
        foundContradiction = true
      } else if (copyRemainingValues2.length == 1) {
        puzzle(i)(j).value = copyRemainingValues2(0)
        puzzle(i)(j).hasValue = true
        if(updateAll81RemainingValues(puzzle)) foundContradiction = true
      }

      ////// Check inside the column
      for {
        (r, c) <- columnIndices
        if (r, c) != (i, j)  // guard against using the index of the outer loop.
      } {
        accColumn ++= puzzle(r)(c).remainingValues
      }
      //// Use these remaining values to potentially update the index focused on in the loop.
      //// We use a copy of the remainingValues so we don't alert the object property
      copyRemainingValues3 --= accColumn
      if (copyRemainingValues3.length > 1) {
        foundContradiction = true
      } else if (copyRemainingValues3.length == 1) {
        puzzle(i)(j).value = copyRemainingValues3(0)
        puzzle(i)(j).hasValue = true
        if(updateAll81RemainingValues(puzzle)) foundContradiction = true
      }
    }
    foundContradiction
  }

  /**
    * This will print out the values in object myPuzzle.puzzle.
    * This generates an output of the values in the sudoku puzzle.
    */
  def printPuzzle(puzzle: Array[Array[elementObject]]): Unit = {
    println();println();println()
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      if (j == 3 || j == 6) print("|")
      print(" " + puzzle(i)(j).value + " ")
      if (j == 8) println()
      if (j == 8 && (i == 2 || i == 5)) println(" - - - -   - - - -   - - - - ")
    }
  }
  // Print original puzzle
  printPuzzle(elementObjectArray)

  def runAlgorithms(puzzle: Array[Array[elementObject]]): Boolean = {
    var foundContradiction = false
    if(updateAll81RemainingValues(puzzle)) println("Found a contradiction during initial update")
    for {
      n <- 1 to 100
      if !foundContradiction
    } {
      if(algorithm1(puzzle)) {
        println("Found a contradiction during algorithm1")
        foundContradiction = true
      }
      if(algorithm2(puzzle)) {
        println("Found a contradiction during algorithm2")
        foundContradiction = true
      }
    }
    foundContradiction
  }
  runAlgorithms(elementObjectArray)

  printPuzzle(elementObjectArray)

  /**
    * Returns true if the puzzle is done
    */
  def puzzleIsDone(puzzle: Array[Array[elementObject]]): Boolean = {
    var isdone = true
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      // check all values and if hasValue is true for all of them then the puzzle is done.
      if (!puzzle(i)(j).hasValue) isdone = false
    }
    isdone
  }

  def sudokuClone(puzzle: Array[Array[elementObject]]): Array[Array[elementObject]] = {
    val clonedPuzzle: Array[Array[elementObject]] = Array(
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject),
      Array.fill(9)(new elementObject))

    // This fills in new initial values for the objects in elementObjectArray.
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      clonedPuzzle(i)(j).i = puzzle(i)(j).i
      clonedPuzzle(i)(j).j = puzzle(i)(j).j
      clonedPuzzle(i)(j).value = puzzle(i)(j).value
      clonedPuzzle(i)(j).hasValue = puzzle(i)(j).hasValue
      clonedPuzzle(i)(j).remainingValues = puzzle(i)(j).remainingValues.clone
    }
    clonedPuzzle
  }

  /**
    * Starts guessing when stuck.  This recursively runs until the puzzle is close to complete or complete
    * @param swapGuess will choose the opposite guess after it is incorrect
    */
  def startGuessingWhenStuck(puzzle: Array[Array[elementObject]], swapGuess: Boolean = false): Array[Array[elementObject]] = {
    var guessElementObjectArray = sudokuClone(puzzle)
    var backupElementObjectArray = sudokuClone(puzzle)
    println(backupElementObjectArray(0)(1).remainingValues)
    //ERROR: it is removing the remaining guesses even though it led to a contradiction

    for {
      i <- 0 to 8
      j <- 0 to 8
      if guessElementObjectArray(i)(j).remainingValues.length == 2
    } {
      println(s"choices are ${guessElementObjectArray(i)(j).remainingValues(0)} and ${guessElementObjectArray(i)(j).remainingValues(1)}")
      println(s"at $i, $j location")
      println(s"we choose to guess ${guessElementObjectArray(i)(j).remainingValues( if (swapGuess) 1 else 0 )}")
      guessElementObjectArray(i)(j).value = guessElementObjectArray(i)(j).remainingValues( if (swapGuess) 1 else 0 )
      guessElementObjectArray(i)(j).hasValue = true
      guessElementObjectArray = if(runAlgorithms(guessElementObjectArray)) {
        // Do this if a contradiction is found
        println("contradiction found in guess loop")
        println("Therefore we are rerunning guessing with data before we last guess.")
        println(s"Furthermore, we are passing in swapGuess=${!swapGuess} ")
        startGuessingWhenStuck(backupElementObjectArray, !swapGuess)
      } else {
        println("no contradiction found in guess loop")
        // Do this if no contradiction is found
        if (puzzleIsDone(guessElementObjectArray)) guessElementObjectArray //puzzle is done!
        else startGuessingWhenStuck(guessElementObjectArray) // rerun this with some default choices
      }
    }
    guessElementObjectArray
  }

  // This will recursively run until you get a final output or it is close to the final output
  val finalAnswer = startGuessingWhenStuck(elementObjectArray)
  printPuzzle(finalAnswer)

  def rowColumnBlockCount(puzzle: Array[Array[elementObject]]): Unit = {
   // val blockIndices = blockIdxToIndices(indexToBlockNumber(i,j))
    val rowSum: Array[Int]    = Array(0,0,0,0,0,0,0,0,0)
    val columnSum: Array[Int] = Array(0,0,0,0,0,0,0,0,0)
    val blockSum: Array[Int]  = Array(0,0,0,0,0,0,0,0,0)
    var acc = 0
    var block0Acc = 0; var block1Acc = 0; var block2Acc = 0; var block3Acc = 0; var block4Acc = 0
    var block5Acc = 0; var block6Acc = 0; var block7Acc = 0; var block8Acc = 0; var currentBlock = 0


    // row sum
    for {
      i <- 0 to 8
      j <- 0 to 8
    } {
      if (j == 0) acc = 0
      acc += puzzle(i)(j).value
      if (j == 8) rowSum(i) = acc
    }
    println()
    println("Row sums are:")
    rowSum.foreach(println)
    println()

    // columm sum
    for {
      j <- 0 to 8
      i <- 0 to 8
    } {
      if (i == 0) acc = 0
      acc += puzzle(i)(j).value
      if (i == 8) rowSum(j) = acc
    }
    println("Row sums are:")
    rowSum.foreach(println)
    println()

    // block sum
    for {
      j <- 0 to 8
      i <- 0 to 8
    } {
      var currentBlock = indexToBlockNumber(i, j)
      currentBlock match {
        case 0 => block0Acc += puzzle(i)(j).value
        case 1 => block1Acc += puzzle(i)(j).value
        case 2 => block2Acc += puzzle(i)(j).value
        case 3 => block3Acc += puzzle(i)(j).value
        case 4 => block4Acc += puzzle(i)(j).value
        case 5 => block5Acc += puzzle(i)(j).value
        case 6 => block6Acc += puzzle(i)(j).value
        case 7 => block7Acc += puzzle(i)(j).value
        case 8 => block8Acc += puzzle(i)(j).value
      }
    }
    println(s"Block0 has sum $block0Acc")
    println(s"Block1 has sum $block1Acc")
    println(s"Block2 has sum $block2Acc")
    println(s"Block3 has sum $block3Acc")
    println(s"Block4 has sum $block4Acc")
    println(s"Block5 has sum $block5Acc")
    println(s"Block6 has sum $block6Acc")
    println(s"Block7 has sum $block7Acc")
    println(s"Block8 has sum $block8Acc")
  }
  rowColumnBlockCount(finalAnswer)
}

