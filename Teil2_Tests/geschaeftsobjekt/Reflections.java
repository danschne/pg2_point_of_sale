package geschaeftsobjekt;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

public class Reflections {

	public static boolean containsMethodName(Class c, String name){

		Method[] methods = c.getMethods();

		for(Method m : methods){
			if(m.getName().equals(name))
				return true;
		}

		return false;

	}

	public static Method getMethod(Class<?> c, String name, Class[] types) throws RuntimeException{
		Method[] methods = c.getMethods();

		for(Method m : methods){
			if(m.getName().equals(name))
				if(types == null || types.length==0){
					return m;
				}
				else if(Arrays.equals(types, m.getParameterTypes()))
					return m;
		}

		throw new RuntimeException("Methode " + name + " mit gesuchten Parametern nicht gefunden");
	}

	public static boolean hasSetterForType(Class<?> klasse, Class<?> c){

		Method[] methods = klasse.getMethods();

		for(Method m : methods){
			if(m.getName().startsWith("set")){
				Class<?>[] types = m.getParameterTypes();
				if(types.length!=1)
					return false;
				if(types[0].equals(c))
					return true;
			}
		}

		return false;
	}
}
