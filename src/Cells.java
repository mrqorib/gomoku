import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JButton;

/**
 * Button khusus yang dibuat dengan tambahan instance
 * yang berguna dalam permainan gomoku
 * 
 * @author M. Reza Qorib
 *
 */
public class Cells extends JButton {
	private static final long serialVersionUID = -9066673904978651873L;
	//posisi Cells dalam row
	private int row;
	//posisi Cells dalam column
	private int col;
	//tiga boolean yang berfungsi untuk merubah pengaturan dari paintComponent
	private boolean clicked, blackwins, whitewins;
	//gambar background dari Cells
	private Image background;
	//gambar pin hitam biasa
	private Image pin0;
	//gambar pin putih biasa
	private Image pin1;
	//gambar pin hitam menang
	private Image pin01;
	//gambar pin putih menang
	private Image pin11;
	//path untuk mengatur gambar mana yang digunakan sebagai background
	private String pathBg;
	/*info mengenai player apa yang jalan dengan disimbolkan dengan integer
	 * 0 untuk hitam
	 * 1 untuk putih
	 */
	private int playerInt;

	public Cells(int row, int col, int height, int width) {
		this.row = row;
		this.col = col;
		setBorderPainted(false);
		clicked = false;
		
		//Setiap pinggiran dan pojok dari area permainan memiliki
		//background yang berbeda, begitu pula bagian tengahnya
		if (row == 0 && col == 0) {
			pathBg = "/kiri-atas.png";
		} else if (row == 0 && col == width - 1) {
			pathBg = "/kanan-atas.png";
		} else if (row == height - 1 && col == 0) {
			pathBg = "/kiri-bawah.png";
		} else if (row == height - 1 && col == width - 1) {
			pathBg = "/kanan-bawah.png";
		} else if (row == 0) {
			pathBg = "/atas.png";
		} else if (col == 0) {
			pathBg = "/kiri.png";
		} else if (row == height - 1) {
			pathBg = "/bawah.png";
		} else if (col == width - 1) {
			pathBg = "/kanan.png";
		} else {
			pathBg = "/all.png";
		}
		
		//membuat objek gambar yang mungkin akan dicetak
		URL url = Cells.class.getResource(pathBg);
		background = Toolkit.getDefaultToolkit().createImage(url);
		URL black = Cells.class.getResource("/black.gif");
		URL white = Cells.class.getResource("/white.gif");
		URL bw = Cells.class.getResource("/blackwins.gif");
		URL ww = Cells.class.getResource("/whitewins.gif");
		pin0 = Toolkit.getDefaultToolkit().createImage(black);
		pin1 = Toolkit.getDefaultToolkit().createImage(white);
		pin01 = Toolkit.getDefaultToolkit().createImage(bw);
		pin11 = Toolkit.getDefaultToolkit().createImage(ww);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void paintComponent(Graphics g) {
		//menggambar background
		g.drawImage(background, 0, 0, null);
		//kondisi jika click biasa, jika hitam menang dan jika putih menang
		if (clicked) {
			if (playerInt == 0){
				g.drawImage(pin0, 0, 0, null);
			} else{
				g.drawImage(pin1, 0, 0, null);
			}
		}else if (blackwins){
			g.drawImage(pin01, 0, 0, null);
		} else if (whitewins){
			g.drawImage(pin11, 0, 0, null);
		}
	}

	/**
	 * method untuk menaruh pin dengan merubah variabel playerInt dan clicked
	 * @param colorIndex warna giliran jalan
	 */
	public void putPin(int colorIndex) {
		if (colorIndex % 2 == 0) {
			playerInt = 0;
		} else {
			playerInt = 1;
		}
		clicked = true;
		Graphics g = getGraphics();
		paint(g);
	}
	
	public void setWidth(int width) {
	}

	public void setHeight(int height) {
	}

	public void setClicked(boolean clicked){
		this.clicked = clicked;
	}

	/**
	 * Method untuk mengosongkan kembali Cell tersebut dari Pin
	 *   Hanya meyisakan background untuk digambar
	 */
	public void restart() {
		clicked = false;
		blackwins = false;
		whitewins = false;
		Graphics g = getGraphics();
		paint(g);
	}
	
	/**
	 * Merubah cell tersebut menjadi pin pemenang sesuai dari warna colorIndex
	 * @param colorIndex pemain yang ingin pin nya di set menang dalam simbol integer
	 */
	public void setWin(int colorIndex){
		if (colorIndex == 0){
			blackwins = true;
		} else{
			whitewins = true;
		}
		clicked = false;
		Graphics g = getGraphics();
		paint(g);
	}
}
