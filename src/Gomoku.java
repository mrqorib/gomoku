import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class untung sistem game dari Gomoku
 * @author M. Reza Qorib
 *
 */
public class Gomoku implements ActionListener {
	//Objek board dari gomoku yang akan berisi data kondisi permainan
	private GomokuBoard board;
	//Scanner untuk membaca file jika menggunakan load game
	private Scanner in;
	//Array 2 dimensi of Cells yang dibentuk di GomokuPanel lalu dikirim ke sini
	private Cells[][] cells;
	//Sekedar string untuk mencetak giliran pemain
	private String[] giliran = { "black", "white" };
	//jumlah langkah yang telah terjadi di permainan
	private int jumLangkah = 0;
	//varabel yang menyimpan button yang di klik
	private Cells button;
	//posisi row dari button
	private int row;
	//posisi col dari button
	private int col;
	//variabel yang menyimpan teks yang akan dicetak ke JTextField
	private String text;
	//lebar dan tinggi default dari gomoku
	private final int DEFAULT_HEIGHT = 19;
	private final int DEFAULT_WIDTH = 19;
	/*indeks pemain yang sedang jalan
	 *0 untuk hitam
	 *1 untuk putih*/
	private int pemain;
	//ArrayList yang menyimpan posisi x dan ybutton yang menjadi faktor kemenangan
	private ArrayList<Integer> winnerX;
	private ArrayList<Integer> winnerY;
	//variabel boolean yang mengatur apakah game masih berjalan/tidak
	private boolean gameOver;

	public Gomoku() {
		board = new GomokuBoard();
		text = "welcome";
		gameOver = false;
		winnerX = new ArrayList<>();
		winnerY = new ArrayList<>();
	}
	
	public Gomoku(int height, int width) {
		board = new GomokuBoard(height, width);
		text = "welcome";
		gameOver = false;
		winnerX = new ArrayList<>();
		winnerY = new ArrayList<>();
	}
	
	//Untuk mengirim data seluruh button dari GomokuPanel ke sini
	public void setCells(Cells[][] cells){
		this.cells = cells;
	}

	/**
	 * @param color
	 *            warna yang sedang dalam giliran jalan yang disimbolkan dalam
	 *            integer
	 * 
	 *            hitam disimbolkan dengan index 0 putih disimbolkan dengan
	 *            index 1
	 */
	public void turn(int colorIndex) {
		/*
		 * Mengecek apakah papan sudah penuh atau belum Mengembalikan berita
		 * bahwa permainan draw jika papan penuh
//		 */
		if (jumLangkah >= (board.getWidth() * board.getHeight())) {
			GomokuPanel.setFieldText("draw");
			gameOver = true;
			return;
		}

		if (!board.isAssigned(row, col)) {
			board.putPin(colorIndex, row, col);
			button.putPin(colorIndex);
			jumLangkah++;
			//Jika langkah pemain yang sekarang jalan belum membuatnya menang maka
			//akan dicetak giliran siapa berikutnya
			if (!hasWin(pemain, row, col)) {
				text = giliran[(pemain + 1) % 2] + "'s turn";
			}
			GomokuPanel.setFieldText(text);
		} else
			System.out
					.println("Input salah. Harap masukkan sesuai koordinat yang tersedia");
		// }
	}

