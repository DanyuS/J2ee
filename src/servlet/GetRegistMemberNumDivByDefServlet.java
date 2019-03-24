package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import service.GetStatisticsService;
import serviceImpl.GetStatisticsServiceImpl;

/**
 * Servlet implementation class GetRegistMemberNumDivByDefServlet
 */
@WebServlet("/GetRegistMemberNumDivByDefServlet")
public class GetRegistMemberNumDivByDefServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	GetStatisticsService getStatisticsService=new GetStatisticsServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRegistMemberNumDivByDefServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get member Statistics--------------------");
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        int startdate=Integer.parseInt(request.getParameter("startdate"));
        int enddate=Integer.parseInt(request.getParameter("enddate"));
        int gap=Integer.parseInt(request.getParameter("gap"));
        
        List<Integer> result=new ArrayList<Integer>();
        try {
			result=getStatisticsService.getRegistMemberNumDivByDef(startdate, enddate, gap);
			
			PrintWriter write=response.getWriter();
			
			Gson gson = new GsonBuilder().serializeNulls().create();
			String gs="";
			try{
				gs = gson.toJson(result);
				System.out.println(gs);
			}catch(Exception e){
				e.printStackTrace();
			}
			write.write(gs);
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
