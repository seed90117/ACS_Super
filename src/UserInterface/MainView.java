package UserInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import ACS_Super.*;
import Variable.*;

public class MainView extends JFrame
{
	//***********�D�e��**************//
		Container myWindows = this.getContentPane();

	//***********��r�r��**************//
		Font myFont = new Font(Font.DIALOG, Font.BOLD, 20);
		
	//***********�U�Ԧ����**************//
		static String[] String_Item = {"Generation", "Shortest Path"};
	
	//***********���Ҫ���**************//
		public JLabel Label_Result = new JLabel("Data Show");
		public JLabel Label_Item = new JLabel("Termination�G");
		public JLabel Label_Ants = new JLabel("Ant�G");
		public JLabel Label_Generation = new JLabel("Generation�G");
		public JLabel Label_ShortestPath = new JLabel("Shortest Path�G");
		public JLabel Label_First_Pheromone = new JLabel("First Pheromone�G");
		public JLabel Label_Drop_Pheromone = new JLabel("Drop Pheromone�G");
		public JLabel Label_Path_Choose = new JLabel("Path Choose�G");
		public JLabel Label_a = new JLabel("a");
		public JLabel Label_b = new JLabel("b");
		
	//***********��J����**************//
		public static JComboBox<String> ComboBox_Item = new JComboBox<String>(String_Item);
		public static JTextField Text_Ants = new JTextField("10");
		public static JTextField Text_Generation = new JTextField("100");
		public static JTextField Text_ShortestPath = new JTextField("300");
		public static JTextField Text_First_Pheromone = new JTextField("0.00001");
		public static JTextField Text_Drop_Pheromone = new JTextField("0.1");
		public static JTextField Text_Path_Choose = new JTextField("0.9");
		public static JTextField Text_a = new JTextField("1");
		public static JTextField Text_b = new JTextField("2");
		public static JFileChooser FileChooser_Data = new JFileChooser("D://Data//");
		public static JCheckBox CheckBox_isShowNumber = new JCheckBox("isShow Number");
		
	//***********��X����**************//
		public static JPanel Panel_Canvas_First = new JPanel();
		public static JPanel Panel_Canvas;
		public static Graphics Graphics_Canvas;
		public static Graphics2D Graphics2D_Canvas;
		public static JTextArea Text_Result = new JTextArea();
		public JScrollPane ScrollPane_Result = new JScrollPane(Text_Result,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
				,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	//***********���s����**************//
		public static JButton Button_Load_File = new JButton("Load File");
		public static JButton Button_Picture = new JButton("Picture File");
		public static JButton Button_Starts = new JButton("Starts");
		public static JButton Button_Updata = new JButton("Updata");
		
	//***********Logo����**************//
		ImageIcon Image_Icon = new ImageIcon("src/logo.gif");
		JLabel myIcon = new JLabel(Image_Icon);
	
	//***********����]�w**************//
		public MainView()
		{
			//***********���Ҫ���]�w**************//
				Label_Result.setBounds(10, 10, 110, 20);
				Label_Result.setFont(myFont);
				myWindows.add(Label_Result);
			
				Label_Item.setBounds(700, 55, 135, 20);
				Label_Item.setFont(myFont);
				myWindows.add(Label_Item);
				
				Label_Ants.setBounds(700, 103, 55, 20);
				Label_Ants.setFont(myFont);
				myWindows.add(Label_Ants);
				
				Label_Generation.setBounds(700, 153, 130, 20);
				Label_Generation.setFont(myFont);
				myWindows.add(Label_Generation);

				Label_ShortestPath.setBounds(700, 153, 155, 20);
				Label_ShortestPath.setFont(myFont);
				Label_ShortestPath.setVisible(false);
				myWindows.add(Label_ShortestPath);
				
				Label_First_Pheromone.setBounds(700, 203, 180, 20);
				Label_First_Pheromone.setFont(myFont);
				myWindows.add(Label_First_Pheromone);
				
				Label_Drop_Pheromone.setBounds(700, 253, 185, 20);
				Label_Drop_Pheromone.setFont(myFont);
				myWindows.add(Label_Drop_Pheromone);
				
				Label_Path_Choose.setBounds(700, 303, 145, 20);
				Label_Path_Choose.setFont(myFont);
				myWindows.add(Label_Path_Choose);
				
				Label_a.setBounds(700, 353, 35, 20);
				Label_a.setFont(myFont);
				myWindows.add(Label_a);
				
				Label_b.setBounds(700, 403, 35, 20);
				Label_b.setFont(myFont);
				myWindows.add(Label_b);
			
			//***********��J����]�w**************//
				ComboBox_Item.setBounds(845, 50, 155, 30);
				ComboBox_Item.setFont(myFont);
				ComboBox_Item.addItemListener(new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0)
					{
						if(ComboBox_Item.getSelectedIndex() == 0)
						{
							Label_Generation.setVisible(true);
							Label_ShortestPath.setVisible(false);
							
							Text_Generation.setVisible(true);
							Text_ShortestPath.setVisible(false);
							
							Variable.isGeneration = true;
						}
						else
						{
							Label_Generation.setVisible(false);
							Label_ShortestPath.setVisible(true);
							
							Text_Generation.setVisible(false);
							Text_ShortestPath.setVisible(true);
							
							Variable.isGeneration = false;
						}
					}
				});
				myWindows.add(ComboBox_Item);
				
