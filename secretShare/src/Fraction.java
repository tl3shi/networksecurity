import java.math.BigInteger;

public class Fraction
{// 分数类
	BigInteger a, b;// a/b,b>0;

	Fraction()
	{// 构造函数1
		a = new BigInteger("0");
		b = new BigInteger("1");
	}

	Fraction(BigInteger val)
	{// 构造函数2
		a = new BigInteger(val.toString());
		b = new BigInteger("1");
	}

	Fraction(String a)
	{
		this.a = new BigInteger(a);
		this.b = new BigInteger("1");
	}

	Fraction(BigInteger a0, BigInteger b0)
	{// 构造函数3
		this.a = a0;
		this.b = b0;
		this.reduction();
	}

	Fraction(String a, String b)
	{
		this.a = new BigInteger(a);
		this.b = new BigInteger(b);
		this.reduction();
	}

	Fraction(Fraction other)
	{
		this.a = new BigInteger(other.a.toString());
		this.b = new BigInteger(other.b.toString());
	}

	void reduction()
	{// 化简
		BigInteger tmp = a.gcd(b);// 其值是 abs(a) 和 abs(b) 的最大公约数。
		if(tmp.compareTo(BigInteger.ZERO) == 0)
			return;
		a = a.divide(tmp);
		b = b.divide(tmp);
		if (b.compareTo(BigInteger.ZERO) == -1)
		{
			b = b.multiply(BigInteger.valueOf(-1));
			a = a.multiply(BigInteger.valueOf(-1));
		}
	}

	Fraction add(Fraction t)
	{// 加一个
		Fraction tmp = new Fraction(a.multiply(t.b).add(b.multiply(t.a)),
				b.multiply(t.b));
		tmp.reduction();
		return tmp;
	}

	Fraction sub(Fraction t)
	{// 减
		Fraction tmp = new Fraction(a.multiply(t.b).subtract(b.multiply(t.a)),
				b.multiply(t.b));
		tmp.reduction();
		return tmp;
	}

	Fraction mult(Fraction t)
	{// 乘
		Fraction tmp = new Fraction(a.multiply(t.a), b.multiply(t.b));
		tmp.reduction();
		return tmp;
	}

	Fraction div(Fraction t)
	{// 除
		Fraction tmp = new Fraction(a.multiply(t.b), b.multiply(t.a));
		tmp.reduction();
		return tmp;
	}

	Fraction abs()
	{
		Fraction tmp = new Fraction(this);
		tmp.a = tmp.a.abs();
		return tmp;
	}

	void show()
	{
		this.reduction();
		if (b.compareTo(BigInteger.ONE) == 0)
			System.out.println(a);
		else
			System.out.println(a + "/" + b);
	}

	@Override
	public String toString()
	{
		this.reduction();
		if (b.compareTo(BigInteger.ONE) == 0)
			return a.toString();
		else
			return a + "/" + b;
	}

	public String toString(int randix)
	{
		return a.toString(randix);
	}

	boolean Biger(Fraction p)
	{
		return this.sub(p).a.compareTo(BigInteger.ZERO) == 1;
	}

	int zero()
	{
		this.reduction();
		return this.a.compareTo(BigInteger.ZERO);
	}
}