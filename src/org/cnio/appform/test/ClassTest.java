package org.cnio.appform.test;

import java.io.*;
import org.cnio.appform.entity.*;
import org.cnio.appform.jaas.AppUserValidation;

import org.cnio.appform.util.*;
import org.cnio.appform.audit.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import static org.hibernate.criterion.Restrictions.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.json.simple.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import java.io.UnsupportedEncodingException;
import java.net.*;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.cnio.appform.jaas.AppUserValidation;
public class ClassTest {

	Session hibSess;
	
	public ClassTest () {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		hibSess = sf.openSession();
	}
	
	
// it works con createinterview
	public Session getHibSes () {
		return hibSess;
	}
	
	
// Falla con createinterview
	public Session getCurrentHibSes () {
		hibSess = HibernateUtil.getSessionFactory().getCurrentSession();
		
		return hibSess;
	}
	
// Falla con createinterview
	public Session getCurrentSession () {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
	
	
	public Session openSession () {
		hibSess = HibernateUtil.getSessionFactory().openSession();
		
		return hibSess;
	}
	
	
	public void closeHibSes () {
		hibSess.close();
	}
	
	
	
/**
 * @param args
 */
	public static void main(String[] args) {
System.out.println("Starting script...");		
		
		ClassTest test = new ClassTest ();
System.out.println("... after initializing scriptlet");
		String username = "cmurta@imim.es", userid = "100";
		String ieName = "pcunningham@tcd.ie", ukName="barny", deName="fmeyer",
				seName = "glarsson", itName="apietrielli";
		String intrvId = "1301";
//		String patCode = "01010101";
//		String patCode = "17171717";
		String patCode = "011030";
		String secId = "200";
		
		Integer usrId = Integer.decode(userid);
		List<AbstractItem> itemsSec = null;
		
		username = "lpalencia";
		userid = "101";
//		userid = "2";
		usrId = Integer.decode(userid);
		AppUser user = (AppUser)test.getHibSes().get(AppUser.class, usrId);
		AppUserCtrl usrCtrl = new AppUserCtrl (test.getHibSes());
		

		IntrvFormCtrl intrFormCtrl = new IntrvFormCtrl(test.getHibSes());
		IntrvController intrCtrl = new IntrvController (test.getHibSes());

		Project prj = (Project)test.getHibSes().get(Project.class, 351);
		Interview src = (Interview)test.getHibSes().get(Interview.class, 2400); // 3700);
		AppGroup grp = usrCtrl.getPrimaryGroup(user);
		grp = (AppGroup)test.getHibSes().get(AppGroup.class, 4);
		
/*		
		List<AnswerItem> ansItems = src.getAnswerItems();
System.out.println ("Items for interview: "+src.getName()+" ("+src.getId()+")");
		int i = 0;
		for (i=0; i<ansItems.size(); i++) {
			Object curr = (Object)ansItems.get(i);
			if (curr instanceof EnumType) {
				EnumType en = (EnumType)curr;
				System.out.println (en.getName()+" ("+en.getId()+") ");
			}
		}
*/
System.out.println ("Replicating: "+src.getName()+" for "+prj.getName()+" ("+grp.getName()+")");
		Interview cloned = intrCtrl.replicateIntrv (2400, grp, prj, "RecogidaMuestraFAM");

		
//		test.testDelete(5050, test);
		
		/*		
		Integer newIntrvId = -1;
		newIntrvId = intrCtrl.createInterview("Kagallon", "zo", 351, usrId, false, true, false);
		
		if (newIntrvId != -1)
			System.out.println ("Interview not created :-(");
		else {
			Interview newIntrv = (Interview)test.getHibSes().get(Interview.class, newIntrvId);
			
			System.out.println("New interview: "+newIntrv.getName()+" ("+newIntrv.getId()+")");
		}
*/
		
		
/*
		Interview idcIntrv = (Interview)test.getHibSes().get(Interview.class, 1301),
				ispIntrv = (Interview)test.getHibSes().get(Interview.class, 1750),
				dietaIntrv = (Interview)test.getHibSes().get(Interview.class, 1250),
				qalIntrv = (Interview)test.getHibSes().get(Interview.class, 1350),
				engQes = (Interview)test.getHibSes().get(Interview.class, 1650),
				ukIntrQa = (Interview)test.getHibSes().get(Interview.class, 1854);

//		Patient pat = intrFormCtrl.getPatientFromCode(IntrvFormCtrl.NULL_PATIENT);


//		List<AppGroup> userGroups = usrCtrl.getGroups(user);
		System.out.println("*=====================================*");
//		AppGroup grp = usrCtrl.getMainGroup(usrCtrl.getUser(ieName));
//		intrCtrl.createInterview("TestLog Intrv", "Nothing to say", 50, 100);
		
		
		Transaction tx = null;
		AppGroup deGrp = usrCtrl.getPrimaryGroup(usrCtrl.getUser("gereditor")),
						ukGrp = usrCtrl.getPrimaryGroup(usrCtrl.getUser("ukeditor")),
						ieGrp = usrCtrl.getPrimaryGroup(usrCtrl.getUser("ieuser"));
		
		

		Patient patbis = (Patient)test.getHibSes().get(Patient.class, new Integer(702));
		AppUser userBis = (AppUser)test.getHibSes().get(AppUser.class, 503);
*
		try {
			List<Interview> lInt = intrCtrl.getInterviews(50, 4);
			JSONArray arrIntrvs = new JSONArray ();
			
			for (Interview intrv: lInt) {
				JSONObject aux = new JSONObject ();
				aux.put("id", intrv.getId());
				aux.put("name", intrv.getName());
				
				arrIntrvs.add(aux);
			}
			JSONObject jsonOut = new JSONObject ();
			jsonOut.put("res", 1);
			jsonOut.put("intrvs", arrIntrvs);
			String jsonStr = jsonOut.toJSONString();
			
			System.out.println(jsonStr);
		}
		catch (Exception ex) {
ex.printStackTrace();				
		}			
*/
		test.closeHibSes();
		
System.out.println ("acabose");
	}
	
	
	
	
	public void testDelete (Integer idIntrv, ClassTest test) {
		Transaction tx = null;
		try {
			tx = test.hibSess.beginTransaction();
			Interview it = 
				(Interview)test.hibSess.get(Interview.class, 5000);
			String theName = it.getName();
		
System.out.println ("About to delete "+theName);
			test.hibSess.delete(it);
			tx.commit();
		}
		catch (Exception ex) {
			tx.rollback ();
			
			ex.printStackTrace();
		}
		
System.out.println ("If no exception, interview with id "+idIntrv+" was deleted");
	}
	
	
	
	
	
	public List<String> getCodes () {
		List<String> aux = null;
//		openHibSession ();
		
		Integer usrid = 503;
		String intrvid = "50";
		
		if (intrvid == null || usrid == null)
			return aux;
		
		AppUserCtrl usrCtrl = new AppUserCtrl (hibSess);
		AppUser usr = (AppUser)hibSess.get(AppUser.class, usrid);
		AppGroup actGrp = usrCtrl.getSecondaryActiveGroup (usr);
		Interview intrv = (Interview)hibSess.get(Interview.class, Integer.parseInt(intrvid));
		
		if (usr != null && actGrp != null && intrv != null) {
			Transaction tx = null;
			String strQry = "select p.codpatient from Patient p join p.performances pf where " +
					"pf.interview=:intrv and pf.group=:grp and " +
					"p.codpatient <> '"+org.cnio.appform.util.IntrvFormCtrl.NULL_PATIENT+
					"' and p.codpatient <> '"+org.cnio.appform.util.IntrvFormCtrl.TEST_PATIENT+
					"' order by 1";
			
			try {
				tx = hibSess.beginTransaction();
				Query qry = hibSess.createQuery(strQry);
				qry.setEntity("intrv", intrv);
				qry.setEntity("grp", actGrp);
				
				aux = qry.list();
				tx.commit();
			}
			catch (HibernateException ex) {
				if (tx != null)
					tx.rollback();
			}
			
		}
		
		return aux;
	}
	
	
	
	public List<Object[]> getIntrvStruct (int prjId, String intrvName) {
		/*	
			select i.idinterview, s.idsection, i.name, s.name, s.section_order
			from interview i, section s
			where upper(i.name) like upper('%qes%')
			  and i.idinterview = s.codinterview
			order by 1, 5
		*/	
			
		String strQry = "select i.id, s.id, i.name, s.name, s.sectionOrder" +
				" from Section s join s.parentInt i where " +
				"UPPER(i.name) like UPPER ('%"+intrvName+"%') "+
//				"i.id in (50,1650,1700,2000)"+
//				"and i.parentPrj=:prj "+
				"order by 1, 5";

		Transaction tx= null;
		List<Object[]> lSecs = null;
		try {
			tx = hibSess.beginTransaction();
			Project prj = (Project)hibSess.get(Project.class, prjId);
			Query qry = hibSess.createQuery(strQry);
//			qry.setEntity("prj", prj);
			lSecs = qry.list();
			tx.commit();
		}
		catch (HibernateException hibEx) {
			if (tx != null)
				tx.rollback();
		}
		
		return lSecs;
	}
	
	
	private void delItem (String itemId) {
		
		String 	id = itemId, jsonOut= "", sons="", msgOut;
		Transaction tx = null;
		boolean res;
		
		AbstractItem it = 
			(AbstractItem)hibSess.get(AbstractItem.class, Long.decode(id));
		
myPrt ("Start deleting item: id="+it.getId()+"; val="+it.getContent());
//I gotta delete the sons before deleting the container object
		sons=(it.getContainees().size() > 0)? ",\"sons\":\"": "";
		for (int i=it.getContainees().size(); i>0 ; i--) {
			AbstractItem son = it.getContainees().get(i-1);
			son.setContainer(null);
			if (son instanceof Question)
				HibController.ItemManager.deleteAnswers(hibSess, (Question)son);
			
			sons += son.getId()+",";
		}
		
		tx = hibSess.beginTransaction();
		sons = sons.equalsIgnoreCase("")? "": 
										sons.substring(0, sons.length()-1)+"\"";
		it.removeContainees();
		tx.commit();

		if (it instanceof Question)
			HibController.ItemManager.deleteAnswers(hibSess, (Question)it);

		tx.begin();
//out.print (it.getId()+".-"+it.getContent());
		if (it instanceof Question) {
			if (((Question)it).getMandatory () == 0) {
				hibSess.delete(it);
				msgOut = "ok";
				res = true;
			}
			else  { // mandatory = 1, so the question is considered as MANDATORY
				msgOut = "The Question can not be deleted";
				res = false;
			}
		}
		else {
			hibSess.delete(it);
			msgOut = "ok";
			res = true;
		}
		tx.commit();
	}
	
	
	
/*
select qai.codquestion, qai.codansitem
from interview i, section s, item it, question q, question_ansitem qai
where i.idinterview = s.codinterview
	and i.idinterview = 50
	and s.idsection = it.idsection
	and it.iditem = q.idquestion
	and q.idquestion = qai.codquestion
order by 1;
*/
	private void updateTypes4Intrv (Interview intrv)  {
		String hql = "from AnswerItem where intrvOwner is null",
					hqlIntrv = "from AnswerItem where intrvOwner = :intrv";
		
		Session mySes = this.getHibSes();
		IntrvController intrvCtrl = new IntrvController (mySes);
		Transaction tx = null;
		List<AnswerItem> lTypesNull, lTypesIntrv;
//		List<QuestionsAnsItems> lQai = intrvCtrl.getQuestions4Intrv(intrv);
//		List<Integer> answerIds = intrvCtrl.getAnswers4Intrv(intrv);
		
myPrt("Updating types to interview "+intrv.getName());

		try {
			tx = mySes.beginTransaction();
			Query hqlQry = this.getHibSes().createQuery(hql), 
						hqlQryIntrv =	this.getHibSes().createQuery(hqlIntrv);
		
// Get the both lists, with interview associated and without interview
			lTypesNull = hqlQry.list();
			hqlQryIntrv.setEntity("intrv", intrv);
			lTypesIntrv = hqlQryIntrv.list();
			
// Next, the "old" id in question_ansitems and answers (stored in lTypesNull)
// has to be substituted by the id corresponding to the same type with interview
// (stored in lTypesIntrv). The correspondence is done by name comparison (both
// have to be the same) 
			int i = 1;
			for (Iterator<AnswerItem> itOldTypes = lTypesNull.iterator(); 
						itOldTypes.hasNext();) {
				AnswerItem oldAi = itOldTypes.next(), newAi=null;
				String oldName = oldAi.getName();
System.out.println(i+". Replacing "+oldName+"("+oldAi.getId()+")");				
// get the new type corresponding to this interview
				for (Iterator<AnswerItem> itNewTypes = lTypesIntrv.iterator();
							itNewTypes.hasNext();) {
					newAi = itNewTypes.next();
					if (oldName.equals(newAi.getName()))
						break; // we found the new type
					
					newAi = null;
				}
				
/* newAi is the AnswerItem from the interview				
				if (newAi != null) {
System.out.println("QuestionsAnsItems...");
					for (Iterator<QuestionsAnsItems> itQai = lQai.iterator(); itQai.hasNext();) {
						QuestionsAnsItems qai = itQai.next();
						if (qai.getTheAnswerItem() == oldAi)
							qai.setTheAnswerItem(newAi);
					}
System.out.println("Answers");					
					for (Iterator<Integer> itAnsId = answerIds.iterator(); itAnsId.hasNext();) {
						Integer ansId = itAnsId.next();
						Answer answer = (Answer)mySes.get(Answer.class, ansId);
						if (answer.getAnswerItem() == oldAi)
							answer.setAnswerItem(newAi);
					}
				}*/
				i++;
			}
			
			tx.commit();
System.out.println (i +" items substitued");
		}
		catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			
			ex.printStackTrace();
		}
	}
	
	
	
