# sudoku-solver

Input a sudoku problem into the Scala code and the application will solve it.

## How to use

Get Scala on your system and edit the code input.  Simply copy the input template and replace the input variable.  This template can be found in the code itself:

/*  val value = Array(<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0),<br/>
    Array(0,0,0,0,0,0,0,0,0))*/
   
This is a 9 by 9 matrix representing the sudoku puzzle.  All zeroes are unknown spaces, and numbers 1 to 9 are known spaces.

How it works isn't properly documented or refactored.  The solution can be done in under 100 lines of code, but this implementation uses about 600 lines, because it uses multiple methods of deducing answers.
