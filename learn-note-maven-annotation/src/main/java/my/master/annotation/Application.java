package my.master.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import my.master.annotation.Anno2;
import my.master.annotation.Test;
import my.master.annotation.Anno1;

public class Application {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class clazz = Test.class;
		
		
		
		//类注解
        boolean hasAnnotation = clazz.isAnnotationPresent(Anno1.class);
      //获取注解TestAnnotation对象
        if ( hasAnnotation ) {
            Anno1 anno1 = (Anno1) clazz.getAnnotation(Anno1.class);
            System.out.println("类注解名称："+anno1.annotationType());
            System.out.println("类注解属性id："+anno1.id());
            System.out.println("类注解属性msg："+anno1.msg());
        }

        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        //成员变量注解
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
        	System.out.println("成员变量类型："+field);
        	field.setAccessible(true);//暴力获取
        	Anno2 anno2 = field.getAnnotation(Anno2.class);
        	if ( anno2 != null ) {
        		System.out.println("成员变量注解值:"+anno2.annotationType());
        		System.out.println("成员变量注解值:"+anno2.value());
        	}
        	System.out.println("");
		}
        
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
        //方法注解
        Method testMethod = clazz.getDeclaredMethod("testMethod");
        testMethod.setAccessible(true);//暴力获取
        if ( testMethod != null ) {
            Annotation[] ans = testMethod.getAnnotations();
            for (Annotation annotation : ans) {
            	System.out.println("方法注解名称:"+annotation.annotationType().getCanonicalName());
            	System.out.println("方法注解名称:"+annotation.annotationType().getSimpleName());
			}
        }
        
        
    }
}
