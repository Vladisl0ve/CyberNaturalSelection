package org.CNS.Controller;

import org.CNS.View.Window;

public class Main {

	public static void main(String[] args) {
		Window w = new Window();
		new Thread(w).run();

	}

}
