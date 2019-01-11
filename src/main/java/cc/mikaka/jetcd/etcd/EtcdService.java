package cc.mikaka.jetcd.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EtcdService {
    @Autowired
    private Client client;

    public GetResponse getKey(String key){
        KV kvClient = client.getKVClient();
        CompletableFuture<GetResponse> getFuture = kvClient.get(ByteSequence.fromString(key));
        try {
            return getFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PutResponse addKey(String key,String value){
        KV kvClient = client.getKVClient();
        CompletableFuture<PutResponse> putFuture = kvClient.put(ByteSequence.fromString(key), ByteSequence.fromString(value));
        try {
            return putFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
