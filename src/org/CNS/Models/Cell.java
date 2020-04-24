package org.CNS.Models;

import java.util.ArrayList;

import org.CNS.View.Window;

public class Cell {

	public int x, y; // object's current coordinates
	public float destX, destY; // destination coordinates
	public float speed = 0.1f, step = 5f;
	public boolean toBeDeleted;

	public Energy clEnergy = null; // closest energy
	public Window win = null; // environment settings

	public int commandSize = 10;
	public int[] commandOrder = new int[commandSize * commandSize];
	public int tick = 0;

	public float sightDistance = 100f;
	public float catchDistance = 6f;

	public float energy = 10f;
	public float energyCapacity = 25f;

	// public double rand = Math.random() * 100 + 1;

	public Cell(int x, int y, Window w) {
		this.x = x;
		this.y = y;
		this.toBeDeleted = false;
		for (int i = 0; i < commandOrder.length; i++) {
			commandOrder[i] = (int) (Math.random() * (commandSize)) + 1;
			// System.out.println(commandOrder[i]);
		}
		this.win = w;
	}

	public void go(ArrayList<Energy> arrE) {
		int command = tick % (commandSize * commandSize);

		switch (commandOrder[command]) {
		case 1:
			findClosestEnergy(arrE);
			break;
		case 2:
			moveN();
			break;
		case 3:
			moveE();
			break;
		case 4:
			moveS();
			break;
		case 5:
			moveW();
			break;
		case 6:
			lookAround();
			break;
		case 7:
			eatUp();
			break;
		case 8:
			pauseFrame();
			break;
		case 9:
			moveToDestX();
			break;
		case 10:
			moveToDestY();
			break;
		default:
			System.out.println("lox");
		}

		tick++;
	}

	private boolean findClosestEnergy(ArrayList<Energy> arrE) { // [1] - looking for closest energy
		// Energy clEnergy = null;
		float distClosestEnergy = win.w * win.w + win.h * win.h; // 800*800 + 800*800 = 1 280 000

		for (Energy e : arrE) {

			float distToCandidate = (this.x - e.x) * (this.x - e.x) + (this.y - e.y) * (this.y - e.y); // 80000

			if (distClosestEnergy > distToCandidate) {
				distClosestEnergy = distToCandidate;
				clEnergy = e;
				destX = e.x;
				destY = e.y;
			}
		}

		return (clEnergy == null) ? false : true;

	}

	private boolean moveN() { // [2] - move North
		if (this.y - 31 >= 0) {
			y -= 1;
			return true;
		} else
			return false;
	}

	private boolean moveE() { // [3] - move East
		if (this.x + 31 <= win.w) {
			x += 1;
			return true;
		} else
			return false;
	}

	private boolean moveS() { // [4] - move South
		if (this.y + 37 <= win.h) {
			y += 1;
			return true;
		} else
			return false;
	}

	private boolean moveW() { // [5] - move West
		if (this.x - 31 >= 0) {
			x -= 1;
			return true;
		} else
			return false;
	}

	private void lookAround() { // [6] - checking area on obstacles
		// nothing to do without walls
	}

	private boolean eatUp() { // [7] - eating up destinated energy

		if (clEnergy != null) {
			if (Math.abs(this.x - clEnergy.x) <= catchDistance) {
				if (Math.abs(this.y - clEnergy.y) <= catchDistance) {
					clEnergy.toBeDeleted = true;
					this.energy++;
					return true;
				} else
					return false;
			} else
				return false;
		} else
			return false;
	}

	private void pauseFrame() { // [8] - do not do anything
		// my favourite function, just waiting
	}

	private boolean moveToDestX() { // [9] - doing one step on the X-line
		if (destY != -1) {
			if (destX > x)
				return this.moveE();
			else if (destX < x)
				return this.moveW();
			else
				return true;

		} else
			return false;
	}

	private boolean moveToDestY() {// [10] - doing one step on the Y-line
		if (destY != -1) {
			if (destY > y)
				return this.moveN();
			else if (destY < y)
				return this.moveS();
			else
				return true;
		} else
			return false;
	}
}
