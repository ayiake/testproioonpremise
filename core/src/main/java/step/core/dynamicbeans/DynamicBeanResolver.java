/*******************************************************************************
 * (C) Copyright 2016 Jerome Comte and Dorian Cransac
 *  
 * This file is part of STEP
 *  
 * STEP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * STEP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with STEP.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package step.core.dynamicbeans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicBeanResolver {
	
	private static Logger logger = LoggerFactory.getLogger(DynamicBeanResolver.class);
	
	DynamicValueResolver valueResolver;
	
	Map<Class<?>,BeanInfo> beanInfoCache = new ConcurrentHashMap<>();

	public DynamicBeanResolver(DynamicValueResolver valueResolver) {
		super();
		this.valueResolver = valueResolver;
	}

	public void evaluate(Object o, Map<String, Object> bindings) {
		if(o!=null) {
			Class<?> clazz = o.getClass();
			
			try {
				BeanInfo beanInfo = beanInfoCache.get(clazz);
				if(beanInfo==null) {
					beanInfo = Introspector.getBeanInfo(clazz, Object.class);
					beanInfoCache.put(clazz, beanInfo);
				}
				
				for(PropertyDescriptor descriptor:beanInfo.getPropertyDescriptors()) {
					Method method = descriptor.getReadMethod();
					if(method.getReturnType().equals(DynamicValue.class)) {
						Object value = method.invoke(o);
						if(value!=null) {
							DynamicValue<?> dynamicValue = (DynamicValue<?>) value;
							valueResolver.evaluate(dynamicValue, bindings);
							evaluate(dynamicValue.get(), bindings);
						}
					} else if(method.isAnnotationPresent(ContainsDynamicValues.class)) {
						Object value = method.invoke(o);
						evaluate(value, bindings);
					}
				}
			} catch (Exception e) {
				logger.warn("Error while evaluating object: "+o.toString(), e);
			}			
		}
	}
}
