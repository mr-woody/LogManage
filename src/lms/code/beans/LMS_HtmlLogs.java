package lms.code.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "LMS_HtmlLogs")
public class LMS_HtmlLogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "S_LMS_HtmlLogs")
	@SequenceGenerator(name = "S_LMS_HtmlLogs", allocationSize = 1, initialValue = 1, sequenceName = "S_LMS_HtmlLogs")
	public long htmlID;
	private long messageId;
	@Column(columnDefinition="LONGTEXT default null")
	public Date errorData;//异常数据
	public long ct;//日志创建时间

	// [start] get and set methods
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public Date getErrorData() {
		return errorData;
	}

	public void setErrorData(Date errorData) {
		this.errorData = errorData;
	}
	// [end]
	
}
