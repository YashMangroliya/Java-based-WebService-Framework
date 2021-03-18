package bobby.test;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.pojo.*;
//@InjectApplicationScope
@Path("/student")
public class Student
{
private Item item;

@InjectRequestParameter(key="cd")
public int code;
public void setCode(int code)
{
this.code=code;
}


@Path("/getAllStudents")
public String getAllStudents()
{
return "allStudentData";
}
//@Forward("/employee/add")
//@POST
@GET
@Path("/add")
public String add(RequestScope requestScope,@RequestParameter(key="nm") String name)
{
item=new Item();
item.setCode(this.code);
item.setName(name);
if(requestScope!=null) requestScope.setAttribute("item",item);
else
{
System.out.println("requestScope is null");
}
return "Student added with code: "+this.code+" and Name: "+name;
}
@OnStartup(priority=1)
public void method1()
{
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
}
@OnStartup(priority=3)
public void method3()
{
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
}
@OnStartup(priority=2)
public void method2()
{
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
}
}