	private void printIntrvTypes (Interview intrv) {
		String hql = "from AnswerItem ai where ai.intrvOwner = :intrv";
		Transaction tx = null;
		List<AnswerItem> lAi;
myPrt ("Printing types for interview '"+intrv.getName()+"'");		
		try {
			tx = this.getHibSes().beginTransaction();
			Query qry = this.getHibSes().createQuery(hql);
			qry.setEntity("intrv", intrv);
			lAi = qry.list();
			
			for (Iterator<AnswerItem> aiIt = lAi.iterator(); aiIt.hasNext();) {
				AnswerItem ai = aiIt.next();
				myPrt(ai.getId()+":"+ai.getName());
				
				if (ai instanceof EnumType) {
					for (Iterator<EnumItem> enIt = ((EnumType)ai).getItems().iterator();
							 enIt.hasNext(); ) {
						EnumItem ei = enIt.next();
						myPrt("- "+ei.getName()+":"+ei.getValue());
					}
				}
			}
			
			tx.commit();
		}
		catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		}
		
		
	}
	
	
	
	private void assignTypes2Intrv (Interview intrv) throws CloneNotSupportedException {
		String hql = "from AnswerItem where intrvOwner is null";
		Transaction tx = null;
		List<AnswerItem> lAi;
		Session mySes = this.getHibSes();
myPrt("Assigning types to interview "+intrv.getName());

		try {
			tx = mySes.beginTransaction();
			Query hqlQry = this.getHibSes().createQuery(hql);
			
			lAi = hqlQry.list();
			for (Iterator<AnswerItem> itAi = lAi.iterator(); itAi.hasNext();) {
				AnswerItem theAi = itAi.next(), newAi;
				if (theAi instanceof AnswerType)
					newAi = (AnswerItem)((AnswerType)theAi).clone();
				
				else
					newAi = (AnswerItem)((EnumType)theAi).clone();

				Long newAiId = (Long)mySes.save(newAi);
myPrt("setting: "+newAiId+" -> "+newAi.getName());

				intrv.setAnswerItem(newAi);
				
				mySes.save(newAi);
			}
			
			tx.commit();
		}
		catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			
			ex.printStackTrace();
		}
		catch (CloneNotSupportedException cloEx) {
			if (tx != null)
				tx.rollback();
			
			cloEx.printStackTrace();
		}
		
	}
	
	
	private Interview replicateIntrv (Session hibSes, AppGroup grp) {
		IntrvController intrvCtrl = new IntrvController (hibSes);
		
		Interview intrvAux = (Interview)hibSes.get(Interview.class, 1700), 
							intrvBis = null;
		Transaction tx = null;
		try {
			tx = hibSes.beginTransaction();
			intrvBis = (Interview)intrvAux.clone();
			Integer intId = (Integer)hibSes.save(intrvBis);
			
//			java.io.Serializable savedIntr = test.getHibSes().save(intrvBis);
			
			RelIntrvGroup rig = new RelIntrvGroup (grp, intrvBis);
			hibSes.save(rig);
			
			tx.commit();
System.out.println("the new intrvId: "+intId);
			
		}
		catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			
			ex.printStackTrace();
		}
		return intrvBis;
	}
	
	
	
	
	
	private Interview replicateIntrv (String ieName, AppUserCtrl usrCtrl, Session hibSes) {
		IntrvController intrvCtrl = new IntrvController (hibSes);
		AppUser user = usrCtrl.getUser(ieName);
		
		Interview intrvAux = (Interview)hibSes.get(Interview.class, 1700), 
							intrvBis = null;
		Transaction tx = null;
		try {
			tx = hibSes.beginTransaction();
			intrvBis = (Interview)intrvAux.clone();
			Integer intId = (Integer)hibSes.save(intrvBis);
			
//			java.io.Serializable savedIntr = test.getHibSes().save(intrvBis);
			if (user != null) {
				AppGroup mainGroup = usrCtrl.getPrimaryGroup(user);
			
				RelIntrvGroup rig = new RelIntrvGroup (mainGroup, intrvBis);
				hibSes.save(rig);
			
				tx.commit();
System.out.println("the new intrvId: "+intId);
			}
			else {
System.out.println("No user with username: "+ieName);
				tx.rollback();
			}
		}
		catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			
			ex.printStackTrace();
		}
		return intrvBis;
	}
	
	
	
	
	
