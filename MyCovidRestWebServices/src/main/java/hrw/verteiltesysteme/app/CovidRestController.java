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

	private void loadCovidInfo() {
		calculateCovidNumber.setCountyList(new RKI().getRKICovidInfo());
		calculateCovidNumber.setGermanyInfoJHU(new JHU().getJHUCovidInfo());
	}

	@GetMapping("/allInfos/{rWerth}/{day}")
	public String getAllInfos(@PathVariable("rWerth") int rWerth, @PathVariable("day") int day) {
		loadCovidInfo();
		JSONObject covindInfoJsonObj = new JSONObject();
		covindInfoJsonObj.put("Date", calculateCovidNumber.getGermanyInfoJHU()
				.get(calculateCovidNumber.getGermanyInfoJHU().size() - 1).getDate()).toString();

		covindInfoJsonObj.put("rGermany", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();

		covindInfoJsonObj.put("totalTargetInfection", calculateCovidNumber.getTotalTargetInfectionRKI(rWerth)).toString();

		covindInfoJsonObj.put("getTargetIncidencForR", calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,
				calculateCovidNumber.getTotalTargetInfectionRKI(50), 7)).toString();

		covindInfoJsonObj.put("getAverageIncrease", calculateCovidNumber.getAverageIncreaseDayJHU(day)).toString();

		covindInfoJsonObj.put("increaseLastDay", calculateCovidNumber.getIncreaseLasteDayJHU()).toString();

		covindInfoJsonObj.put("totalInfection", calculateCovidNumber.getTotalInfectionsJHU()).toString();

		covindInfoJsonObj.put("newInfectionLastDay", calculateCovidNumber.getNewInfectionsLastDayJHU()).toString();

		return covindInfoJsonObj.toString();
	}

	@GetMapping("/date")
	public String getDate() {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getGermanyInfoJHU()
				.get(calculateCovidNumber.getGermanyInfoJHU().size() - 1).getDate()).toString();
	}

	// RKI Anforderungen
	@GetMapping("/rWerthTotalGermany")
	public String getRWerthTotalGermany() {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();
	}

	@GetMapping("/totalTargetInfection/{rWerth}")
	public String getTotalTargetInfection(@PathVariable("rWerth") int rWerth) {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getTotalTargetInfectionRKI(rWerth)).toString();
	}

	@GetMapping("/targetIncidenceForRWert/{rWerth}/{day}")
	public String getTargetIncidenceForRWert(@PathVariable("rWerth") int rWerth, @PathVariable("day") int day) {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,
				calculateCovidNumber.getTotalTargetInfectionRKI(50), 7)).toString();
	}

	// JHU Anforderungen
	@GetMapping("/averageIncrease/{day}")
	public String getAverageIncreaseDay(@PathVariable("day") int day) {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getAverageIncreaseDayJHU(day)).toString();
	}

	@GetMapping("/percentInfection")
	public String getPercenteInfection() {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getIncreaseLasteDayJHU()).toString();
	}

	@GetMapping("/totalInfection")
	public String getTotalInfection() {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getTotalInfectionsJHU()).toString();
	}

	@GetMapping("/newInfection24")
	public String getNewInfection() {
		loadCovidInfo();
		return new JSONObject().put("Wert", calculateCovidNumber.getNewInfectionsLastDayJHU()).toString();
	}

}
