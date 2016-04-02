import java.io.PrintWriter;

public class GomokuBoard {
	private Pin[][] board;
	private boolean[][] assign;
	private int width;
	private int height;
	
	GomokuBoard(){
		this (19,19);
	}
	
	/**
	 * Membuat board permainan dengan panjang
	 * dan lebar yang ditentukan
	 * 
	 * @param height lebar papan
	 * @param width panjang papan
	 */
	GomokuBoard(int height, int width){
		this.height = height;
		this.width = width;
		board = new Pin[height][width];
		assign = new boolean[height][width];
		System.out.println("Besar Papan adalah 19x19.");
		System.out.println("Koordinat yang sah adalah: 1- 19.");
	}
	
	/**
	 * Method menambahkan pin saat permianan dalam
	 * koordinat tertentu
	 * 
	 * @param color warna yang ingin ditambahkan
	 * @param x koordinat x
	 * @param y koordinat y
	 */
	
	public void putPin(int color, int x,int y){
		board[x][y] = new Pin(color);
		assign[x][y] = true;
	}
	
	
	/**
	 * Print seluruh papan ke dalam file text
	 */
	public void printBoard(PrintWriter out){
		for (int i=0; i<height; i++){
			for (int j=0; j<width; j++){
				if (isAssigned(i,j))
					out.print(board[i][j].getColorSymbol());
				else
					out.print(".");
			}
			out.println();
		}
		out.close();
		System.out.println("save berhasil");
	}
	
	/**
	 * Meminta indeks warna dari koordinat tertentu
	 * 
	 * @param x
	 * @param y
	 * @return nilai index dari warna pada koordinat x,y
	 * 			0 untuk hitam
	 * 			1 untuk putih
	 */
	public int getColorIndex(int x, int y){
		return board[x][y].getColorIndex();
	}
	
	
	/**
	 * Mengecek apakah kotak tersebut sudah terisi pin
	 * atau belum
	 * 
	 * @param x
	 * @param y
	 * @return boolean true jika koordinat sudah terisi
	 * 				  false jika koordinat masih kosong
	 */
	public boolean isAssigned(int x, int y){
		return assign[x][y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
}
