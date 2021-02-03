package controller.memo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.memo.dao.MemoDAO;
import model.memo.dto.MemoDTO;


@WebServlet("/memo_servlet/*")
public class MemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		MemoDAO dao = new MemoDAO();
		
		String path = request.getContextPath();
		String url = request.getRequestURL().toString();
		String page = "/memo/memo.jsp";
		
		String pageNumber_;
		pageNumber_ = request.getParameter("pageNumber");
		if (pageNumber_ == null || pageNumber_.trim().equals("")) {
			pageNumber_ = "1";
		}
		char imsi;

		int pageNumber = Integer.parseInt(pageNumber_);
		
		if (url.indexOf("memo.do") != -1) {		
			request.setAttribute("menu_gubun", "memo_insert");
			request.setAttribute("pageNumber", pageNumber);
			RequestDispatcher rd = request.getRequestDispatcher(page);
			rd.forward(request, response);		
			
		} else if(url.indexOf("memoProc.do") != -1) {
	

			String name = request.getParameter("name");
			String memo = request.getParameter("memo");

			
			System.out.println("--------insert-------");

			System.out.println("memo : " + memo);
			System.out.println("name : " + name);

			
			String temp = path;


			MemoDTO dto = new MemoDTO();
		
			dto.setName(name);
			dto.setMemo(memo);

			int result = dao.setInsert(dto);
			pageNumber = 1;
			if(result > 0) {
				System.out.println("등록되었습니다");	
			} else {
				System.out.println("실패_결과코드 : " + result);
			}

		} else if (url.indexOf("memolist.do") != -1) {
		
			int pageSize = 10; //페이지에 나오는 리스트 수
			int blockSize = 10; //아래 페이지 장수 
			
			int totalRecord = dao.getTotalRecord();
			int jj = totalRecord - pageSize * (pageNumber -1);
			
			int startRecord = pageSize * (pageNumber - 1 ) + 1;
			int lastRecord = pageSize * pageNumber;
			
			int totalPage = 0;
			int startPage = 1;
			int lastPage = 1;
			
			if(totalRecord > 0) {
				
				totalPage = totalRecord / pageSize + (totalRecord % pageSize == 0 ? 0 : 1);
				startPage = (pageNumber / blockSize - (pageNumber % blockSize != 0 ? 0 : 1)) * blockSize + 1;
							
				lastPage = startPage + blockSize -1;
				if (lastPage > totalPage) {
				  lastPage = totalPage;
				}
			}
			
			ArrayList<MemoDTO> list = dao.getListAll(startRecord,lastRecord);
			
			request.setAttribute("list", list);
			System.out.println("list >>> " + list);
			
			request.setAttribute("pageNumber", pageNumber);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("blockSize", blockSize);
			request.setAttribute("totalRecord", totalRecord);
			request.setAttribute("jj", jj);
			 
			request.setAttribute("startRecord", startRecord);
			request.setAttribute("lastRecord", lastRecord);
			 
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("lastPage", lastPage);
			
			RequestDispatcher rd = request.getRequestDispatcher("/memo/memolist.jsp");
			rd.forward(request, response);
		}
	}

}
