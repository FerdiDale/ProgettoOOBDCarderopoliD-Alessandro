import java.util.ArrayList;
import java.util.Date;

public interface CameriereDAO 
{
	public ArrayList<Cameriere> EstraiCamerieriInServizio(Ristorante ristorante);
	
	public ArrayList<Cameriere> EstraiCamerieriLicenziati(Ristorante ristorante);
	
	public void riassumiCameriereLicenziato(Cameriere c,String data);
	
	public String licenziaCameriereAssunto(Cameriere c, String data);


	
	public void assumiNuovoCameriere(Cameriere c);
}
