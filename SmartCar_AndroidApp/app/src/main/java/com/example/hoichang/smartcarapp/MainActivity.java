package com.example.hoichang.smartcarapp;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private Button btnStop;         // 정지
    private Button btnForward;      // 전진
    private Button btnBackward;     // 후진
    private Button btnLeft;         // 좌회전
    private Button btnRight;        // 우회전
    private Button btnHome;         // 바퀴 가운데로
    private Button btnXplus;        // 카메라 오른쪽
    private Button btnXminus;       // 카메라 왼쪽
    private Button btnYplus;        // 카메라 상
    private Button btnYminus;       // 카메라 하
    private Button btnxy_Home;      // 카메라 가운데로
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStop = (Button)findViewById(R.id.btnStop);
        btnForward = (Button)findViewById(R.id.btnForward);
        btnBackward = (Button)findViewById(R.id.btnBackward);
        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);
        btnHome = (Button)findViewById(R.id.btnHome);
        btnXplus = (Button)findViewById(R.id.btnXplus);
        btnXminus = (Button)findViewById(R.id.btnXminus);
        btnYplus = (Button)findViewById(R.id.btnYplus);
        btnYminus = (Button)findViewById(R.id.btnYminus);
        btnxy_Home = (Button)findViewById(R.id.btnxy_Home);

        btnStop.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        btnBackward.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnXplus.setOnClickListener(this);
        btnXminus.setOnClickListener(this);
        btnYplus.setOnClickListener(this);
        btnYminus.setOnClickListener(this);
        btnxy_Home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btnStop : {
                ControlRequest reqStop = new ControlRequest("stop");
                reqStop.start();
                break;
            }

            case R.id.btnForward : {
                ControlRequest reqForward = new ControlRequest("forward");
                reqForward.start();
                break;
            }

            case R.id.btnBackward : {
                ControlRequest reqBackward = new ControlRequest("backward");
                reqBackward.start();
                break;
            }

            case R.id.btnLeft : {
                ControlRequest reqLeft = new ControlRequest("left");
                reqLeft.start();
                break;
            }

            case R.id.btnRight : {
                ControlRequest reqRight = new ControlRequest("right");
                reqRight.start();
                break;
            }

            case R.id.btnHome : {
                ControlRequest reqHome = new ControlRequest("home");
                reqHome.start();
                break;
            }

            case R.id.btnXplus : {
                ControlRequest reqHome = new ControlRequest("x+");
                reqHome.start();
                break;
            }

            case R.id.btnXminus : {
                ControlRequest reqHome = new ControlRequest("x-");
                reqHome.start();
                break;
            }

            case R.id.btnYplus : {
                ControlRequest reqHome = new ControlRequest("y+");
                reqHome.start();
                break;
            }

            case R.id.btnYminus : {
                ControlRequest reqHome = new ControlRequest("y-");
                reqHome.start();
                break;
            }

            case R.id.btnxy_Home : {
                ControlRequest reqHome = new ControlRequest("xy_home");
                reqHome.start();
                break;
            }
        }
    }

    //mobius root url setting
    public class MobiusConfig{
        public final static String MOBIUS_ROOT_URL = "http://192.168.0.9:7579/mobius-yt";
    //public final static String MOBIUS_ROOT_URL = "http://192.168.0.5:7579/mobius-yt";
    }

    //response callback
    public interface IReceived{
        void getResponseBody(String msg);
    }

    //send command to control container of the mobius
    class ControlRequest extends Thread{
        private final Logger LOG = Logger.getLogger(ControlRequest.class.getName());

        private IReceived receiver;

        private String ae_name = "seslab"; //change to your ae name
        private String container_name = "smartcar"; //change to control container name

        private ContentInstanceObject instance;

        public ControlRequest(String comm){
            instance = new ContentInstanceObject();
            instance.setAeName(ae_name);
            instance.setContainerName(container_name);
            instance.setContent(comm);
        }

        public ControlRequest(String aeName, String containerName, String comm){

            this.ae_name = aeName;
            this.container_name = containerName;

            instance = new ContentInstanceObject();
            instance.setAeName(ae_name);
            instance.setContainerName(container_name);
            instance.setContent(comm);
        }

        public void setReceiver(IReceived hanlder){
            this.receiver = hanlder;
        }

        @Override
        public void run() {
            try{
                StringBuilder sb = new StringBuilder();
                sb.append(MobiusConfig.MOBIUS_ROOT_URL).append("/");
                sb.append(ae_name).append("/");
                sb.append(container_name);

                URL mUrl = new URL(sb.toString());

                HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false);

                conn.setRequestProperty("Accept", "application/xml");
                conn.setRequestProperty("Content-Type", "application/vnd.onem2m-res+xml;ty=4");
                conn.setRequestProperty("locale", "ko");
                conn.setRequestProperty("X-M2M-RI", "12345");
                //             conn.setRequestProperty("X-M2M-Origin", "Origin");
                conn.setRequestProperty("X-M2M-Origin", "Sseslab");
                conn.setRequestProperty("nmtype", "short");
                String reqContent = instance.makeBodyXML();
                conn.setRequestProperty("Content-Length", String.valueOf(reqContent.length()));

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(reqContent.getBytes());
                dos.flush();
                dos.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String resp = "";
                String strLine;
                while((strLine = in.readLine()) != null) {
                    resp += strLine;
                }

                if(receiver != null){
                    receiver.getResponseBody(resp);
                }
                conn.disconnect();

            }catch(Exception exp){
                LOG.log(Level.SEVERE, exp.getMessage());
            }
        }
    }
    //content instance data object(for sending command data)
    class ContentInstanceObject {
        private String aeName = "";
        private String containerName = "";
        private String content = "";

        public void setAeName(String value){ this.aeName = value; }

        public void setContainerName(String value) { this.containerName = value; }

        public String getAeName() { return this.aeName; }

        public String getContainerName() { return this.containerName; }

        public void setContent(String value) { this.content = value; }

        public String getContent() { return this.content; }

        public String makeBodyXML(){
            String xml = "";

            xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            xml += "<m2m:cin ";
            xml += "xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" ";
            xml += "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
            xml += "<cnf>text</cnf>";
            xml += "<con>" + content + "</con>";
            xml += "</m2m:cin>";

            return xml;
        }
    }
}


