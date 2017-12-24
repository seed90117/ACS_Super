package ACS_Super;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

import UserInterface.MainView;
import Variable.Ant;
import Variable.Variable;

public class ACS_Super
{	
	static String Super_Generation = "";
	static long STime, ETime;	
	
	//***********開啟檔案副程式**************//
		public static void Open_File()
		{
			Variable.filePath = MainView.FileChooser_Data.getSelectedFile();	//將檔案路徑存入File
		}
		
	//***********畫檔案副程式**************//
		public static void Picture()
		{
			//***********讀取檔案**************//
				Variable.isLoadData = true;
				Variable.isRun = false;
				
				FileReader fr = null;											//宣告讀取檔案
					
				try
				{
					fr = new FileReader(Variable.filePath.getPath().toString());		
				}
				catch(FileNotFoundException ex)
				{
				}
					
				BufferedReader br = new BufferedReader(fr);						//宣告讀取檔案內容
					
				String Read_str = null;
					
				try
				{
					String Cut_str = "";
					String[] Cut_str_Array = {"	", " ", ","};
					String[] str;
					Read_str = br.readLine();									//讀取內容
					for(String s : Cut_str_Array)
					{
						str = Read_str.split(s);								//切割內容
						if(str.length > 1)
						{
							Cut_str = s;
							break;
						}
					}
					
					str = Read_str.split(Cut_str);
					Variable.ACS_Data_Size = Integer.valueOf(str[0]);
					
					Variable.ACS_Data = new double[Variable.ACS_Data_Size][Integer.valueOf(str[1])];
						
					for(int i = 0; (Read_str = br.readLine()) != null; i++)
					{
						str = Read_str.split(Cut_str);
						Variable.ACS_Data[i][0] = Double.valueOf(str[0]);
						Variable.ACS_Data[i][1] = Double.valueOf(str[1]);
					}
				}
				catch(IOException ex)
				{
				}
				
				//***********放大倍率計算**************//
					Variable.Magnification = rate();
					
				//***********輸出文字清除**************//
					MainView.Text_Result.setText("");
			}

		//***********執行ACS程式**************//
			public static void Starts()
			{				
				//***********外部變數**************//
					Variable.Ants = Integer.valueOf(MainView.Text_Ants.getText());		
					Variable.Generation = Integer.valueOf(MainView.Text_Generation.getText());
					Variable.α = Integer.valueOf(MainView.Text_a.getText());
					Variable.β = Integer.valueOf(MainView.Text_b.getText());
					Variable.First_Pheromone = Double.valueOf(MainView.Text_First_Pheromone.getText());
					Variable.Drop_Pheromone = Double.valueOf(MainView.Text_Drop_Pheromone.getText());
					Variable.Path_Choose = Double.valueOf(MainView.Text_Path_Choose.getText());
					Variable.ant = new Ant[Variable.Ants];
					Variable.Now_Path_Pheromone = new double[Variable.ACS_Data_Size][Variable.ACS_Data_Size];
					Variable.Super_Generation_Path = new int[Variable.ACS_Data_Size + 1];			//最優秀路線
					Variable.isRun = true;
					int End_Length = Integer.valueOf(MainView.Text_ShortestPath.getText());
					
				//***********執行ACS**************//
						Super_Generation = "";
						Variable.Super_Generation_Length_Int = Integer.MAX_VALUE;	//優秀距離(int)
						Variable.Super_Generation_Length = Double.MAX_VALUE;
						STime = System.currentTimeMillis(); 
						
					//***********匯入初始Pheromone**************//	
						for(int i = 0; i < Variable.Now_Path_Pheromone.length; i++)
						{
							for(int j = 0; j < Variable.Now_Path_Pheromone.length; j++)
							{
								Variable.Now_Path_Pheromone[i][j] = Variable.First_Pheromone;
							}
						}
						
						if(Variable.isGeneration)
						{
							for(int i = 0; i < Variable.Generation; i++)
							{
								ACS_Ant(i);
							}
						}
						else
						{
							for(int i = 0; Variable.Super_Generation_Length_Int > End_Length; i++)
							{
								ACS_Ant(i);
							}
						}
						
						ETime = System.currentTimeMillis();
					
						MainView.Text_Result.append(Super_Generation);
						MainView.Text_Result.append("消耗時間：" + (ETime - STime) + "mS");
						
						for(int i = 0; i <= Variable.ACS_Data_Size; i++)
						{
							if(i != Variable.ACS_Data_Size)
								MainView.Text_Result.append((Variable.Super_Generation_Path[i] + 1) + "→");
							else
								MainView.Text_Result.append((Variable.Super_Generation_Path[i] + 1) + "");
						}
			}
			
