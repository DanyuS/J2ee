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
 * Servlet implementation class MemberDeleteAddressServlet
 */
@WebServlet("/MemberDeleteAddressServlet")
public class MemberDeleteAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ManageInformationService manageInformationService=new ManageInformationServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteAddressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("deleting----");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String mid = request.getParameter("mid");
		String addrid = request.getParameter("addrid");
		System.out.println("deleteaddress:  "+addrid);
		boolean b = false;
		try {
			b=manageInformationService.deleteMemberAddress(Integer.parseInt(mid), addrid);
			
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
