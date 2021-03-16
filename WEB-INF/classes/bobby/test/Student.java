package bobby.test;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.pojo.*;
@InjectApplicationScope
@Path("/student")
public class Student
{
private ApplicationScope applicationScope;
private Item item;
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
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
public String add(@RequestParameter(key="cd") long code,@RequestParameter(key="nm") String name)
{
item=new Item();
item.setCode(61);
item.setName("Salt");
applicationScope.setAttribute("item",item);
return "Student added with code: "+code+" and Name: "+name;
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
