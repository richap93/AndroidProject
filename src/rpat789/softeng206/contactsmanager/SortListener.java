package rpat789.softeng206.contactsmanager;

/**
 * Interface for responding to a sortevent. 
 * Classes implementing this interface must have an implemention of this
 * method that refreshes screens according to the sort order changed
 * @author Richa Patel
 *
 */
public interface SortListener {
	
	public void OrderChanged(SortEvent se);

}
