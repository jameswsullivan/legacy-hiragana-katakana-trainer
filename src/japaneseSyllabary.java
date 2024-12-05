import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;

import javax.swing.JSeparator;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;

import javazoom.jl.player.*;

import java.io.FileInputStream;
import java.io.IOException;

public class japaneseSyllabary extends JFrame{

	//Resources Declaration.
	private resources res = new resources();
	private viewAllSyllable viewAll = new viewAllSyllable();
	private presetSettings preset = new presetSettings();
	private Random random = new Random();
	private JPanel contentPane;
	private AtomicBoolean paused;
	private Thread flashCard;
	private int from, to, delay, nextOne, randomTemp, flashcardIndex;
	private boolean showHide;
	private final String rangeWarn = "\"From\" and \"To\" should be between 1 and " + res.syllabary.length + ", \"From\" shoud be less than \"To\".";
	private final String emptyWarn = "\"From\" and \"To\" can't be empty. Please try again.";
	Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
	//End of Declaration of Resources.
	
	//Upper Section Declarations.
	private JMenuBar menuBar;
	private JMenu fileMenu, helpMenu;
	private JMenuItem viewAllMenuItem, exitMenuItem, instructionsMenuItem, aboutMenuItem;
	
	private JLabel upperSectionLabel, setRangeLabel, fromLabel, toLabel, minLabel, maxLabel, delayLabel, delayDisplayField, practiceLabel; 
	private JTextField fromField, toField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton pronCheckbox, hkCheckbox, dictationCheckbox;
	private JCheckBox randomCheckbox;
	private JButton setButton, resetButton, viewAllButton, saveProgressButton;
	private JSeparator upperSeparator;
	private JSlider delaySlider;
	//Actions.
	private ChangeListener delaySliderAction;
	private FocusListener fromFocusAction, toFocusAction, delayFocusAction;
	private AbstractAction resetButtonAction, viewAllButtonAction;
	private ActionListener fromAction, toAction, delayAction, setButtonAction, pronCheckboxAction, hkCheckboxAction, dictationCheckboxAction, randomCheckboxAction;
	//End of Upper Section Declarations.
	
	//Middle Section Declarations.
	private JSeparator middleSeparator;
	private JLabel showBoxl, showBoxm, showBoxr;
	private JCheckBox alwaysShowCheckbox;
	private JButton showButton, nextButton, pronounceButton, flashcardButton, pauseButton, endFlashcardButton;
	//Actions.
	private AbstractAction showButtonAction, nextButtonAction, pronounceButtonAction, flashcardButtonAction, pauseButtonAction, endFlashcardButtonAction;
	private ActionListener alwaysShowAction;
	//End of Middle Section Declarations.
	
	//Lower Section Declarations.
	private JSeparator lowerSeparator;
	private JButton generateSentenceButton, sentenceAnswerButton, pronounceSentenceButton, clearMultipleFieldsButton;
	private JLabel lowerSectionLabel, sentenceArea, answerArea;
	private String sentence, sentenceAnswer1, sentenceAnswer2;
	private String[] sentencePronounciation;
	//Actions.
	private AbstractAction generateSentenceButtonAction, sentenceAnswerButtonAction, pronounceSentenceButtonAction, clearMultipleFieldsButtonAction;
	//End of Lower Section Declarations.
	
