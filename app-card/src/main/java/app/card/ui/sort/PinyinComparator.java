package app.card.ui.sort;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel>
{

	public int compare(SortModel o1, SortModel o2) {
		if (o1.letters.equals("@")
				|| o2.letters.equals("#")) {
			return -1;
		} else if (o1.letters.equals("#")
				|| o2.letters.equals("@")) {
			return 1;
		} else {
			return o1.letters.compareTo(o2.letters);
		}
	}

}
