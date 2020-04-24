# Cyber Natural Selection
Cyber Natural Selection (CNS) is a Java simulation of cells with commands in random order. After some iterations natural selection makes a new step in evolution: commands order becomes good enough for prosperity of life.

### TO DO List:
* Make a "Field4Life" (create environment)
* Develop "Round's rules" (each round - one generation)

### TO CHANGE List:
* I have object Window in Cell class, so every Cell object has that. I think that's not good for optimization. Fix that, Vladislove!!! (24.04.2020)
* Still thinking on void-boolean functions "moveToDestX()" and "moveToDestY()"


------------------------------------------------------------

## Changelogs: 

### Version 0.01a

* Command order consists of some methods:

```
[1] bool - findClosestEnergy ()
[2] bool - moveN() - moving on North
[3] bool - moveE() - on East
[4] bool - moveS() - South
[5] bool - moveW() - West
[6] void - lookAround() - I'll improve that function in the near future
[7] bool - eatUp() - eating up destinated energy (nor returning false)
[8] void - pauseFrame() - do nothing, waiting for the next frame
[9] bool (?) - moveToDestX() - doing one step on the X-line 
[10] bool (?) - moveToDestY() - doing one step on the Y-line
```
* Minimized all objects on the screen-field (still testing)


### Version 0.01b

* Testing on cell's behavior completed well.
* Changed *float* coordinates on *int*	




------------------------------
------------------------------
## DONE TASKS

* Cell Class - DONE
* Energy Class - DONE
* Window Class - DONE
* Main Class - DONE
* Code logic for cells (command order) - DONE, version 0.01a