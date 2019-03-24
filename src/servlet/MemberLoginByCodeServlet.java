package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Member;
import net.sf.json.JSONObject;
import service.LoginService;
import serviceImpl.LoginServiceImpl;

/**
 * Servlet implementation class MemberLoginByCodeServlet
 */
@WebServlet("/MemberLoginByCodeServlet")
public class MemberLoginByCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	LoginService loginService=new LoginServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberLoginByCodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("loginING....");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		
		String code = request.getParameter("code");
		
		Member member;
		
		try {
			member = loginService.memberLoginByCode(code);
			PrintWriter write = response.getWriter();
			if (member != null) {

				JSONObject json = JSONObject.fromObject(member);
				String strJson = json.toString();
				System.out.println("member------------"+strJson);
				write.write(strJson);
			} else {
				write.write("{\"data\":\"false\"}");
			}
			write.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