			//***********螞蟻副程式**************//
				private static void ACS_Ant(int i)
				{
					int Super_Number = 0;									//第x代優秀螞蟻編號
					double Super_Value = Double.MAX_VALUE;					//第x代優秀距離
					
					//***********隨機將螞蟻放入程式點**************//
						for(int j = 0; j < Variable.Ants; j++)
						{
							int pathPoint = 0;
							
							Variable.ant[j] = new Ant();
							Variable.ant[j].Path = new int[Variable.ACS_Data_Size + 1];
							Variable.ant[j].isCity = new double[Variable.ACS_Data_Size];

							Variable.ant[j].Path[0] = pathPoint;
							Variable.ant[j].isCity[pathPoint] = 1;
							Variable.ant[j].First_City = pathPoint;
						}
					
					
					//***********尋找路線**************//
						for(int j = 0; j < Variable.ACS_Data_Size; j++)
						{
							Variable.now_Path = j;
							
							//***********螞蟻從A點移到B點**************//
								if(j < Variable.ACS_Data_Size - 1)
								{
									for(int k = 0; k < Variable.Ants; k++)
									{
										int pathPoint = Conversion_rules(k);								//執行轉換規則
										
										Variable.ant[k].Path[Variable.now_Path + 1] = pathPoint;
										Variable.ant[k].isCity[pathPoint] = 1;
										
										
										Variable.ant[k].Total_Length += Distance(Variable.ant[k].Path[Variable.now_Path], pathPoint);
										Variable.ant[k].Total_Length_Int += Math.round(Distance(Variable.ant[k].Path[Variable.now_Path], pathPoint));
										
										//***********局部Pheromone更新**************//
											int Citynum = Variable.ant[k].Path[Variable.now_Path];
											Variable.Now_Path_Pheromone[Citynum][pathPoint] = 
													(1 - Variable.Drop_Pheromone) * Variable.Now_Path_Pheromone[Citynum][pathPoint]	+ Variable.Drop_Pheromone * Variable.First_Pheromone;
													
											Variable.Now_Path_Pheromone[pathPoint][Citynum] = Variable.Now_Path_Pheromone[Citynum][pathPoint];
									}
								}
								else
								{
									//***********讓所有螞蟻回到原點**************//
										for(int k = 0; k < Variable.Ants; k++)
										{								
											Variable.ant[k].Path[Variable.ACS_Data_Size] = Variable.ant[k].First_City;
											Variable.ant[k].Total_Length += Distance(Variable.ant[k].Path[Variable.now_Path], Variable.ant[k].Path[Variable.now_Path + 1]);
											Variable.ant[k].Total_Length_Int += Math.round(Distance(Variable.ant[k].Path[Variable.now_Path], Variable.ant[k].Path[Variable.now_Path + 1]));
										}
								}
						}
						
						for(int j = 0; j < Variable.Ants; j++)
						{								
							if(Super_Value > Variable.ant[j].Total_Length_Int)
							{
								Super_Value = Variable.ant[j].Total_Length_Int;
								Super_Number = j;
							}
						}
						
						//***********執行2-opt**************//
						//	two_opt(Super_Number);
						
					//***********全域Pheromone更新**************//							
						double difference = Variable.Drop_Pheromone * (Variable.Q / Variable.ant[Super_Number].Total_Length);
						
						for(int l = 0; l < Variable.ACS_Data_Size - 1; l++)
						{
							int CitynumA = Variable.ant[Super_Number].Path[l];
							int CitynumB = Variable.ant[Super_Number].Path[l + 1];
							
							Variable.Now_Path_Pheromone[CitynumA][CitynumB] = (1 - Variable.Drop_Pheromone) * Variable.Now_Path_Pheromone[CitynumA][CitynumB] + difference;
							
							Variable.Now_Path_Pheromone[CitynumB][CitynumA] = Variable.Now_Path_Pheromone[CitynumA][CitynumB];
						}
						
					if(Variable.Super_Generation_Length_Int > Variable.ant[Super_Number].Total_Length_Int)
					{
						Variable.Super_Generation_Path = Variable.ant[Super_Number].Path;
						
						Variable.Super_Generation_Length = Variable.ant[Super_Number].Total_Length;
						Variable.Super_Generation_Length_Int = Variable.ant[Super_Number].Total_Length_Int;
						
						Super_Generation = "第" + i + "代找到最佳解\n總長度(double)：" + Variable.Super_Generation_Length 
								         + "\n總長度(int)：" + Variable.Super_Generation_Length_Int + "\n";
						
						//System.out.println(i + ", " + Variable.Super_Generation_Length_Int);
					}
				}

