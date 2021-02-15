package hrw.verteiltesysteme.covid;

import java.util.List;

public class CalculateCovidNumber {
	private List<JhuDataSet> germanyInfoJHU;
	private List<County> countyList;

	public CalculateCovidNumber(List<County> countyList, List<JhuDataSet> germanyInfoJHU) {
		this.countyList = countyList;
		this.germanyInfoJHU = germanyInfoJHU;
	}

	public int getNewInfectionsLastDayJHU() {
		JhuDataSet lastday = germanyInfoJHU.get(germanyInfoJHU.size() - 1);
		JhuDataSet dayBeforeLastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 2);
		return lastday.getConfirmed() - dayBeforeLastDay.getConfirmed();
	}

	public int getTotalInfectionsJHU() {
		JhuDataSet lastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 1);
		return lastDay.getConfirmed() - lastDay.getDeaths() - lastDay.getRecovered();
	}

	public double getIncreaseLasteDayJHU() {
		JhuDataSet lastday = germanyInfoJHU.get(germanyInfoJHU.size() - 1);
		JhuDataSet dayBeforeLastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 2);
		return 100 - (100 / new Double(lastday.getConfirmed()) * new Double(dayBeforeLastDay.getConfirmed()));
	}

	public int getAverageIncreaseDayJHU(int days) {
		int totalDays = 0;
		for (int i = 0; i < days; i++) {
			totalDays = totalDays + getNewInfectionAtThisDay(i);
		}
		return totalDays / days;
	}

	private int getNewInfectionAtThisDay(int dayIndex) {
		JhuDataSet lastday = germanyInfoJHU.get(germanyInfoJHU.size() - 1 - dayIndex);
		JhuDataSet dayBeforeLastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 2 - dayIndex);
		return lastday.getConfirmed() - dayBeforeLastDay.getConfirmed();
	}

	public double getRWerthTotalGermanyRKI() {
		double totalRWerth = 0;
		for (County county : countyList) {
			totalRWerth = totalRWerth + county.getrNumber();
		}
		return totalRWerth / countyList.size();
	}

	public double getTotalTargetInfectionRKI(double rZiel) {
		double aktuellInfiziertePersonen = getNewInfectionsLastDayJHU();
		double rWerth = getRWerthTotalGermanyRKI();
		return (aktuellInfiziertePersonen / rWerth) * rZiel;
	}

	public Double getTargetIncidenceForRWerthRKI(double rZiel, double totalTargetInfection, int nDay) {
		JhuDataSet lastday = germanyInfoJHU.get(germanyInfoJHU.size() - 1);
		JhuDataSet dayBeforeLastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 2);

		double firstNumber = lastday.getConfirmed() - dayBeforeLastDay.getConfirmed();
		double gesamtAktuell = getTotalInfectionsJHU();
		double gesamtZiel = getTotalTargetInfectionRKI(50);
		double tage = (firstNumber - gesamtZiel) / getNDay(7);
		return tage;
	}

	private double getNDay(int nDay) {
		int dayWert = 0;
		for (int i = 0; i < nDay; i++) {
			JhuDataSet lastday = germanyInfoJHU.get(germanyInfoJHU.size() - 1 - i);
			JhuDataSet dayBeforeLastDay = germanyInfoJHU.get(germanyInfoJHU.size() - 2 - i);

			int firstNumber = lastday.getConfirmed() - dayBeforeLastDay.getConfirmed();

			JhuDataSet lastday2 = germanyInfoJHU.get(germanyInfoJHU.size() - 2 - i);
			JhuDataSet dayBeforeLastDay2 = germanyInfoJHU.get(germanyInfoJHU.size() - 3 - i);

			int secondNumber = lastday2.getConfirmed() - dayBeforeLastDay2.getConfirmed();
			int dif = firstNumber - secondNumber;
			dayWert = dayWert + dif;
		}
		if (dayWert < 0) {
			dayWert = dayWert * -1;
		}
		return dayWert / nDay;
	}

	public List<JhuDataSet> getGermanyInfoJHU() {
		return germanyInfoJHU;
	}

	public void setGermanyInfoJHU(List<JhuDataSet> germanyInfoJHU) {
		this.germanyInfoJHU = germanyInfoJHU;
	}

	public List<County> getCountyList() {
		return countyList;
	}

	public void setCountyList(List<County> countyList) {
		this.countyList = countyList;
	}

}
