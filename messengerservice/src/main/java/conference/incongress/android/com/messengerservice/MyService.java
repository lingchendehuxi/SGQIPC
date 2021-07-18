package conference.incongress.android.com.messengerservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyService extends Service {
    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case Constants.MessageFromClient:
                    Log.d("sunguoqing", "handleMessage: 来自客户端的消息"+msg.getData().getString("msg"));
                    Messenger client = msg.replyTo;
                    Message replymsg = Message.obtain(null,Constants.MessageFromService);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","I Service have receive your message");
                    replymsg.setData(bundle);
                    try {
                        client.send(replymsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
    private final Messenger messenger = new Messenger(new MyHandler());

}