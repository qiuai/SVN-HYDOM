package com.carinsurance.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 转换类
 * @author Administrator
 *
 */
public class Convert {
	
	public static String convert(int d){
		String s=String.valueOf(d);
		String[] str={"零","一","二","三","四","五","六","七","八","九"};//可以吧“一”换成“壹”
		String ss[] = new String[]{"","十","百","千","万","十万"};//数值过大，可以继续加“万”，“十万”....
		StringBuffer sb=new StringBuffer();			
		for(int i=0,j=(s.length()-1);i<s.length();i++,j--){
			String index=String.valueOf(s.charAt(i));
			//遇到零的时候的处理，例如：405
			if(str[Integer.parseInt(index)].equals("零")){
				if(i==(s.length()-1)){
					continue;
				}
				sb=sb.append(str[Integer.parseInt(index)]);
			}else{
				sb=sb.append(str[Integer.parseInt(index)]+ss[j]);
			}
		}
		String numble = sb.toString();
		//清除sb中的字符值
		sb.delete(0, sb.length());
		return numble;
	}
	
	/**
	 * map转换成list
	 * 
	 * @param map
	 * @return
	 */
	public static List mapTransitionList(Map map) {
		List list = new ArrayList();
		Iterator iter = map.entrySet().iterator(); // 获得map的Iterator
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			list.add(entry.getValue());
		}
		return list;
	}
	
   
	/**
	 * list转化为string
	 * @param list
	 * @return
	 */
	public static String[] ListTransitionString(List<String> list)
	{
		String[] arr = list.toArray(new String[list.size()]);
		return arr;
	}
			
	
	// 1.list转set
		// Set set = new HashSet(new ArrayList());
		// 2.set转list
		// List list = new ArrayList(new HashSet());
		// 3.数组转为list
		// List stooges = Arrays.asList("Larry", "Moe", "Curly");
		// 或者
		// String[] arr = {"1", "2"};
		// List list = Arrays.asList(arr);
		// 4.数组转为set
		// int[] a = { 1, 2, 3 };
		// Set set = new HashSet(Arrays.asList(a));
		// 5.map的相关操作。
		// Map map = new HashMap();
		// map.put("1", "a");
		// map.put('2', 'b');
		// map.put('3', 'c');
		// System.out.println(map);
		// // 输出所有的值
		// System.out.println(map.keySet());
		// // 输出所有的键
		// System.out.println(map.values());
		// // 将map的值转化为List
		// List list = new ArrayList(map.values());
		// System.out.println(list);
		// // 将map的值转化为Set
		// Set set = new HashSet(map.values());
		// System.out.println(set);
		// 6.list转数组
		// List list = Arrays.asList("a","b");
	
	   
		// System.out.println(Arrays.toString(arr));
}
