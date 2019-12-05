package cn.center.service;

import javax.jws.WebService;

@WebService(endpointInterface="org.zttc.service.IMyService")
public class MyServiceImpl implements IMyService {

	@Override
	public int add(int a, int b) {
		System.out.println(a+"+"+b+"="+(a+b));
		return a+b;
	}

	@Override
	public int minus(int a, int b) {
		System.out.println(a+"-"+b+"="+(a-b));
		return a-b;
	}

	@Override
	public User login(String username, String password) {
		System.out.println(username+" is logining");
		User user = new User();
		user.setId(1);
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

}
