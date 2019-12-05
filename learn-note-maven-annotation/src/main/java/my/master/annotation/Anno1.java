package my.master.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//可以给类、接口、枚举进行注解
@Retention(RetentionPolicy.RUNTIME)//注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。
public @interface Anno1 {

	public int id() default -1;

    public String msg() default "Hi";
}