/**
 * Cloning interview test with binary streams...
 * @param intrvId
 * @return
 */	
	public Interview cloneIntrv (Integer intrvId) {
		Interview source = null;
		Interview copy = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out;
		try
		{
			source = (Interview)getCurrentSession().get(Interview.class, intrvId);
		
// Materialize the associations by trying to reach 'em.
			List<Section> sections = source.getSections();
//			Iterator iterator = accountVersionStatuses.iterator();
			out = new ObjectOutputStream(bos);
			try {
				out.writeObject(source);
				out.flush();
			}
			finally {
				out.close();
			}
		
		// Retrieve an input stream from the byte array and read
		// a copy of the object back in.
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
		
			copy = (Interview)in.readObject();
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
//		copyOfAccountVersion.setAccountVersionId(null);
		copy.setId(null);
//		copyOfAccountVersion.setOptimisticLockVersion(0);

//		Set copiedClasses = copyOfAccountVersion.getAccountVersionStatuses();
		List<Section> copiedSecs = copy.getSections();
		for (Iterator<Section> iter = copiedSecs.iterator(); iter.hasNext();) {
			Section element = (Section)iter.next();
			element.setId(null);
		}

		getCurrentSession().save(copy);
		getCurrentSession().flush();
		
		return copy;
	}

//////////////////////////////////////////////////////////////////////////
// END OF REPLICATION TEST METHODS ///////////////////////////////////////
//////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	private void printUserRoles (AppUserCtrl usrCtrl) {
		List<Object[]> userRoles = usrCtrl.getAllUsers();
		int i = 0;
		AppUser old = null;
		while (i < userRoles.size()) {
			Object[] pair = userRoles.get(i);
			AppUser u = (AppUser)pair[0];
			AppuserRole ur = (AppuserRole)pair[1];
			
			if (u == old)
				System.out.print(", "+ur.getTheRole().getName());
			
			else {
				System.out.println();
				System.out.print(u.getUsername()+": "+ur.getTheRole().getName());
				old = u;
			}
			i++;
		}
	}
	
	
	private void refillForm (Session hibSes) {
		String username = "cmurta@imim.es", userid = "100";
//		String patCode = "01010101";
		String patCode = "17171717";
//		String patCode = "012345";
		String secId = "200";
//		String secId = "50";
		String itrtd="<tr><td>", etrtd="</td></tr>";
		
		Integer usrId = Integer.decode(userid);
		List<AbstractItem> itemsSec = null;
		Section sec = null;
		
		if (secId != null) { // it is suppossed than we have always a secId
			sec = (Section)hibSes.get(Section.class, Integer.decode(secId));
//			itemsSec = HibernateUtil.getItems4Section (hibSes, Integer.decode(secId));
			itemsSec = HibernateUtil.getContainers4Section(hibSes, sec);
		}
		
//		HibController.RenderEng.viewSection(hibSes, sec);

		AppUser user = (AppUser)hibSes.get(AppUser.class, usrId);
		IntrvFormCtrl intrCtrl = new IntrvFormCtrl(hibSes);
		Patient pat = intrCtrl.getPatientFromCode(patCode);
		Integer patId = pat.getId();
		
		System.out.println ("<form id=\"sectionForm\" name=\"sectionForm\">");
		System.out.println ("<span class=\"titleForm\">"+sec.getName()+"</span><br><br>");
		System.out.println ("<table>");
		System.out.println ("<input type=\"hidden\" name=\"patId\" value=\""+patId+"\" />");
		System.out.println ("<input type=\"hidden\" name=\"secId\" value=\""+secId+"\" />");
		
		
//		HibController.RenderEng.clearHtmlStr();
		RenderEng render = new RenderEng ();
		for (AbstractItem ai : itemsSec) {
			render.clearHtmlStr();
			render.html4Item(hibSes, ai, patId, intrCtrl);
//			strItem (hibSes, ai, patId, intrCtrl);
			String html = render.getHtmlStr();
			System.out.println (html);
		}	
		
		
		
/*		
		String msg = "";
		for (AbstractItem ai : itemsSec) {
//			singleItem (hibSes, ai, patId, intrCtrl);

//			singleItem (hibSes, ai, patId, intrCtrl);
			strItem (hibSes, ai, patId, intrCtrl);
		} // loop for
*/
		
	}

	
	
	
