package com.hzit.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hzit.entity.Student;
import com.hzit.service.StudentService;
import com.hzit.service.StudentServiceImpl;
import com.hzit.servlet.base.BaseServlet;
import com.hzit.util.HBUtil;

public class StudentServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public StudentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String studentId = request.getParameter("student.id");
		String password = request.getParameter("student.password");
		System.out.println("”√ªß£∫" + studentId + "°£°£°£’˝‘⁄µ«¬º°£°£°£");
		Session session = HBUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		try {
			Student result = (Student) session
					.createQuery("from Student s where s.cardNo=:studentId and s.password = :password")
					.setString("studentId", studentId).setString("password", password).uniqueResult();

			ts.commit();
			if (result != null) {
				HttpSession httpSession = request.getSession(true);
				
				httpSession.setAttribute("userLevel", 1);
				
				httpSession.setAttribute("currentUser", result);

				return "main.jsp";
			} else {
				request.setAttribute("error", "’À∫≈ªÚ’ﬂ√‹¬Î¥ÌŒÛ!");
				return "login.jsp";
			}
		} catch (Exception e) {

			System.out.println("login:rollback");
			e.printStackTrace();
			ts.rollback();
		}

		return null;
	}

	public String updatePassword(HttpServletRequest request, HttpServletResponse response) {
		String newPassword = request.getParameter("student.password");
		System.out.println("–ﬁ∏ƒ√‹¬Î°£°£°£");
		StudentService service = new StudentServiceImpl();
		HttpSession session = request.getSession();
		Student currentUser = (Student) session.getAttribute("currentUser");
		currentUser.setPassword(newPassword);
		if(service.save(currentUser)) {
			request.setAttribute("mainPage", "student/updateSuccess.jsp");
		}else {
			System.out.println("√‹¬Î–ﬁ∏ƒ ß∞‹");
		}
		return "main.jsp";
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "login.jsp";
	}
	
	public String showAllStudents(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("mainPage", "student/studentList.jsp");
		StudentService service = new StudentServiceImpl();
		List<Student> studentList = service.getStudentList();
		request.setAttribute("studentList", studentList);
		return "main.jsp";
	}
}
