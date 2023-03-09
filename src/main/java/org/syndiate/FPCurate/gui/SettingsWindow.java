package org.syndiate.FPCurate.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.syndiate.FPCurate.I18N;
import org.syndiate.FPCurate.SettingsManager;
import org.syndiate.FPCurate.gui.preferences.GeneralPrefs;
import org.syndiate.FPCurate.gui.preferences.Paths;

public class SettingsWindow extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3875324643848537471L;
	private static Map<String, String> queuedSettings = new HashMap<>();
	

	public SettingsWindow() {
		
		Map<String, String> settingsMenuStrs = I18N.getStrings("settings");
		
        this.setTitle(settingsMenuStrs.get("windowTitle"));
        this.setSize(960, 540);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);

        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(1, 1));
        generalPanel.add(new JLabel("Setting 1:"));
        generalPanel.add(new JTextField());
        generalPanel.add(new JLabel("Setting 2:"));
        generalPanel.add(new JTextField());

        JPanel advancedPanel = new JPanel();
        advancedPanel.setLayout(new GridLayout(2, 2));
        advancedPanel.add(new JLabel("Setting 3:"));
        advancedPanel.add(new JTextField());
        advancedPanel.add(new JLabel("Setting 4:"));
        advancedPanel.add(new JTextField());

        tabbedPane.addTab(settingsMenuStrs.get("generalTab"), new GeneralPrefs());
        tabbedPane.addTab(settingsMenuStrs.get("pathsTab"), new Paths());
        tabbedPane.addTab("Advanced", advancedPanel);

        this.add(tabbedPane, BorderLayout.NORTH);
/*
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 2));
        bottomPanel.add(new JLabel("Setting 5:"));
        bottomPanel.add(new JTextField());
        bottomPanel.add(new JLabel("Setting 6:"));
        bottomPanel.add(new JTextField());*/

//        add(bottomPanel, BorderLayout.CENTER);
        
        
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);



		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener((ActionEvent e) -> {
			SettingsWindow.saveSettings();
			dispose();
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);


		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		

        this.setVisible(true);
    }
	
	
	
	
	public static void queueSetting(String key, String value) {
		SettingsWindow.queuedSettings.put(key, value);
	}
	
	public static void saveSettings() {
		for (Map.Entry<String, String> entry: SettingsWindow.queuedSettings.entrySet()) {
			SettingsManager.saveSetting(entry.getKey(), entry.getValue());
		}
	}
	
	
	
	
	
	

    public static void main(String[] args) {
        new SettingsWindow();
    }
}