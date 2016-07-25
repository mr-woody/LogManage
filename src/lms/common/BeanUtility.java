package lms.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class BeanUtility extends org.springframework.beans.BeanUtils {

	public static void copyProperties(Object source, Object target)throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空
						if (value != null) {
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						System.out.println(targetPd.getName()+"属性复制出错!");
						throw new FatalBeanException("Could not copy properties from source to target",ex);
					}
				}
			}
		}
	}
}
