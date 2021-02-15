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
		covindInfoJsonObj.put("date", calculateCovidNumber.getGermanyInfoJHU()
				.get(calculateCovidNumber.getGermanyInfoJHU().size() - 1).getDate()).toString();

		covindInfoJsonObj.put("rWerthTotalGermany", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();

		covindInfoJsonObj.put("totalTargetInfection", calculateCovidNumber.getTotalTargetInfectionRKI(rWerth))
				.toString();

		covindInfoJsonObj.put("targetIncidenceForRWert", calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,
				calculateCovidNumber.getTotalTargetInfectionRKI(50), 7)).toString();

		covindInfoJsonObj.put("averageIncrease", calculateCovidNumber.getAverageIncreaseDayJHU(day)).toString();

		covindInfoJsonObj.put("percentInfection", calculateCovidNumber.getIncreaseLasteDayJHU()).toString();

		covindInfoJsonObj.put("totalInfection", calculateCovidNumber.getTotalInfectionsJHU()).toString();

		covindInfoJsonObj.put("newInfection24", calculateCovidNumber.getNewInfectionsLastDayJHU()).toString();

		return covindInfoJsonObj.toString();
	}

	@GetMapping("/date")
	public String getDate() {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getGermanyInfoJHU()
				.get(calculateCovidNumber.getGermanyInfoJHU().size() - 1).getDate()).toString();
		obj.put("info", "date").toString();
		return obj.toString();
	}

	// RKI Anforderungen
	@GetMapping("/rWerthTotalGermany")
	public String getRWerthTotalGermany() {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getRWerthTotalGermanyRKI()).toString();
		obj.put("info", "rWerthTotalGermany").toString();
		return obj.toString();

	}

	@GetMapping("/totalTargetInfection/{rWerth}")
	public String getTotalTargetInfection(@PathVariable("rWerth") int rWerth) {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getTotalTargetInfectionRKI(rWerth)).toString();
		obj.put("info", "totalTargetInfection/" + rWerth).toString();
		return obj.toString();
	}

	@GetMapping("/targetIncidenceForRWert/{rWerth}/{day}")
	public String getTargetIncidenceForRWert(@PathVariable("rWerth") int rWerth, @PathVariable("day") int day) {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getTargetIncidenceForRWerthRKI(50,
				calculateCovidNumber.getTotalTargetInfectionRKI(50), 7)).toString();
		obj.put("info", "targetIncidenceForRWert/" + rWerth + "/" + day).toString();
		return obj.toString();
	}

	// JHU Anforderungen
	@GetMapping("/averageIncrease/{day}")
	public String getAverageIncreaseDay(@PathVariable("day") int day) {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getAverageIncreaseDayJHU(day)).toString();
		obj.put("info", "averageIncrease/" + day).toString();
		return obj.toString();
	}

	@GetMapping("/percentInfection")
	public String getPercenteInfection() {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getIncreaseLasteDayJHU()).toString();
		obj.put("info", "percentInfection").toString();
		return obj.toString();
	}

	@GetMapping("/totalInfection")
	public String getTotalInfection() {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getTotalInfectionsJHU()).toString();
		obj.put("info", "totalInfection").toString();
		return obj.toString();
	}

	@GetMapping("/newInfection24")
	public String getNewInfection() {
		loadCovidInfo();
		JSONObject obj = new JSONObject();
		obj.put("value", calculateCovidNumber.getNewInfectionsLastDayJHU()).toString();
		obj.put("info", "newInfection24").toString();
		return obj.toString();
	}

}
