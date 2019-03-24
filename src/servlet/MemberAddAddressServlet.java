package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ManageInformationService;
import serviceImpl.ManageInformationServiceImpl;

/**
 * Servlet implementation class MemberAddAddressServlet
 */
@WebServlet("/MemberAddAddressServlet")
public class MemberAddAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ManageInformationService manageInformationService=new ManageInformationServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAddAddressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Add AddressING....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String mid = request.getParameter("mid");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String street = request.getParameter("street");
		
		try {
			boolean result=manageInformationService.addMemberAddress(Integer.parseInt(mid), name, phone, province, city, district, street);
			System.out.println("--------------------"+result);
			PrintWriter write=response.getWriter();
			write.write("{\"data\":\""+result+"\"}");
			write.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
