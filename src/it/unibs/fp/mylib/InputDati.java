package it.unibs.fp.mylib;
import java.util.*;
public class InputDati 
{
	  private static Scanner lettore = creaScanner();
	  
	  private final static String ERRORE_FORMATO = "Error:the data format is not correct";
	  private final static String ERRORE_MINIMO= "Error: the value must be higher than";
	  private final static String ERRORE_STRINGA_VUOTA= "Error: you enter no char";
	  private final static String ERRORE_MASSIMO= "Error: tha value must be lower than";
	  private final static String MESSAGGIO_AMMISSIBILI= "Attention: the allowed chars are: ";

	  private final static char RISPOSTA_SI='Y';
	  private final static char RISPOSTA_NO='N';

	  
 
	  private static Scanner creaScanner ()
	  {
	   Scanner creato = new Scanner(System.in);
	   creato.useDelimiter(System.getProperty("line.separator"));
	   return creato;
	  }
	  
	  public static String leggiStringa (String messaggio)
	  {
		  System.out.print(messaggio);
		  return lettore.next();
	  }
	  
	  public static String leggiStringaNonVuota(String messaggio)
	  {
	   boolean finito=false;
	   String lettura = null;
	   do
	   {
		 lettura = leggiStringa(messaggio);
		 lettura = lettura.trim();
		 if (lettura.length() > 0)
		  finito=true;
		 else
		  System.out.println(ERRORE_STRINGA_VUOTA);
	   } while (!finito);
	   
	   return lettura;
	  }
	  
	  public static char leggiChar (String messaggio)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	    {
	     System.out.print(messaggio);
	     String lettura = lettore.next();
	     if (lettura.length() > 0)
	      {
	       valoreLetto = lettura.charAt(0);
	       finito = true;
	      }
	     else
	      {
	       System.out.println(ERRORE_STRINGA_VUOTA);
	      }
	    } while (!finito);
	   return valoreLetto;
	  }
	  
	  public static char leggiUpperChar (String messaggio, String ammissibili)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	   {
	    valoreLetto = leggiChar(messaggio);
	    valoreLetto = Character.toUpperCase(valoreLetto);
	    if (ammissibili.indexOf(valoreLetto) != -1)
		 finito  = true;
	    else
	     System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
	   } while (!finito);
	   return valoreLetto;
	  }
	  
	  
	  public static Integer leggiIntero (String messaggio)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     try
	      {
	    	 valoreLetto =Integer.parseInt(leggiStringaNonVuota(messaggio)) ;
	    	 finito = true;
	      }
	     catch ( InputMismatchException | NumberFormatException e)
	      {
	       System.out.println(ERRORE_FORMATO);
	      // String daButtare = lettore.next();
	      }
	    } while (!finito);
	   return valoreLetto;
	  }
	  

	  public static int leggiInteroPositivo(String messaggio)
	  {
		  return leggiInteroConMinimo(messaggio,1);
	  }
	  
	  public static int leggiInteroNonNegativo(String messaggio)
	  {
		  return leggiInteroConMinimo(messaggio,0);
	  }
	  
	  
	  public static int leggiInteroConMinimo(String messaggio, int minimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiIntero(messaggio);
	     if (valoreLetto >= minimo)
	      finito = true;
	     else
	      System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
	     
	   return valoreLetto;
	  }

	  public static int leggiIntero(String messaggio, int minimo, int massimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiIntero(messaggio);
	     if (valoreLetto >= minimo && valoreLetto<= massimo)
	      finito = true;
	     else
	      if (valoreLetto < minimo)
	         System.out.println(ERRORE_MINIMO + minimo);
	      else
	    	 System.out.println(ERRORE_MASSIMO + massimo); 
	    } while (!finito);
	     
	   return valoreLetto;
	  }

	  
	  public static double leggiDouble (String messaggio)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     try
	      {
	    	 valoreLetto =Double.parseDouble(leggiStringaNonVuota(messaggio)) ;
	    	 finito = true;
	      }
	     catch (InputMismatchException | NumberFormatException e)
	      {
	       System.out.println(ERRORE_FORMATO);
	      // String daButtare = lettore.next();
	      }
	    } while (!finito);
	   return valoreLetto;
	  }
	 
	  public static float leggiFloat (String messaggio, int minimo, int massimo)
	  {
	  return (float)leggiDouble(messaggio, minimo, massimo);
	  }
	  
	  public static double leggiDoubleConMinimo (String messaggio, double minimo)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiDouble(messaggio);
	     if (valoreLetto >= minimo)
	      finito = true;
	     else
	      System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
	     
	   return valoreLetto;
	  }

	  public static double leggiDouble(String messaggio, int minimo, int massimo)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiDouble(messaggio);
	     if (valoreLetto >= minimo && valoreLetto<= massimo)
	      finito = true;
	     else
	      if (valoreLetto < minimo)
	         System.out.println(ERRORE_MINIMO + minimo);
	      else
	    	 System.out.println(ERRORE_MASSIMO + massimo); 
	    } while (!finito);
	     
	   return valoreLetto;
	  }
	  
	  public static boolean yesOrNo(String messaggio)
	  {
		  String mioMessaggio = messaggio + "("+RISPOSTA_SI+"/"+RISPOSTA_NO+")";
		  char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(RISPOSTA_SI)+String.valueOf(RISPOSTA_NO));
		  
		  if (valoreLetto == RISPOSTA_SI)
			return true;
		  else
			return false;
	  }

}