/*****************************************************************************/		
/*****************************************************************************/
/*****************************************************************************/
/*****************************************************************************/	
	private void singleItem (Session hibSes, AbstractItem ai, Integer patId,
													IntrvFormCtrl intrCtrl) {
		String itrtd="<tr><td>", etrtd="</td></tr>";
		
		if (ai.getContainees().size() > 0) {
// Imprimo el padre
// consigo sus hijos
// Por si es repetible, renderizo sus preguntas separadamente
			System.out.println(itrtd+"<span id=\"t"+ai.getId()+"\"><b>"+
												StringEscapeUtils.escapeHtml(ai.getContent())+
					"</b></span><br>"+etrtd);
			
			
			System.out.println("<table>");
//			out += "<table>";
		}
		
		
		if (ai instanceof Text) {
			System.out.println(itrtd+"<b>"+StringEscapeUtils.escapeHtml(ai.getContent())+
								"</b><br>"+etrtd);
	//System.System.out.println("* "+ai.getContent());
		} 
		else if (ai instanceof Question) {
			
			List<Object[]> answers = intrCtrl.getAnswers ((Question)ai, patId, 1);
			List<AnswerItem> cai = 
					HibernateUtil.getAnswerTypes4Question(hibSes, ai);
			
	//		if (cai.size() > 1) // question with several answers
				System.out.println (itrtd+StringEscapeUtils.escapeHtml(ai.getContent())+etrtd);
// The answeritems are retrieved in the correct order			
			int orderCai = 1;
			System.out.println(itrtd);
			String inputVal;
			String numQuestion;
			for (AnswerItem ansi : cai) {
//get the answer for this question...
				Object[] answer = null;
				if (answers != null)
					 answer = answers.get(orderCai-1);
				
				inputVal = (answer != null)? (String)answer[1]: "";
				numQuestion = (answer!=null)? 
											Integer.toString(((Integer)answer[2]).intValue()): "1";
											
				if (ansi instanceof AnswerType) { // simple answer (label, number)
					AnswerType anst = (AnswerType)ansi;
					String maxLen = "maxlength=\"", maxSize="size=\"";			
					if (ansi.getName().equalsIgnoreCase("number")) {
						maxLen+="10\"";
						maxSize+="12\"";
					}
					else {
						maxLen+="128\"";
						maxSize+="32\"";
					}
//the name of the answer will be like q154-1-1, 154 idquesion
//the first 1 answer number (to allow repetitivity, next one will be 2)
//and the last 1 is the answer order
//besides, we gotta check the value...
					System.out.println("&nbsp;<input type=\"text\" name=\"q" + ai.getId() +
							"-"+numQuestion+"-"+ orderCai + "\" "+maxLen+" "+maxSize+
							" value=\""+inputVal+ "\"/>");
/*							" onblur=\"intrvFormCtrl.chk" + ansi.getName()
							+ "(this)\"/>"); */
				} 
				else if (ansi instanceof EnumType) {
					String sel = "selected=\"selected\"";
					List<EnumItem> ceni = 
						HibController.EnumTypeCtrl.getEnumItems(hibSes, (EnumType) ansi);
							
					System.out.println("&nbsp;<select name=\"q" + ai.getId() + 
							"-"+numQuestion+"-"+ orderCai + "\" style=\"width:200px;\">");
					for (EnumItem eni : ceni) {
						if (eni.getValue().equalsIgnoreCase(inputVal))
							System.out.println("<option value=\"" + eni.getValue() + "\" " +
									sel+" >"
									+ StringEscapeUtils.escapeHtml(eni.getName()) + "</option>");
						else
							System.out.println("<option value=\"" + eni.getValue() + "\">"
									+ StringEscapeUtils.escapeHtml(eni.getName()) + "</option>");
					}
					
					System.out.print("</select>");
				} 
				else {
					System.out.println("ERROR: Not implemented case -&gt: "
							+ StringEscapeUtils.escapeHtml(ansi.getName()) + "<br>");
				}
				
				if (orderCai < cai.size()) {
					System.out.print ("&nbsp;-&nbsp;");
					orderCai++;
				}/*
				else
					System.out.println (etrtd); */
			} // end of num of answers for loop
			
			if (((Question)ai).getRepeatable().intValue() == 1) {
				String plusBtn = "<input type=\"button\" name=\"btnPlus\" id=\"btnPlus\"";
				plusBtn += " onclick=\"intrvFormCtrl.repeat(q"+ai.getId()+");\" />";
				System.out.println();
				System.out.println(plusBtn);
			}
				
			System.out.println();
		} // Question case 
		else {
			// Caso imposible
			System.out.print("ERROR: Not implemented case -&gt: "
					+ StringEscapeUtils.escapeHtml(ai.getContent()) + "<br>");
		}
		
		Collection<AbstractItem> items = 
			HibController.ItemManager.getOrderedContainees(hibSes, ai);
		
		if (items != null && !items.isEmpty()) {				
			Iterator<AbstractItem> itItem = items.iterator();
			while (itItem.hasNext())
				singleItem (hibSes, itItem.next(), patId, intrCtrl);
			
			System.out.println(etrtd+"</table>");
		}
		
	}
	
	
	
	private void getIntrv (Session hibSes, String username) {
		String idPrj = "50";
		AppUserCtrl usrCtrl = new AppUserCtrl (hibSes);
		Collection<Interview> intrCol = null;
		List<Section> sections = null;
		Collection<AbstractItem> itemCol;
		IntrvController intrvCtrl = new IntrvController (hibSes);
		
		Project prjAux = (Project)hibSes.get(Project.class, Integer.decode(idPrj));
//		pageContext.setAttribute("project", prjAux);
		AppUser user = usrCtrl.getUser(username);
		Integer usrId = user.getId();
		intrCol = intrvCtrl.getIntr4Proj(Integer.decode(idPrj), usrId);

		Interview intrv = (Interview)intrCol.iterator().next();
		
		sections = HibController.SectionCtrl.getSectionsFromIntrv(hibSes, intrv);
System.out.println("Interview ("+intrv.getId()+"): "+intrv.getName());		
		
		for (Section sec: sections) {
System.out.println("Section("+sec.getId()+"): "+sec.getName());			
			itemCol = HibernateUtil.getItems4Section(hibSes, sec);
	
			// pageContext.setAttribute("items", itemCol);
	
			// si instanceof Text, escribirlo
			// si instanceof Question, montar un item de formulario
			for (AbstractItem ai : itemCol) {
				System.out.println();
				if (ai instanceof Text) {
//					System.out.print(StringEscapeUtils.escapeHtml(ai.getContent()) + "<br>");
System.out.println("* "+ai.getContent());
				} 
				else if (ai instanceof Question) {
System.out.print("* "+ai.getContent());					
					Collection<AnswerItem> cai = 
							HibernateUtil.getAnswerTypes4Question(hibSes, ai);
					
// This is just for beautify
					if (cai.size() > 1) // multiple anser items
						System.out.println();
					
					for (AnswerItem ansi : cai) {
						if (ansi instanceof AnswerType) { // simple answer (label, number)
							AnswerType anst = (AnswerType)ansi;
/*							
							System.out.print(StringEscapeUtils.escapeHtml(ai.getContent())
									+ "&nbsp;<input type='text' name='q" + ai.getId() + "-"
									+ ansi.getAnswerOrder() + "' onblur='chk." + anst.getName()
									+ "'></input><br>"); */
							System.out.print("<input type='text' onblur='javascript'>"+
															anst.getName()+"   ");
						} 
						else if (ansi instanceof EnumType) {
							Collection<EnumItem> ceni = 
								HibController.EnumTypeCtrl.getEnumItems(hibSes, (EnumType) ansi);
/*							
							System.out.print(StringEscapeUtils.escapeHtml(ai.getContent())
									+ "&nbsp;<select name='q" + ai.getId() + "-"
									+ ansi.getAnswerOrder() + "'>");*/
							for (EnumItem eni : ceni) {
								System.out.println("- "+eni.getName()+":"+eni.getValue());
/*								System.out.print("<option value='" + eni.getId() + "'>"
										+ StringEscapeUtils.escapeHtml(eni.getName()) + "</option>");
										*/
							}
//							System.out.print("</select><br>");
						} 
						else {
							System.out.print("ERROR: Not implemented case -&gt: "
									+ StringEscapeUtils.escapeHtml(ansi.getName()) + "<br>");
						}
					}
					System.out.println();
				} // Question case 
				else {
					// Caso imposible
					System.err.print("ERROR: Not implemented case -&gt: "
							+ StringEscapeUtils.escapeHtml(ai.getContent()) + "<br>");
				}
			}
			System.out.println();
			System.out.println();
		} // sections
		
	}
	
	
	private void sampleUserData (Session hibSess) {
		Transaction tx = hibSess.beginTransaction();
		
		try {
/* Patients			
			Patient pat1 = new Patient ("Jos�� Manu��l Rodr��guez"),
			pat2 = new Patient ("Jos�� Mar��a Fernandez");
			pat1.setCodpatient("01007001");
			pat1.setNumHC("HC9834");
			
			pat2.setCodpatient("01007002");
			pat2.setNumHC("HC0101");
			
			hibSess.save(pat1);
			hibSess.save(pat2);
			tx.commit();
*/
	
// HOSPITALS
			Hospital hosp1 = new Hospital ("Hospital del Mar", 1),
			hosp2 = new Hospital ("Hospital Sant Pau", 2),
			hosp3 = new Hospital ("Hospital Vall Hebron", 3),
			hosp4 = new Hospital ("Hospital General Universitario de Elche", 4),
			hosp5 = new Hospital ("Hospital Clinico Universitario, Salamanca", 5),
			hosp6 = new Hospital ("Hospital Cl��nico Universitario, Santiago Compostela", 6),
			hosp7 = new Hospital ("Instituto Universitario Oncolog��a Principado Asturias", 7);
								
			hibSess.save(hosp1);
			hibSess.save(hosp2);
			hibSess.save(hosp3);
			hibSess.save(hosp4);
			hibSess.save(hosp5);
			hibSess.save(hosp6);
			hibSess.save(hosp7);
			tx.commit();
			
// ROLES			
			tx.begin();
			Role admRole = new Role ("admin", "An admin user"),
			 intrRole = new Role ("interviewer", "An user to create and perform interviews");
	
			Integer admId = (Integer)hibSess.save(admRole), 
						intrId = (Integer)hibSess.save(intrRole);
			tx.commit();

// USERS			
			tx.begin();
			AppUser cris = new AppUser ("cmurta@imim.es", "cmurta"),
							laia = new AppUser ("lpalencia", "lpalencia");
			Integer idCris = (Integer)hibSess.save(cris),
							idLaia = (Integer)hibSess.save(laia);
			tx.commit();
			
// USER-ROLES			
			tx.begin();
			AppuserRole cadmRol = new AppuserRole (cris, admRole),
									cintrRol = new AppuserRole (cris, intrRole),
									lintrRol = new AppuserRole (laia, intrRole);
			
			Integer idCadm = (Integer)hibSess.save(cadmRol),
							idCintr = (Integer)hibSess.save(cintrRol),
							idLintr = (Integer)hibSess.save(lintrRol);
									
			tx.commit();
			
System.out.println("Se fin��");
		}
		catch (HibernateException hibEx) {
//						if (tx != null)
			hibEx.printStackTrace();
				tx.rollback();
		}
	}
	
	
	
