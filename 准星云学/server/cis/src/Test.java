import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Test {
	// <line x1="1" y1="100" x2="20" y2="1000"
	// style="stroke:rgb(99,99,99);stroke-width:2" />

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
		System.out.println(UUID.fromString("b720660c-25cd-464e-8e7f-7f1995213ca3"));
	}

	public static byte[][] getHexSplits(String startKey, String endKey, int numRegions) {
		byte[][] splits = new byte[numRegions - 1][];
		BigInteger lowestKey = new BigInteger(startKey, 16);
		System.out.println("lk="+lowestKey);
		BigInteger highestKey = new BigInteger(endKey, 16);
		System.out.println("hk="+highestKey);
		BigInteger range = highestKey.subtract(lowestKey);
		System.out.println("range="+highestKey);
		BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
		System.out.println("regionIncrement="+regionIncrement);
		lowestKey = lowestKey.add(regionIncrement);
		for (int i = 0; i < numRegions - 1; i++) {
			BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
			byte[] b = String.format("%016x", key).getBytes();
			splits[i] = b;
		}
		return splits;
	}
	public static byte[][] getHexSplits1(String startKey, String endKey, int numRegions) {
		byte[][] splits = new byte[numRegions - 1][];
		BigInteger lowestKey = new BigInteger(startKey, 16);
		System.out.println("lk="+lowestKey);
		BigInteger highestKey = new BigInteger(endKey, 16);
		System.out.println("hk="+highestKey);
		BigInteger range = highestKey.subtract(lowestKey);
		System.out.println("range="+highestKey);
		BigInteger regionIncrement = range.divide(BigInteger.valueOf(numRegions));
		System.out.println("regionIncrement="+regionIncrement);
		lowestKey = lowestKey.add(regionIncrement);
		for (int i = 0; i < numRegions - 1; i++) {
			BigInteger key = lowestKey.add(regionIncrement.multiply(BigInteger.valueOf(i)));
			byte[] b = String.format("%016x", key).getBytes();
			splits[i] = b;
		}
		return splits;
	}

	public static String getdata() {
		return "{\"x\":234,\"y\":1346},{\"x\":232,\"y\":1347}";
	}

	public static void test() {
		String json = "{start: 0, pageCount: 2, dataList: [{\"STRPRODUCTCODE\":\"DWJ100\",\"STRVERSIONXZTEXT\":\"初始安装包\",\"STRPRODUCTNAME\":\"对外经贸查询系统\",\"STRVERSIONTYPE\":\"10\",\"STRVERSIONID\":\"V1                                      \",\"STRVERSIONCODE\":\"1.0.2.1018\",\"LINE_NUM\":1,\"STRVERSIONXZ\":\"10\",\"LIMIT\":1,\"NUMSHOWORDER\":1,\"STRVERSIONLOG\":\"修复1个bug，增加2个功能\",\"STRVERSIONTYPETEXT\":\"标准版\",\"DATRELEASEDATE\":\"2013-9-28 10:47:26\",\"STRVERSIONFY\":\"10万人民币\",\"STRSUPPORTOS\":\"Windows 2003/Windows 2008\",\"STRDELFLG\":\"0\"}"
				+ ",{\"STRPRODUCTCODE\":\"DWJ100\",\"STRVERSIONXZTEXT\":\"初始安装包\",\"STRPRODUCTNAME\":\"对外经贸查询系统\",\"STRVERSIONTYPE\":\"10\",\"STRVERSIONID\":\"V1                                      \",\"STRVERSIONCODE\":\"1.0.2.1018\",\"LINE_NUM\":1,\"STRVERSIONXZ\":\"10\",\"LIMIT\":1,\"NUMSHOWORDER\":1,\"STRVERSIONLOG\":\"修复1个bug，增加2个功能\",\"STRVERSIONTYPETEXT\":\"标准版\",\"DATRELEASEDATE\":\"2013-9-28 10:47:26\",\"STRVERSIONFY\":\"10万人民币\",\"STRSUPPORTOS\":\"Windows 2003/Windows 2008\",\"STRDELFLG\":\"0\"}"
				+ "]}";
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonArray jsonArray = jsonObject.getAsJsonArray("dataList");
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement el = jsonArray.get(i);
			Map<String, String> tmp = gson.fromJson(el, type);
			list.add(tmp);
			System.out.println(tmp.get("STRPRODUCTCODE"));
		}
		jsonObject.remove("dataList");
		jsonObject.add("dataList", parser.parse(gson.toJson(list)).getAsJsonArray());
		System.out.println(gson.toJson(jsonObject));
	}
}
