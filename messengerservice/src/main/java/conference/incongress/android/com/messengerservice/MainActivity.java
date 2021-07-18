package conference.incongress.android.com.messengerservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public class ClienHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                //接收来自服务端的消息
                case Constants.MessageFromService:
                    Log.d("sunguoqing", "handleMessage: 接收来自服务器的消息 "+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
    private Messenger messenger;
    private Messenger mClientMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClientMessenger = new Messenger(new ClienHandler());
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);   //创建binder，通过他向服务端发送消息
            Message message = Message.obtain(null,Constants.MessageFromClient);
            Bundle bundle = new Bundle();
            bundle.putString("msg","hello I am client");
            message.setData(bundle);
            message.replyTo = mClientMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}