/**
 * Print out the details of the items in a section
 * @param hibSess, a hibernate session to perform de queries
 * @param sec, the section which is gonna be detailed
 */
	private void getSectionItemsDetails (Session hibSess, Section sec){
		
		Collection<AbstractItem> items = 
			HibernateUtil.getItems4Section(hibSess, sec);
		LogFile.stdout("Items for the section..." + sec.getName());
		if (items == null)
			LogFile.stderr("Section " + sec.getName() + " is empty");

		else {
			Iterator<AbstractItem> itq = items.iterator();
			while (itq.hasNext()) {
				AbstractItem ait = itq.next();

				if (ait instanceof Text) {
					LogFile.stdout("This is a text: " + ait.getId() + ". "
							+ ait.getContent() + "(pos: " + ait.getItemOrder() + ")");
				} 
				else {
					LogFile.stdout("This is a question: " + ait.getId() + ". "
							+ ait.getContent() + "(pos: " + ait.getItemOrder() + ")");

					// get the container for this question if it has
					AbstractItem grouping = ait.getContainer();
					if (grouping != null) {
						LogFile.stdout("Container: " + grouping.getId() + ". "
								+ grouping.getContent());
					}
					
					Collection<AnswerItem> col = 
												HibernateUtil.getAnswerTypes4Question(hibSess, ait);
LogFile.stdout("Number of answer types: "+col.size());

					List<AbstractItem> colItems = 
																	HibController.ItemManager.getContainees(hibSess, ait);
LogFile.stdout("Number of containees. "+colItems.size());

				} // else ait instanceof Question

			} // while
		} // else items not null		
	}

	
	
	private void myPrt (String str) {
		System.out.println (str);
	}
	
	
