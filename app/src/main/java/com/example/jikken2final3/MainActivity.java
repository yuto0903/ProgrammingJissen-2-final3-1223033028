package com.example.jikken2final3;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //音楽開始ボタン
        Button buttonStart = findViewById(R.id.start);
        //リスナーを登録に登録
        buttonStart.setOnClickListener(v -> {
            //音楽再生
            audioPlay();
        });
        //音楽停止ボタン
        Button buttonStop = findViewById(R.id.stop);
        //リスナーをボタンに登録
        buttonStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                //音楽停止
                audioStop();
            }
        });

    }

    private boolean audioSetup() {
        mediaPlayer = new MediaPlayer();
        //音楽ファイル名、あるいはパス
        String filePath = "ラディーチェ.mp3";
        boolean fileCheck = false;
        //アセットからmp3ファイル読み込み
        try (AssetFileDescriptor afdescripter = getAssets().openFd(filePath)) {
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(), afdescripter.getStartOffset(), afdescripter.getLength());
            //音量調整
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fileCheck;
    }
    private void audioPlay() {
        if(mediaPlayer == null){
            //audio ファイルを読み出し
            if (audioSetup()){
                Toast.makeText(getApplication(),"Read audio file",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplication(),"Error:read audio file",Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            mediaPlayer.stop();
            mediaPlayer.reset();
            //リソースの解放
            mediaPlayer.release();
        }
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(mp ->{
            Log.d("debug","end of audio");
            audioStop();
        });
    }

    private void audioStop() {
        //再生終了
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
