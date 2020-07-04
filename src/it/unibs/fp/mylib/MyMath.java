package it.unibs.fp.mylib;

public class MyMath{
	private static final int POWER_2 = 2;
	private static final double MINIMUM = 0.00001;
	
	public static double logBaseGenerica(double base,double x) {
		return Math.log(x)/Math.log(base);
	}
	
	public static double logBase2(double x) {
		return logBaseGenerica(POWER_2,x);
	}
	public static boolean compareDouble(double d1, double d2) {
		if(Math.abs((d1-d2))<MINIMUM) {
			return true;
		}
		else {
			return false;
		}
	}
	public static double distance(double x1, double x2,double y1,double y2) {
		return Math.sqrt((Math.pow(x2-x1, POWER_2)+Math.pow(y2-y1, POWER_2)));
	
	}
	public static double cathetus(double hypot,double cathetus2) {
		double value=Math.sqrt(Math.pow(hypot, POWER_2))-Math.pow(cathetus2, POWER_2);
		return value;
	}
}
