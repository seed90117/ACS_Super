package Variable;

import java.io.File;
import java.util.Random;

public class Variable
{
	//***********參數值**************//
		public static Random ran = new Random();	//亂數產生
		public static File filePath;				//檔案路徑
		public static double Magnification;			//放大倍率
		public static int Ants;						//螞蟻數
		public static int Generation;				//代數
		public static int α;						//轉換規則中，Pheromone的權重
		public static int β;						//轉換規則中，距離的權重
		public static int ACS_Data_Size;			//資料集大小
		public static final int Q = 1;				//費洛蒙強度			
		public static double First_Pheromone;		//初始Pheromone
		public static double Drop_Pheromone;		//Pheromone衰退率
		public static double Path_Choose;			//路徑權重
		public static int now_Path;					//現在路徑
		public static boolean isGeneration = true;	//終止條件判斷
		public static boolean isRun = false;		//是否跑過演算法
		public static boolean isLoadData = false;	//是否載入資料
		public static int Super_Generation_Length_Int = Integer.MAX_VALUE;	//優秀距離(int)
		public static double Super_Generation_Length = Double.MAX_VALUE;	//優秀距離(double)

	//***********陣列**************//
		public static Ant[] ant;
		public static double[][] ACS_Data;
		public static double[][] Now_Path_Pheromone;
		public static int[] Super_Generation_Path;	//優秀路徑
}
