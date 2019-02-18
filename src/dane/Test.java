package dane;
import java.io.Serializable;
import java.util.ArrayList;

public class Test implements Serializable {
	private int id;
	private String tytul;
	private String autor;
	private String opis;
	private ArrayList<Pytanie> pytania = new ArrayList<Pytanie>();
	public Test() {
		super();
	}
	public Test(int id, String tytul, String autor, String opis, ArrayList<Pytanie> pytania) {
		super();
		this.id = id;
		this.tytul = tytul;
		this.autor = autor;
		this.opis = opis;
		this.pytania = pytania;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTytul() {
		return tytul;
	}
	public void setTytul(String tytul) {
		this.tytul = tytul;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<Pytanie> getPytania() {
		return pytania;
	}
	public void setPytania(ArrayList<Pytanie> pytania) {
		this.pytania = pytania;
	}
}
