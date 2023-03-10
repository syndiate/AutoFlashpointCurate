package org.syndiate.FPCurate.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import org.syndiate.FPCurate.gui.SettingsWindow;
import org.syndiate.FPCurate.gui.common.CommonGUI;

import java.io.File;



public class MainWindow {

	private JFrame frmAPCurator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			try {
				MainWindow window = new MainWindow();
				window.frmAPCurator.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @wbp.parser.entryPoint
	 */
	public MainWindow() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		
		
		
		frmAPCurator = new JFrame();
		frmAPCurator.getContentPane().setBackground(new Color(255, 255, 255));
		frmAPCurator.setBackground(new Color(255, 255, 255));
		frmAPCurator.setTitle("AutoFPCurator");
		frmAPCurator.setBounds(100, 100, 1280, 720);
		frmAPCurator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAPCurator.setLocationRelativeTo(null);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.WHITE);
		frmAPCurator.setJMenuBar(menuBar);
		
		

		JMenu fileMenu = new JMenu("File");
		
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener((ActionEvent e) -> {
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
			    File selectedFile = fileChooser.getSelectedFile();
			    // Code to open the selected file goes here
			}
			
		});
		CommonGUI.setMenuItemShortcut(openItem, KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		fileMenu.add(openItem);
		
		
		
		JMenuItem preferences = new JMenuItem("Preferences");
		preferences.addActionListener((ActionEvent e) -> {
			new SettingsWindow();
		});
		CommonGUI.setMenuItemShortcut(preferences, KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
		fileMenu.add(preferences);
		
		
		menuBar.add(fileMenu);
		
		JLabel label = new JLabel("<html>Navigate to File > Open and select a file/directory to begin. "
				+ "Select a file (SWF or ZIP) if you would to curate a game. "
				+ "Select a directory if you would like AutoFPCurator to"
				+ "<br> iterate through every Flash game in the folder."
				+ "<br><br><b>NOTICE:</b>"
				+ "<br>This tool is only designed FOR SINGLE ASSET GAMES that work in the Flash projector OR in a standard HTML embed. Otherwise, please use Flashpoint Core.");
        label.setFont(label.getFont().deriveFont(16.0f)); // set font size to 64
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(label, new GridBagConstraints());

        frmAPCurator.add(panel, BorderLayout.CENTER);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}