package it.unibs.fp.mylib;

/**
 * Questa classe rappresenta un menu testuale generico a piu' voci Si suppone
 * che la voce per uscire sia sempre associata alla scelta 0 e sia presentata in
 * fondo al menu
 * 
 */

public class MyMenu {

	final private static String VOCE_USCITA = "0\tExit";
	final private static String RICHIESTA_INSERIMENTO = "Enter the number of the required option: ";

	private String titolo;
	private String[] voci;
	private int numeroDiX;

	public MyMenu(String titolo, String[] voci) {
		this.titolo = titolo;
		this.voci = voci;
	}

	public MyMenu(String titolo, String[] voci, int numeroDiX) {
		this.titolo = titolo;
		this.voci = voci;
		this.numeroDiX = numeroDiX;
	}

	public int scegli() {
		stampaMenu();
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
	}

	public int scegliConX() {
		stampaMenuConX();
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
	}
	public int scegliSenzaUscita() {
		stampaMenuSenzaUscita();
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
	}
	public void stampaMenu() {
		System.out.println(stampaCornice());
		System.out.println(titolo);
		System.out.println(stampaCornice());
		for (int i = 0; i < voci.length; i++) {
			System.out.println((i + 1) + "\t" + voci[i]);
		}
		System.out.println();
		System.out.println(VOCE_USCITA);
		System.out.println();
	}

	public void stampaMenuConX() {
		System.out.println(stampaCornice());
		System.out.println(titolo);
		System.out.println(stampaCornice());
		for (int i = 0; i < voci.length; i++) {
			System.out.print((i + 1) + "\t" + voci[i] +" ");
			for(int j=0;j<numeroDiX;j++) {
				System.out.print("X");
			}
			System.out.println(" ");
		}
		System.out.println();
		System.out.println(VOCE_USCITA);
		System.out.println();
	}
	public void stampaMenuSenzaUscita() {
		System.out.println(stampaCornice());
		System.out.println(titolo);
		System.out.println(stampaCornice());
		for (int i = 0; i < voci.length; i++) {
			System.out.println((i + 1) + "\t" + voci[i]);
		}
	
	}
	private String stampaCornice() {
		StringBuilder c = new StringBuilder();
		for (int i = 0; i <= titolo.length(); i++) {
			c.append('-');
		}
		return c.toString();

	}
}
