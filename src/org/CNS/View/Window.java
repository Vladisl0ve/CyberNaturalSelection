package org.CNS.View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.CNS.Models.*;

public class Window extends JFrame implements Runnable {

	public final int w = 800; // resolution
	public final int h = 800;

	private final int FRAMES_TOTAL = 1000000;
	private final int SKIP_FRAMES = 1;
	private int frame = 0;

	private final Color BG = new Color(200, 200, 200, 255);
	private final Color BLUE = new Color(150, 160, 255, 255);
	private final Color RED = new Color(255, 100, 120, 255);
	private BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	private BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	private BufferedImage sprites[] = new BufferedImage[3];

	private int NumberCells = 100; // start number of groups of each type
	private final int CELL_RADIUS = 10, ENERGY_RADIUS = 3;

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

		for (int i = 0; i < NumberCells; i++) {
			Cell c = new Cell((int) (Math.random() * (w - 100) + 50), (int) (Math.random() * (h - 100) + 50), this);
			cells.add(c);
		}

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

		for (int i = 0; i < SKIP_FRAMES; i++)
			logic();

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

	private void addNewEnergy() {
		Energy e = new Energy((int) (Math.random() * (w - 100) + 50), (int) (Math.random() * (h - 100) + 50));
		energies.add(e);
	}

	private void logic() {

		for (Cell c : cells) {
			c.go(energies);
			//System.out.println(c.energy);
		}

		if (frame % 1000 == 0)
			addNewEnergy();
		frame++;

		for (int i = 0; i < energies.size(); i++) {
			if (energies.get(i).toBeDeleted) {
				energies.remove(i);
				i--;
			}
		}

	}

	@Override
	public void run() {
		while (frame < FRAMES_TOTAL)
			this.repaint();
	}

}
