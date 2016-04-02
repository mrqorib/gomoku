public class Pin {
	private char[] arrColorSymbol = {'B','W'};
	private char colorSymbol;
	private int colorIndex;
	
	/**
	 * Membuat objek pin
	 * @param colorIndex index warna pin yang akan dibuat
	 * 
	 * 0 untuk hitam
	 * 1 untuk putih
	 */
	public Pin(int colorIndex){
		this.colorIndex = colorIndex;
		this.colorSymbol = arrColorSymbol[colorIndex];
	}

	public int getColorIndex() {
		return colorIndex;
	}
	
	public char getColorSymbol(){
		return colorSymbol;
	}
	
}
