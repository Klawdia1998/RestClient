package itm;

import itm.model.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Main {
    private final String url = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    static String result = "";

    public Main() {
        String sessionId = getAllUsers();
        headers.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.addUser();
        main.updateUser();
        main.deleteUser(3L);
        if (result.length() != 18) {
            System.out.println("Ошибка");
        } else {
            System.out.println("Итоговый код - " + result);
        }
    }

    public String getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("set-cookie")));
    }

    public void addUser() {
        User user = new User("James", "Brown", (byte) 25);
        user.setId(3L);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        String request = restTemplate.postForEntity(url, httpEntity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void updateUser() {
        User user = new User("Thomas", "Shelby", (byte) 20);
        user.setId(3L);
        HttpEntity<User> httpEntity = new HttpEntity<>(user, headers);
        String request = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void deleteUser(@PathVariable Long id) {
        HttpEntity<User> httpEntity = new HttpEntity<>(null, headers);
        String request = restTemplate.exchange(url + "/" + id, HttpMethod.DELETE, httpEntity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }
}
