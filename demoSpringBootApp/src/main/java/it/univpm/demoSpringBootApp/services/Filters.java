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

	
	public static <T> Collection<T> select(Collection<T> src, List<String> fieldName, List<String> operator, List<Object> value, List<String> LogOp, List<Object> yearl) {
		
		Collection<T> out = new ArrayList<T>();
		Object tmp;
		for(T item: src) {
			try {
				Method m = item.getClass().getMethod("get"+fieldName.get(0).substring(0, 1).toUpperCase()+fieldName.get(0).substring(1),null);
				
				try {
					
					if(m.getName().equals("getMigrants")) {
						int index = Integer.parseInt((String) yearl.get(0));
						List tmpV = TSVReader.getMigYear(index);
						List<Integer> indexList = filterL(tmpV,operator.get(0), value.get(0));
						List<MigrationStatus> outlist = new ArrayList<>();
						List<MigrationStatus> miglist = TSVReader.getMigrantsList();
						List<MigrationStatus> miglistApp=new ArrayList(miglist);
						
						for(int i : indexList) {
							miglistApp.get(i).setMigrantsFilteredValue((double) tmpV.get(i));
							outlist.add(miglistApp.get(i));
						}
						
					
						return (Collection<T>) outlist;
						
					}else {tmp = m.invoke(item);
					
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
		if (LogOp!=null && LogOp.size()>0)	
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

	