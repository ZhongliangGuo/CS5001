# README

Score: 18/20

Report for CS5001 Practice 2

Matriculation number of author: **[210016568](mailto:zg34@st-andrews.ac.uk)**

## Abstract

This report is to illustrate the processing that I implement the Classes in UML diagram. This document is more of a description of the purpose and conditions of use of each method, for the explanation of parameters need to see `JavaDoc`. Here will be divided into three parts, analysis of the rules, demonstration of this project, and the process I trying to put this project into a GUI.

## Analysis of the rules

According to the pdf and tests Michael provided, I analyzed the rules of this game.

* The game has two Players, which will be defined in Class `Player`
* each player has their own pieces (defined in the Class `Piece`)
* those animals can move basing on their own moving directions
* Chick and Cat can **(automatically)** promote when they reach the two rows farthest from their board
* Once the promoted pieces are captured, it will be non-promoted
* The captured piece can be placed on the board by capturer, but both two players cannot move it

 

## Demonstration of this project

In this section, I analyzed the UML diagram, and figured out the function of each method. I will demonstrate the method showed in UML as well as the methods and Class in `myutil` package I defined.

The order of the discussion is the order in which I wrote the code.

###  Class `Player`

This class is to define the Player in this game. In addition to the document in `JavaDoc`, I prefer to describe some attributes and several methods.

#### Attributes

* ```private final String name``` is used to store the name of player, because the name cannot be changed, so I used **final**
* ```private final int player number``` is used to store the unique number of player for identifying, because this number cannot be changed, so I used **final**
* ```private ArrayList<Piece> handPiece``` is used to store the pieces which is captured by this player
* ```private boolean won``` is used to record the status whether this player win the game, it has setter ```winGame()``` and getter ```hasWon()```

#### Constructor

##### ```dropPiece()```

This method is used to drop the piece from `handPiece`. In this method, it should determine whether the square is empty, if it is not empty, that means here has already been a piece, the player cannot put the handpiece in this square.

#### ```winGame()```

This method actually is a setter of attribute `won`. It should be used **after the Lion is captured**.

#### ```hasWon()```

This method can be regarded as the getter of attribute `won`. It should be used **after every turn of game**.

### Class ```Point``` in `myutil` package

This Class is to define the point in a two-dimensional coordinate system, the coordinate should be  `(row, col)`.

It will work in method `getLegalMoves` (I will discuss it later).

The reason why I use it is to **make the code neater**.

### Class `Square` and `Piece`

In this part, I prefer to talk about those two Classes together, because they have some complicated interaction with each other.

The relationship between those two classes should be that, one object of `Piece` can be put into one object of `Square`, and one object of `Square` can be put no more than one object of the object of `Piece`.

Inside both of them, each has an attribute that points to the other's object.

#### Class `Square`

##### Attributes

```private Player promotesPlayer = null``` is used to record whose promotable pieces can promote in this square. If it is a non-promotion zone, the value should be ```null```.

##### Two constructors

Here are two constructors in this Class.

```public Square(Game game, int row, int col)``` is used to create the square between row 2 to 3, because in those 2 rows, there is no zone for promotable pieces to promote.

```public Square(Game game, int row, int col, Player promotesPlayer)``` is used to create the square which can promote for promotable pieces. Between row 0 to 1, it is the zone for player 1 to promote. Between row 4 to 5, it is the zone for player 0 to promote.

##### ```placePiece()```

This method is to put the new piece in this square. For the object of `Piece`, it just take responsibility of checking whether itself is empty. It can only be used **when a player get legal moves of a certain piece and has emptied the piece in this square or a player put a hand piece into it**.

##### ```removePiece()```

This method just set its reference for piece as empty. It should be used combining with `beCaptured()` method.

##### `isPromotionZone()`

To get that whether a certain piece belonging to a certain player can promote here.

#### Class ```Piece```

##### Attributes

```private boolean isCapturedBefore = false``` is used to record has this piece ever been captured by others, because **once a piece is captured, although it can be place on the checkerboard, but it cannot move**.

##### ```getLegalMoves()```

This method is an abstract method because different animals have different ways of moving. It should be override compulsorily.

##### ``` move()``` and ```beCaptured()```

Those two method should be talked together, because there are some confused interactions will happen it the action they did is overlapping.

###### ```move()```

This method should be used after `getLegalMoves()` and then choose one of the legal way to move. If it is empty, the piece can directly move in. If it is not empty, the piece which in the destination square should be captured, then `this.piece` can move in.

###### ```beCaptured()```

This method should be used in `move()` method.

###### Example to demonstrate

The reason why I put those two method together to demonstrate is that, here is a frequent condition: a piece of player 0 captures the piece of player 1.

In this condition, for the square which be-captured-piece is in, it will has relationship with two pieces.

We assume the capturer is `piece 1` in `square 1`, the piece will be captured is `piece 2` in `square 2`. The flow of this behavior should look like this:

* `piece 1` call for the method `getSquare` to get the reference which point at `square 1`

* This reference call for the method `removePiece()` of `square 1`

* Shift the square reference of `piece 1` from `square 1` to `square 2`

* Call the `beCaptured()`method of `piece 2`, it should be captured by the owner of `piece 1`

* In `beCaptured()`, the piece reference of `square 2` is set as ```null```, this piece is added to capturer's hand, its owner changed, and set `isCapturedBefore` as true

