import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GomokuPanel extends JPanel {
	private static final long serialVersionUID = 561879861470957894L;
	Gomoku gomoku;
	Cells[][] table;
	static final int DEFAULT_WIDTH = 19;
	static final int DEFAULT_HEIGHT = 19;
	JPanel panelKiri, panelGame;
	JButton play, save, load, restart;
	static JTextArea field;
	static JDialog dialog;
	static JLabel messageLbl;

	public GomokuPanel(int height, int width) {
		gomoku = new Gomoku(height, width);
		//mengatur LayoutManager menjadi GridBagLayout
	    setLayout(new GridBagLayout());
	    GridBagConstraints cons = new GridBagConstraints();

	    //Membuat panel kiri dengan LayoutManager GridBagLayout
		panelKiri = new JPanel();
		panelKiri.setLayout(new GridBagLayout());
		GridBagConstraints left = new GridBagConstraints();

        left.fill = GridBagConstraints.HORIZONTAL;
		
		panelKiri.setPreferredSize(new Dimension(200,400));
		panelKiri.setMaximumSize(new Dimension(200,400));
		panelKiri.setMinimumSize(new Dimension(200,400));
		
		
		//membuat tombol save game
		save = new JButton("SAVE");
        save.addActionListener(new SaveListener());
        left.ipady = 10;
        left.weightx = 0.5;
        left.gridx = 0;
        left.gridy = 2;
        panelKiri.add(save,left);
        
        //memuat tombol load game
        load = new JButton("LOAD");
		load.addActionListener(new LoadListener());
        left.ipady = 10;
        left.weightx = 0.5;
		left.gridx = 1;
        left.gridy = 2;
        panelKiri.add(load,left);
        
		//membuat JTextField untuk status dari permainan
		field = new JTextArea();
		field.setAlignmentX(TextArea.CENTER_ALIGNMENT);
		field.setAlignmentY(TextArea.CENTER_ALIGNMENT);
		field.setEditable(false);
		field.setText(gomoku.getText());
		left.ipady = 40;
		left.weightx = 0.0;
        left.gridwidth = 2;
        left.gridx = 0;
        left.gridy = 0;
        panelKiri.add(field,left);
        
        //membuat tombol restart
        restart = new JButton("RESTART");
        restart.addActionListener(new RestartListener());
        left.ipady = 10;
		left.weightx = 0.0;
        left.gridwidth = 2;
        left.gridx = 0;
        left.gridy = 1;
        panelKiri.add(restart,left);
        
        //mengatur panelKiri dalam GomokuPanel
	    cons.weightx = 0.8;
		cons.gridx = 0;
		cons.gridy = 0;
		add(panelKiri, cons);
		
		// Membuat panel game
		// Panel Game harus berukuran 665 * 665
		panelGame = new JPanel(new GridLayout(height, width));
		panelGame.setPreferredSize(new Dimension(height *35, width*35));
		panelGame.setMaximumSize(new Dimension(height*35, width*35));
		panelGame.setMinimumSize(new Dimension(height*35, width*35));

		//Membuat grid dari Cell sebagai papan permainan
		table = new Cells[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				table[i][j] = new Cells(i, j, height, width);
				table[i][j].addActionListener(gomoku);
				panelGame.add(table[i][j]);
			}
		}
		//mengirimkan data Cells[][] ke class Gomoku
		gomoku.setCells(table);

		//mengatur posisi panelGame dalam GomokuPanel
		cons.gridx = 2;
		cons.gridy = 0;
		
		add(panelGame,cons);
	}

	//Merubah isi dari JTextField
	public static void setFieldText(String s) {
		field.setText(s);
	}

	//Constructor Default
	public GomokuPanel() {
		this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}

	//ActionListener dari button restart
	class RestartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
	        gomoku.restart();
		}
		
	}
	
	//ActionListener dari button save
	class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gomoku.printBoard();
		}
	}

	//ActionListener dari button load
	class LoadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			gomoku.loadGame();
		}
	}
}
