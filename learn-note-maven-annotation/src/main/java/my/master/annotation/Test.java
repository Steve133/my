package my.master.annotation;

@Anno1(msg="hello")
public class Test {

	@Anno2(value="hi")
	private int name;
	@Anno2(value="11")
    private int age;
	
	@Anno3
	private void testMethod(){
	}
	
	
}
