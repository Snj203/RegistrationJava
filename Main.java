package org.example;

import org.example.certFetcher.CertificateFetcher;
import org.example.certFetcher.LoadCertificate;
import org.example.certFetcher.SSLContextSetup;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class Main {
    public static void main(String[] args) {
        String host = "195.38.164.139";
        int port = 443;

        String getRoute = "/login/signup.php";
        String postRoute = "/login/signup.php";

        CustomSocketJava customSocket = new CustomSocketJava(host, port);
        try {
             CertificateFetcher.getCertificate(host, port, "OutCert.crt");
             KeyStore keyStore = LoadCertificate.loadCertificate("OutCert.crt");
             SSLContextSetup.createSSLContext();

             System.out.println("Выполняется GET-запрос...");
//            customSocket.sendGetRequest(getRoute, keyStore);
//            System.out.println("GET-запрос успешно выполнен, ответ сохранён в файл.");

            System.out.println("Извлечение sesskey из файла...");
            String sesskey = customSocket.getSesskeyFromHTML("index.html");

            if (!sesskey.equals("not found sesskey")) {
                System.out.println("Sesskey найден: " + sesskey);

                User user = new User(
                        "ieletskii",
                        "passWord123!@#",
                        "test911@yandex.ru",
                        "test911@yandex.ru",
                        "Илья",
                        "Елецкий",
                        "Бишкек",
                        "KG"
                );

                String postData = String.format(
                        "sesskey=%s&_qf__login_signup_form=1&mform_isexpanded_id_createuserandpass=1" +
                                "&mform_isexpanded_id_supplyinfo=1"
                                + "&username=%s"
                                + "&password=%s"
                                + "&email=%s"
                                + "&email2=%s"
                                + "&firstname=%s"
                                + "&lastname=%s"
                                + "&city=%s"
                                + "&country=%s"
                                + "&submitbutton=Создать мой новый аккаунт",
                        sesskey, user.getUsername(), user.getPassword(), user.getEmail(), user.getEmail2(),
                                user.getFirstname(), user.getLastname(), user.getCity(), user.getCountry()
                );

                System.out.println("Выполняется POST-запрос...");
//                customSocket.sendPostRequest(postRoute, postData, keyStore);
//                System.out.println("POST-запрос успешно выполнен.");
            } else {
                System.err.println("Не удалось извлечь sesskey. Проверьте содержимое файла ответа GET-запроса.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
