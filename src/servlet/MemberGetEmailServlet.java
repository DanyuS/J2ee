package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ModifyService;
import serviceImpl.ModifyServiceImpl;

/**
 * Servlet implementation class MemberGetEmailServlet
 */
@WebServlet("/MemberGetEmailServlet")
public class MemberGetEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ModifyService modifyService=new ModifyServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberGetEmailServlet() {
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
        String mid=request.getParameter("mid");
		String email="";
		try {
			email=modifyService.getMemberEmailById(Integer.parseInt(mid));
			PrintWriter write=response.getWriter();
			write.write("{\"data\":\""+email+"\"}");
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
