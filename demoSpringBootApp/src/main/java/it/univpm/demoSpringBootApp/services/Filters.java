package it.univpm.demoSpringBootApp.services;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.univpm.demoSpringBootApp.services.TSVReader;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import it.univpm.demoSpringBootApp.models.MigrationStatus;

/**
 * Class that define which filter use for string and numeric values.
 * The main difference is if the field is "migrants", then we expect another word for the year to analyze.
 * In this case the expectation is to compare with mathematical operators (=,>,<,>=,<=) the value of the specific year for each object.
 * Altough the filter consider string comparison between the field called and the refering attribute of the object migrant,
 * the return is the list of objects that respect the filter.
 * @author Tommaso
 *
 */

public class Filters {
	public static boolean check(Object value, String operator, Object th) {
		if (th instanceof Number && value instanceof Number) {	
			Double thC = ((Number)th).doubleValue();
			Double valuec = ((Number)value).doubleValue();
			
			if (operator.equals("=="))
				return value.equals(th);
			else if (operator.equals(">"))
				return valuec > thC;
			else if (operator.equals("<"))
				return valuec < thC;
			else if (operator.equals(">="))
				return valuec >= thC;
			else if (operator.equals("<="))
				return valuec <= thC;
			
		}else if(th instanceof String && value instanceof String)
			return value.equals(th);
		
		return false;		
	}
	
	/**
	 * Method that filters a list and returns the indexes that respect the condition.
	 * @param val, list of values to filters
	 * @param oper, mathematical operator
	 * @param rif, value of reference
	 * @return
	 */
	
	 public static List<Integer> filterL(List val, String oper, Object rif) {
	        List<Integer> indexL = new ArrayList<>();
	        String rifS = (String) rif;
	        for (int i = 0; i < val.size(); i++) {
	            if (check(val.get(i), oper, Double.parseDouble(rifS))){ 
	            	
	                indexL.add(i);
	                }
	        }
	        return indexL; 
	    }
	 
	 /**
	  * Main method for filtering data, use of recursion to filter all the list of values. 
	  * @param src, complete dataset
	  * @param fieldName, list of reference fields
	  * @param operator, list of reference operators
	  * @param value, list of reference values
	  * @param LogOp, list of link operators
	  * @param yearl, list of reference years
	  * @return collection of filtered data
	  */
	
	public static <T> Collection<T> select(Collection<T> src, List<String> fieldName, List<String> operator, List<Object> value, List<String> LogOp, List<Object> yearl) {
		
		Collection<T> out = new ArrayList<T>();
		Object tmp;
		for(T item: src) {
			try {
				Method m = item.getClass().getMethod("get"+fieldName.get(0).substring(0, 1).toUpperCase()+fieldName.get(0).substring(1),null); // method that generates get methods of reference fields
				
				try {
					
					if(m.getName().equals("getMigrants")) { //if the filter is numerical
						int index = Integer.parseInt((String) yearl.get(0));
						List tmpV = TSVReader.getMigYear(index);
						List<Integer> indexList = filterL(tmpV,operator.get(0), value.get(0)); //returns the list of indexes refered to the values that meet the condition of the filter
						List<MigrationStatus> outlist = new ArrayList<>();
						List<MigrationStatus> miglist = TSVReader.getMigrantsList();
						List<MigrationStatus> miglistApp=new ArrayList(miglist);
						
						for(int i : indexList) {
							miglistApp.get(i).setMigrantsFilteredValue((double) tmpV.get(i));
							outlist.add(miglistApp.get(i));
						}
						
					
						return (Collection<T>) outlist; //collection of filtered data
						
					}else {tmp = m.invoke(item); //for string filters is easier to check the filter condition
					
					if(Filters.check(tmp, operator.get(0), value.get(0)))
						out.add(item);
					}
				
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}					
		}
		if (LogOp!=null && LogOp.size()>0)	//recursive part: if is present a link operator the method is recalled with parameters that are substrings of the old parameters
			{
				List<String> subLogicalLinkOperator=null;
					if (LogOp.size()>1)
							subLogicalLinkOperator=LogOp.subList(1, LogOp.size());

					if (LogOp.get(0).equals("AND"))	
						return select(out,fieldName.subList(1, fieldName.size()),operator.subList(1, operator.size()),value.subList(1,value.size()),subLogicalLinkOperator,yearl);
					else
					{
						Collection<T> temp=select(src,fieldName.subList(1, fieldName.size()),operator.subList(1, operator.size()),value.subList(1,value.size()),subLogicalLinkOperator,yearl);
						out.addAll(temp);	
					}
			}
		return out;
	}
}

	