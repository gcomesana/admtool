package org.cnio.appform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.SequenceGenerator;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="question_ansitem")
public class QuestionsAnsItems {
	@Id
	@Column(name="id")
	@SequenceGenerator(sequenceName="question_ansitem_id_seq", name = "QASeqGen")
	@GeneratedValue(generator="QASeqGen", strategy = SEQUENCE)
	private Long id;
//	@ManyToOne (cascade={CascadeType.ALL})
	@ManyToOne (targetEntity=Question.class,cascade={CascadeType.ALL})
	@JoinColumn(name="codquestion")
	@ForeignKey(name="fk_question")
	private Question theQuestion;
	
//	@ManyToOne (cascade={CascadeType.ALL})
	@ManyToOne (targetEntity=AnswerItem.class, cascade=CascadeType.ALL)
	@JoinColumn(name="codansitem")
	@ForeignKey(name="fk_answer_item")
	private AnswerItem theAnswerItem;
	
	@Column(name="answer_order")
	private Long answerOrder;
	
	@Column(name="pattern")
	private String pattern;
	
	
	public QuestionsAnsItems () { 
		pattern = "";
	}
	
	public QuestionsAnsItems (Question q, AnswerItem a) {
		this ();
		
		this.theAnswerItem = a;
		this.theQuestion = q;
		
// Guarantee referential integrity
		q.getQuestionAnsItems().add(this);
		a.getQuestionsAnsItems().add(this);
/*		
		anUser.getUserRoles().add(this);
		aRole.getUserRoles().add(this);
*/
/*
 * or, for us
 * this.user.addUserRole(this);
 * this.role.addUserRole(this);
 * because we implement the method
 */		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Question getTheQuestion() {
		return theQuestion;
	}


	public void setTheQuestion(Question theQuestion) {
		this.theQuestion = theQuestion;
		
		theQuestion.getQuestionAnsItems().add(this);
	}


	public AnswerItem getTheAnswerItem() {
		return theAnswerItem;
	}


	public void setTheAnswerItem(AnswerItem theAnswerItem) {
		this.theAnswerItem = theAnswerItem;
		
		theAnswerItem.getQuestionsAnsItems().add(this);
	}


	public Long getAnswerOrder() {
		return answerOrder;
	}


	public void setAnswerOrder(Long answerOrder) {
		this.answerOrder = answerOrder;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
	public Object clone () throws CloneNotSupportedException {
		QuestionsAnsItems qai = 
				new QuestionsAnsItems(this.theQuestion, this.theAnswerItem);
		qai.setId(null);
		qai.setPattern(this.pattern);
		qai.setAnswerOrder(this.answerOrder);
		
		return qai;
	}
	
}
