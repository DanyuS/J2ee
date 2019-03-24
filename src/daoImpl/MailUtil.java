package daoImpl;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
    public void sendMail(String email,String code) throws AddressException,MessagingException{
    	Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("1079933502@qq.com"));
        // 设置收件人邮箱地址 
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(email)});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject("Yummy注册激活码");
        // 设置邮件内容
        
        String context="这是一封激活邮件,激活请点击以下链接————http://localhost:8080/Yummy/CheckMail.html?~code~" + code;
        message.setText(context);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect("1079933502@qq.com", "gljabqdgpwanghed");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("邮件成功发送!");
    }
    /*public static void main(String[] args) throws AddressException,MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("1079933502@qq.com"));
        // 设置收件人邮箱地址 
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxxxxxx@qq.com")});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//一个收件人
        // 设置邮件标题
        message.setSubject("Yummy注册激活码");
        // 设置邮件内容
        message.setText("邮件内容邮件内容邮件内容xmqtest");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect("xxxxxxx@qq.com", "xxxxxxxx");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        System.out.println("邮件成功发送!");
    }*/
}
//public class MailUtil implements Runnable {
/*	// =========收件人的信息========== 
	private String email;// 收件人邮箱
	private String code;// 激活码
	// =========初始化=============== 

	public MailUtil(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public void run() {
		// 1.创建连接对象javax.mail.Session
		// 2.创建邮件对象 javax.mail.Message
		// 3.发送一封激活邮件

		String from = "xxxxxxxxxx@qq.com";// 发件人电子邮箱
		String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)
		Properties properties = System.getProperties();// 获取系统属性
		properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
		properties.setProperty("mail.smtp.auth", "true");// 打开认证

		try {
			// QQ邮箱需要下面这段代码，163邮箱不需要
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			properties.put("mail.smtp.ssl.enable", "true");
			properties.put("mail.smtp.ssl.socketFactory", sf);

			// 1.获取默认session对象（创建连接对象，连接到邮箱服务器）
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("xxxxxxxxxx@qq.com", "xxxxxxxxxxx"); // 发件人邮箱账号、密码
				}
			});

			// 2.创建邮件对象
			Message message = new MimeMessage(session);
			// 2.1设置发件人
			message.setFrom(new InternetAddress(from));
			// 2.2设置接收人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			// 2.3设置邮件主题
			message.setSubject("这是一封账号激活邮件");
			// 2.4设置邮件内容
			String content = "<html><head></head><body><h1>这是一封激活邮件,激活请点击以下链接</h1><h3><a href='http://localhost:3000/RegisterWeb/ActiveServlet?code="
					+ code + "'>http://localhost:8080/Yummy/CheckMail?code=" + code
					+ "</href></h3></body></html>";
			message.setContent(content, "text/html;charset=UTF-8");

			// 3.发送邮件
			Transport.send(message);
			System.out.println("邮件成功发送!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

