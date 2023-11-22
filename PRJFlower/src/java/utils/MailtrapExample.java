import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailtrapExample {

    public static void main(String[] args) {
        final String username = "your_mailtrap_username"; // Thay bằng tên người dùng Mailtrap của bạn
        final String password = "your_mailtrap_password"; // Thay bằng mật khẩu Mailtrap của bạn

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", "2525"); // Sử dụng cổng 2525 cho Mailtrap

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@gmail.com")); // Địa chỉ email người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient@example.com")); // Địa chỉ email người nhận
            message.setSubject("Mailtrap Example");
            message.setText("This is a Mailtrap example email.");

            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
