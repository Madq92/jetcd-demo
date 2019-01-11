package cc.mikaka.jetcd.configuration;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("etcd")
public class EtcdConfig {
    private String endpoints;
    private String user;
    private String password;

    @Bean
    public Client createEtcdClient() {
        Client client = Client.builder()
                .endpoints(endpoints)
                .user(ByteSequence.fromString(user))
                .password(ByteSequence.fromString(password))
                .lazyInitialization(false)
                .build();
        return client;
    }
}
