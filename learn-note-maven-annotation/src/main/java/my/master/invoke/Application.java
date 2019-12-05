package my.master.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射测试
 * @author :陈进松
 * @date :2019年9月26日 下午4:34:03
 */
public class Application {

	/**
	 * 获取类对象
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static <T> Class getClazz(String packPath) throws ClassNotFoundException {
//		 方式一：类名.class
//		Class retClass = User.class;
//		 方式二：对象名.getClass();
//		Class retClass = new User().getClass();
//		 方式三：Class.forName("类的全限定名") 全限定名：包名+类名
		 Class<T> retClass = (Class<T>) Class.forName(packPath);
		return retClass;
	}

	/**
	 * 反射获取类实例
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> T getNewInstance(Class clazz) {
		try {
			// 1.获得所有public类型的构造方法
			Constructor<?>[] constructors = clazz.getConstructors();
			for (Constructor<?> constructor : constructors) {
				
				System.out.println(constructor.getParameterCount());
				Class<?>[] parameterTypes = constructor.getParameterTypes();
				for (Class o : parameterTypes) {
					System.out.println(o);
				}
			}
			// 2.获得所有构造方法(包含私有的)
//			Method[] constructors = clazz.getDeclaredMethods();
			// 3.1 获得public User(String username, String password)构造方法的Constructor对象
//			Constructor constructor = clazz.getConstructor(String.class, String.class);
			// 4.1 获得private User(Integer age, String sex)构造方法的Constructor对象(包含私有)
			Constructor constructor1 = clazz.getDeclaredConstructor(Integer.class, String.class);
//			当构造方法为private修饰时，需要暴力反射才能使用该构造方法
			constructor1.setAccessible(true);
			T inst = (T) constructor1.newInstance(100, "男");
			return inst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static User setFieldByName(User man, String FieldName,String value) {
		try {
			Class clazz = man.getClass();
			// 1.获得所有public类型的字段(包括父类的)
			Field[] fields = clazz.getFields();// length:2
			System.out.println(fields.length);
//		        获得所有字段(包括私有的，不包括父类的)
//		Field[] declaredFields = obj.getClass().getDeclaredFields();// length:4
//		        获得指定public类型的字段 public String username
//		Field username = obj.getClass().getField("username");
//		        获得指定字段(包括私有的) private String sex;
			Field sexField = clazz.getDeclaredField(FieldName);
			// 4.1当字段被private修饰时，需要暴力反射才能使用该字段
			sexField.setAccessible(true);
			// 4.2赋值 参数1：Class对象对应的类的对象 参数2：要赋的值
			sexField.set(man, value);
			return man;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void invokeMethodByName(User obj, String MethodName) {
		try {
			Class clazz = obj.getClass();
			// 1.获得所有public类型的方法（包括父类的）
//			Method[] methods = clazz.getMethods();
			// 2.获得所有方法(包括私有的，不包括父类)
//			Method[] declaredMethods = clazz.getDeclaredMethods();
			// 3.获得指定public类型的方法 public void Study(String username) 参数1：方法名 参数2：方法名中的参数类型
//			Method method = clazz.getMethod("study", String.class);
			// 4.获得指定方法(包括私有的) private void eat(String[] username)参数1：方法名 参数2：方法名中的参数类型
			Method eat = clazz.getDeclaredMethod(MethodName, String[].class);
			// 4.1当方法被private修饰时，需要暴力反射才能使用该方法
			eat.setAccessible(true);
			// 4.2调用方法 参数1：调用方法的对象 参数2：可变参数会把数据解析成多个参数，
			// 传递到eat()方法中的new String[]{"张三", "李四"}实际上是 "张三" ，"李四"这两个参数，所以需要强转
			// ps:如果不需要调用方法的对象（main方法）则传null
			Object o = eat.invoke(obj, (Object) new String[] { "汉堡", "鸡腿" });
			System.out.println(o);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		Class clazz = getClazz("my.master.invoke.User");

		User man = (User) getNewInstance(clazz);
		System.out.println(man);

		man = (User) setFieldByName( man, "sex","女");
		System.out.println(man.getSex());

		invokeMethodByName(man, "eat");
	}

}
