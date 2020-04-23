package org.CNS.Models;

public class Cell {

	public float x, y; // object's current coordinates
	public float speed = 0.1f, step = 5f;
	public boolean toBeDeleted;
	public int age = 0; // in frames

	public float sightDistance = 100f;
	public float catchDistance = 6f;

	public float energy = 10f;
	public float energyCapacity = 25f;

	// public double rand = Math.random() * 100 + 1;

	public Cell(float w, float h) {
		this.x = w;
		this.y = h;
		this.toBeDeleted = false;

	}

}
