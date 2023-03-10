package org.syndiate.FPCurate.gui.common;

import java.awt.event.ItemEvent;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.syndiate.FPCurate.SettingsManager;
import org.syndiate.FPCurate.gui.SettingsWindow;



public class SettingsGUI {
	
	
	public static JTextField createTextField(String settingsId) {
		
		JTextField textField = new JTextField();
		textField.setText(SettingsManager.getSetting(settingsId));
		
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
            public void insertUpdate(DocumentEvent e) {
				if (SettingsManager.getSetting(settingsId).equals(textField.getText())) {
					return;
				}
                SettingsWindow.queueSetting(settingsId, textField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
            	if (SettingsManager.getSetting(settingsId).equals(textField.getText())) {
					return;
				}
                SettingsWindow.queueSetting(settingsId, textField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            	if (SettingsManager.getSetting(settingsId).equals(textField.getText())) {
					return;
				}
                SettingsWindow.queueSetting(settingsId, textField.getText());
            }
		});
		
		return textField;
		
	}
	
	public static JComboBox<String> createDropdown(String settingsId, Map<String, String> dropdownItems) {
		
		JComboBox<String> dropdown = new JComboBox<>();
		
		dropdown.addItemListener((ItemEvent e) -> {
			SettingsWindow.queueSetting(settingsId,  dropdown.getSelectedItem().toString());
		});
		
		return dropdown;
		
		
	}


}
