//import dev.onvoid.webrtc.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//
//public class WebRtcClient {
//
//    private final RTCPeerConnection peerConnection;
//
//    public WebRtcClient(String serverUrl) {
//        // Инициализация PeerConnectionFactory
//        PeerConnectionFactory factory = new PeerConnectionFactory();
//
//        // Настройка ICE-серверов
//        new RTCIceServer().username;
//        RTCPe.IceServer iceServer = RTCIceServer  ("stun:stun.l.google.com:19302").createIceServer();
//        PeerConnection.RTCConfiguration config = new PeerConnection.RTCConfiguration(iceServer);
//
//        // Создание PeerConnection
//        this.peerConnection = factory.createPeerConnection(config, new CustomPeerConnectionObserver());
//
//        // Создание VideoTrack (для эмуляции камеры)
//        createMockVideoTrack(factory);
//
//        // Отправка SDP-предложения на сервер
//        sendSdpOffer(serverUrl);
//    }
//
//    private void createMockVideoTrack(PeerConnectionFactory factory) {
//        // Эмуляция видео с помощью чёрного кадра
//        VideoTrack localVideoTrack = factory.createVideoTrack("video", factory.createVideoSource(() -> {
//            return VideoFrame.createBlackFrame(640, 480);
//        }));
//
//        // Добавление трека в PeerConnection
//        MediaStream stream = factory.createLocalMediaStream("stream");
//        stream.addTrack(localVideoTrack);
//        peerConnection.addStream(stream);
//    }
//
//    private void sendSdpOffer(String serverUrl) {
//        // Создание предложения
//        peerConnection.createOffer(new MediaConstraints(), offer -> {
//            peerConnection.setLocalDescription(new CustomSdpObserver("setLocalDescription"), offer);
//
//            // Отправка предложения на сервер
//            RestTemplate restTemplate = new RestTemplate();
//            JSONObject json = new JSONObject();
//            json.put("sdp", offer.toString());
//            json.put("type", "offer");
//
//            String response = restTemplate.postForObject(URI.create(serverUrl + "/offer"), json.toString(), String.class);
//            JSONObject answerJson = new JSONObject(response);
//
//            // Установка ответа
//            SessionDescription answer = new SessionDescription(
//                    SessionDescription.Type.fromCanonicalForm(answerJson.getString("type")),
//                    answerJson.getString("sdp")
//            );
//            peerConnection.setRemoteDescription(new CustomSdpObserver("setRemoteDescription"), answer);
//        }, error -> System.err.println("Error creating offer: " + error));
//    }
//
//    public void close() {
//        peerConnection.dispose();
//    }
//
//    // Наблюдатель для PeerConnection
//    private static class CustomPeerConnectionObserver implements PeerConnection.Observer {
//        @Override
//        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
//            System.out.println("Signaling state changed: " + signalingState);
//        }
//
//        @Override
//        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
//            System.out.println("ICE connection state changed: " + iceConnectionState);
//        }
//
//        @Override
//        public void onAddStream(MediaStream mediaStream) {
//            System.out.println("Received remote stream");
//            mediaStream.videoTracks.forEach(track -> track.addSink(frame -> {
//                System.out.println("Received video frame");
//            }));
//        }
//
//        @Override
//        public void onIceCandidate(IceCandidate iceCandidate) {
//            System.out.println("New ICE candidate: " + iceCandidate);
//        }
//    }
//
//    // Наблюдатель для SDP
//    private static class CustomSdpObserver implements SdpObserver {
//        private final String label;
//
//        public CustomSdpObserver(String label) {
//            this.label = label;
//        }
//
//        @Override
//        public void onCreateSuccess(SessionDescription sessionDescription) {
//            System.out.println(label + ": SDP created successfully");
//        }
//
//        @Override
//        public void onSetSuccess() {
//            System.out.println(label + ": SDP set successfully");
//        }
//
//        @Override
//        public void onCreateFailure(String s) {
//            System.err.println(label + ": Error creating SDP: " + s);
//        }
//
//        @Override
//        public void onSetFailure(String s) {
//            System.err.println(label + ": Error setting SDP: " + s);
//        }
//    }
//}