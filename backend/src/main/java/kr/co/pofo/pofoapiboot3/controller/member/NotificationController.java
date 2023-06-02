package kr.co.pofo.pofoapiboot3.controller.member;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.pofo.pofoapiboot3.entity.Notification;
import kr.co.pofo.pofoapiboot3.service.NotificationService;

@CrossOrigin
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public void postNotification(@RequestBody Notification notification) {
        service.create(notification);
    }

    @DeleteMapping
    public void deleteNotification(@RequestBody Notification notification) {
        service.delete(notification);
    }

    @GetMapping("list/{id}")
    public List<Notification> notificationList(@PathVariable int id){
        List<Notification> list = service.getList(id);
        return list;
    }

    @PutMapping("changeflag/{id}")
    public String changeClickFlag(@PathVariable int id){
        service.updateFlag(id);
        return "ok";
    }

    @DeleteMapping("remove/{id}")
    public String remove(@PathVariable int id){
        service.remove(id);
        return "ok";
    }
}
