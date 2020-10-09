package yimin.ego.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/10/7 17:40
 *   @Description :
 *
 */
@Component
public class MyMailSender {

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void send(String to, String orderId){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("You have successfully bought the items");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("jqk.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            String page = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            helper.setText(page, true);
            javaMailSender.send(message);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
