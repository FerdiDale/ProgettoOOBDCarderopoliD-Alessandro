
public class NumeroAvventoriGiorno {

	private Integer NumAvventori;
	private Integer Giorno;
	private String MeseRiferimento;
	private Integer AnnoRiferimento;
	
	NumeroAvventoriGiorno(Integer numeroAvventoriCorrente, Integer giornoCorrente, Integer meseIntero, Integer annoCorrente){
		
		this.NumAvventori = numeroAvventoriCorrente;
		this.Giorno = giornoCorrente;
		
		switch(meseIntero) {
		case 0: this.MeseRiferimento = "Gennaio";
		case 1: this.MeseRiferimento = "Febbraio";
		case 2: this.MeseRiferimento = "Marzo";
		case 3: this.MeseRiferimento = "Aprile";
		case 4: this.MeseRiferimento = "Maggio";
		case 5: this.MeseRiferimento = "Giugno";
		case 6: this.MeseRiferimento = "Luglio";
		case 7: this.MeseRiferimento = "Agosto";
		case 8: this.MeseRiferimento = "Settembre";
		case 9: this.MeseRiferimento = "Ottobre";
		case 10: this.MeseRiferimento = "Novembre";
		case 11: this.MeseRiferimento = "Dicembre";
		default: this.MeseRiferimento = null;
		}
		
		this.AnnoRiferimento = annoCorrente;
	}
}
