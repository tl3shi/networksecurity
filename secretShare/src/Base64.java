/*
 *  com.twiek.Utils.Base64* package version 1.0, Base64 encoding/decoding utilities
 *  Copyright (C) 2003  Brent "Twiek" Crowe.  All rights reserved.
 *
 *  This file is part of the com.twiek.Utils.Base64* package
 *
 *  The com.twiek.Utils.Base64* package is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

/**
 * Base64 encoding and decoding.  Conforms to section 5.2 of RFC 1341
 * @version $Revision: 1.0 $
 * @author Brent "Twiek" Crowe <Twiek@softhome.net>
 */
public final class Base64
{

	private static final char[] BASE64CHARS =
	{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
			'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static final char PAD = '=';
	private static final String CRLF = "\r\n";

	private Base64()
	{
	}

	public static String encode(String str)
	{

		StringBuffer output = new StringBuffer();
		byte[] source = str.getBytes();
		int buffer;

		for (int i = 0; i < source.length; i += 3)
		{

			buffer = 0;

			if ((i % 54) == 0 && i != 0)
				output.append(CRLF);

			if (source.length <= i + 1)
			{

				buffer = (eightbit(source[i]) << 16);
				output.append(BASE64CHARS[sixbit(buffer >> 18)]);
				output.append(BASE64CHARS[sixbit(buffer >> 12)]);
				output.append(PAD);
				output.append(PAD);

			} else if (source.length <= i + 2)
			{

				buffer = (eightbit(source[i]) << 16)
						| (eightbit(source[i + 1]) << 8);
				output.append(BASE64CHARS[sixbit(buffer >> 18)]);
				output.append(BASE64CHARS[sixbit(buffer >> 12)]);
				output.append(BASE64CHARS[sixbit(buffer >> 6)]);
				output.append(PAD);

			} else
			{

				buffer = (eightbit(source[i]) << 16)
						| (eightbit(source[i + 1]) << 8)
						| (eightbit(source[i + 2]));
				output.append(BASE64CHARS[sixbit(buffer >> 18)]);
				output.append(BASE64CHARS[sixbit(buffer >> 12)]);
				output.append(BASE64CHARS[sixbit(buffer >> 6)]);
				output.append(BASE64CHARS[sixbit(buffer)]);

			}

		}

		return output.toString();

	}

	public static String decode(String str)
	{

		StringBuffer output = new StringBuffer();
		byte[] source = str.getBytes();
		int data = 0, buffer = 0, bytesRead = 0;

		for (int i = 0; i < source.length; i++)
		{

			if (bytesRead < 4)
			{

				data = decodeInt(source[i]);

				if (data == -1)
					continue;
				else if (data == -2 && bytesRead != 2 && bytesRead != 3)
					return null;
				else if (data == -2 && bytesRead == 2)
				{

					output.append((char) eightbit(buffer >> 4));

					return output.toString();

				} else if (data == -2 && bytesRead == 3)
				{

					output.append((char) eightbit(buffer >> 10));
					output.append((char) eightbit(buffer >> 2));

					return output.toString();

				} else
				{

					buffer = (buffer << 6) | sixbit(data);
					bytesRead++;

				}

			}
			if (bytesRead == 4)
			{

				output.append((char) eightbit(buffer >> 16));
				output.append((char) eightbit(buffer >> 8));
				output.append((char) eightbit(buffer));

				buffer = 0;
				bytesRead = 0;

			}

		}

		return output.toString();

	}

	private static int decodeInt(int input)
	{

		if (input >= 'A' && input <= 'Z')
			return input - 'A';
		if (input >= 'a' && input <= 'z')
			return input - 'a' + 26;
		if (input >= '0' && input <= '9')
			return input - '0' + 52;
		if (input == '+')
			return 62;
		if (input == '/')
			return 63;
		if (input == '=')
			return -2;

		return -1;

	}

	private static int sixbit(int input)
	{

		return (input & 0x3F);

	}

	private static int eightbit(int input)
	{

		return (input & 0xFF);

	}
	public static void main(String[] args)
	{
		System.out.println(Base64.encode("232443434wedsadd"));
		System.out.println(Base64.decode("dGFuZ2xlaXRodQ=="));
	}

}