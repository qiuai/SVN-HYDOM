package com.hydom.account.ebean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hydom.util.dao.BaseEntity;

/**
 * 技师上下班记录
 * @author WY
 *
 */
@Entity
@Table(name = "t_workLog")
public class WorkLog extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2947602907903271830L;

	/**技师*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "technician_id")
	private Technician technician;
	
	/**上下班状态*/
	//false为没上班，true为上班
	@Column(name="jobstatus")
	private boolean jobstatus = false;

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	public boolean isJobstatus() {
		return jobstatus;
	}

	public void setJobstatus(boolean jobstatus) {
		this.jobstatus = jobstatus;
	}
	
}
