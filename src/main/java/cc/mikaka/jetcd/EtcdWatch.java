package cc.mikaka.jetcd;


import com.coreos.jetcd.Auth;
import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.Maintenance;
import com.coreos.jetcd.Watch;
import com.coreos.jetcd.auth.AuthUserListResponse;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.maintenance.AlarmMember;
import com.coreos.jetcd.maintenance.AlarmResponse;
import com.coreos.jetcd.watch.WatchEvent;
import com.coreos.jetcd.watch.WatchResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class EtcdWatch {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Client client = Client.builder()
                .endpoints("http://192.168.99.100:32771")
                .user(ByteSequence.fromString("root"))
                .password(ByteSequence.fromString("root"))
                .lazyInitialization(false)
                .build();

        Maintenance maintenanceClient = client.getMaintenanceClient();
        AlarmResponse alarmResponse = maintenanceClient.listAlarms().get();
        List<AlarmMember> alarms = alarmResponse.getAlarms();
        print(alarms, EtcdWatch::alarms2String);


        KV kvClient = client.getKVClient();
        GetResponse getResponse = kvClient.get(ByteSequence.fromString("aaa")).get();
        List<KeyValue> kvs = getResponse.getKvs();
        print(kvs, EtcdWatch::kv2String);

        Watch watchClient = client.getWatchClient();
        Watch.Watcher aaa = watchClient.watch(ByteSequence.fromString("aaa"));

        while (true) {
            try {
                WatchResponse listen = aaa.listen();
                List<WatchEvent> events = listen.getEvents();
                print(events, EtcdWatch::event2String);
            } catch (Exception e) {
                break;
            }
        }

        System.err.println("over");
    }

    public static <T> void print(List<T> list, Function<? super T, ? super String> function) {
        if (null != list && list.size() != 0) {
            list.stream().forEach(o -> System.err.println(function.apply(o)));
        } else {
            System.err.println("---null---");
        }
    }

    public static String event2String(WatchEvent event) {
        StringBuffer sb = new StringBuffer();
        sb.append("eventType = ").append(event.getEventType()).append(System.getProperty("line.separator"));
        sb.append("kv = ").append(kv2String(event.getKeyValue())).append(System.getProperty("line.separator"));
        sb.append("preKv = ").append(kv2String(event.getPrevKV())).append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public static String kv2String(KeyValue keyValue) {
        return "key:" + keyValue.getKey().toStringUtf8() + ";" + "value:" + keyValue.getValue().toStringUtf8();
    }

    public static String alarms2String(AlarmMember alarmMember) {
        return "alarmType:" + alarmMember.getAlarmType() + ";" + "memberId:" + alarmMember.getMemberId();
    }

}
