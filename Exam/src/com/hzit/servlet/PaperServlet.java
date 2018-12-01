package com.hzit.servlet;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hzit.constant.Constant;
import com.hzit.entity.Exam;
import com.hzit.entity.Paper;
import com.hzit.entity.Question;
import com.hzit.service.PaperService;
import com.hzit.service.QuestionService;
import com.hzit.servlet.base.BaseServlet;
import com.hzit.util.HBUtil;

/**
 * Servlet implementation class PagerServlet
 */
public class PaperServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String examType(HttpServletRequest request, HttpServletResponse response) {
		PaperService service = new PaperService();
		List<Paper> papers = service.getPapers();
		request.setAttribute("paperList", papers);
		request.setAttribute("mainPage", "exam/selectPaper.jsp");
		return "main.jsp";
	}

	public String getDetailPaper(HttpServletRequest request, HttpServletResponse response) {
		int paperId = Integer.parseInt(request.getParameter("paperId"));
		QuestionService questionService = new QuestionService();
		PaperService service = new PaperService();
		Paper paper = service.getPaperById(paperId);
		System.out.println("paper=" + paper);
		request.setAttribute("paper", paper);
		List<Question> radioList = questionService.getDetails(Constant.RADIO, paperId);

		request.setAttribute("squestionList", radioList);

		List<Question> checkboxList = questionService.getDetails(Constant.CHECKBOX, paperId);

		request.setAttribute("mquestionList", checkboxList);
		request.setAttribute("mainPage", "exam/paper.jsp");
		return "main.jsp";
	}

	public String getResult(HttpServletRequest request, HttpServletResponse response) {
		String stuId = request.getParameter("exam.student.id");
		Integer paperId = Integer.parseInt(request.getParameter("exam.paper.id"));

		QuestionService questionService = new QuestionService();
		List<Integer> rlist = questionService.getIdByPaperIdAndType(paperId, Constant.RADIO);
		List<Integer> clist = questionService.getIdByPaperIdAndType(paperId, Constant.CHECKBOX);
		HashMap<String, String> rmap = new HashMap<>();
		HashMap<String, String> cmap = new HashMap<>();
		for (Integer integer : rlist) {
			String ranswer = request.getParameter("id-r-" + integer);
			rmap.put("id-r-" + integer, ranswer);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer integer : clist) {
			String[] canswer = request.getParameterValues("id-c-" + integer);

			cmap.put("id-c-" + integer, Arrays.toString(canswer));
		}
		System.out.println("=========================");
		System.out.println(rmap);
		System.out.println(cmap);
		String questionType = "id-r";
		int rscore = questionService.getExamOfResult(questionType, 20, rmap);
		int cscore = questionService.getExamOfResult("id-c", 30, cmap);
		System.out.println("rscore==============" + rscore);
		System.out.println("cscore==============" + cscore);
		request.setAttribute("rscore", rscore);
		request.setAttribute("cscore", cscore);
		request.setAttribute("mainPage", "exam/examResult.jsp");
//		int rscore = questionService.getExamOfResult(stuId,paperId);
		
		Exam exam = new Exam();
		exam.setStudentId(stuId);
		Timestamp examDate = new Timestamp(System.currentTimeMillis());
		exam.setExamDate(examDate);
		exam.setMoreScore(cscore);
		exam.setSingleScore(rscore);
		exam.setScore(rscore+cscore);
		Paper paper = new Paper();
		paper.setId(paperId);
		Session session = HBUtil.getCurrentSession();
		Transaction beginTransaction = session.beginTransaction();
		try {
			paper = session.get(Paper.class, paperId);
			exam.setPaper(paper);
			session.save(exam);
			beginTransaction.commit();
		} catch (Exception e) {
			System.out.println("±£¥Êexam ß∞‹");
			beginTransaction.rollback();
			e.printStackTrace();
		}
		return "main.jsp";
	}
}
