import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class KeyTool2
{
	private int keyNum;//the number generated
	private int leastKeyNum;//number to reconstruct the key
	private int[] randNum; // the random number generated
	private String secret; // the secret 
	private BigInteger digitalSecret ;
	static boolean  debug = true;
	static boolean base64 = true;//if encode the generate key with base64
	public KeyTool2(int keyNum, int leastKeyNum, String secret)
	{
		this.keyNum = keyNum;
		this.leastKeyNum = leastKeyNum;
		this.randNum = this.genRandNum();
		this.secret = secret;
		this.handleStrSecret(secret);
	}
	
	
	private String[] genKeys()
	{
		Map<Integer,String> map = new HashMap<Integer,String>();
		String[] keys = new String[this.keyNum];
		Random rand = new Random();
		for(int j = 0; j < this.keyNum; j++)
		{ 
			//ascii 32 - 126, are the printable ones ,32  is blank
			int x = (rand.nextInt(126-33) + 33);
			if(debug)
				System.out.println("x:" + x);
			char char_x = (char)x;
			
			BigInteger result = this.digitalSecret;
			for(int i = 0; i < this.leastKeyNum - 1; i++)
			{
				BigInteger b = BigInteger.valueOf(randNum[i]);
				b = b.multiply(BigInteger.valueOf(x).pow(i+1));
				result = result.add(b);
			}
			//should store the least num key in the generated keys, while the num should printable
			if(base64)
				keys[j] = char_x + Base64.encode(result.toString()) + (char)(this.leastKeyNum+32);
			else
				keys[j] = char_x + result.toString() + (char)(this.leastKeyNum+32);
			
		}
		return keys;
	}
	
	private void handleStrSecret(String key)
	{
		char[] keys = key.toCharArray();
		String hxSecret = "";
		for(int i = 0; i < keys.length; i++)
		{
			int k = keys[i];
			if(debug)
				System.out.println((char)keys[i] + ":" + Integer.toHexString(k));
			hxSecret += Integer.toHexString(k);
		}
		if (debug)
		{
			System.out.println("hxSecret:"+hxSecret);
			System.out.println("10:"+new BigInteger(hxSecret, 16).toString(10));
		}
		this.digitalSecret = new BigInteger (new BigInteger(hxSecret, 16).toString(10));//BigInteger.valueOf(Integer.valueOf(hxSecret,16));
	}
	
	public static String decode(String [] keys)
	{
		int size = keys.length;
		int leastKeyNum = keys[0].charAt(keys[0].length() - 1) - 32;
		if(size < leastKeyNum)
			return  leastKeyNum + " keys Required, you have just " + size ;
		
		Fraction [][] a = new Fraction[size+1][size+1];
		Fraction[] b = new Fraction[size+1];
		for (int i = 0; i < size; i++)
		{
			int x = keys[i].charAt(0);
			for(int j = 1; j <= size; j++)
			{
				a[i+1][j] = new Fraction(BigInteger.valueOf(x).pow(j-1));
			}
			if(base64)
				b[i+1] = new Fraction(Base64.decode(keys[i].substring(1)));
			else
				b[i+1] = new Fraction(keys[i].substring(1));
			
		}
		if(debug)
		{
			System.out.println(size);
			System.out.println(new Matrix(a));
			System.out.println(new Matrix(b));
		}
		
		BigDuriRitterNPlus1 t = new BigDuriRitterNPlus1(a,b,size);
		b = t.getResult();
		
		//if(!hasResult) 
		{
			System.out.println(new Matrix(b));
			//return "Error in solve Equation.";
		}
		if(debug)
		{
			for(int i = 1; i <  b.length; i++)
				System.out.println(b[i]);
		}
		String hxResult = b[1].toString(16);
		String result = "";
		if(debug)
			System.out.println("hxResult :" + hxResult);
		
		for(int i = 0; i < hxResult.length(); i+=2)
		{
			int x = Integer.valueOf(hxResult.substring(i, i+2), 16);
			result += (char)x;
		}
		
		return result;
	}
	
	private int[] genRandNum()
	{
		/*
		int[] randnum = new int[leastKeyNum - 1];
		Random rand = new Random();
		for(int i = 0; i < randnum.length; i++)
		{
			randnum[i] = rand.nextInt();
		}
		return randnum;
		*/
		int[] randnum = new int[]{97,91,89,87,83,79,73,71,67,61};
		return randnum;
	}
	
	public static void main(String[] args)
	{
		KeyTool2 tool = new KeyTool2(9, 9, "networksecurity?222");
		String[] keys = tool.genKeys();
		
		for(int i = 0; i < keys.length; i++)
			System.out.println(keys[i]);
		
		//String[] map = new String[]{keys[0],keys[3],keys[4], keys[5]};
		
		System.out.println(KeyTool2.decode(keys));
	}
}
