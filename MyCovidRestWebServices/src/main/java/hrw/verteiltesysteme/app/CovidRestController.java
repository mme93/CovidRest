package hrw.verteiltesysteme.app;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import hrw.verteiltesysteme.covid.CalculateCovidNumber;
import hrw.verteiltesysteme.covid.JHU;
import hrw.verteiltesysteme.covid.RKI;

@RestController
public class CovidRestController {

	private CalculateCovidNumber calculateCovidNumber;

	public CovidRestController() {
		this.calculateCovidNumber = new CalculateCovidNumber(new RKI().getRKICovidInfo(), new JHU().getJHUCovidInfo());
	}

	// RKI Anforderungen
	@GetMapping("/rWerthTotalGermany")
	public String getRWerthTotalGermany() {
		return new JSONObject().put("Wert", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();
	}

	@GetMapping("/totalTargetInfection/{rWerth}")
	public String getTotalTargetInfection(@PathVariable("rWerth") int rWerth) {
		return new JSONObject().put("Wert", calculateCovidNumber.getTotalTargetInfectionRKI(rWerth)).toString();
	}

	@GetMapping("/targetIncidenceForRWert/{rWerth}/{day}")
	public String getTargetIncidenceForRWert(@PathVariable("rWerth") int rWerth, @PathVariable("day") int day) {
		return new JSONObject().put("Wert", calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,
				calculateCovidNumber.getTotalTargetInfectionRKI(50), 7)).toString();
	}

	// JHU Anforderungen
	@GetMapping("/averageIncrease/{day}")
	public String getAverageIncreaseDay(@PathVariable("day") int day) {
		return new JSONObject().put("Wert", calculateCovidNumber.getAverageIncreaseDayJHU(day)).toString();
	}

	@GetMapping("/percentInfection")
	public String getPercenteInfection() {
		return new JSONObject().put("Wert", calculateCovidNumber.getIncreaseLasteDayJHU()).toString();
	}

	@GetMapping("/totalInfection")
	public String getTotalInfection() {
		return new JSONObject().put("Wert", calculateCovidNumber.getTotalInfectionsJHU()).toString();
	}

	@GetMapping("/newInfection24")
	public String getNewInfection() {
		return new JSONObject().put("Wert", calculateCovidNumber.getNewInfectionsLastDayJHU()).toString();
	}

	@GetMapping("/rWerth")
	public String getWerth() {
		return new JSONObject().put("Wert", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();
	}

}