	//Main Program.
	public static void main(String[] args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					japaneseSyllabary trainer = new japaneseSyllabary();
					trainer.setResizable(false);
					trainer.setVisible(true);
					trainer.requestFocusInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	//End of Main Program.
	
	//Constructor.
	public japaneseSyllabary() throws IOException, InterruptedException {
		declareActionListener();
		setLayout();
		init();
		startRunnable();
		enableAllFields();
		initDisableFields();
	}
	//End of Constructor.
	
	//Action Listener Body.
	public void declareActionListener()
	{
		//Upper Left Section Actions
		fromFocusAction = new FocusListener() {
			public void focusGained(FocusEvent e) {
				fromField.setText("");
				setButton.setText("Start");
				rangeFieldFocusEnableDisableFields();
			}
			public void focusLost(FocusEvent e) {}
		};
		
		toFocusAction  = new FocusListener() {
			public void focusGained(FocusEvent e) {
				toField.setText("");
				setButton.setText("Set to Begin");
				rangeFieldFocusEnableDisableFields();
			}
			public void focusLost(FocusEvent e) {}
		};
		
		delaySliderAction = new ChangeListener () {
			public void stateChanged(ChangeEvent e) {
				if (delaySlider.getValue()%100 == 0)
				{
					delayDisplayField.setText(Double.toString((double)delaySlider.getValue()/1000));
					delay = delaySlider.getValue();
				}
			}
		};
		
		setButtonAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fromTemp, toTemp;
				if (fromField.getText().equals("") || toField.getText().equals("")) showMsg(emptyWarn);
				else
				{
					fromTemp = (int)Math.floor(Double.parseDouble(fromField.getText()));
					toTemp = (int)Math.floor(Double.parseDouble(toField.getText()));
					if (fromTemp<0 || toTemp<0 || fromTemp>res.syllabary.length || toTemp>res.syllabary.length || fromTemp>toTemp ) showMsg(rangeWarn);
					else
					{
						from = fromTemp - 1;
						to = toTemp - 1;
						flashcardIndex = from;
						nextOne = from;
						randomTemp = random.nextInt(to - from + 1) + from;
						showHide = false;
						setEnableFields();
					}
					if (randomCheckbox.isSelected()) showSyllable(randomTemp); else showSyllable(nextOne);
					setButton.setEnabled(false);
					setTitle("Japanese Syllabary Trainer (Index: "+Integer.toString(nextOne+1)+")");
				}
			}
		};		
		//End of Upper Left Section
		
