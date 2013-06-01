
public class Matrix
{
	private int m;
	private int n;
	private int[][] data;
	
	private Fraction[][] data_;
	
	public Matrix(int [][] d)
	{
		this.data = d;
		m = d[0].length;
		n = d.length;
	}
	
	public Matrix(Fraction [] d)
	{
		this.data_ = new Fraction[1][d.length];
		this.data_[0] = d;
		m = 1;
		n = d.length;
	}

	
	public Matrix(Fraction [][] d)
	{
		this.data_ = d;
		m = d[0].length;
		n = d.length;
	}
	
	@Override 
	public String toString()
	{
		String s= "{";
		for(int i = 0; i < m; i++)
		{
			s += "[";
			for(int j = 0 ;j < n; j++)
				s += data_[i][j] + ",";
			s += "]\n";
		}
		return s+"}\n";
		
	}
}
