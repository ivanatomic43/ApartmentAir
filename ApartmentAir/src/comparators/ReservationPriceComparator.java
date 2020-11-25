package comparators;

import java.util.Comparator;


import dto.ReservationDTO;
import enums.OrderComparator;

public class ReservationPriceComparator implements Comparator<ReservationDTO> {

	private OrderComparator order;
	
	public  ReservationPriceComparator(OrderComparator order) {
		// TODO Auto-generated constructor stub
		this.order = order;
	}
	@Override
	public int compare(ReservationDTO r1, ReservationDTO r2) {
		// TODO Auto-generated method stub
		Double diffAsc = r1.getTotalPrice() - r2.getTotalPrice();
		Double diffDesc = r2.getTotalPrice() - r1.getTotalPrice();
		if (this.order == OrderComparator.ASCENDING)
			return diffAsc.intValue();
		return diffDesc.intValue();
	}
	
	
	
}
