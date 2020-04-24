		Natural Selection
	There are cells with commands in random order. After some iterations natural selection makes 
a new step in evolution: command's order becomes good enough for prosperity of life.

2nd TO DO List:
1. Code logic for cells (command order) - DONE, version 0.01a
2. Make a "Field4Life" (create environment)
3. Develop "Round's rules" (each round - one generation)




------------------------------

Command order consists of some methods:
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



------------------------------
DONE LISTS

1st TO DO List:
1. Cell Class - DONE
2. Energy Class - DONE
3. Window Class - DONE
4. Main Class - DONE