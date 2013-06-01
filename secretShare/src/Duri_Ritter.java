public class Duri_Ritter
{
	/**
	 * @杜里特尔法求解线性方程组
	 */
	static double a[][];
	static double b[];
	static double x[];
	static int n;

	public static void LU()
	{// 将系数矩阵进行LU分解
		for (int j = 2; j <= n; j++)
			a[j][1] = a[j][1] / a[1][1];
		for (int r = 2; r <= n; r++)
		{
			for (int i = r; i <= n; i++)
				a[r][i] = a[r][i] - jisuan(r, i);
			for (int i = r + 1; i <= n; i++)
				a[i][r] = (a[i][r] - jisuan1(r, i)) / a[r][r];
		}
		PrintA();
	}

	public static void Jie_xy()
	{
		x[1] = b[1];
		for (int i = 2; i <= n; i++)
			x[i] = b[i] - jisuan2(i);
		PrintX(0);
		x[n] = x[n] / a[n][n];
		for (int i = n - 1; i >= 1; i--)
			x[i] = (x[i] - jisuan3(i)) / a[i][i];
		PrintX(1);
	}

	public static void HL()
	{// 计算系数矩阵A的行列式
		double cj = 1.0;
		for (int i = 1; i <= n; i++)
			cj = cj * a[i][i];
		System.out.println("A的行列式：|A| = " + cj);
	}

	public static double jisuan(int r, int i)
	{
		double Sum = 0.0;
		for (int k = 1; k <= r - 1; k++)
			Sum = Sum + a[r][k] * a[k][i];
		return Sum;
	}

	public static double jisuan1(int r, int i)
	{
		double Sum = 0.0;
		for (int k = 1; k <= r - 1; k++)
			Sum = Sum + a[i][k] * a[k][r];
		return Sum;
	}

	public static double jisuan2(int i)
	{
		double Sum = 0.0;
		for (int k = 1; k <= i - 1; k++)
			Sum = Sum + a[i][k] * x[k];
		return Sum;
	}

	public static double jisuan3(int i)
	{
		double Sum = 0.0;
		for (int k = i + 1; k <= n; k++)
			Sum = Sum + a[i][k] * x[k];
		return Sum;
	}

	public static void PrintA()
	{// 输出矩阵A
		System.out.println("分解后的矩阵A:");
		for (int i = 1; i <= n; i++)
		{
			for (int j = 1; j <= n; j++)
				System.out.print(a[i][j] + "    ");
			System.out.print("\n");
		}
	}

	public static void PrintX(int x1)
	{// 输出数组X
		if (x1 == 0)
			System.out.println("方程组Ly=b的解为：");
		else
			System.out.println("方程组Ux=y的解为：");
		for (int i = 1; i <= n; i++)
		{
			if (x1 == 0)
				System.out.print("y" + i + " = " + x[i] + "    ");
			else
				System.out.print("x" + i + " = " + x[i] + "    ");
		}
		System.out.print("\n");
	}

	public static void main(String[] args)
	{
		n = 3;
		x = new double[n + 1];
		a = new double[][]
		{
		{ 0, 0, 0, 0 },
		{ 0, 2, 2, -1 },
		{ 0, 1, -2, 4 },
		{ 0, 5, 7, 1 } };
		b = new double[]
		{ 0, 6, 3, 28 };

		LU();
		Jie_xy();
		HL();
	}
}