				Text_Ants.setBounds(885, 100, 115, 24);
				Text_Ants.setFont(myFont);
				Text_Ants.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_Ants);
				
				Text_Generation.setBounds(885, 150, 115, 24);
				Text_Generation.setFont(myFont);
				Text_Generation.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_Generation);

				Text_ShortestPath.setBounds(885, 150, 115, 24);
				Text_ShortestPath.setFont(myFont);
				Text_ShortestPath.setHorizontalAlignment(JTextField.RIGHT);
				Text_ShortestPath.setVisible(false);
				myWindows.add(Text_ShortestPath);
				
				Text_First_Pheromone.setBounds(885, 200, 115, 24);
				Text_First_Pheromone.setFont(myFont);
				Text_First_Pheromone.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_First_Pheromone);
				
				Text_Drop_Pheromone.setBounds(885, 250, 115, 24);
				Text_Drop_Pheromone.setFont(myFont);
				Text_Drop_Pheromone.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_Drop_Pheromone);
				
				Text_Path_Choose.setBounds(885, 300, 115, 24);
				Text_Path_Choose.setFont(myFont);
				Text_Path_Choose.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_Path_Choose);
				
				Text_a.setBounds(885, 350, 115, 24);
				Text_a.setFont(myFont);
				Text_a.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_a);
				
				Text_b.setBounds(885, 400, 115, 24);
				Text_b.setFont(myFont);
				Text_b.setHorizontalAlignment(JTextField.RIGHT);
				myWindows.add(Text_b);
				
				CheckBox_isShowNumber.setBounds(700, 450, 175, 24);
				CheckBox_isShowNumber.setFont(myFont);
				CheckBox_isShowNumber.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(Variable.isLoadData)
						{
							Panel_Canvas = ACS_Super.drow(Variable.ACS_Data);
							repaint();
						}
					}
				});
				myWindows.add(CheckBox_isShowNumber);
				
			//***********��X����]�w**************//
				Panel_Canvas_First.setBounds(10, 40, 650, 650);
				Panel_Canvas_First.setBackground(Color.WHITE);
				Panel_Canvas_First.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				myWindows.add(Panel_Canvas_First);
				
				Text_Result.setFont(myFont);
				Text_Result.setForeground(Color.black);
				Text_Result.setEditable(false);
				Text_Result.setLineWrap(true);
				
				ScrollPane_Result.setBounds(10, 700, 650, 140);
				myWindows.add(ScrollPane_Result);

			//***********���s����]�w**************//
				Button_Load_File.setBounds(700, 500, 300, 30);
				Button_Load_File.setFont(myFont);
				Button_Load_File.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						// TODO Auto-generated method stub
						FileChooser_Data.setDialogTitle("Load Old File"); 									//����ɮ׿�ܾ������Y
						if(FileChooser_Data.showDialog(null, "Open File") == JFileChooser.APPROVE_OPTION)	//showDialog(�ɮ׿�ܾ��X�{����m(�H��L���󬰷�), ���s�W��)
						{
							ACS_Super.Open_File();
						}
					}
				});
				myWindows.add(Button_Load_File);
				
				Button_Picture.setBounds(700, 550, 300, 30);
				Button_Picture.setFont(myFont);
				Button_Picture.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						//Panel_Canvas_First.setVisible(false);
						ACS_Super.Picture();
						
						//***********�e��**************//
							Panel_Canvas = ACS_Super.drow(Variable.ACS_Data);
							Panel_Canvas_First.add(Panel_Canvas);
							Panel_Canvas_First.repaint();
						repaint();
					}
				});
				myWindows.add(Button_Picture);
				
				Button_Starts.setBounds(700, 600, 300, 30);
				Button_Starts.setFont(myFont);
				Button_Starts.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						ACS_Super.Starts();
						
						//***********�e��**************//
							Panel_Canvas = ACS_Super.drow(Variable.ACS_Data);
						repaint();
					}
				});
				myWindows.add(Button_Starts);
				
				Button_Updata.setBounds(700, 650, 300, 30);
				Button_Updata.setFont(myFont);
				Button_Updata.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						//***********�e��**************//
							Panel_Canvas = ACS_Super.drow(Variable.ACS_Data);
						repaint();
					}
				});
				myWindows.add(Button_Updata);
				
			//***********Logo����]�w**************//
				myIcon.setBounds(725, 700, 250, 80);
				myWindows.add(myIcon);
			
			//***********���ҳ]�w**************//
				this.setSize(1050, 900);
				this.setLayout(null);
				this.setTitle("ACS_Super");
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				this.setVisible(true);
		}

	//***********�D�{��**************//
		public static void main(String[] args)
		{
			new MainView();
		}
}
