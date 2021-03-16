package com.thinking.machines.webRock;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.thinking.machines.webRock.pojo.*;
import com.thinking.machines.webRock.model.*;
import com.thinking.machines.webRock.annotations.*;
public class TMWebRock extends HttpServlet
{

// implementing all logics on doGet for now, there are no difference between doGet and doPost except checking for METHOD TYPE of request
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called"+request.getRequestURI());
try{
System.out.println("1");
PrintWriter pw=response.getWriter();
ServletContext servletContext=getServletContext();
WebRockModel webRockModel;
String key,methodType;
int index;
Service service;
Class serviceClass;
Method serviceMethod;
System.out.println("2");
response.setContentType("text/html");
webRockModel=(WebRockModel)servletContext.getAttribute("webRockModel");
key=request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
System.out.println("3");
index=key.indexOf("/",1);
key=key.substring(index,key.length());
HashMap<String,Service> map=webRockModel.getMap();
service=map.get(key);
serviceClass=service.getServiceClass();
serviceMethod=service.getService();

// checking method type of request (GET/POST)
System.out.println("4");
if(!service.getIsGetAllowed() && service.getIsPostAllowed())
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
System.out.println("5");
Object serviceClassObject=serviceClass.newInstance();

// checking for RequestParameters
Object requestParameters[];
System.out.println("5.1");
LinkedHashMap<String,Class> requestParameterMap=service.getRequestParameterMap();
requestParameters=null;
System.out.println("5.2");
if(requestParameterMap!=null)
{
System.out.println("5.3");
Set<String> keySet=requestParameterMap.keySet();
requestParameters=new Object[keySet.size()];
System.out.println("5.4");
String keyString;
Iterator<String> itr=keySet.iterator();
System.out.println("5.5");
int integerValue;
Object obj;
System.out.println("Size of set: "+keySet.size());
for(int i=0;itr.hasNext();i++)
{
System.out.println("5.6");
keyString=itr.next();
System.out.println("5.7");
if(requestParameterMap.get(keyString).isPrimitive())
{
String primitiveTypeString=requestParameterMap.get(keyString).toString();
System.out.println(primitiveTypeString);
System.out.println("5.7.1");
if(primitiveTypeString.equalsIgnoreCase("int") || primitiveTypeString.equalsIgnoreCase("Integer"))
{
System.out.println("5.7.2");
System.out.println(request.getParameter(keyString));
requestParameters[i]=Integer.parseInt(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("long"))
{
System.out.println("5.7.3");
System.out.println(request.getParameter(keyString));
requestParameters[i]=Long.parseLong(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("short"))
{
requestParameters[i]=Short.parseShort(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("double"))
{
requestParameters[i]=Double.parseDouble(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("float"))
{
requestParameters[i]=Float.parseFloat(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("boolean"))
{
requestParameters[i]=Boolean.parseBoolean(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("byte"))
{
requestParameters[i]=Byte.parseByte(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("char"))
{
requestParameters[i]=request.getParameter(keyString).charAt(0);
}
}
else
{
System.out.println("5.9");
requestParameters[i]=request.getParameter(keyString);
}
}
}

System.out.println("5.10");
Class classes[]=new Class[1];
// checking for IOC (INVERSE OF CONTROL)
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.ApplicationScope");
if(service.getInjectApplicationScope() && serviceClass.getMethod("setApplicationScope",classes)!=null)
{
serviceClass.getMethod("setApplicationScope",classes).invoke(serviceClassObject,new ApplicationScope(servletContext));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.SessionScope");
if(service.getInjectSessionScope() && serviceClass.getMethod("setSessionScope",classes)!=null)
{
serviceClass.getMethod("setSessionScope",classes).invoke(serviceClassObject,new SessionScope(request.getSession()));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.RequestScope");
if(service.getInjectRequestScope() && serviceClass.getMethod("setRequestScope",classes)!=null)
{
serviceClass.getMethod("setRequestScope",classes).invoke(serviceClassObject,new RequestScope(request));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.ApplicationDirectory");
if(service.getInjectApplicationDirectory() && serviceClass.getMethod("setApplicationDirectory",classes)!=null)
{
serviceClass.getMethod("setApplicationDirectory",classes).invoke(serviceClassObject,new ApplicationDirectory(new File(servletContext.getRealPath(""))));
}
System.out.println("6");
// checking for IOC (INVERSE OF CONTROL) Next Level

List<AutoWiredProperty> autoWiredProperties;
autoWiredProperties=service.getAutoWiredProperties();
String autoWiredPropertyName;
Object autoWiredPropertyTypeObject;
Class autoWiredPropertyType;
Method autoWiredPropertySetter;
for(AutoWiredProperty autoWiredProperty: autoWiredProperties)
{
autoWiredPropertyName=autoWiredProperty.getName();
autoWiredPropertyType=autoWiredProperty.getType();
autoWiredPropertyTypeObject=request.getAttribute(autoWiredPropertyName);
autoWiredPropertySetter=service.getServiceClass().getMethod("set"+autoWiredPropertyType.getSimpleName(),autoWiredPropertyType);
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertyTypeObject=request.getSession().getAttribute(autoWiredPropertyName);
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertyTypeObject=servletContext.getAttribute(autoWiredPropertyName);
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertySetter.invoke(serviceClassObject);
}
}
}
}



System.out.println("6");
System.out.println("6.1");
// checking for request forwarding
String forwardTo=service.getForwardTo();
if(forwardTo!=null)
{
System.out.println("6.2");
if(map.containsKey(forwardTo))
{
System.out.println("6.3");
if(requestParameters!=null) service.getService().invoke(serviceClassObject);
else service.getService().invoke(serviceClassObject,requestParameters);
System.out.println("6.4");
service=map.get(forwardTo);
String contextURI=request.getRequestURI();
int index1=contextURI.indexOf("/",1);
int index2=contextURI.indexOf("/",index1+1);
String str=contextURI.substring(index1,index2);

RequestDispatcher requestDispatcher=request.getRequestDispatcher(str+forwardTo);
requestDispatcher.forward(request,response);

}
else
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher(forwardTo);
requestDispatcher.forward(request,response);
}
}
else
{
System.out.println("6.5");
System.out.println(requestParameters!=null);
System.out.println(service.getService().toString());
System.out.println(requestParameters[0]);
System.out.println(requestParameters[0] instanceof Integer);
System.out.println(requestParameters[1]);
System.out.println(requestParameters[1] instanceof String);
if(requestParameters!=null) pw.println(service.getService().invoke(serviceClassObject,requestParameters));
else pw.println(service.getService().invoke(serviceClassObject));
pw.flush();
}
System.out.println("7");
}catch(Exception e)
{
System.out.println(e+" "+request.getRequestURI());
System.out.println(e.getCause());
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
try{
PrintWriter pw=response.getWriter();
WebRockModel webRockModel;
String key,methodType;
int index;
Service service;
Class serviceClass;
Method serviceMethod;
response.setContentType("text/html");
webRockModel=(WebRockModel)getServletContext().getAttribute("webRockModel");
key=request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
index=key.indexOf("/",1);
key=key.substring(index,key.length());
HashMap<String,Service> map=webRockModel.getMap();
service=map.get(key);
serviceClass=service.getServiceClass();
serviceMethod=service.getService();
Class get=Class.forName("com.thinking.machines.webRock.annotations.GET");
if(!service.getIsPostAllowed() && service.getIsGetAllowed())
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
pw.println(service.getService().invoke(service.getServiceClass().newInstance()));
pw.flush();
}catch(Exception e)
{
System.out.println(e);
}
}
}