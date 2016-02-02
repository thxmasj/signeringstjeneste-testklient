package no.difi.signature.testclient.configuration;

import no.digipost.signature.client.ClientConfiguration;
import no.digipost.signature.client.core.Sender;
import no.digipost.signature.client.core.internal.KeyStoreConfig;
import no.digipost.signature.client.direct.DirectClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class PostenDirectClientConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DirectClient directClient(KeyStore clientKeyStore) {
        KeyStoreConfig keyStoreConfig = new KeyStoreConfig(
                clientKeyStore,
                environment.getRequiredProperty("signatureService.privateKeyAlias"),
                null, // KeyStore password not required/used
                environment.getRequiredProperty("signatureService.privateKeyPassword")
        );
        ClientConfiguration clientConfiguration = new ClientConfiguration(
                URI.create(environment.getRequiredProperty("signatureService.url")),
                keyStoreConfig,
                new Sender(environment.getRequiredProperty("signatureService.senderOrganizationNumber"))
        );
        return new DirectClient(clientConfiguration);
    }

    @Bean
    public KeyStore clientKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(
                getClass().getResourceAsStream(environment.getRequiredProperty("signatureService.clientKeyStoreResource")),
                environment.getRequiredProperty("signatureService.clientKeyStorePassword").toCharArray()
        );
        return keyStore;
    }

}
