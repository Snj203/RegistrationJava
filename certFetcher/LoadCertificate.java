package org.example.certFetcher;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;

public class LoadCertificate {
    public static KeyStore loadCertificate(String certFilePath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        try (FileInputStream fis = new FileInputStream(certFilePath)) {
            var certificate = cf.generateCertificate(fis);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("myCert", certificate);

            return keyStore;
        }
    }
}
