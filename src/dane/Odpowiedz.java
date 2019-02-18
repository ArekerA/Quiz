package dane;

import java.io.Serializable;

public class Odpowiedz implements Serializable {
	private int id;
	private String tekst;
	private String autor;
	private int licznik;
	private boolean prawidlowa;
	private boolean zaznaczona;
	public Odpowiedz() {
		super();
	}
	public Odpowiedz(int id, String tekst, String autor, int licznik, boolean prawidlowa) {
		super();
		this.id = id;
		this.tekst = tekst;
		this.autor = autor;
		this.licznik = licznik;
		this.prawidlowa = prawidlowa;
		this.zaznaczona = false;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTekst() {
		return tekst;
	}
	public void setTekst(String tekst) {
		this.tekst = tekst;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public int getLicznik() {
		return licznik;
	}
	public void dodajLicznik() {
		licznik++;
	}
	public boolean isPrawidlowa() {
		return prawidlowa;
	}
	public void setPrawidlowa(boolean prawidlowa) {
		this.prawidlowa = prawidlowa;
	}
	public boolean isZaznaczona() {
		return zaznaczona;
	}
	public void setZaznaczona(boolean zaznaczona) {
		this.zaznaczona = zaznaczona;
	}
}
