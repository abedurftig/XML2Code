package com.xml2code.java.shared.reflection;

import java.lang.reflect.Method;
import java.util.List;

import com.xml2code.java.shared.domain.IDomainObject;

public class ReflectionUtil {

	public static <T extends IDomainObject> void populateBackReferences(T obj) {
		ReflectionUtil.populateBackReferences(obj, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IDomainObject> void populateBackReferences(T obj, T parent) {

		Method[] methods = obj.getClass().getMethods();
		Method method = null;
		List<T> list = null;

		try {

			for (int i = 0; i < methods.length; i++) {

				method = methods[i];

				// if the object has a method that returns a list, we assume it is
				// a list of DomainObject instances.
				if (method.getReturnType() == List.class) {
					list = (List<T>) method.invoke(obj, new Object[0]);
					for (T item : list) {
						// for every DomainObject instance in the list,
						// try to populate the back reference to the object
						ReflectionUtil.populateBackReferences(item, obj);
					}
				} else if (isBackReferenceSetter(method, parent)) {
					method.invoke(obj, new Object[] {parent});
				} else if (parent == null) {

					// check for one to one relation ships
					if (method.getReturnType() != void.class) {
						Object instance = method.getReturnType().newInstance();
						if (instance instanceof IDomainObject) {
							T child = (T) method.invoke(obj, new Object[0]);
							ReflectionUtil.populateBackReferences(child, obj);
						}
					}
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static<T extends IDomainObject> void getDomainObjectsInGraph(T obj, List<T> domainObjects) {

		Method[] methods = obj.getClass().getMethods();
		Method method = null;
		List<T> list = null;

		try {

			for (int i = 0; i < methods.length; i++) {

				method = methods[i];

				// if the object has a method that returns a list, we assume it is
				// a list of DomainObject instances.
				if (method.getReturnType() == List.class) {

					list = (List<T>) method.invoke(obj, new Object[0]);
					for (T item : list) {

						domainObjects.add(item);
						getDomainObjectsInGraph(item, domainObjects);

					}
				} else if (method.getReturnType() != void.class) {
					
					Object instance = method.getReturnType().newInstance();
					if (instance instanceof IDomainObject) {
						T child = (T) method.invoke(obj, new Object[0]);
						domainObjects.add(child);
					}
					
				}
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	private static boolean isBackReferenceSetter(Method method, Object owner) {

		return owner != null && 
				method.getParameterTypes().length == 1 && 
				(method.getParameterTypes()[0] == owner.getClass() || 
				method.getParameterTypes()[0] == owner.getClass().getSuperclass());

	}
}
