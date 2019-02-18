package serwer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import dane.Odpowiedz;
import dane.Pytanie;
import dane.Test;
import javafx.collections.ObservableList;

public class JDBC {
	static Connection con;
	public static boolean checkDriver(String driver) {
		System.out.print("Sprawdzanie sterownika:");
		try {
			Class.forName(driver).newInstance();
			System.out.println(" ... OK");
			return true;
		} catch (Exception e) {
			System.out.println("... ERROR");
			return false;
		}
	}
	public static Connection getConnection(String kindOfDatabase, String adres, int port, String userName, String password) {

		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		connectionProps.put("characterEncoding", "utf8");
		try {
			conn = DriverManager.getConnection(kindOfDatabase + adres + ":" + port + "/",
					connectionProps);
		} catch (SQLException e) {
			System.out.println("Błąd połączenia z bazą danych! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(2);
		}
		System.out.println("Połączenie z bazą danych: ... OK");
		return conn;
	}
	private static Statement createStatement(Connection connection) {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Błąd createStatement! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(3);
		}
		return null;
	}
	private static void closeConnection(Connection connection, Statement s) {
		System.out.print("\nZamykanie polaczenia z bazą:");
		try {
			s.close();
			connection.close();
		} catch (SQLException e) {
			System.out
					.println("Bląd przy zamykaniu polączenia z bazą! " + e.getMessage() + ": " + e.getErrorCode());;
			System.exit(4);
		}
		System.out.print(" zamknięcie OK");
	}

