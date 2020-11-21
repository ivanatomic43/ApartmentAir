package comparators;

import java.util.Comparator;

import beans.Apartment;
import enums.OrderComparator;

public class ApartmentPriceComparator implements Comparator<Apartment> {

	private OrderComparator order;
	
	public ApartmentPriceComparator(OrderComparator order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	@Override
	public int compare(Apartment a1, Apartment a2) {
		// TODO Auto-generated method stub
		Double diffAsc = a1.getPricePerNight() - a2.getPricePerNight();
		Double diffDesc = a2.getPricePerNight() - a1.getPricePerNight();
		if (this.order == OrderComparator.ASCENDING)
			return diffAsc.intValue();
		return diffDesc.intValue();
	}
	
	
	
}
