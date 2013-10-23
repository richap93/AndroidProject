package rpat789.softeng206.contactsmanager;

/**
 * Sort EVENT class that sets and gets the sort order.
 * @author Richa Patel
 */
public class SortEvent {

	private String order;

	public SortEvent(String order){
		this.order = order;
	}

	public String getOrder(){
		return order;
	}
}
