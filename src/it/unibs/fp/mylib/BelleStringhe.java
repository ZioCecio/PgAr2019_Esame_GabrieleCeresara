package it.unibs.fp.mylib;

public class BelleStringhe
{

 private final static String SPAZIO = " ";
 
 private final static String ACAPO = System.lineSeparator();



 public static String incornicia (String s)
	{ 
	 StringBuilder res = new StringBuilder();
		
	 res.append(stampaCornice(s)+ACAPO);
	 res.append(s+ACAPO);
	 res.append(stampaCornice(s)+ACAPO);

 	 return res.toString();

  }

 
 public static String incolonna (String s, int larghezza)
	{
	 StringBuilder res = new StringBuilder(larghezza);
	 int numCharDaStampare = Math.min(larghezza,s.length());
	 res.append(s.substring(0, numCharDaStampare));
	 for (int i=s.length()+1; i<=larghezza; i++)
		res.append(SPAZIO);
	 return res.toString();
	}
	
 public static String centrata (String s, int larghezza)
	{
	 StringBuilder res = new StringBuilder(larghezza);
	 if (larghezza <= s.length())
		res.append(s.substring(larghezza));
	 else
		{
		 int spaziPrima = (larghezza - s.length())/2;
		 int spaziDopo = larghezza - spaziPrima - s.length();
		 for (int i=1; i<=spaziPrima; i++)
			res.append(SPAZIO);
			
		 res.append(s);
		
		 for (int i=1; i<=spaziDopo; i++)
			res.append(SPAZIO);
		}
	 	 return res.toString();

	}
 
 private static String stampaCornice(String messa) {
	  StringBuilder c =new StringBuilder();
	  for (int i = 0; i <= messa.length()*2; i++) {
		c.append('-');}
		return c.toString();
	
 }

}

