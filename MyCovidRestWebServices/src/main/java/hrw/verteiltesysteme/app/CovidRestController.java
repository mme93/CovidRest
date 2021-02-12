package hrw.verteiltesysteme.app;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hrw.verteiltesysteme.covid.CalculateCovidNumber;
import hrw.verteiltesysteme.covid.JHU;
import hrw.verteiltesysteme.covid.RKI;

@RestController
public class CovidRestController {
	
	private CalculateCovidNumber calculateCovidNumber;
	
	public CovidRestController() {
		this.calculateCovidNumber = new CalculateCovidNumber(new RKI().getRKICovidInfo(), new JHU().getJHUCovidInfo());
	}
	//RKI Anforderungen
	@GetMapping("/rWerthTotalGermany")
	public String getRWerthTotalGermany() {
		System.out.println("R-Wert für ganz deutschland: "+calculateCovidNumber.getRWerthTotalGermanyRKI());
		return "R-Wert für ganz deutschland: "+calculateCovidNumber.getRWerthTotalGermanyRKI();
	}
	@GetMapping("/totalTargetInfection/{rWerth}")
	public String getTotalTargetInfection(@PathVariable("rWerth") int rWerth) {
		System.out.println("Ziel-Gesamtinfektion: "+calculateCovidNumber.getTotalTargetInfectionRKI(rWerth));
		return "Ziel-Gesamtinfektion: "+calculateCovidNumber.getTotalTargetInfectionRKI(rWerth);
	}
	
	@GetMapping("/targetIncidenceForRWert/{rWerth}/{day}")
	public String getTargetIncidenceForRWert(@PathVariable("rWerth") int rWerth,@PathVariable("day") int day) {
		System.out.println("Durchschnittlicher dauer in Tagen bis Wert erreicht 50: "+calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,calculateCovidNumber.getTotalTargetInfectionRKI(50),7));
		return "Durchschnittlicher dauer in Tagen bis Wert erreicht 50: "+calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,calculateCovidNumber.getTotalTargetInfectionRKI(50),7);
	}
	
	//JHU Anforderungen
	@GetMapping("/averageIncrease/{day}")
	public String getAverageIncreaseDay(@PathVariable("day") int day) {
		System.out.println("Durchschnittlicher Anstieg in den letzten "+day+" Tagen: "+calculateCovidNumber.getAverageIncreaseDayJHU(day));
		return "Durchschnittlicher Anstieg in den letzten "+day+" Tagen: "+calculateCovidNumber.getAverageIncreaseDayJHU(day);
	}
	@GetMapping("/percentInfection")
	public String getPercenteInfection() {
		System.out.println("Anstieg in 24h in %: "+calculateCovidNumber.getIncreaseLasteDayJHU()+"%");
		return "Anstieg in 24h in %: "+calculateCovidNumber.getIncreaseLasteDayJHU()+"%";
	}
	
	@GetMapping("/totalInfection")
	public String getTotalInfection() {
		System.out.println("Gesamtinfektionen: "+calculateCovidNumber.getTotalInfectionsJHU());
		return "Gesamtinfektionen: "+calculateCovidNumber.getTotalInfectionsJHU();
	}	
	
	
	@GetMapping("/newInfection24")
	public String getNewInfection() {
		System.out.println("Neueinfektionen: "+calculateCovidNumber.getNewInfectionsLastDayJHU());
		return "Neueinfektionen: "+calculateCovidNumber.getNewInfectionsLastDayJHU();
	}	
	
	
	@GetMapping("/rWerth")
	public String getWerth() {
		System.out.println("R-Wert für ganz deutschland: "+calculateCovidNumber.getRWerthTotalGermanyRKI());
		return "R-Wert für ganz deutschland: "+calculateCovidNumber.getRWerthTotalGermanyRKI();
	}

}
