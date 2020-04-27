package org.CNS.View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.CNS.Models.Cell;
import org.CNS.Models.Energy;

public class Window extends JFrame implements Runnable {

	public final int w = 800; // resolution
	public final int h = 800;

	private final int FRAMES_TOTAL = 100000000;
	private final int SKIP_FRAMES = 10;
	public int frame = 0;

	public int startFrame = 0;
	public int endFrame = 0;

	private final Color BG = new Color(200, 200, 200, 255);
	private BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	private BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	private BufferedImage sprites[] = new BufferedImage[3];

	private int numberCellStart = 100; // start number of cells
	private int numberEnergyStart = 50; // start number of energy
	private final int CELL_RADIUS = 10, ENERGY_RADIUS = 3;
	private int generationNumber = 0;

	private ArrayList<Cell> cells = new ArrayList<>();
	private ArrayList<Energy> energies = new ArrayList<>();

	public Window() {

		for (int i = 0; i < sprites.length; i++) {
			try {
				sprites[i] = ImageIO.read(new File("img/cell" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.setSize(w, h);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(500, 50);

		fillArrCells(cells);

	}

	@Override
	public void paint(Graphics g) {
		try {
			drawScene(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Graphics2D g2 = buf.createGraphics();
		g2.drawImage(img, null, 0, 0);
		((Graphics2D) g).drawImage(buf, null, 8, 31);

		for (int i = 0; i < SKIP_FRAMES; i++) {
			if (logic())
				selectNextGeneration();
		}
	}

	private void drawScene(BufferedImage image) throws IOException {
		Graphics2D g2 = image.createGraphics();
		g2.setColor(BG);
		g2.fillRect(0, 0, w, h);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Energy e : energies) {
			g2.setColor(Energy.COLOR[e.type]);
			g2.fillOval((int) e.x - ENERGY_RADIUS, (int) e.y - ENERGY_RADIUS, ENERGY_RADIUS, ENERGY_RADIUS);
		}

		float cellScale = CELL_RADIUS * 0.01f;

		for (Cell c : cells) {
			float sw = sprites[0].getWidth() * cellScale;
			float sh = sprites[0].getHeight() * cellScale;
			AffineTransform trans = new AffineTransform();
			trans.translate(c.x - sw, c.y - sh);
			trans.scale(cellScale, cellScale);
			g2.drawImage(sprites[0], trans, this);
		}

	}

	private void fillArrCells(ArrayList<Cell> cells) {
		for (int i = 0; i < numberCellStart; i++) {
			Cell c = new Cell((int) (Math.random() * (w - 100) + 50), (int) (Math.random() * (h - 100) + 50), this);
			cells.add(c);
		}
	}

	private void addNewEnergy(ArrayList<Energy> arrE) {
		Energy e = new Energy((int) (Math.random() * (w - 150) + 50), (int) (Math.random() * (h - 150) + 50));
		arrE.add(e);
	}

	private void selectNextGeneration() {
		endFrame = frame;
		ArrayList<Cell> arrListMain = new ArrayList<Cell>();
		ArrayList<Cell> arrListCopy = new ArrayList<Cell>();
		int size = (int) Math.sqrt(numberCellStart);

		for (int j = 0; j < cells.size(); j++) {
			arrListCopy.add(cells.get(j));
			for (int i = 0; i < size - 1; i++) {
				arrListCopy.add(arrListCopy.get(0)); // arrListCopy[0] is always cloneable item

				if (i > 0) {
					randChangeGene(arrListCopy.get(i));
				}
			}

			arrListMain.addAll(arrListCopy);
			arrListCopy.clear();
		}

		cells.clear();

		fillArrCells(cells);
		if (arrListMain.size() == cells.size()) {
			for (int j = 0; j < cells.size(); j++) {
				for (int i = 0; i < cells.get(j).commandOrderSize; i++) {
					cells.get(j).commandOrder[i] = arrListMain.get(j).commandOrder[i];
				}
			}
		} else
			System.out.println("Error! #1");

		energies.clear();
		for (int i = 0; i < numberEnergyStart; i++) {
			addNewEnergy(energies);
		}
		System.out.println("Generation:\t" + generationNumber + "\nTicks:\t" + (endFrame - startFrame)+"\n");

		generationNumber++;
		startFrame = frame;
	}

	private void randChangeGene(Cell c) {
		int geneNumber = (int) (Math.random() * c.commandOrderSize);
		int newCommand = (int) (Math.random() * c.commandSize) + 1;

		c.commandOrder[geneNumber] = newCommand;
	}

	private boolean logic() {

		// System.out.println(cells.size() + " " + generationNumber);

		/*
		 * ----------------- PROPERTY CHANGES OF CELLS ARE DOWN HERE ------------------
		 */
		for (Cell c : cells) {
			//System.out.println(c.energy);
			c.go(energies);
		}

		/*
		 * ----------- QUANTITY CHANGES OF CELLS AND ENERGY ARE DOWN HERE -----------
		 */

		if (frame % 30 == 0)
			addNewEnergy(energies);

		frame++;

		for (int i = 0; i < energies.size(); i++) {
			if (energies.get(i).toBeDeleted) {
				energies.remove(i);
				i--;
			}
		}

		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).toBeDeleted) {
				addNewEnergy(energies);
				cells.remove(i);

				/*
				 * positive end of function (starting of creation new generation)
				 */
				if (cells.size() == (int) Math.sqrt(numberCellStart)) {
					return true;
				}
				i--;
			}
		}
		return false;
	}

	@Override
	public void run() {
		while (frame < FRAMES_TOTAL)
			this.repaint();
	}

}
