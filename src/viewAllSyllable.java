import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javazoom.jl.player.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.util.HashMap;

//Display the whole syllabary table
public class viewAllSyllable extends JFrame {
	
	//Resource Declaration
	private JPanel contentPane, displayPane; //displayPane is inside the scrollPane, so the entire pane can scroll as a whole. contentPane is for the main window.
	private JScrollPane scrollPane;
	private JLabel[][] Fifty, TwentyOne, TwentyFive, Twelve; //Four groups of syllables on the screen.
	HashMap<Integer, String> map = new HashMap<Integer, String>(); //Mapping syllable JLabels to their corresponding sound file path.
	resources res = new resources(); //Resource object.
	playSoundAction playSound = new playSoundAction(); //playSound Action Listener.
	//End of Resource Declaration
	
	//Constructor, setLayout.
	public viewAllSyllable() {
		setTitle("Syllabary Chart (Click On Each Syllable To Hear Pronunciation)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 600);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		displayPane = new JPanel();	
		Fifty = new JLabel[11][5];
		TwentyOne = new JLabel[7][3];
		TwentyFive = new JLabel[5][5];
		Twelve = new JLabel[4][3];
		
		displayPane.setLayout(null);
		displayPane.setPreferredSize(new Dimension(620, 1150));
		setFifty(); // From 50 50 to 340 700
		setTwentyOne(); // From 360 110 to 610 xxx
		setTwentyFive();
		setTwelve();
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(displayPane);
        scrollPane.setBounds(0, 0, 690, 555);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(20, Integer.MAX_VALUE));
        contentPane.add(scrollPane);
	}
	
	//Syllabary Table Layout
	public void setFifty() //The first group of 50 syllables.
	{
		int index = 0;
		int xPos, yPos, width;
		String s = "";
		xPos = 50; yPos = 50; width = 50;
		for (int i=0; i<11; i++)
		{
			for (int j=0; j<5; j++)
			{
				if (i==10)
				{
					s = "<html><center>" + res.syllabary[50][1] + "  " + res.syllabary[50][2] + "<br/>" + res.syllabary[50][0] + "</center></html>"; 
					Fifty[i][0] = new JLabel(s);
					Fifty[i][0].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					Fifty[i][0].setBounds(xPos, yPos+i*60, width, width);
					Fifty[i][0].setBackground(Color.WHITE);
					Fifty[i][0].setOpaque(true);
					Fifty[i][0].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(Fifty[i][0]);
					map.put(Fifty[i][0].hashCode(), res.soundFolder + res.syllabary[50][0] + res.soundExtension);
					Fifty[i][0].addMouseListener(playSound);
					break;
				}
				else
				{
					s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
					Fifty[i][j] = new JLabel(s);
					Fifty[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					Fifty[i][j].setBounds(xPos+j*60, yPos+i*60, width, width);
					Fifty[i][j].setBackground(Color.WHITE);
					Fifty[i][j].setOpaque(true);
					Fifty[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(Fifty[i][j]);
					map.put(Fifty[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
					Fifty[i][j].addMouseListener(playSound);
					index++;
				}
			}
			
		}
	}

	public void setTwentyOne() //The second group of 21 syllables on the right of the first 50.
	{
		int index = 51;
		int xPos, yPos, width, height;
		String s = "";
		xPos = 360; yPos = 110; width = 80; height = 50;
		for (int i=0; i<7; i++)
		{
			for (int j=0; j<3; j++)
			{
				if (i==6)
				{
					s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
					TwentyOne[i][j] = new JLabel(s);
					TwentyOne[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					TwentyOne[i][j].setBounds(xPos+j*90, yPos+i*60+60, width, height);
					TwentyOne[i][j].setBackground(Color.WHITE);
					TwentyOne[i][j].setOpaque(true);
					TwentyOne[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(TwentyOne[i][j]);
					map.put(TwentyOne[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
					TwentyOne[i][j].addMouseListener(playSound);
					index++;
				}
				else
				{
					s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
					TwentyOne[i][j] = new JLabel(s);
					TwentyOne[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					TwentyOne[i][j].setBounds(xPos+j*90, yPos+i*60, width, height);
					TwentyOne[i][j].setBackground(Color.WHITE);
					TwentyOne[i][j].setOpaque(true);
					TwentyOne[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(TwentyOne[i][j]);
					map.put(TwentyOne[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
					TwentyOne[i][j].addMouseListener(playSound);
					index++;
				}
			}
			
		}
	}

	public void setTwentyFive() //The third group of 25 syllables below the first 50.
	{
		int index = 72;
		int xPos, yPos, width, height;
		String s = "";
		xPos = 50; yPos = 760; width = 50; height = 50;
		for (int i=0; i<5; i++)
		{
			for (int j=0; j<5; j++)
			{
				s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
				TwentyFive[i][j] = new JLabel(s);
				TwentyFive[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
				TwentyFive[i][j].setBounds(xPos+j*60, yPos+i*60, width, height);
				TwentyFive[i][j].setBackground(Color.WHITE);
				TwentyFive[i][j].setOpaque(true);
				TwentyFive[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				displayPane.add(TwentyFive[i][j]);
				map.put(TwentyFive[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
				TwentyFive[i][j].addMouseListener(playSound);
				index++;
			}
			
		}
	}
	
	public void setTwelve() //The forth group of 12 syllables on the right of group 3. 
	{
		int index = 97;
		int xPos, yPos, width, height;
		String s = "";
		xPos = 360; yPos = 760; width = 80; height = 50;
		for (int i=0; i<4; i++)
		{
			for (int j=0; j<3; j++)
			{
				if (i==2 || i==3)
				{
					s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
					Twelve[i][j] = new JLabel(s);
					Twelve[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					Twelve[i][j].setBounds(xPos+j*90, yPos+i*60+60, width, height);
					Twelve[i][j].setBackground(Color.WHITE);
					Twelve[i][j].setOpaque(true);
					Twelve[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(Twelve[i][j]);
					map.put(Twelve[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
					Twelve[i][j].addMouseListener(playSound);
					index++;
				}
				else
				{
					s = "<html><center>" + res.syllabary[index][1] + "  " + res.syllabary[index][2] + "<br/>" + res.syllabary[index][0] + "</center></html>";
					Twelve[i][j] = new JLabel(s);
					Twelve[i][j].setFont(new Font("Microsoft Yahei", Font.PLAIN, 18));
					Twelve[i][j].setBounds(xPos+j*90, yPos+i*60, width, height);
					Twelve[i][j].setBackground(Color.WHITE);
					Twelve[i][j].setOpaque(true);
					Twelve[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					displayPane.add(Twelve[i][j]);
					map.put(Twelve[i][j].hashCode(), res.soundFolder + res.syllabary[index][0] + res.soundExtension);
					Twelve[i][j].addMouseListener(playSound);
					index++;
				}
			}
			
		}
	}
	//End of Syllabary Table Layout
	
	//Play Pronounciation ActionListener (Mouse Listener)
	class playSoundAction implements MouseListener {
		public void mousePressed(MouseEvent e) {
			try
			  {
				  FileInputStream soundSource = new FileInputStream(map.get(e.getSource().hashCode()));
				  Player player = new Player(soundSource);
				  player.play();
				  player.close();
			  }catch(Exception err) { System.out.println(err); }
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
