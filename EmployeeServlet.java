package com.emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
       Connection conn;
       ResultSet rs;
       Statement st;
    /**
     * @see GenericServlet#GenericServlet()
     */
    public EmployeeServlet() {
        super();
        System.out.println("calling....");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("Initialising...");
		try
		{
			//1st step : load the driver
			System.out.println("Trying to load the driver.......");
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("Driver loaded....");
		
			//2nd step : connect to the database 
			System.out.println("Trying to connect to the database");
			//jdbc:oracle:thin:@localhost:1521:yourInstanceName XE/ORCL/OSE whaterver name found in tnsnames.ora file
			conn = DriverManager.getConnection("jdbc:oracle:thin:"
					+ "@localhost:1521:ORCL", "system", "hima");
			System.out.println("Connected to the database");
		}
		catch(Exception e) {
			System.out.println("Some Problem : "+e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroying1...");
		  try {
	            st.close();
	            rs.close();
	            conn.close();
	        }
	        catch(Exception e) {
	        	System.out.println("some problem in destroy block"+e);
	        }
	}

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw=response.getWriter();
        try {
        st = conn.createStatement();
        System.out.println("Statement made...");
        rs = st.executeQuery("select * from emp");
        pw.println("<html><body><h2>The Select query has following results : </h2>");
        pw.println("<hr></br><table cellspacing='0' cellpadding='5' border='5'>");
        pw.println("<tr>");
        pw.println("<td><b>empno</b></td>");
        pw.println("<td><b>ename</b></td>");
        pw.println("<td><b>job</b></td>");
        pw.println("<td><b>mgr</b></td>");
        pw.println("<td><b>hiredate</b></td>");
        pw.println("<td><b>sal</b></td>");
        pw.println("<td><b>comm</b></td>");
        pw.println("<td><b>deptno</b></td>");
        pw.println("<td><b>empno</b></td>");//remove after testing git
        pw.println("<td><b>ename</b></td>");//  ""    ""   ""
        pw.println("<td><b>deptno</b></td>");
        pw.println("</tr>");
     while(rs.next()) {
               pw.println("<tr>");
              pw.println("<td>"+rs.getInt(1) + "</td>");
              pw.println("<td>"+rs.getString(2) + "</td>");
              pw.println("<td>"+rs.getString(3) + "</td>");
              pw.println("<td>"+rs.getString(4) + "</td>");
              pw.println("<td>"+rs.getDate(5) + "</td>");
              pw.println("<td>"+rs.getInt(6) + "</td>");
              pw.println("<td>"+rs.getString(7) + "</td>");
              pw.println("<td>"+rs.getInt(8) + "</td>");
              pw.println("</tr>");
}
pw.println("</table></br><hr></body></html>");
     }
       catch(Exception e) {
           System.out.println("Some Problem : "+e);
       }
       
   }
}
	
	class Employee
	{
		private int EmployeeNumber;
		private String EmployeeName;
		private String EmployeeJob;
		private int EmployeeMgr;
		private String EmployeeHireDate;
		private int EmployeeSalary;
		private int EmployeeComm;
		private int EmployeeDeptNo;
		public int getEmployeeNumber() {
			return EmployeeNumber;
		}
		public void setEmployeeNumber(int employeeNumber) {
			EmployeeNumber = employeeNumber;
		}
		public String getEmployeeName() {
			return EmployeeName;
		}
		public void setEmployeeName(String employeeName) {
			EmployeeName = employeeName;
		}
		public String getEmployeeJob() {
			return EmployeeJob;
		}
		public void setEmployeeJob(String employeeJob) {
			EmployeeJob = employeeJob;
		}
		public int getEmployeeMgr() {
			return EmployeeMgr;
		}
		public void setEmployeeMgr(int employeeMgr) {
			EmployeeMgr = employeeMgr;
		}
		public String getEmployeeHireDate() {
			return EmployeeHireDate;
		}
		public void setEmployeeHireDate(String employeeHireDate) {
			EmployeeHireDate = employeeHireDate;
		}
		public int getEmployeeSalary() {
			return EmployeeSalary;
		}
		public void setEmployeeSalary(int employeeSalary) {
			EmployeeSalary = employeeSalary;
		}
		public int getEmployeeComm() {
			return EmployeeComm;
		}
		public void setEmployeeComm(int employeeComm) {
			EmployeeComm = employeeComm;
		}
		public int getEmployeeDeptNo() {
			return EmployeeDeptNo;
		}
		public void setEmployeeDeptNo(int employeeDeptNo) {
			EmployeeDeptNo = employeeDeptNo;
		}
		
	}
	interface EmployeeDAO
	{
		void addEmployee(Employee dRef);

		Employee findEmployee(int eno);			

		List<Employee> findEmployees();			
		List<Employee> findEmployees(int dno);			
		List<Employee> findEmployees(String job);			
		List<Employee> findEmployees(Date hiredate);			

		void modifyEmployee(Employee dRef);		
		void removeEmployee(Employee dRef);
			void removeEmployee1(Employee job);
			void removeEmployee2(Employee deptno);
	}
  
	class EmployeeDAOImpl implements EmployeeDAO
	{
		Connection conn;
		ResultSet rs;
		Statement st;
		PreparedStatement pst;
		
		EmployeeDAOImpl()
		{
			try
			{
				//1st step : load the driver
				System.out.println("Trying to load the driver.......");
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				System.out.println("Driver loaded....");
			
				//2nd step : connect to the database 
				System.out.println("Trying to connect to the database");
				//jdbc:oracle:thin:@localhost:1521:yourInstanceName XE/ORCL/OSE whaterver name found in tnsnames.ora file
				conn = DriverManager.getConnection("jdbc:oracle:thin:"
						+ "@localhost:1521:ORCL", "system", "hima");
				System.out.println("Connected to the database");
			}
			catch(Exception e) {
				System.out.println("Some Problem : "+e);
			}
		}

		public void addEmployee(Employee dRef) {
			// TODO Auto-generated method stub
			
			try {
				PreparedStatement pst = conn.prepareStatement("insert into emp values (?,?,?,?,?,?,?,?)"); // this is for DML
				pst.setInt(1, dRef.getEmployeeNumber());	
				pst.setString(2, dRef.getEmployeeName()); 
				pst.setString(3, dRef.getEmployeeJob());
				pst.setInt(4, dRef.getEmployeeMgr());
				pst.setString(5, dRef.getEmployeeHireDate());
				pst.setInt(6, dRef.getEmployeeSalary());
				pst.setInt(7, dRef.getEmployeeComm());
				pst.setInt(8, dRef.getEmployeeDeptNo());
				System.out.println("PrepareStatement made....for DML");
				
				System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
				int rows = pst.executeUpdate(); //fire the pst associated insert query
				System.out.println("Record inserted..."+rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
	}

		public Employee findEmployee(int eno) {
			// TODO Auto-generated method stub
			Employee eObj=null;
			try {
				st = conn.createStatement();
				System.out.println("Statment made .... ");
				
				rs = st.executeQuery("select * from emp where empno="+eno); // type the query here
				System.out.println("Query fired...and got the result....");
				System.out.println("Now processing the result....."); //5th step : process the result
				if(rs.next()) { // process the result set like a cursor program
					int x1 = rs.getInt(1); 	
					String x2=rs.getString(2);
					String x3=rs.getString(3);
					int x4 = rs.getInt(4); 	
					String x5=rs.getString(5);
					int x6 = rs.getInt(6); 	
					int x7 = rs.getInt(7); 	
					int x8 = rs.getInt(8); 	
					
					eObj = new Employee(); //empty single object
					eObj.setEmployeeNumber(x1);
					eObj.setEmployeeName(x2);
					eObj.setEmployeeJob(x3);
					eObj.setEmployeeMgr(x4);
					eObj.setEmployeeHireDate(x5);
					eObj.setEmployeeSalary(x6);
					eObj.setEmployeeComm(x7);
					eObj.setEmployeeDeptNo(x8);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			return eObj;
		}
			

		public void modifyEmployee(Employee dRef) {
			// TODO Auto-generated method stub
			
			try {
				System.out.println("Trying to make a PreparedStatement for DML(update)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
				PreparedStatement pst = conn.prepareStatement("update emp set ename=?, Job=?,mgr=?, HireDate=?, Sal=?, Comm=?,DeptNo=? where empno=?"); // this is for DML
				 
				pst.setString(1, dRef.getEmployeeName());
				pst.setString(2, dRef.getEmployeeJob());
				pst.setInt(3, dRef.getEmployeeMgr());
				pst.setString(4, dRef.getEmployeeHireDate());
				pst.setInt(5, dRef.getEmployeeSalary());
				pst.setInt(6, dRef.getEmployeeComm());
				pst.setInt(7, dRef.getEmployeeDeptNo());
				pst.setInt(8, dRef.getEmployeeNumber()); 
				
				System.out.println("PrepareStatement made....for DML update");
				System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
				int rows = pst.executeUpdate();
				System.out.println("Record updated : "+rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}	
		}

		public void removeEmployee(Employee dRef) {
			// TODO Auto-generated method stub
			
			try {
				System.out.println("Trying to make a PreparedStatement for DML(delete)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
				PreparedStatement pst = conn.prepareStatement("delete from emp where EMPNO=?"); // this is for DML
				pst.setInt(1, dRef.getEmployeeNumber()); //set the question mark with job
				
				System.out.println("PrepareStatement made....for DML delete");
				System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
				int rows = pst.executeUpdate(); //fire the pst associated insert query
				System.out.println("Record deleted..."+rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			
		}

		public List<Employee> findEmployees(){
			List<Employee> empList=new ArrayList<Employee>();
			try {
				Statement st = conn.createStatement();
				System.out.println("Statement made...");
				rs = st.executeQuery("select * from emp"); // type the query here
				System.out.println("Query fired...and got the result....");
				System.out.println("Now processing the result....."); //5th step : process the result
				while(rs.next()) { // process the result set like a cursor program
					int x1 = rs.getInt(1); 	
					String x2 = rs.getString(2); // dname
					String x3 = rs.getString(3); 
					int x4 = rs.getInt(4); 
					String x5 = rs.getString(5); 
					int x6 = rs.getInt(6); 
					int x7 = rs.getInt(7); 
					int x8 = rs.getInt(8); 
					Employee empObj = new Employee(); //empty single object
					empObj.setEmployeeNumber(x1);
					empObj.setEmployeeName(x2);
					empObj.setEmployeeJob(x3);
					empObj.setEmployeeMgr(x4);
					empObj.setEmployeeHireDate(x5);
					empObj.setEmployeeSalary(x6);
					empObj.setEmployeeComm(x7);
					empObj.setEmployeeDeptNo(x8);
					empList.add(empObj);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			return empList;		
		}
		

		public void removeEmployee1(Employee job) {
			
			try {
				System.out.println("Trying to make a PreparedStatement for DML(delete)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
				PreparedStatement pst = conn.prepareStatement("delete from emp where JOB=?"); // this is for DML
				pst.setString(1, job.getEmployeeJob()); //set the question mark with job
				
				System.out.println("PrepareStatement made....for DML delete");
				System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
				int rows = pst.executeUpdate(); //fire the pst associated insert query
				System.out.println("Record deleted..."+rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}	
		}

		public void removeEmployee2(Employee deptno) {
		
			try {
				System.out.println("Trying to make a PreparedStatement for DML(delete)");	//3rd step : create statement of your choice select(DQL)/DML/PL-SQL(proce/funs)
				PreparedStatement pst = conn.prepareStatement("delete from emp where DEPTNO=?"); // this is for DML
				pst.setInt(1, deptno.getEmployeeDeptNo()); //set the question mark with dno
				
				System.out.println("PrepareStatement made....for DML delete");
				System.out.println("Trying to fire it... ");	//4th step : fire the statement and acquire result if any
				int rows = pst.executeUpdate(); //fire the pst associated insert query
				System.out.println("Record deleted..."+rows);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
		}

		public List<Employee> findEmployees(int dno) {
			// TODO Auto-generated method stub
			List<Employee> empList=new ArrayList<Employee>();
			try {
				Statement st = conn.createStatement();
				System.out.println("Statement made...");
				rs = st.executeQuery("select * from emp where deptno="+dno); // type the query here
				System.out.println("Query fired...and got the result....");
				System.out.println("Now processing the result....."); //5th step : process the result
				while(rs.next()) { // process the result set like a cursor program
					int x1 = rs.getInt(1); 	
					String x2 = rs.getString(2); // dname
					String x3 = rs.getString(3); 
					int x4 = rs.getInt(4); 
					String x5 = rs.getString(5); 
					int x6 = rs.getInt(6); 
					int x7 = rs.getInt(7); 
					int x8 = rs.getInt(8); 
					Employee empObj = new Employee(); //empty single object
					empObj.setEmployeeNumber(x1);
					empObj.setEmployeeName(x2);
					empObj.setEmployeeJob(x3);
					empObj.setEmployeeMgr(x4);
					empObj.setEmployeeHireDate(x5);
					empObj.setEmployeeSalary(x6);
					empObj.setEmployeeComm(x7);
					empObj.setEmployeeDeptNo(x8);
					empList.add(empObj);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			return empList;
		}

		public List<Employee> findEmployees(String job) {
			
			List<Employee> empList=new ArrayList<Employee>();
			try {
				Statement st = conn.createStatement();
				System.out.println("Statement made...");
				rs = st.executeQuery("select * from emp where job="+job); // type the query here
				System.out.println("Query fired...and got the result....");
				System.out.println("Now processing the result....."); //5th step : process the result
				while(rs.next()) { // process the result set like a cursor program
					int x1 = rs.getInt(1); 	
					String x2 = rs.getString(2); // dname
					String x3 = rs.getString(3); 
					int x4 = rs.getInt(4); 
					String x5 = rs.getString(5); 
					int x6 = rs.getInt(6); 
					int x7 = rs.getInt(7); 
					int x8 = rs.getInt(8); 
					Employee empObj = new Employee(); //empty single object
					empObj.setEmployeeNumber(x1);
					empObj.setEmployeeName(x2);
					empObj.setEmployeeJob(x3);
					empObj.setEmployeeMgr(x4);
					empObj.setEmployeeHireDate(x5);
					empObj.setEmployeeSalary(x6);
					empObj.setEmployeeComm(x7);
					empObj.setEmployeeDeptNo(x8);
					empList.add(empObj);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			return empList;		
		}

		public List<Employee> findEmployees(Date hiredate) {
			List<Employee> empList=new ArrayList<Employee>();
			try {
				Statement st = conn.createStatement();
				System.out.println("Statement made...");
				rs = st.executeQuery("select * from emp where HireDate="+hiredate); // type the query here
				System.out.println("Query fired...and got the result....");
				System.out.println("Now processing the result....."); //5th step : process the result
				while(rs.next()) { // process the result set like a cursor program
					int x1 = rs.getInt(1); 	
					String x2 = rs.getString(2); // dname
					String x3 = rs.getString(3); 
					int x4 = rs.getInt(4); 
					String x5 = rs.getString(5); 
					int x6 = rs.getInt(6); 
					int x7 = rs.getInt(7); 
					int x8 = rs.getInt(8); 
					Employee empObj = new Employee(); //empty single object
					empObj.setEmployeeNumber(x1);
					empObj.setEmployeeName(x2);
					empObj.setEmployeeJob(x3);
					empObj.setEmployeeMgr(x4);
					empObj.setEmployeeHireDate(x5);
					empObj.setEmployeeSalary(x6);
					empObj.setEmployeeComm(x7);
					empObj.setEmployeeDeptNo(x8);
					empList.add(empObj);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("some problem.."+e);
			}
			return empList;			
		}	
}