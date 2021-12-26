
public class NumeroAvventoriMese {
	
	private Integer NumAvventori;
	private String Mese;
	private Integer Anno;
	
	NumeroAvventoriMese(Integer numeroAvventoriCorrente, Integer meseIntero, Integer annoCorrente){
		
		this.NumAvventori = numeroAvventoriCorrente;
		
		switch(meseIntero) {
		case 1: this.Mese = "Gennaio"; 
			break;
		case 2: this.Mese = "Febbraio";
			break;
		case 3: this.Mese = "Marzo";
			break;
		case 4: this.Mese = "Aprile";
			break;
		case 5: this.Mese = "Maggio";
			break;
		case 6: this.Mese = "Giugno";
			break;
		case 7: this.Mese = "Luglio";
			break;
		case 8: this.Mese = "Agosto";
			break;
		case 9: this.Mese = "Settembre";
			break;
		case 10: this.Mese = "Ottobre";
			break;
		case 11: this.Mese = "Novembre";
			break;
		case 12: this.Mese = "Dicembre";
			break;
		default: this.Mese = null;
		}
		
		this.Anno = annoCorrente;
	}
	
	public Integer getNumAvventori() {
		return NumAvventori;
	}

	public void setNumAvventori(Integer numAvventori) {
		NumAvventori = numAvventori;
	}

	public String getMese() {
		return Mese;
	}

	public void setMese(String mese) {
		Mese = mese;
	}

	public Integer getAnno() {
		return Anno;
	}

	public void setAnno(Integer anno) {
		Anno = anno;
	}
}