			//***********轉換規則副程式**************//
				private static int Conversion_rules(int Ant_Number)
				{
					double q = Variable.ran.nextDouble();
					double distance;
					
					if(q <= Variable.Path_Choose)								//判斷要選 轉換機率 還是 最大吸引力
					{
						//***********最大吸引力**************//
							int path = 0;
							int Citynum = Variable.ant[Ant_Number].Path[Variable.now_Path];
							double Max_Attractive = 0;
							
							
							for(int i = 0; i < Variable.ACS_Data_Size; i++)
							{
								if(Variable.ant[Ant_Number].isCity[i] == 0)
								{
									distance = Distance(Citynum, i);
									
									double Attractive = Variable.Now_Path_Pheromone[Citynum][i] * Math.pow(1 / distance, Variable.β);
									
									if(Max_Attractive < Attractive)
									{
										Max_Attractive = Attractive;
										path = i;
									}
								}
							}
							
							return path;
					}
					else
					{
						//***********轉換機率**************//
							int path = 0;
							int Citynum = Variable.ant[Ant_Number].Path[Variable.now_Path];
							double Denominator = 0;
							double[] table = new double[Variable.ACS_Data_Size];
							
							for(int i = 0; i < Variable.ACS_Data_Size; i++)
							{
								if(Variable.ant[Ant_Number].isCity[i] == 0)
								{
									distance = Distance(Citynum, i);
									
									table[i] += Denominator + Variable.Now_Path_Pheromone[Citynum][i] * Math.pow(1 / distance, Variable.β);
									Denominator = table[i];
								}
							}
							
							q = Variable.ran.nextDouble();
							
							for(int i = 0; i < Variable.ACS_Data_Size; i++)
							{
								if(Variable.ant[Ant_Number].isCity[i] == 0)
								{
									if(q < (table[i] / Denominator))
									{
										path = i;
										break;
									}
								}
							}
	
							return path;
					}
				}
			
		//***********2opt副程式**************//
			private static void two_opt(int Ants_Number)
			{
				double DistanceAB, DistanceCD, DistanceAC, DistanceBD;					  
				int stop=0;
				int count = 0;
				while((stop == 0))
				{
					stop ++;
					for(int i = 0; i < Variable.ant[Ants_Number].Path.length - 2; i++)
					{
						for(int j = i + 2; j < Variable.ant[Ants_Number].Path.length - 1; j++)
						{
							DistanceAB = Point.distance(Variable.ACS_Data[Variable.ant[Ants_Number].Path[i]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[i]][1]
									, Variable.ACS_Data[Variable.ant[Ants_Number].Path[i + 1]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[i + 1]][1]);
							
							DistanceCD = Point.distance(Variable.ACS_Data[Variable.ant[Ants_Number].Path[j]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[j]][1]
									, Variable.ACS_Data[Variable.ant[Ants_Number].Path[j + 1]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[j + 1]][1]);
							
							DistanceAC = Point.distance(Variable.ACS_Data[Variable.ant[Ants_Number].Path[i]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[i]][1]
									, Variable.ACS_Data[Variable.ant[Ants_Number].Path[j]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[j]][1]);
							
							DistanceBD = Point.distance(Variable.ACS_Data[Variable.ant[Ants_Number].Path[i + 1]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[i + 1]][1]
									, Variable.ACS_Data[Variable.ant[Ants_Number].Path[j + 1]][0], Variable.ACS_Data[Variable.ant[Ants_Number].Path[j + 1]][1]);
				    
							if((DistanceAB+DistanceCD)>(DistanceAC+DistanceBD))
							{
								if(stop != 0)
									stop = 0;
				      
								int opt1 = Variable.ant[Ants_Number].Path[i + 1];
								int opt2 = Variable.ant[Ants_Number].Path[j];
								
								Variable.ant[Ants_Number].Path[i + 1] = opt2;
								Variable.ant[Ants_Number].Path[j] = opt1;
							}
						}
					}
					count++;
				}
				
				System.out.println(count);
				
				Variable.ant[Ants_Number].Total_Length = 0;
				
				for(int i = 0; i < Variable.ACS_Data_Size; i++)
				{
					Variable.ant[Ants_Number].Total_Length += Point.distance(Variable.ACS_Data[(int) Variable.ant[Ants_Number].Path[i]][0], Variable.ACS_Data[(int) Variable.ant[Ants_Number].Path[i]][1]
						, Variable.ACS_Data[(int) Variable.ant[Ants_Number].Path[i + 1]][0], Variable.ACS_Data[(int) Variable.ant[Ants_Number].Path[i + 1]][1]);
				}
			}
			
