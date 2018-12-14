package com.hzit.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hzit.entity.Student;
import com.hzit.service.StudentService;
import com.hzit.util.HBUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author tomato
 *
 */
/**
 * @author tomato
 *
 */
/**
 * @author tomato
 *
 */
public class StudentAction {
	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	private StudentService studentService;

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	/**
	 * ÃÌº”Student
	 * 
	 * @return
	 */
	public String addStudent() {
		Logger log = Logger.getLogger("StudentAction");
		log.info("÷¥––addStudent==============");
		studentService.save(student);
		return "success";
	}

	public String login() {
		Session session = HBUtil.getCurrentSession();
		Transaction ts = session.beginTransaction();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession httpSession = request.getSession();
		try {
			Student result = (Student) session
					.createQuery("from Student s where s.id=:studentId and s.password = :password")
					.setString("studentId", student.getId()).setString("password", student.getPassword())
					.uniqueResult();

			ts.commit();
			if (result != null) {

				httpSession.setAttribute("userLevel", 1);

				httpSession.setAttribute("currentUser", result);

				return "success";
			} else {
				request.setAttribute("error", "’À∫≈ªÚ’ﬂ√‹¬Î¥ÌŒÛ!");
				return "error";
			}
		} catch (Exception e) {

			System.out.println("login:rollback");
			e.printStackTrace();
			ts.rollback();
		}

		return "error";
	}

}
