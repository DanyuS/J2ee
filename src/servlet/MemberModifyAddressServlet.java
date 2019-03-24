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
 * Servlet implementation class MemberModifyAddressServlet
 */
@WebServlet("/MemberModifyAddressServlet")
public class MemberModifyAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ManageInformationService manageInformationService=new ManageInformationServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberModifyAddressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String addrId=request.getParameter("addrId");

		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String province=request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String street=request.getParameter("street");
		
		boolean b = false;
		try {
			b=manageInformationService.modifyMemberAddress(addrId, name, phone, province, city, district, street);
			
			PrintWriter write = response.getWriter();
			write.write("{\"data\":\""+b+"\"}");
			
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
