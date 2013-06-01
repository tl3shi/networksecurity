import java.math.BigInteger;

public class EquationTool
{
	static boolean gauss_cpivot(int n, Fraction a[][], Fraction b[])
	{// 高斯消元法解方程组，有唯一解返回true,结果放在b[]中，否则返回false
		int i, j, k, row = 0;
		for (k = 0; k < n; ++k)
		{
			Fraction maxp = new Fraction();
			for (i = k; i < n; ++i)
				if (a[i][k].abs().Biger(maxp.abs()))
				{
					row = i;
					maxp = new Fraction(a[i][k]);
				}
			if (maxp.abs().zero() == 0)
				return false;
			if (row != k)
			{
				for (j = k; j < n; ++j)
				{
					Fraction t = new Fraction(a[k][j]);
					a[k][j] = a[row][j];
					a[row][j] = t;
				}
				Fraction t = new Fraction(b[k]);
				b[k] = b[row];
				b[row] = t;
			}
			for (j = k + 1; j < n; ++j)
			{
				a[k][j] = a[k][j].div(maxp);
				for (i = k + 1; i < n; ++i)
					a[i][j] = a[i][j].sub(a[i][k].mult(a[k][j]));
			}
			b[k] = b[k].div(maxp);
			for (i = k + 1; i < n; ++i)
				b[i] = b[i].sub(b[k].mult(a[i][k]));

		}
		for (i = n - 1; i >= 0; --i)
			for (j = i + 1; j < n; ++j)
				b[i] = b[i].sub(b[j].mult(a[i][j]));
		return true;
	}

	public static void main(String[] args)
	{
		/*
		 * 2x1+2x2-x3=6 x1-2x2+4x3=45x1+7x2+x3=28
		 */
		Fraction a[][] =
		{
		{ new Fraction("2"), new Fraction("2"), new Fraction("-1") },
		{ new Fraction("1"), new Fraction("-2"), new Fraction("4") },
		{ new Fraction("5"), new Fraction("7"), new Fraction("1") } };
		Fraction b[] =
		{ new Fraction("6"), new Fraction("3"), new Fraction("28") };
		System.out.println(gauss_cpivot(3, a, b));
		for (int i = 0; i < 3; i++)
		{
			b[i].show();
		}
	}
}
