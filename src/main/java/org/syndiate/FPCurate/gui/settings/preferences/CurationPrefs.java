package org.syndiate.FPCurate.gui.settings.preferences;

import static java.util.Map.entry;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.Element;

import org.apache.commons.validator.routines.UrlValidator;
import org.syndiate.FPCurate.CommonMethods;
import org.syndiate.FPCurate.Curation;
import org.syndiate.FPCurate.SettingsManager;
import org.syndiate.FPCurate.gui.common.SettingsGUI;
import org.syndiate.FPCurate.gui.settings.DocumentChangeListener;
import org.syndiate.FPCurate.gui.settings.SettingsWindow;

public class CurationPrefs extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1393840234024191975L;
	
	
	
	private static final Map<String, String> askFors = Map.ofEntries(
			entry("lcDirDefault", "Ask for launch command, default prefix:"),
			entry("libraryDefault", "Ask whether the curation is a game or animation, default (G/A):"),
			entry("seriesDefault", "Ask for series, default:"),
			entry("devDefault", "Ask for developer, default:"),
			entry("publishDefault", "Ask for publisher, default:"),
			entry("modeDefault", "Ask for play mode, default (s,m):"),
			entry("releaseDefault", "Ask for release date, default:"),
			entry("verDefault", "Ask for version, default:"),
			entry("langDefault", "Ask for languages, default:"),
			entry("tagsDefault", "Ask for tags, default:"),
			entry("srcDefault", "Ask for source, default:"),
			entry("statusDefault", "Ask for status, default (p,pa,h):"),
			entry("gameNotesDefault", "Ask for game notes, default:"),
			entry("descDefault", "Ask for original description, default:"),
			entry("ssDefault", "Prompt screenshot, default action (Y/N):"),
			entry("promptZipDefault", "Prompt to close curation, default action (Y/N):")
	);
	
	
	
	public CurationPrefs() {

		
		this.setLayout(new GridLayout(askFors.size(), 3));
		
		
		for (Entry<String, String> entry : askFors.entrySet()) {
			createAskFor(entry);
		}
		
		
		
	}
	
	
	private void createAskFor(Entry<String, String> entry) {
		
		String id = entry.getKey();
		JCheckBox box = SettingsGUI.createCheckBox(id.replace("Default", "DefOff"));
//		box.setEnabled(Boolean.parseBoolean(SettingsManager.getSetting("lcDirDefGreyed")));
		
		
		JTextField field;
//		JTextField field = SettingsGUI.createTextField(entry.getKey());
		
		switch(id) {
		
			case "lcDirDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					public void update(Document doc) {
						JTextField field = (JTextField) doc.getProperty("parent");
//					JCheckBox box = (JCheckBox) e.getDocument().getProperty("checkBox");
						String fieldId = (String) doc.getProperty("settingsId");
						UrlValidator urlValidator = new UrlValidator(Curation.lcProtocols);

						if (urlValidator.isValid(field.getText())) {
							SettingsWindow.queueSetting(fieldId, field.getText());
//						box.setEnabled(true);
							SettingsWindow.queueSetting("lcDirDefGreyed", true);
							normalFieldBorder(field);

						} else {
//						SettingsWindow.unqueueSetting(fieldId);
							invalidFieldBorder(field);
//						box.setEnabled(false);
//						SettingsGUI.Box(box, false);
							SettingsWindow.queueSetting("lcDirDefGreyed", false);
						}
						SettingsWindow.queueSetting(fieldId, field.getText());
					}
				};
				
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}
			
			
			case "libraryDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					public void update(Document doc) {
						
						JTextField field = (JTextField) doc.getProperty("parent");
						String fieldId = (String) doc.getProperty("settingsId");
						String fieldText = field.getText().toLowerCase();
						
						if (fieldText.equals("g") || fieldText.equals("a")
								|| fieldText.equals("game") || fieldText.equals("animation")
								|| fieldText.equals("library") || fieldText.equals("arcade")) {
							SettingsWindow.queueSetting(fieldId, fieldText);
							normalFieldBorder(field);
						} else {
//							SettingsWindow.unqueueSetting(fieldId);
							invalidFieldBorder(field);
						}
					}
				};
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}
			
			case "modeDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					public void update(Document doc) {
						
						JTextField field = (JTextField) doc.getProperty("parent");
						String fieldId = (String) doc.getProperty("settingsId");
						String fieldText = field.getText().toLowerCase();
						
						if (fieldText.equals("s") || fieldText.equals("m")
							|| fieldText.equals("singleplayer")|| fieldText.equals("multiplayer")) {
							SettingsWindow.queueSetting(fieldId, CommonMethods.correctSeparators(fieldText, ";"));
							normalFieldBorder(field);
						} else {
//							SettingsWindow.unqueueSetting(fieldId);
							invalidFieldBorder(field);
						}
					}
				};
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}
			
			case "langDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					@SuppressWarnings("unchecked")
					public void update(Document doc) {
						
						JTextField field = (JTextField) doc.getProperty("parent");
						String fieldId = (String) doc.getProperty("settingsId");
						String fieldText = CommonMethods.correctSeparators(field.getText().toLowerCase(), ";");
						
						Map<String, String> langs = (Map<String, String>) CommonMethods.parseJSONStr(CommonMethods.getResource("langs.json"));
						for (String lang : fieldText.split(";")) {
							
							if (!langs.containsKey(lang)) {
								invalidFieldBorder(field);
								return;
							}
							
						}
						SettingsWindow.queueSetting(fieldId, fieldText);
						
					}
				};
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}
			
			case "statusDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					public void update(Document doc) {
						
						JTextField field = (JTextField) doc.getProperty("parent");
						String fieldId = (String) doc.getProperty("settingsId");
						String fieldText = field.getText().toLowerCase();
						
						if (fieldText.equals("p") || fieldText.equals("pa") || fieldText.equals("h")
							|| fieldText.equals("playable")|| fieldText.equals("partial") || fieldText.equals("hacked")) {
							SettingsWindow.queueSetting(fieldId, CommonMethods.correctSeparators(fieldText, ";"));
							normalFieldBorder(field);
						} else {
//							SettingsWindow.unqueueSetting(fieldId);
							invalidFieldBorder(field);
						}
						
					}
				};
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}
			
			
			case "promptZipDefault":
			case "ssDefault": {
				
				CurPrefsListener listener = new CurPrefsListener() {
					public void update(Document doc) {
						
						JTextField field = (JTextField) doc.getProperty("parent");
						String fieldId = (String) doc.getProperty("settingsId");
						String fieldText = field.getText().toLowerCase();
						
						if (fieldText.equals("yes") || fieldText.equals("no") 
								|| fieldText.equals("y") || fieldText.equals("n")) {
							SettingsWindow.queueSetting(fieldId, CommonMethods.correctSeparators(fieldText, ";"));
							normalFieldBorder(field);
						} else {
//							SettingsWindow.unqueueSetting(fieldId);
							invalidFieldBorder(field);
						}
						
					}
				};
				field = SettingsGUI.createTextField(id, listener);
				field.getDocument().putProperty("checkBox", box);
				listener.update(field.getDocument());
				
				break;
				
			}

			default:
				field = SettingsGUI.createTextField(id);
				field.setEnabled(!box.isSelected());
				box.addItemListener((ItemEvent e) -> field.setEnabled(!box.isSelected()));
				field.getDocument().putProperty("checkBox", box);
				
		}
		
//		field.setEnabled(!box.isSelected());
//		box.addItemListener((ItemEvent e) -> field.setEnabled(!box.isSelected()));
		
		
		this.add(box);
		this.add(new JLabel(entry.getValue()));
		this.add(field);
	}
	
	
	
	
	private void invalidFieldBorder(JTextField field) {
		field.setBorder(new LineBorder(Color.RED, 1, false));
		JCheckBox box = (JCheckBox) field.getDocument().getProperty("checkBox");
		box.setEnabled(false);
		box.setSelected(true);
	}
	private void normalFieldBorder(JTextField field) {
		field.setBorder(new LineBorder(Color.BLACK, 1, false));
		JCheckBox box = (JCheckBox) field.getDocument().getProperty("checkBox");
		box.setEnabled(true);
	}
	
	

}






abstract class CurPrefsListener extends DocumentChangeListener {
	
	public void update(DocumentEvent e) {
		update(e.getDocument());
	}
	
	public abstract void update(Document doc);
	
}
