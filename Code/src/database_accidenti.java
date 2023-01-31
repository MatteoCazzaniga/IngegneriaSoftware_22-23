import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class database_accidenti {
	private int n_carta;
	private String tipo_accidente;

//////////////////////////////////////////////////////////////////////////////
//								COSTRUTTORI									//
//////////////////////////////////////////////////////////////////////////////

	// costruttore esistente sul db
	public database_accidenti(int tesserasospetta) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM database_accidenti WHERE n_carta='" + tesserasospetta + "'");
			if (rs.next()) {
				setN_carta(rs.getInt("n_carta"));
				setTipo_accidente(rs.getString("tipo_accidente"));
			}
			con.close();
			rs.close();
			stmt.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	// costruttore nuova tessera accidentata
	public database_accidenti(String n_carta, String tipo_accidente) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "admin", "admin");
			String query = "INSERT INTO database_accidenti (n_carta, tipo_accidente) VALUES (?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(n_carta));
			pstmt.setString(2, tipo_accidente);
			@SuppressWarnings("unused")
			int rowsInserted = pstmt.executeUpdate();
			con.close();
			pstmt.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

//////////////////////////////////////////////////////////////////////////////
//									GET/SET									//
//////////////////////////////////////////////////////////////////////////////

	// getters
	public int getN_carta() {
		return n_carta;
	}

	public String getTipo_accidente() {
		return tipo_accidente;
	}

	// setters
	public void setN_carta(int n_carta) {
		this.n_carta = n_carta;
	}

	public void setTipo_accidente(String tipo_accidente) {
		this.tipo_accidente = tipo_accidente;
	}

//////////////////////////////////////////////////////////////////////////////
//								FUNZIONI									//
//////////////////////////////////////////////////////////////////////////////

	public boolean cartaaccidentata(int n_carta) {
		database_accidenti db = new database_accidenti(n_carta);
		if (db.tipo_accidente != null) {
			return true; // ritorna true se accidentata
		} else {
			return false; // ritorna false se tessera valida
		}
	}
}
