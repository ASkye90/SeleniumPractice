package andrewSkye.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class for parsing JSON files into simple String Maps.
 * 
 * @author Andrew Skye
 */
public class JSONMapper {

	/**
	 * Parse a JSON file into a Map
	 * 
	 * @param	relativeFilePath	File path to JSON relative to project root directory
	 * @return Map representing JSON file
	 * @throws FileNotFoundException	Unable to find JSON file at relativeFilePath.
	 */
	public static List<HashMap<String, String>> parseJSON(String relativeFilePath) throws FileNotFoundException {
		File jsonFile = new File(System.getProperty("user.dir") + relativeFilePath);
		FileReader reader = new FileReader(jsonFile);

		Gson gson = new Gson();
		List<HashMap<String, String>> map = gson.fromJson(reader, new TypeToken<List<HashMap<String, String>>>() {
		}.getType());
		return map;
	}

}