/**
 * 
 * @param hibSes
 *
	private void createEnumTypes (Session hibSes) {
		
	// First the enum types
		Hashtable<Integer, String> aux = new Hashtable<Integer, String> (); 
		Vector<String> auxNames = new Vector<String>(), 
									auxVals = new Vector<String>();
		aux.put(1, "January");
		aux.put(2, "February");
		aux.put(3, "March");
		aux.put(4, "April");
		aux.put(5, "May");
		aux.put(6, "June");
		aux.put(7, "July");
		aux.put(8, "August");
		aux.put(9, "September");
		aux.put(10, "October");
		aux.put(11, "November");
		aux.put(12, "December");
		HibController.EnumTypeCtrl.createEnumType (hibSes, "Months (EN)", aux);
		
		int curYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		for (int i=1970; i<= curYear; i++) 
			aux.put(new Integer(i), Integer.toString(i));
		HibController.EnumTypeCtrl.createEnumType (hibSes, "Years (EN)", aux);
		
		aux.clear();
		aux.put(1, "Yes");
		aux.put(2, "No");
		aux.put(99, "Unknown");
		HibController.EnumTypeCtrl.createEnumType (hibSes, "Yes-No (EN)", aux);
				
		aux.put(1, "Black cigarettes");
		aux.put(2, "Blonde cigarettes");
		aux.put(3, "Black tobacco");
		aux.put(4, "Blonde tobacco");
		aux.put(5, "Habanos");
		aux.put(6, "Cigars");
		aux.put(7, "Pipe");
		HibController.EnumTypeCtrl.createEnumType (hibSes, "Tobacco Types (EN)", aux);
	}
*/	
}



