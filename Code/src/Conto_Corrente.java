import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * La classe Conto_Corrente gestisce il contocorrente.
 * 
 * @author botta
 * @version 1.4.3
 */
public class Conto_Corrente {
	private int n_conto;
	private Boolean importo_minimo;
	private Double bilancio;

//////////////////////////////////////////////////////////////////////////////
//								COSTRUTTORI									//
//////////////////////////////////////////////////////////////////////////////	

	/**
	 * Costruisce l'oggetto Conto_Corrente a partire da un Contocorrente esistente
	 * sul DB.
	 * 
	 * @param n_conto
	 */
	public Conto_Corrente(int n_conto) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM conto_corrente where n_conto ='" + n_conto + "'");
			if (rs.next()) {
				this.n_conto = n_conto;
				importo_minimo = rs.getBoolean("importo_minimo");
				setBilancio(rs.getDouble("bilancio"));
			}
			con.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Costruisce l'oggetto ContoCorrente e lo aggiunge al DB.
	 */
	public Conto_Corrente() {
		// genero N_conto per utente di 5 int
		Random rand = new Random();
		for (int i = 0; i < 5; i++) {
			n_conto = rand.nextInt(999999);
		}
		importo_minimo = false;
		bilancio = 0.0;
		// carico nel DB
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"INSERT into conto_corrente VALUES('" + n_conto + "','" + importo_minimo + "','" + bilancio + "')");
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "CONTO CORRENTE CREATO CORRETTAMENTE!");
			}
			con.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

//////////////////////////////////////////////////////////////////////////////
//									GET/SET									//
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Il metodo ritorna il numero di conto del contocorrente.
	 * 
	 * @return numero di conto
	 */
	public int getN_conto() {
		return n_conto;
	}

	/**
	 * Il metodo ritorna vero se c'� un importo minimo al contocorrente.
	 * 
	 * @return importo minimo (0/1)
	 */
	public Boolean getImporto_minimo() {
		return importo_minimo;
	}

	/**
	 * Il metodo ritorna il bilancio del contocorrente.
	 * 
	 * @return bilancio
	 */
	public Double getBilancio() {
		return bilancio;
	}

	/**
	 * Il metodo imposta il bilancio del contocorrente.
	 * 
	 * @param bilancio
	 */
	public void setBilancio(Double bilancio) {
		this.bilancio = bilancio;
	}

	/**
	 * Il metodo imposta il numero del conto del contocorrente.
	 * 
	 * @param n_conto
	 */
	public void setN_conto(int n_conto) {
		this.n_conto = n_conto;
	}

	/**
	 * Il metodo imposta l'importo minimo del contocorrente.
	 * 
	 * @param importo_minimo
	 */
	public void setImporto_minimo(Boolean importo_minimo) {
		this.importo_minimo = importo_minimo;
	}

//////////////////////////////////////////////////////////////////////////////
//								FUNZIONI									//
//////////////////////////////////////////////////////////////////////////////

	/**
	 * Il metodo serve a effettuare un prelievo da un contocorrente e aggiorna il
	 * DB.
	 * 
	 * @param qtaprelievo
	 * @param conto
	 */
	public void PrelevaContante(int qtaprelievo, int conto) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			statement = connection
					.prepareStatement("UPDATE conto_corrente SET bilancio = bilancio - ? WHERE n_conto = ?");

			statement.setDouble(1, qtaprelievo);
			statement.setInt(2, conto);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				bilancio -= qtaprelievo;
				JOptionPane.showMessageDialog(null, "USCITE: " + qtaprelievo + "�; NUOVO SALDO: " + bilancio + "�");
			} else {
				JOptionPane.showMessageDialog(null, "ERRORE. Verifica il numero del Conto.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Il metodo serve a effettuare un deposito a un contocorrente e aggiorna il DB.
	 * 
	 * @param qtadeposito
	 * @param conto
	 */
	public void depositaContante(int qtadeposito, int conto) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			statement = connection
					.prepareStatement("UPDATE conto_corrente SET bilancio = bilancio + ? WHERE n_conto = ?");

			statement.setDouble(1, qtadeposito);
			statement.setInt(2, conto);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				bilancio += qtadeposito;
			} else {
				JOptionPane.showMessageDialog(null, "ERRORE. Verifica il numero del Conto.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Il metodo cerca se esiste un contocorrente dato un numero di conto.
	 * 
	 * @param n_conto
	 * @return true o false
	 */
	public boolean esisteCC(int n_conto) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM conto_corrente where n_conto ='" + n_conto + "'");
			if (rs.next()) {
				return true;
			}
			con.close();
			stmt.close();
			rs.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return false;
	}

//	public void scrivi_importo_transazione() {
//		// da implementare
//	}
}