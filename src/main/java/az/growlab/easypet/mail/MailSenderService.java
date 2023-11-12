package az.growlab.easypet.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String message) throws IllegalArgumentException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("EasyPet");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.setSubject(subject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("An error occur while sending notification email");
        }
        javaMailSender.send(mimeMessage);
    }
}
