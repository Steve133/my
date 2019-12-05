package cn.center.demo;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * 单元测试 测试整合
 */
public class TestForWebService {

	public static void main(String[] args) throws Exception {
		JaxWsDynamicClientFactory dcf =JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client =dcf.createClient("http://localhost:8888/ns?wsdl");
        //getUser 为接口中定义的方法名称  张三为传递的参数   返回一个Object数组
        Object[] objects = client.invoke("add",1,2);
        //输出调用结果
        System.out.println(objects[0].toString());
	}

}
