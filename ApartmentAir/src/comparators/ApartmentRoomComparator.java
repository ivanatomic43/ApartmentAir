package comparators;

import java.util.Comparator;

import beans.Apartment;
import enums.OrderComparator;

public class ApartmentRoomComparator implements Comparator<Apartment>{
	
	private OrderComparator order;
	
	public ApartmentRoomComparator(OrderComparator order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	
	@Override
	public int compare(Apartment a1, Apartment a2) {
		// TODO Auto-generated method stub
		if (this.order == OrderComparator.ASCENDING)
			return a1.getNumberOfRooms() - a2.getNumberOfRooms();
		return a2.getNumberOfRooms()- a1.getNumberOfRooms();
	}

}