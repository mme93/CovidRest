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

public class RKI {
	List<County> countyList = new ArrayList<>();

	public List<County> getRKICovidInfo() {
		try {
			URL url = new URL(
					"https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=county,cases7_per_100k,recovered,cases7_bl_per_100k,cases7_per_100k_txt,cases_per_100k,BEZ&returnGeometry=false&outSR=4326&f=json");
			JSONObject germanListJson = new JSONObject(IOUtils.toString(url, Charset.forName("UTF-8")));
			JSONArray countryJsonArray = germanListJson.names();
			for (Object obj : countryJsonArray) {
				if (obj.toString().equals("features")) {
					JSONArray countyArray = germanListJson.getJSONArray("features");
					for (int i = 0; i < countyArray.length(); i++) {
						countyList.add(getCounty(countyArray.getJSONObject(i).getJSONObject("attributes")));
					}
				}
			}

		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return countyList;

	}

	private County getCounty(JSONObject countAttributesJsonObject) {
		try {
			County county = new County();
			county.setName(countAttributesJsonObject.getString("county"));
			county.setrNumber(Double.valueOf(countAttributesJsonObject.getDouble("cases7_per_100k")));
			return county;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}