	/**
	 * Mengecek apakah bidak yang ditaruh di koordinat x dan y membuatnya menang
	 * 
	 * @param colorIndex
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hasWin(int colorIndex, int x, int y) {
		/*
		 * Aray paralel dX dan dY berupa arah yang akan di cek dari koordinat
		 * input user Geser arah dengan menggeser index dari dX dan dY koordinat
		 * yang dicek dimulai dari atas koordinat user (x ditambah -1, y
		 * ditambah 0) lalu diagonal kanan atas (x ditambah -1, y ditambah 1)
		 * dst
		 */
		int[] dX = { -1, -1, 0, 1 };
		int[] dY = { 0, 1, 1, 1 };
		int count = 0;
		for (int i = 0; i < 4; i++) {
			/*
			 * dari arah yang sudah ditentukan, kita cek dari 4 kotak sejauh
			 * arah positif dari koordinat masukan sampai 4 kotak sejauh arah
			 * negatif dari masukan
			 */
			for (int k = -4; k < 5; k++) {
				int moveX = x + dX[i] * k;
				int moveY = y + dY[i] * k;
				/*
				 * jika koordinat yang akan di cek masih dalam area papan dan
				 * koordinat tersebut sudah diisi oleh pin maka akan di cek
				 * kesamaan warna nya
				 */
				if (isInsideBox(moveX, moveY) && board.isAssigned(moveX, moveY)) {
					/*
					 * jika warna nya sama makan akan ditambahkan dalam count
					 * jika warna nya tidak sama, jika count sudah 4 maka akan
					 * diexit jika warna tidak sama dan count belum 4, count di
					 * reset kembali
					 */
					if (board.getColorIndex(moveX, moveY) == colorIndex) {
						count++;
						winnerX.add(moveX);
						winnerY.add(moveY);
					} else {
						if (count >= 5)
							break;
						count = 0;
						winnerX = new ArrayList<>();
						winnerY = new ArrayList<>();
					}
					/*
					 * jika di luar papan atau koordinat yang akan dicek belum
					 * diisi count akan di reset
					 */
				} else {
					if (count >= 5)
						break;
					count = 0;
					winnerX = new ArrayList<>();
					winnerY = new ArrayList<>();
				}
				/*
				 * ketika berpindah arah pengecekan count di reset
				 */
			}
			if (count >= 5)
				break;
			count = 0;
			winnerX = new ArrayList<>();
			winnerY = new ArrayList<>();
		}
		/*
		 * Ketika count sudah lebih dari sama dengan 5 artinya sudah ada 5 warna
		 * berurutan dalam satu line Maka game dihentikan dan dicetak pengumuman
		 * pemain yang menang
		 */
		if (count >= 5) {
			System.out.println("Selamat! " + giliran[colorIndex]
					+ " telah menang dalam " + jumLangkah + " langkah");
			text = "Congratulation! \n" + giliran[colorIndex]
					+ " wins in " + jumLangkah + " turns";
			for (int i=0; i<winnerX.size();i++){
				cells[winnerX.get(i)][winnerY.get(i)].setWin(colorIndex);
			}
			gameOver = true;
			GomokuPanel.setFieldText(text);
			for (int i=0; i<winnerX.size();i++){
				cells[winnerX.get(i)][winnerY.get(i)].repaint();
			}
			return true;
		} else
			return false;
	}

	/**
	 * Mengecek apakah koordinat berdalam dalam papan permainan
	 * 
	 * @param x
	 *            koordinat x
	 * @param y
	 *            koordinat y
	 * @return true jika koordinat tersebut dalm papan
	 */
	public boolean isInsideBox(int x, int y) {
		return ((0 <= x && x < board.getHeight()) && (0 <= y && y < board
				.getWidth())) ? true : false;
	}

	/**
	 * 
	 * @return Teks yang ingin dicetak ke JTextField
	 */
	public String getText() {
		return text;
	}

	/**
	 * Method untuk me-restart seluruh permainan
	 */
	public void restart(){
		jumLangkah = 0;
		text = "Welcome!";
		board = new GomokuBoard();
		for (int i = 0; i < DEFAULT_HEIGHT; i++){
			for (int j=0; j< DEFAULT_WIDTH; j++){
				cells[i][j].restart();
			}
		}
		gameOver = false;
		GomokuPanel.setFieldText(text);
	}
	
	/**
	 * Method untuk mencetak permainan ke dalam file
	 */
	public void printBoard() {
		PrintWriter printer;
		try {
			printer = new PrintWriter("save1.gomoku");
			board.printBoard(printer);
			GomokuPanel.setFieldText("game saved successfully");
		} catch (FileNotFoundException e) {
			System.out.println("save gagal");
			e.printStackTrace();
		}	
	}

	/**
	 * Method untuk mengisi board permainan dengan input file
	 * yang berisi permainan yang telah disimpan
	 */
	public void loadGame() {
		restart();
		FileReader file;
		try {
			file = new FileReader("save1.gomoku");
			in = new Scanner(file);
			for (int i = 0; i < DEFAULT_HEIGHT; i++) {
				row = i;
				String s = in.nextLine();
				for (int j = 0; j < DEFAULT_WIDTH; j++) {
					col = j;
					char c = s.charAt(j);
					button = cells[i][j];
					switch (c) {
					case 'B':
						turn(0);
						break;
					case 'W':
						turn(1);
						break;
					default:
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("game load error");
		}
		GomokuPanel.setFieldText("game loaded successfully");
		in.close();
	}

	/**
	 * ButtonListener dari Cells yang berfungsi sebagai sarana input dari user
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!gameOver ){
			button = (Cells) arg0.getSource();
			row = button.getRow();
			col = button.getCol();
			pemain = jumLangkah % 2;

			turn(pemain);
		}
	}
	
	/**
	 * Method untuk mengetahui giliran dalam permainan
	 * 
	 * @return giliran sekarang dalam permainan
	 */
	public String getGiliran(){
		return giliran[pemain];
	}

}