* The piece reference of `square 2` point at `null`

* then do the same thing in method `placePiece()`
  Here can use the recursive thoughts, the implement I wrote in the `JavaDoc`.

##### `isLegal()`

Here I defined this method by myself. This method will be used  in animal pieces, I will show those things later.

At first, I need to explain two words, `potential moves` and `legal moves`.

For `potential moves`, it will not consider of the size of checkerboard, and just give the potential square this piece can move to. For example, an object of `Chick` locate on `(X, Y)`, its potential moves should be `(X-1, Y)`.

For `legal moves`, it firstly check if the each move in `potential moves` is existing. Then, it check the not empty square's owner, if it is same as the piece which is moving, this move is illegal, else it is legal.

The input parameter is a `Point` list. This list will be defined in different animal piece. 

### Class ```Lion```

For this Class, it inherit from `Piece` excluding the method `getLegalMoves()` and `beCaptured()`, all things are same as Piece.

#### ```getLegalMoves()```

In this method, I create a list and it will contain the `potential moves` of a lion (combining the code gives a clearer picture of why I did what I did), then put it into the method ```isLegal()``` to get the legal moves.

#### ```beCaptured()```

Once the lion is captured, the capturer win the game.

### Class ```Giraffe```

This class only needs to override ```getLegalMoves()```, its thought is same as Lion's, only the `potential moves` is different.

### Class ```PromotablePiece```

This class is an abstract class, used to describe pieces which can promote (cat and chick).

#### Attribute

```private boolean promoted``` is to record the promotion status of this piece. Actually the method ```promote()``` and ```getIsPromoted()``` are the setter and getter of it.

#### ```move()```

The move method is a little different. Because when a promotable piece enter the promotion zone, it should promote itself automatically.

#### ```beCaptured()```

When the piece is captured, the promotion status should be false.

### Class ```Cat```

This class inherit from `PromotablePiece`.

#### ```getLegalMoves()```

In this class, only the ```getLegalMoves()``` is different from its parent class. Because in this method, it should define two `potential moves` list for promoted Cat and non-promoted Cat.

### Class ```Chick```

This class inherit from `PromotablePiece`.

#### ```getLegalMoves()```

In this class, only the ```getLegalMoves()``` is different from its parent class. Because in this method, it should define two `potential moves` list for promoted Chick and non-promoted Chick.

### Class Game

In the initialization section, the board is divided into three areas, The area where `player 0` can promote, the area where `player 1` can promote, the area where no player can promote. They are implemented through two different constructors of Square.

I used a two-dimensional `Array` as getter for those 30 squares.

I also came up with a way to use a method to achieve the same result of it and shared it with classmates (No academic misconduct occurred, and only other irrelevant examples were used to convey ideas). It is that put 30 square in an `ArrayList`, as shown below:

```java
private List<Square> checkerBoard = new ArrayList<Square>();
·
·
·
public Square getSquareInCheckerBoard(int row, int col) {
    return this.checkerBoard.get(row * 5 + col);
}
```

 This approach may have a better space complexity, but since it is not intuitive enough, I choose to use a two-dimensional array.

## GUI

For this part actually I really want to make a GUI for this game, but I only used `WPF (in C#)` and a little `QT (in Python)` before. I sent an email to **[Michael](mailto:mct25@st-andrews.ac.uk)** and when he reply me `swing` I was disappointed XD.I am afraid that I cannot finish it. It is a little awkward ヽ(ˋ▽ˊ)ノ. But I will try my best to reach out and figure out a new thing.

Now it is 13th October 1pm, just two days, I do not know if I can create a miracle XD.

My plan for this GUI project was to use buttons to act as `Square` in the original `animalchess` package, with the titles on the buttons acting as `Piece`, and some buttons next to each player board to hold the pieces that are captured by them. Buttons without titles (i.e. `Square` without `Piece`) cannot be manipulated.

### `implement` package

Here are some Class I inherit from swing provided.

#### Class ```AnimalChessFrame```

In the class which inherited from ```JFrame```, I added an object ```private Game myGame = new Game(p0, p1)``` into the class I defined.

#### Class ```AnimalChessPanel```

This class inherit from `JPanel`. I added some lines and buttons in it to simulate the checkerboard and pieces.

#### Class ```ButtonWithCoordinate```

 This class inherit from `JButton`. I added the `row` and `col` in it for indexing the `Square` in `checkerBoard`.

### After that

Now it is 15th Oct 4 pm, I do not know how to use the `ActionListener` of `JButton`, and I don't have enough time to learn it. 

Saaaaaaaad, I need to pass the `JavaDoc` first. The GUI project was forced to abort TAT.

### Overview

In this section I cannot finish the GUI project, but I can illustrate what I want to do subsequently:

* Every time the player click the button, only the legal move destination buttons are enabled
* The captured pieces will be put in the right of screen, and it can be clicked when the player want to place it (by the way, because the result of `handPiece.getLegalMoves()` is empty, so here should have a method to check the empty Square)
* A button is also needed to restart the game at the back

## Summary

In the first project, the `animalchess`, it is not difficult, but troublesome, I used 5 hours to understand all the rules from pdf and tests, then I finish it.

The next project `GUI` is quite interesting, but I do not have enough time and knowledge to finish it QAQ. What a pity is.
