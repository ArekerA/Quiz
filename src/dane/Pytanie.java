package dane;
import java.io.Serializable;
import java.util.ArrayList;

public class Pytanie implements Serializable {
	private int id;
	private String tekst;
	private String autor;
	private ArrayList<Odpowiedz> odpowiedzi = new ArrayList<Odpowiedz>();
	public Pytanie() {
		super();
	}
	public Pytanie(int id, String tekst, String autor, ArrayList<Odpowiedz> odpowiedzi) {
		super();
		this.id = id;
		this.tekst = tekst;
		this.autor = autor;
		this.odpowiedzi = odpowiedzi;
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
	public ArrayList<Odpowiedz> getOdpowiedzi() {
		return odpowiedzi;
	}
	public void setOdpowiedzi(ArrayList<Odpowiedz> odpowiedzi) {
		this.odpowiedzi = odpowiedzi;
	}
	public float procent(int odp)
	{
		float a = 0;
		for(Odpowiedz o : odpowiedzi)
		{
			a += o.getLicznik();
		}
		return odpowiedzi.get(odp).getLicznik()/a;
	}
}
