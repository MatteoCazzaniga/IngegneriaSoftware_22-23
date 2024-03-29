import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * La classe ATM gestisce i dati di un atm.
 * 
 * @author botta
 * @version 1.4.3
 *
 */
public class atm {
	private int n_serie;
	private int n_filiale;
	private boolean carta_ok;
	private String luogo;

//////////////////////////////////////////////////////////////////////////////
//								COSTRUTTORI									//
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Costruisce l'oggetto atm a partire da un atm esistente sul DB.
	 * 
	 * @param luogo
	 */
	public atm(String luogo) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM atm WHERE Luogo='" + luogo + "'");
			if (rs.next()) {
				n_serie = rs.getInt("n_serie");
				n_filiale = rs.getInt("n_filiale");
				carta_ok = rs.getBoolean("carta_ok");
				setLuogo(rs.getString("Luogo"));
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Costruisce un oggetto atm e lo aggiunge al DB.
	 * 
	 * @param n_serie
	 * @param n_filiale
	 * @param carta_ok
	 * @param luogo
	 */
	public atm(int n_serie, int n_filiale, Boolean carta_ok, String luogo) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"INSERT INTO atm VALUES('" + n_serie + "','" + n_filiale + "','" + carta_ok + "','" + luogo + "')");
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "ATM CREATO CON SUCCESSO!");
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

//////////////////////////////////////////////////////////////////////////////
//									GET/SET									//
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Il metodo ritorna il numero di serie dell' atm.
	 * 
	 * @return numero di serie
	 */
	public int getN_serie() {
		return n_serie;
	}

	/**
	 * Il metodo imposta il numero di serie dell'oggetto.
	 * 
	 * @param n_serie
	 */
	public void setN_serie(int n_serie) {
		this.n_serie = n_serie;
	}

	/**
	 * Il metodo ritorna il numero di filiale.
	 * 
	 * @return numero filiale
	 */
	public int getN_filiale() {
		return n_filiale;
	}

	/**
	 * Il metodo imposta il numero di filiale dell'oggetto.
	 * 
	 * @param n_filiale
	 */
	public void setN_filiale(int n_filiale) {
		this.n_filiale = n_filiale;
	}

	/**
	 * Il metodo ritorna il luogo dell'oggetto.
	 * 
	 * @return luogo
	 */
	public String getLuogo() {
		return luogo;
	}

	/**
	 * Il metodo imposta il luogo dell'oggetto.
	 * 
	 * @param luogo
	 */
	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	/**
	 * Il metodo ritorna true se la carta � ok.
	 * 
	 * @return true o false
	 */
	public boolean isCarta_ok() {
		return carta_ok;
	}

	/**
	 * Il metodo imposta la carta ok.
	 * 
	 * @param carta_ok
	 */
	public void setCarta_ok(boolean carta_ok) {
		this.carta_ok = carta_ok;
	}

//////////////////////////////////////////////////////////////////////////////
//								FUNZIONI									//
//////////////////////////////////////////////////////////////////////////////

	// public void richiedi_carta() {
	// // prompt user to insert card
	// }
	//
	// public void richiedi_PIN() {
	// // prompt user to enter PIN
	// }
	//
	// public void verifica_carta() {
	// // check if card is valid
	// }
	//
	// public void segnala_transazione() {
	// // log transaction details
	// }
	//
	// public void verifica_limite_conto() {
	// // check if account has sufficient funds
	// }
	//
	// public void eroga_transazione() {
	// // perform transaction (e.g. withdraw cash)
	// }
	//
	// public void controlla_rimanenza_carta() {
	// // check remaining balance on card
	// }
	//
	// public void eroga_ricevuta() {
	// // print receipt
	// }
	//
	// public void richiesta_rilascio_carta() {
	// // prompt user to remove card
	// }
	//
	// public void rilascia_carta() {
	// // release card
	// }
	//
	// public void visualizza_transazioni() {
	// // display transaction history
	// }
	//
	// public void connetti_conto() {
	// // connect to account (e.g. fetch account details from database)
	// }
}
