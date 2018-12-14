package com.hzit.dao;

import java.util.List;

import com.hzit.entity.Student;

public interface StudentDao {

	public boolean save(Student currentUser);
//		Session session = HBUtil.getCurrentSession();
//		Transaction ts = session.beginTransaction();
//		try {
//			session.saveOrUpdate(currentUser);
//			ts.commit();
//		} catch (Exception e) {
//			System.out.println("ÃÜÂëĞŞ¸ÄÊ§°Ü£¬»Ø¹ö¡£¡£¡£");
//			ts.rollback();
//			return false;
//		}
//		return true;
//	}
//
	public List<Student> getStudentList();
//		 List<Student> list = null;
//		 Session session = HBUtil.getCurrentSession();
//			Transaction ts = session.beginTransaction();
//			try {
//				list = session.createQuery("from Student").list();
//				ts.commit();
//			} catch (Exception e) {
//				ts.rollback();
//				return list;
//			}
//			return list;
//	}

}