	/**
	 * Wykonanie kwerendy i przesłanie wyników do obiektu ResultSet
	 * 
	 * @param s - Statement
	 * @param sql - zapytanie
	 * @return wynik
	 */
	private static ResultSet executeQuery(Statement s, String sql) {
		try {
			return s.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return null;
	}
	private static int executeUpdate(Statement s, String sql) {
		try {
			return s.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return -1;
	}
	
	/**
	 * Wyświetla dane uzyskane zapytaniem select
	 * @param r - wynik zapytania
	 */
	private static void printDataFromQuery(ResultSet r) {
		ResultSetMetaData rsmd;
		try {
			rsmd = r.getMetaData();
			int numcols = rsmd.getColumnCount(); // pobieranie liczby kolumn
			// wyswietlanie nazw kolumn:
			for (int i = 1; i <= numcols; i++) {
				System.out.print("\t" + rsmd.getColumnLabel(i) + "\t|");
			}
			System.out
					.print("\n____________________________________________________________________________\n");
			/**
			 * r.next() - przejście do kolejnego rekordu (wiersza) otrzymanych wyników
			 */
			// wyswietlanie kolejnych rekordow:
			while (r.next()) {
				for (int i = 1; i <= numcols; i++) {
					Object obj = r.getObject(i);
					if (obj != null)
						System.out.print("\t" + obj.toString() + "\t|");
					else
						System.out.print("\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("Bląd odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
		}
	}
	/**
	 * Metoda pobiera dane na podstawie nazwy kolumny
	 */
	public static void sqlGetDataByName(ResultSet r) {
		System.out.println("Pobieranie danych z wykorzystaniem nazw kolumn");
		try {
			ResultSetMetaData rsmd = r.getMetaData();
			int numcols = rsmd.getColumnCount();
			// Tytul tabeli z etykietami kolumn zestawow wynikow
			for (int i = 1; i <= numcols; i++) {
				System.out.print(rsmd.getColumnLabel(i) + "\t|\t");
			}
			System.out
			.print("\n____________________________________________________________________________\n");
			while (r.next()) {
				int size = r.getMetaData().getColumnCount();
				for(int i = 1; i <= size; i++){
					switch(r.getMetaData().getColumnTypeName(i)){
					case "INT":
						System.out.print(r.getInt(r.getMetaData().getColumnName(i)) + "\t|\t");
						break;
					case "DATE":
						System.out.print(r.getDate(r.getMetaData().getColumnName(i)) + "\t|\t");
						break;
					case "VARCHAR":
						System.out.print(r.getString(r.getMetaData().getColumnName(i)) + "\t|\t");
						break;
					default:
						System.out.print(r.getMetaData().getColumnTypeName(i));
					}
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("Bląd odczytu z bazy! " + e.getMessage() + ": " + e.getErrorCode());
		}
	}
	public static void init(String adres, String user, String pass, String baza)
	{
		if (!checkDriver("com.mysql.jdbc.Driver"))
			System.exit(1);
		con = getConnection("jdbc:mysql://", adres, 3306, user, pass);
		Statement st = createStatement(con);
		if (executeUpdate(st, "USE "+baza+";") == 0)
			System.out.println("Baza "+baza+" wybrana");
		else {
			System.out.println("Baza nie istnieje! Tworzymy bazę: ");
			if (executeUpdate(st, "create Database "+baza+" DEFAULT CHARSET=utf8;") == 1)
				System.out.println("Baza "+baza+" utworzona");
			else
			{
				System.out.println("Baza "+baza+" nieutworzona!");
				System.exit(10);
			}
			if (executeUpdate(st, "USE "+baza+";") == 0)
				System.out.println("Baza "+baza+" wybrana");
			else
			{
				System.out.println("Baza "+baza+" niewybrana!");
				System.exit(11);
			}
		}
		if (executeUpdate(st,
				"CREATE TABLE testy ( id INT NOT NULL, tytul TEXT NOT NULL, opis TEXT NOT NULL, autor VARCHAR(50) NOT NULL, PRIMARY KEY (id) );") == 0)
			System.out.println("Tabela testy utworzona");
		else
			System.out.println("Tabela testy nie utworzona!");
		if (executeUpdate(st,
				"CREATE TABLE pytania ( id INT NOT NULL, id_test INT NOT NULL, tekst VARCHAR(200) NOT NULL, autor VARCHAR(50) NOT NULL, PRIMARY KEY (id),  FOREIGN KEY (id_test) REFERENCES testy(id) );") == 0)
			System.out.println("Tabela pytania utworzona");
		else
			System.out.println("Tabela pytania nie utworzona!");
		if (executeUpdate(st,
				"CREATE TABLE odpowiedzi ( id INT NOT NULL, id_pytanie INT NOT NULL, tekst TEXT NOT NULL, autor VARCHAR(50) NOT NULL, poprawna TINYINT(1) NOT NULL, licznik INT NOT NULL, PRIMARY KEY (id),  FOREIGN KEY (id_pytanie) REFERENCES pytania(id) );") == 0)
			System.out.println("Tabela odpowiedzi utworzona");
		else
			System.out.println("Tabela odpowiedzi nie utworzona!");
		if (executeUpdate(st,
				"CREATE TABLE users ( id INT NOT NULL, nazwa VARCHAR(50) NOT NULL, PRIMARY KEY (id));") == 0)
			System.out.println("Tabela users utworzona");
		else
			System.out.println("Tabela users nie utworzona!");
		if (executeUpdate(st,
				"CREATE TABLE users_odpowiedz ( id_u INT NOT NULL, id_o INT NOT NULL, PRIMARY KEY (id_u, id_o), FOREIGN KEY (id_u) REFERENCES users(id), FOREIGN KEY (id_o) REFERENCES odpowiedzi(id));") == 0)
			System.out.println("Tabela users_odpowiedz utworzona");
		else
			System.out.println("Tabela users_odpowiedz nie utworzona!");
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void initTest()
	{
		Statement st = createStatement(con);
		String sql;
		sql = "INSERT INTO testy VALUES(0, 'tytuł', 'opis', 'autor'),"
				+ "(1, 'tytuł 2', 'długi opis', 'kolejny autor'),"
				+ "(2, 'super tytuł', 'długi super opis', 'kolejny autor'),"
				+ "(3, 'Mój pierwszy test', 'Test o grach', 'Czarodziej');";
		executeUpdate(st, sql);
		sql = "INSERT INTO pytania VALUES(0, 0, 'tekst', 'autor'),"
				+ "(1, 1, 'tekst', 'kolejny autor'),"
				+ "(2, 2, 'tekst', 'kolejny autor'),"
				+ "(3, 3, 'Która gra powstała najwcześniej', 'Czarodziej'),"
				+ "(4, 3, 'W którym roku powstała gra Heroes of Might and Magic III', 'Czarodziej'),"
				+ "(5, 3, 'Ile gier liczy seria Might and Magic (bez serii pobocznych)', 'Czarodziej');";
		executeUpdate(st, sql);
		sql = "INSERT INTO odpowiedzi VALUES(0, 0, 'tekst', 'autor', 1, 0),"
				+ "(1, 1, 'tekst', 'kolejny autor', 1, 0),"
				+ "(2, 2, 'tekst', 'kolejny autor', 1, 0),"
				+ "(3, 3, 'Pac-Man', 'Czarodziej', 1, 0),"
				+ "(4, 3, 'Mario', 'Czarodziej', 0, 0),"
				+ "(5, 3, 'Tetris', 'Czarodziej', 0, 0),"
				+ "(6, 3, 'Arkanoid', 'Czarodziej', 0, 0),"
				+ "(7, 4, '1997', 'Czarodziej', 0, 0),"
				+ "(8, 4, '1998', 'Czarodziej', 0, 0),"
				+ "(9, 4, '1999', 'Czarodziej', 1, 0),"
				+ "(10, 4, '2000', 'Czarodziej', 0, 0),"
				+ "(11, 5, '9', 'Czarodziej', 0, 0),"
				+ "(12, 5, '10', 'Czarodziej', 1, 0),"
				+ "(13, 5, '11', 'Czarodziej', 0, 0),"
				+ "(14, 5, '12', 'Czarodziej', 0, 0);";
		executeUpdate(st, sql);
		sql = "INSERT INTO users VALUES(0, 'test');";
		executeUpdate(st, sql);
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close()
	{
		Statement st = createStatement(con);
		closeConnection(con, st);
		try {
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int dodajTest(String tytul, String opis, String autor)
	{
		System.out.println("Dodaje test "+tytul);
		Statement st = createStatement(con);
		try {
			ResultSet r = executeQuery(st, "Select MAX(id) from testy;");
			r.next();
			int a = (r.getInt(1)+1);
			executeUpdate(st, "INSERT INTO testy VALUES("+a+", '"+tytul+"', '"+opis+"', '"+autor+"');");
			System.out.println("Test dodany id:"+a);
			st.close();
			return a;
		} catch (SQLException e) {
			System.out.println("Błąd dodawania testu");
			e.printStackTrace();
		}
		return -1;
	}
	public static int dodajUser(String nazwa)
	{
		System.out.println("Dodaje usera "+nazwa);
		Statement st = createStatement(con);
		try {
			ResultSet r = executeQuery(st, "Select MAX(id) from users;");
			r.next();
			int a = (r.getInt(1)+1);
			executeUpdate(st, "INSERT INTO users VALUES("+a+", '"+nazwa+"');");
			System.out.println("User dodany id:"+a);
			st.close();
			return a;
		} catch (SQLException e) {
			System.out.println("Błąd dodawania usera");
			e.printStackTrace();
		}
		return -1;
	}
	public static int dodajUserOdp(int id_u, int id_o)
	{
		System.out.println("Dodaje odpowiedzi ");
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "INSERT INTO users_odpowiedz VALUES("+id_u+", '"+id_o+"');");
			System.out.println("odpowiedź dodana id:"+id_o);
			st.close();
			return 1;
		} catch (SQLException e) {
			System.out.println("Błąd dodawania odpowiedzi");
			e.printStackTrace();
		}
		return -1;
	}
	public static int dodajPytanie(int idTestu, String tekst, String autor)
	{
		Statement st = createStatement(con);
		try {
			ResultSet r = executeQuery(st, "Select MAX(id) from pytania;");
			r.next();
			int a = (r.getInt(1)+1);
			executeUpdate(st, "INSERT INTO pytania VALUES("+a+", "+idTestu+", '"+tekst+"', '"+autor+"');");
			st.close();
			return a;
		} catch (SQLException e) {
			System.out.println("Błąd dodawania pytania");
			e.printStackTrace();
		}
		return -1;
	}
	public static int dodajOdpowiedz(int idPytania, String tekst, String autor, boolean poprawna)
	{
		Statement st = createStatement(con);
		try {
			ResultSet r = executeQuery(st, "Select MAX(id) from odpowiedzi;");
			r.next();
			int a = (r.getInt(1)+1);
			executeUpdate(st, "INSERT INTO odpowiedzi VALUES("+a+", "+idPytania+", '"+tekst+"', '"+autor+"', '"+(poprawna?1:0)+"', 0);");
			st.close();
			return a;
		} catch (SQLException e) {
			System.out.println("Błąd dodawania odpowiedzi");
			e.printStackTrace();
		}
		return -1;
	}
	public static void usunTest(int id)
	{
		System.out.println("Zaczynam usuwanie testu id:"+id);
		Statement st = createStatement(con);
		try {
			usunPytania(id);
			executeUpdate(st, "DELETE FROM testy WHERE id = "+id+";");
			st.close();
			System.out.println("Test usunięty id:"+id);
		} catch (SQLException e) {
			System.out.println("Błąd usuwania testu");
			e.printStackTrace();
		}
	}
	public static void usunPytanie(int id)
	{
		System.out.println("Zaczynam usuwanie pytania id:"+id);
		Statement st = createStatement(con);
		try {
			usunOdpowiedzi(id);
			executeUpdate(st, "DELETE FROM pytania WHERE id = "+id+";");
			st.close();
			System.out.println("Pytanie usunięte id:"+id);
		} catch (SQLException e) {
			System.out.println("Błąd usuwania pytania");
			e.printStackTrace();
		}
	}
	public static void usunPytania(int idTestu)
	{
		System.out.println("Zaczynam usuwanie pytań z testu id:"+idTestu);
		Statement st = createStatement(con);
		Statement stm = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM pytania WHERE id_test = "+idTestu+";");
		try {
			while(r.next())
			{
				usunOdpowiedzi(r.getInt("id"));
				executeUpdate(stm, "DELETE FROM pytania WHERE id = "+r.getInt("id")+";");
			}
			st.close();
			stm.close();
			System.out.println("Pytania usunięte z testu id:"+idTestu);
		} catch (SQLException e) {
			System.out.println("Błąd usuwania pytań z testu id:"+idTestu);
			e.printStackTrace();
		}
	}
	public static void usunOdpowiedzi(int idPytania)
	{
		System.out.println("Zaczynam usuwanie odpowiedzi z pytania id:"+idPytania);
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "DELETE FROM odpowiedzi WHERE id_pytanie = "+idPytania+";");
			st.close();
			System.out.println("Odpowiedzi usunięte z pytania id:"+idPytania);
		} catch (SQLException e) {
			System.out.println("Błąd usuwania odpowiedzi z pytania id:"+idPytania);
			e.printStackTrace();
		}
	}
	public static void usunOdpowiedz(int id)
	{
		System.out.println("Zaczynam usuwanie odpowiedzi id:"+id);
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "DELETE FROM odpowiedzi WHERE id = "+id+";");
			st.close();
			System.out.println("Odpowiedź usunięta id:"+id);
		} catch (SQLException e) {
			System.out.println("Błąd usuwania odpowiedzi");
			e.printStackTrace();
		}
	}
	public static void edytujTest(Test t)
	{
		System.out.println("Zaczynam edycje testu id:"+t.getId());
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "UPDATE testy SET tytul='"+t.getTytul()+"', autor='"+t.getAutor()+"', opis='"+t.getOpis()+"' WHERE id= "+t.getId()+";");
			st.close();
			System.out.println("Test zedytowany id:"+t.getId());
		} catch (SQLException e) {
			System.out.println("Błąd edycji testu");
			e.printStackTrace();
		}
	}
	public static void edytujPytanie(Pytanie t)
	{
		System.out.println("Zaczynam edycje pytania id:"+t.getId());
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "UPDATE pytania SET tekst='"+t.getTekst()+"', autor='"+t.getAutor()+"' WHERE id= "+t.getId()+";");
			st.close();
			System.out.println("Pytanie zedytowane id:"+t.getId());
		} catch (SQLException e) {
			System.out.println("Błąd edycji pytania");
			e.printStackTrace();
		}
	}
	public static void edytujOdpowiedz(Odpowiedz t)
	{
		System.out.println("Zaczynam edycje odpowiedzi id:"+t.getId());
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "UPDATE odpowiedzi SET tekst='"+t.getTekst()+"', autor='"+t.getAutor()+"', poprawna="+(t.isPrawidlowa()?1:0)+" WHERE id= "+t.getId()+";");
			st.close();
			System.out.println("Odpowiedź zedytowane id:"+t.getId());
		} catch (SQLException e) {
			System.out.println("Błąd edycji odpowiedzi");
			e.printStackTrace();
		}
	}
	public static void dodajLicznikOdpowiedzi(int id)
	{
		System.out.println("Zaczynam edycje licznika odpowiedzi id:"+id);
		Statement st = createStatement(con);
		try {
			executeUpdate(st, "UPDATE odpowiedzi SET licznik=licznik+1 WHERE id= "+id+";");
			st.close();
			System.out.println("Licznik zedytowany id:"+id);
		} catch (SQLException e) {
			System.out.println("Błąd edycji licznika odpowiedzi");
			e.printStackTrace();
		}
	}
	public static Test pobierzTest(int id)
	{
		System.out.println("Pobieram test id:"+id);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM testy WHERE id = "+id);
		Test p = null;
		try {
			if(r.next())
				p = new Test(r.getInt("id"), r.getString("tytul"), r.getString("autor"), r.getString("opis"), pobierzPytania(r.getInt("id")));
			st.close();
			System.out.println("Test pobrany");
			return p;

		} catch (SQLException e) {
			System.out.println("Błąd pobrania testu");
			e.printStackTrace();
		}
		return p;
	}
	public static Pytanie pobierzPytanie(int id)
	{
		System.out.println("Pobieram pytanie id:"+id);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM pytania WHERE id = "+id);
		Pytanie p = null;
		try {
			if(r.next())
				p = new Pytanie(r.getInt("id"), r.getString("tekst"), r.getString("autor"), pobierzOdpowiedzi(r.getInt("id")));
			st.close();
			System.out.println("Pytanie pobrane");
			return p;

		} catch (SQLException e) {
			System.out.println("Błąd pobrania pytania");
			e.printStackTrace();
		}
		return p;
	}
	public static int pobierzUser(String nazwa)
	{
		System.out.println("Pobieram usera id:"+nazwa);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM users WHERE nazwa = '"+nazwa+"'");
		int p = 0;
		try {
			if(r.next())
			{
				p = r.getInt("id");
				st.close();
				System.out.println("Pytanie pobrane");
				return p;
			}
			else
			{
				p = dodajUser(nazwa);
				return p;
			}

		} catch (SQLException e) {
			System.out.println("Błąd pobrania pytania");
			e.printStackTrace();
		}
		return p;
	}
	public static Odpowiedz pobierzOdpowiedz(int id)
	{
		System.out.println("Pobieram odpowiedź id:"+id);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM pytania WHERE id = "+id);
		Odpowiedz p = null;
		try {
			if(r.next())
				p = new Odpowiedz(r.getInt("id"), r.getString("tekst"), r.getString("autor"), r.getInt("licznik"), r.getBoolean("poprawna"));
			st.close();
			System.out.println("Odpowiedź pobrana");
			return p;

		} catch (SQLException e) {
			System.out.println("Błąd pobrania odpowiedzi");
			e.printStackTrace();
		}
		return p;
	}
	public static ArrayList<Test> pobierzTesty()
	{
		System.out.println("Pobieram testy");
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM testy;");
		ArrayList<Test> o = new ArrayList<Test>();
		try {
			while(r.next())
				o.add(new Test(r.getInt("id"), r.getString("tytul"), r.getString("autor"), r.getString("opis"), pobierzPytania(r.getInt("id"))));
			st.close();
			System.out.println("Testy pobrane");
			return o;

		} catch (SQLException e) {
			System.out.println("Błąd pobierania testów");
			e.printStackTrace();
		}
		return o;
	}
	public static ArrayList<Pytanie> pobierzPytania(int idTestu)
	{
		System.out.println("Pobieram pytania dla testu id:"+idTestu);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM pytania WHERE id_test = "+idTestu);
		ArrayList<Pytanie> o = new ArrayList<Pytanie>();
		try {
			while(r.next())
				o.add(new Pytanie(r.getInt("id"), r.getString("tekst"), r.getString("autor"), pobierzOdpowiedzi(r.getInt("id"))));
			st.close();
			System.out.println("Pytania pobrane");
			return o;

		} catch (SQLException e) {
			System.out.println("Błąd pobrania pytań");
			e.printStackTrace();
		}
		return o;
	}
	public static ArrayList<Odpowiedz> pobierzOdpowiedzi(int idPytania)
	{
		System.out.println("Pobieram odpowiedzi dla pytania id:"+idPytania);
		Statement st = createStatement(con);
		ResultSet r = executeQuery(st, "SELECT * FROM odpowiedzi WHERE id_pytanie = "+idPytania);
		ArrayList<Odpowiedz> o = new ArrayList<Odpowiedz>();
		try {
			while(r.next())
				o.add(new Odpowiedz(r.getInt("id"), r.getString("tekst"), r.getString("autor"), r.getInt("licznik"), r.getBoolean("poprawna")));
			st.close();
			System.out.println("Odpowiedzi pobrane");
			return o;

		} catch (SQLException e) {
			System.out.println("Błąd pobrania odpowiedzi");
			e.printStackTrace();
		}
		return o;
	}
}
