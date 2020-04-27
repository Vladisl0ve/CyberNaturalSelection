# Cyber Natural Selection
Cyber Natural Selection (CNS) is a Java simulation of cells with commands in random order. After some iterations natural selection makes a new step in evolution: commands order becomes good enough for prosperity of life.

------------------------------------------------------------

## Changelogs: 

### Version 0.1.1

* Showing generation number + life time (temp. like a syso)
* Balance changes
* Bug fixes (stopping at the border)
* Notice:
```
life span:
1st generation: 300-400 ticks
10th generation: 800+ ticks
100th generation: 900+ ticks
Then span is 800 - 1400 ticks, changes like a sinusoid.
```


### Version 0.1

*Field4Life is done;
*Round's rules are wrotten:
```
Field with W x H (width and height).
Start: 1. cells are seeking to have energy as much as they can. If energy becomes be less of normal, 
cell dies (and leave energy after itself).
2. The lasts - and the hardest - are fathers of new generation.
3. We select (copy) them in ^2 of their number.
4. We change ONE random command (gene) in commandOrder.
5. Life starts with a clean slate!
6. GOTO 1;
```
*Changed sprite for cells (more attractive now)
*Some fixes


### Version 0.0.1b

* Testing on cell's behavior completed well.
* Changed *float* coordinates on *int*	

### Version 0.0.1a

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

-------------------------------
### Rebalance questions:
* To allow cells move without borders (teleportation from south to north poles and back) - *consider about that*
* To make a one method instead of two for movingToDestination (X+Y, not X & Y)
* To decrease energy for every step 


-------------------------------

### EARLY TO CHANGE List:
* To rebalance rules;
* To change some field's properties;
* To write/read from file commandOrder (for explaining behavior of cells and watching thier evolution by facts)


### LATE TO CHANGE List:
* *To add GUI and visual settings* to change all properties without having knowledge in code-writting

* To remove object Window in Cell class
* Still thinking on void-boolean functions "moveToDestX()" and "moveToDestY()"

------------------------------
------------------------------
## DONE TASKS

* Cell Class - DONE
* Energy Class - DONE
* Window Class - DONE
* Main Class - DONE
* Code logic for cells (command order) - DONE, version 0.01a
* Make a "Field4Life" (create environment), version 0.1
* Develop "Round's rules" (each round - one generation), version 0.1