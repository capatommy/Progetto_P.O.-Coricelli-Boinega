package it.univpm.demoSpringBootApp.services;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import it.univpm.demoSpringBootApp.models.MigYear;
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
		
		else if(th instanceof List){
			Double valuec = ((Number)value).doubleValue();
			List thL = ((List) th);
			if(!thL.isEmpty() && thL.get(0) instanceof Number) {
				List<Double> valueL = new ArrayList<>();
				for(Object o : thL) {
					valueL.add(((Number) o).doubleValue());
				}
				switch (operator) {
				case "$in":
					return valueL.contains(valuec);
				case "$nin" :
					return !valueL.contains(valuec);
				case "$bt" :
					double first = valueL.get(0);
					double second = valueL.get(1);
					return valuec >= first && valuec <= second;
				default:
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "errore");
				}
			}else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lista vuota o non numerica");
		}
		return false;		
	}
	
	public static <T> Collection<T> select(Collection<T> src, List<String> fieldName, List<String> operator, List<Object> value, List<String> LogOp) {
		
		Collection<T> out = new ArrayList<T>();
		for(T item: src) {
			try {
				Method m = item.getClass().getMethod("get"+fieldName.get(0).substring(0, 1).toUpperCase()+fieldName.get(0).substring(1),null);
				
				try {
					
					Object tmp = m.invoke(item);
					if(m.getName().equals("getMigrants")) {
						List<MigYear> list = new ArrayList<MigYear>();
						List<MigYear> outList= new ArrayList<MigYear>();
						MigrationStatus oggetto= new MigrationStatus();
						List<MigrationStatus> output = new ArrayList<MigrationStatus>();
						list=((List)tmp);
						
						for(int i=0;i<11;i++) {
		
							if(Filters.check(list.get(i).getValue(), operator.get(0), value.get(0)))
								{
								outList.add(list.get(i));
								}
						}
						oggetto=(MigrationStatus)item;
						oggetto.setMigrants(outList);
						output.add(oggetto);
						out.addAll((Collection<T>)output);
						
					}else {
						
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
						return select(out,fieldName.subList(1, fieldName.size()),operator.subList(1, operator.size()),value.subList(1,value.size()),subLogicalLinkOperator);
					else
					{
						Collection<T> temp=select(src,fieldName.subList(1, fieldName.size()),operator.subList(1, operator.size()),value.subList(1,value.size()),subLogicalLinkOperator);
						out.addAll(temp);	
					}
			}
		return out;
	}
}

	