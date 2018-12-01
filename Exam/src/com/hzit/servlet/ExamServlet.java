package com.hzit.servlet;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hzit.entity.Exam;
import com.hzit.entity.Student;
import com.hzit.servlet.base.BaseServlet;
import com.hzit.util.HBUtil;

public class ExamServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String getExams(HttpServletRequest request, HttpServletResponse response) {
//		String studentId = request.getParameter("s_exam.student.id");
		HttpSession session = request.getSession();
		Student currentUser = (Student) session.getAttribute("currentUser");
		request.setAttribute("currentUser", currentUser);
		Set<Exam> exam = null;
		Session hsession = HBUtil.getCurrentSession();
		Transaction beginTransaction = hsession.beginTransaction();
		try {
			exam = hsession.get(currentUser.getClass(), currentUser.getId()).getExam();
			beginTransaction.commit();
		} catch (Exception e) {
			System.out.println("±£¥Êexam ß∞‹");
			beginTransaction.rollback();
			e.printStackTrace();
		}
		System.out.println(exam);
		request.setAttribute("examList", exam);
		request.setAttribute("mainPage", "exam/myExam.jsp");
		return "main.jsp";
	}

}
