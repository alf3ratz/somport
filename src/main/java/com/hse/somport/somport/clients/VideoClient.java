package com.hse.somport.somport.clients;

import org.bytedeco.javacv.*;
import org.bytedeco.ffmpeg.avutil.*;
import org.bytedeco.ffmpeg.avcodec.*;
import org.bytedeco.ffmpeg.avformat.*;

public class VideoClient {

    private FFmpegFrameGrabber grabber;
    private FFmpegFrameRecorder recorder;

    public VideoClient() {
        grabber = new FFmpegFrameGrabber("input_video_path_or_device");  // Путь или устройство видео
        recorder = new FFmpegFrameRecorder("output_video_path", 640, 480);  // Путь для записи видео
    }

    public void start() throws Exception {
        grabber.start();  // Запуск захвата видео
        recorder.start();  // Запуск записи видео

        while (true) {
            Frame frame = grabber.grab();  // Захват кадра
            if (frame != null) {
                recorder.record(frame);  // Запись кадра
            }
        }
    }

    public void stop() throws Exception {
        grabber.stop();  // Остановка захвата
        recorder.stop();  // Остановка записи
    }
}