		//***********畫圖副程式**************//
			public static JPanel drow(double[][] ACS_Data)
			{				
				JPanel myJpanel = new JPanel()
				{
					public void paint (Graphics myg)
					{
						if(Variable.ACS_Data_Size == ACS_Data.length)
						{
							Graphics2D myg2d = (Graphics2D)myg;
							
							myg2d.setColor(Color.white);
							myg2d.fillRect(1, 1, 648, 648);					//畫出填滿長方形
							
							myg2d.setColor(Color.black); 	
							
							String str="";
							
							for(int i = 0; i < Variable.ACS_Data_Size; i++)
							{
								myg2d.fillOval((int)(ACS_Data[i][0] * Variable.Magnification) + 20, (int)(ACS_Data[i][1] * Variable.Magnification) + 20, 4, 4);
								
								if(MainView.CheckBox_isShowNumber.isSelected())
									str = String.valueOf(i + 1);
								
								myg2d.drawString(str, (int) (ACS_Data[i][0] * Variable.Magnification) + 20, (int) (ACS_Data[i][1] * Variable.Magnification) + 20);
							}
							
							if(Variable.isRun)
							{
								for(int i = 0; i < Variable.ACS_Data_Size; i++)
								{
									myg2d.setColor(Color.red);
									myg2d.drawLine((int) (ACS_Data[Variable.Super_Generation_Path[i]][0] * Variable.Magnification) + 20, (int) (Variable.ACS_Data[Variable.Super_Generation_Path[i]][1] * Variable.Magnification) + 20
										, (int) (ACS_Data[Variable.Super_Generation_Path[i + 1]][0] * Variable.Magnification) + 20, (int) (ACS_Data[Variable.Super_Generation_Path[i + 1]][1] * Variable.Magnification) + 20);
								}
							}
						}
					}
				};
				
				myJpanel.setBounds(1, 1, 648, 648);
				
				return myJpanel;
			}
			
		//***********畫布清除副程式**************//
			private static void ClearCanvas(int starts_x, int starts_y, int width, int height)
			{
				MainView.Graphics_Canvas = MainView.Panel_Canvas.getGraphics();
				MainView.Graphics_Canvas.setColor(Color.WHITE);
				MainView.Graphics_Canvas.fillRect(starts_x, starts_y, width, height);
				
				MainView.Graphics2D_Canvas = (Graphics2D)MainView.Graphics_Canvas;
			}
			
		//***********畫點副程式**************//
			private static void Painting_Point(int x, int y, Color color, String str)
			{
					MainView.Graphics2D_Canvas.setColor(color);
					
				//***********畫點**************//
					MainView.Graphics2D_Canvas.fillRect(x, y, 2, 2);
					
				//***********畫點編號**************//
					if(MainView.CheckBox_isShowNumber.isSelected())
					{
						MainView.Graphics2D_Canvas.drawString(str, x, y);
					} 
			}
			
		//***********畫線副程式**************//
			private static void Painting_Line(int x_A, int x_B, int y_A, int y_B)
			{
				//***********畫線**************//
					MainView.Graphics2D_Canvas.setColor(Color.red);
					MainView.Graphics2D_Canvas.setStroke(new BasicStroke(1.0f));
					MainView.Graphics2D_Canvas.drawLine(x_A, y_A, x_B, y_B); 
			}
			
		//***********倍率副程式**************//
			private static double rate()
			{
				double rate_x, rate_y;
				
				rate_x = Variable.ACS_Data[0][0];
				rate_y = Variable.ACS_Data[0][1];
				
				for(int i = 0; i < Variable.ACS_Data.length; i++)
				{
					if(rate_x < Variable.ACS_Data[i][0])
					{
						rate_x = Variable.ACS_Data[i][0];
					}
					
					if(rate_y < Variable.ACS_Data[i][1])
					{
						rate_y = Variable.ACS_Data[i][1];
					}
				}
				
				if(rate_y > rate_x)
					rate_x = rate_y;
				
				return ((MainView.Panel_Canvas_First.getWidth() - 50)/rate_x);
			}
		
		//***********距離副程式**************//
			private static double Distance(int A, int B)
			{
				return Point.distance(Variable.ACS_Data[A][0], Variable.ACS_Data[A][1], Variable.ACS_Data[B][0], Variable.ACS_Data[B][1]);
			}
}
