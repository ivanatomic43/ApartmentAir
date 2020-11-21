package comparators;

import java.util.Comparator;

import beans.Apartment;
import enums.OrderComparator;

public class ApartmentGuestComparator implements Comparator<Apartment>{

	private OrderComparator order;
	
	public ApartmentGuestComparator(OrderComparator order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	
	@Override
	public int compare(Apartment a1, Apartment a2) {
		// TODO Auto-generated method stub
		if (this.order == OrderComparator.ASCENDING)
			return a1.getNumberOfGuests() - a2.getNumberOfGuests();
		return a2.getNumberOfGuests() - a1.getNumberOfGuests();
	}
}