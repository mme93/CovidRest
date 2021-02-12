package hrw.verteiltesysteme.covid;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JHU {
	public List<JhuDataSet> getJHUCovidInfo() {
		List<JhuDataSet>germanyInfoJHU= new ArrayList<>();
		try {
			JSONObject worldListJson = new JSONObject(IOUtils
					.toString(new URL("https://pomber.github.io/covid19/timeseries.json"), Charset.forName("UTF-8")));
			JSONArray countryJsonArray = worldListJson.names();
			for (Object country : countryJsonArray) {
				if (country.toString().equals("Germany")) {
					JSONArray germanyArray=worldListJson.getJSONArray(country.toString());			
					for(Object dayInfo:germanyArray) {
						germanyInfoJHU.add(getJhuDataSet(dayInfo.toString().substring(1, dayInfo.toString().length()-1)));
					}
				}
			}
			return germanyInfoJHU;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private JhuDataSet getJhuDataSet(String dayInfo) {
		JhuDataSet jhuDataSet = new JhuDataSet();
		String[]infoArry=dayInfo.split(",");
		for(String info:infoArry) {
			String [] wert=info.split(":");
			if(wert[0].substring(1, wert[0].toString().length()-1).equals("date")) {
				jhuDataSet.setDate(wert[1].substring(1, wert[1].toString().length()-1));
			}else if(wert[0].substring(1, wert[0].toString().length()-1).equals("deaths")) {
				jhuDataSet.setDeaths(Integer.valueOf(wert[1]));
			}else if(wert[0].substring(1, wert[0].toString().length()-1).equals("recovered")) {
				jhuDataSet.setRecovered(Integer.valueOf(wert[1]));
			}else if(wert[0].substring(1, wert[0].toString().length()-1).equals("confirmed")) {
				jhuDataSet.setConfirmed(Integer.valueOf(wert[1]));
			}
		}
		return jhuDataSet;
	}

}
