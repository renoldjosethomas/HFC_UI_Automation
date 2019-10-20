package clubkitchen.utils;

import java.io.File;
import java.util.ArrayList;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtility 
{
	private HtmlEmail email;
	private ExcelUtility excel;
	private String fromEmail = "redasterixtest@gmail.com";
	private String fromEmailPassword = "9074079045";
	
	public EmailUtility()
	{
		excel = new ExcelUtility();
	}
	
	public void sendEmailReport()
	{
		try
		{
			setEmailConfig(fromEmail, fromEmailPassword);
			setEmailContent(excel.getCellData(1,1),excel.getCellData(2,1), excel.getCellData(3,1));
			setEmailCC(excel.getRowData(4,1));
			setEmailRecepients(excel.getRowData(5,1));
			setEmailAttachments(excel.getRowData(6, 1));
			email.send();
		}
		catch(Exception sysEx)
		{
			System.out.println("sendEmailReport" + sysEx);
		}
	}
	
	public void setEmailConfig(String emailID, String emailPassword)
	//Email Configuration & Authentication Setting
	{
		try
		{
			email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthentication(emailID, emailPassword);
			email.setSSLOnConnect(true);
			email.setSSLCheckServerIdentity(true);	
			email.setFrom(emailID);	
		}
		catch(Exception sysEx)
		{
			System.out.println("setEmailConfig" + sysEx);
		}
	}

	public void setEmailContent(String emailSubject, String emailContent, String htmlEmbeddFile)
	//Set Email Subject & Content
	{
		try
		{	
			//Set Email Subject and Body
			email.setSubject(emailSubject);
			//Set Email HTML Body
//			email.setMsg(emailContent);
			File htmlFile = new File(htmlEmbeddFile);
			email.embed(htmlFile);
		}
		catch(Exception sysEx)
		{
			System.out.println("setEmailContent " + sysEx);
		}
	}
	
	public void setEmailCC(ArrayList<String> emailCC)
	{
		try
		{
			for(int data = 0; data <= emailCC.size()-1; data++)
			{
				email.addCc(emailCC.get(data));
			}
		}
		catch(Exception sysEx)
		{
			System.out.println("setEmailContent " + sysEx);
		}
	}
	
	public void setEmailRecepients(ArrayList<String> emailRecepients)
	{
		try
		{
			for(int data = 0; data <= emailRecepients.size()-1; data++)
			{
				email.addTo(emailRecepients.get(data));
			}
		}
		catch(Exception sysEx)
		{
			System.out.println("setEmailContent " + sysEx);
		}
	}
	
	public void setEmailAttachments(ArrayList<String> emailAttachments)
	//Attach Plain HTML Files
	{
		try
		{
			EmailAttachment attachment = new EmailAttachment();
			for(int file = 0; file <= emailAttachments.size()-1; file++)
			{
				attachment.setPath(emailAttachments.get(file));
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				email.attach(attachment);
			}
		}
		catch(Exception sysEx)
		{
			System.out.println("setEmailAttachements" + sysEx);
		}
	}
}