		//Upper Right Section Actions
		pronCheckboxAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paused.get() == true)
				{
					alwaysShowCheckbox.setEnabled(true);
					if (randomCheckbox.isSelected()) showSyllable(randomTemp); else showSyllable(nextOne);
				}
			}
		};
		
		hkCheckboxAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paused.get() == true)
				{
					alwaysShowCheckbox.setEnabled(true);
					if (randomCheckbox.isSelected()) showSyllable(randomTemp); else showSyllable(nextOne);
				}
			}
		};
		
		dictationCheckboxAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paused.get() == true)
				{
					alwaysShowCheckbox.setEnabled(false);
					showBoxl.setText("");
					showBoxm.setText("");
					showBoxr.setText("");
				}
			}
		};
		
		randomCheckboxAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (randomCheckbox.isSelected()) { showSyllable(randomTemp); }
				if (!randomCheckbox.isSelected()) { showSyllable(nextOne); }
			}
		};
		
		resetButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				try { resetAll(); }	catch (IOException err) { err.printStackTrace(); }
			}
		};
		
		viewAllButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				viewAll.setVisible(true);
			}
		};
		//End of Upper Right Section
		
		//Middle Section Actions
		showButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				showHide = !showHide;
				if (randomCheckbox.isSelected()) showSyllable(randomTemp); else showSyllable(nextOne);
			}
		};
		
		alwaysShowAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showButton.setEnabled(!alwaysShowCheckbox.isSelected());
				if (randomCheckbox.isSelected()) showSyllable(randomTemp); else showSyllable(nextOne);
			}
		};
		
		nextButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				showHide = false;
				if (randomCheckbox.isSelected())
				{
					randomTemp = random.nextInt(to - from + 1) + from;
					showSyllable(randomTemp);
				}
				else
				{
					if (nextOne == to) nextOne = from; else nextOne++;
					showSyllable(nextOne);
				}
			}
		};
		
		pronounceButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String s = "";
				if (randomCheckbox.isSelected())
				{
					s = res.soundFolder + res.syllabary[randomTemp][0] + res.soundExtension;
					playPronounciation(s);
				}
				else
				{
					s = res.soundFolder + res.syllabary[nextOne][0] + res.soundExtension;
					playPronounciation(s);
				}
			}
		};
		
		flashcardButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (flashcardButton.getText().equals("Flashcard (Home)")) flashcardButton.setText("Restart (Home)");
				flashcardIndex = from;
				showHide = true;
				flashcardEnableDisableFields();
				paused.set(false);
				synchronized(flashCard)
				{
					flashCard.notify();
				}
			}
		};
		
		pauseButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (paused.get())
				{
						paused.set(false);
						synchronized(flashCard)
						{
							flashCard.notify();
						}
						pauseButton.setText("Pause (End)");
				}
				else if (!paused.get())
				{
					paused.set(true);
					pauseButton.setText("Resume (End)");
				}
				contentPane.requestFocus();
			}
		};
		
		endFlashcardButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				paused.set(true);
				endFlashcardEnableDisableFields();
				flashcardButton.setText("Flashcard (Home)");
			}
		};
		//End of Middle Section Actions
		
		//Lower Section Actions
		generateSentenceButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				sentenceAnswerButton.setEnabled(true);
				pronounceSentenceButton.setEnabled(true);
				clearMultipleFieldsButton.setEnabled(true);
				sentence = ""; sentenceAnswer1 = ""; sentenceAnswer2 = "";
				int length, hk, syllable;
				hk = length = syllable = 0;
				if (to < 51) length = 20; else length = 12;
				syllable = random.nextInt(to - from + 1) + from;
				sentencePronounciation = new String[length];
				if (hkCheckbox.isSelected()) hk = random.nextInt(2) + 1;
				if (pronCheckbox.isSelected()) hk = 0;
				for (int i=0; i<length; i++)
				{
					sentence += res.syllabary[syllable][hk];
					sentencePronounciation[i] = res.soundFolder + res.syllabary[syllable][0] + res.soundExtension;
					if (i!=length-1) sentence+= " ";
					if (hkCheckbox.isSelected())
					{
						sentenceAnswer1 += res.syllabary[syllable][0];
						if (i!=length-1) sentenceAnswer1 += " ";
					}
					if (pronCheckbox.isSelected())
					{
						sentenceAnswer1 += res.syllabary[syllable][1];
						sentenceAnswer2 += res.syllabary[syllable][2];
						if (i!=length-1) { sentenceAnswer1 += " "; sentenceAnswer2 += " "; }
					}
					syllable = random.nextInt(to - from + 1) + from;
				}
				sentenceArea.setText(sentence);
				answerArea.setText("");
			}
		};
		
		sentenceAnswerButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String s = "";
				s = "<html><center>" + sentenceAnswer1 + "<br/>" + sentenceAnswer2 + "</center></html>";
				answerArea.setText(s);
			}
		};
		
		pronounceSentenceButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				for (String x : sentencePronounciation)
				{
					playPronounciation(x);
				}
			}
		};
		
		clearMultipleFieldsButtonAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				sentenceArea.setText("");
				answerArea.setText("");
				sentence = ""; sentenceAnswer1 = ""; sentenceAnswer2 = "";
				sentenceAnswerButton.setEnabled(false);
				pronounceSentenceButton.setEnabled(false);
				clearMultipleFieldsButton.setEnabled(false);
			}
		};
		//End of Lower Section Actions
	}
	
	public void setLayout()
	{
		//Main Window Layout.
		setTitle("Japanese Syllabary Trainer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 670);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { requestFocus(); } });
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		//End of Main Window Layout.
		
		//Menu Layout.
		//Menu Bar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		//End of Menu Bar
		
		//File Menu
		fileMenu = new JMenu("File");
		fileMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(fileMenu);
		
		viewAllMenuItem = new JMenuItem("View Syllable Chart");
		viewAllMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { viewAll.setVisible(true); } });
		viewAllMenuItem.setFont(new Font("Arial", Font.PLAIN, 18));
		fileMenu.add(viewAllMenuItem);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { System.exit(0); } });
		exitMenuItem.setFont(new Font("Arial", Font.PLAIN, 18));
		fileMenu.add(exitMenuItem);
		//End of File Menu
		
		//Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setFont(new Font("Arial", Font.PLAIN, 18));
		menuBar.add(helpMenu);
		
		instructionsMenuItem = new JMenuItem("Instructions");
		instructionsMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { JOptionPane.showMessageDialog(null, res.instruction); } });
		instructionsMenuItem.setFont(new Font("Arial", Font.PLAIN, 18));
		helpMenu.add(instructionsMenuItem);
		
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { JOptionPane.showMessageDialog(null, res.about); } });
		aboutMenuItem.setFont(new Font("Arial", Font.PLAIN, 18));
		helpMenu.add(aboutMenuItem);
		//End of Help Menu
		//End of Menu Layout
		
		//Upper Section Layout
		upperSectionLabel = new JLabel("Practice Single Syllables");
		upperSectionLabel.setFont(new Font("Arial", Font.BOLD, 17));
		upperSectionLabel.setBounds(20, 11, 220, 20);
		contentPane.add(upperSectionLabel);
		
		setRangeLabel = new JLabel("Set Practice Range");
		setRangeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		setRangeLabel.setBounds(20, 42, 140, 20);
		contentPane.add(setRangeLabel);

		fromLabel = new JLabel("From");
		fromLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		fromLabel.setBounds(20, 73, 46, 20);
		contentPane.add(fromLabel);
		
		toLabel = new JLabel("To");
		toLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		toLabel.setBounds(20, 98, 20, 20);
		contentPane.add(toLabel);
		
		minLabel = new JLabel("Min: 1");
		minLabel.setBounds(153, 73, 46, 14);
		contentPane.add(minLabel);
		
		maxLabel = new JLabel("Max:"+Integer.toString(res.syllabary.length));
		maxLabel.setBounds(153, 99, 70, 14);
		contentPane.add(maxLabel);
		
		fromField = new JTextField("");
		fromField.setBounds(63, 73, 80, 20);
		contentPane.add(fromField);
		fromField.setColumns(10);
		fromField.addFocusListener(fromFocusAction);
		fromField.addActionListener(setButtonAction);
		
		toField = new JTextField("");
		toField.setBounds(63, 96, 80, 20);
		contentPane.add(toField);
		toField.setColumns(10);
		toField.addFocusListener(toFocusAction);
		toField.addActionListener(setButtonAction);
		
		setButton = new JButton("Set and Begin");
		setButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		setButton.setBounds(20, 123, 180, 23);
		contentPane.add(setButton);
		setButton.addActionListener(setButtonAction);
		
		upperSeparator = new JSeparator();
		upperSeparator.setOrientation(SwingConstants.VERTICAL);
		upperSeparator.setBounds(258, 11, 2, 132);
		contentPane.add(upperSeparator);

		delayLabel = new JLabel("Delay (s)");
		delayLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		delayLabel.setBounds(271, 11, 70, 20);
		contentPane.add(delayLabel);
		
		delaySlider = new JSlider(JSlider.HORIZONTAL, 500, 5000, 500);
		delaySlider.setValue(500);
		delaySlider.setMajorTickSpacing(500);
		delaySlider.setMinorTickSpacing(500);
		delaySlider.setSnapToTicks(true);
		delaySlider.setPaintTicks(true);
		delaySlider.setBounds(337, 11, 200, 26);
		delaySlider.addFocusListener(delayFocusAction);
		delaySlider.addChangeListener(delaySliderAction);
		contentPane.add(delaySlider);
		
		delayDisplayField = new JLabel(Double.toString((double)delaySlider.getValue()/1000));
		delayDisplayField.setBounds(547, 11, 40, 20);
		delayDisplayField.setHorizontalAlignment(SwingConstants.CENTER);
		delayDisplayField.setBackground(Color.WHITE);
		delayDisplayField.setOpaque(true);
		contentPane.add(delayDisplayField);
		
		practiceLabel = new JLabel("Practice");
		practiceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		practiceLabel.setBounds(271, 42, 70, 20);
		contentPane.add(practiceLabel);
		
		pronCheckbox = new JRadioButton("Pronunciation");
		pronCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(pronCheckbox);
		pronCheckbox.setBounds(337, 46, 160, 20);
		contentPane.add(pronCheckbox);
		pronCheckbox.addActionListener(pronCheckboxAction);
		
		hkCheckbox = new JRadioButton("Hiragana | Katakana");
		hkCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(hkCheckbox);
		hkCheckbox.setBounds(337, 71, 200, 20);
		contentPane.add(hkCheckbox);
		hkCheckbox.setSelected(true);
		hkCheckbox.addActionListener(hkCheckboxAction);
		
		dictationCheckbox = new JRadioButton("Dictation");
		dictationCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonGroup.add(dictationCheckbox);
		dictationCheckbox.setBounds(337, 96, 110, 20);
		contentPane.add(dictationCheckbox);
		dictationCheckbox.addActionListener(dictationCheckboxAction);
		
		randomCheckbox = new JCheckBox("Random");
		randomCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		randomCheckbox.setBounds(271, 127, 80, 14);
		contentPane.add(randomCheckbox);
		randomCheckbox.addActionListener(randomCheckboxAction);
		
		resetButton = new JButton("Reset Program");
		resetButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		resetButton.addActionListener(resetButtonAction);
		resetButton.setBounds(599, 58, 190, 25);
		contentPane.add(resetButton);
		
		saveProgressButton = new JButton("Save Progress");
		saveProgressButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { try { preset.saveSettings(from, to, delay, nextOne); } catch (IOException err) { err.printStackTrace(); } } });
		saveProgressButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveProgressButton.setBounds(599, 88, 190, 25);
		contentPane.add(saveProgressButton);
		
		viewAllButton = new JButton("View Syllable Chart");
		viewAllButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		viewAllButton.addActionListener(viewAllButtonAction);
		viewAllButton.setBounds(599, 118, 190, 25);
		contentPane.add(viewAllButton);
		
		middleSeparator = new JSeparator();
		middleSeparator.setBounds(10, 152, 779, 2);
		contentPane.add(middleSeparator);
		//End of Upper Section Layout
		
		//Middle Section Layout
		showBoxl = new JLabel();
		showBoxl.setFont(new Font("Microsoft YaHei", Font.PLAIN, 90));
		showBoxl.setBounds(10, 167, 200, 200);
		showBoxl.setBackground(Color.WHITE);
		showBoxl.setOpaque(true);
		showBoxl.setBorder(border);
		showBoxl.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(showBoxl);
		
		showBoxm = new JLabel();
		showBoxm.setFont(new Font("Microsoft YaHei", Font.PLAIN, 90));
		showBoxm.setBounds(222, 167, 200, 200);
		showBoxm.setBackground(Color.WHITE);
		showBoxm.setOpaque(true);
		showBoxm.setBorder(border);
		showBoxm.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(showBoxm);
		
		showBoxr = new JLabel();
		showBoxr.setFont(new Font("Microsoft YaHei", Font.PLAIN, 90));
		showBoxr.setBounds(434, 167, 200, 200);
		showBoxr.setBackground(Color.WHITE);
		showBoxr.setOpaque(true);
		showBoxr.setBorder(border);
		showBoxr.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(showBoxr);
		
		showButton = new JButton("<html><center>Show | Hide<br/>Answer (PgU)</center></html>");
		showButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		showButton.addActionListener(showButtonAction);
		showButton.setBounds(646, 167, 143, 45);
		contentPane.add(showButton);
		showButton.setEnabled(true);
		showButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_UP, 0), "PgUPressed");
		showButton.getActionMap().put("PgUPressed", showButtonAction);
		
		alwaysShowCheckbox = new JCheckBox("Always Show");
		alwaysShowCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		alwaysShowCheckbox.setBounds(646, 215, 143, 20);
		contentPane.add(alwaysShowCheckbox);
		alwaysShowCheckbox.setEnabled(true);
		alwaysShowCheckbox.addActionListener(alwaysShowAction);
		
		nextButton = new JButton("Next (PgD)");
		nextButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nextButton.addActionListener(nextButtonAction);
		nextButton.setBounds(646, 240, 143, 23);
		contentPane.add(nextButton);
		nextButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PAGE_DOWN, 0), "PgDPressed");
		nextButton.getActionMap().put("PgDPressed", nextButtonAction);
		
		pronounceButton = new JButton("Pronounce (Ins)");
		pronounceButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pronounceButton.setBounds(646, 266, 143, 23);
		pronounceButton.addActionListener(pronounceButtonAction);
		contentPane.add(pronounceButton);
		pronounceButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0), "InsertPressed");
		pronounceButton.getActionMap().put("InsertPressed", pronounceButtonAction);
		
		flashcardButton = new JButton("Flashcard (Home)");
		flashcardButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		flashcardButton.addActionListener(flashcardButtonAction);
		flashcardButton.setBounds(646, 292, 143, 23);
		contentPane.add(flashcardButton);
		flashcardButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_HOME,0), "HomePressed");
		flashcardButton.getActionMap().put("HomePressed", flashcardButtonAction);
		
		pauseButton = new JButton("Pause (End)");
		pauseButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pauseButton.addActionListener(pauseButtonAction);
		pauseButton.setBounds(646, 318, 143, 23);
		contentPane.add(pauseButton);
		pauseButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_END,0), "EndPressed");
		pauseButton.getActionMap().put("EndPressed", pauseButtonAction);
		
		endFlashcardButton = new JButton("End (Esc)");
		endFlashcardButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		endFlashcardButton.addActionListener(endFlashcardButtonAction);
		endFlashcardButton.setEnabled(true);
		endFlashcardButton.setBounds(646, 344, 143, 23);
		contentPane.add(endFlashcardButton);
		endFlashcardButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE,0), "EscPressed");
		endFlashcardButton.getActionMap().put("EscPressed", endFlashcardButtonAction);
		//End of Middle Section Layout
		
		//Lower Section Layout
		lowerSeparator = new JSeparator();
		lowerSeparator.setBounds(10, 380, 779, 2);
		contentPane.add(lowerSeparator);
		
		lowerSectionLabel = new JLabel("Practice Multiple Syllables");
		lowerSectionLabel.setFont(new Font("Arial", Font.BOLD, 17));
		lowerSectionLabel.setBounds(10, 396, 220, 20);
		contentPane.add(lowerSectionLabel);
		
		generateSentenceButton = new JButton("Generate (F10)");
		generateSentenceButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		generateSentenceButton.setBounds(235, 395, 145, 25);
		generateSentenceButton.addActionListener(generateSentenceButtonAction);
		generateSentenceButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0), "F10Pressed");
		generateSentenceButton.getActionMap().put("F10Pressed", generateSentenceButtonAction);
		contentPane.add(generateSentenceButton);
		
		sentenceAnswerButton = new JButton("Answer (F11)");
		sentenceAnswerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sentenceAnswerButton.setBounds(390, 395, 145, 25);
		sentenceAnswerButton.addActionListener(sentenceAnswerButtonAction);
		sentenceAnswerButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0), "F11Pressed");
		sentenceAnswerButton.getActionMap().put("F11Pressed", sentenceAnswerButtonAction);
		contentPane.add(sentenceAnswerButton);
		
		pronounceSentenceButton = new JButton("Read (F12)");
		pronounceSentenceButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pronounceSentenceButton.setBounds(545, 395, 145, 25);
		pronounceSentenceButton.addActionListener(pronounceSentenceButtonAction);
		pronounceSentenceButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0), "F12Pressed");
		pronounceSentenceButton.getActionMap().put("F12Pressed", pronounceSentenceButtonAction);
		contentPane.add(pronounceSentenceButton);
		
		clearMultipleFieldsButton = new JButton("Clear");
		clearMultipleFieldsButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clearMultipleFieldsButton.addActionListener(clearMultipleFieldsButtonAction);
		clearMultipleFieldsButton.setBounds(700, 396, 90, 25);
		contentPane.add(clearMultipleFieldsButton);
		
		sentenceArea = new JLabel("");
		sentenceArea.setBounds(10, 431, 779, 45);
		sentenceArea.setBackground(Color.WHITE);
		sentenceArea.setBorder(border);
		sentenceArea.setOpaque(true);
		sentenceArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 25));
		sentenceArea.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(sentenceArea);
		
		answerArea = new JLabel("");
		answerArea.setBounds(10, 487, 779, 85);
		answerArea.setBackground(Color.WHITE);
		answerArea.setBorder(border);
		answerArea.setOpaque(true);
		answerArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 25));
		answerArea.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(answerArea);
		//End of Lower Section Layout
	}
	
	public void init() throws IOException
	{
		if (preset.fileExists())
		{
			preset.getSettings();
			fromField.setText(preset.presetFrom);
			toField.setText(preset.presetTo);
			delay = preset.presetDelay;
			delaySlider.setValue(delay);
			delayDisplayField.setText(Double.toString((double)delaySlider.getValue()/1000));
			flashcardIndex = from;
			nextOne = preset.presetPos;
			randomTemp = random.nextInt(to - from + 1) + from;
			sentenceArea.setText("");
			answerArea.setText("");
			
			setButton.setText("Ready to Begin");
		}
		if (!preset.fileExists())
		{
			from = 0;
			to = res.syllabary.length - 1;
			
			delaySlider.setValue(500);
			delay = delaySlider.getValue();
			delayDisplayField.setText(Double.toString((double)delaySlider.getValue()/1000));
			
			flashcardIndex = from;
			nextOne = from;
			randomTemp = random.nextInt(to - from + 1) + from;
			
			showBoxl.setText(""); showBoxm.setText(""); showBoxr.setText("");
			fromField.setText(""); toField.setText("");
			sentenceArea.setText(""); answerArea.setText("");
			preset.saveSettings(from, to, delay, nextOne);
		}
	}
	
	public void startRunnable()
	{
		//Flashcard Thread Runnable
		paused = new AtomicBoolean(true);
		Runnable runnable = new Runnable() {
			public void run() {
				while (true)
				{
					if (paused.get() == true)
					{
						synchronized(flashCard)
						{
							try {
								flashCard.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					if (randomCheckbox.isSelected())
					{
						showSyllable(random.nextInt(to - from + 1) + from);
						try { Thread.sleep(delay); } catch (InterruptedException e) { e.printStackTrace(); }
					}
					else
					{
						showSyllable(flashcardIndex);
						if (flashcardIndex == to) flashcardIndex = from; else flashcardIndex++;
						try { Thread.sleep(delay); } catch (InterruptedException e) { e.printStackTrace(); }
					}
				}
			}
		};
		flashCard = new Thread(runnable);
		flashCard.start();
	}
	
	public void resetAll() throws IOException
	{
		paused.set(true);
		init();
		enableAllFields();
		initDisableFields();
	}
	
	public void enableAllFields()
	{
		fromField.setEnabled(true);
		toField.setEnabled(true);
		setButton.setEnabled(true);
		delaySlider.setEnabled(true);
		pronCheckbox.setEnabled(true);
		hkCheckbox.setEnabled(true);
		hkCheckbox.setSelected(true);
		dictationCheckbox.setEnabled(true);
		randomCheckbox.setEnabled(true);
		randomCheckbox.setSelected(false);
		saveProgressButton.setEnabled(true);
		viewAllButton.setEnabled(true);
		
		showButton.setEnabled(true);
		alwaysShowCheckbox.setEnabled(true);
		alwaysShowCheckbox.setSelected(false);
		nextButton.setEnabled(true);
		pronounceButton.setEnabled(true);
		flashcardButton.setEnabled(true);
		pauseButton.setEnabled(true);
		endFlashcardButton.setEnabled(true);
		
		generateSentenceButton.setEnabled(true);
		sentenceAnswerButton.setEnabled(true);
		pronounceSentenceButton.setEnabled(true);
		clearMultipleFieldsButton.setEnabled(true);
	}
	
	public void initDisableFields()
	{
		pronCheckbox.setEnabled(false);
		hkCheckbox.setEnabled(false);
		dictationCheckbox.setEnabled(false);
		randomCheckbox.setEnabled(false);
		saveProgressButton.setEnabled(false);
		
		showButton.setEnabled(false);
		alwaysShowCheckbox.setEnabled(false);
		nextButton.setEnabled(false);
		pronounceButton.setEnabled(false);
		flashcardButton.setEnabled(false);
		pauseButton.setEnabled(false);
		endFlashcardButton.setEnabled(false);
		
		generateSentenceButton.setEnabled(false);
		sentenceAnswerButton.setEnabled(false);
		pronounceSentenceButton.setEnabled(false);
		clearMultipleFieldsButton.setEnabled(false);
	}
	
	public void rangeFieldFocusEnableDisableFields()
	{
		setButton.setEnabled(true);
		pronCheckbox.setEnabled(false);
		hkCheckbox.setEnabled(false);
		dictationCheckbox.setEnabled(false);
		randomCheckbox.setEnabled(false);
		saveProgressButton.setEnabled(false);
		
		showButton.setEnabled(false);
		alwaysShowCheckbox.setEnabled(false);
		nextButton.setEnabled(false);
		pronounceButton.setEnabled(false);
		flashcardButton.setEnabled(false);
		pauseButton.setEnabled(false);
		endFlashcardButton.setEnabled(false);
		
		generateSentenceButton.setEnabled(false);
		sentenceAnswerButton.setEnabled(false);
		pronounceSentenceButton.setEnabled(false);
		clearMultipleFieldsButton.setEnabled(false);
	}
	
	public void setEnableFields()
	{
		pronCheckbox.setEnabled(true);
		hkCheckbox.setEnabled(true);
		dictationCheckbox.setEnabled(true);
		randomCheckbox.setEnabled(true);
		saveProgressButton.setEnabled(true);
		
		if (!alwaysShowCheckbox.isSelected()) showButton.setEnabled(true);
		alwaysShowCheckbox.setEnabled(true);
		nextButton.setEnabled(true);
		pronounceButton.setEnabled(true);
		flashcardButton.setEnabled(true);
		
		generateSentenceButton.setEnabled(true);
	}
	
	public void flashcardEnableDisableFields()
	{
		fromField.setEnabled(false);
		toField.setEnabled(false);
		setButton.setEnabled(false);
		dictationCheckbox.setEnabled(false);
		saveProgressButton.setEnabled(false);
		
		showButton.setEnabled(false);
		alwaysShowCheckbox.setEnabled(false);
		nextButton.setEnabled(false);
		pronounceButton.setEnabled(false);
		pauseButton.setEnabled(true);
		endFlashcardButton.setEnabled(true);
		
		generateSentenceButton.setEnabled(false);
		sentenceAnswerButton.setEnabled(false);
		pronounceSentenceButton.setEnabled(false);
		clearMultipleFieldsButton.setEnabled(false);
	}
	
	public void endFlashcardEnableDisableFields()
	{
		fromField.setEnabled(true);
		toField.setEnabled(true);
		dictationCheckbox.setEnabled(true);
		saveProgressButton.setEnabled(true);
		
		if (!alwaysShowCheckbox.isSelected()) showButton.setEnabled(true);
		alwaysShowCheckbox.setEnabled(true);
		nextButton.setEnabled(true);
		pronounceButton.setEnabled(true);
		pauseButton.setEnabled(false);
		endFlashcardButton.setEnabled(false);
		
		generateSentenceButton.setEnabled(true);
	}
	
	public void showSyllable(int index)
	{
		int ran1, ran2;
		ran1 = random.nextInt(2) + 1;
		if (ran1 == 1) ran2 = 2; else ran2 = 1;
		setTitle("Japanese Syllabary Trainer (Index: "+Integer.toString(index+1)+")");

		if (hkCheckbox.isSelected())
		{
			if (alwaysShowCheckbox.isSelected())
			{
				showBoxl.setText(res.syllabary[index][ran1]);
				showBoxm.setText(res.syllabary[index][ran2]);
				showBoxr.setText(res.syllabary[index][0]);
			}
			else
			{
				showBoxl.setText(res.syllabary[index][ran1]);
				showBoxm.setText(res.syllabary[index][ran2]);
				if (showHide) showBoxr.setText(res.syllabary[index][0]);
				else showBoxr.setText("");
			}
		}
		
		if (pronCheckbox.isSelected())
		{
			if (alwaysShowCheckbox.isSelected())
			{
				showBoxl.setText(res.syllabary[index][0]);
				showBoxm.setText(res.syllabary[index][ran1]);
				showBoxr.setText(res.syllabary[index][ran2]);
			}
			else
			{
				showBoxl.setText(res.syllabary[index][0]);
				if (showHide)
				{
					showBoxm.setText(res.syllabary[index][ran1]);
					showBoxr.setText(res.syllabary[index][ran2]);
				}
				else
				{
					showBoxm.setText("");
					showBoxr.setText("");
				}
			}
		}
		
		if (dictationCheckbox.isSelected())
		{
			if (showHide)
			{
				showBoxl.setText(res.syllabary[index][1]);
				showBoxm.setText(res.syllabary[index][2]);
				showBoxr.setText(res.syllabary[index][0]);
			}
			else
			{
				showBoxl.setText("");
				showBoxm.setText("");
				showBoxr.setText("");
			}	
		}
	}
	
	public void playPronounciation(String s)
	{
		  try
		  {
			  FileInputStream soundSource = new FileInputStream(s);
			  Player player = new Player(soundSource);
			  player.play();
			  player.close();
			  
		  }catch(Exception e) { System.out.println(e); }
	}
	
	public void showMsg(String s)
	{
		JOptionPane.showMessageDialog(null, s);
	}
}
