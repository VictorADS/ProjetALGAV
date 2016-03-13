package util;

public class Tools {


	/*Tete mot*/


	public static char head(String mot){
		if(mot.length()!=0){
			return mot.charAt(0);

		}else{
			return '\0';
		}
	}
	public static int MaxThree(int a1,int a2,int a3){
		int res=Math.max(a1, a2);
		return Math.max(res,a3);
	}
	public static String tail(String mot){
		if(mot.length()!=0){
			return mot.substring(1);

		}else{
			return "";
		}
	}

}
