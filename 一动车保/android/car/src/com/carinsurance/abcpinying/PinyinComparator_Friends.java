package com.carinsurance.abcpinying;

import java.util.Comparator;

public class PinyinComparator_Friends implements Comparator<MyFriendsModel> {

	@Override
	public int compare(MyFriendsModel o1, MyFriendsModel o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
